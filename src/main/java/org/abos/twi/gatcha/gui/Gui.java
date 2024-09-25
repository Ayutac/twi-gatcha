package org.abos.twi.gatcha.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.abos.twi.gatcha.core.Booster;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.data.Characters;
import org.abos.twi.gatcha.data.Lookups;
import org.abos.twi.gatcha.gui.component.pane.AbstractScreen;
import org.abos.twi.gatcha.gui.component.pane.BattleScreen;
import org.abos.twi.gatcha.gui.component.pane.BoosterScreen;
import org.abos.twi.gatcha.gui.component.pane.CampaignScreen;
import org.abos.twi.gatcha.gui.component.pane.CharacterScreen;
import org.abos.twi.gatcha.gui.component.pane.MainMenu;
import org.abos.twi.gatcha.gui.component.pane.HomeScreen;
import org.abos.twi.gatcha.gui.component.pane.MissionModeScreen;
import org.abos.twi.gatcha.gui.component.pane.PartyScreen;
import org.abos.twi.gatcha.gui.component.pane.QuestScreen;
import org.abos.twi.gatcha.gui.component.pane.RoosterScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public final class Gui extends Application {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final Map<CharacterBase, Image> IMAGE_MAP;
    public static final Map<CharacterBase, Image> IMAGE_HEX_MAP;

    private final @NotNull Scene mainMenuScene = new Scene(new MainMenu(this), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull HomeScreen homeScreen = new HomeScreen(this);
    private final @NotNull Scene homeScreenScene = new Scene(homeScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull RoosterScreen roosterScreen = new RoosterScreen(this);
    private final @NotNull Scene roosterScreenScene = new Scene(roosterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull CharacterScreen characterScreen = new CharacterScreen(this);
    private final @NotNull Scene characterScreenScene = new Scene(characterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull PartyScreen partyScreen = new PartyScreen(this);
    private final @NotNull Scene partyScreenScene = new Scene(partyScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull BoosterScreen boosterScreen = new BoosterScreen(this);
    private final @NotNull Scene boosterScreenScene = new Scene(boosterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull MissionModeScreen missionModeScreen = new MissionModeScreen(this);
    private final @NotNull Scene missionModeScreenScene = new Scene(missionModeScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull CampaignScreen campaignScreen = new CampaignScreen(this);
    private final @NotNull Scene campaignScreenScene = new Scene(campaignScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull BattleScreen battleScreen = new BattleScreen(this);
    private final @NotNull Scene battleScreenScene = new Scene(battleScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull QuestScreen questScreen = new QuestScreen(this);
    private final @NotNull Scene questScreenScene = new Scene(questScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final @NotNull FileChooser chooser = new FileChooser();
    private Stage stage;

    private @Nullable Player player;

    static {
        final Map<CharacterBase, Image> imageMap = new HashMap<>();
        final Map<CharacterBase, Image> imageHexMap = new HashMap<>();
        try {
            for (final Field characterField : Characters.class.getFields()) {
                final CharacterBase character = (CharacterBase) characterField.get(null);
                imageMap.put(character, new Image("/textures/characters/" + character.imageName()));
                imageHexMap.put(character, new Image("/textures/hexagons/" + character.imageName()));
            }
        }
        catch (IllegalAccessException ex) {
            Logger.getGlobal().warning("Couldn't access characters!");
        }
        IMAGE_MAP = Map.copyOf(imageMap);
        IMAGE_HEX_MAP = Map.copyOf(imageHexMap);
        try {
            Lookups.registerAll();
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Lookup registration failed!", ex);
        }
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        setupKeyboardNavigation();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TWI Gacha save files", "*.sav"));
        this.stage.setScene(mainMenuScene);
        this.stage.show();
    }

    private void setupKeyboardNavigation() {
        final EventHandler<KeyEvent> simplyReturnHome = keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showHomeScreen();
            }
            else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                saveGame();
            }
        };
        homeScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                saveGame();
            }
        });
        characterScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showRoosterScreen(homeScreen);
            }
        });
        roosterScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                if (roosterScreen.getCaller() instanceof PartyScreen) {
                    showPartyScreen(false);
                }
                else {
                    showHomeScreen();
                }
            }
        });
        partyScreenScene.setOnKeyReleased(simplyReturnHome);
        boosterScreenScene.setOnKeyReleased(simplyReturnHome);
        missionModeScreenScene.setOnKeyReleased(simplyReturnHome);
        campaignScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showMissionModeScreen();
            }
        });
        questScreenScene.setOnKeyReleased(simplyReturnHome);
    }

    @Override
    public void stop() {
        battleScreen.shutdown();
        Battle.shutdown();
        saveGame();
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(final @Nullable Player player) {
        this.player = player;
        roosterScreen.setPlayer(this.player);
        partyScreen.setPlayer(this.player);
    }

    public void newGame() {
        final Player newPlayer = new Player("Dev", Instant.now().truncatedTo(ChronoUnit.HALF_DAYS));
        newPlayer.addToRooster(Characters.ERIN);
        newPlayer.addParty(new Party("First Party", List.of(
                newPlayer.getCharacter(Characters.ERIN))));
        setPlayer(newPlayer);
        showHomeScreen();

        // Test code
        for (int i = 0; i < 30; i++) {
            newPlayer.getCharacter(Characters.ERIN).increaseLevel(newPlayer.getStats());
        }
        player.addToRooster(Characters.CERIA);
        player.addToRooster(Characters.PISCES);
        player.addToRooster(Characters.PISCES_CRELER);
        player.addToRooster(Characters.KSMVR);
        player.addToRooster(Characters.YVLON);
        player.addToRooster(Characters.ZOMBIE);
        player.addToRooster(Characters.SKELETON);
        player.addToRooster(Characters.SKELETON_ARCHER);
    }

    public void saveGame() {
        if (player == null) {
            return;
        }
        File location = chooser.showSaveDialog(stage);
        if (location == null) {
            return;
        }
        final String fileString = location.toString();
        if (!fileString.endsWith(".sav")) {
            location = new File(fileString + ".sav");
        }
        try (final FileOutputStream fos = new FileOutputStream(location);
             final ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            player.save(oos);
        } catch (final IOException ex) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh!");
            alert.setContentText("Couldn't save game!");
            alert.showAndWait();
        }
    }

    public void loadGame() {
        final File location = chooser.showOpenDialog(stage);
        if (location == null) {
            return;
        }
        try (final FileInputStream fis = new FileInputStream(location);
             final ObjectInputStream ois = new ObjectInputStream(fis)) {
            setPlayer(Player.load(ois));
            showHomeScreen();
        } catch (final IOException ex) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh!");
            alert.setContentText("Couldn't load game!");
            alert.showAndWait();
        }
    }

    public void showHomeScreen() {
        homeScreen.update();
        stage.setScene(homeScreenScene);
    }

    public void showRoosterScreen(final @Nullable AbstractScreen caller) {
        roosterScreen.setCaller(caller);
        roosterScreen.updateGallery();
        stage.setScene(roosterScreenScene);
    }

    public void showCharacterScreen(final @NotNull CharacterModified character) {
        characterScreen.setCharacter(character);
        stage.setScene(characterScreenScene);
    }

    public void showPartyScreen(boolean reset) {
        if (reset) {
            partyScreen.update();
        }
        stage.setScene(partyScreenScene);
    }

    public void showBoosterScreen() {
        boosterScreen.update();
        stage.setScene(boosterScreenScene);
    }

    public void showMissionModeScreen() {
        stage.setScene(missionModeScreenScene);
    }

    public void showCampaignScreen() {
        campaignScreen.update();
        stage.setScene(campaignScreenScene);
    }

    public void showBattleScreen(final @NotNull AbstractScreen caller, final @NotNull Battle battle) {
        if (player == null || player.getNumberOfParties() == 0) {
            return;
        }
        battleScreen.setCaller(caller);
        battleScreen.setBattle(battle);
        stage.setScene(battleScreenScene);
        battle.startPlacement(player.getParty(0));
    }

    public void showQuestScreen() {
        questScreen.update();
        stage.setScene(questScreenScene);
    }

    public static void showNotImplemented() {
        final Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Uh oh!");
        warning.setContentText("This feature is not yet implemented!");
        warning.showAndWait();
    }

    public static @NotNull Optional<Booster> getBoosterByName(final @Nullable String name) {
        return Optional.ofNullable(Lookups.BOOSTERS.get(name));
    }

    public static void main(String[] args) {
        launch();
    }
}

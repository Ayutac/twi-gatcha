package org.abos.twi.gatcha.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.data.Characters;
import org.abos.twi.gatcha.gui.component.pane.AbstractScreen;
import org.abos.twi.gatcha.gui.component.pane.BattleScreen;
import org.abos.twi.gatcha.gui.component.pane.CharacterScreen;
import org.abos.twi.gatcha.gui.component.pane.MainMenu;
import org.abos.twi.gatcha.gui.component.pane.HomeScreen;
import org.abos.twi.gatcha.gui.component.pane.MissionModeScreen;
import org.abos.twi.gatcha.gui.component.pane.PartyScreen;
import org.abos.twi.gatcha.gui.component.pane.RoosterScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class Gui extends Application {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final Map<CharacterBase, Image> IMAGE_MAP;
    public static final Map<CharacterBase, Image> IMAGE_HEX_MAP;

    private final Scene mainMenuScene = new Scene(new MainMenu(this), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final HomeScreen homeScreen = new HomeScreen(this);
    private final Scene homeMenuScene = new Scene(homeScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final RoosterScreen roosterScreen = new RoosterScreen(this);
    private final Scene roosterScreenScene = new Scene(roosterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final CharacterScreen characterScreen = new CharacterScreen(this);
    private final Scene characterScreenScene = new Scene(characterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final PartyScreen partyScreen = new PartyScreen(this);
    private final Scene partyScreenScene = new Scene(partyScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final MissionModeScreen missionModeScreen = new MissionModeScreen(this);
    private final Scene missionModeScreenScene = new Scene(missionModeScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final BattleScreen battleScreen = new BattleScreen(this);
    private final Scene battleScreenScene = new Scene(battleScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        characterScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showRoosterScreen(homeScreen);
            }
        });
        roosterScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                if (roosterScreen.getCaller() instanceof PartyScreen) {
                    showPartyScreen();
                }
                else {
                    showHomeScreen();
                }
            }
        });
        partyScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showHomeScreen();
            }
        });
        missionModeScreenScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE || keyEvent.getCode() == KeyCode.BACK_SPACE) {
                showHomeScreen();
            }
        });
        this.stage.setScene(mainMenuScene);
        this.stage.show();
    }

    @Override
    public void stop() {
        Battle.shutdown();
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
        final Player newPlayer = new Player("Dev");
        newPlayer.addToRooster(Characters.ERIN);
        newPlayer.addParty(new Party("First Party", List.of(
                newPlayer.getCharacter(Characters.ERIN))));
        setPlayer(newPlayer);
        stage.setScene(homeMenuScene);

        // Test code
        player.addToRooster(Characters.CERIA);
        player.addToRooster(Characters.PISCES);
        player.addToRooster(Characters.KSMVR);
        player.addToRooster(Characters.YVLON);
        player.addToRooster(Characters.ZOMBIE);
        player.addToRooster(Characters.SKELETON);
        player.addToRooster(Characters.SKELETON_ARCHER);
        /*final Level level = new Level(10, 10, List.of(), Set.of(
                new Wave(0, List.of(
                        new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(9, 9), DirectRandomAttacker::new),
                        new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(9, 8), DirectRandomAttacker::new),
                        new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(9, 7), DirectRandomAttacker::new),
                        new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(9, 5), DirectRandomAttacker::new)))),
                Set.of(new Vec2i(0, 0), new Vec2i(0, 1), new Vec2i(0, 2), new Vec2i(1, 0), new Vec2i(1, 1), new Vec2i(1, 2)));
        final Battle battle = level.prepareBattle();
        battle.startPlacement(List.of(
                new CharacterModified(Characters.PISCES),
                new CharacterModified(Characters.CERIA),
                new CharacterModified(Characters.KSMVR),
                new CharacterModified(Characters.YVLON)));
        stage.setScene(battleScreenScene);
        battleScreen.setBattle(battle);*/
    }

    public void showHomeScreen() {
        stage.setScene(homeMenuScene);
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

    public void showPartyScreen() {
        stage.setScene(partyScreenScene);
    }

    public void showMissionModeScreen() {
        stage.setScene(missionModeScreenScene);
    }

    public static void showNotImplemented() {
        final Alert warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Uh oh!");
        warning.setContentText("This feature is not yet implemented!");
        warning.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}

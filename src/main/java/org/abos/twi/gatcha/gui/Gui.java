package org.abos.twi.gatcha.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.data.Characters;
import org.abos.twi.gatcha.gui.component.pane.BattleScreen;
import org.abos.twi.gatcha.gui.component.pane.CharacterScreen;
import org.abos.twi.gatcha.gui.component.pane.MainMenu;
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
    private final BattleScreen battleScreen = new BattleScreen(this);
    private final Scene battleScreenScene = new Scene(battleScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final RoosterScreen roosterScreen = new RoosterScreen(this);
    private final Scene roosterScreenScene = new Scene(roosterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final CharacterScreen characterScreen = new CharacterScreen(this);
    private final Scene characterScreenScene = new Scene(characterScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private final PartyScreen partyScreen = new PartyScreen(this);
    private final Scene partyScreenScene = new Scene(partyScreen, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private Stage stage;

    private Player player;

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
                showRoosterScreen(null);
            }
        });
        this.stage.setScene(mainMenuScene);
        this.stage.show();
    }

    public void newGame() {
        player = new Player("Dev");
        player.addToRooster(Characters.CERIA);
        player.addToRooster(Characters.PISCES);
        player.addToRooster(Characters.KSMVR);
        player.addToRooster(Characters.ERIN);
        player.addToRooster(Characters.YVLON);
        player.addToRooster(Characters.ZOMBIE);
        player.addToRooster(Characters.SKELETON);
        player.addToRooster(Characters.SKELETON_ARCHER);
//        stage.setScene(roosterScreenScene);
        roosterScreen.setPlayer(player);
        player.addParty(new Party("test", List.of(
                player.getCharacter(Characters.ERIN),
                player.getCharacter(Characters.YVLON))));
        stage.setScene(partyScreenScene);
        partyScreen.setPlayer(player);
        /*final Battle battle = new Battle(10, 10, List.of());
        battle.addWave(new Wave(0, List.of(
                new DirectRandomAttacker(new CharacterModified(Characters.ZOMBIE), battle, TeamKind.ENEMY, new Vec2i(9, 9)),
                new DirectRandomAttacker(new CharacterModified(Characters.ZOMBIE), battle, TeamKind.ENEMY, new Vec2i(9, 8)),
                new DirectRandomAttacker(new CharacterModified(Characters.ZOMBIE), battle, TeamKind.ENEMY, new Vec2i(9, 7)),
                new DirectRandomAttacker(new CharacterModified(Characters.ZOMBIE), battle, TeamKind.ENEMY, new Vec2i(9, 5)))));
        battle.addPlayerSpawn(new Vec2i(0, 0));
        battle.addPlayerSpawn(new Vec2i(0, 1));
        battle.addPlayerSpawn(new Vec2i(0, 2));
        battle.addPlayerSpawn(new Vec2i(1, 0));
        battle.addPlayerSpawn(new Vec2i(1, 1));
        battle.addPlayerSpawn(new Vec2i(1, 2));
        battle.startPlacement(List.of(
                new CharacterModified(Characters.PISCES),
                new CharacterModified(Characters.CERIA),
                new CharacterModified(Characters.KSMVR),
                new CharacterModified(Characters.YVLON)));
        stage.setScene(battleScreenScene);
        battleScreen.setBattle(battle);*/
    }

    public void showRoosterScreen(final @Nullable PartyScreen caller) {
        roosterScreen.setCaller(caller);
        stage.setScene(roosterScreenScene);
    }

    public void showCharacterScreen(final @NotNull CharacterModified character) {
        characterScreen.setCharacter(character);
        stage.setScene(characterScreenScene);
    }

    public void showPartyScreen() {
        stage.setScene(partyScreenScene);
    }

    public static void main(String[] args) {
        launch();
    }
}

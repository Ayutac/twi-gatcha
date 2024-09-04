package org.abos.twi.gatcha.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.battle.Wave;
import org.abos.twi.gatcha.core.battle.ai.SlowWanderer;
import org.abos.twi.gatcha.data.Characters;
import org.abos.twi.gatcha.gui.component.pane.BattlefieldPane;
import org.abos.twi.gatcha.gui.component.pane.MainMenu;

import java.util.List;

public final class Gui extends Application {

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;

    private final Scene mainMenuScene = new Scene(new MainMenu(this), DEFAULT_WIDTH, DEFAULT_HEIGHT);
    private Stage stage;

    private Player player;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setScene(mainMenuScene);
        this.stage.show();
    }

    public void newGame() {
        final Battle battle = new Battle(10, 10, List.of());
        battle.addWave(new Wave(0, List.of(new SlowWanderer(new CharacterModified(Characters.ERIN), battle, TeamKind.ENEMY, new Vec2i(9, 9)))));
        battle.startPlacement();
        final BattlefieldPane battlefieldPane = new BattlefieldPane(battle, 30);
        stage.setScene(new Scene(battlefieldPane, DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public static void main(String[] args) {
        launch();
    }
}

package org.abos.twi.gatcha.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Level;
import org.abos.twi.gatcha.gui.component.pane.LevelPane;
import org.abos.twi.gatcha.gui.component.pane.MainMenu;

import java.util.List;

public final class Gui extends Application {

    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;

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
        stage.setScene(new Scene(new LevelPane(new Level(10, 10, List.of()), 30, 50, 50), DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public static void main(String[] args) {
        launch();
    }
}

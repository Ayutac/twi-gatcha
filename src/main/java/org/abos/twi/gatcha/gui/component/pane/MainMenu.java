package org.abos.twi.gatcha.gui.component.pane;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class MainMenu extends AbstractScreen {

    private static final int BUTTON_WIDTH = 100;

    public MainMenu(final @NotNull Gui gui) {
        super(gui);
        final Button newGame = new Button("New Game");
        newGame.setMinWidth(BUTTON_WIDTH);
        newGame.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.gui.newGame();
            }
        });
        final Button loadGame = new Button("Load Game");
        loadGame.setMinWidth(BUTTON_WIDTH);
        loadGame.setOnMouseClicked(mouseEvent -> Gui.showNotImplemented());
        final Button exit = new Button("Exit");
        exit.setMinWidth(BUTTON_WIDTH);
        exit.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Platform.exit();
            }
        });
        final VBox buttons = new VBox(newGame, loadGame, exit);
        setRight(buttons);
    }

}
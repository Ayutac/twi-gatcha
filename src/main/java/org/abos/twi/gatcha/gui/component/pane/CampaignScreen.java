package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.data.Levels;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class CampaignScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 100;

    private final @NotNull Button oneOneBtn = new Button("1-1");
    private final @NotNull Button oneTwoBtn = new Button("1-2");

    public CampaignScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showMissionModeScreen();
            }
        });
        oneOneBtn.setPrefWidth(BUTTON_WIDTH);
        oneOneBtn.setOnMouseClicked(mouseEvent -> this.gui.showBattleScreen(this, Levels.ONE_ONE.prepareBattle()));
        oneTwoBtn.setPrefWidth(BUTTON_WIDTH);
        oneTwoBtn.setOnMouseClicked(mouseEvent -> this.gui.showBattleScreen(this, Levels.ONE_TWO.prepareBattle()));
        final VBox centerBox = new VBox(oneOneBtn, oneTwoBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }

}

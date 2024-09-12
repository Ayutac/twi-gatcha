package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class MissionModeScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 150;

    private final @NotNull Button campaignBtn = new Button("Campaign");
    private final @NotNull Button dungeonBtn = new Button("Dungeon");

    public MissionModeScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showHomeScreen();
            }
        });
        campaignBtn.setPrefWidth(BUTTON_WIDTH);
        campaignBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.gui.showCampaignScreen();
            }
        });
        dungeonBtn.setPrefWidth(BUTTON_WIDTH);
        dungeonBtn.setOnMouseClicked(mouseEvent -> Gui.showNotImplemented());
        final VBox rightBox = new VBox(campaignBtn, dungeonBtn);
        setRight(rightBox);
    }

}

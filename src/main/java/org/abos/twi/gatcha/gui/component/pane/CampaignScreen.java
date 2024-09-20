package org.abos.twi.gatcha.gui.component.pane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Level;
import org.abos.twi.gatcha.data.Levels;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class CampaignScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 100;

    private final @NotNull Button zeroOneBtn = new Button(Levels.ZERO_ONE.getName());
    private final @NotNull Button zeroTwoBtn = new Button(Levels.ZERO_TWO.getName());

    public CampaignScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showMissionModeScreen();
            }
        });
        zeroOneBtn.setPrefWidth(BUTTON_WIDTH);
        zeroOneBtn.setOnMouseClicked(enterLevelEvent(Levels.ZERO_ONE));
        zeroOneBtn.setTooltip(new Tooltip(String.format("%d Stamina", Levels.ZERO_ONE.staminaNeeded())));
        zeroOneBtn.setDisable(true);
        zeroTwoBtn.setPrefWidth(BUTTON_WIDTH);
        zeroTwoBtn.setOnMouseClicked(enterLevelEvent(Levels.ZERO_TWO));
        zeroTwoBtn.setTooltip(new Tooltip(String.format("%d Stamina", Levels.ZERO_TWO.staminaNeeded())));
        zeroTwoBtn.setDisable(true);
        final VBox centerBox = new VBox(zeroOneBtn, zeroTwoBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }

    public void update() {
        final Player player = gui.getPlayer();
        if (player == null) {
            return;
        }
        zeroOneBtn.setDisable(!Levels.ZERO_ONE.satisfiesRequirements(player));
        zeroTwoBtn.setDisable(!Levels.ZERO_TWO.satisfiesRequirements(player));
    }

    private EventHandler<? super MouseEvent> enterLevelEvent(final @NotNull Level level) {
        return mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            gui.showBattleScreen(this, level.prepareBattle());
        };
    }

}

package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.quest.Quest;
import org.abos.twi.gatcha.data.Quests;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class QuestScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 100;

    private final @NotNull GridPane gridPane = new GridPane();
    private final @NotNull Map<Quest, Button> buttons = new HashMap<>();

    public QuestScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showHomeScreen();
            }
        });
        int row = 0;
        gridPane.add(new Label(Quests.DAILY_ENEMIES_DEFEATED_5.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_ENEMIES_DEFEATED_5), 1, row++);
        gridPane.add(new Label(Quests.DAILY_ENEMIES_DEFEATED_50.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_ENEMIES_DEFEATED_50), 1, row++);
        gridPane.add(new Label(Quests.DAILY_CHARACTERS_LEVELLED_UP_1.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_CHARACTERS_LEVELLED_UP_1), 1, row++);
        gridPane.add(new Label(Quests.DAILY_CHARACTERS_LEVELLED_UP_10.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_CHARACTERS_LEVELLED_UP_10), 1, row++);
        gridPane.add(new Label(Quests.DAILY_LEVELS_WON_1.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_LEVELS_WON_1), 1, row++);
        gridPane.add(new Label(Quests.DAILY_LEVELS_WON_10.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_LEVELS_WON_10), 1, row++);
        gridPane.add(new Label(Quests.DAILY_BOOSTERS_PULLED_1.description()), 0, row);
        gridPane.add(installButton(Quests.DAILY_BOOSTERS_PULLED_1), 1, row++);
        final HBox centerBox = new HBox(gridPane);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);
    }

    private Button installButton(final @NotNull Quest quest) {
        final Button button = new Button("Collect");
        button.setPrefWidth(BUTTON_WIDTH);
        button.setDisable(true);
        button.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                final Player player = gui.getPlayer();
                if (player == null) {
                    return;
                }
                if (quest.maybeGive(player)) {
                    final Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Congratulations!");
                    info.setContentText(String.format("You received %s!", quest.reward()));
                    info.showAndWait();
                    update();
                }
            }
        });
        buttons.put(quest, button);
        return button;
    }

    public void update() {
        final Player player = gui.getPlayer();
        if (player != null) {
            buttons.forEach((quest, button) -> {
                button.setText("Collect" + (quest.hasBeenTaken(player.getStats()) ? "ed" : ""));
                button.setDisable(
                        quest.hasBeenTaken(player.getStats()) || !quest.fulfilledBy(player.getStats()));
            });
        }
        else {
            for (final Button button : buttons.values()) {
                button.setText("Collect");
                button.setDisable(true);
            }
        }
    }

}

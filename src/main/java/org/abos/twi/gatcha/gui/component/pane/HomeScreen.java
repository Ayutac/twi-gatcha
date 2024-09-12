package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.core.InventoryKind;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class HomeScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 180;

    private final @NotNull Label greetingsLabel = new Label();
    private final @NotNull Label currenciesLabel = new Label();
    private final @NotNull Button roosterBtn = new Button("Garden of Sanctuary");
    private final @NotNull Button partyBtn = new Button("Pavilion of Secrets");
    private final @NotNull Button pullBtn = new Button("Palace of Fates");
    private final @NotNull Button missionBtn = new Button("<Quests>");

    public HomeScreen(@NotNull Gui gui) {
        super(gui);
        final VBox topBox = new VBox(greetingsLabel, currenciesLabel);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);
        // right buttons
        roosterBtn.setPrefWidth(BUTTON_WIDTH);
        roosterBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.gui.showRoosterScreen(this);
            }
        });
        partyBtn.setPrefWidth(BUTTON_WIDTH);
        partyBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.gui.showPartyScreen();
            }
        });
        pullBtn.setPrefWidth(BUTTON_WIDTH);
        pullBtn.setOnMouseClicked(mouseEvent -> Gui.showNotImplemented());
        missionBtn.setPrefWidth(BUTTON_WIDTH);
        missionBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.gui.showMissionModeScreen();
            }
        });
        final VBox rightBox = new VBox(roosterBtn, partyBtn, pullBtn, missionBtn);
        setRight(rightBox);
    }

    public void update() {
        final Player player = gui.getPlayer();
        if (player != null) {
            greetingsLabel.setText("Welcome " + player.getName() + "!");
            final int gold = player.getInventory().get(InventoryKind.GOLD);
            final int magicore = player.getInventory().get(InventoryKind.MAGICORE);
            final int flowers = player.getInventory().get(InventoryKind.FAERIE_FLOWERS);
            currenciesLabel.setText(String.format("%d %s, %d %s, %d %s",
                    gold, InventoryKind.GOLD.getName(gold == 1),
                    magicore, InventoryKind.MAGICORE.getName(magicore == 1),
                    flowers, InventoryKind.FAERIE_FLOWERS.getName(flowers == 1)));
        }
        else {
            greetingsLabel.setText("");
            currenciesLabel.setText("");
        }
    }

}

package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

public final class HomeScreen extends AbstractScreen {

    private static final int BUTTON_WIDTH = 180;

    private final @NotNull Button roosterBtn = new Button("Garden of Sanctuary");
    private final @NotNull Button partyBtn = new Button("Pavilion of Secrets");
    private final @NotNull Button pullBtn = new Button("Palace of Fates");
    private final @NotNull Button missionBtn = new Button("<Quests>");

    public HomeScreen(@NotNull Gui gui) {
        super(gui);
        roosterBtn.setPrefWidth(BUTTON_WIDTH);
        roosterBtn.setOnMouseClicked(mouseEvent -> this.gui.showRoosterScreen(this));
        partyBtn.setPrefWidth(BUTTON_WIDTH);
        partyBtn.setOnMouseClicked(mouseEvent -> this.gui.showPartyScreen());
        pullBtn.setPrefWidth(BUTTON_WIDTH);
        pullBtn.setOnMouseClicked(mouseEvent -> Gui.showNotImplemented());
        missionBtn.setPrefWidth(BUTTON_WIDTH);
        missionBtn.setOnMouseClicked(mouseEvent -> Gui.showNotImplemented());
        final VBox rightBox = new VBox(roosterBtn, partyBtn, pullBtn, missionBtn);
        setRight(rightBox);
    }

}

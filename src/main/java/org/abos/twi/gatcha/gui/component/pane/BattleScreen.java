package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.abos.common.Named;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.BattlePhase;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;

public final class BattleScreen extends StackPane {

    private final Gui gui;

    private final @NotNull BorderPane borderPane = new BorderPane();
    private final @NotNull Label turnOrder = new Label();
    private @Nullable BattlefieldPane battlefieldPane;

    private @Nullable Battle battle;

    public BattleScreen(final @NotNull Gui gui) {
        this.gui = Objects.requireNonNull(gui);
        final VBox topBox = new VBox(new Label("Turn Order"), turnOrder);
        borderPane.setTop(topBox);
        getChildren().add(borderPane);
    }

    public @Nullable Battle getBattle() {
        return battle;
    }

    public void setBattle(final @Nullable Battle battle) {
        this.battle = battle;
        if (this.battle != null) {
            battlefieldPane = new BattlefieldPane(this, battle, 30);
            borderPane.setCenter(battlefieldPane);
        }
        else {
            battlefieldPane = null;
            borderPane.setCenter(null);
            turnOrder.setText("");
        }
    }

    public void update() {
        if (battle != null && battle.getPhase().ordinal() > BattlePhase.PLACEMENT.ordinal()) {
            final String text = battle.getCharacterOrder().stream().map(Named::getName).collect(Collectors.joining(", "));
            turnOrder.setText(text);
        }
    }
}

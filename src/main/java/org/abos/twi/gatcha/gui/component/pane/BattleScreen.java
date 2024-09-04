package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BattleScreen extends StackPane {

    private final Gui gui;

    private final @NotNull BorderPane borderPane = new BorderPane();
    private @Nullable BattlefieldPane battlefieldPane;

    private @Nullable Battle battle;

    public BattleScreen(final @NotNull Gui gui) {
        this.gui = Objects.requireNonNull(gui);
        getChildren().add(borderPane);
    }

    public @Nullable Battle getBattle() {
        return battle;
    }

    public void setBattle(final @Nullable Battle battle) {
        this.battle = battle;
        if (this.battle != null) {
            battlefieldPane = new BattlefieldPane(battle, 30);
            borderPane.setCenter(battlefieldPane);
        }
        else {
            battlefieldPane = null;
            borderPane.setCenter(null);
        }
    }
}

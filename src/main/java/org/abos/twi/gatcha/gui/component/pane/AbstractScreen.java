package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.layout.BorderPane;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AbstractScreen extends BorderPane {

    protected final @NotNull Gui gui;

    public AbstractScreen(final @NotNull Gui gui) {
        this.gui = Objects.requireNonNull(gui);
    }

    public @NotNull Gui getGui() {
        return gui;
    }
}

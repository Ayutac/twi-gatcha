package org.abos.twi.gatcha;

import javafx.application.Platform;
import org.abos.twi.gatcha.gui.Gui;

public final class Main {
    public static void main(final String[] args) {
        Platform.startup(() -> {});
        Gui.main(args);
    }
}
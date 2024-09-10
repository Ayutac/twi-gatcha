package org.abos.twi.gatcha.gui.component.pane;

import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PartyScreen extends AbstractScreen {

    private @Nullable PartyGalleryPane galleryPane;

    private @Nullable Player player;

    public PartyScreen(@NotNull Gui gui) {
        super(gui);
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(@Nullable Player player) {
        this.player = player;
        if (this.player != null) {
            galleryPane = new PartyGalleryPane(this, player, 125);
            setCenter(galleryPane);
        }
        else {
            galleryPane = null;
            setCenter(null);
        }
    }
}

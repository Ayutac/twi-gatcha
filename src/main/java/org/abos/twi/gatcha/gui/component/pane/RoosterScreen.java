package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RoosterScreen extends AbstractScreen {

    private @Nullable RoosterGalleryPane galleryPane;
    private @Nullable AbstractScreen caller;

    private @Nullable Player player;

    public RoosterScreen(final @NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                if (caller instanceof PartyScreen) {
                    this.gui.showPartyScreen(false);
                }
                else {
                    this.gui.showHomeScreen();
                }
            }
        });
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(final @Nullable Player player) {
        this.player = player;
        if (this.player != null) {
            galleryPane = new RoosterGalleryPane(this, player, 125);
            final HBox box = new HBox(galleryPane);
            box.setAlignment(Pos.CENTER);
            setCenter(new ScrollPane(box));
        }
        else {
            galleryPane = null;
            setCenter(null);
        }
    }

    public @Nullable AbstractScreen getCaller() {
        return caller;
    }

    public void setCaller(@Nullable AbstractScreen caller) {
        this.caller = caller;
    }

    public void updateGallery() {
        if (galleryPane != null) {
            galleryPane.updateGallery();
        }
    }
}

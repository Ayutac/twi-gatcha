package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.Gui;
import org.abos.twi.gatcha.gui.component.LabelledCharacterView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PartyScreen extends AbstractScreen {

    private @Nullable PartyGalleryPane galleryPane;

    private @Nullable Player player;

    public PartyScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showHomeScreen();
            }
        });
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

    public void select(final @Nullable CharacterModified character) {
        if (galleryPane != null && galleryPane.getSelectedIndex() != null) {
            final LabelledCharacterView view = galleryPane.getViews().get(galleryPane.getSelectedIndex());
            view.setCharacter(character == view.getCharacter() ? null : character);
        }
    }
}

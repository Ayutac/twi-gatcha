package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.component.LabelledCharacterView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PartyGalleryPane extends GridPane {

    protected final @NotNull PartyScreen screen;
    protected final @NotNull List<LabelledCharacterView> views = new LinkedList<>();

    protected final @NotNull Player player;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int radius;
    protected @Nullable Integer selectedIndex;

    public PartyGalleryPane(final @NotNull PartyScreen screen, final @NotNull Player player, final @Range(from = 1, to = Integer.MAX_VALUE) int radius) {
        this.screen = Objects.requireNonNull(screen);
        this.player = Objects.requireNonNull(player);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
        Party party = Party.EMPTY;
        if (player.getNumberOfParties() != 0) {
            party = player.getParty(0);
        }
        final LabelledCharacterView[] localViews = new LabelledCharacterView[Player.PARTY_MAX_SIZE];
        for (int i = 0; i < localViews.length; i++) {
            localViews[i] = createView(party.characters(), i);
        }
        add(localViews[0], 0, 0, 2, 1);
        add(localViews[1], 2, 0, 2, 1);
        add(localViews[2], 4, 0, 2, 1);
        add(localViews[3], 1, 1, 2, 1);
        add(localViews[4], 3, 1, 2, 1);
        final int columns = 6;
        for (int i = 0; i < columns; i++) {
            final ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth(100d / columns);
            getColumnConstraints().add(constraints);
        }
    }

    private @NotNull LabelledCharacterView createView(@NotNull List<CharacterModified> characters, final @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        final LabelledCharacterView view = new LabelledCharacterView(null, true, 2 * radius);
        if (characters.size() > index) {
            view.setCharacter(characters.get(index));
        }
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            selectedIndex = index;
            screen.getGui().showRoosterScreen(screen);
        });
        views.add(view);
        return view;
    }

    public @NotNull List<LabelledCharacterView> getViews() {
        return views;
    }

    public @Nullable Integer getSelectedIndex() {
        return selectedIndex;
    }
}

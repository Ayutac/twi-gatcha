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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartyGalleryPane extends GridPane {

    protected final @NotNull PartyScreen screen;
    protected final @NotNull List<LabelledCharacterView> views = new ArrayList<>();

    protected final @NotNull Player player;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int radius;
    protected @Range(from = -1, to = Integer.MAX_VALUE) int selectedPartyIndex = -1;
    protected @Nullable Integer selectedCharIndex;

    public PartyGalleryPane(final @NotNull PartyScreen screen, final @NotNull Player player, final @Range(from = 1, to = Integer.MAX_VALUE) int radius) {
        this.screen = Objects.requireNonNull(screen);
        this.player = Objects.requireNonNull(player);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
        final Party party = getDefaultParty();
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

    private Party getDefaultParty() {
        Party party = Party.EMPTY;
        if (player.getNumberOfParties() != 0) {
            selectedPartyIndex = 0;
            party = player.getParty(selectedPartyIndex);
        }
        return party;
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
            selectedCharIndex = index;
            screen.getGui().showRoosterScreen(screen);
        });
        views.add(view);
        return view;
    }

    public @NotNull List<LabelledCharacterView> getViews() {
        return views;
    }

    @Range(from = -1, to = Integer.MAX_VALUE)
    public int getSelectedPartyIndex() {
        return selectedPartyIndex;
    }

    public @Nullable Integer getSelectedCharIndex() {
        return selectedCharIndex;
    }

    public void update() {
        final Party party = getDefaultParty();
        for (int index = 0; index < Player.PARTY_MAX_SIZE; index++) {
            if (party.characters().size() > index) {
                views.get(index).setCharacter(party.characters().get(index));
            }
            else {
                views.get(index).setCharacter(null);
            }
        }
    }

}

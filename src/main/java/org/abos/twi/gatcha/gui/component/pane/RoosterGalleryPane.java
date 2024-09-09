package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.layout.GridPane;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.component.LabelledCharacterView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RoosterGalleryPane extends GridPane {

    /**
     * How many columns there are in the gallery.
     */
    public static final int COLUMNS = 4;

    protected final @NotNull RoosterScreen screen;
    protected final @NotNull List<LabelledCharacterView> views = new LinkedList<>();

    protected final @NotNull Player player;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int radius;

    public RoosterGalleryPane(final @NotNull RoosterScreen screen, final @NotNull Player player, final @Range(from = 1, to = Integer.MAX_VALUE) int radius) {
        super(20d, 20d);
        this.screen = Objects.requireNonNull(screen);
        this.player = Objects.requireNonNull(player);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
        int count = 0;
        for (final CharacterModified character : player.getCharacters()) {
            final LabelledCharacterView view = new LabelledCharacterView(character, true, 250d);
            views.add(view);
            add(view, count % COLUMNS, count / COLUMNS);
            count++;
        }
    }

}

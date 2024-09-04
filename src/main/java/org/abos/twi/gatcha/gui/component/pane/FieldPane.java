package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.abos.common.Vec2d;
import org.abos.twi.gatcha.core.battle.Field;
import org.abos.twi.gatcha.gui.shape.Hexagon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FieldPane extends Pane {

    protected List<Hexagon> hexagons = new LinkedList<>();

    protected final @NotNull Field field;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int radius;
    protected int offsetX;
    protected int offsetY;

    public FieldPane(final @NotNull Field field, final @Range(from = 1, to = Integer.MAX_VALUE) int radius, int offsetX, int offsetY) {
        this.field = Objects.requireNonNull(field);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        addHexagons();
        addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            final Optional<Hexagon> hexagon = findHexagonAt(mouseEvent.getX(), mouseEvent.getY());
            if (hexagon.isPresent()) {
                for (final Hexagon other : hexagons) {
                    if (hexagon.get() == other) {
                        continue;
                    }
                    other.setFill(Color.TRANSPARENT);
                }
                hexagon.get().setFill(Color.BEIGE);
            }
        });
    }

    public void addHexagons() {
        final double yOffset = radius * (0.5 + Hexagon.RADII_FACTOR); // the amount we go down with the next row
        var children = getChildren();
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                final Hexagon hexagon = new Hexagon(radius, new Vec2d(
                        offsetX + radius + 2 * radius * x + (y % 2 == 0 ? 0 : radius),
                        offsetY + radius + yOffset * y));
                hexagons.add(hexagon);
                children.add(hexagon);
            }
        }
    }

    protected Optional<Hexagon> findHexagonAt(double x, double y) {
        for (final Hexagon hexagon : hexagons) {
            if (hexagon.intersects(x, y, 1, 1)) {
                return Optional.of(hexagon);
            }
        }
        return Optional.empty();
    }

}

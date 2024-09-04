package org.abos.twi.gatcha.gui.shape;

import javafx.scene.shape.Polyline;
import org.abos.common.Vec2d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public class Hexagon extends Polyline {

    public static double RADII_FACTOR = Math.sqrt(3)/2;

    protected final @Range(from = 0, to = Integer.MAX_VALUE) int radius;
    protected final @NotNull Vec2d center;

    public Hexagon(final @Range(from = 0, to = Integer.MAX_VALUE) int radius, final @NotNull Vec2d center) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative!");
        }
        this.radius = radius;
        this.center = Objects.requireNonNull(center);
        final Double[] points = new Double[] {
                center.x(), center.y() - RADII_FACTOR * radius,
                center.x() + radius, center.y() - radius / 2d,
                center.x() + radius, center.y() + radius / 2d,
                center.x(), center.y() + RADII_FACTOR * radius,
                center.x() - radius, center.y() + radius / 2d,
                center.x() - radius, center.y() - radius / 2d,
                center.x(), center.y() - RADII_FACTOR * radius
        };
        getPoints().addAll(points);
    }

}

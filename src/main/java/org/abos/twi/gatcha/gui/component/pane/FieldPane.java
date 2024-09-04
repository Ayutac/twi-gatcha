package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.layout.Pane;
import org.abos.common.Vec2d;
import org.abos.twi.gatcha.gui.shape.Hexagon;

public class FieldPane extends Pane {

    public FieldPane() {
        getChildren().add(new Hexagon(10, new Vec2d(15, 15)));
        getChildren().add(new Hexagon(10, new Vec2d(35, 15)));
        getChildren().add(new Hexagon(10, new Vec2d(55, 15)));
        getChildren().add(new Hexagon(10, new Vec2d(75, 15)));
        final double yOffset = 5 + 10 * Hexagon.RADII_FACTOR;
        getChildren().add(new Hexagon(10, new Vec2d(25, 15 + yOffset)));
        getChildren().add(new Hexagon(10, new Vec2d(45, 15 + yOffset)));
        getChildren().add(new Hexagon(10, new Vec2d(65, 15 + yOffset)));
        getChildren().add(new Hexagon(10, new Vec2d(85, 15 + yOffset)));

    }

}

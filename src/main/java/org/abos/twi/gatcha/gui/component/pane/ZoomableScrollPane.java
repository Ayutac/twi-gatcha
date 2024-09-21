package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * This is a scalable, pannable JavaFX {@link ScrollPane}.
 */
// code mostly from https://stackoverflow.com/a/44314455/9396257
public class ZoomableScrollPane extends ScrollPane {
    protected double scaleValue = 0.7;
    protected double zoomIntensity = 0.01;
    protected Node target;
    protected Node zoomNode;

    public ZoomableScrollPane(final @NotNull Node target) {
        this.target = Objects.requireNonNull(target);
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true); //center
        setFitToWidth(true); //center

        updateScale();
    }

    private Node outerNode(final @NotNull Node node) {
        final Node outerNode = centeredNode(node);
        outerNode.setOnScroll(scrollEvent -> {
            if (scrollEvent.isControlDown()) {
                scrollEvent.consume();
                onScroll(scrollEvent.getDeltaY(), new Point2D(scrollEvent.getX(), scrollEvent.getY()));
            }
        });
        return outerNode;
    }

    private Node centeredNode(final @NotNull Node node) {
        VBox vBox = new VBox(Objects.requireNonNull(node));
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }

    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }
}
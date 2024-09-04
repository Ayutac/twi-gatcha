package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.abos.common.Vec2d;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.BattlePhase;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.gui.shape.Hexagon;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class BattlefieldPane extends Pane {

    protected final @NotNull BidiMap<Vec2i, Hexagon> hexagons = new DualHashBidiMap<>();
    protected final @NotNull BattleScreen screen;

    protected final @NotNull Battle battle;
    protected @Range(from = 1, to = Integer.MAX_VALUE) int radius;

    public BattlefieldPane(final @NotNull BattleScreen screen, final @NotNull Battle battle, final @Range(from = 1, to = Integer.MAX_VALUE) int radius) {
        this.screen = Objects.requireNonNull(screen);
        this.battle = Objects.requireNonNull(battle);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
        final double width = 2 * radius * this.battle.getWidth() + (this.battle.getHeight() > 1 ? radius : 0);
        final double height = (this.battle.getHeight() - 1) * radius * (0.5 + Hexagon.RADII_FACTOR) + 2 * radius;
        addHexagons();
        addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> updateGrid(mouseEvent.getX(), mouseEvent.getY()));
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            final Optional<Hexagon> hexagon = findHexagonAt(mouseEvent.getX(), mouseEvent.getY());
            if (hexagon.isPresent()) {
                final Vec2i position = hexagons.getKey(hexagon.get());
                if (battle.getPhase() == BattlePhase.PLACEMENT) {
                    if (battle.isPlayerSpawnAt(position) && !battle.getPlacementParty().isEmpty()) {
                        battle.placePlayerCharacterAt(battle.getPlacementParty().poll(), position);
                    }
                    if (battle.getPlacementParty().isEmpty()) {
                        battle.start();
                        screen.update();
                    }
                }
                updateGrid(mouseEvent.getX(), mouseEvent.getY());
            }
        });
        updateGrid(0d, 0d);
    }

    public void addHexagons() {
        final double yOffset = radius * (0.5 + Hexagon.RADII_FACTOR); // the amount we go down with the next row
        var children = getChildren();
        for (int y = 0; y < battle.getHeight(); y++) {
            for (int x = 0; x < battle.getWidth(); x++) {
                final Hexagon hexagon = new Hexagon(radius, new Vec2d(
                        radius + 2 * radius * x + (y % 2 == 0 ? 0 : radius),
                        radius + yOffset * y));
                hexagons.put(new Vec2i(x, y), hexagon);
                children.add(hexagon);
            }
        }
    }

    protected void updateGrid(double mouseX, double mouseY) {
        final Optional<Hexagon> hexagon = findHexagonAt(mouseX, mouseY);
        for (final Map.Entry<Vec2i, Hexagon> other : hexagons.entrySet()) {
            if (hexagon.isPresent() && hexagon.get() == other.getValue()) {
                continue;
            }
            final Optional<CharacterInBattle> character = battle.getCharacterAt(other.getKey());
            if (character.isEmpty()) {
                if (battle.getPhase() == BattlePhase.PLACEMENT && battle.isPlayerSpawnAt(other.getKey())) {
                    other.getValue().setFill(Color.AQUA);
                }
                else {
                    other.getValue().setFill(Color.TRANSPARENT);
                }
            }
            else {
                switch (character.get().getTeam()) {
                    case ENEMY -> other.getValue().setFill(Color.RED);
                    case PLAYER -> other.getValue().setFill(Color.BLUE);
                    default -> throw new IllegalStateException("Unhandled enum encountered!");
                }
            }
        }
        if (hexagon.isPresent()) {
            hexagon.get().setFill(Color.ORANGE);
        }
    }

    protected Optional<Hexagon> findHexagonAt(double x, double y) {
        for (final Hexagon hexagon : hexagons.values()) {
            if (hexagon.intersects(x, y, 1, 1)) {
                return Optional.of(hexagon);
            }
        }
        return Optional.empty();
    }

}

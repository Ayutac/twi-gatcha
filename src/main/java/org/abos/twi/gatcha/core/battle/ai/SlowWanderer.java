package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.CollectionUtil;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.battle.WaveUnit;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.graph.DefaultEdge;

import java.util.LinkedList;
import java.util.List;

/**
 * An {@link AiCharacter} that always moves one tile if possible.
 */
public class SlowWanderer extends AiCharacter {
    public SlowWanderer(final @NotNull CharacterModified modified, final @NotNull Battle battle, final @NotNull TeamKind team, final @NotNull Vec2i position) {
        super(modified, battle, team, position);
    }

    public SlowWanderer(final @NotNull WaveUnit unit, final @NotNull Battle battle) {
        this(unit.character(), battle, unit.team(), unit.startPos());
    }

    @Override
    public void turn() {
        // move one tile
        final var movementGraph = battle.getCharacterMovementGraph(this);
        final var oldPosition = position;
        final List<DefaultEdge> directions = new LinkedList<>(movementGraph.outgoingEdgesOf(oldPosition));
        directions.removeIf(defaultEdge -> movementGraph.getEdgeWeight(defaultEdge) > getMovement());
        if (!directions.isEmpty()) {
            final DefaultEdge e = CollectionUtil.getRandomEntry(directions, random);
            setMoved((int)Math.round(movementGraph.getEdgeWeight(e)));
            setPosition(movementGraph.getEdgeTarget(e));
            if (battle.getUi() != null) {
                battle.getUi().characterMoved(this, oldPosition, position);
            }
        }
    }
}

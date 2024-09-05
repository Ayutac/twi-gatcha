package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.CollectionUtil;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.graph.DefaultEdge;

import java.util.LinkedList;
import java.util.List;

/**
 * An {@link AiCharacter} that always moves one tile if possible.
 */
public class SlowWanderer extends AiCharacter {
    public SlowWanderer(@NotNull CharacterModified modified, @NotNull Battle battle, @NotNull TeamKind team, @NotNull Vec2i position) {
        super(modified, battle, team, position);
    }

    @Override
    public void turn() {
        // move one tile
        final var movementGraph = battle.getCharacterMovementGraph(this);
        final List<DefaultEdge> directions = new LinkedList<>(movementGraph.outgoingEdgesOf(position));
        directions.removeIf(defaultEdge -> movementGraph.getEdgeWeight(defaultEdge) > getMoved());
        if (!directions.isEmpty()) {
            final DefaultEdge e = CollectionUtil.getRandomEntry(directions, random);
            setMoved(getMoved() + (int)Math.round(movementGraph.getEdgeWeight(e)));
            setPosition(movementGraph.getEdgeTarget(e));
        }
    }
}

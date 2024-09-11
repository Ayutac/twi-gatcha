package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.CollectionUtil;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.battle.WaveUnit;
import org.abos.twi.gatcha.core.effect.AttackEffect;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DirectRandomAttacker extends AiCharacter {
    public DirectRandomAttacker(final @NotNull CharacterModified modified, final @NotNull Battle battle, final @NotNull TeamKind team, final @NotNull Vec2i position) {
        super(modified, battle, team, position);
    }

    public DirectRandomAttacker(final @NotNull WaveUnit unit, final @NotNull Battle battle) {
        this(unit.character(), battle, unit.team(), unit.startPos());
    }

    @Override
    public void turn() {
        // get all visible enemies on the map
        final List<Vec2i> enemyPositions = battle.getCharacterOrder().stream()
                .filter(character -> character.getTeam() != team && !character.isInvisible())
                .map(CharacterInBattle::getPosition)
                .toList();
        if (enemyPositions.isEmpty()) {
            return;
        }
        // target one of them
        final Vec2i target = CollectionUtil.getRandomEntry(enemyPositions, random);
        final List<Vec2i> neighborTiles = battle.getTerrainGraph().incomingEdgesOf(target).stream()
                .map(e -> battle.getTerrainGraph().getEdgeSource(e))
                .filter(position -> !battle.isCharacterAt(position))
                .toList();
        if (neighborTiles.isEmpty()) {
            return;
        }
        // move there
        final Vec2i movementTarget = CollectionUtil.getRandomEntry(neighborTiles, random);
        final var movementGraph = battle.getCharacterMovementGraph(this);
        final var graphPath = BellmanFordShortestPath.findPathBetween(movementGraph, position, movementTarget);
        if (graphPath.getWeight() != Double.POSITIVE_INFINITY) {
            final List<DefaultEdge> pathEdges = graphPath.getEdgeList();
            double pathWeight = 0d;
            int count = 0;
            // we go as far as possible
            for (final DefaultEdge e : pathEdges) {
                if (pathWeight + movementGraph.getEdgeWeight(e) <= getMovement()) {
                    pathWeight += movementGraph.getEdgeWeight(e);
                    count++;
                } else {
                    break;
                }
            }
            // even one step could be too much
            if (count != 0) {
                setMoved((int)Math.round(pathWeight));
                setPosition(movementGraph.getEdgeTarget(pathEdges.get(count-1)));
            }
        }
        // attack something in range
        final Map<Attack, List<Vec2i>> possibleTargets = new HashMap<>();
        // … with normal attack
        battle.setSelectedAttack(modified.getBase().attacks().normal());
        if (!Collections.disjoint(battle.getPossibleAttackFields(), enemyPositions)) {
            final List<Vec2i> targets = new LinkedList<>();
            for (final Vec2i enemyPos : enemyPositions) {
                if (battle.getPossibleAttackFields().contains(enemyPos)) {
                    targets.add(enemyPos);
                }
            }
            possibleTargets.put(modified.getBase().attacks().normal(), targets);
        }
        // … with special 1 attack
        battle.setSelectedAttack(modified.getBase().attacks().special1());
        if (!Collections.disjoint(battle.getPossibleAttackFields(), enemyPositions)) {
            final List<Vec2i> targets = new LinkedList<>();
            for (final Vec2i enemyPos : enemyPositions) {
                if (battle.getPossibleAttackFields().contains(enemyPos)) {
                    targets.add(enemyPos);
                }
            }
            possibleTargets.put(modified.getBase().attacks().special1(), targets);
        }
        // … with special 2 attack
        battle.setSelectedAttack(modified.getBase().attacks().special2());
        if (!Collections.disjoint(battle.getPossibleAttackFields(), enemyPositions)) {
            final List<Vec2i> targets = new LinkedList<>();
            for (final Vec2i enemyPos : enemyPositions) {
                if (battle.getPossibleAttackFields().contains(enemyPos)) {
                    targets.add(enemyPos);
                }
            }
            possibleTargets.put(modified.getBase().attacks().special2(), targets);
        }
        // if no targets, no attack
        if (possibleTargets.isEmpty()) {
            return;
        }
        final Attack chosenAttack = CollectionUtil.getRandomEntry(possibleTargets.keySet(), random);
        final Vec2i chosenTarget = CollectionUtil.getRandomEntry(possibleTargets.get(chosenAttack), random);
        for (final AttackEffect effect : chosenAttack.effects()) {
            effect.apply(battle.getCurrentCharacter(), chosenTarget, battle);
        }
    }
}

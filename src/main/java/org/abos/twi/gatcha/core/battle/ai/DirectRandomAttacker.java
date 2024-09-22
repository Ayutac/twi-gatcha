package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.CollectionUtil;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.battle.WaveUnit;
import org.abos.twi.gatcha.core.battle.graph.GraphUtil;
import org.abos.twi.gatcha.core.effect.ApplicableEffect;
import org.jetbrains.annotations.NotNull;

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
                .filter(character -> team.attacks(character.getTeam()) && !character.isInvisible())
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
        final Vec2i oldPosition = position;
        final Vec2i movementTarget = CollectionUtil.getRandomEntry(neighborTiles, random);
        final var movementGraph = battle.getCharacterMovementGraph(this);
        final var nextClosest = GraphUtil.getNextClosest(movementGraph, position, movementTarget, getMovement());
        if (nextClosest.isPresent()) {
            setMoved((int) Math.round(nextClosest.get().weight()));
            setPosition(nextClosest.get().vertex());
        }
        if (battle.getUi() != null && !oldPosition.equals(position)) {
            battle.getUi().characterMoved(this, oldPosition, position);
        }
        // attack something in range
        final Map<Attack, List<Vec2i>> possibleTargets = new HashMap<>();
        // … with normal attack
        if (cooldownNormal == 0) { // should always be the case
            savePossibleTargetsForAttack(modified.getBase().attacks().normal(), enemyPositions, possibleTargets);
        }
        // … with special 1 attack
        if (cooldownSpecial1 == 0) {
            savePossibleTargetsForAttack(modified.getBase().attacks().special1(), enemyPositions, possibleTargets);
        }
        // … with special 2 attack
        if (cooldownSpecial2 == 0) {
            savePossibleTargetsForAttack(modified.getBase().attacks().special2(), enemyPositions, possibleTargets);
        }
        // if no targets, no attack
        if (possibleTargets.isEmpty()) {
            battle.setSelectedAttack(null);
            return;
        }
        final Attack chosenAttack = CollectionUtil.getRandomEntry(possibleTargets.keySet(), random);
        battle.setSelectedAttack(chosenAttack);
        final Vec2i chosenTarget = CollectionUtil.getRandomEntry(possibleTargets.get(chosenAttack), random);
        for (final ApplicableEffect effect : chosenAttack.effects()) {
            effect.apply(battle.getCurrentCharacter(), chosenTarget, battle);
        }
    }
}

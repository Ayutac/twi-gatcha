package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.Group;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.data.Lookups;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jgrapht.alg.shortestpath.BFSShortestPath;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApplicableEffect extends Effect {

    protected final @Range(from = 0, to = Integer.MAX_VALUE) int characterAoeRadius;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int terrainAoeRadius;
    protected final double applicableChance;
    protected final @Nullable String applicableGroupId;
    private @Nullable Optional<Group> applicableGroup;
    protected final @NotNull List<TeamKind> applicableTeam;

    public ApplicableEffect(final @NotNull EffectType effectType,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int maxPower,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int characterAoeRadius,
                            final @Range(from = 0, to = Integer.MAX_VALUE) int terrainAoeRadius,
                            final double applicableChance,
                            final @Nullable String affectedGroupId,
                            final @Nullable String applicableGroupId,
                            final @Nullable List<TeamKind> applicableTeam) {
        super(effectType, maxPower, maxDuration, affectedGroupId);
        if (characterAoeRadius < 0 || terrainAoeRadius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative!");
        }
        this.characterAoeRadius = characterAoeRadius;
        this.terrainAoeRadius = terrainAoeRadius;
        if (applicableChance < 0d || applicableChance > 1d) {
            throw new IllegalArgumentException("Applicable Chance must be a probability!");
        }
        this.applicableChance = applicableChance;
        this.applicableGroupId = applicableGroupId;
        this.applicableTeam = Objects.requireNonNullElseGet(applicableTeam, () -> Arrays.asList(TeamKind.values()));
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getCharacterAoeRadius() {
        return characterAoeRadius;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getTerrainAoeRadius() {
        return terrainAoeRadius;
    }

    public Optional<Group> getApplicableGroup() {
        if (applicableGroup == null) {
            if (applicableGroupId == null) {
                applicableGroup = Optional.empty();
            }
            else {
                applicableGroup = Optional.of(Lookups.GROUPS.get(applicableGroupId));
            }
        }
        return applicableGroup;
    }

    protected List<CharacterInBattle> getApplicableTargets(final CharacterInBattle from, final @NotNull Vec2i center, final @NotNull Battle battle) {
        final Optional<Group> applicableGroup = getApplicableGroup();
        // if no AoE effect, just return character at target position
        if (characterAoeRadius == 0) {
            final Optional<CharacterInBattle> character = battle.getCharacterAt(center);
            if (character.isPresent() && (applicableGroup.isEmpty() || applicableGroup.get().characters().contains(character.get().getModified().getBase()))
                    && applicableTeam.contains(character.get().getTeam())) {
                // calculate miss chance
                int lowerAcc = 0;
                for (final PersistentEffect effect : from.getPersistentEffects()) {
                    if (effect.getEffectType() == EffectType.LOWER_ACCURANCY) {
                        lowerAcc -= effect.maxPower;
                    }
                }
                // if hit, return the character
                if (Math.max(0, 100-lowerAcc) / 100d > battle.getRandom().nextDouble()) {
                    return List.of(character.get());
                } // else we miss our single target
            }
            return List.of();
        }
        // else calculate all targets via BFS
        final var graph = battle.getTerrainGraph();
        final var paths = new BFSShortestPath<>(graph).getPaths(center);
        final List<CharacterInBattle> aoeTargets = new LinkedList<>();
        for (final Vec2i position : graph.vertexSet()) {
            double distance = paths.getPath(position).getWeight();
            if (distance <= characterAoeRadius && battle.isCharacterAt(position)) {
                aoeTargets.add(battle.getCharacterAt(position).get());
            }
        }
        // filter through applicable targets if needed
        return aoeTargets.stream()
                .filter(character -> applicableTeam.contains(character.getTeam()))
                .filter(character -> applicableGroup.isEmpty() || applicableGroup.get().characters().contains(character.getModified().getBase()))
                .toList();
    }

    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        List<CharacterInBattle> aoeTargets = getApplicableTargets(from, target, battle);
        for (final CharacterInBattle aoeTarget : aoeTargets) {
            // applicable chance is tested for each target
            if (applicableChance < battle.getRandom().nextDouble()) {
                continue;
            }
            int dmg = 0;
            switch (effectType) {
                case DAMAGE_BLUNT, DAMAGE_SLASH -> {
                    dmg = Math.max(1, from.getAttack(aoeTarget) - aoeTarget.getDefense(from) + maxPower);
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_PIERCE -> {
                    dmg = Math.max(1, from.getAttack(aoeTarget) - aoeTarget.getDefense(from) + maxPower) * 2;
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_DEATH -> {
                    int resistance = 0;
                    for (final PersistentEffect effect : aoeTarget.getPersistentEffects()) {
                        if (effect.getEffectType() == EffectType.RESIST_DEATH) {
                            resistance += effect.getMaxPower();
                        }
                    }
                    dmg = (int)Math.round(maxPower * (1 - Math.min(100, resistance) / 100d));
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_FROST -> {
                    dmg = Math.max(1, (from.getAttack(aoeTarget) + maxPower) / 3);
                    aoeTarget.takeDamage(dmg);
                }
                case HEALING -> {
                    final int heal = from.getAttack(aoeTarget) + maxPower;
                    aoeTarget.heal(heal);
                    dmg = heal;
                }
                case INVISIBILITY, INVULNERABILITY, TURN_FRIENDLY, BUFF_ATTACK, BUFF_DEFENSE, BUFF_SPEED, DEBUFF_SPEED, RESIST_DEATH, LOWER_ACCURANCY -> {
                    aoeTarget.getPersistentEffects().add(new PersistentEffect(effectType, maxPower, maxDuration, affectedGroupId));
                }
                case BUFF_HEALTH -> {
                    aoeTarget.getPersistentEffects().add(new DeterioratingEffect(effectType, maxPower, maxDuration, affectedGroupId));
                }
                case SUMMON -> throw new IllegalStateException(String.format("Use %s for summon effects!", SummonEffect.class.getSimpleName()));
                default -> throw new AssertionError("An unknown effect type has been associated with this " + ApplicableEffect.class.getSimpleName() + "!"); // shouldn't happen
            }
            if (battle.getUi() != null) {
                battle.getUi().characterAttacked(from, aoeTarget, effectType, dmg);
            }
        }
    }


}

package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jgrapht.alg.shortestpath.BFSShortestPath;

import java.util.LinkedList;
import java.util.List;

/**
 * For attacks that lead to a duration effect that also has power.
 */
public class AoeAttackEffect extends SimpleAttackEffect {

    protected final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMin;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMax;

    public AoeAttackEffect(final @NotNull EffectType type,
                           final @Range(from = 0, to = Integer.MAX_VALUE) int power,
                           final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMin,
                           final @Range(from = 0, to = Integer.MAX_VALUE) int rangeMax) {
        super(type, power);
        if (rangeMin < 0 || rangeMax < 0) {
            throw new IllegalArgumentException("Range cannot be negative!");
        }
        if (rangeMin > rangeMax) {
            throw new IllegalArgumentException("Min range cannot be greater than max range!");
        }
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
    }

    public List<CharacterInBattle> getAoeTargets(final @NotNull Vec2i target, final @NotNull Battle battle) {
        final var graph = battle.getTerrainGraph();
        final var paths = new BFSShortestPath<>(graph).getPaths(target);
        final List<CharacterInBattle> aoeTargets = new LinkedList<>();
        for (final Vec2i position : graph.vertexSet()) {
            double distance = paths.getPath(position).getWeight();
            if (rangeMin <= distance && distance <= rangeMax && battle.isCharacterAt(position)) {
                aoeTargets.add(battle.getCharacterAt(position).get());
            }
        }
        return aoeTargets;
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        List<CharacterInBattle> aoeTargets = getAoeTargets(target, battle);
        // changes here should be reflected in SimpleAttackEffect
        for (final CharacterInBattle aoeTarget : aoeTargets) {
            int dmg = 0;
            switch (type) {
                case DAMAGE_BLUNT, DAMAGE_SLASH -> {
                    dmg = Math.max(1, from.getAttack() - aoeTarget.getDefense() + power);
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_PIERCE -> {
                    dmg = Math.max(1, from.getAttack() - aoeTarget.getDefense() + power) * 2;
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_DEATH -> {
                    int resistance = 0;
                    for (final Effect effect : aoeTarget.getActiveEffects()) {
                        if (effect instanceof DurationEffect resEffect && effect.getEffectType() == EffectType.RESIST_DEATH) {
                            resistance += resEffect.getPower();
                        }
                    }
                    dmg = (int)Math.round(power * (1 - Math.min(100, resistance) / 100d));
                    aoeTarget.takeDamage(dmg);
                }
                case DAMAGE_FROST -> {
                    dmg = Math.max(1, (from.getAttack() + power) / 3);
                    aoeTarget.takeDamage(dmg);
                }
                case HEALING -> {
                    final int heal = from.getAttack() + power;
                    aoeTarget.heal(heal);
                    dmg = heal;
                }
                case INVISIBILITY -> {
                    aoeTarget.getActiveEffects().add(new SimpleDurationEffect(EffectType.INVISIBILITY, power));
                }
                case INVULNERABILITY -> {
                    aoeTarget.getActiveEffects().add(new SimpleDurationEffect(EffectType.INVULNERABILITY, power));
                }
                default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + AoeAttackEffect.class.getSimpleName() + "!");
            }
            if (battle.getUi() != null) {
                battle.getUi().characterAttacked(from, aoeTarget, type, dmg);
            }
        }
    }
}

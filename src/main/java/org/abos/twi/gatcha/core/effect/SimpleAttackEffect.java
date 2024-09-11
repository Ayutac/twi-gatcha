package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

public class SimpleAttackEffect implements AttackEffect {

    protected final @NotNull EffectType type;
    protected final @Range(from = 0, to = Integer.MAX_VALUE) int power;

    public SimpleAttackEffect(final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int power) {
        this.type = Objects.requireNonNull(type);
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative!");
        }
        this.power = power;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return type;
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        final Optional<CharacterInBattle> to = battle.getCharacterAt(target);
        if (to.isEmpty()) {
            return;
        }
        int dmg = 0;
        // changes here should be reflected in AoeAttackEffect
        switch (type) {
            case DAMAGE_BLUNT, DAMAGE_SLASH -> {
                dmg = Math.max(1, from.getAttack() - to.get().getDefense() + power);
                to.get().takeDamage(dmg);
            }
            case DAMAGE_PIERCE -> {
                dmg = Math.max(1, from.getAttack() - to.get().getDefense() + power) * 2;
                to.get().takeDamage(dmg);
            }
            case DAMAGE_IGNORES_ARMOR -> {
                dmg = from.getAttack() + power;
                to.get().takeDamage(dmg);
            }
            case DAMAGE_FROST -> {
                dmg = Math.max(1, (from.getAttack() + power) / 3);
                to.get().takeDamage(dmg);
            }
            case HEALING -> {
                final int heal = from.getAttack() + power;
                to.get().heal(heal);
                dmg = heal;
            }
            case INVISIBILITY -> {
                to.get().getActiveEffects().add(new SimpleDurationEffect(EffectType.INVISIBILITY, power));
            }
            case INVULNERABILITY -> {
                to.get().getActiveEffects().add(new SimpleDurationEffect(EffectType.INVULNERABILITY, power));
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + SimpleAttackEffect.class.getSimpleName() + "!");
        }
        if (battle.getUi() != null) {
            battle.getUi().characterAttacked(from, to.get(), type, dmg);
        }
    }
}

package org.abos.twi.gatcha.core.battle;

import org.abos.common.Describable;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Group;
import org.abos.twi.gatcha.core.effect.DeterioratingEffect;
import org.abos.twi.gatcha.core.effect.Effect;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.abos.twi.gatcha.core.effect.PersistentEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CharacterInBattle implements Describable {

    protected final @NotNull CharacterModified modified;
    protected final @NotNull Battle battle;
    protected final @NotNull List<PersistentEffect> persistentEffects = new LinkedList<>();

    protected @NotNull TeamKind team;
    protected @NotNull Vec2i position;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int health;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int moved;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int cooldownNormal;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int cooldownSpecial1;
    protected @Range(from = 0, to = Integer.MAX_VALUE) int cooldownSpecial2;

    public CharacterInBattle(final @NotNull CharacterModified modified, final @NotNull Battle battle, final @NotNull TeamKind team, final @NotNull Vec2i position) {
        this.modified = Objects.requireNonNull(modified);
        this.battle = Objects.requireNonNull(battle);
        this.team = Objects.requireNonNull(team);
        this.position = Objects.requireNonNull(position);
        this.health = getMaxHealth();
        persistentEffects.addAll(this.modified.getBase().effects());
    }

    @Override
    public @NotNull String getName() {
        return modified.getName();
    }

    @Override
    public @NotNull String getDescription() {
        return modified.getDescription();
    }

    public @NotNull CharacterModified getModified() {
        return modified;
    }

    public @NotNull TeamKind getTeam() {
        int count = 0;
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.TURN_FRIENDLY) {
                count++;
            }
        }
        if (count % 2 == 1) {
            if (team == TeamKind.ENEMY) {
                return TeamKind.ALLY;
            }
            else {
                return TeamKind.ENEMY;
            }
        }
        return team;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getHealth() {
        return health;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMaxHealth() {
        return modified.getMaxHealth();
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getSpeed() {
        int speed = modified.getSpeed();
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.DEBUFF_SPEED) {
                speed -= effect.getMaxPower();
            }
        }
        return Math.max(0, speed);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getAttack(final @NotNull CharacterInBattle against) {
        int attack = modified.getAttack();
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.BUFF_ATTACK) {
                final Optional<Group> affectedGroup = effect.getAffectedGroup();
                if (affectedGroup.isEmpty() || affectedGroup.get().characters().contains(against.getModified().getBase())) {
                    attack += effect.getMaxPower();
                }
            }
        }
        return attack;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getDefense(final @NotNull CharacterInBattle against) {
        int defense = modified.getDefense();
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.BUFF_DEFENSE) {
                final Optional<Group> affectedGroup = effect.getAffectedGroup();
                if (affectedGroup.isEmpty() || affectedGroup.get().characters().contains(against.getModified().getBase())) {
                    defense += effect.getMaxPower();
                }
            }
        }
        return defense;
    }

    public double getInitiative() {
        return getSpeed() / 4d;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMovement() {
        return (int)Math.round(getInitiative());
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMoved() {
        return moved;
    }

    public void setMoved(final @Range(from = 0, to = Integer.MAX_VALUE) int moved) {
        if (moved < 0) {
            throw new IllegalArgumentException("Moved cannot be negative!");
        }
        this.moved = moved;
    }

    public @NotNull Vec2i getPosition() {
        return position;
    }

    public void setPosition(final @NotNull Vec2i position) {
        this.position = Objects.requireNonNull(position);
    }

    public @NotNull List<PersistentEffect> getPersistentEffects() {
        return persistentEffects;
    }

    public boolean isAt(final Vec2i position) {
        return position.equals(this.position);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getCooldownNormal() {
        return cooldownNormal;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getCooldownSpecial1() {
        return cooldownSpecial1;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getCooldownSpecial2() {
        return cooldownSpecial2;
    }

    public void takeDamage(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        Optional<PersistentEffect> invulnerability = persistentEffects.stream().filter(effect -> effect.getEffectType() == EffectType.INVULNERABILITY).findFirst();
        if (invulnerability.isPresent()) {
            persistentEffects.remove(invulnerability.get());
            return;
        }
        int remainingAmount = amount;
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.BUFF_HEALTH && effect instanceof DeterioratingEffect de && de.getRemainingPower() > 0) {
                if (de.getRemainingPower() >= remainingAmount) {
                    de.setRemainingPower(de.getRemainingPower() - remainingAmount);
                    remainingAmount = 0;
                }
                else {
                    remainingAmount -= de.getRemainingPower();
                    de.setRemainingPower(0);
                }
            }
        }
        health = health <= remainingAmount ? 0 : health - remainingAmount;
        if (health == 0) {
            battle.removeCharacter(this);
            if (battle.getUi() != null) {
                battle.getUi().characterDefeated(this);
            }
        }
    }

    public void heal(final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of damage must be positive!");
        }
        health = getMaxHealth() - health <= amount ? getMaxHealth() : health + amount;
    }

    public boolean isInvisible() {
        for (final Effect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.INVISIBILITY) {
                return true;
            }
        }
        return false;
    }

    protected void attacksCoolDown() {
        if (cooldownNormal > 0) {
            cooldownNormal--;
        }
        if (cooldownSpecial1 > 0) {
            cooldownSpecial1--;
        }
        if (cooldownSpecial2 > 0) {
            cooldownSpecial2--;
        }
    }

    public void startTurn() {
        setMoved(0);
        final List<PersistentEffect> expiredEffects = new LinkedList<>();
        for (final PersistentEffect effect : persistentEffects) {
            effect.decreaseRemainingDuration();
            if (effect.getRemainingDuration() == 0) {
                expiredEffects.add(effect);
            }
        }
        persistentEffects.removeAll(expiredEffects);
        attacksCoolDown();
    }

    public void turn() {
        // intentionally left empty
    }

    public void endTurn() {
        for (final PersistentEffect effect : persistentEffects) {
            if (effect.getEffectType() == EffectType.BLEED) {
                takeDamage(effect.getMaxPower());
            }
        }
        final Attack usedAttack = battle.getSelectedAttack();
        if (usedAttack == null) {
            return;
        }
        if (usedAttack == modified.getBase().attacks().normal()) {
            cooldownNormal = usedAttack.cooldown();
        }
        else if (usedAttack == modified.getBase().attacks().special1()) {
            cooldownSpecial1 = usedAttack.cooldown();
        }
        else if (usedAttack == modified.getBase().attacks().special2()) {
            cooldownSpecial2 = usedAttack.cooldown();
        }
    }
}

package org.abos.twi.gatcha.core;

import org.jetbrains.annotations.Range;

public record CharacterStats(int maxHealth, int speed, int attack, int defense) {

    public CharacterStats(final @Range(from = 0, to = Integer.MAX_VALUE) int maxHealth,
                          final @Range(from = 0, to = Integer.MAX_VALUE) int speed,
                          final @Range(from = 0, to = Integer.MAX_VALUE) int attack,
                          final @Range(from = 0, to = Integer.MAX_VALUE) int defense) {
        if (maxHealth < 0 || speed < 0 || attack < 0 || defense < 0) {
            throw new IllegalArgumentException("No stat can be negative!");
        }
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
    }

    double initiative() {
        return speed/4d;
    }

    int movement() {
        return (int)Math.round(initiative());
    }

}

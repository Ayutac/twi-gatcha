package org.abos.twi.gatcha.core;

public record CharacterStats(int maxHealth, int speed, int attack, int defense) {

    public CharacterStats {
        if (maxHealth < 0 || speed < 0 || attack < 0 || defense < 0) {
            throw new IllegalArgumentException("No stat can be negative!");
        }
    }

    double initiative() {
        return speed/4d;
    }

    int movement() {
        return (int)Math.round(initiative());
    }

}

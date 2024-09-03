package org.abos.twi.gatcha.core.battle;

public interface Effect {

    EffectType getEffectType();

    void apply(final CharacterInBattle from, final Position target, final Field field);

}

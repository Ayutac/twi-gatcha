package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2d;

public interface Effect {

    EffectType getEffectType();

    void apply(final CharacterInBattle from, final Vec2d target, final Field field);

}

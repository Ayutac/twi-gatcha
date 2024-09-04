package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;

public interface Effect {

    EffectType getEffectType();

    void apply(final CharacterInBattle from, final Vec2i target, final Field field);

}

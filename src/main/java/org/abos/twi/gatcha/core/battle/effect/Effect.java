package org.abos.twi.gatcha.core.battle.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;

public interface Effect {

    EffectType getEffectType();

    void apply(final CharacterInBattle from, final Vec2i target, final Battle battle);

}

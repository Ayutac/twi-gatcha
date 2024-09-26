package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface AiConstructor {

    AiCharacter apply(final @NotNull CharacterModified modified, final @NotNull Battle battle, final @NotNull TeamKind team, final @NotNull Vec2i position);

}

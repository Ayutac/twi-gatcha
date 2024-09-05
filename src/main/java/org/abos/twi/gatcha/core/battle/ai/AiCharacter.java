package org.abos.twi.gatcha.core.battle.ai;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * A {@link CharacterInBattle} that holds a {@link Random} instance seeded with its base.
 * <p>
 * This class also serves as a marker which characters should move automatically in {@link Battle}.
 */
public abstract class AiCharacter extends CharacterInBattle {

    /**
     * A randomizer for the character's movement and attack pattern.
     */
    protected @NotNull Random random;

    public AiCharacter(@NotNull CharacterModified modified, @NotNull Battle battle, @NotNull TeamKind team, @NotNull Vec2i position) {
        super(modified, battle, team, position);
        random = new Random(modified.getBase().hashCode());
    }

}

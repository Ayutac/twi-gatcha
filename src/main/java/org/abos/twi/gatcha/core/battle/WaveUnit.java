package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.BiFunction;

public record WaveUnit(CharacterModified character, TeamKind team, Vec2i startPos, BiFunction<WaveUnit, Battle, CharacterInBattle> cibCreator) {

    public WaveUnit(final @NotNull CharacterModified character, @NotNull TeamKind team, @NotNull Vec2i startPos,
                    final @NotNull BiFunction<WaveUnit, Battle, CharacterInBattle> cibCreator) {
        this.character = Objects.requireNonNull(character);
        this.team = Objects.requireNonNull(team);
        this.startPos = Objects.requireNonNull(startPos);
        this.cibCreator = Objects.requireNonNull(cibCreator);
    }

    public WaveUnit(final @NotNull CharacterModified character, @NotNull Vec2i startPos,
                    final @NotNull BiFunction<WaveUnit, Battle, CharacterInBattle> cibCreator) {
        this(character, TeamKind.ENEMY, startPos, cibCreator);
    }

    public CharacterInBattle createCharacterInBattle(final @NotNull Battle battle) {
        return cibCreator.apply(this, Objects.requireNonNull(battle));
    }

}

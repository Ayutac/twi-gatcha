package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.battle.ai.AiConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public class SummonEffect extends ApplicableEffect {

    protected final @NotNull CharacterBase summon;
    protected final @NotNull AiConstructor ai;

    public SummonEffect(@Range(from = 0, to = Integer.MAX_VALUE) int maxPower, @Range(from = 0, to = Integer.MAX_VALUE) int maxDuration, final @NotNull CharacterBase summon, final @NotNull AiConstructor ai) {
        super(EffectType.SUMMON, maxPower, maxDuration, 0, 0, null, null, null);
        this.summon = Objects.requireNonNull(summon);
        this.ai = Objects.requireNonNull(ai);
    }

    @Override
    public void apply(final @NotNull CharacterInBattle from, final @NotNull Vec2i target, final @NotNull Battle battle) {
        if (battle.isCharacterAt(target)) {
            return;
        }
        battle.summon(ai.apply(new CharacterModified(summon, Math.max(1, from.getModified().getLevel() - 10)), battle, from.getTeam() == TeamKind.ENEMY ? TeamKind.ENEMY : TeamKind.ALLY, target));
    }
}

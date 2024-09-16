package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.Group;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.data.Lookups;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;
import java.util.Optional;

/**
 * For attacks that lead to a duration effect but only affect a specific {@link org.abos.twi.gatcha.core.Group}.
 */
public class GroupAttackEffect extends SimpleAttackEffect {

    protected final @NotNull String groupId;
    protected Group group;

    public GroupAttackEffect(final @NotNull EffectType type,
                             final @Range(from = 0, to = Integer.MAX_VALUE) int power,
                             final @NotNull String groupId) {
        super(type, power);
        this.groupId = Objects.requireNonNull(groupId);
    }

    @Override
    public void apply(final CharacterInBattle from, final Vec2i target, final Battle battle) {
        final Optional<CharacterInBattle> to = battle.getCharacterAt(target);
        if (to.isEmpty()) {
            return;
        }
        if (group == null) {
            group = Lookups.GROUPS.get(groupId);
        }
        if (!group.characters().contains(to.get().getModified().getBase())) {
            return;
        }
        switch (type) {
            case TURN_FRIENDLY -> {
                to.get().getActiveEffects().add(new SimpleDurationEffect(type, power));
            }
            default -> throw new IllegalStateException("An unfitting effect type has been associated with this " + GroupAttackEffect.class.getSimpleName() + "!");
        }
        if (battle.getUi() != null) {
            battle.getUi().characterAttacked(from, to.get(), type, 0);
        }
    }


}

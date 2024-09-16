package org.abos.twi.gatcha.core;

import org.abos.common.Describable;
import org.abos.twi.gatcha.data.Lookups;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * The character as modified by a player outside of battle, based on {@link CharacterBase}.
 */
public class CharacterModified implements Describable {

    public static final int MAX_LEVEL = 60;

    protected final @NotNull CharacterBase base;

    protected @Range(from = 1, to = MAX_LEVEL) int level = 1;

    public CharacterModified(final @NotNull CharacterBase base) {
        this.base = Objects.requireNonNull(base);
    }

    @Range(from = 1, to = MAX_LEVEL)
    public int getLevel() {
        return level;
    }

    public void increaseLevel(final @Nullable PlayerStats stats) {
        if (level == MAX_LEVEL) {
            throw new IllegalArgumentException("Level cannot be increased anymore!");
        }
        this.level++;
        if (stats != null) {
            stats.increaseCharacterLevelledUp();
        }
    }

    @Override
    public @NotNull String getName() {
        return base.getName();
    }

    @Override
    public @NotNull String getDescription() {
        return base.getDescription();
    }

    public @NotNull CharacterBase getBase() {
        return base;
    }

    public static int calcLinValue(final @Range(from = 0, to = Integer.MAX_VALUE) int startValue,
                                   final @Range(from = 0, to = Integer.MAX_VALUE) int endValue,
                                   final @Range(from = 1, to = MAX_LEVEL) int level) {
        final double m = (endValue - startValue) / (double)(MAX_LEVEL - 1);
        final double n = (MAX_LEVEL * startValue - endValue) / (double)(MAX_LEVEL - 1);
        return (int)Math.round(m * level + n);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMaxHealth() {
        return calcLinValue(base.startStats().maxHealth(), base.endStats().maxHealth(), level);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getSpeed() {
        return calcLinValue(base.startStats().speed(), base.endStats().speed(), level);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getAttack() {
        return calcLinValue(base.startStats().attack(), base.endStats().attack(), level);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getDefense() {
        return calcLinValue(base.startStats().defense(), base.endStats().defense(), level);
    }

    public double getInitiative() {
        return getSpeed() / 4d;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getMovement() {
        return (int)Math.round(getInitiative());
    }

    public void save(final @NotNull ObjectOutputStream oos) throws IOException {
        oos.writeUTF(base.getId());
        oos.writeInt(level);
    }

    public static CharacterModified load(final @NotNull ObjectInputStream ois) throws IOException {
        final CharacterBase base = Lookups.CHARACTERS.get(ois.readUTF());
        final CharacterModified character = new CharacterModified(base);
        character.level = ois.readInt();
        return character;
    }
}

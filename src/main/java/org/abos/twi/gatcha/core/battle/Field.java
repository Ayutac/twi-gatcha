package org.abos.twi.gatcha.core.battle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Field {

    protected final @Range(from = 1, to = Integer.MAX_VALUE) int height;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int width;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int size;

    protected final List<CharacterInBattle> characters = new LinkedList<>();

    public Field(final @Range(from = 1, to = Integer.MAX_VALUE) int height,
                 final @Range(from = 1, to = Integer.MAX_VALUE) int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Dimensions must be positive!");
        }
        this.height = height;
        this.width = width;
        try {
            this.size = Math.multiplyExact(height, width);
        }
        catch (ArithmeticException ex) {
            throw new IllegalArgumentException("Dimensions are too big!");
        }
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getHeight() {
        return height;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getWidth() {
        return width;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getSize() {
        return size;
    }

    @NotNull
    public Optional<CharacterInBattle> getCharacterAt(final Position position) {
        for (final CharacterInBattle character : characters) {
            if (character.isAt(position)) {
                return Optional.of(character);
            }
        }
        return Optional.empty();
    }

    public boolean removeCharacter(final CharacterInBattle character) {
        return characters.remove(character);
    }
}

package org.abos.twi.gatcha.core;

import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the player of this game.
 */
public class Player implements Named {

    public static final int PARTY_MAX_SIZE = 5;

    /**
     * @see #getName()
     */
    protected String name;

    protected InventoryMap inventory = new InventoryMap();

    protected Map<CharacterBase, CharacterModified> rooster = new HashMap<>();

    protected List<Party> parties = new LinkedList<>();

    public Player(final @NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    /**
     * Sets the name of this player.
     * @param name the new name, not {@code null}
     */
    public void setName(final @NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * This {@link Player}'s inventory.
     * @return the inventory, not {@code null}.
     */
    public InventoryMap getInventory() {
        return inventory;
    }

    public boolean hasCharacter(final @NotNull CharacterBase character) {
        return rooster.containsKey(character);
    }

    public CharacterModified getCharacter(final @NotNull CharacterBase character) {
        if (!rooster.containsKey(character)) {
            throw new IllegalArgumentException("The player doesn't have this character!");
        }
        return rooster.get(character);
    }

    public List<CharacterModified> getCharacters() {
        return new ArrayList<>(rooster.values());
    }

    public void addToRooster(final @NotNull CharacterBase character) {
        if (rooster.containsKey(character)) {
            throw new IllegalArgumentException("Player already owns this character!");
        }
        rooster.put(character, new CharacterModified(character));
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getNumberOfParties() {
        return parties.size();
    }

    public Party getParty(final @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        return parties.get(index);
    }

    public void addParty(final @NotNull Party party) {
        if (party.characters().size() > PARTY_MAX_SIZE) {
            throw new IllegalArgumentException("Party can have at most " + PARTY_MAX_SIZE + " members!");
        }
        if (party.characters().size() != party.characters().stream().distinct().count()) {
            throw new IllegalArgumentException("Party cannot contain duplicates!");
        }
        parties.add(party);
    }

}

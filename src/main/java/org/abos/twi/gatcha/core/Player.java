package org.abos.twi.gatcha.core;

import org.abos.common.Named;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    public static final int DEFAULT_MAX_STAMINA = 100;

    public static final int PARTY_MAX_SIZE = 5;

    /**
     * @see #getName()
     */
    protected @NotNull String name;

    protected @NotNull Instant lastAutoFill;

    protected @Range(from = 1, to = Integer.MAX_VALUE) int maxStamina;

    protected @Range(from = 0, to = Integer.MAX_VALUE) int stamina;

    protected final InventoryMap inventory = new InventoryMap();

    protected final Map<String, CharacterModified> rooster = new HashMap<>();

    protected final List<Party> parties = new LinkedList<>();

    protected final PlayerStats stats = new PlayerStats();

    public Player(final @NotNull String name, final @NotNull Instant lastAutoFill) {
        this.name = Objects.requireNonNull(name);
        this.stamina = this.maxStamina = DEFAULT_MAX_STAMINA;
        this.lastAutoFill = lastAutoFill;
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

    public @Range(from = 1, to = Integer.MAX_VALUE) int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(final @Range(from = 1, to = Integer.MAX_VALUE) int maxStamina) {
        if (maxStamina < 1) {
            throw new IllegalArgumentException("Maximal Stamina must be positive!");
        }
        this.maxStamina = maxStamina;
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getStamina() {
        return stamina;
    }

    public void setStamina(final @Range(from = 0, to = Integer.MAX_VALUE) int stamina) {
        if (stamina < 0) {
            throw new IllegalArgumentException("Stamina cannot be negative!");
        }
        this.stamina = stamina;
    }

    public void fillStamina() {
        stamina = Math.max(maxStamina, stamina);
        lastAutoFill = Instant.now().truncatedTo(ChronoUnit.HALF_DAYS);
    }

    /**
     * This {@link Player}'s inventory.
     * @return the inventory, not {@code null}.
     */
    public InventoryMap getInventory() {
        return inventory;
    }

    public boolean hasCharacter(final @NotNull CharacterBase character) {
        return rooster.containsKey(character.id());
    }

    public CharacterModified getCharacter(final @NotNull CharacterBase character) {
        if (!rooster.containsKey(character.id())) {
            throw new IllegalArgumentException("The player doesn't have this character!");
        }
        return rooster.get(character.id());
    }

    public List<CharacterModified> getCharacters() {
        return new ArrayList<>(rooster.values());
    }

    public void addToRooster(final @NotNull CharacterBase character) {
        if (rooster.containsKey(character.id())) {
            throw new IllegalArgumentException("Player already owns this character!");
        }
        rooster.put(character.id(), new CharacterModified(character));
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
        parties.add(party);
    }

    public void updateParty(final @NotNull Party party, final @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        parties.set(index, Objects.requireNonNull(party));
    }

    public PlayerStats getStats() {
        return stats;
    }

    public void save(final @NotNull ObjectOutputStream oos) throws IOException {
        oos.writeUTF(name);
        oos.writeLong(lastAutoFill.getEpochSecond());
        oos.writeInt(maxStamina);
        oos.writeInt(stamina);
        inventory.save(oos);
        oos.writeInt(rooster.size());
        for (final CharacterModified character : rooster.values()) {
            character.save(oos);
        }
        oos.writeInt(parties.size());
        for (final Party party : parties) {
            oos.writeUTF(party.getName());
            oos.writeInt(party.characters().size());
            for (final CharacterModified character : party.characters()) {
                oos.writeUTF(character.getBase().id());
            }
        }
        stats.save(oos);
    }

    public static Player load(final @NotNull ObjectInputStream ois) throws IOException {
        final String name = ois.readUTF();
        final Instant lastAutoFill = Instant.ofEpochSecond(ois.readLong());
        final Player player = new Player(name, lastAutoFill);
        player.maxStamina = ois.readInt();
        player.stamina = ois.readInt();
        if (Instant.now().truncatedTo(ChronoUnit.HALF_DAYS).isAfter(lastAutoFill)) {
            player.fillStamina();
        }
        final InventoryMap inventory = InventoryMap.load(ois);
        player.inventory.addAll(inventory);
        final int roosterSize = ois.readInt();
        for (int i = 0; i < roosterSize; i++) {
            final CharacterModified character = CharacterModified.load(ois);
            player.rooster.put(character.getBase().getId(), character);
        }
        final int partiesSize = ois.readInt();
        for (int i = 0; i < partiesSize; i++) {
            final String partyName = ois.readUTF();
            final int partySize = ois.readInt();
            final List<CharacterModified> characters = new ArrayList<>(partySize);
            for (int j = 0; j < partySize; j++) {
                characters.add(player.rooster.get(ois.readUTF()));
            }
            player.parties.add(new Party(partyName, characters));
        }
        player.stats.copyStats(PlayerStats.load(ois));
        player.inventory.setAssociatedStats(player.stats);
        return player;
    }

}

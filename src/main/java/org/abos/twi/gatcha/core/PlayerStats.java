package org.abos.twi.gatcha.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerStats {

    protected final @NotNull InventoryMap itemGot = new InventoryMap();
    protected final @NotNull HashMap<String, Integer> characterDeployed = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> characterInSquat = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> characterDeployedDefeated = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> enemyDefeated = new HashMap<>();
    protected int dailyEnemyDefeated = 0;
    protected boolean dailyEnemyDefeated5 = false;
    protected boolean dailyEnemyDefeated50 = false;
    protected int characterLevelledUp = 0;
    protected int dailyCharacterLevelledUp = 0;
    protected boolean dailyCharacterLevelledUp1 = false;
    protected boolean dailyCharacterLevelledUp10 = false;
    protected final @NotNull HashMap<String, Integer> levelWon = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> levelTied = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> levelLost = new HashMap<>();
    protected int dailyLevelWon = 0;
    protected boolean dailyLevelWon1 = false;
    protected boolean dailyLevelWon10 = false;
    protected final @NotNull HashMap<String, Integer> boosterPulled = new HashMap<>();
    protected int dailyBoosterPulled = 0;
    protected boolean dailyBoosterPulled1 = false;
    protected final @NotNull EnumMap<Rarity, Integer> rarityPulled = new EnumMap<>(Rarity.class);

    public void increaseItemGot(final @NotNull InventoryKind item, final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        itemGot.add(item, amount);
    }

    public void resetDailies() {
        dailyEnemyDefeated = 0;
        dailyEnemyDefeated5 = false;
        dailyEnemyDefeated50 = false;
        dailyCharacterLevelledUp = 0;
        dailyCharacterLevelledUp1 = false;
        dailyCharacterLevelledUp10 = false;
        dailyLevelWon = 0;
        dailyLevelWon1 = false;
        dailyLevelWon10 = false;
        dailyBoosterPulled = 0;
        dailyBoosterPulled1 = false;
    }

    public int getItemGot(final @NotNull InventoryKind item) {
        return itemGot.getOrDefault(item, 0);
    }

    public void increaseCharacterDeployed(final @NotNull CharacterModified character) {
        final String id = character.getBase().getId();
        characterDeployed.put(id, 1 + characterDeployed.getOrDefault(id, 0));
    }

    public int getCharacterDeployed(final @NotNull CharacterModified character) {
        return characterDeployed.getOrDefault(character.getBase().getId(), 0);
    }

    public void increaseCharacterInSquat(final @NotNull CharacterModified character) {
        final String id = character.getBase().getId();
        characterInSquat.put(id, 1 + characterInSquat.getOrDefault(id, 0));
    }

    public int getCharacterInSquat(final @NotNull CharacterModified character) {
        return characterInSquat.getOrDefault(character.getBase().getId(), 0);
    }

    public void increaseCharacterDeployedDefeated(final @NotNull CharacterModified character) {
        final String id = character.getBase().getId();
        characterDeployedDefeated.put(id, 1 + characterDeployedDefeated.getOrDefault(id, 0));
    }

    public int getCharacterDeployedDefeated(final @NotNull CharacterModified character) {
        return characterDeployedDefeated.getOrDefault(character.getBase().getId(), 0);
    }

    public void increaseEnemyDefeated(final @NotNull CharacterModified character) {
        final String id = character.getBase().getId();
        enemyDefeated.put(id, 1 + enemyDefeated.getOrDefault(id, 0));
        dailyEnemyDefeated++;
    }

    public int getEnemyDefeated(final @NotNull CharacterModified character) {
        return enemyDefeated.getOrDefault(character.getBase().getId(), 0);
    }

    public int getDailyEnemyDefeated() {
        return dailyEnemyDefeated;
    }

    public boolean isDailyEnemyDefeated5() {
        return dailyEnemyDefeated5;
    }

    public void useDailyEnemyDefeated5() {
        dailyEnemyDefeated5 = true;
    }

    public boolean isDailyEnemyDefeated50() {
        return dailyEnemyDefeated50;
    }

    public void useDailyEnemyDefeated50() {
        dailyEnemyDefeated50 = true;
    }

    public void increaseCharacterLevelledUp() {
        characterLevelledUp++;
        dailyCharacterLevelledUp++;
    }

    public int getCharacterLevelledUp() {
        return characterLevelledUp;
    }

    public int getDailyCharacterLevelledUp() {
        return dailyCharacterLevelledUp;
    }

    public boolean isDailyCharacterLevelledUp1() {
        return dailyCharacterLevelledUp1;
    }

    public void useDailyCharacterLevelledUp1() {
        dailyCharacterLevelledUp1 = true;
    }

    public boolean isDailyCharacterLevelledUp10() {
        return dailyCharacterLevelledUp10;
    }

    public void useDailyCharacterLevelledUp10() {
        dailyCharacterLevelledUp10 = true;
    }

    public void increaseLevelWon(final @NotNull String levelId) {
        levelWon.put(levelId, 1 + levelWon.getOrDefault(levelId, 0));
        dailyLevelWon++;
    }

    public int getLevelWon(final @NotNull String levelId) {
        return levelWon.getOrDefault(levelId, 0);
    }

    public void increaseLevelTied(final @NotNull String levelId) {
        levelTied.put(levelId, 1 + levelTied.getOrDefault(levelId, 0));
    }

    public int getLevelTied(final @NotNull String levelId) {
        return levelTied.getOrDefault(levelId, 0);
    }

    public void increaseLevelLost(final @NotNull String levelId) {
        levelLost.put(levelId, 1 + levelLost.getOrDefault(levelId, 0));
    }

    public int getLevelLost(final @NotNull String levelId) {
        return levelLost.getOrDefault(levelId, 0);
    }

    public int getDailyLevelWon() {
        return dailyLevelWon;
    }

    public boolean isDailyLevelWon1() {
        return dailyLevelWon1;
    }

    public void useDailyLevelWon1() {
        dailyLevelWon1 = true;
    }

    public boolean isDailyLevelWon10() {
        return dailyLevelWon10;
    }

    public void useDailyLevelWon10() {
        dailyLevelWon10 = true;
    }

    public void increaseBoosterPulled(final @NotNull Booster booster) {
        final String id = booster.getId();
        boosterPulled.put(id, 1 + boosterPulled.getOrDefault(id, 0));
        dailyBoosterPulled++;
    }

    public int getBoosterPulled(final @NotNull Booster booster) {
        return boosterPulled.getOrDefault(booster.getId(), 0);
    }

    public int getDailyBoosterPulled() {
        return dailyBoosterPulled;
    }

    public boolean isDailyBoosterPulled1() {
        return dailyBoosterPulled1;
    }

    public void useDailyBoosterPulled1() {
        dailyBoosterPulled1 = true;
    }

    public void increaseRarityPulled(final @NotNull Rarity rarity) {
        rarityPulled.put(rarity, 1 + rarityPulled.getOrDefault(rarity, 0));
    }

    public int getRarityPulled(final @NotNull Rarity rarity) {
        return rarityPulled.getOrDefault(rarity, 0);
    }

    void copyStats(final @NotNull PlayerStats from) {
        itemGot.clear();
        itemGot.putAll(from.itemGot);
        characterDeployed.clear();
        characterDeployed.putAll(from.characterDeployed);
        characterInSquat.clear();
        characterInSquat.putAll(from.characterInSquat);
        characterDeployedDefeated.clear();
        characterDeployedDefeated.putAll(from.characterDeployedDefeated);
        enemyDefeated.clear();
        enemyDefeated.putAll(from.enemyDefeated);
        dailyEnemyDefeated = from.dailyEnemyDefeated;
        dailyEnemyDefeated5 = from.dailyEnemyDefeated5;
        dailyEnemyDefeated50 = from.dailyEnemyDefeated50;
        characterLevelledUp = from.characterLevelledUp;
        dailyCharacterLevelledUp = from.dailyCharacterLevelledUp;
        dailyCharacterLevelledUp1 = from.dailyCharacterLevelledUp1;
        dailyCharacterLevelledUp10 = from.dailyCharacterLevelledUp10;
        levelWon.clear();
        levelWon.putAll(from.levelWon);
        levelTied.clear();
        levelTied.putAll(from.levelTied);
        levelLost.clear();
        levelLost.putAll(from.levelLost);
        dailyLevelWon = from.dailyLevelWon;
        dailyLevelWon1 = from.dailyLevelWon1;
        dailyLevelWon10 = from.dailyLevelWon10;
        boosterPulled.clear();
        boosterPulled.putAll(from.boosterPulled);
        dailyBoosterPulled = from.dailyBoosterPulled;
        dailyBoosterPulled1 = from.dailyBoosterPulled1;
        rarityPulled.clear();
        rarityPulled.putAll(from.rarityPulled);
    }

    public void save(final @NotNull ObjectOutputStream oos) throws IOException {
        itemGot.save(oos);
        oos.writeObject(characterDeployed);
        oos.writeObject(characterInSquat);
        oos.writeObject(characterDeployedDefeated);
        oos.writeObject(enemyDefeated);
        oos.writeInt(dailyEnemyDefeated);
        oos.writeBoolean(dailyEnemyDefeated5);
        oos.writeBoolean(dailyEnemyDefeated50);
        oos.writeInt(characterLevelledUp);
        oos.writeInt(dailyCharacterLevelledUp);
        oos.writeBoolean(dailyCharacterLevelledUp1);
        oos.writeBoolean(dailyCharacterLevelledUp10);
        oos.writeObject(levelWon);
        oos.writeObject(levelTied);
        oos.writeObject(levelLost);
        oos.writeInt(dailyLevelWon);
        oos.writeBoolean(dailyLevelWon1);
        oos.writeBoolean(dailyLevelWon10);
        oos.writeObject(boosterPulled);
        oos.writeInt(dailyBoosterPulled);
        oos.writeBoolean(dailyBoosterPulled1);
        oos.writeObject(rarityPulled);
    }

    public static PlayerStats load(final @NotNull ObjectInputStream ois) throws IOException {
        final PlayerStats stats = new PlayerStats();
        final InventoryMap itemGot = InventoryMap.load(ois);
        stats.itemGot.addAll(itemGot);
        try {
            stats.characterDeployed.putAll((Map<String, Integer>) ois.readObject());
            stats.characterInSquat.putAll((Map<String, Integer>) ois.readObject());
            stats.characterDeployedDefeated.putAll((Map<String, Integer>) ois.readObject());
            stats.enemyDefeated.putAll((Map<String, Integer>) ois.readObject());
            stats.dailyEnemyDefeated = ois.readInt();
            stats.dailyEnemyDefeated5 = ois.readBoolean();
            stats.dailyEnemyDefeated50 = ois.readBoolean();
            stats.characterLevelledUp = ois.readInt();
            stats.dailyCharacterLevelledUp = ois.readInt();
            stats.dailyCharacterLevelledUp1 = ois.readBoolean();
            stats.dailyCharacterLevelledUp10 = ois.readBoolean();
            stats.levelWon.putAll((Map<String, Integer>) ois.readObject());
            stats.levelTied.putAll((Map<String, Integer>) ois.readObject());
            stats.levelLost.putAll((Map<String, Integer>) ois.readObject());
            stats.dailyLevelWon = ois.readInt();
            stats.dailyLevelWon1 = ois.readBoolean();
            stats.dailyLevelWon10 = ois.readBoolean();
            stats.boosterPulled.putAll((Map<String, Integer>) ois.readObject());
            stats.dailyBoosterPulled = ois.readInt();
            stats.dailyBoosterPulled1 = ois.readBoolean();
            stats.rarityPulled.putAll((Map<Rarity, Integer>) ois.readObject());
        }
        catch (final ClassNotFoundException ex) {
            Logger.getGlobal().warning("Stats couldn't be loaded fully!");
        }
        return stats;
    }

}

package org.abos.twi.gatcha.core;

import org.abos.twi.gatcha.core.quest.QuestId;
import org.abos.twi.gatcha.core.quest.QuestKind;
import org.abos.twi.gatcha.core.quest.QuestLineId;
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

    public static final @NotNull String ENEMIES_DEFEATED = "enemies_defeated";
    public static final @NotNull String CHARACTER_LEVELLED_UP = "character_levelled_up";
    public static final @NotNull String LEVELS_WON = "levels_won";
    public static final @NotNull String BOOSTERS_PULLED = "boosters_pulled";

    protected final @NotNull HashMap<QuestLineId, Integer> questTracker = new HashMap<>();
    protected final @NotNull HashMap<QuestId, Boolean> questTaker = new HashMap<>();

    protected final @NotNull InventoryMap itemGot = new InventoryMap();
    protected final @NotNull HashMap<String, Integer> characterDeployed = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> characterInSquat = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> characterDeployedDefeated = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> enemyDefeated = new HashMap<>();
    // we don't track individual level ups because that's just the characters' levels
    protected int characterLevelledUp = 0;
    protected final @NotNull HashMap<String, Integer> levelWon = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> levelTied = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> levelLost = new HashMap<>();
    protected final @NotNull HashMap<String, Integer> boosterPulled = new HashMap<>();
    protected final @NotNull EnumMap<Rarity, Integer> rarityPulled = new EnumMap<>(Rarity.class);

    public void increaseItemGot(final @NotNull InventoryKind item, final @Range(from = 0, to = Integer.MAX_VALUE) int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative!");
        }
        itemGot.add(item, amount);
    }

    public int getQuestCounter(final @NotNull QuestLineId qlId) {
        return questTracker.getOrDefault(qlId, 0);
    }

    public boolean hasQuestBeenTaken(final @NotNull QuestId qId) {
        return questTaker.getOrDefault(qId, false);
    }

    public void takeQuest(final @NotNull QuestId qId) {
        questTaker.put(qId, true);
    }

    public void resetDailies() {
        for (final QuestLineId qlId : questTracker.keySet()) {
            if (qlId.kind() == QuestKind.DAILY) {
                questTracker.put(qlId, 0);
            }
        }
        for (final QuestId qId : questTaker.keySet()) {
            if (qId.kind() == QuestKind.DAILY) {
                questTaker.put(qId, false);
            }
        }
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
        final QuestLineId qlId = new QuestLineId(QuestKind.DAILY, ENEMIES_DEFEATED);
        questTracker.put(qlId, 1 + questTracker.getOrDefault(qlId, 0));
    }

    public int getEnemyDefeated(final @NotNull CharacterModified character) {
        return enemyDefeated.getOrDefault(character.getBase().getId(), 0);
    }

    public void increaseCharacterLevelledUp() {
        characterLevelledUp++;
        final QuestLineId qlId = new QuestLineId(QuestKind.DAILY, CHARACTER_LEVELLED_UP);
        questTracker.put(qlId, 1 + questTracker.getOrDefault(qlId, 0));
    }

    public int getCharacterLevelledUp() {
        return characterLevelledUp;
    }

    public void increaseLevelWon(final @NotNull String levelId) {
        levelWon.put(levelId, 1 + levelWon.getOrDefault(levelId, 0));
        final QuestLineId qlId = new QuestLineId(QuestKind.DAILY, LEVELS_WON);
        questTracker.put(qlId, 1 + questTracker.getOrDefault(qlId, 0));
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

    public void increaseBoosterPulled(final @NotNull Booster booster) {
        final String id = booster.getId();
        boosterPulled.put(id, 1 + boosterPulled.getOrDefault(id, 0));
        final QuestLineId qlId = new QuestLineId(QuestKind.DAILY, BOOSTERS_PULLED);
        questTracker.put(qlId, 1 + questTracker.getOrDefault(qlId, 0));
    }

    public int getBoosterPulled(final @NotNull Booster booster) {
        return boosterPulled.getOrDefault(booster.getId(), 0);
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
        characterLevelledUp = from.characterLevelledUp;
        levelWon.clear();
        levelWon.putAll(from.levelWon);
        levelTied.clear();
        levelTied.putAll(from.levelTied);
        levelLost.clear();
        levelLost.putAll(from.levelLost);
        boosterPulled.clear();
        boosterPulled.putAll(from.boosterPulled);
        rarityPulled.clear();
        rarityPulled.putAll(from.rarityPulled);
        questTracker.clear();
        questTracker.putAll(from.questTracker);
        questTaker.clear();
        questTaker.putAll(from.questTaker);
    }

    public void save(final @NotNull ObjectOutputStream oos) throws IOException {
        oos.writeObject(questTracker);
        oos.writeObject(questTaker);
        itemGot.save(oos);
        oos.writeObject(characterDeployed);
        oos.writeObject(characterInSquat);
        oos.writeObject(characterDeployedDefeated);
        oos.writeObject(enemyDefeated);
        oos.writeInt(characterLevelledUp);
        oos.writeObject(levelWon);
        oos.writeObject(levelTied);
        oos.writeObject(levelLost);
        oos.writeObject(boosterPulled);
        oos.writeObject(rarityPulled);
    }

    public static PlayerStats load(final @NotNull ObjectInputStream ois) throws IOException {
        final PlayerStats stats = new PlayerStats();
        final InventoryMap itemGot = InventoryMap.load(ois);
        stats.itemGot.addAll(itemGot);
        try {
            stats.questTracker.putAll((Map<QuestLineId, Integer>) ois.readObject());
            stats.questTaker.putAll((Map<QuestId, Boolean>) ois.readObject());
            stats.characterDeployed.putAll((Map<String, Integer>) ois.readObject());
            stats.characterInSquat.putAll((Map<String, Integer>) ois.readObject());
            stats.characterDeployedDefeated.putAll((Map<String, Integer>) ois.readObject());
            stats.enemyDefeated.putAll((Map<String, Integer>) ois.readObject());
            stats.characterLevelledUp = ois.readInt();
            stats.levelWon.putAll((Map<String, Integer>) ois.readObject());
            stats.levelTied.putAll((Map<String, Integer>) ois.readObject());
            stats.levelLost.putAll((Map<String, Integer>) ois.readObject());
            stats.boosterPulled.putAll((Map<String, Integer>) ois.readObject());
            stats.rarityPulled.putAll((Map<Rarity, Integer>) ois.readObject());
        }
        catch (final ClassNotFoundException ex) {
            Logger.getGlobal().warning("Stats couldn't be loaded fully!");
        }
        return stats;
    }

}

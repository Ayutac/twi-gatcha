package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.InventoryKind;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.PlayerStats;
import org.abos.twi.gatcha.core.quest.Quest;
import org.abos.twi.gatcha.core.quest.QuestId;
import org.abos.twi.gatcha.core.quest.QuestKind;

public interface Quests {

    Quest DAILY_ENEMIES_DEFEATED_5 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.ENEMIES_DEFEATED, 5),
            "Defeat 5 enemies.",
            new InventoryMap(InventoryKind.MAGICORE, 5));

    Quest DAILY_ENEMIES_DEFEATED_50 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.ENEMIES_DEFEATED, 50),
            "Defeat 50 enemies.",
            new InventoryMap(InventoryKind.MAGICORE, 10));

    Quest DAILY_CHARACTERS_LEVELLED_UP_1 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.CHARACTER_LEVELLED_UP, 1),
            "Level up any character once.",
            new InventoryMap(InventoryKind.MAGICORE, 5));

    Quest DAILY_CHARACTERS_LEVELLED_UP_10 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.CHARACTER_LEVELLED_UP, 10),
            "Level up characters 10 times.",
            new InventoryMap(InventoryKind.MAGICORE, 10));

    Quest DAILY_LEVELS_WON_1 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.LEVELS_WON, 1),
            "Win any mission.",
            new InventoryMap(InventoryKind.MAGICORE, 5));

    Quest DAILY_LEVELS_WON_10 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.LEVELS_WON, 10),
            "Win any 10 missions.",
            new InventoryMap(InventoryKind.MAGICORE, 10));

    Quest DAILY_BOOSTERS_PULLED_1 = new Quest(new QuestId(QuestKind.DAILY, PlayerStats.BOOSTERS_PULLED, 1),
            "Summon a character once.",
            new InventoryMap(InventoryKind.FAERIE_FLOWERS, 1));

}

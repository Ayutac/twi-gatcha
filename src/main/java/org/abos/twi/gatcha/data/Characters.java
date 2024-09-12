package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterClass;
import org.abos.twi.gatcha.core.CharacterStats;
import org.abos.twi.gatcha.core.Rarity;

import java.util.List;

public interface Characters {

    CharacterBase ERIN = new CharacterBase(
            "Erin Solstice",
            "A girl lost in another world.",
            "she", "her",
            CharacterClass.SUPPORT,
            new CharacterStats(10, 8, 4, 3),
            new CharacterAttacks(Attacks.MINOTAUR_PUNCH, Attacks.UNERRING_KNIFE_THROW, Attacks.PASTA),
            List.of(),
            Rarity.RARE,
            "erin.png");

    CharacterBase PISCES = new CharacterBase(
            "Pisces",
            "A mage, striving to change peoples mind about his infamous passion. Seems to have a constantly running nose.",
            "he", "him",
            CharacterClass.MAGIC,
            new CharacterStats(9, 19, 9, 7),
            new CharacterAttacks(Attacks.BONE_DART, Attacks.INVISIBILITY, Attacks.BONE_FRACTURE),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase CERIA = new CharacterBase(
            "Ceria Springwalker",
            "The cheerful and infamous Ice Squirrel, eyes open for any kind of treasure.",
            "she", "her",
            CharacterClass.MAGIC,
            new CharacterStats(10, 9, 10, 7),
            new CharacterAttacks(Attacks.ICE_SHARD, Attacks.FROST_ARMOR, Attacks.FROZEN_WIND),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase KSMVR = new CharacterBase(
            "Ksmvr",
            "Former Prognugator of the Free Hive. Facing the shame of his failings, he now learns to fit in society.",
            "he", "him",
            CharacterClass.SPECIALIST,
            new CharacterStats(10, 15, 9, 9),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.QUICK_MOVEMENT, Attacks.SIDE_STEP),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase YVLON = new CharacterBase(
            "Yvlon Byres",
            "The young daughter of House Byres, wielding silver and bland conversations against evil.",
            "she", "her",
            CharacterClass.MELEE,
            new CharacterStats(16, 5, 9, 16),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.KEEN_EDGE, Attacks.IGNORE_PAIN),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase ZOMBIE = new CharacterBase(
            "Zombie",
            "This was once a person…",
            "it", "it",
            CharacterClass.MELEE,
            new CharacterStats(10, 5, 5, 2),
            new CharacterAttacks(Attacks.MEDIUM_PUNCH, Attacks.UNDEAD_CLAW, Attacks.UNDEAD_BITE),
            List.of(),
            Rarity.COMMON,
            "placeholder.png");

    CharacterBase SKELETON = new CharacterBase(
            "Skeleton",
            "All that's left is bones and rage.",
            "it", "it",
            CharacterClass.MELEE,
            new CharacterStats(13, 5, 3, 1),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.QUICK_SLASH, Attacks.UNDEAD_BITE),
            List.of(),
            Rarity.COMMON,
            "placeholder.png");

    CharacterBase SKELETON_ARCHER = new CharacterBase(
            "Skeleton Archer",
            "A skeleton, but look out for arrows.",
            "it", "it",
            CharacterClass.RANGED,
            new CharacterStats(7, 2, 8, 1),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.UNDEAD_BITE, Attacks.ARROW),
            List.of(),
            Rarity.COMMON,
            "placeholder.png");

}

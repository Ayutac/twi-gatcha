package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterStats;
import org.abos.twi.gatcha.core.Rarity;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.abos.twi.gatcha.core.effect.PersistentEffect;

import java.util.List;

public interface Characters {

    CharacterBase ERIN = new CharacterBase("erin_1",
            "Erin Solstice",
            "A girl lost in another world.",
            "she", "her",
            new CharacterStats(10, 8, 4, 3),
            new CharacterStats(100, 10, 24, 18),
            new CharacterAttacks(Attacks.MINOTAUR_PUNCH, Attacks.UNERRING_KNIFE_THROW, Attacks.PASTA),
            List.of(),
            Rarity.RARE,
            "erin.png");

    CharacterBase PISCES = new CharacterBase("pisces_pre",
            "Pisces",
            "A mage, striving to change peoples mind about his infamous passion. Seems to have a constantly running nose.",
            "he", "him",
            new CharacterStats(9, 19, 9, 7),
            new CharacterStats(90, 21, 36, 42),
            new CharacterAttacks(Attacks.BONE_DART, Attacks.INVISIBILITY, Attacks.BONE_FRACTURE),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase PISCES_CRELER = new CharacterBase("pisces_post",
            "Pisces Jealnet, [Deathbane Necromancer]",
            "The necromancer of the new Horns of Hammerad. He fears Wistram no longer.",
            "he", "him",
            new CharacterStats(10, 23, 14, 9),
            new CharacterStats(100, 25, 72, 54),
            new CharacterAttacks(Attacks.DEATH_BOLT, Attacks.TURN_SKELETON, Attacks.CONSTANT_FOE_UNDEAD),
            List.of(),
            Rarity.SUPER_RARE,
            "placeholder.png");

    CharacterBase CERIA = new CharacterBase("ceria_pre",
            "Ceria Springwalker",
            "The Ice mage and Captain of the new Horns of Hammerad.",
            "she", "her",
            new CharacterStats(10, 9, 10, 7),
            new CharacterStats(100, 11, 60, 42),
            new CharacterAttacks(Attacks.ICE_SHARD, Attacks.FROST_ARMOR, Attacks.FROZEN_WIND),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase KSMVR = new CharacterBase("ksmvr_pre",
            "Ksmvr",
            "Former Prognugator of the Free Hive. Facing the shame of his failings, he now learns to fit in society.",
            "he", "him",
            new CharacterStats(10, 15, 9, 9),
            new CharacterStats(100, 17, 54, 54),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.QUICK_MOVEMENT, Attacks.SIDE_STEP),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase YVLON = new CharacterBase("yvlon_pre",
            "Yvlon Byres",
            "The young daughter of House Byres, wielding silver and bland conversations against evil.",
            "she", "her",
            new CharacterStats(16, 5, 9, 16),
            new CharacterStats(160, 7, 54, 96),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.KEEN_EDGE, Attacks.IGNORE_PAIN),
            List.of(),
            Rarity.RARE,
            "placeholder.png");

    CharacterBase ZOMBIE = new CharacterBase("zombie",
            "Zombie",
            "This was once a person…",
            "it", "it",
            new CharacterStats(10, 5, 5, 2),
            new CharacterStats(100, 7, 30, 12),
            new CharacterAttacks(Attacks.MEDIUM_PUNCH, Attacks.UNDEAD_CLAW, Attacks.UNDEAD_BITE),
            List.of(new PersistentEffect(EffectType.RESIST_DEATH, 100, Integer.MAX_VALUE, null)),
            Rarity.COMMON,
            "placeholder.png");

    CharacterBase SKELETON = new CharacterBase("skeleton",
            "Skeleton",
            "All that's left is bones and rage.",
            "it", "it",
            new CharacterStats(13, 5, 3, 1),
            new CharacterStats(130, 7, 18, 6),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.QUICK_SLASH, Attacks.UNDEAD_BITE),
            List.of(new PersistentEffect(EffectType.RESIST_DEATH, 100, Integer.MAX_VALUE, null)),
            Rarity.COMMON,
            "placeholder.png");

    CharacterBase SKELETON_ARCHER = new CharacterBase("skeleton_archer",
            "Skeleton Archer",
            "A skeleton, but look out for arrows.",
            "it", "it",
            new CharacterStats(7, 2, 8, 1),
            new CharacterStats(70, 4, 48, 6),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.UNDEAD_BITE, Attacks.ARROW),
            List.of(new PersistentEffect(EffectType.RESIST_DEATH, 100, Integer.MAX_VALUE, null)),
            Rarity.COMMON,
            "placeholder.png");

}

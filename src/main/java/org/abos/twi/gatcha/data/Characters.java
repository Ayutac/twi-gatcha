package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterStats;
import org.abos.twi.gatcha.core.InventoryKind;
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
            InventoryKind.TOKEN_ERIN,
            "erin_1.png");

    CharacterBase PISCES = new CharacterBase("pisces_pre",
            "Pisces",
            "A mage, striving to change peoples mind about his infamous passion. Seems to have a constantly running nose.",
            "he", "him",
            new CharacterStats(9, 19, 9, 7),
            new CharacterStats(90, 21, 36, 42),
            new CharacterAttacks(Attacks.BONE_DART, Attacks.INVISIBILITY, Attacks.BONE_FRACTURE),
            List.of(),
            Rarity.RARE,
            InventoryKind.TOKEN_PISCES,
            "pisces_pre.png");

    CharacterBase PISCES_CRELER = new CharacterBase("pisces_post",
            "Pisces Jealnet, [Deathbane Necromancer]",
            "The necromancer of the new Horns of Hammerad. He fears Wistram no longer.",
            "he", "him",
            new CharacterStats(10, 23, 14, 9),
            new CharacterStats(100, 25, 72, 54),
            new CharacterAttacks(Attacks.DEATH_BOLT, Attacks.TURN_SKELETON, Attacks.CONSTANT_FOE_UNDEAD),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_PISCES_CRELER,
            "placeholder_gold.png");

    CharacterBase CERIA = new CharacterBase("ceria_pre",
            "Ceria Springwalker",
            "The Ice mage and Captain of the new Horns of Hammerad.",
            "she", "her",
            new CharacterStats(10, 9, 10, 7),
            new CharacterStats(100, 11, 60, 42),
            new CharacterAttacks(Attacks.ICE_SHARD, Attacks.FROST_ARMOR, Attacks.FROZEN_WIND),
            List.of(),
            Rarity.RARE,
            InventoryKind.TOKEN_CERIA,
            "ceria_pre.png");

    CharacterBase CERIA_CRELER = new CharacterBase("ceria_post",
            "Ceria Springwalker, [Arctic Cyromancer]",
            "The Ice mage and Captain of the new Horns of Hammerad.",
            "she", "her",
            new CharacterStats(15, 10, 13, 9),
            new CharacterStats(150, 12, 78, 54),
            new CharacterAttacks(Attacks.ICE_SPRAY, Attacks.ICE_SPIKE, Attacks.BATTLEFIELD_OF_THE_FROZEN_WORLD),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_CERIA_CRELER,
            "placeholder_gold.png");

    CharacterBase KSMVR = new CharacterBase("ksmvr_pre",
            "Ksmvr",
            "Former Prognugator of the Free Hive. Facing the shame of his failings, he now learns to fit in society.",
            "he", "him",
            new CharacterStats(10, 15, 9, 9),
            new CharacterStats(100, 17, 54, 54),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.QUICK_MOVEMENT, Attacks.SIDE_STEP),
            List.of(),
            Rarity.RARE,
            InventoryKind.TOKEN_KSMVR,
            "ksmvr_pre.png");

    CharacterBase KSMVR_CRELER = new CharacterBase("ksvmr_post",
            "Ksmvr, [Skirmisher]",
            "Former Prognugator of the Free Hive. He would give anything for his team.",
            "he", "him",
            new CharacterStats(10, 18, 11, 13),
            new CharacterStats(100, 20, 66, 78),
            new CharacterAttacks(Attacks.FAST_RELOADING_CROSSBOW_SHOT, Attacks.THREEFOLD_SHOT, Attacks.KNUCKLES_OF_IRON),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_KSMVR_CRELER,
            "placeholder_gold.png");

    CharacterBase YVLON = new CharacterBase("yvlon_pre",
            "Yvlon Byres",
            "The young daughter of House Byres, wielding silver and bland conversations against evil.",
            "she", "her",
            new CharacterStats(16, 5, 9, 16),
            new CharacterStats(160, 7, 54, 96),
            new CharacterAttacks(Attacks.QUICK_SLASH, Attacks.KEEN_EDGE, Attacks.IGNORE_PAIN),
            List.of(),
            Rarity.RARE,
            InventoryKind.TOKEN_YVLON,
            "yvlon_pre.png");

    CharacterBase YVLON_CRELER = new CharacterBase("yvlon_post",
            "Yvlon Byres, [Silversteel Armsmistress]",
            "The young daughter of House Byres, wielding silver and bland conversations against evil.",
            "she", "her",
            new CharacterStats(18, 5, 10, 20),
            new CharacterStats(180, 7, 60, 120),
            new CharacterAttacks(Attacks.AF_DUELIST, Attacks.AF_TELESCOPING, Attacks.AF_RAZORKIND),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_YVLON_CRELER,
            "placeholder_gold.png");

    CharacterBase RELC = new CharacterBase("relc",
            "Relc",
            "A senior guardsman in Liscor.",
            "he", "him",
            new CharacterStats(12, 20, 12, 17),
            new CharacterStats(120, 22, 72, 102),
            new CharacterAttacks(Attacks.RELC_PUNCH, Attacks.RELC_HEADBUTT, Attacks.TRIPLE_THRUST),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_RELC,
            "relc.png");

    CharacterBase KLBKCH = new CharacterBase("klbkch",
            "Klbkch",
            "A senior guardsman in Liscor.",
            "he", "him",
            new CharacterStats(10, 17, 14, 11),
            new CharacterStats(100, 19, 84, 66),
            new CharacterAttacks(Attacks.WIDE_SWEEP, Attacks.ARMOR_PIERCING_BLOW, Attacks.ANTI_MAGIC_SLASH),
            List.of(),
            Rarity.SUPER_RARE,
            InventoryKind.TOKEN_KLBKCH,
            "klbkch.png");

    CharacterBase ZEL = new CharacterBase("zel",
            "Zel",
            "The legendary Tidebreaker himself.",
            "he", "him",
            new CharacterStats(15, 12, 15, 18),
            new CharacterStats(150, 14, 90, 108),
            new CharacterAttacks(Attacks.ANTI_MAGIC_CLAW, Attacks.TAIL_SLAP, Attacks.TIDEBREAKER),
            List.of(),
            Rarity.ULTRA_RARE,
            InventoryKind.TOKEN_ZEL,
            "zel.png");

    CharacterBase RYOKA = new CharacterBase("ryoka_1",
            "Ryoka Griffin",
            "An aspiring City Runner from another world.",
            "she", "her",
            new CharacterStats(10, 12, 5, 4),
            new CharacterStats(100, 14, 30, 24),
            new CharacterAttacks(Attacks.KARATE_KICK, Attacks.FLASHBANG, Attacks.TRIPVINE_BAG),
            List.of(),
            Rarity.COMMON,
            InventoryKind.TOKEN_RYOKA,
            "ryoka_1.png");

    CharacterBase TROYDEL = new CharacterBase("troydel",
            "Troydel",
            "Prime member of the UGH.",
            "he", "him",
            new CharacterStats(8, 4, 4, 3),
            new CharacterStats(80, 6, 24, 18),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.COMPLAIN, Attacks.HIDE),
            List.of(),
            Rarity.COMMON,
            InventoryKind.TOKEN_TROYDEL,
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
            null,
            "zombie.png");

    CharacterBase SKELETON = new CharacterBase("skeleton",
            "Skeleton",
            "All that's left is bones and rage.",
            "it", "it",
            new CharacterStats(13, 5, 3, 1),
            new CharacterStats(130, 7, 18, 6),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.QUICK_SLASH, Attacks.UNDEAD_BITE),
            List.of(new PersistentEffect(EffectType.RESIST_DEATH, 100, Integer.MAX_VALUE, null)),
            Rarity.COMMON,
            null,
            "skeleton.png");

    CharacterBase SKELETON_ARCHER = new CharacterBase("skeleton_archer",
            "Skeleton Archer",
            "A skeleton, but look out for arrows.",
            "it", "it",
            new CharacterStats(7, 2, 8, 1),
            new CharacterStats(70, 4, 48, 6),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.UNDEAD_BITE, Attacks.ARROW),
            List.of(new PersistentEffect(EffectType.RESIST_DEATH, 100, Integer.MAX_VALUE, null)),
            Rarity.COMMON,
            null,
            "skeleton.png");

    CharacterBase BABY_CRELER = new CharacterBase("baby_creler",
            "Baby Creler",
            "Small but vicious, a Baby Creler can still be a dangerous threat if not handled carefully.",
            "it", "it",
            new CharacterStats(5, 5, 7, 4),
            new CharacterStats(30, 30, 35, 24),
            new CharacterAttacks(Attacks.CRELER_BITE, Attacks.CHITIN_CHARGE, Attacks.ADHESIVE_SPRAY),
            List.of(),
            Rarity.COMMON,
            null,
            "baby_creler.png");
    CharacterBase JUNIOR_CRELER = new CharacterBase("junior_creler",
            "Junior Creler",
            "The Juvenile Creler is a dangerous adversary, with more developed weapons and aggressive instincts.",
            "it", "it",
            new CharacterStats(9, 15, 12, 8),
            new CharacterStats(36, 60, 48, 32),
            new CharacterAttacks(Attacks.MANDIBLE_CRUSH, Attacks.CARAPACE_BASH, Attacks.WEB_TRAP),
            List.of(),
            Rarity.COMMON,
            null,
            "young_creler.png");
//
    CharacterBase ADULT_CRELER = new CharacterBase("adult_creler",
            "Adult Creler",
            "The Adult Creler is a nightmarish creature, combining deadly offense with monstrous resilience.",
            "it", "it",
            new CharacterStats(20, 13, 16, 15),
            new CharacterStats(60, 39, 48, 45),
            new CharacterAttacks(Attacks.REND_AND_TEAR, Attacks.ARMOR_SHRED, Attacks.HORRIFIC_ROAR),
            List.of(),
            Rarity.COMMON,
            null,
            "adult_creler.png");
}
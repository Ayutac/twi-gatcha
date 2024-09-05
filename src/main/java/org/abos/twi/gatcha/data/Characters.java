package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.CharacterClass;
import org.abos.twi.gatcha.core.CharacterStats;
import org.abos.twi.gatcha.core.Rarity;

public interface Characters {

    CharacterBase ERIN = new CharacterBase(
            "Erin Solstice",
            "A girl lost in another world.",
            CharacterClass.SUPPORT,
            new CharacterStats(10, 8, 4, 3),
            new CharacterAttacks(Attacks.MINOTAUR_PUNCH, Attacks.UNERRING_KNIFE_THROW, Attacks.PASTA),
            Rarity.RARE);

    CharacterBase ZOMBIE = new CharacterBase(
            "Zombie",
            "This was once a person…",
            CharacterClass.MELEE,
            new CharacterStats(10, 5, 5, 2),
            new CharacterAttacks(Attacks.MEDIUM_PUNCH, Attacks.UNDEAD_CLAW, Attacks.UNDEAD_BITE),
            Rarity.COMMON);

    CharacterBase SKELETON = new CharacterBase(
            "Skeleton",
            "All that's left is bones and rage.",
            CharacterClass.MELEE,
            new CharacterStats(13, 5, 3, 1),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.QUICK_SLASH, Attacks.UNDEAD_BITE),
            Rarity.COMMON);

    CharacterBase SKELETON_ARCHER = new CharacterBase(
            "Skeleton Archer",
            "All that's left is bones and rage.",
            CharacterClass.RANGED,
            new CharacterStats(7, 2, 8, 1),
            new CharacterAttacks(Attacks.WEAK_PUNCH, Attacks.UNDEAD_BITE, Attacks.ARROW),
            Rarity.COMMON);

}

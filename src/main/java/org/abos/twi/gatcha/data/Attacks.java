package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.battle.TeamKind;
import org.abos.twi.gatcha.core.effect.ApplicableEffect;
import org.abos.twi.gatcha.core.effect.EffectType;

import java.util.List;

public interface Attacks {

    Attack WEAK_PUNCH = new Attack(
            "Weak Punch",
            "This punch doesn't have much strength to it.",
            1, 1, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_BLUNT, 1, 0, 0, 0, 1d, null, null, null)));

    Attack MEDIUM_PUNCH = new Attack(
            "Medium Punch",
            "Punch like an average person.",
            1, 1, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_BLUNT, 2, 0, 0, 0, 1d, null, null, null)));

    Attack MINOTAUR_PUNCH = new Attack(
            "[Minotaur Punch]",
            "Punch like a Minotaur.",
            1, 1, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_BLUNT, 3, 0, 0, 0, 1d, null, null, null)));

    Attack UNERRING_KNIFE_THROW = new Attack(
            "Unerring Knife Throw",
            "[Unerring Throw] with a knife.",
            2, 4, 2,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 2, 0, 0, 0, 1d, null, null, null)));

    Attack PASTA = new Attack(
            "Erin's lunch special",
            "Pasta with a glass of Blue Fruit Juice.",
            1, 1, 2,
            List.of(new ApplicableEffect(EffectType.HEALING, 2, 0, 0, 0, 1d, null, null, null)));

    Attack QUICK_SLASH = new Attack(
            "Quick Slash",
            "Quick slash with a sword or something similar.",
            1, 1, 2,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 3, 0, 0, 0, 1d, null, null, null)));

    Attack ARROW = new Attack(
            "Shoot Arrow",
            "Shoot a regular arrow at your target.",
            2, 6, 3,
            List.of(new ApplicableEffect(EffectType.DAMAGE_PIERCE, 3, 0, 0, 0, 1d, null, null, null)));

    Attack BONE_DART = new Attack(
            "[Bone Dart]",
            "Shoot a bit of sharp bone at your target.",
            2, 4, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 2, 0, 0, 0, 1d, null, null, null)));

    Attack BONE_FRACTURE = new Attack(
            "Bone Fracture",
            "Directly damages the bones, bypassing armor.",
            1, 1, 5,
            List.of(new ApplicableEffect(EffectType.DAMAGE_DEATH, 5, 0, 0, 0, 1d, null, null, null)));

    Attack DEATH_BOLT = new Attack(
            "[Death Bolt]",
            "Fires a bolt of potent death mana, dealing minor damage but bypassing the opponents defense and armor.",
            2, 4, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_DEATH, 10, 0, 0, 0, 1d, null, null, null)));

    Attack ICE_SHARD = new Attack(
            "[Ice Shard]",
            "Shoot a frozen bit of water at your target.",
            2, 4, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 1, 0, 0, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.DAMAGE_FROST, 1, 0, 0, 0, 1d, null, null, null)));

    Attack FROST_ARMOR = new Attack(
            "Frost Armor",
            "Cover yourself in a layer of ice.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.BUFF_DEFENSE, 5, 3, 0, 0, 1d, null, null, null)));

    Attack FROZEN_WIND = new Attack(
            "Frozen Wind",
            "Shoot cold wind at your targets.",
            2, 4, 5,
            List.of(new ApplicableEffect(EffectType.DAMAGE_FROST, 2, 0, 1, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.DEBUFF_SPEED, 2, 3, 1, 0, 1d, null, null, null)));

    Attack ICE_SPRAY = new Attack(
            "Ice Spray",
            "Shot a spray of ice at your target",
            1, 3, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 1, 0, 1, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.DAMAGE_FROST, 1, 0, 1, 0, 1d, null, null, null)));

    Attack ICE_SPIKE = new Attack(
            "[Ice Spike]",
            "Shoot a sharp piece of ice at your target.",
            2, 5, 2,
            List.of(new ApplicableEffect(EffectType.DAMAGE_PIERCE, 3, 0, 0, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.DAMAGE_FROST, 1, 0, 0, 0, 1d, null, null, null)));

    Attack BATTLEFIELD_OF_THE_FROZEN_WORLD = new Attack(
            "Battlefield of the Frozen World",
            "Freezes the entire battlefield.",
            0, 0, 6,
            List.of(new ApplicableEffect(EffectType.DAMAGE_FROST, 5, 0, Integer.MAX_VALUE, 0, 1d, null, null, List.of(TeamKind.ENEMY)),
                    new ApplicableEffect(EffectType.DEBUFF_SPEED, 5, 4, Integer.MAX_VALUE, 0, 1d, null, null, List.of(TeamKind.ENEMY))));

    Attack QUICK_MOVEMENT = new Attack(
            "Quick Movement",
            "Be faster for a while.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.BUFF_SPEED, 7, 3, 0, 0, 1d, null, null, null)));

    Attack SIDE_STEP = new Attack(
            "Side Step",
            "Dodge the next enemy attack.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.INVULNERABILITY, 0, 3, 0, 0, 1d, null, null, null)));

    Attack IGNORE_PAIN = new Attack(
            "Ignore Pain",
            "Just ignore it!",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.BUFF_HEALTH, 15, 3, 0, 0, 1d, null, null, null)));

    Attack KEEN_EDGE = new Attack(
            "Keen Edge",
            "Use the sharp end of your weapon.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.BUFF_ATTACK, 5, 3, 0, 0, 1d, null, null, null)));

    Attack KARATE_KICK = new Attack(
            "Karate Kick",
            "Delivers a powerful kick aimed at taking down a target.",
            1, 1, 1,
            List.of(new ApplicableEffect(EffectType.DAMAGE_BLUNT, 3, 0, 0, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.STUN, 0, 1, 0, 0, 0.1, null, null, null)));

    Attack FLASHBANG = new Attack(
            "Flashbang",
            "Use light and sound to temporarily blind and disorient enemies.",
            1, 1, 2,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SOUND, 2, 0, 1, 0, 1d, null, null, List.of(TeamKind.ENEMY)),
                    new ApplicableEffect(EffectType.LOWER_ACCURACY, 10, 2, 1, 0, 1d, null, null, List.of(TeamKind.ENEMY))));

    Attack TRIPVINE_BAG = new Attack(
            "Tripvine Bag",
            "A small bag filled with tripvines to entangle people.",
            1, 3, 3,
            List.of(new ApplicableEffect(EffectType.DAMAGE_BLUNT, 1, 0, 1, 0, 1d, null, null, null),
                    new ApplicableEffect(EffectType.DEBUFF_SPEED, 10, 2, 1, 0, 1d, null, null, null)));

    Attack INVISIBILITY = new Attack(
            "[Invisibility]",
            "Become invisible.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.INVISIBILITY, 0, 3, 0, 0, 1d, null, null, null)));

    Attack TURN_SKELETON = new Attack(
            "Turn Skeleton",
            "Turn a skeleton friendly.",
            1, 3, 5,
            List.of(new ApplicableEffect(EffectType.TURN_FRIENDLY, 0, 3, 0, 0, 1d, null, Groups.WEAK_SKELETONS_ID, null)));

    Attack CONSTANT_FOE_UNDEAD = new Attack(
            "Constant Foe (Undead)",
            "Increases Attack against undead enemies.",
            0, 0, 5,
            List.of(new ApplicableEffect(EffectType.BUFF_ATTACK, 15, 3, 0, 0, 1d, null, Groups.UNDEAD_ID, null)));

    Attack UNDEAD_CLAW = new Attack(
            "Undead Claw",
            "Attack with a foul claw.",
            1, 1, 2,
            List.of(new ApplicableEffect(EffectType.DAMAGE_SLASH, 3, 0, 0, 0, 1d, null, null, null)));

    Attack UNDEAD_BITE = new Attack(
            "Undead Bite",
            "A foul bite.",
            1, 1, 3,
            List.of(new ApplicableEffect(EffectType.DAMAGE_PIERCE, 4, 0, 0, 0, 1d, null, null, null)));

}

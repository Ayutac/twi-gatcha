package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.effect.AoeAttackEffect;
import org.abos.twi.gatcha.core.effect.AoeDurationAttackEffect;
import org.abos.twi.gatcha.core.effect.DurationAttackEffect;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.abos.twi.gatcha.core.effect.SimpleAttackEffect;

import java.util.List;

public interface Attacks {

    Attack WEAK_PUNCH = new Attack(
            "Weak Punch",
            "This punch doesn't have much strength to it.",
            1, 1, 1,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_BLUNT, 1)));

    Attack MEDIUM_PUNCH = new Attack(
            "Medium Punch",
            "Punch like an average person.",
            1, 1, 1,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_BLUNT, 2)));

    Attack MINOTAUR_PUNCH = new Attack(
            "[Minotaur Punch]",
            "Punch like a Minotaur.",
            1, 1, 1,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_BLUNT, 3)));

    Attack UNERRING_KNIFE_THROW = new Attack(
            "Unerring Knife Throw",
            "[Unerring Throw] with a knife.",
            2, 4, 2,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_SLASH, 2)));

    Attack PASTA = new Attack(
            "Erin's lunch special",
            "Pasta with a glass of Blue Fruit Juice.",
            1, 1, 2,
            List.of(new SimpleAttackEffect(EffectType.HEALING, 2)));

    Attack QUICK_SLASH = new Attack(
            "Quick Slash",
            "Quick slash with a sword or something similar.",
            1, 1, 2,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_SLASH, 3)));

    Attack ARROW = new Attack(
            "Shoot Arrow",
            "Shoot a regular arrow at your target.",
            2, 6, 3,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_PIERCE, 3)));

    Attack BONE_DART = new Attack(
            "[Bone Dart]",
            "Shoot a bit of sharp bone at your target.",
            2, 4, 1,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_SLASH, 2)));

    Attack BONE_FRACTURE = new Attack(
            "Bone Fracture",
            "Directly damages the bones, bypassing armor.",
            1, 1, 5,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_IGNORES_ARMOR, 5)));

    Attack ICE_SHARD = new Attack(
            "[Ice Shard]",
            "Shoot a frozen bit of water at your target.",
            2, 4, 1,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_SLASH, 1),
                    new SimpleAttackEffect(EffectType.DAMAGE_FROST, 1)));

    Attack FROST_ARMOR = new Attack(
            "Frost Armor",
            "Cover yourself in a layer of ice.",
            0, 0, 5,
            List.of(new DurationAttackEffect(EffectType.BUFF_DEFENSE, 5, 3)));

    Attack FROZEN_WIND = new Attack(
            "Frozen Wind",
            "Cover yourself in a layer of ice.",
            2, 4, 5,
            List.of(new AoeAttackEffect(EffectType.DAMAGE_FROST, 2, 0, 1),
                    new AoeDurationAttackEffect(EffectType.DEBUFF_SPEED, 2, 3, 0, 1)));

    Attack QUICK_MOVEMENT = new Attack(
            "Quick Movement",
            "Be faster for a while.",
            0, 0, 5,
            List.of(new DurationAttackEffect(EffectType.BUFF_SPEED, 7, 3)));

    Attack SIDE_STEP = new Attack(
            "Side Step",
            "Dodge the next enemy attack.",
            0, 0, 5,
            List.of(new SimpleAttackEffect(EffectType.INVULNERABILITY, 3)));

    Attack IGNORE_PAIN = new Attack(
            "Ignore Pain",
            "Just ignore it!",
            0, 0, 5,
            List.of(new DurationAttackEffect(EffectType.BUFF_HEALTH, 15, 3)));

    Attack KEEN_EDGE = new Attack(
            "Keen Edge",
            "Use the sharp end of your weapon.",
            0, 0, 5,
            List.of(new DurationAttackEffect(EffectType.BUFF_ATTACK, 5, 3)));

    Attack INVISIBILITY = new Attack(
            "[Invisibility]",
            "Become invisible.",
            0, 0, 5,
            List.of(new SimpleAttackEffect(EffectType.INVISIBILITY, 3)));

    Attack UNDEAD_CLAW = new Attack(
            "Claw",
            "Attack with a foul claw.",
            1, 1, 2,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_SLASH, 3)));

    Attack UNDEAD_BITE = new Attack(
            "Bite",
            "A foul bite.",
            1, 1, 3,
            List.of(new SimpleAttackEffect(EffectType.DAMAGE_PIERCE, 4)));

}

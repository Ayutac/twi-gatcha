package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.abos.twi.gatcha.core.effect.SimpleEffect;

import java.util.List;

public interface Attacks {

    Attack WEAK_PUNCH = new Attack(
            "Weak Punch",
            "This punch doesn't have much strength to it.",
            1, 1, 1,
            List.of(new SimpleEffect(EffectType.DAMAGE_BLUNT, 1)));

    Attack MEDIUM_PUNCH = new Attack(
            "Medium Punch",
            "Punch like an average person.",
            1, 1, 1,
            List.of(new SimpleEffect(EffectType.DAMAGE_BLUNT, 2)));

    Attack MINOTAUR_PUNCH = new Attack(
            "[Minotaur Punch]",
            "Punch like a Minotaur.",
            1, 1, 1,
            List.of(new SimpleEffect(EffectType.DAMAGE_BLUNT, 3)));

    Attack UNERRING_KNIFE_THROW = new Attack(
            "Unerring Knife Throw",
            "[Unerring Throw] with a knife.",
            2, 4, 2,
            List.of(new SimpleEffect(EffectType.DAMAGE_SLASH, 2)));

    Attack PASTA = new Attack(
            "Erin's lunch special",
            "Pasta with a glass of Blue Fruit Juice.",
            1, 1, 2,
            List.of(new SimpleEffect(EffectType.HEALING, 2)));

    Attack QUICK_SLASH = new Attack(
            "Quick Slash",
            "Quick slash with a sword or something similar.",
            1, 1, 2,
            List.of(new SimpleEffect(EffectType.DAMAGE_SLASH, 3)));

    Attack ARROW = new Attack(
            "Shoot Arrow",
            "Shoot a regular arrow at your target.",
            2, 6, 3,
            List.of(new SimpleEffect(EffectType.DAMAGE_PIERCE, 3)));

    Attack UNDEAD_CLAW = new Attack(
            "Claw",
            "Attack with a foul claw.",
            1, 1, 2,
            List.of(new SimpleEffect(EffectType.DAMAGE_SLASH, 3)));

    Attack UNDEAD_BITE = new Attack(
            "Bite",
            "A foul bite.",
            1, 1, 3,
            List.of(new SimpleEffect(EffectType.DAMAGE_PIERCE, 4)));

}

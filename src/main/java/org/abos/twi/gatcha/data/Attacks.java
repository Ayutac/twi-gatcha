package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.battle.EffectType;
import org.abos.twi.gatcha.core.battle.SimpleEffect;

import java.util.List;

public interface Attacks {

    Attack MINOTAUR_PUNCH = new Attack(
            "[Minotaur Punch]",
            "Punch like a Minotaur.",
            1, 1, 1,
            List.of(new SimpleEffect(EffectType.DAMAGE, 3)));

    Attack UNERRING_KNIFE_THROW = new Attack(
            "Unerring Knife Throw",
            "[Unerring Throw] with a knife.",
            3, 5, 2,
            List.of(new SimpleEffect(EffectType.DAMAGE, 2)));

    Attack PASTA = new Attack(
            "Erin's lunch special",
            "Pasta with a glass of Blue Fruit Juice.",
            3, 5, 2,
            List.of(new SimpleEffect(EffectType.HEALING, 2)));

}

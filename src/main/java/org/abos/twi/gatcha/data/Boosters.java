package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.Booster;

import java.util.List;

public interface Boosters {

    Booster LISCOR_CLASSIC = new Booster(
            "Classic Liscor",
            "Summon from Liscor shortly after Erin arrived!",
            List.of(Characters.ERIN));

    Booster ADVENTURER_GUILD = new Booster(
            "Adventurer Guild",
            "Summon from an Adventurer's Guild",
            List.of(Characters.PISCES, Characters.CERIA, Characters.KSMVR, Characters.YVLON));

}

package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.Booster;
import org.abos.twi.gatcha.core.InventoryKind;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.Trigger;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

public interface Boosters {

    Booster DEMO_STARTER = new Booster(
        "Demo Starter",
        "Summon from most characters available in the demo!",
        List.of(Characters.ERIN, Characters.RYOKA, Characters.TROYDEL, Characters.PISCES, Characters.CERIA,
                Characters.KSMVR, Characters.YVLON, Characters.RELC, Characters.KLBKCH, Characters.ZEL),
        new InventoryMap(InventoryKind.MAGICORE, 25),
        Trigger.ALWAYS);

//    Booster LISCOR_CLASSIC = new Booster(
//            "Classic Liscor",
//            "Summon from Liscor shortly after Erin arrived!",
//            List.of(Characters.ERIN),
//            new InventoryMap(InventoryKind.MAGICORE, 10),
//            Trigger.ALWAYS);
//
//    Booster ADVENTURER_GUILD = new Booster(
//            "Adventurer Guild",
//            "Summon from an Adventurer's Guild",
//            List.of(Characters.PISCES, Characters.CERIA, Characters.KSMVR, Characters.YVLON),
//            new InventoryMap(InventoryKind.MAGICORE, 50),
//            player -> ZonedDateTime.now(ZoneId.of("UTC")).get(ChronoField.DAY_OF_WEEK) == DayOfWeek.THURSDAY.getValue());

}

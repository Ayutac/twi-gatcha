package org.abos.twi.gatcha.data;

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
            Rarity.RARE);

}

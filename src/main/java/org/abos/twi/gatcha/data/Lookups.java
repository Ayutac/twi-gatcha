package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.Booster;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.battle.Attack;
import org.abos.twi.gatcha.core.battle.Level;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface Lookups {

    // TODO make the lookups only changeable here
    Map<String, Attack> ATTACKS = new HashMap<>();
    Map<String, Booster> BOOSTERS = new HashMap<>();
    Map<String, CharacterBase> CHARACTERS = new HashMap<>();
    Map<String, Level> LEVELS = new HashMap<>();

    static void registerAll() throws IllegalAccessException {
        for (final Field attackField : Attacks.class.getFields()) {
            ((Attack) attackField.get(null)).registerWith(ATTACKS);
        }
        for (final Field boosterField : Boosters.class.getFields()) {
            ((Booster) boosterField.get(null)).registerWith(BOOSTERS);
        }
        for (final Field characterField : Characters.class.getFields()) {
            ((CharacterBase)characterField.get(null)).registerWith(CHARACTERS);
        }
        for (final Field levelField : Levels.class.getFields()) {
            ((Level)levelField.get(null)).registerWith(LEVELS);
        }
    }

}

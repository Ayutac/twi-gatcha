package org.abos.twi.gatcha.data;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.InventoryKind;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.battle.Level;
import org.abos.twi.gatcha.core.battle.Wave;
import org.abos.twi.gatcha.core.battle.WaveUnit;
import org.abos.twi.gatcha.core.battle.ai.DirectRandomAttacker;
import org.abos.twi.gatcha.core.battle.ai.SlowWanderer;

import java.util.List;
import java.util.Set;

public interface Levels {

    Level ONE_ONE = new Level(3, 2,
            List.of(),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(2, 1), SlowWanderer::new)))),
            Set.of(new Vec2i(0, 0)),
            new InventoryMap(new InventoryKind[]{InventoryKind.GOLD}, new int[]{1}));

    Level ONE_TWO = new Level(3, 2,
            List.of(),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(2, 1), DirectRandomAttacker::new)))),
            Set.of(new Vec2i(0, 0)),
            new InventoryMap(new InventoryKind[]{InventoryKind.GOLD}, new int[]{5}));
}

package org.abos.twi.gatcha.data;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.InventoryKind;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.battle.Level;
import org.abos.twi.gatcha.core.battle.Terrain;
import org.abos.twi.gatcha.core.battle.TerrainType;
import org.abos.twi.gatcha.core.battle.Wave;
import org.abos.twi.gatcha.core.battle.WaveUnit;
import org.abos.twi.gatcha.core.battle.ai.DirectRandomAttacker;
import org.abos.twi.gatcha.core.battle.ai.SlowWanderer;

import java.util.List;
import java.util.Set;

public interface Levels {

    Level ZERO_ONE = new Level("0-1", 3, 2,
            List.of(),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.BABY_CRELER), new Vec2i(2, 1), DirectRandomAttacker::new)))),
            Set.of(new Vec2i(0, 0)),
            new InventoryMap(InventoryKind.GOLD, 1),
            1,
            Set.of());

    Level ZERO_TWO = new Level("0-2", 3, 2,
            List.of(),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.JUNIOR_CRELER), new Vec2i(2, 1), DirectRandomAttacker::new)))),
            Set.of(new Vec2i(0, 0)),
            new InventoryMap(InventoryKind.GOLD, 5),
            5,
            Set.of(ZERO_ONE));

    Level ZERO_THREE = new Level("0-3", 4, 3,
            List.of(),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(3, 2), SlowWanderer::new))),
                   new Wave(1, List.of(
                    new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(3, 2), DirectRandomAttacker::new)))),
            Set.of(new Vec2i(0, 0), new Vec2i(0, 1), new Vec2i(0, 2)),
            new InventoryMap(InventoryKind.GOLD, 5),
            5,
            Set.of(ZERO_TWO));

    Level ZERO_FOUR = new Level("0-4", 4, 3,
            List.of(new Terrain(TerrainType.BLOCKED, new Vec2i(1, 0)),
                    new Terrain(TerrainType.BLOCKED, new Vec2i(2, 0)),
                    new Terrain(TerrainType.HILL, new Vec2i(1, 1)),
                    new Terrain(TerrainType.BLOCKED, new Vec2i(1, 2)),
                    new Terrain(TerrainType.BLOCKED, new Vec2i(2, 2))),
            Set.of(new Wave(0, List.of(
                    new WaveUnit(new CharacterModified(Characters.ZOMBIE), new Vec2i(3, 2), DirectRandomAttacker::new)))),
            Set.of(new Vec2i(0, 0), new Vec2i(0, 1), new Vec2i(0, 2)),
            new InventoryMap(InventoryKind.GOLD, 5),
            5,
            Set.of(ZERO_THREE));
}

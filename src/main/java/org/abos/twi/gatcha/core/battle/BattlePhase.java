package org.abos.twi.gatcha.core.battle;

public enum BattlePhase {

    /**
     * After construction of the {@link Battle} and before placement. First wave takes place here.
     */
    INACTIVE,
    /**
     * When the {@link org.abos.twi.gatcha.core.Player} places their party.
     */
    PLACEMENT,
    /**
     * The {@link Battle} commences.
     */
    IN_PROGRESS,
    /**
     * The {@link Battle} is over.
     */
    DONE

}

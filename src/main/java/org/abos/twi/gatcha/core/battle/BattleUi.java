package org.abos.twi.gatcha.core.battle;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface BattleUi {

    /**
     * Returns the battle this UI listens to.
     * @return this UI's battle, not {@code null}
     */
    @NotNull Battle getBattle();

    /**
     * Gets called when a character was placed on the field.
     * @param character the character who was placed, not {@code null}
     * @param position the position the character was placed on, not {@code null}
     */
    void characterPlaced(final @NotNull CharacterInBattle character, final @NotNull Vec2i position);

    /**
     * Gets called when a character moved.
     * @param character the character who moved, not {@code null}
     * @param from where the character started from
     * @param to which field the character reached
     */
    void characterMoved(final @NotNull CharacterInBattle character, final @NotNull Vec2i from, final @NotNull Vec2i to);

    /**
     * Gets called when a character attacked another.
     * @param attacker who started the attack, not {@code null}
     * @param defender who was attacked, may be {@code null} if nothing was attacked
     * @param type the effect type applied
     * @param damage the damage (or heal amount), not negative
     */
    void characterAttacked(final @NotNull CharacterInBattle attacker, final @Nullable CharacterInBattle defender, final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int damage);

    /**
     * Gets called when a character gets defeated.
     * @param defeated the character who got defeated, not {@code null}
     */
    void characterDefeated(final @NotNull CharacterInBattle defeated);

    /**
     * Tells if it is the player's turn to move a character.
     * @return {@code true} if the player has to move a character, else {@code false}
     */
    boolean isPlayerMoving();

    /**
     * Tells if it is the player's turn to attack with a character.
     * @return {@code true} if the player has to attack with a character, else {@code false}
     */
    boolean isPlayerAttacking();

    /**
     * Tells if it is the player's turn.
     * @return {@code true} if it is the player's turn, else {@code false}
     */
    default boolean isPlayersTurn() {
        return isPlayerMoving() || isPlayerAttacking();
    }

    /**
     * Blocks on the future object until the player move is done.
     * @return a {@link Future} that will contain {@code null} when finished
     */
    CompletableFuture<Object> waitForPlayer();

    /**
     * Gets called if the player has won a stage.
     */
    void hasWon();

    /**
     * Gets called if the player has tied a stage.
     */
    void hasTied();

    /**
     * Gets called if the player has lost a stage.
     */
    void hasLost();

}

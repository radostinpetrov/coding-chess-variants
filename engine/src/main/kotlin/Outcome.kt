import players.Player

/**
 * Represents an outcome of the game
 *
 * @property message the message to show the users
 */
sealed class Outcome(open val message: String) {
    /**
     * Represents a win
     *
     * @property winner the player who won the game
     */
    data class Win(val winner: Player, override val message: String) : Outcome(message)

    /**
     * Represents a draw
     */
    data class Draw(override val message: String) : Outcome(message)
}

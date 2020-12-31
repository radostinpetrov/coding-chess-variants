import players.Player

sealed class Outcome(open val message: String) {
    data class Win(val winner: Player, override val message: String) : Outcome(message)
    data class Draw(override val message: String) : Outcome(message)
}

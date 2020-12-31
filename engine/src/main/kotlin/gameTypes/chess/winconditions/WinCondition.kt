package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import players.Player

interface WinCondition<T> {
    fun evaluate(game: @UnsafeVariance T, player: Player, moves: List<GameMove2D>): Outcome?
}

package gameTypes.chess.rules

import gameMoves.GameMove2D
import gameTypes.GameType2D
import players.Player

interface SpecialRules<out T : GameType2D> {
    fun getPossibleMoves(game: @UnsafeVariance T, player: Player, moves: MutableList<GameMove2D>)
}

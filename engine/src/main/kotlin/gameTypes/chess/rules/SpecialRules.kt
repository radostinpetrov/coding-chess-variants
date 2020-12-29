package gameTypes.chess.rules

import gameMoves.GameMove2D
import gameTypes.GameType
import players.Player

interface SpecialRules<out T : GameType> {
    fun getPossibleMoves(game: @UnsafeVariance T, player: Player, moves: MutableList<GameMove2D>)
}

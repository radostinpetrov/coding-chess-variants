package gameTypes.chess.rules

import GameMove
import gameTypes.GameType
import players.Player

interface SpecialRules<out T : GameType> {
    fun getPossibleMoves(game: @UnsafeVariance T, player: Player, moves: MutableList<GameMove>)
}

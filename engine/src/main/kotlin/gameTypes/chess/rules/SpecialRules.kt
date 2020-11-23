package main.kotlin.gameTypes.chess.rules

import main.kotlin.GameMove
import main.kotlin.gameTypes.GameType
import main.kotlin.players.Player

interface SpecialRules<out T : GameType> {
    fun getPossibleMoves(game: @UnsafeVariance T, player: Player, moves: MutableList<GameMove>)
}

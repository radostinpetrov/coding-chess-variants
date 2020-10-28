package main.kotlin

import main.kotlin.players.Player
import pieces.Piece

sealed class GameMove(open val player: Player) {
    data class BasicGameMove(val from: Coordinate, val to: Coordinate, val pieceMoved: Piece, override val player: Player, val pieceCaptured: Piece? = null, val piecePromotedTo:Piece? = null, val checkForCheck: Boolean = true) : GameMove(player)
    data class CompositeGameMove(val gameMoves: List<BasicGameMove>, override val player: Player) : GameMove(player)
}
package pieces.chess

import pieces.Piece
import players.Player

data class StandardWhitePawn(override val player: Player) : WhitePawn(player, 1, 7, listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))
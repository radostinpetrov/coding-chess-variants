package pieces.antichess

import pieces.chess.*
import players.Player

/**
 * Represents a white pawn of anti chess
 */
data class AntiChessWhitePawn(override val player: Player) : WhitePawn(player, 1, 7, listOf(Queen(player), Bishop(player), Knight(player), Rook(player), AntiChessKing(player)))

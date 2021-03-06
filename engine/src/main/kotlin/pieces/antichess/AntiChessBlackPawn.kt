package pieces.antichess

import pieces.chess.*
import players.Player

/**
 * Represents a black pawn of anti chess
 */
data class AntiChessBlackPawn(override val player: Player) : BlackPawn(player, 6, 0, listOf(Queen(player), Bishop(player), Knight(player), Rook(player), AntiChessKing(player)))

package pieces.chess

import players.Player

/**
 * Represents a black pawn in standard chess
 */
class StandardBlackPawn(override val player: Player) : BlackPawn(player, 6, 0, listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))

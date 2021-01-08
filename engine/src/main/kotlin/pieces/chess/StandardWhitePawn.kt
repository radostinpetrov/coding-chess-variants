package pieces.chess

import players.Player

/**
 * Represents a white pawn in standard chess
 */
class StandardWhitePawn(override val player: Player) : WhitePawn(player, 1, 7, listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))
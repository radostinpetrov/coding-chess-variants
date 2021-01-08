package pieces.chess

import players.Player

/**
 * Represents a black pawn in Capablanca chess
 */
class CapablancaBlackPawn(override val player: Player) : BlackPawn(
    player, 6, 0,
    listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
)

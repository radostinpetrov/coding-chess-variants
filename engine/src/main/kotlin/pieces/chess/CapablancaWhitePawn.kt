package pieces.chess

import players.Player

/**
 * Represents a white pawn in Capablanca chess
 */
class CapablancaWhitePawn(override val player: Player) : WhitePawn(
    player, 1, 7,
    listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
)

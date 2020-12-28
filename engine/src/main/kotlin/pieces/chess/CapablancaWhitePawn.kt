package pieces.chess

import players.Player

data class CapablancaWhitePawn(override val player: Player) : WhitePawn(
    player, 1, 7,
    listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
)

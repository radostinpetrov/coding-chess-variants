package pieces.chess

import players.Player

data class CapablancaBlackPawn(override val player: Player) : BlackPawn(
    player, 6, 0,
    listOf(Queen(player), Bishop(player), Knight(player), Rook(player), Marshal(player), Cardinal(player))
)

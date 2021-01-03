package pieces.chess

import players.Player

class StandardBlackPawn(override val player: Player) : BlackPawn(player, 6, 0, listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))

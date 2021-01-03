package pieces.chess

import players.Player

class StandardWhitePawn(override val player: Player) : WhitePawn(player, 1, 7, listOf(Queen(player), Bishop(player), Knight(player), Rook(player)))
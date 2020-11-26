package pieces.chess

import players.Player

data class GrandBlackPawn(override val player: Player) : BlackPawn(player, 7)

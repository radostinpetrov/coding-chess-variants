package pieces.chess

import players.Player

data class StandardBlackPawn(override val player: Player) : BlackPawn(player, 6)
package pieces.janggi

import moveGenerators.Direction
import players.Player

data class RedSoldier(override val player: Player) : JanggiSoldier(player, Direction.NORTH)
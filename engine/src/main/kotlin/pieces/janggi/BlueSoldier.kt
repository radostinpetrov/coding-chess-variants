package pieces.janggi

import moveGenerators.Direction
import players.Player

data class BlueSoldier(override val player: Player) : JanggiSoldier(player, Direction.SOUTH)

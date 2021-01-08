package pieces.janggi

import moveGenerators.Direction
import players.Player

/**
 * Represents a blue soldier in Janggi
 */
data class BlueSoldier(override val player: Player) : JanggiSoldier(player, Direction.SOUTH)

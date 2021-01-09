package pieces.janggi

import moveGenerators.Direction
import players.Player

/**
 * Represents a red soldier in Janggi
 */
data class RedSoldier(override val player: Player) : JanggiSoldier(player, Direction.NORTH)
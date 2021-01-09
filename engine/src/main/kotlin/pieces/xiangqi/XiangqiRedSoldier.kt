package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import regions.BoxRegion
import players.Player

/**
 * Represents a red soldier in Xiangqi
 */
data class XiangqiRedSoldier(override val player: Player) :
    XiangqiSoldier(player, Direction.NORTH, SpecialRegion.blueSideRiver)

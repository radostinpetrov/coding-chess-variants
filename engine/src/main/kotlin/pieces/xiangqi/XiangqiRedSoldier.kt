package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import regions.BoxRegion
import players.Player

data class XiangqiRedSoldier(override val player: Player) :
    XiangqiSoldier(player, Direction.NORTH, BoxRegion(Coordinate2D(0, 5), Coordinate2D(9, 9)))

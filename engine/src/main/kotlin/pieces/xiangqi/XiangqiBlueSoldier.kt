package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import regions.BoxRegion
import players.Player

data class XiangqiBlueSoldier(override val player: Player) :
    XiangqiSoldier(player, Direction.SOUTH, BoxRegion(Coordinate2D(0, 0), Coordinate2D(9, 4)))

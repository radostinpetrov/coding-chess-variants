package pieces.xiangqi

import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import regions.BoxRegion
import pieces.Piece2D
import players.Player

data class XiangqiBlueElephant(override val player: Player) :
    XiangqiElephant(player, BoxRegion(Coordinate2D(0, 5), Coordinate2D(8, 9)))
package pieces.xiangqi

import moveGenerators.Direction
import players.Player

data class XiangqiBlueSoldier(override val player: Player) :
    XiangqiSoldier(player, Direction.SOUTH, SpecialRegion.redSideRiver)

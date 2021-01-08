package pieces.xiangqi

import players.Player

/**
 * Represents a blue elephant in Xiangqi
 */
data class XiangqiBlueElephant(override val player: Player) :
    XiangqiElephant(player, SpecialRegion.blueBeforeRiver)
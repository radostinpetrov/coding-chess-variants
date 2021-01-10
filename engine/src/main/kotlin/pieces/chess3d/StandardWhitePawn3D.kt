package pieces.chess3d

import coordinates.Coordinate3D
import pieces.chess.*
import players.Player
import regions.BoxRegion3D

data class StandardWhitePawn3D(override val player: Player) : WhitePawn3D(player, BoxRegion3D(Coordinate3D(0, 4, 0), Coordinate3D(4, 4, 4)), listOf())
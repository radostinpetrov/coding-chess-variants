package pieces.chess3d

import moveGenerators.Direction3D
import pieces.Piece3D
import pieces.chess.ChessPawn3D
import players.Player
import regions.Region3D

/**
 * Represents a Black Pawn in 3D chess
 */
open class BlackPawn3D(override val player: Player, val promotionRegion: Region3D, val pawnPromotions: List<Piece3D>) :
    Piece3D, ChessPawn3D(player, listOf(Direction3D.SOUTH, Direction3D.NADIR), listOf(Direction3D.SOUTH_WEST, Direction3D.SOUTH_EAST, Direction3D.NADIR_SOUTH, Direction3D.NADIR_WEST, Direction3D.NADIR_EAST), promotionRegion, pawnPromotions)

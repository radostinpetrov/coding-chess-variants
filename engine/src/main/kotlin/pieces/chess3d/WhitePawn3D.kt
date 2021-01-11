package pieces.chess3d

import moveGenerators.Direction3D
import pieces.Piece3D
import pieces.chess.ChessPawn3D
import players.Player
import regions.Region3D

/**
 * Represents a White Pawn in 3D chess
 */

open class WhitePawn3D(override val player: Player, val promotionRegion: Region3D, val pawnPromotions: List<Piece3D>) :
    Piece3D, ChessPawn3D(player, listOf(Direction3D.NORTH, Direction3D.ZENITH), listOf(Direction3D.NORTH_WEST, Direction3D.NORTH_EAST, Direction3D.ZENITH_NORTH, Direction3D.ZENITH_WEST, Direction3D.ZENITH_EAST), promotionRegion, pawnPromotions)

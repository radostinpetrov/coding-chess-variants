package pieces.chess

import moveGenerators.Direction3D
import moveGenerators.MoveGenerator3D
import pieces.Pawn
import pieces.Piece3D
import players.Player
import regions.Region3D

/**
 * Represents a Pawn in 3D chess
 *
 * @param player the player who owns the pawn
 * @param direction the direction that the pawn can move to without capturing
 * @param captureOnlyDir the direction that the pawn can move to by capturing
 * @param promotionRegion the region that the promotion can occur
 * @param pawnPromotions the pieces that the pawn can be promoted to
 */

abstract class ChessPawn3D(
    override val player: Player,
    private val direction: List<Direction3D>,
    private val captureOnlyDir: List<Direction3D>,
    private val promotionRegion: Region3D,
    private val pawnPromotions: List<Piece3D>
) :
    Pawn, Piece3D {

    override val moveGenerators: List<MoveGenerator3D>
        get() = listOf(
            MoveGenerator3D.AddPromotion(
                listOf(
                    MoveGenerator3D.Stepper3D(direction, 1),
                    MoveGenerator3D.CaptureOnly(
                        MoveGenerator3D.Stepper3D(captureOnlyDir, 1, true)
                    ),
                ),
                promotionRegion,
                pawnPromotions,
                true
            )
        )

    override fun getSymbol(): String {
        return "P"
    }
}

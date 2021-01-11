package pieces.hex

import moveGenerators.DirectionHex
import moveGenerators.MoveGeneratorHex
import pieces.Pawn
import players.Player
import regions.Region2D

/**
 * Represents a pawn in standard chess
 *
 * @param player the player who owns the pawn
 * @param direction the direction that the pawn is facing
 *        (determines the direction that pawn moves to)
 * @param startingRegion the region that the pawn starts from
 *        (used for double step move)
 * @param promotionRegion the region that the promotion can occur
 * @param pawnPromotions the pieces that the pawn can be promoted to
 */
abstract class ChessPawnHex(override val player: Player,
                            private val direction: DirectionHex,
                            private val startingRegion: Region2D,
                            private val promotionRegion: Region2D,
                            private val pawnPromotions: List<PieceHex> =
                                listOf(HexQueen(player), HexBishop(player), HexKnight(player), HexRook(player)))
    : Pawn, PieceHex {

    private val captureOnlyDir = if (direction == DirectionHex.DOWN)
        listOf(DirectionHex.DOWN_LEFT, DirectionHex.DOWN_RIGHT)
    else listOf(DirectionHex.UP_LEFT, DirectionHex.UP_RIGHT)

    override val moveGenerators: List<MoveGeneratorHex>
        get() = listOf(
            MoveGeneratorHex.Restricted(MoveGeneratorHex.Stepper(direction, 2), startingRegion),
            MoveGeneratorHex.AddPromotion(
                listOf(
                    MoveGeneratorHex.Stepper(direction, 1),
                    MoveGeneratorHex.CaptureOnly(
                        MoveGeneratorHex.Stepper(captureOnlyDir, 1, true)
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
package gameTypes.chess

import boards.Board2D
import coordinates.Coordinate2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import pieces.Pawn
import pieces.Piece2D
import pieces.chess.*
import players.Player
import regions.BoxRegion
import regions.CompositeRegion
import regions.RowRegion
import endconditions.StandardEndConditions
import utils.FenUtility

/**
 * Represents Balbo's game
 */
class BalbosGame : AbstractChess(listOf(), listOf(StandardEndConditions())) {
    override val name = "Balbo's Game"

    abstract class BalboPawn(override val player: Player, private val standardMoves: List<MoveGenerator2D>, private val promotionRegion: List<BoxRegion>, private val restrictedRegion: List<BoxRegion>) : Piece2D, Pawn {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(
            MoveGenerator2D.AddPromotion(
                standardMoves,
                CompositeRegion(promotionRegion),
                listOf(Bishop(player), Knight(player)),
                true
            ),
            MoveGenerator2D.RestrictedDestination(
                MoveGenerator2D.AddPromotion(
                    standardMoves,
                    CompositeRegion(restrictedRegion),
                    listOf(Queen(player), Rook(player)),
                    true
                ),
                CompositeRegion(restrictedRegion)
            )
        )

        override fun getSymbol(): String {
            return "P"
        }
    }


    companion object BalbosWhitePawnRegions {
        val balbosWhitePawnStandardMoves = listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH, 2), RowRegion(2)),
            MoveGenerator2D.Stepper(Direction.NORTH, 1),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true)),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true))
        )
        val balbosWhitePawnRegion1 = listOf(
            BoxRegion(Coordinate2D(4, 9), Coordinate2D(6, 9)),
            BoxRegion(Coordinate2D(3, 8), Coordinate2D(3, 8)),
            BoxRegion(Coordinate2D(7, 8), Coordinate2D(7, 8)),
            BoxRegion(Coordinate2D(2, 7), Coordinate2D(2, 7)),
            BoxRegion(Coordinate2D(8, 7), Coordinate2D(8, 7))
        )
        val balbosWhitePawnRegion2 = listOf(
            BoxRegion(Coordinate2D(4, 9), Coordinate2D(6, 9)),
            BoxRegion(Coordinate2D(3, 8), Coordinate2D(3, 8)),
            BoxRegion(Coordinate2D(7, 8), Coordinate2D(7, 8)),
        )
        val balbosBlackPawnStandardMoves = listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH, 2), RowRegion(7)),
            MoveGenerator2D.Stepper(Direction.SOUTH, 1),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true)),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true))
        )
        val balbosBlackPawnRegion1 = listOf(
            BoxRegion(Coordinate2D(4, 0), Coordinate2D(6, 0)),
            BoxRegion(Coordinate2D(3, 1), Coordinate2D(3, 1)),
            BoxRegion(Coordinate2D(7, 1), Coordinate2D(7, 1)),
            BoxRegion(Coordinate2D(2, 2), Coordinate2D(2, 2)),
            BoxRegion(Coordinate2D(8, 2), Coordinate2D(8, 2))
        )
        val balbosBlackPawnRegion2 = listOf(
            BoxRegion(Coordinate2D(4, 0), Coordinate2D(6, 0)),
            BoxRegion(Coordinate2D(3, 1), Coordinate2D(3, 1)),
            BoxRegion(Coordinate2D(7, 1), Coordinate2D(7, 1)),
        )

    }

    /**
     * Represents a white pawn in Balbo's game
     */
    data class BalboWhitePawn(override val player: Player) : BalboPawn(player, balbosWhitePawnStandardMoves, balbosWhitePawnRegion1, balbosWhitePawnRegion2)

    /**
     * Represents a black pawn in Balbo's game
     */
    data class BalboBlackPawn(override val player: Player) : BalboPawn(player, balbosBlackPawnStandardMoves, balbosBlackPawnRegion1, balbosBlackPawnRegion2)

    /**
     * Represents a region that is considered invalid
     */
    private val outOfBoundsRegion = CompositeRegion(
        listOf(
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(3, 0)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(1, 2)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(2, 1)),
            BoxRegion(Coordinate2D(0, 0), Coordinate2D(0, 3)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(3, 9)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(2, 8)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(1, 7)),
            BoxRegion(Coordinate2D(0, 9), Coordinate2D(0, 6)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(7, 9)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(8, 8)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(9, 7)),
            BoxRegion(Coordinate2D(10, 9), Coordinate2D(10, 6)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(7, 0)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(8, 1)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(9, 2)),
            BoxRegion(Coordinate2D(10, 0), Coordinate2D(10, 3)),
        )
    )
    override val board: Board2D = Board2D(10, 11, outOfBoundsRegion)

    override fun initBoard() {
        val fen = FenUtility("4kbq4/3rnbnr3/2ppppppp2/11/11/11/11/2PPPPPPP2/3RNBNR3/4KBQ4")
        fen.extendFenPiecesCaseSensitive('p', ::BalboWhitePawn, ::BalboBlackPawn)
        fen.initBoardWithFEN(board, players[0], players[1])
    }
}

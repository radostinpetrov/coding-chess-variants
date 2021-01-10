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

    /**
     * Represents a white pawn in Balbo's game
     */
    data class BalboWhitePawn(override val player: Player) : Piece2D, Pawn {
        private val standardMoves = listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.NORTH, 2), RowRegion(2)),
            MoveGenerator2D.Stepper(Direction.NORTH, 1),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_EAST, 1, true)),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.NORTH_WEST, 1, true))
        )

        override val moveGenerators: List<MoveGenerator2D> = listOf(
            MoveGenerator2D.AddPromotion(
                standardMoves,
                CompositeRegion(
                    listOf(
                        BoxRegion(Coordinate2D(4, 9), Coordinate2D(6, 9)),
                        BoxRegion(Coordinate2D(3, 8), Coordinate2D(3, 8)),
                        BoxRegion(Coordinate2D(7, 8), Coordinate2D(7, 8)),
                        BoxRegion(Coordinate2D(2, 7), Coordinate2D(2, 7)),
                        BoxRegion(Coordinate2D(8, 7), Coordinate2D(8, 7))
                    )
                ),
                listOf(Bishop(player), Knight(player)),
                true
            ),
            MoveGenerator2D.RestrictedDestination(
                MoveGenerator2D.AddPromotion(
                    standardMoves,
                    CompositeRegion(
                        listOf(
                            BoxRegion(Coordinate2D(4, 9), Coordinate2D(6, 9)),
                            BoxRegion(Coordinate2D(3, 8), Coordinate2D(3, 8)),
                            BoxRegion(Coordinate2D(7, 8), Coordinate2D(7, 8)),
                        )
                    ),
                    listOf(Queen(player), Rook(player)),
                    true
                ),
                CompositeRegion(
                    listOf(
                        BoxRegion(Coordinate2D(4, 9), Coordinate2D(6, 9)),
                        BoxRegion(Coordinate2D(3, 8), Coordinate2D(3, 8)),
                        BoxRegion(Coordinate2D(7, 8), Coordinate2D(7, 8)),
                    )
                )
            )
        )

        override fun getSymbol(): String {
            return "P"
        }
    }

    /**
     * Represents a black pawn in Balbo's game
     */
    data class BalboBlackPawn(override val player: Player) : Piece2D, Pawn {
        private val standardMoves = listOf(
            MoveGenerator2D.Restricted(MoveGenerator2D.Stepper(Direction.SOUTH, 2), RowRegion(7)),
            MoveGenerator2D.Stepper(Direction.SOUTH, 1),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_EAST, 1, true)),
            MoveGenerator2D.CaptureOnly(MoveGenerator2D.Stepper(Direction.SOUTH_WEST, 1, true))
        )

        override val moveGenerators: List<MoveGenerator2D> = listOf(
            MoveGenerator2D.AddPromotion(
                standardMoves,
                CompositeRegion(
                    listOf(
                        BoxRegion(Coordinate2D(4, 0), Coordinate2D(6, 0)),
                        BoxRegion(Coordinate2D(3, 1), Coordinate2D(3, 1)),
                        BoxRegion(Coordinate2D(7, 1), Coordinate2D(7, 1)),
                        BoxRegion(Coordinate2D(2, 2), Coordinate2D(2, 2)),
                        BoxRegion(Coordinate2D(8, 2), Coordinate2D(8, 2))
                    )
                ),
                listOf(Bishop(player), Knight(player)),
                true
            ),
            MoveGenerator2D.RestrictedDestination(
                MoveGenerator2D.AddPromotion(
                    standardMoves,
                    CompositeRegion(
                        listOf(
                            BoxRegion(Coordinate2D(4, 0), Coordinate2D(6, 0)),
                            BoxRegion(Coordinate2D(3, 1), Coordinate2D(3, 1)),
                            BoxRegion(Coordinate2D(7, 1), Coordinate2D(7, 1)),
                        )
                    ),
                    listOf(Queen(player), Rook(player)),
                    true
                ),
                CompositeRegion(
                    listOf(
                        BoxRegion(Coordinate2D(4, 0), Coordinate2D(6, 0)),
                        BoxRegion(Coordinate2D(3, 1), Coordinate2D(3, 1)),
                        BoxRegion(Coordinate2D(7, 1), Coordinate2D(7, 1)),
                    )
                )
            )
        )

        override fun getSymbol(): String {
            return "P"
        }
    }

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

        // val player1 = players[0]
        // val player2 = players[1]
        // board.addPiece(Coordinate2D(4, 0), King(player1))
        // board.addPiece(Coordinate2D(5, 0), Bishop(player1))
        // board.addPiece(Coordinate2D(6, 0), Queen(player1))
        // board.addPiece(Coordinate2D(3, 1), Rook(player1))
        // board.addPiece(Coordinate2D(4, 1), Knight(player1))
        // board.addPiece(Coordinate2D(5, 1), Bishop(player1))
        // board.addPiece(Coordinate2D(6, 1), Knight(player1))
        // board.addPiece(Coordinate2D(7, 1), Rook(player1))
        // board.addPiece(Coordinate2D(2, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(3, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(4, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(5, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(6, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(7, 2), BalboWhitePawn(player1))
        // board.addPiece(Coordinate2D(8, 2), BalboWhitePawn(player1))
        //
        // board.addPiece(Coordinate2D(4, 9), King(player2))
        // board.addPiece(Coordinate2D(5, 9), Bishop(player2))
        // board.addPiece(Coordinate2D(6, 9), Queen(player2))
        // board.addPiece(Coordinate2D(3, 8), Rook(player2))
        // board.addPiece(Coordinate2D(4, 8), Knight(player2))
        // board.addPiece(Coordinate2D(5, 8), Bishop(player2))
        // board.addPiece(Coordinate2D(6, 8), Knight(player2))
        // board.addPiece(Coordinate2D(7, 8), Rook(player2))
        // board.addPiece(Coordinate2D(2, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(3, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(4, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(5, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(6, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(7, 7), BalboBlackPawn(player2))
        // board.addPiece(Coordinate2D(8, 7), BalboBlackPawn(player2))
    }
}

package gameTypes.checkers

import endconditions.Outcome
import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import moves.Move2D
import pieces.Piece2D
import players.Player
import regions.RowRegion
import rules.ForcedCaptureRule
import endconditions.EndCondition2D

class Checkers : AbstractChess(rules = listOf(ForcedCaptureRule()), winConditions = listOf(CheckersEndCondition())) {
    override val name = "Checkers"

    class CheckersEndCondition() : EndCondition2D<AbstractChess> {
        override fun evaluate(game: AbstractChess, player: Player, moves: List<Move2D>): Outcome? {
            for (p in game.players) {
                val pieces = game.board.getPieces(player)
                if (pieces.isEmpty()) {
                    return Outcome.Win(game.players.first { it != p }, "by capturing all pieces")
                }
            }
            if (moves.isEmpty()) {
                return Outcome.Win(game.players.first { it != player }, "by no valid moves for opponent")
            }
            return null
        }
    }

    class CheckersCapture() : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<Move2D> {
            val res = mutableListOf<Move2D>()
            MoveGenerator2D.Leaper(2, 2).generate(board, coordinate, piece, player).forEach {
                val captureCoordinate = (it.from + it.to) / 2
                val pieceCaptured = board.getPiece(captureCoordinate)
                val pieceDestination = board.getPiece(it.to)
                if (pieceCaptured != null && pieceDestination == null && pieceCaptured.player != player) {
                    res.add(Move2D.SimpleMove.BasicMove(it.from, it.to, it.pieceMoved, player, pieceCaptured, captureCoordinate))
                }
            }
            return res
        }
    }

    abstract class CheckerPawn(override val player: Player, val directions: List<Direction>, val promotionRow: Int) : Piece2D {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(
                MoveGenerator2D.AddPromotion(
                    listOf(
                        MoveGenerator2D.Stepper(directions, 1),
                        CheckersCapture()
                    ),
                    RowRegion(promotionRow),
                    listOf(CheckerKing(player)),
                    true
                ),
            )
        override fun getSymbol(): String = "C"
    }

    data class WhiteChecker(override val player: Player) : CheckerPawn(player, listOf(Direction.NORTH_EAST, Direction.NORTH_WEST), 7)
    data class BlackChecker(override val player: Player) : CheckerPawn(player, listOf(Direction.SOUTH_EAST, Direction.SOUTH_WEST), 0)

    data class CheckerKing(override val player: Player) : Piece2D {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(
                MoveGenerator2D.Stepper(listOf(Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST), 1),
                CheckersCapture()
            )
        override fun getSymbol(): String = "K"
    }

    override val board: Board2D = Board2D(8, 8)

    override fun initBoard() {
        for (i in 0..2) {
            var start = if (i % 2 == 0) 0 else 1
            for (j in start..7 step 2) {
                board.addPiece(Coordinate2D(j, i), WhiteChecker(players[0]))
            }
            start = if (i % 2 == 0) 1 else 0
            for (j in start..7 step 2) {
                board.addPiece(Coordinate2D(j, i + 5), BlackChecker(players[1]))
            }
        }
    }

    override fun playerMakeMove(move: Move2D) {
        makeMove(move)
        if (!(move is Move2D.SimpleMove.BasicMove && move.pieceCaptured != null && getValidMoves().any { it is Move2D.SimpleMove.BasicMove && it.pieceCaptured != null })) {
            nextPlayer()
        }
    }
}

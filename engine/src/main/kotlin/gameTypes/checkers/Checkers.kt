package gameTypes.checkers

import endconditions.Outcome
import boards.Board2D
import coordinates.Coordinate2D
import gameTypes.chess.AbstractChess2D
import moveGenerators.Direction
import moveGenerators.MoveGenerator2D
import moves.*
import pieces.Piece2D
import players.Player
import regions.RowRegion
import rules.ForcedCaptureRule
import endconditions.EndCondition2D
import rules.SpecialRules2D

/**
 * Represents a checker game
 */
class Checkers : AbstractChess2D(rules = listOf(ForcedCaptureRule(), DoubleCaptureRule()), endConditions = listOf(CheckersEndCondition())) {
    override val name = "Checkers"

    /**
     * Represents end conditions for checker game
     */
    class CheckersEndCondition : EndCondition2D<AbstractChess2D> {
        override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {
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

    /**
     * Move generator for checker game
     */
    class CheckersCapture : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<Move2D> {
            val res = mutableListOf<Move2D>()
            MoveGenerator2D.Leaper(2, 2).generate(board, coordinate, piece, player).forEach {
                val captureCoordinate = (it.from + it.to) / 2
                val pieceCaptured = board.getPiece(captureCoordinate)
                val pieceDestination = board.getPiece(it.to)
                if (pieceCaptured != null && pieceDestination == null && pieceCaptured.player != player) {
                    res.add(BasicMove2D(it.from, it.to, it.pieceMoved, player, pieceCaptured, captureCoordinate))
                }
            }
            return res
        }
    }

    /**
     * Represents a pawn in checker game
     */
    abstract class CheckerPawn(override val player: Player, private val directions: List<Direction>, private val promotionRow: Int) : Piece2D {
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

    /**
     * Represents a white checker in checker game
     */
    data class WhiteChecker(override val player: Player) : CheckerPawn(player, listOf(Direction.NORTH_EAST, Direction.NORTH_WEST), 7)

    /**
     * Represents a black checker in checker game
     */
    data class BlackChecker(override val player: Player) : CheckerPawn(player, listOf(Direction.SOUTH_EAST, Direction.SOUTH_WEST), 0)

    /**
     * Represents a checker king in checker game (not a royal piece)
     */
    data class CheckerKing(override val player: Player) : Piece2D {
        override val moveGenerators: List<MoveGenerator2D>
            get() = listOf(
                MoveGenerator2D.Stepper(listOf(Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST), 1),
                CheckersCapture()
            )
        override fun getSymbol(): String = "K"
    }

    /**
     * Double capture rule.
     * If previous move was a capture, next move must be as well.
     */
    class DoubleCaptureRule : SpecialRules2D<AbstractChess2D> {
        override fun getPossibleMoves(game: AbstractChess2D, player: Player, moves: MutableList<Move2D>) {
            val moveLog = game.moveLog

            if (moveLog.isEmpty()) {
                return
            }

            val prevMove = moveLog.last()

            if (prevMove.player != player
                || prevMove !is BasicMove2D
                || prevMove.pieceCaptured == null) {
                return
            }

            val pieceMoved = prevMove.pieceMoved

            val pred = { it: Move2D -> it is BasicMove2D && it.pieceMoved == pieceMoved }
            if (moves.any(pred)) {
                moves.retainAll(pred)
            }
        }
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
        if (!(move is BasicMove2D && move.pieceCaptured != null && getValidMoves().any { it is BasicMove2D && it.pieceCaptured != null && it.pieceMoved == move.pieceMoved })) {
            nextPlayer()
        }
    }
}

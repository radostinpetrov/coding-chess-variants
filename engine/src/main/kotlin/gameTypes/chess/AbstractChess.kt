package gameTypes.chess

import endconditions.Outcome
import coordinates.Coordinate2D
import moves.Move2D
import moves.Move2D.CompositeMove
import moves.Move2D.SimpleMove
import moves.Move2D.SimpleMove.*
import gameTypes.GameType2D
import rules.SpecialRules2D
import endconditions.StandardEndConditions
import endconditions.EndCondition2D
import pieces.Piece2D
import pieces.Royal
import players.Player

/**
 * Represents a standard n-player game
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property winConditions the list of conditions that will end the game
 */
abstract class AbstractChess(
    val rules: List<SpecialRules2D<AbstractChess>> = listOf(),
    var winConditions: List<EndCondition2D<AbstractChess>> = listOf(
        StandardEndConditions()), startPlayer: Int = 0)
    : GameType2D {
    override val players: List<Player> = listOf(Player(), Player())
    override var playerTurn: Int = startPlayer
    // This is set as the winner when either player concedes
    private var concededWinner: Player? = null

    override var seed: Double? = null

    override val moveLog: MutableList<Move2D> = mutableListOf()

    override fun isOver(): Boolean {
        return getOutcome(getCurrentPlayer()) != null
    }

    override fun initGame() {
        board.clearBoard()
        initBoard()
    }

    override fun getOutcome(player: Player): Outcome? {
        if (concededWinner != null) {
            return Outcome.Win(player, "by enemy concede")
        }
        val moves = getValidMoves(player)
        for (wc in winConditions) {
            val outcome = wc.evaluate(this, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }

    override fun getValidMoves(player: Player): List<Move2D> {
        if (!players.contains(player)) {
            throw Exception("Not a valid player")
        }
        val possibleMoves = getPossibleMoves(player).toMutableList()
        val moves = filterForCheck(player, possibleMoves)
        return moves
    }

    private fun getPossibleMoves(player: Player): List<Move2D> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<Move2D>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }
        for (rule in rules) {
            rule.getPossibleMoves(this, player, possibleMoves)
        }
        return possibleMoves
    }

    private fun filterForCheck(player: Player, possibleMoves: List<Move2D>): List<Move2D> {
        val res = mutableListOf<Move2D>()
        for (move in possibleMoves) {
            when (move) {
                is SimpleMove -> {
                    makeMove(move)
                    if (!move.checkForCheck || !inCheck(player)) {
                        res.add(move)
                    }
                    undoMove()
                }
                is CompositeMove -> {
                    var valid = true
                    for (m in move.moves) {
                        makeMove(m)
                        if (m.checkForCheck && inCheck(player)) {
                            valid = false
                        }
                    }
                    for (m in move.moves) {
                        undoMove()
                    }
                    if (valid) {
                        res.add(move)
                    }
                }
            }
        }
        return res
    }

    override fun inCheck(player: Player): Boolean {
        val king = board.getPieces(player).find { p -> p.first.player == player && p.first is Royal } ?: return false
        val kingCoordinate = king.second
        return squareUnderAttack(kingCoordinate, player)
    }

    private fun squareUnderAttack(coordinate: Coordinate2D, player: Player): Boolean {
        val nextPlayer = players[(players.indexOf(player) + 1) % 2]
        val moves = getPossibleMoves(nextPlayer)

        for (m in moves) {
            when (m) {
                is BasicMove, is CompositeMove -> {
                    if (m.displayTo.x == coordinate.x && m.displayTo.y == coordinate.y) {
                        return true
                    }
                }
                is AddPieceMove, is RemovePieceMove -> {
                    return false
                }
            }
        }

        return false
    }

    override fun undoMove() {
        if (moveLog.size == 0) {
            return
        }
        undoMoveHelper(moveLog.removeAt(moveLog.size - 1))
    }

    private fun undoMoveHelper(move: Move2D) {
        when (move) {
            is BasicMove -> {
                undoBasicMove(move)
            }
            is AddPieceMove -> {
                board.removePiece(move.coordinate, move.piece)
            }
            is RemovePieceMove -> {
                board.addPiece(move.coordinate, move.piece)
            }
            is CompositeMove -> {
                for (m in move.moves.reversed()) {
                    undoMoveHelper(m)
                }
            }
        }
    }

    private fun undoBasicMove(move: BasicMove) {
        if (move.piecePromotedTo != null) {
            board.removePiece(move.to, move.piecePromotedTo)
        } else {
            board.removePiece(move.to, move.pieceMoved)
        }

        if (move.pieceCaptured != null) {
            board.addPiece(move.pieceCapturedCoordinate, move.pieceCaptured)
        }
        board.addPiece(move.from, move.pieceMoved)
    }

    private fun getValidMoveForPiece(pair: Pair<Piece2D, Coordinate2D>): List<Move2D> {
        val possibleMoves = mutableListOf<Move2D>()
        // validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveGenerators) {
            possibleMoves.addAll(move.generate(board, coordinate, piece, getCurrentPlayer()))
        }

        return possibleMoves
    }

    override fun makeMove(move: Move2D) {
        when (move) {
            is SimpleMove -> {
                makeSimpleMove(move)
            }
            is CompositeMove -> {
                for (m in move.moves) {
                    makeSimpleMove(m)
                }
            }
        }
        moveLog.add(move)
    }

    override fun concede(player: Player) {
        // TODO discuss for a way to improve
        concededWinner = players.filterNot { p -> p != player }[0]
    }

    private fun makeSimpleMove(move: SimpleMove) {
        when (move) {
            is BasicMove -> {
                makeBasicMove(move)
            }
            is AddPieceMove -> {
                makeAddPieceMove(move)
            }
            is RemovePieceMove -> {
                makeRemovePieceMove(move)
            }
        }
    }

    private fun makeBasicMove(move: BasicMove) {
        board.removePiece(move.from, move.pieceMoved)
        if (move.pieceCaptured != null) {
            board.removePiece(board.getPieceCoordinate(move.pieceCaptured)!!, move.pieceCaptured)
        }
        if (move.piecePromotedTo != null) {
            board.addPiece(move.to, move.piecePromotedTo)
        } else {
            board.addPiece(move.to, move.pieceMoved)
        }
    }

    private fun makeAddPieceMove(move: AddPieceMove) {
        board.addPiece(move.coordinate, move.piece)
    }

    private fun makeRemovePieceMove(move: RemovePieceMove) {
        board.removePiece(move.coordinate, move.piece)
    }

    fun getOpponentPlayer(player: Player): Player {
        return getOpponentPlayers(player)[0]
    }
}

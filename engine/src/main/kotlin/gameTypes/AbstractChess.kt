package gameTypes

import boards.Board
import boards.Board2D
import coordinates.Coordinate
import endconditions.EndCondition
import moves.Move2D
import moves.Move.*
import moves.Move.SimpleMove.*
import gameTypes.GameType2D
import rules.SpecialRules2D
import endconditions.StandardEndConditions
import endconditions.EndCondition2D
import gameTypes.GameType2P
import moveGenerators.MoveGenerator
import moveGenerators.MoveGenerator2D
import moves.*
import pieces.Piece
import pieces.Piece2D
import pieces.Royal
import players.Player
import rules.SpecialRules

/**
 * Represents a standard n-player game,
 * which contains no special rules and standard end conditions.
 *
 * @property rules the list of special rules (e.g. Castling, EnPassant)
 * @property endConditions the list of conditions that will end the game
 */
abstract class AbstractChess<B : Board<B, MG, P, C>, MG : MoveGenerator<B, MG, P, C>, P: Piece<B, MG, P, C>, C: Coordinate>(
    override val rules: List<SpecialRules<GameType<B, MG, P, C>, B, MG, P, C>>,
    override val endConditions: List<EndCondition<GameType<B, MG, P, C>, B, MG, P, C>>,
    startPlayer: Int = 0):
    GameType2P<B, MG, P, C>() {

    override var playerTurn: Int = startPlayer

    /**
     * @throws Exception if a given player is invalid (in the players list)
     */
    override fun getValidMoves(player: Player): List<Move<B, MG, P, C>> {
        if (!players.contains(player)) {
            throw Exception("Not a valid player")
        }
        val possibleMoves = getPossibleMoves(player).toMutableList()
        val moves = filterForCheck(player, possibleMoves)
        return moves
    }

    private fun getPossibleMoves(player: Player): List<Move<B, MG, P, C>> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<Move<B, MG, P, C>>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }
        for (rule in rules) {
            rule.getPossibleMoves(this, player, possibleMoves)
        }
        return possibleMoves
    }

    private fun filterForCheck(player: Player, possibleMoves: List<Move<B, MG, P, C>>): List<Move<B, MG, P, C>> {
        val res = mutableListOf<Move<B, MG, P, C>>()
        for (move in possibleMoves) {
            when (move) {
                is SimpleMove<B, MG, P, C> -> {
                    makeMove(move)
                    if (!move.checkForCheck || !inCheck(player)) {
                        res.add(move)
                    }
                    undoMove()
                }
                is CompositeMove<B, MG, P, C> -> {
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

    private fun squareUnderAttack(coordinate: Coordinate, player: Player): Boolean {
        val nextPlayer = players[(players.indexOf(player) + 1) % 2]
        val moves = getPossibleMoves(nextPlayer)

        for (m in moves) {
            when (m) {
                is BasicMove<B, MG, P, C>, is CompositeMove<B, MG, P, C> -> {
                    if (m.displayTo!! == coordinate) {
                        return true
                    }
                }
                is AddPieceMove<B, MG, P, C>, is RemovePieceMove<B, MG, P, C> -> {
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

    private fun undoMoveHelper(move: Move<B, MG, P, C>) {
        when (move) {
            is BasicMove<B, MG, P, C> -> {
                undoBasicMove(move)
            }
            is AddPieceMove<B, MG, P, C> -> {
                board.removePiece(move.coordinate, move.piece)
            }
            is RemovePieceMove<B, MG, P, C> -> {
                board.addPiece(move.coordinate, move.piece)
            }
            is CompositeMove<B, MG, P, C> -> {
                for (m in move.moves.reversed()) {
                    undoMoveHelper(m)
                }
            }
        }
    }

    private fun undoBasicMove(move: BasicMove<B, MG, P, C>) {
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

    override fun makeMove(move: Move<B, MG, P, C>) {
        when (move) {
            is SimpleMove<B, MG, P, C> -> {
                makeSimpleMove(move)
            }
            is CompositeMove<B, MG, P, C> -> {
                for (m in move.moves) {
                    makeSimpleMove(m)
                }
            }
        }
        moveLog.add(move)
    }

    private fun makeSimpleMove(move: SimpleMove<B, MG, P, C>) {
        when (move) {
            is BasicMove<B, MG, P, C> -> {
                makeBasicMove(move)
            }
            is AddPieceMove<B, MG, P, C> -> {
                makeAddPieceMove(move)
            }
            is RemovePieceMove<B, MG, P, C> -> {
                makeRemovePieceMove(move)
            }
        }
    }

    private fun makeBasicMove(move: BasicMove<B, MG, P, C>) {
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

    private fun makeAddPieceMove(move: AddPieceMove<B, MG, P, C>) {
        board.addPiece(move.coordinate, move.piece)
    }

    private fun makeRemovePieceMove(move: RemovePieceMove<B, MG, P, C>) {
        board.removePiece(move.coordinate, move.piece)
    }
}

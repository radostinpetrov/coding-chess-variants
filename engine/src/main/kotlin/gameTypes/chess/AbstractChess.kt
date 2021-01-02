package gameTypes.chess

import Outcome
import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.GameType
import gameTypes.chess.rules.SpecialRules
import gameTypes.chess.winconditions.WinCondition
import pieces.King
import pieces.Piece2D
import players.Player

abstract class AbstractChess(val rules: List<SpecialRules<AbstractChess>> = listOf(), var winConditions: List<WinCondition<AbstractChess>>, startPlayer : Int = 0) : GameType {
    override val players: List<Player> = listOf(Player(), Player())
    override var playerTurn: Int = startPlayer

    override var seed: Double? = null

    override val moveLog: MutableList<GameMove2D> = mutableListOf()

    override val NUM_PLAYERS = 2

    override fun isOver(): Boolean {
        return getOutcome(getCurrentPlayer()) != null
    }

    override fun getOutcome(player: Player): Outcome? {
        val moves = getValidMoves(player)
        for (wc in winConditions) {
            val outcome = wc.evaluate(this, player, moves)
            if (outcome != null) {
                return outcome
            }
        }
        return null
    }

    override fun getValidMoves(player: Player): List<GameMove2D> {
        val possibleMoves = getPossibleMoves(player).toMutableList()
        val moves = filterForCheck(player, possibleMoves)
        return moves
    }

    private fun getPossibleMoves(player: Player): List<GameMove2D> {
        val pieces = board.getPieces(player)
        val possibleMoves = mutableListOf<GameMove2D>()
        for (piece in pieces) {
            possibleMoves.addAll(getValidMoveForPiece(piece))
        }
        for (rule in rules) {
            rule.getPossibleMoves(this, player, possibleMoves)
        }
        return possibleMoves
    }

    private fun filterForCheck(player: Player, possibleMoves: List<GameMove2D>): List<GameMove2D> {
        val res = mutableListOf<GameMove2D>()
        for (move in possibleMoves) {
            when (move) {
                is GameMove2D.BasicGameMove -> {
                    makeMove(move)
                    if (!inCheck(player)) {
                        res.add(move)
                    }
                    undoMove()
                }
                is GameMove2D.CompositeGameMove -> {
                    var valid = true
                    for (m in move.gameMoves) {
                        makeMove(m)
                        if (m.checkForCheck && inCheck(player)) {
                            valid = false
                        }
                    }
                    for (m in move.gameMoves) {
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
        val king = board.getPieces(player).find { p -> p.first.player == player && p.first is King } ?: return false
        val kingCoordinate = king.second
        return squareUnderAttack(kingCoordinate, player)
    }

    private fun squareUnderAttack(coordinate: Coordinate2D, player: Player): Boolean {
        val nextPlayer = players[(players.indexOf(player) + 1) % 2]
        val moves = getPossibleMoves(nextPlayer)

        for (m in moves) {
            when (m) {
                is GameMove2D.BasicGameMove -> {
                    if (m.to.x == coordinate.x && m.to.y == coordinate.y) {
                        return true
                    }
                }
                is GameMove2D.CompositeGameMove -> {
                    for (move in m.gameMoves) {
                        if (move.to.x == coordinate.x && move.to.y == coordinate.y) {
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    override fun undoMove() {
        if (moveLog.size == 0) {
            return
        }
        when (val gameMove = moveLog.removeAt(moveLog.size - 1)) {
            is GameMove2D.BasicGameMove -> {
                undoBasicMove(gameMove)
            }
            is GameMove2D.CompositeGameMove -> {
                for (move in gameMove.gameMoves.reversed()) {
                    undoBasicMove(move)
                }
            }
        }
    }

    private fun undoBasicMove(gameMove: GameMove2D.BasicGameMove) {
        if (gameMove.piecePromotedTo != null) {
            board.removePiece(gameMove.to, gameMove.piecePromotedTo)
        } else {
            board.removePiece(gameMove.to, gameMove.pieceMoved)
        }

        if (gameMove.pieceCaptured != null) {
            // CHECK FOR BUG HERE
            board.addPiece(gameMove.to, gameMove.pieceCaptured)
        }
        board.addPiece(gameMove.from, gameMove.pieceMoved)
    }

    private fun getValidMoveForPiece(pair: Pair<Piece2D, Coordinate2D>): List<GameMove2D> {
        val possibleMoves = mutableListOf<GameMove2D>()
        // validate possible moves

        val piece = pair.first
        val coordinate = pair.second

        for (move in piece.moveTypes) {
            possibleMoves.addAll(move.generate(board, coordinate, piece, getCurrentPlayer()))
        }

        return possibleMoves
    }

    override fun makeMove(gameMove: GameMove2D) {
        when (gameMove) {
            is GameMove2D.BasicGameMove -> {
                makeBasicMove(gameMove)
            }
            is GameMove2D.CompositeGameMove -> {
                for (move in gameMove.gameMoves) {
                    makeBasicMove(move)
                }
            }
        }
        moveLog.add(gameMove)
    }

    private fun makeBasicMove(gameMove: GameMove2D.BasicGameMove) {
        board.removePiece(gameMove.from, gameMove.pieceMoved)
        if (gameMove.pieceCaptured != null) {
            board.removePiece(board.getPieceCoordinate(gameMove.pieceCaptured)!!, gameMove.pieceCaptured)
        }
        if (gameMove.piecePromotedTo != null) {
            board.addPiece(gameMove.to, gameMove.piecePromotedTo)
        } else {
            board.addPiece(gameMove.to, gameMove.pieceMoved)
        }
    }
}

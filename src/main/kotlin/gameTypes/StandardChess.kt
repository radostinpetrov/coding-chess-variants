package gameTypes

import Coordinate
import boards.Board
import GameMove
import History
import boards.Board2D

class StandardChess() : GameType{

    override val board: Board = Board2D()
    override val history: MutableList<History> = mutableListOf()

    override fun initGame() {
        TODO("Not yet implemented")
    }

    override fun isOver(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getValidMoves(coordinate: Coordinate): List<GameMove> {
        val piece = board.getPiece(coordinate)
        val moveTypes = piece.moveTypes
        val possibleMoves = mutableListOf<GameMove>()
        for (moveType in moveTypes) {
            possibleMoves.addAll(moveType.getPossibleMoves(this, coordinate))
        }
        //filter checkmate and stalemate

        return possibleMoves
    }

    // override fun getHistory(): List<Pair<Board, GameMove>> {
    //     TODO("Not yet implemented")
    // }

    override fun makeMove(gameMove: GameMove) {
        TODO("Not yet implemented")
    }
}
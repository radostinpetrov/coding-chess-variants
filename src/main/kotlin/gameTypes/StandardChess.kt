package gameTypes

import Board
import Coordinate
import GameMove
import moves.Move
import pieces.Piece

class StandardChess: GameType{
    override fun initGame() {
        TODO("Not yet implemented")
    }

    override fun isOver(): Boolean {
        TODO("Not yet implemented")
    }

    override fun canMove(gameMove: GameMove): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPieces(): List<Pair<Piece, Coordinate>> {
        TODO("Not yet implemented")
    }

    override fun getPiece(coordinate: Coordinate): Piece {
        TODO("Not yet implemented")
    }

    override fun getPieceCoordinate(piece: Piece): Coordinate {
        TODO("Not yet implemented")
    }

    override fun getHistory(): List<Pair<Board, GameMove>> {
        TODO("Not yet implemented")
    }

    override fun makeMove(gameMove: GameMove) {
        TODO("Not yet implemented")
    }
}
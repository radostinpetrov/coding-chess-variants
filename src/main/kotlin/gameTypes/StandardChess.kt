package gameTypes

import Coordinate
import boards.Board
import GameMove
import History
import boards.Board2D
import pieces.Bishop
import pieces.King
import pieces.Knight
import pieces.Pawn
import pieces.Piece
import pieces.Queen
import pieces.Rook
import players.HumanPlayer

class StandardChess() : GameType{

    override val board: Board<Piece> = Board2D(8, 8)
    override val history: MutableList<History> = mutableListOf()

    override fun initGame() {
        val player1 = HumanPlayer()
        val player2 = HumanPlayer()
        for (i in 0..7){
            board.addPiece(Coordinate(i, 1), Pawn(player1))
            board.addPiece(Coordinate(i, 6), Pawn(player2))
        }
        // board.addPiece(Coordinate(0, 0), Rook(player1))
        // board.addPiece(Coordinate(7, 0), Rook(player1))
        // board.addPiece(Coordinate(0, 7), Rook(player2))
        // board.addPiece(Coordinate(7, 7), Rook(player2))
        // board.addPiece(Coordinate(1, 0), Knight(player1))
        // board.addPiece(Coordinate(6, 0), Knight(player1))
        // board.addPiece(Coordinate(1, 7), Knight(player2))
        // board.addPiece(Coordinate(6, 7), Knight(player2))
        // board.addPiece(Coordinate(2, 0), Bishop(player1))
        // board.addPiece(Coordinate(5, 0), Bishop(player1))
        // board.addPiece(Coordinate(2, 7), Bishop(player2))
        // board.addPiece(Coordinate(5, 7), Bishop(player2))
        // board.addPiece(Coordinate(4, 0), King(player1))
        // board.addPiece(Coordinate(4, 7), King(player2))
        // board.addPiece(Coordinate(3, 0), Queen(player1))
        // board.addPiece(Coordinate(3, 7), Queen(player2))
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
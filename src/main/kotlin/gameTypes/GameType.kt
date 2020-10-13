package gameTypes

import Board
import Coordinate
import GameMove
import moves.Move
import pieces.Piece

interface GameType {
    fun initGame()
    fun isOver(): Boolean
    // fun getWinner()
    fun canMove(gameMove: GameMove): Boolean
    fun getPieces(): List<Pair<Piece, Coordinate>>
    fun getPiece(coordinate: Coordinate): Piece
    fun getPieceCoordinate(piece: Piece): Coordinate
    fun getHistory(): List<Pair<Board, GameMove>>
    fun makeMove(gameMove: GameMove)
}

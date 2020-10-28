package main.kotlin.moves.visitors

import main.kotlin.Coordinate
import main.kotlin.GameMove
import main.kotlin.boards.Board
import main.kotlin.players.Player
import moves.Move
import pieces.Piece

interface MoveVisitor<B> where B : Board<Piece> {
    val board: B
    fun visit(coordinate: Coordinate, piece: Piece, move: Move, player: Player): List<GameMove>
}
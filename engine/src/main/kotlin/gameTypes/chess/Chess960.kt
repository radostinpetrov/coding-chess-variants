package main.kotlin.gameTypes.chess

import main.kotlin.Coordinate
import main.kotlin.boards.Board2D
import main.kotlin.gameTypes.chess.rules.Enpassant
import main.kotlin.moves.visitors.Board2DMoveVisitor
import main.kotlin.pieces.chess.StandardBlackPawn
import main.kotlin.pieces.chess.StandardWhitePawn
import main.kotlin.pieces.chess.*

class Chess960 : AbstractChess(listOf(Enpassant())) {
    override val board = Board2D(8, 8)
    override val moveVisitor by lazy { Board2DMoveVisitor(board) }

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7) {
            board.addPiece(Coordinate(i, 1), StandardWhitePawn(player1))
            board.addPiece(Coordinate(i, 6), StandardBlackPawn(player2))
        }

        /**
         * White non pawns are placed randomly on the first rank following two rules
         * 1. The bishop must be placed in opposite colour
         * 2. The king must be placed between rooks
         * **/

        val pieces = listOf('R', 'B', 'N', 'Q', 'K', 'N', 'B', 'R')

        val permutations = permute(pieces)

        val regBishop = """.*B(..|....|......|)B.*""".toRegex()
        val regKing = """.*R.*K.*R.*""".toRegex()
        val possiblePermutations = mutableSetOf<String>()
        for (permutation in permutations) {
            val strPermutation = permutation.joinToString("")
            if (strPermutation.matches(regBishop) && strPermutation.matches(regKing)) {
                possiblePermutations.add(strPermutation)
            }
        }

        val permutation = possiblePermutations.random()

        for ((i, c) in permutation.withIndex()) {
            when (c) {
                'R' -> {
                    board.addPiece(Coordinate(i, 0), Rook(player1))
                    board.addPiece(Coordinate(i, 7), Rook(player2))
                }
                'B' -> {
                    board.addPiece(Coordinate(i, 0), Bishop(player1))
                    board.addPiece(Coordinate(i, 7), Bishop(player2))
                }
                'N' -> {
                    board.addPiece(Coordinate(i, 0), Knight(player1))
                    board.addPiece(Coordinate(i, 7), Knight(player2))
                }
                'Q' -> {
                    board.addPiece(Coordinate(i, 0), Queen(player1))
                    board.addPiece(Coordinate(i, 7), Queen(player2))
                }
                'K' -> {
                    board.addPiece(Coordinate(i, 0), King(player1))
                    board.addPiece(Coordinate(i, 7), King(player2))
                }
            }
        }
    }

    private fun <T> permute(input: List<T>): List<List<T>> {
        if (input.size == 1) return listOf(input)
        val perms = mutableListOf<List<T>>()
        val toInsert = input[0]
        for (perm in permute(input.drop(1))) {
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, toInsert)
                perms.add(newPerm)
            }
        }
        return perms
    }
}

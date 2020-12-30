package gameTypes.chess

import coordinates.Coordinate2D
import boards.Board2D
import gameTypes.chess.rules.Chess960Castling
import gameTypes.chess.rules.Enpassant
import pieces.chess.*

class Chess960 : AbstractChess(listOf(Chess960Castling(), Enpassant())) {
    override val board = Board2D(8, 8)

    override fun initGame() {
        val player1 = players[0]
        val player2 = players[1]
        for (i in 0..7) {
            board.addPiece(Coordinate2D(i, 1), StandardWhitePawn(player1))
            board.addPiece(Coordinate2D(i, 6), StandardBlackPawn(player2))
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

        seed = 5.0/960.0
        val permutation = if (seed == null) possiblePermutations.random() else possiblePermutations.toList()[(seed!! * possiblePermutations.size).toInt()]
        for ((i, c) in permutation.withIndex()) {
            when (c) {
                'R' -> {
                    board.addPiece(Coordinate2D(i, 0), Rook(player1))
                    board.addPiece(Coordinate2D(i, 7), Rook(player2))
                }
                'B' -> {
                    board.addPiece(Coordinate2D(i, 0), Bishop(player1))
                    board.addPiece(Coordinate2D(i, 7), Bishop(player2))
                }
                'N' -> {
                    board.addPiece(Coordinate2D(i, 0), Knight(player1))
                    board.addPiece(Coordinate2D(i, 7), Knight(player2))
                }
                'Q' -> {
                    board.addPiece(Coordinate2D(i, 0), Queen(player1))
                    board.addPiece(Coordinate2D(i, 7), Queen(player2))
                }
                'K' -> {
                    board.addPiece(Coordinate2D(i, 0), King(player1))
                    board.addPiece(Coordinate2D(i, 7), King(player2))
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

package testGameTypes

import coordinates.Coordinate2D
import gameTypes.chess.Chess960
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pieces.chess.*

class Chess960Test {
    private var mockChess960 = Chess960(0.0)

    val player1 = mockChess960.players[0]
    val player2 = mockChess960.players[1]

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun numberOfPermutationsAre960() {
        assertEquals(mockChess960.getPossiblePermutations().size, 960)
    }

    @Test
    fun startingPermutationIsValid() {
        val startingPermutation = "RBKNBNRQ"
        val possiblePermutations = mockChess960.getPossiblePermutations()
        assertTrue(possiblePermutations.contains(startingPermutation))
    }

    @Test
    fun startingPermutationIsInvalid() {
        val startingPermutation = "RBKNNBRQ"
        val possiblePermutations = mockChess960.getPossiblePermutations()
        assertFalse(possiblePermutations.contains(startingPermutation))
    }

    @Test
    fun bishopsAreDifferentColours() {
        var sameColouredBishops = 0
        var paritySum = 0
        val possiblePermutations = mockChess960.getPossiblePermutations()
        for (permutation in possiblePermutations) {
            for (i in 0..permutation.length - 1) {
                if (permutation[i] == 'B') {
                    paritySum += i % 2
                }
            }
            if (paritySum != 1) {
                sameColouredBishops++
            }
            paritySum = 0
        }
        assertEquals(sameColouredBishops, 0)
    }

    @Test
    fun kingIsBetweenRooks() {
        var kingsOutsideRooks = 0
        val possiblePermutations = mockChess960.getPossiblePermutations()
        for (permutation in possiblePermutations) {
            if (permutation.indexOf('K') < permutation.indexOfFirst { it == 'R' }) {
                kingsOutsideRooks++
            } else if (permutation.indexOf('K') > permutation.indexOfLast { it == 'R' }) {
                kingsOutsideRooks++
            }
        }
        assertEquals(kingsOutsideRooks, 0)
    }

    @Test
    fun boardIsInitialized() {

        val initPiecesTest = listOf(

            // Pawns
            Pair(StandardWhitePawn(player1), Coordinate2D(0, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(1, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(2, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(3, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(4, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(5, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(6, 1)),
            Pair(StandardWhitePawn(player1), Coordinate2D(7, 1)),
            Pair(StandardBlackPawn(player2), Coordinate2D(0, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(1, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(2, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(3, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(4, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(5, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(6, 6)),
            Pair(StandardBlackPawn(player2), Coordinate2D(7, 6)),
            // Rooks
            Pair(Rook(player1), Coordinate2D(0, 0)),
            Pair(Rook(player1), Coordinate2D(7, 0)),
            Pair(Rook(player2), Coordinate2D(0, 7)),
            Pair(Rook(player2), Coordinate2D(7, 7)),
            // Knights
            Pair(Knight(player1), Coordinate2D(2, 0)),
            Pair(Knight(player1), Coordinate2D(5, 0)),
            Pair(Knight(player2), Coordinate2D(2, 7)),
            Pair(Knight(player2), Coordinate2D(5, 7)),
            // Bishops
            Pair(Bishop(player1), Coordinate2D(1, 0)),
            Pair(Bishop(player1), Coordinate2D(6, 0)),
            Pair(Bishop(player2), Coordinate2D(1, 7)),
            Pair(Bishop(player2), Coordinate2D(6, 7)),
            // Queens
            Pair(Queen(player1), Coordinate2D(3, 0)),
            Pair(Queen(player2), Coordinate2D(3, 7)),
            // Kings
            Pair(King(player1), Coordinate2D(4, 0)),
            Pair(King(player2), Coordinate2D(4, 7))
        )

        mockChess960.initGame()
        val initPieces = mockChess960.board.getPieces()
        assertTrue(initPieces.containsAll(initPiecesTest))
        assertEquals(initPieces.size, initPiecesTest.size)
    }
}

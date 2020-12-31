package testGameTypes

import gameTypes.chess.Chess960
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Chess960Test {
    private var mockChess960 = spyk<Chess960>()

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
}

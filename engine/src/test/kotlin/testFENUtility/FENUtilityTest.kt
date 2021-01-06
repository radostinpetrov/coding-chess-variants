package testFENUtility

import utils.FenUtility
import gameTypes.chess.StandardChess
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FENUtilityTest {

    @Test
    fun testFENIncorrectNumberOfParameters() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq d3 0 1")
        }
    }

    @Test
    fun testFENIncorrectActiveColourThrowsError() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R a KQkq")
        }
    }

    @Test
    fun testFENIncorrectCastlingAvailabilityThrowsError1() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w a")
        }
    }

    @Test
    fun testFENIncorrectCastlingAvailabilityThrowsError2() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQa")
        }
    }

    @Test
    fun testFENCorrectCastlingAvailabilityDoesNotThrowError3() {
        Assertions.assertDoesNotThrow() {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w -")
        }
    }

    @Test
    fun testFENCorrectCastlingAvailabilityDoesNotThrowError4() {
        Assertions.assertDoesNotThrow() {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq")
        }
    }

    @Test
    fun testFENIncorrectCastlingAvailabilityThrowsError5() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkqk")
        }
    }

    @Test
    fun testFENInitBoardIncorrectNumberOfRows() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            val standardChess = StandardChess(FenUtility("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R/R3K2R w KQkq"))
            standardChess.initGame()
        }
    }

    @Test
    fun testFENInitBoardIncorrectNumberOfColumns1() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            val standardChess = StandardChess(FenUtility("r3k2rk/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq"))
            standardChess.initGame()
        }
    }

    @Test
    fun testFENInitBoardIncorrectNumberOfColumns2() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            val standardChess = StandardChess(FenUtility("r3k2r/p1ppqpb1/9/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq"))
            standardChess.initGame()
        }
    }
}
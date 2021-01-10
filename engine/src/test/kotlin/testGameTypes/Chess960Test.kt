package testGameTypes

import coordinates.Coordinate2D
import gameTypes.chess.Chess960
import io.mockk.MockKAnnotations
import moves.*
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

    @Test
    fun castlingLeftTest() {
        val chess = Chess960(180.0 / 960)
        chess.initBoard()
        val initMoves: List<BasicMove2D> = listOf(
            BasicMove2D(Coordinate2D(4, 1), Coordinate2D(4, 3), StandardWhitePawn(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
            BasicMove2D(Coordinate2D(5, 0), Coordinate2D(4, 1), Bishop(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
            BasicMove2D(Coordinate2D(6, 0), Coordinate2D(5, 2), Knight(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
        )

        for (move in initMoves) {
            chess.makeMove(move)
        }

        val moves = chess.getValidMoves(chess.players[0])
        val castleMove = CompositeMove2D(
            moves = listOf(
                RemovePieceMove2D(
                    player = chess.players[0],
                    piece = Rook(chess.players[0]),
                    coordinate = Coordinate2D(7, 0)
                ),
                BasicMove2D(
                    from = Coordinate2D(4, 0),
                    to = Coordinate2D(5, 0),
                    pieceMoved = King(player = chess.players[0]),
                    player = chess.players[0],
                    pieceCaptured = null,
                    piecePromotedTo = null,
                    checkForCheck = true
                ),
                BasicMove2D(
                    from = Coordinate2D(5, 0),
                    to = Coordinate2D(6, 0),
                    pieceMoved = King(player = chess.players[0]),
                    player = chess.players[0],
                    pieceCaptured = null,
                    piecePromotedTo = null,
                    checkForCheck = true
                ),
                AddPieceMove2D(
                    player = chess.players[0],
                    piece = Rook(chess.players[0]),
                    coordinate = Coordinate2D(5, 0)
            )
            ),
            player = chess.players[0]
        )
        assertTrue(moves.contains(castleMove))
    }

    @Test
    fun castlingRightTest() {
        val chess = Chess960(180.0 / 960)
        chess.initBoard()
        val initMoves: List<BasicMove2D> = listOf(
            BasicMove2D(Coordinate2D(3, 1), Coordinate2D(3, 3), StandardWhitePawn(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(0, 6), Coordinate2D(0, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
            BasicMove2D(Coordinate2D(3, 0), Coordinate2D(3, 2), Queen(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(1, 6), Coordinate2D(1, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
            BasicMove2D(Coordinate2D(2, 0), Coordinate2D(3, 1), Bishop(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(2, 6), Coordinate2D(2, 5), StandardBlackPawn(chess.players[1]), chess.players[1]),
            BasicMove2D(Coordinate2D(1, 0), Coordinate2D(2, 2), Knight(chess.players[0]), chess.players[0]),
            BasicMove2D(Coordinate2D(3, 6), Coordinate2D(3, 5), StandardBlackPawn(chess.players[1]), chess.players[1])
        )

        for (move in initMoves) {
            chess.makeMove(move)
        }

        val moves = chess.getValidMoves(chess.players[0])
        val castleMove = CompositeMove2D(
            moves = listOf(
                RemovePieceMove2D(
                    player = chess.players[0],
                    piece = Rook(chess.players[0]),
                    coordinate = Coordinate2D(0, 0)
                ),
                BasicMove2D(
                    from = Coordinate2D(4, 0),
                    to = Coordinate2D(3, 0),
                    pieceMoved = King(player = chess.players[0]),
                    player = chess.players[0],
                    pieceCaptured = null,
                    piecePromotedTo = null,
                    checkForCheck = true
                ),
                BasicMove2D(
                    from = Coordinate2D(3, 0),
                    to = Coordinate2D(2, 0),
                    pieceMoved = King(player = chess.players[0]),
                    player = chess.players[0],
                    pieceCaptured = null,
                    piecePromotedTo = null,
                    checkForCheck = true
                ),
                AddPieceMove2D(
                    player = chess.players[0],
                    piece = Rook(chess.players[0]),
                    coordinate = Coordinate2D(3, 0)
                )
            ),
            player = chess.players[0]
        )
        assertTrue(moves.contains(castleMove))
    }
}

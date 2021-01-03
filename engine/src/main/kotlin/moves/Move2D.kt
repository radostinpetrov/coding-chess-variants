package moves

import boards.Board2D
import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameMoves.GameMove2D.SimpleGameMove.BasicGameMove
import moves.region.Region
import pieces.Piece2D
import players.Player

sealed class Move2D : Move<Board2D, Move2D, GameMove2D, Piece2D, Coordinate2D> {
    class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            if (H) {
                result.addAll(helper(board, coordinate, piece, 1, 0, player))
                result.addAll(helper(board, coordinate, piece, -1, 0, player))
            }
            if (V) {
                result.addAll(helper(board, coordinate, piece, 0, 1, player))
                result.addAll(helper(board, coordinate, piece, 0, -1, player))
            }
            if (D) {
                result.addAll(helper(board, coordinate, piece, 1, 1, player))
                result.addAll(helper(board, coordinate, piece, -1, -1, player))
            }
            if (A) {
                result.addAll(helper(board, coordinate, piece, 1, -1, player))
                result.addAll(helper(board, coordinate, piece, -1, 1, player))
            }
            return result
        }

        private fun helper(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, dx: Int, dy: Int, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            var nextCoordinate = Coordinate2D(coordinate.x + dx, coordinate.y + dy)
            while (board.isInBounds(nextCoordinate)) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece == null) {
                    result.add(BasicGameMove(coordinate, nextCoordinate, piece, player, null, null))
                    nextCoordinate = Coordinate2D(nextCoordinate.x + dx, nextCoordinate.y + dy)
                } else if (piece.player != destPiece.player) {
                    result.add(BasicGameMove(coordinate, nextCoordinate, piece, player, destPiece, null))
                    break
                } else {
                    break
                }
            }
            return result
        }
    }
    data class Stepper(val directions: List<Direction>, val step: Int, val canCapture: Boolean = false) : Move2D() {
        constructor(direction: Direction, step: Int, canCapture: Boolean = false) : this(listOf(direction), step, canCapture)
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            for (direction in directions) {
                var s = 1
                var nextCoordinate = Coordinate2D(
                    coordinate.x + direction.coordinate.x,
                    coordinate.y + direction.coordinate.y
                )
                while (s < step) {
                    if (!board.isInBounds(nextCoordinate) || board.getPiece(nextCoordinate) != null) {
                        return result
                    }
                    nextCoordinate = Coordinate2D(
                        nextCoordinate.x + direction.coordinate.x,
                        nextCoordinate.y + direction.coordinate.y
                    )
                    s++
                }

                val destPiece = board.getPiece(nextCoordinate)
                if (board.isInBounds(nextCoordinate)) {
                    if (destPiece == null || (canCapture && destPiece.player != piece.player)) {
                        result.add(BasicGameMove(coordinate, nextCoordinate, piece, player, destPiece, null))
                    }
                }
            }

            return result
        }
    }
    data class Leaper(val dx: Int, val dy: Int) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            val destCoordinates = listOf(
                Coordinate2D(coordinate.x + dx, coordinate.y + dy),
                Coordinate2D(coordinate.x + dx, coordinate.y - dy),
                Coordinate2D(coordinate.x - dx, coordinate.y + dy),
                Coordinate2D(coordinate.x - dx, coordinate.y - dy),
                Coordinate2D(coordinate.x + dy, coordinate.y + dx),
                Coordinate2D(coordinate.x + dy, coordinate.y - dx),
                Coordinate2D(coordinate.x - dy, coordinate.y + dx),
                Coordinate2D(coordinate.x - dy, coordinate.y - dx),
            ).distinct()

            for (destCoordinate in destCoordinates) {
                if (board.isInBounds(destCoordinate)) {
                    val destPiece = board.getPiece(destCoordinate)
                    if (destPiece == null || piece.player != destPiece.player) {
                        result.add(BasicGameMove(coordinate, destCoordinate, piece, player, destPiece, null))
                    }
                }
            }
            return result
        }
    }
    data class Hopper(val HV: Boolean = false, val D: Boolean = false, val canJumpOverSamePiece: Boolean) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            if (HV) {
                result.addAll(helper(board, coordinate, piece, 1, 0, player))
                result.addAll(helper(board, coordinate, piece, -1, 0, player))
                result.addAll(helper(board, coordinate, piece, 0, 1, player))
                result.addAll(helper(board, coordinate, piece, 0, -1, player))
            }
            if (D) {
                result.addAll(helper(board, coordinate, piece, 1, 1, player))
                result.addAll(helper(board, coordinate, piece, -1, 1, player))
                result.addAll(helper(board, coordinate, piece, 1, -1, player))
                result.addAll(helper(board, coordinate, piece, -1, -1, player))
            }
            return result
        }

        private fun helper(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, dx: Int, dy: Int, player: Player): List<BasicGameMove> {
            val result = mutableListOf<BasicGameMove>()
            var nextCoordinate = Coordinate2D(coordinate.x + dx, coordinate.y + dy)
            var count = 0

            while (board.isInBounds(nextCoordinate) && count < 2) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece != null) {
                    if (!canJumpOverSamePiece && destPiece::class == piece::class) {
                        break
                    }
                    if (count == 1 && destPiece.player != piece.player) {
                        result.add(BasicGameMove(coordinate, nextCoordinate, piece, player, destPiece, null))
                    }
                    count++
                } else if (count == 1) {
                    result.add(BasicGameMove(coordinate, nextCoordinate, piece, player, null, null))
                }
                nextCoordinate = Coordinate2D(nextCoordinate.x + dx, nextCoordinate.y + dy)
            }
            return result
        }
    }

    data class CaptureOnly(val move: Move2D) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            return move.generate(board, coordinate, piece, player).filterIsInstance<BasicGameMove>().filter {
                it.pieceCaptured != null
            }
        }
    }

    data class NoCapture(val move: Move2D) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            return move.generate(board, coordinate, piece, player).filterIsInstance<BasicGameMove>().filter {
                it.pieceCaptured == null
            }
        }
    }

    data class Restricted(val move: Move2D, val region: Region) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            return move.generate(board, coordinate, piece, player).filterIsInstance<BasicGameMove>().filter {
                region.isInRegion(it.from)
            }
        }
    }
    data class RestrictedDestination(val move: Move2D, val region: Region) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            return move.generate(board, coordinate, piece, player).filterIsInstance<BasicGameMove>().filter {
                region.isInRegion(it.to)
            }
        }
    }
    data class AddPromotion(val moves: List<Move2D>, val region: Region, val promoPieces: List<Piece2D>, val forced: Boolean) : Move2D() {
        constructor(move: Move2D, region: Region, promoPieces: List<Piece2D>, forced: Boolean) : this(listOf(move), region, promoPieces, forced)
        override fun generate(
            board: Board2D,
            coordinate: Coordinate2D,
            piece: Piece2D,
            player: Player
        ): List<BasicGameMove> {
            val result: MutableList<BasicGameMove> = mutableListOf()
            moves.forEach { move ->
                move.generate(board, coordinate, piece, player)
                    .filterIsInstance<BasicGameMove>()
                    .forEach {
                        if (region.isInRegion(it.to)) {
                            for (promoPiece in promoPieces) {
                                result.add(
                                    BasicGameMove(
                                        it.from,
                                        it.to,
                                        it.pieceMoved,
                                        player,
                                        it.pieceCaptured,
                                        promoPiece
                                    )
                                )
                            }
                            if (!forced) {
                                result.add(BasicGameMove(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
                            }
                        } else {
                            result.add(BasicGameMove(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
                        }
                    }
            }
            return result
        }
    }

    data class Composite(val moves: List<Move2D>) : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            val iter = moves.iterator()
            var gameMoves: List<BasicGameMove> = iter.next().generate(board, coordinate, piece, player).filterIsInstance<BasicGameMove>()
            while (iter.hasNext()) {
                val move = iter.next()
                val newGameMoves: MutableList<BasicGameMove> = mutableListOf()
                gameMoves.forEach { prevMove ->
                    move.generate(board, prevMove.to, prevMove.pieceMoved, player).filterIsInstance<BasicGameMove>().forEach { currMove ->
                        newGameMoves.add(
                            BasicGameMove(
                                prevMove.from,
                                currMove.to,
                                currMove.pieceMoved,
                                player,
                                currMove.pieceCaptured,
                                currMove.piecePromotedTo
                            )
                        )
                    }
                    gameMoves = newGameMoves
                }
            }
            return gameMoves
        }
    }

    object Skip : Move2D() {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicGameMove> {
            return listOf(
                BasicGameMove(coordinate, coordinate, piece, player, null, null)
            )
        }
    }
}

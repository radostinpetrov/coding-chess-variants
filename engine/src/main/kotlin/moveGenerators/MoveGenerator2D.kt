package moveGenerators

import boards.Board2D
import coordinates.Coordinate2D
import moves.*
import pieces.Piece2D
import players.Player
import regions.Region2D

/**
 * Implementation of the Move Generator interface for a 2d square board.
 */
interface MoveGenerator2D : MoveGenerator<Board2D, MoveGenerator2D, Piece2D, Coordinate2D> {
    /**
     * Moves along a ray direction until they encounter another piece or the edge of the board
     *
     * @property H true if it can move along horizontal direction
     * @property V true if it can move along vertical direction
     * @property D true if it can move along diagonal direction
     * @property A true if it can move along anti-diagonal direction
     */
    class Slider(val H: Boolean = false, val V: Boolean = false, val D: Boolean = false, val A: Boolean = false) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            val directions = mutableListOf<Direction>()
            if (H) {
                directions.addAll(listOf(Direction.EAST, Direction.WEST))
            }
            if (V) {
                directions.addAll(listOf(Direction.NORTH, Direction.SOUTH))
            }
            if (D) {
                directions.addAll(listOf(Direction.NORTH_WEST, Direction.SOUTH_EAST))
            }
            if (A) {
                directions.addAll(listOf(Direction.NORTH_EAST, Direction.SOUTH_WEST))
            }

            val result = mutableListOf<BasicMove2D>()
            directions.map{
                result.addAll(helper(board, coordinate, piece, it, player))
            }
            return result
        }

        private fun helper(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, direction: Direction, player: Player): List<BasicMove2D> {
            val result = mutableListOf<BasicMove2D>()
            val dx = direction.coordinate.x
            val dy = direction.coordinate.y
            var nextCoordinate = Coordinate2D(coordinate.x + dx, coordinate.y + dy)
            while (board.isInBounds(nextCoordinate)) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece == null) {
                    result.add(BasicMove2D(coordinate, nextCoordinate, piece, player, null))
                    nextCoordinate = Coordinate2D(nextCoordinate.x + dx, nextCoordinate.y + dy)
                } else if (piece.player != destPiece.player) {
                    result.add(BasicMove2D(coordinate, nextCoordinate, piece, player, destPiece))
                    break
                } else {
                    break
                }
            }
            return result
        }
    }

    /**
     * Performs single (repeated) steps in a particular board direction
     *
     * @property directions a list of available directions
     * @property step the number of steps
     * @property canCapture true if it can capture an enemy piece
     */
    data class Stepper(val directions: List<Direction>, val step: Int, val canCapture: Boolean = false) : MoveGenerator2D {
        constructor(direction: Direction, step: Int, canCapture: Boolean = false) : this(listOf(direction), step, canCapture)
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            val result = mutableListOf<BasicMove2D>()
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
                        result.add(BasicMove2D(coordinate, nextCoordinate, piece, player, destPiece))
                    }
                }
            }

            return result
        }
    }

    /**
     * Performs single steps to specified target squares
     * Takes in a step-vector, which is then mirrored to give a total of
     * up to 8 target coordinates
     *
     * @property dx x component of the step-vector
     * @property dy y component of the step-vector
     */
    data class Leaper(val dx: Int, val dy: Int) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            val result = mutableListOf<BasicMove2D>()
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
                        result.add(BasicMove2D(coordinate, destCoordinate, piece, player, destPiece))
                    }
                }
            }
            return result
        }
    }

    /**
     * Can move along a ray direction, but must jump over another piece
     *
     * @property HV true if it can move along horizontal and vertical direction
     * @property D true if it can move along diagonal and anti-diagonal direction
     * @property canJumpOverSamePiece true if it can jump over the same kind of piece
     */
    data class Hopper(val HV: Boolean = false, val D: Boolean = false, val canJumpOverSamePiece: Boolean) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            val result = mutableListOf<BasicMove2D>()
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

        private fun helper(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, dx: Int, dy: Int, player: Player): List<BasicMove2D> {
            val result = mutableListOf<BasicMove2D>()
            var nextCoordinate = Coordinate2D(coordinate.x + dx, coordinate.y + dy)
            var count = 0

            while (board.isInBounds(nextCoordinate) && count < 2) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece != null) {
                    if (!canJumpOverSamePiece && destPiece::class == piece::class) {
                        break
                    }
                    if (count == 1 && destPiece.player != piece.player) {
                        result.add(BasicMove2D(coordinate, nextCoordinate, piece, player, destPiece))
                    }
                    count++
                } else if (count == 1) {
                    result.add(BasicMove2D(coordinate, nextCoordinate, piece, player, null))
                }
                nextCoordinate = Coordinate2D(nextCoordinate.x + dx, nextCoordinate.y + dy)
            }
            return result
        }
    }

    /**
     * A given move can only occur if it captures a piece
     *
     * @property moveGenerator the move to
     */
    data class CaptureOnly(val moveGenerator: MoveGenerator2D) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove2D>().filter {
                it.pieceCaptured != null
            }
        }
    }

    /**
     * A given move can only occur if it does not capture a piece
     */
    data class NoCapture(val moveGenerator: MoveGenerator2D) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove2D>().filter {
                it.pieceCaptured == null
            }
        }
    }

    /**
     * A given move can only occur when the piece starts in a specific region
     *
     * @property region the region that the piece can start from
     */
    data class Restricted(val moveGenerator: MoveGenerator2D, val region: Region2D) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove2D>().filter {
                region.isInRegion(it.from)
            }
        }
    }

    /**
     * A given move can only occur if the destination is within a specific region
     *
     * @property region the region that the piece can end in
     */
    data class RestrictedDestination(val moveGenerator: MoveGenerator2D, val region: Region2D) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove2D>().filter {
                region.isInRegion(it.to)
            }
        }
    }

    /**
     * A piece can be promoted to another piece in a specific region
     *
     * @property moves a list of moves which can results in promotion
     * @property region the region that the promotion can occur
     * @property promoPieces a list of pieces that the piece can be promoted to
     * @property forced true if the piece must be promoted to one of promoPieces
     */
    data class AddPromotion(val moveGenerators: List<MoveGenerator2D>, val region: Region2D, val promoPieces: List<Piece2D>, val forced: Boolean) : MoveGenerator2D {
        constructor(move: MoveGenerator2D, region: Region2D, promoPieces: List<Piece2D>, forced: Boolean) : this(listOf(move), region, promoPieces, forced)
        override fun generate(
            board: Board2D,
            coordinate: Coordinate2D,
            piece: Piece2D,
            player: Player
        ): List<BasicMove2D> {
            val result: MutableList<BasicMove2D> = mutableListOf()
            moveGenerators.forEach { move ->
                move.generate(board, coordinate, piece, player)
                    .filterIsInstance<BasicMove2D>()
                    .forEach {
                        if (region.isInRegion(it.to)) {
                            for (promoPiece in promoPieces) {
                                result.add(
                                    BasicMove2D(
                                        it.from,
                                        it.to,
                                        it.pieceMoved,
                                        player,
                                        it.pieceCaptured,
                                        it.pieceCapturedCoordinate,
                                        promoPiece
                                    )
                                )
                            }
                            if (!forced) {
                                result.add(BasicMove2D(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
                            }
                        } else {
                            result.add(BasicMove2D(it.from, it.to, it.pieceMoved, player, it.pieceCaptured, it.pieceCapturedCoordinate))
                        }
                    }
            }
            return result
        }
    }

    /**
     * A wrapper around a list of basic moves to represent composite moves
     */
    data class Composite(val moveGenerators: List<MoveGenerator2D>) : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            val iter = moveGenerators.iterator()
            var moves: List<BasicMove2D> = iter.next().generate(board, coordinate, piece, player).filterIsInstance<BasicMove2D>()
            while (iter.hasNext()) {
                val move = iter.next()
                val newMoves: MutableList<BasicMove2D> = mutableListOf()
                moves.forEach { prevMove ->
                    move.generate(board, prevMove.to, prevMove.pieceMoved, player).filterIsInstance<BasicMove2D>().forEach { currMove ->
                        newMoves.add(
                            BasicMove2D(
                                prevMove.from,
                                currMove.to,
                                currMove.pieceMoved,
                                player,
                                currMove.pieceCaptured,
                                currMove.pieceCapturedCoordinate,
                                currMove.piecePromotedTo
                            )
                        )
                    }
                    moves = newMoves
                }
            }
            return moves
        }
    }

    /**
     * Skip move: a player may decide to pass if there is no safe or desirable move
     */
    object Skip : MoveGenerator2D {
        override fun generate(board: Board2D, coordinate: Coordinate2D, piece: Piece2D, player: Player): List<BasicMove2D> {
            return listOf(
                BasicMove2D(coordinate, coordinate, piece, player, null )
            )
        }
    }
}

package moveGenerators

import boards.Board3D
import coordinates.Coordinate3D
import moves.*
import pieces.Piece3D
import players.Player
import regions.Region3D

/**
 * Implementation of the Move Generator interface for a 3D square board.
 */
interface MoveGenerator3D : MoveGenerator<Board3D, MoveGenerator3D, Piece3D, Coordinate3D> {
    /**
     * Moves along a ray direction until they encounter another piece or the edge of the board
     *
     * @property X true if it can move along X axis
     * @property Y true if it can move along Y axis
     * @property Z true if it can move along Z axis
     * @property D2D true if it can move along diagonal direction on the X,Y plane
     * @property AD2D true if it can move along anti-diagonal direction on the X,Y plane
     * @property D true if it can move diagonally along Z,Y axis
     * @property D3D true if it can move diagonally along X,Y,Z axis
     */
    class Slider3D(val X: Boolean = false, val Y: Boolean = false, val Z: Boolean = false, val D2D: Boolean = false, val AD2D: Boolean = false, val D: Boolean = false, val D3D: Boolean = false) : MoveGenerator3D {
        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
            val result = mutableListOf<BasicMove3D>()
            if (X) {
                result.addAll(helper(board, coordinate, piece, 1, 0, 0, player))
                result.addAll(helper(board, coordinate, piece, -1, 0, 0, player))
            }
            if (Y) {
                result.addAll(helper(board, coordinate, piece, 0, 1, 0, player))
                result.addAll(helper(board, coordinate, piece, 0, -1, 0, player))
            }
            if (Z) {
                result.addAll(helper(board, coordinate, piece, 0, 0, 1, player))
                result.addAll(helper(board, coordinate, piece, 0, 0, -1, player))
            }
            if (D2D) {
                result.addAll(helper(board, coordinate, piece, 1, 1, 0, player))
                result.addAll(helper(board, coordinate, piece, -1, -1, 0, player))
            }
            if (AD2D) {
                result.addAll(helper(board, coordinate, piece, 1, -1, 0, player))
                result.addAll(helper(board, coordinate, piece, -1, 1, 0, player))
            }
            if (D) {
                result.addAll(helper(board, coordinate, piece, 1, 0, 1, player))
                result.addAll(helper(board, coordinate, piece, -1, 0, -1, player))
                result.addAll(helper(board, coordinate, piece, -1, 0, 1, player))
                result.addAll(helper(board, coordinate, piece, 1, 0, -1, player))
                result.addAll(helper(board, coordinate, piece, 0, 1, 1, player))
                result.addAll(helper(board, coordinate, piece, 0, -1, -1, player))
                result.addAll(helper(board, coordinate, piece, 0, -1, 1, player))
                result.addAll(helper(board, coordinate, piece, 0, 1, -1, player))
            }
            if (D3D) {
                result.addAll(helper(board, coordinate, piece, 1, 1, 1, player))
                result.addAll(helper(board, coordinate, piece, 1, 1, -1, player))
                result.addAll(helper(board, coordinate, piece, -1, 1, 1, player))
                result.addAll(helper(board, coordinate, piece, -1, 1, -1, player))
                result.addAll(helper(board, coordinate, piece, 1, -1, 1, player))
                result.addAll(helper(board, coordinate, piece, 1, -1, -1, player))
                result.addAll(helper(board, coordinate, piece, -1, -1, 1, player))
                result.addAll(helper(board, coordinate, piece, -1, -1, -1, player))
            }

            return result
        }

        private fun helper(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, dx: Int, dy: Int, dz: Int, player: Player): List<BasicMove3D> {
            val result = mutableListOf<BasicMove3D>()
            var nextCoordinate = Coordinate3D(coordinate.x + dx, coordinate.y + dy, coordinate.z + dz)
            while (board.isInBounds(nextCoordinate)) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece == null) {
                    result.add(BasicMove3D(coordinate, nextCoordinate, piece, player, null))
                    nextCoordinate = Coordinate3D(nextCoordinate.x + dx, nextCoordinate.y + dy, nextCoordinate.z + dz)
                } else if (piece.player != destPiece.player) {
                    result.add(BasicMove3D(coordinate, nextCoordinate, piece, player, destPiece))
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
    data class Stepper3D(val directions: List<Direction3D>, val step: Int, val canCapture: Boolean = false) : MoveGenerator3D {
        constructor(direction: Direction3D, step: Int, canCapture: Boolean = false) : this(listOf(direction), step, canCapture)
        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
            val result = mutableListOf<BasicMove3D>()
            for (direction in directions) {
                var s = 1
                var nextCoordinate = direction.coordinate + coordinate
                while (s < step) {
                    if (!board.isInBounds(nextCoordinate) || board.getPiece(nextCoordinate) != null) {
                        return result
                    }
                    nextCoordinate += direction.coordinate
                    s++
                }

                val destPiece = board.getPiece(nextCoordinate)
                if (board.isInBounds(nextCoordinate)) {
                    if (destPiece == null || (canCapture && destPiece.player != piece.player)) {
                        result.add(BasicMove3D(coordinate, nextCoordinate, piece, player, destPiece))
                    }
                }
            }

            return result
        }
    }

    /**
     * Performs single steps to specified target squares
     * Takes in a step-vector, which is then mirrored
     *
     * @property dx x component of the step-vector
     * @property dy y component of the step-vector
     * @property dz z component of the step-vector
     */
    data class Leaper3D(val dx: Int, val dy: Int, val dz: Int) : MoveGenerator3D {
        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
            val result = mutableListOf<BasicMove3D>()
            val destCoordinates: MutableSet<Coordinate3D> = mutableSetOf()
            for (i in listOf(dx, -dx)) {
                for (j in listOf(dy, -dy)) {
                    for (k in listOf(dz, -dz)) {
                        destCoordinates.add(Coordinate3D(i, j, k) + coordinate)
                        destCoordinates.add(Coordinate3D(i, k, j) + coordinate)
                        destCoordinates.add(Coordinate3D(j, i, k) + coordinate)
                        destCoordinates.add(Coordinate3D(j, j, i) + coordinate)
                        destCoordinates.add(Coordinate3D(k, i, j) + coordinate)
                        destCoordinates.add(Coordinate3D(k, j, i) + coordinate)
                    }
                }
            }

            for (destCoordinate in destCoordinates) {
                if (board.isInBounds(destCoordinate)) {
                    val destPiece = board.getPiece(destCoordinate)
                    if (destPiece == null || piece.player != destPiece.player) {
                        result.add(BasicMove3D(coordinate, destCoordinate, piece, player, destPiece))
                    }
                }
            }
            return result
        }
    }
//
//    /**
//     * Can move along a ray direction, but must jump over another piece
//     *
//     * @property HV true if it can move along horizontal and vertical direction
//     * @property D true if it can move along diagonal and anti-diagonal direction
//     * @property canJumpOverSamePiece true if it can jump over the same kind of piece
//     */
//    data class Hopper(val HV: Boolean = false, val D: Boolean = false, val canJumpOverSamePiece: Boolean) : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            val result = mutableListOf<BasicMove3D>()
//            if (HV) {
//                result.addAll(helper(board, coordinate, piece, 1, 0, player))
//                result.addAll(helper(board, coordinate, piece, -1, 0, player))
//                result.addAll(helper(board, coordinate, piece, 0, 1, player))
//                result.addAll(helper(board, coordinate, piece, 0, -1, player))
//            }
//            if (D) {
//                result.addAll(helper(board, coordinate, piece, 1, 1, player))
//                result.addAll(helper(board, coordinate, piece, -1, 1, player))
//                result.addAll(helper(board, coordinate, piece, 1, -1, player))
//                result.addAll(helper(board, coordinate, piece, -1, -1, player))
//            }
//            return result
//        }
//
//        private fun helper(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, dx: Int, dy: Int, player: Player): List<BasicMove3D> {
//            val result = mutableListOf<BasicMove3D>()
//            var nextCoordinate = Coordinate3D(coordinate.x + dx, coordinate.y + dy)
//            var count = 0
//
//            while (board.isInBounds(nextCoordinate) && count < 2) {
//                val destPiece = board.getPiece(nextCoordinate)
//                if (destPiece != null) {
//                    if (!canJumpOverSamePiece && destPiece::class == piece::class) {
//                        break
//                    }
//                    if (count == 1 && destPiece.player != piece.player) {
//                        result.add(BasicMove3D(coordinate, nextCoordinate, piece, player, destPiece))
//                    }
//                    count++
//                } else if (count == 1) {
//                    result.add(BasicMove3D(coordinate, nextCoordinate, piece, player, null))
//                }
//                nextCoordinate = Coordinate3D(nextCoordinate.x + dx, nextCoordinate.y + dy)
//            }
//            return result
//        }
//    }
//
    /**
     * A given move can only occur if it captures a piece
     *
     * @property moveGenerator the move to
     */
    data class CaptureOnly(val moveGenerator: MoveGenerator3D) : MoveGenerator3D {
        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove3D>().filter {
                it.pieceCaptured != null
            }
        }
    }
//
//    /**
//     * A given move can only occur if it does not capture a piece
//     */
//    data class NoCapture(val moveGenerator: MoveGenerator3D) : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove3D>().filter {
//                it.pieceCaptured == null
//            }
//        }
//    }
//
//    /**
//     * A given move can only occur when the piece starts in a specific region
//     *
//     * @property region the region that the piece can start from
//     */
//    data class Restricted(val moveGenerator: MoveGenerator3D, val region: Region3D) : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove3D>().filter {
//                region.isInRegion(it.from)
//            }
//        }
//    }
//
//    /**
//     * A given move can only occur if the destination is within a specific region
//     *
//     * @property region the region that the piece can end in
//     */
//    data class RestrictedDestination(val moveGenerator: MoveGenerator3D, val region: Region3D) : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMove3D>().filter {
//                region.isInRegion(it.to)
//            }
//        }
//    }
//
    /**
     * A piece can be promoted to another piece in a specific region
     *
     * @property moves a list of moves which can results in promotion
     * @property region the region that the promotion can occur
     * @property promoPieces a list of pieces that the piece can be promoted to
     * @property forced true if the piece must be promoted to one of promoPieces
     */
    data class AddPromotion(val moveGenerators: List<MoveGenerator3D>, val region: Region3D, val promoPieces: List<Piece3D>, val forced: Boolean) : MoveGenerator3D {
        constructor(move: MoveGenerator3D, region: Region3D, promoPieces: List<Piece3D>, forced: Boolean) : this(listOf(move), region, promoPieces, forced)
        override fun generate(
            board: Board3D,
            coordinate: Coordinate3D,
            piece: Piece3D,
            player: Player
        ): List<BasicMove3D> {
            val result: MutableList<BasicMove3D> = mutableListOf()
            moveGenerators.forEach { move ->
                move.generate(board, coordinate, piece, player)
                    .filterIsInstance<BasicMove3D>()
                    .forEach {
                        if (region.isInRegion(it.to)) {
                            for (promoPiece in promoPieces) {
                                result.add(
                                    BasicMove3D(
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
                                result.add(BasicMove3D(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
                            }
                        } else {
                            result.add(BasicMove3D(it.from, it.to, it.pieceMoved, player, it.pieceCaptured, it.pieceCapturedCoordinate))
                        }
                    }
            }
            return result
        }
    }
//
//    /**
//     * A wrapper around a list of basic moves to represent composite moves
//     */
//    data class Composite(val moveGenerators: List<MoveGenerator3D>) : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            val iter = moveGenerators.iterator()
//            var moves: List<BasicMove3D> = iter.next().generate(board, coordinate, piece, player).filterIsInstance<BasicMove3D>()
//            while (iter.hasNext()) {
//                val move = iter.next()
//                val newMoves: MutableList<BasicMove3D> = mutableListOf()
//                moves.forEach { prevMove ->
//                    move.generate(board, prevMove.to, prevMove.pieceMoved, player).filterIsInstance<BasicMove3D>().forEach { currMove ->
//                        newMoves.add(
//                            BasicMove3D(
//                                prevMove.from,
//                                currMove.to,
//                                currMove.pieceMoved,
//                                player,
//                                currMove.pieceCaptured,
//                                currMove.pieceCapturedCoordinate,
//                                currMove.piecePromotedTo
//                            )
//                        )
//                    }
//                    moves = newMoves
//                }
//            }
//            return moves
//        }
//    }
//
//    /**
//     * Skip move: a player may decide to pass if there is no safe or desirable move
//     */
//    object Skip : MoveGenerator3D {
//        override fun generate(board: Board3D, coordinate: Coordinate3D, piece: Piece3D, player: Player): List<BasicMove3D> {
//            return listOf(
//                BasicMove3D(coordinate, coordinate, piece, player, null )
//            )
//        }
//    }
}

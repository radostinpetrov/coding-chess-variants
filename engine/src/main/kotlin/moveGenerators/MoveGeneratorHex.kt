package moveGenerators

import boards.BoardHex
import coordinates.Coordinate2D
import moves.*
import pieces.hex.PieceHex
import players.Player
import regions.Region2D

/**
 * Implementation of the Move Generator interface for a 2d square board.
 */
interface MoveGeneratorHex : MoveGenerator<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D> {
    /**
     * Moves along a ray direction until they encounter another piece or the edge of the board
     *
     * @property directions list of directions that the piece can move along
     */
    class Slider(private val directions: List<DirectionHex>) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            val result = mutableListOf<BasicMoveHex>()
            directions.map{
                result.addAll(helper(board, coordinate, piece, it, player))
            }
            return result
        }

        private fun helper(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, direction: DirectionHex, player: Player): List<BasicMoveHex> {
            val result = mutableListOf<BasicMoveHex>()
            val dx = direction.coordinate.x
            val dy = direction.coordinate.y
            var nextCoordinate = Coordinate2D(coordinate.x + dx, coordinate.y + dy)
            while (board.isInBounds(nextCoordinate)) {
                val destPiece = board.getPiece(nextCoordinate)
                if (destPiece == null) {
                    result.add(BasicMoveHex(coordinate, nextCoordinate, piece, player, null))
                    nextCoordinate = Coordinate2D(nextCoordinate.x + dx, nextCoordinate.y + dy)
                } else if (piece.player != destPiece.player) {
                    result.add(BasicMoveHex(coordinate, nextCoordinate, piece, player, destPiece))
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
    data class Stepper(val directions: List<DirectionHex>, val step: Int, val canCapture: Boolean = false) : MoveGeneratorHex {
        constructor(direction: DirectionHex, step: Int, canCapture: Boolean = false) : this(listOf(direction), step, canCapture)
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            val result = mutableListOf<BasicMoveHex>()
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
                        result.add(BasicMoveHex(coordinate, nextCoordinate, piece, player, destPiece))
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
     * @property stepVectors a list of step-vectors
     */
    data class Leaper(val stepVectors: List<Coordinate2D>) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            val result = mutableListOf<BasicMoveHex>()
            val destCoordinates = stepVectors.map { step -> coordinate + step }.distinct()

            for (destCoordinate in destCoordinates) {
                if (board.isInBounds(destCoordinate)) {
                    val destPiece = board.getPiece(destCoordinate)
                    if (destPiece == null || piece.player != destPiece.player) {
                        result.add(BasicMoveHex(coordinate, destCoordinate, piece, player, destPiece))
                    }
                }
            }
            return result
        }
    }

    /**
     * A given move can only occur if it captures a piece
     *
     * @property moveGenerator the move to
     */
    data class CaptureOnly(val moveGenerator: MoveGeneratorHex) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMoveHex>().filter {
                it.pieceCaptured != null
            }
        }
    }

    /**
     * A given move can only occur if it does not capture a piece
     */
    data class NoCapture(val moveGenerator: MoveGeneratorHex) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMoveHex>().filter {
                it.pieceCaptured == null
            }
        }
    }

    /**
     * A given move can only occur when the piece starts in a specific region
     *
     * @property region the region that the piece can start from
     */
    data class Restricted(val moveGenerator: MoveGeneratorHex, val region: Region2D) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMoveHex>().filter {
                region.isInRegion(it.from)
            }
        }
    }

    /**
     * A given move can only occur if the destination is within a specific region
     *
     * @property region the region that the piece can end in
     */
    data class RestrictedDestination(val moveGenerator: MoveGeneratorHex, val region: Region2D) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            return moveGenerator.generate(board, coordinate, piece, player).filterIsInstance<BasicMoveHex>().filter {
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
    data class AddPromotion(val moveGenerators: List<MoveGeneratorHex>, val region: Region2D, val promoPieces: List<PieceHex>, val forced: Boolean) : MoveGeneratorHex {
        constructor(move: MoveGeneratorHex, region: Region2D, promoPieces: List<PieceHex>, forced: Boolean) : this(listOf(move), region, promoPieces, forced)
        override fun generate(
            board: BoardHex,
            coordinate: Coordinate2D,
            piece: PieceHex,
            player: Player
        ): List<BasicMoveHex> {
            val result: MutableList<BasicMoveHex> = mutableListOf()
            moveGenerators.forEach { move ->
                move.generate(board, coordinate, piece, player)
                    .filterIsInstance<BasicMoveHex>()
                    .forEach {
                        if (region.isInRegion(it.to)) {
                            for (promoPiece in promoPieces) {
                                result.add(
                                    BasicMoveHex(
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
                                result.add(BasicMoveHex(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
                            }
                        } else {
                            result.add(BasicMoveHex(it.from, it.to, it.pieceMoved, player, it.pieceCaptured, it.pieceCapturedCoordinate))
                        }
                    }
            }
            return result
        }
    }

    /**
     * A wrapper around a list of basic moves to represent composite moves
     */
    data class Composite(val moveGenerators: List<MoveGeneratorHex>) : MoveGeneratorHex {
        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
            val iter = moveGenerators.iterator()
            var moves: List<BasicMoveHex> = iter.next().generate(board, coordinate, piece, player).filterIsInstance<BasicMoveHex>()
            while (iter.hasNext()) {
                val move = iter.next()
                val newMoves: MutableList<BasicMoveHex> = mutableListOf()
                moves.forEach { prevMove ->
                    move.generate(board, prevMove.to, prevMove.pieceMoved, player).filterIsInstance<BasicMoveHex>().forEach { currMove ->
                        newMoves.add(
                            BasicMoveHex(
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

//    /**
//     * Skip move: a player may decide to pass if there is no safe or desirable move
//     */
//    object Skip : MoveGeneratorHex {
//        override fun generate(board: BoardHex, coordinate: Coordinate2D, piece: PieceHex, player: Player): List<BasicMoveHex> {
//            return listOf(
//                BasicMoveHex(coordinate, coordinate, piece, player, null )
//            )
//        }
//    }
}

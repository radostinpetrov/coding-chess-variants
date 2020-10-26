package moves.visitors

import Coordinate
import GameMove
import boards.Board2D
import moves.Direction
import moves.Move
import pieces.Piece

class Board2DMoveVisitor(override val board: Board2D): MoveVisitor<Board2D> {
    override fun visit(coordinate: Coordinate, piece: Piece, move: Move): List<GameMove> {
        return when (move) {
            is Move.Slider -> {
                val result = mutableListOf<GameMove>()
                if (move.H) {
                    result.addAll(visitSlider(coordinate, piece, 1, 0))
                    result.addAll(visitSlider(coordinate, piece, -1, 0))
                }
                if (move.V) {
                    result.addAll(visitSlider(coordinate, piece, 0, 1))
                    result.addAll(visitSlider(coordinate, piece, 0, -1))
                }
                if (move.D) {
                    result.addAll(visitSlider(coordinate, piece, 1, 1))
                    result.addAll(visitSlider(coordinate, piece, -1, -1))
                }
                if (move.A) {
                    result.addAll(visitSlider(coordinate, piece, 1, -1))
                    result.addAll(visitSlider(coordinate, piece, -1, 1))
                }
                result
            }
            is Move.Stepper -> {
                visitStepper(coordinate, piece, move.direction, move.step, move.canCapture)
            }
            is Move.Leaper -> {
                visitLeaper(coordinate, piece, move.dx, move.dy)
            }
            is Move.CaptureOnly -> {
                visit(coordinate, piece, move.move).filter {
                    it.pieceCaptured != null
                }
            }
            is Move.Restricted -> {
                visit(coordinate, piece, move.move).filter {
                    val validX = move.x.isEmpty() || move.x.contains(it.from.x)
                    val validY = move.y.isEmpty() || move.y.contains(it.from.y)
                    validX && validY
                }
            }
            is Move.AddForcedPromotion -> {
                val result: MutableList<GameMove> = mutableListOf()
                visit(coordinate, piece, move.move).forEach {
                    val validX = move.x.isEmpty() || move.x.contains(it.to.x)
                    val validY = move.y.isEmpty() || move.y.contains(it.to.y)
                    if (validX && validY) {
                        for (promoPiece in move.promoPieces) {
                            result.add(GameMove(it.from, it.to, it.pieceMoved, it.pieceCaptured, promoPiece))
                        }
                    } else {
                        result.add(it)
                    }
                }
                return result
            }
        }
    }

    private fun visitSlider(coordinate: Coordinate, piece: Piece, dx: Int, dy: Int): List<GameMove> {
        val result = mutableListOf<GameMove>()
        var nextCoordinate = Coordinate(coordinate.x + dx, coordinate.y + dy)
        while (board.isInBounds(nextCoordinate)) {
            val destPiece = board.getPiece(nextCoordinate)
            if (destPiece == null) {
                result.add(GameMove(coordinate, nextCoordinate, piece, null, null))
                nextCoordinate = Coordinate(nextCoordinate.x + dx, nextCoordinate.y + dy)
            } else if (piece.player != destPiece.player) {
                result.add(GameMove(coordinate, nextCoordinate, piece, destPiece, null))
                break
            } else {
                break
            }
        }
        return result
    }

    private fun visitStepper(coordinate: Coordinate, piece: Piece, direction: Direction, steps: Int, canCapture: Boolean): List<GameMove> {
        val result = mutableListOf<GameMove>()
        var step = 1
        var nextCoordinate = Coordinate(
            coordinate.x + direction.coordinate.x,
            coordinate.y + direction.coordinate.y
        )
        while (step < steps) {
            if (!board.isInBounds(nextCoordinate) || board.getPiece(nextCoordinate) != null) {
                return result
            }
            nextCoordinate = Coordinate(
                nextCoordinate.x + direction.coordinate.x,
                nextCoordinate.y + direction.coordinate.y
            )
            step++
        }

        val destPiece = board.getPiece(nextCoordinate)
        if (board.isInBounds(nextCoordinate)) {
            if (destPiece == null || (canCapture && destPiece.player != piece.player)) {
                result.add(GameMove(coordinate, nextCoordinate, piece, destPiece, null))
            }
        }

        return result
    }

    private fun visitLeaper(coordinate: Coordinate, piece: Piece, dx: Int, dy: Int): List<GameMove> {
        val result = mutableListOf<GameMove>()
        val destCoordinates = listOf(
            Coordinate(coordinate.x + dx, coordinate.y + dy),
            Coordinate(coordinate.x + dx, coordinate.y - dy),
            Coordinate(coordinate.x - dx, coordinate.y + dy),
            Coordinate(coordinate.x - dx, coordinate.y - dy),
            Coordinate(coordinate.x + dy, coordinate.y + dx),
            Coordinate(coordinate.x + dy, coordinate.y - dx),
            Coordinate(coordinate.x - dy, coordinate.y + dx),
            Coordinate(coordinate.x - dy, coordinate.y - dx),
        )

        for (destCoordinate in destCoordinates) {
            if (board.isInBounds(destCoordinate)) {
                val destPiece = board.getPiece(destCoordinate)
                if (destPiece == null || piece.player != destPiece.player) {
                    result.add(GameMove(coordinate, destCoordinate, piece, destPiece, null))
                }
            }
        }
        return result
    }
}

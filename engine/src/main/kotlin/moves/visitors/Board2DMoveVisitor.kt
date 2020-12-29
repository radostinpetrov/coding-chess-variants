//package moves.visitors
//
//import Coordinate
//import gameMoves.GameMove
//import boards.Board2D
//import moves.Direction
//import moves.Move2D
//import pieces.Piece
//import players.Player
//
//class Board2DMoveVisitor(override val board: Board2D) : MoveVisitor<Board2D> {
//    override fun visit(coordinate: Coordinate, piece: Piece, move: Move2D, player: Player): List<gameMoves.GameMove.BasicGameMove> {
//        return when (move) {
//            is Move2D.Slider -> {
//                val result = mutableListOf<gameMoves.GameMove.BasicGameMove>()
//                if (move.H) {
//                    result.addAll(visitSlider(coordinate, piece, 1, 0, player))
//                    result.addAll(visitSlider(coordinate, piece, -1, 0, player))
//                }
//                if (move.V) {
//                    result.addAll(visitSlider(coordinate, piece, 0, 1, player))
//                    result.addAll(visitSlider(coordinate, piece, 0, -1, player))
//                }
//                if (move.D) {
//                    result.addAll(visitSlider(coordinate, piece, 1, 1, player))
//                    result.addAll(visitSlider(coordinate, piece, -1, -1, player))
//                }
//                if (move.A) {
//                    result.addAll(visitSlider(coordinate, piece, 1, -1, player))
//                    result.addAll(visitSlider(coordinate, piece, -1, 1, player))
//                }
//                result
//            }
//            is Move2D.Stepper -> {
//                visitStepper(coordinate, piece, move.direction, move.step, move.canCapture, player)
//            }
//            is Move2D.Leaper -> {
//                visitLeaper(coordinate, piece, move.dx, move.dy, player)
//            }
//            is Move2D.Hopper -> {
//                val result = mutableListOf<gameMoves.GameMove.BasicGameMove>()
//                if (move.HV) {
//                    result.addAll(visitHopper(coordinate, piece, 1, 0, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, -1, 0, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, 0, 1, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, 0, -1, move.canJumpOverSamePiece, player))
//                }
//                if (move.D) {
//                    result.addAll(visitHopper(coordinate, piece, 1, 1, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, -1, 1, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, 1, -1, move.canJumpOverSamePiece, player))
//                    result.addAll(visitHopper(coordinate, piece, -1, -1, move.canJumpOverSamePiece, player))
//                }
//                result
//            }
//            is Move2D.CaptureOnly -> {
//                visit(coordinate, piece, move.move, player).filter {
//                    it.pieceCaptured != null
//                }
//            }
//            is Move2D.NoCapture -> {
//                visit(coordinate, piece, move.move, player).filter {
//                    it.pieceCaptured == null
//                }
//            }
//            is Move2D.Restricted -> {
//                visit(coordinate, piece, move.move, player).filter {
//                    move.region.isInRegion(it.from)
//                }
//            }
//            is Move2D.RestrictedDestination -> {
//                visit(coordinate, piece, move.move, player).filter {
//                    move.region.isInRegion(it.from)
//                }
//            }
//            is Move2D.AddPromotion -> {
//                val result: MutableList<gameMoves.GameMove.BasicGameMove> = mutableListOf()
//                visit(coordinate, piece, move.move, player).forEach {
//                    val valid = move.region.isInRegion(it.to)
//                    if (valid) {
//                        for (promoPiece in move.promoPieces) {
//                            result.add(gameMoves.GameMove.BasicGameMove(it.from, it.to, it.pieceMoved, player, it.pieceCaptured, promoPiece))
//                        }
//                        if (!move.forced) {
//                            result.add(gameMoves.GameMove.BasicGameMove(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
//                        }
//                    } else {
//                        result.add(gameMoves.GameMove.BasicGameMove(it.from, it.to, it.pieceMoved, player, it.pieceCaptured))
//                    }
//                }
//                return result
//            }
//            is Move2D.Composite -> {
//                var isEmpty = false
//                val iter = move.moves.iterator()
//                var gameMoves: List<gameMoves.GameMove.BasicGameMove> = visit(coordinate, piece, iter.next(), player)
//                while (iter.hasNext()) {
//                    val move = iter.next()
//                    val newGameMoves: MutableList<gameMoves.GameMove.BasicGameMove> = mutableListOf()
//                    gameMoves.forEach { prevMove ->
//                        visit(prevMove.to, prevMove.pieceMoved, move, player).forEach { currMove ->
//                            newGameMoves.add(gameMoves.GameMove.BasicGameMove(prevMove.from, currMove.to, currMove.pieceMoved, player, currMove.pieceCaptured, currMove.piecePromotedTo))
//                        }
//                    }
//                    gameMoves = newGameMoves
//                }
//                return gameMoves
//            }
//        }
//    }
//
//    private fun visitSlider(coordinate: Coordinate, piece: Piece, dx: Int, dy: Int, player: Player): List<gameMoves.GameMove.BasicGameMove> {
//        return listOf()
//    }
//
//    private fun visitStepper(coordinate: Coordinate, piece: Piece, direction: Direction, steps: Int, canCapture: Boolean, player: Player): List<gameMoves.GameMove.BasicGameMove> {
//        val result = mutableListOf<gameMoves.GameMove.BasicGameMove>()
//        var step = 1
//        var nextCoordinate = Coordinate(
//            coordinate.x + direction.coordinate.x,
//            coordinate.y + direction.coordinate.y
//        )
//        while (step < steps) {
//            if (!board.isInBounds(nextCoordinate) || board.getPiece(nextCoordinate) != null) {
//                return result
//            }
//            nextCoordinate = Coordinate(
//                nextCoordinate.x + direction.coordinate.x,
//                nextCoordinate.y + direction.coordinate.y
//            )
//            step++
//        }
//
//        val destPiece = board.getPiece(nextCoordinate)
//        if (board.isInBounds(nextCoordinate)) {
//            if (destPiece == null || (canCapture && destPiece.player != piece.player)) {
//                result.add(gameMoves.GameMove.BasicGameMove(coordinate, nextCoordinate, piece, player, destPiece, null))
//            }
//        }
//
//        return result
//    }
//
//    private fun visitLeaper(coordinate: Coordinate, piece: Piece, dx: Int, dy: Int, player: Player): List<gameMoves.GameMove.BasicGameMove> {
//        val result = mutableListOf<gameMoves.GameMove.BasicGameMove>()
//        val destCoordinates = listOf(
//            Coordinate(coordinate.x + dx, coordinate.y + dy),
//            Coordinate(coordinate.x + dx, coordinate.y - dy),
//            Coordinate(coordinate.x - dx, coordinate.y + dy),
//            Coordinate(coordinate.x - dx, coordinate.y - dy),
//            Coordinate(coordinate.x + dy, coordinate.y + dx),
//            Coordinate(coordinate.x + dy, coordinate.y - dx),
//            Coordinate(coordinate.x - dy, coordinate.y + dx),
//            Coordinate(coordinate.x - dy, coordinate.y - dx),
//        )
//
//        for (destCoordinate in destCoordinates) {
//            if (board.isInBounds(destCoordinate)) {
//                val destPiece = board.getPiece(destCoordinate)
//                if (destPiece == null || piece.player != destPiece.player) {
//                    result.add(gameMoves.GameMove.BasicGameMove(coordinate, destCoordinate, piece, player, destPiece, null))
//                }
//            }
//        }
//        return result
//    }
//
//    private fun visitHopper(coordinate: Coordinate, piece: Piece, dx: Int, dy: Int, canJumpOverSamePiece: Boolean, player: Player): List<gameMoves.GameMove.BasicGameMove> {
//        val result = mutableListOf<gameMoves.GameMove.BasicGameMove>()
//        var nextCoordinate = Coordinate(coordinate.x + dx, coordinate.y + dy)
//        var count = 0
//
//        while (board.isInBounds(nextCoordinate) && count < 2) {
//            val destPiece = board.getPiece(nextCoordinate)
//            if (destPiece != null) {
//                if (!canJumpOverSamePiece && destPiece::class == piece::class) {
//                    break
//                }
//                if (count == 1 && destPiece.player != piece.player) {
//                    result.add(gameMoves.GameMove.BasicGameMove(coordinate, nextCoordinate, piece, player, destPiece, null))
//                }
//                count++
//            } else if (count == 1) {
//                result.add(gameMoves.GameMove.BasicGameMove(coordinate, nextCoordinate, piece, player, null, null))
//            }
//            nextCoordinate = Coordinate(nextCoordinate.x + dx, nextCoordinate.y + dy)
//        }
//        return result
//    }
//}

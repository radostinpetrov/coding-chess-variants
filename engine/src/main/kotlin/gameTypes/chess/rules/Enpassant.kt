package gameTypes.chess.rules

import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import pieces.chess.BlackPawn
import pieces.chess.WhitePawn
import players.Player
import kotlin.math.abs

class Enpassant : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player, moves: MutableList<GameMove2D>) {
        val board = game.board
        val moveLog = game.moveLog
        val res = mutableListOf<GameMove2D>()
        if (moveLog.isEmpty()) {
            return
        }
        val prevMove = moveLog[moveLog.size - 1]
        if (!(prevMove is GameMove2D.SimpleGameMove.BasicGameMove && ((prevMove.pieceMoved is WhitePawn || prevMove.pieceMoved is BlackPawn) && abs(prevMove.from.y - prevMove.to.y) == 2))) {
            return
        }
        val pawns = board.getPieces(player).filter { p -> (p.first is WhitePawn || p.first is BlackPawn) && (p.second.y == prevMove.to.y) && (abs(p.second.x - prevMove.to.x) == 1) }
        for (pawn in pawns) {
            val dy = if (pawn.first is BlackPawn) -1 else 1
            res.add(
                GameMove2D.CompositeGameMove(
                    listOf(
                        GameMove2D.SimpleGameMove.BasicGameMove(
                            Coordinate2D(pawn.second.x, pawn.second.y),
                            Coordinate2D(prevMove.to.x, prevMove.to.y), pawn.first, player, prevMove.pieceMoved, checkForCheck = false),
                        GameMove2D.SimpleGameMove.BasicGameMove(
                            Coordinate2D(prevMove.to.x, prevMove.to.y),
                            Coordinate2D(prevMove.to.x, prevMove.to.y + dy), pawn.first, player)
                    ),
                    player
                )
            )
        }
        moves.addAll(res)
    }
}

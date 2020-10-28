package main.kotlin.gameTypes.chess.rules

import main.kotlin.Coordinate
import main.kotlin.GameMove
import main.kotlin.gameTypes.GameType
import main.kotlin.gameTypes.chess.AbstractChess
import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.players.Player
import pieces.BlackPawn
import pieces.Rook
import pieces.WhitePawn
import kotlin.math.abs

class Enpassant : SpecialRules<AbstractChess> {
    override fun getPossibleMoves(game: AbstractChess, player: Player): List<GameMove> {
        val board = game.board
        val moveLog = game.moveLog
        val res = mutableListOf<GameMove>()
        if (moveLog.isEmpty()) {
            return res
        }
        val prevMove = moveLog[moveLog.size - 1]
        if (!(prevMove is GameMove.BasicGameMove && ((prevMove.pieceMoved is WhitePawn || prevMove.pieceMoved is BlackPawn) && abs(prevMove.from.y - prevMove.to.y) == 2))) {
            return res
        }
        val pawns = board.getPieces(player).filter {p -> (p.first is WhitePawn || p.first is BlackPawn) && (p.second.y ==prevMove.to.y) && (abs(p.second.x - prevMove.to.x) == 1)}
        for (pawn in pawns) {
            val dy = if (pawn.first is BlackPawn) -1 else 1
            res.add(GameMove.CompositeGameMove(listOf(
                    GameMove.BasicGameMove(Coordinate(pawn.second.x, pawn.second.y), Coordinate(prevMove.to.x, prevMove.to.y), pawn.first, player, prevMove.pieceMoved, checkForCheck = false),
                    GameMove.BasicGameMove(Coordinate(prevMove.to.x, prevMove.to.y), Coordinate(prevMove.to.x, prevMove.to.y+dy), pawn.first, player)
            ), player))
        }
        return res
    }
}
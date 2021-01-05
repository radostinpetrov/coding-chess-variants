package gameTypes.chess.winconditions

import Outcome
import gameMoves.GameMove2D
import gameTypes.chess.AbstractChess
import players.Player

class InsufficientMaterialStalemate : WinCondition<AbstractChess> {
    override fun evaluate(game: AbstractChess, player: Player, moves: List<GameMove2D>): Outcome? {

        val board = game.board
        val pieces = board.getPieces()
        val piecesBySymbol = pieces.map { it.first.getSymbol() }

        val kingKnightVsSoleKing = piecesBySymbol.filter { it == "K" }.size == 2 &&
            piecesBySymbol.filter { it == "N" }.size == 1 &&
            pieces.size == 3

        var draw: Boolean
        draw = piecesBySymbol.filter { it == "K" }.size == 2
        draw = draw && piecesBySymbol.filter { it == "B" }.size == pieces.size - 2

        // checking for bishops to be on the same colour
        if (draw && piecesBySymbol.filter { it == "B" }.size > 1) {
            var x_parity = -1
            var y_parity = -1
            for (piece in pieces) {
                if (piece.first.getSymbol() == "B") {
                    if (x_parity == -1 && y_parity == -1) {
                        x_parity = piece.second.x % 2
                        y_parity = piece.second.y % 2
                    } else {
                        draw = (x_parity == y_parity && piece.second.x % 2 == piece.second.y % 2) ||
                            (x_parity != y_parity && piece.second.x % 2 != piece.second.y % 2)
                    }
                }
            }
        }

        if (kingKnightVsSoleKing || draw) {
            return Outcome.Draw("Stalemate by Insufficient Material")
        }

        return null
    }
}

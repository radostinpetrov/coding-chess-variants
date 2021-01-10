package endconditions

import moves.Move2D
import gameTypes.chess.AbstractChess2D
import players.Player

/**
 * Condition for stalemate by insufficient material
 */
class InsufficientMaterialStalemate : EndCondition2D<AbstractChess2D> {
    override fun evaluate(game: AbstractChess2D, player: Player, moves: List<Move2D>): Outcome? {

        val board = game.board
        val pieces = board.getPieces()
        val piecesBySymbol = pieces.map { it.first.getSymbol() }

        val kingKnightOrCardinalVsSoleKing = piecesBySymbol.filter { it == "K" || it == "C" }.size == 2 &&
            piecesBySymbol.filter { it == "N" }.size == 1 &&
            pieces.size == 3

        var draw: Boolean
        draw = piecesBySymbol.filter { it == "K" }.size == 2
        draw = draw && piecesBySymbol.filter { it == "B" }.size == pieces.size - 2

        // checking for bishops to be on the same colour
        if (draw && piecesBySymbol.filter { it == "B" }.size > 1) {
            var xParity = -1
            var yParity = -1
            for (piece in pieces) {
                if (piece.first.getSymbol() == "B") {
                    if (xParity == -1 && yParity == -1) {
                        xParity = piece.second.x % 2
                        yParity = piece.second.y % 2
                    } else {
                        draw = (xParity == yParity && piece.second.x % 2 == piece.second.y % 2) ||
                            (xParity != yParity && piece.second.x % 2 != piece.second.y % 2)
                    }
                }
            }
        }

        if (kingKnightOrCardinalVsSoleKing || draw) {
            return Outcome.Draw("Stalemate by Insufficient Material")
        }

        return null
    }
}

package moves

import Coordinate
import GameMove
import gameTypes.GameType

interface Move {
    // checks for valid moves other than checkmate, stalemate, en passant and castles
    fun getPossibleMoves(gameType: GameType, from: Coordinate): List<GameMove>
}
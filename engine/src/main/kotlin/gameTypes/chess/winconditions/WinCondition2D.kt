package gameTypes.chess.winconditions

import Outcome
import boards.Board2D
import coordinates.Coordinate2D
import gameMoves.GameMove2D
import gameTypes.GameType
import gameTypes.GameType2D
import gameTypes.WinCondition
import moves.Move2D
import pieces.Piece2D
import players.Player

interface WinCondition2D<G: GameType2D> : WinCondition<G, Board2D, Move2D, GameMove2D, Piece2D, Coordinate2D>

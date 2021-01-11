package gameTypes.hex

import boards.BoardHex
import coordinates.Coordinate2D
import gameTypes.GameType
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex

/**
 * Implementation of the GameType interface for a 2d hexagonal board.
 */
interface GameTypeHex : GameType<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>

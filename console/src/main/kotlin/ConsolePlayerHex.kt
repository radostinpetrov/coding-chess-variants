
import boards.BoardHex
import coordinates.Coordinate2D
import moveGenerators.MoveGeneratorHex
import pieces.hex.PieceHex

interface ConsolePlayerHex : ConsolePlayer<BoardHex, MoveGeneratorHex, PieceHex, Coordinate2D>

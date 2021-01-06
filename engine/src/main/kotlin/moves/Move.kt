package moves

import boards.Board
import coordinates.Coordinate
import moveGenerators.MoveGenerator
import pieces.Piece

interface Move<B : Board<B, MG, M, P, C>, MG : MoveGenerator<B, MG, M, P, C>, M : Move<B, MG, M, P, C>, P : Piece<B, MG, M, P, C>, C : Coordinate>

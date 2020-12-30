package gameMoves

import boards.Board
import coordinates.Coordinate
import moves.Move
import pieces.Piece

interface GameMove<B : Board<B, M, GM, P, C>, M : Move<B, M, GM, P, C>, GM : GameMove<B, M, GM, P, C>, P : Piece<B, M, GM, P, C>, C : Coordinate>

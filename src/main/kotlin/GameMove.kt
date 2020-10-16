import pieces.Piece

data class GameMove(val from: Coordinate, val to: Coordinate, val pieceMoved: Piece, val pieceCaptured: Piece? = null, val piecePromotedTo:Piece? = null)
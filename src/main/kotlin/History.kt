import pieces.Piece

data class History(val board:Board, val from: Coordinate, val to: Coordinate, val pieceMoved: Piece, val pieceCaptured: Piece?, val piecePromotedTo:Piece?, val algebraicNotation: String)
import pieces.Piece

interface Board {
    fun getBoardState(): Array<Array<Piece>>
}

import boards.Board
import coordinates.Coordinate
import gameTypes.GameType
import moveGenerators.MoveGenerator
import moves.Move
import pieces.Piece
import players.Player

interface ConsolePlayer<B : Board<B, MG, P, C>, MG : MoveGenerator<B, MG, P, C>, P : Piece<B, MG, P, C>, C : Coordinate> {
    val gameType: GameType<B, MG, P, C>
    val player: Player
    fun playTurn() {
        val moves = gameType.getValidMoves(player)
        if (moves.isEmpty()) {
            return
        }
        gameType.playerMakeMove(getTurn(moves))
    }

    fun getTurn(choiceOfMoves: List<Move<B, MG, P, C>>): Move<B, MG, P, C>
}

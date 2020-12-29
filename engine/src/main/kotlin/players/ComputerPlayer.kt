//package players
//
//import gameMoves.GameMove
//
//class ComputerPlayer(val delay: Long) : ConsolePlayer() {
//    override var playerMove: gameMoves.GameMove? = null
//
//    override fun getTurn(choiceOfMoves: List<gameMoves.GameMove>): gameMoves.GameMove? {
//        println("Computer is thinking...")
//        Thread.sleep(0)
//        return choiceOfMoves[(choiceOfMoves.indices).random()]
//    }
//}

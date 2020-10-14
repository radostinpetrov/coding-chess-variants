package players

import GameMove

interface Player {
    //returns the valid chosen move
    fun getTurn(): GameMove
}
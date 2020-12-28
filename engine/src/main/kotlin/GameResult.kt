import players.Player

sealed class GameResult

class Win(val winner: Player) : GameResult()
class Stalemate() : GameResult()

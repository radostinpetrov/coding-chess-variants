import boards.Board
import coordinates.Coordinate
import gameTypes.AbstractChess
import gameTypes.checkers.Checkers
import gameTypes.chess.*
import gameTypes.chess3d.AbstractChess3D
import gameTypes.chess3d.RaumschachChess
import gameTypes.hex.AbstractChessHex
import gameTypes.hex.HexagonalChess
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import moveGenerators.MoveGenerator
import pieces.Piece

object Chess {
    val mappedVariants = mapOf(
        1 to StandardChess(),
        2 to GrandChess(),
        3 to CapablancaChess(),
        4 to Chess960(),
        5 to MiniChess(),
        6 to BalbosGame(),
        7 to AntiChess(),
        8 to Xiangqi(),
        9 to Janggi(),
        10 to Checkers(),
        11 to RaumschachChess(),
        12 to HexagonalChess()
    )
}

fun main() {
    println("Let's play chess!")
    println("Input the index of the variant you want to play. ")

    for ((i, v) in Chess.mappedVariants) {
        println("$i: ${v.name}")
    }

    var input = readLine()
    while (input == null) {
        input = readLine()
    }

    val chess = Chess.mappedVariants[input.toInt()]!!

    println("Select players:")
    println("1: Human vs Human")
    println("2: Human vs Computer")
    println("3: Computer vs Human")
    println("4: Computer vs Computer")

    input = readLine()
    while (input == null) {
        input = readLine()
    }
    val selection = input.toInt()
    if (chess is AbstractChess2D) {
        val player1 = if (selection <= 2) {
            HumanConsolePlayer2D(chess, chess.players[0])
        } else {
            ComputerConsolePlayer2D(10, chess, chess.players[0])
        }

        val player2 = if (selection % 2 == 1) {
            HumanConsolePlayer2D(chess, chess.players[1])
        } else {
            ComputerConsolePlayer2D(10, chess, chess.players[1])
        }

        val game = ConsoleGameHelper(chess, player1, player2)
        game.start()
    } else if (chess is AbstractChess3D) {
        val player1 = if (selection <= 2) {
            HumanConsolePlayer3D(chess, chess.players[0])
        } else {
            ComputerConsolePlayer3D(10, chess, chess.players[0])
        }

        val player2 = if (selection % 2 == 1) {
            HumanConsolePlayer3D(chess, chess.players[1])
        } else {
            ComputerConsolePlayer3D(10, chess, chess.players[1])
        }

        val game = ConsoleGameHelper(chess, player1, player2)
        game.start()
    } else if (chess is AbstractChessHex) {
        val player1 = if (selection <= 2) {
            HumanConsolePlayerHex(chess, chess.players[0])
        } else {
            ComputerConsolePlayerHex(10, chess, chess.players[0])
        }

        val player2 = if (selection % 2 == 1) {
            HumanConsolePlayerHex(chess, chess.players[1])
        } else {
            ComputerConsolePlayerHex(10, chess, chess.players[1])
        }

        val game = ConsoleGameHelper(chess, player1, player2)
        game.start()
    } else {
        throw Exception("Doomed")
    }
}

typealias Game = AbstractChess<out Board<*, out MoveGenerator<*, *, *, *>, out Piece<*, *, *, *>, out Coordinate>, out MoveGenerator<out Board<*, *, *, *>, *, out Piece<*, *, *, *>, out Coordinate>, out Piece<out Board<*, *, *, *>, out MoveGenerator<*, *, *, *>, *, out Coordinate>, out Coordinate>

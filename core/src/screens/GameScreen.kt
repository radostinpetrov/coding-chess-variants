package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.MyGdxGame
import com.mygdx.game.PlayerType
import com.mygdx.game.assets.Textures
import ktx.app.KtxScreen
import main.kotlin.Coordinate
import main.kotlin.Game
import main.kotlin.GameMove
import main.kotlin.players.ComputerPlayer
import main.kotlin.players.HumanPlayer
import main.kotlin.players.Player
import main.kotlin.gameTypes.xiangqi.Janggi
import main.kotlin.gameTypes.xiangqi.Xiangqi

class GameScreen(val game: MyGdxGame, val gameEngine: Game, val players: MutableList<PlayerType>) : KtxScreen {
    private val windowHeight: Int = 800
    
    private val possibleMoveCircleRadius = 8f
    private val possibleMoveColour = Color(Color.rgba4444(30f, 76f, 63f, 0.75f))
    private lateinit var shapeRenderer: ShapeRenderer

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    val gameType = gameEngine.gameType
    var board = gameType.board
    val rows = board.n
    val columns = board.m

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    var currPlayer: Player? = null

    private val textures = Textures(game.assets)

    var playerMove: GameMove? = null

    // todo: hard coded, enum for colour ?
    var playerMapping: Map<Player, Color>? = null

    override fun show() {
        Gdx.input.inputProcessor = Stage()
        if (rows != columns) {
            val windowWidth = (windowHeight * columns) / rows
            Gdx.graphics.setWindowedMode(windowWidth, windowHeight)
            game.batch.projectionMatrix.setToOrtho2D(0f, 0f, windowWidth.toFloat(), windowHeight.toFloat())
        }
        createPlayers()
        shapeRenderer = ShapeRenderer()
        currPlayer = gameType.getCurrentPlayer()
        playerMapping = mapOf(currPlayer!! to Color.WHITE, gameType.getNextPlayer() to Color.BLACK)
        gameEngine.start()
    }

    private fun createPlayers() {
        val firstPlayer = createPlayer(players[0])
        val secondPlayer = createPlayer(players[1])
        gameType.addPlayer(secondPlayer)
        gameType.addPlayer(firstPlayer)
    }

    private fun createPlayer(player: PlayerType): Player {
        return if (player == PlayerType.HUMAN) {
            createHumanPlayer()
        } else {
            createComputerPlayer()
        }
    }

    private fun createComputerPlayer() = ComputerPlayer(200)

    private fun createHumanPlayer(): HumanPlayer {
        return object : HumanPlayer() {
            override fun getTurn(choiceOfMoves: List<GameMove>): GameMove? {
                val temp = playerMove
                playerMove = null
                if (temp != null) {
                    srcX = null
                    srcY = null
                    dstX = null
                    dstY = null
                }
                return temp
            }
        }
    }

    fun switchToGameOverScreen(player: Player) {
        game.removeScreen<GameOverScreen>()

        //change this.
        val playerName = playerMapping?.get(player)!!.toString()

        game.addScreen(GameOverScreen(game, gameEngine, playerName!!))
        game.setScreen<GameOverScreen>()
    }

    override fun render(delta: Float) {
        currPlayer = gameType.getCurrentPlayer()
        val moves = gameEngine.gameType.getValidMoves(currPlayer!!)
        if (!gameEngine.turn()) {
            switchToGameOverScreen(currPlayer!!)
        }

        drawBoard(moves)
        drawPieces()
        drawDots(moves)
        controls()
    }

    private fun reverseRow(index: Int) {
        val b = board.board

        for (i in 0 until columns / 2) {
            val tmpPiece = b[index][i]
            b[index][i] = b[index][columns - i - 1]
            b[index][columns - i - 1] = tmpPiece
        }
    }

    private fun flipBoard() {
        val b = board.board
        if (rows % 2 != 0) {
            reverseRow(rows / 2)
        }

        for (i in 0 until (rows / 2) - 1) {
            for (j in 0 until columns) {
                val tmpPiece = b[i][j]
                b[i][j] = b[rows - i - 1][columns - j - 1]
                b[rows - i - 1][columns - j - 1] = tmpPiece
            }
        }
    }

    private fun controls() {
        val input = Gdx.input
        val graphics = Gdx.graphics
        if (input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (srcX == null) {
                srcX = input.x
                srcY = graphics.height - input.y
            } else if (srcX != null && dstX == null) {
                dstX = input.x
                dstY = graphics.height - input.y
            } else {
                srcX = input.x
                srcY = graphics.height - input.y
                dstX = null
                dstY = null
            }
        }

        if (input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            srcX = null
            srcY = null
            dstX = null
            dstY = null
        }
    }

    private fun drawBoard(moves: List<GameMove>, flipped: Boolean = false) {
        when (gameType) {
            is Xiangqi, is Janggi -> drawXiangqiBoard(moves)
            else -> drawChessBoard(moves)
        }
    }

    private fun drawLineWithCenterOffset(x1: Int , y1: Int, x2: Int, y2: Int, width: Float) {
        val offset = squareWidth/2
        shapeRenderer.rectLine(squareWidth * x1 + offset, squareWidth * y1 + offset, squareWidth * x2 + offset,  squareWidth * y2 + offset, width)
    }

    private fun drawXiangqiBoard(moves: List<GameMove>, flipped: Boolean = false) {
        val lineWidth = 4f
        Gdx.gl.glClearColor(1f, 0.7f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        shapeRenderer.color = Color.BROWN
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        for (j in 0 until rows) {
            drawLineWithCenterOffset(0, j, columns - 1, j, lineWidth)
        }
        for (j in 0 until columns) {
            drawLineWithCenterOffset(j, 0, j, 4, lineWidth)
            drawLineWithCenterOffset(j, 5, j, 9, lineWidth)
        }
        drawLineWithCenterOffset(3, 0, 5, 2, lineWidth)
        drawLineWithCenterOffset(5, 0, 3, 2, lineWidth)
        drawLineWithCenterOffset(3, 7, 5, 9, lineWidth)
        drawLineWithCenterOffset(5, 7, 3, 9, lineWidth)

        shapeRenderer.end()
        if (dstX != null && dstY != null) {
            if (srcX != null && srcY != null) {
                playerMove = getMove(
                    getPieceCoordinateFromMousePosition(srcX!!, srcY!!),
                    getPieceCoordinateFromMousePosition(dstX!!, dstY!!),
                    moves
                )
            }
        }
    }

    private fun drawChessBoard(moves: List<GameMove>, flipped: Boolean = false) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        var colour1: Color = Color.TAN
        var colour2: Color = Color.BROWN

        if (flipped) {
            colour1 = Color.BROWN
            colour2 = Color.TAN
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        for (i in 0 until columns) {
            for (j in 0 until rows) {
                if ((i + j) % 2 == 0) {
                    shapeRenderer.color = colour1
                } else {
                    shapeRenderer.color = colour2
                }

                if (srcX != null && srcY != null) {
                    if (squareWidth * i <= srcX!! && srcX!! < squareWidth * (i + 1) && squareWidth * j <= srcY!! && srcY!! < squareWidth * (j + 1)) {
                        shapeRenderer.color = Color.FOREST
                    }
                }
                if (dstX != null && dstY != null) {
                    if (squareWidth * i <= dstX!! && dstX!! < squareWidth * (i + 1) && squareWidth * j <= dstY!! && dstY!! < squareWidth * (j + 1)) {
                        shapeRenderer.color = Color.GREEN
                        if (srcX != null && srcY != null) {
                            playerMove = getMove(
                                getPieceCoordinateFromMousePosition(srcX!!, srcY!!),
                                getPieceCoordinateFromMousePosition(dstX!!, dstY!!),
                                moves
                            )
                        }
                    }
                }
                shapeRenderer.rect(squareWidth * i, squareWidth * j, squareWidth, squareWidth)
            }
        }

        shapeRenderer.end()
    }

    private fun getMove(from: Coordinate, to: Coordinate, moves: List<GameMove>): GameMove? {
        val playerMove = moves.filter { m -> m.displayFrom == from && m.displayTo == to }
        if (playerMove.isEmpty()) {
            return null
        }

        // TODO promotion
        return playerMove[0]
    }

    private fun drawDots(moves: List<GameMove>) {
        if (srcX == null || srcY == null) {
            return
        }

        val toCoordinates = moves.filter { m -> m.displayFrom == getPieceCoordinateFromMousePosition(srcX!!, srcY!!) }
            .map { m -> m.displayTo }

        /* Draw toCoordinates dots for a selected piece. */
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = possibleMoveColour
        val position = squareWidth / 2
        for (c in toCoordinates) {
            shapeRenderer.circle(squareWidth * c.x + position, squareWidth * (c.y) + position, possibleMoveCircleRadius)
        }

        shapeRenderer.end()

    }

    private fun getPieceCoordinateFromMousePosition(srcX: Int, srcY: Int) =
        Coordinate(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())

    private fun drawPieces() {
        val batch = game.batch
        batch.begin()
        val pieces = board.getPieces()

        for ((p, c) in pieces) {
            val texture = textures.getTextureFromPiece(p, playerMapping!![p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2
            sprite.setPosition(squareWidth * c.x + posWithinSquare, squareWidth * c.y + posWithinSquare)

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
            sprite.draw(game.batch)
        }

        batch.end()
    }
}

package screens

import boards.ChessBoard
import boards.GUIBoard
import boards.XiangqiBoard
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.MyGdxGame
import com.mygdx.game.assets.Textures
import ktx.app.KtxScreen
import main.kotlin.Coordinate
import main.kotlin.Game
import main.kotlin.GameMove
import main.kotlin.gameTypes.xiangqi.Janggi
import main.kotlin.gameTypes.xiangqi.Xiangqi
import main.kotlin.players.HumanPlayer
import main.kotlin.players.Player

class GameScreen(val game: MyGdxGame, val gameEngine: Game) : KtxScreen {
    private val textures = Textures(game.assets)
    private val windowHeight: Int = 800
    private var windowWidth: Int = 800

    private val possibleMoveCircleRadius = 8f
    private val possibleMoveColour = Color.FOREST
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var moves: List<GameMove>

    private var panelWidth: Int = 300

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    val gameType = gameEngine.gameType
    var board = gameType.board
    val rows = board.n
    val columns = board.m

    lateinit var guiBoard: GUIBoard

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    var currPlayer: Player? = null

    // todo: hard coded, enum for colour ?
    var playerMapping: Map<Player, Color>? = null

    var isPromotionScreen = false
    lateinit var promotableMoves: List<GameMove>

    override fun show() {
        if (rows != columns) {
            windowWidth = (windowHeight * columns) / rows
        }
        Gdx.graphics.setWindowedMode(windowWidth + panelWidth, windowHeight)
        game.batch.projectionMatrix.setToOrtho2D(0f, 0f, windowWidth.toFloat() + panelWidth, windowHeight.toFloat())

//        shapeRenderer = game.shapeRenderer
        shapeRenderer = ShapeRenderer()

        currPlayer = gameType.getCurrentPlayer()
        playerMapping = mapOf(currPlayer!! to Color.WHITE, gameType.getNextPlayer() to Color.BLACK)
        Gdx.input.inputProcessor = Stage()
        gameEngine.start()
        moves = gameEngine.gameType.getValidMoves(currPlayer!!)

        guiBoard =  when (gameType) {
            is Xiangqi, is Janggi -> XiangqiBoard(shapeRenderer, board, game.batch, squareWidth, textures, playerMapping!!)
            else -> ChessBoard(shapeRenderer, board, game.batch, squareWidth, textures, playerMapping!!)
        }
    }

    private fun showPromotionScreen(moves: List<GameMove>) {
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        shapeRenderer.color = Color(1f, 1f, 1f, 0.5f)
        shapeRenderer.rect(0f, 0f, windowWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()

        Gdx.gl.glDisable(GL20.GL_BLEND)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        val batch = game.batch
        batch.begin()

        val coordinateMap = mutableMapOf<Int, GameMove>()

        val xCoordinate = (rows - moves.size) / 2
        val yCoordinate = (columns - 1) / 2

        val x = xCoordinate * squareWidth
        val y = yCoordinate * squareWidth

//        shapeRenderer.color = Color.z
//        for (i in moves.indices) {
//            shapeRenderer.rect( x + (i * squareWidth), y, squareWidth, squareWidth)
//        }
        shapeRenderer.end()

        for ((i, m) in moves.withIndex()) {
            val p = m.displayPiecePromotedTo

            val texture = textures.getTextureFromPiece(p!!, playerMapping!![p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2

//            shapeRenderer.rect( x + (i * squareWidth), y, squareWidth, squareWidth)

            sprite.setPosition(x + (i * squareWidth) + posWithinSquare, y + posWithinSquare)
            coordinateMap[i + xCoordinate] = m

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
        }
        batch.end()

        if (srcX != null && srcY != null) {
            val coordinate = getPieceCoordinateFromMousePosition(srcX!!, srcY!!)
            if (coordinate.y == yCoordinate && coordinateMap[coordinate.x] != null) {
                currPlayer?.playerMove = coordinateMap[coordinate.x]
                isPromotionScreen = false
            }
        }
    }

    fun switchToGameOverScreen(player: Player) {
        game.removeScreen<GameOverScreen>()

        // change this.
        val playerName = playerMapping?.get(player)!!.toString()
        if (playerName == "fffffff") {
            game.addScreen(GameOverScreen(game, gameEngine, "Black"))
        } else {
            game.addScreen(GameOverScreen(game, gameEngine, "White"))
        }
        game.setScreen<GameOverScreen>()
    }

    override fun render(delta: Float) {
        currPlayer = gameType.getCurrentPlayer()

        if (!gameEngine.turn()) {
            switchToGameOverScreen(currPlayer!!)
        }

        if (gameEngine.gameType.getCurrentPlayer() != currPlayer) {
            currPlayer = gameType.getCurrentPlayer()
            moves = gameEngine.gameType.getValidMoves(currPlayer!!)
            resetClicks()
        }
        val flip = playerMapping?.get(currPlayer!!) == Color.BLACK
        // print(playerMapping?.get(currPlayer!!) == Color.BLACK)
        // drawBoard(moves, flipped = flip)
        guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
        // drawPieces(flipped = flip)
        drawDots(moves, flip)
        controls(flip)
        drawPanel()

        if (isPromotionScreen) {
            showPromotionScreen(promotableMoves)
        }
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

    private fun controls(flipped: Boolean) {
        if (this.currPlayer !is HumanPlayer) {
            return
        }
        val input = Gdx.input
        val graphics = Gdx.graphics

        var x = input.x
        var y = graphics.height - input.y

        if (flipped) {
            x = ((columns * squareWidth) - x).toInt()
            y = graphics.height - y
        }

        if (input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (srcX == null || isPromotionScreen) {
                srcX = x
                srcY = y
            } else if (srcX != null && dstX == null &&
                moves.any { m -> m.displayFrom == getPieceCoordinateFromMousePosition(srcX!!, srcY!!)
                            && m.displayTo == getPieceCoordinateFromMousePosition(x, y) }
            ) {
                dstX = x
                dstY = y
                currPlayer?.playerMove = getMove(
                    getPieceCoordinateFromMousePosition(srcX!!, srcY!!),
                    getPieceCoordinateFromMousePosition(dstX!!, dstY!!),
                    moves
                )
            } else {
                srcX = x
                srcY = y
                dstX = null
                dstY = null
            }
        }

        if (input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            resetClicks()
        }

    }


    private fun getMove(from: Coordinate, to: Coordinate, moves: List<GameMove>): GameMove? {
        val playerMoves = moves.filter { m -> m.displayFrom == from && m.displayTo == to }
        if (playerMoves.isEmpty()) {
            return null
        }

        if (playerMoves.all { m -> m.displayPiecePromotedTo != null }) {
            isPromotionScreen = true
            promotableMoves = playerMoves
            resetClicks()
            return null
        }

        return playerMoves[0]
    }

    private fun resetClicks() {
        srcX = null
        srcY = null
        dstX = null
        dstY = null
    }

    private fun drawDots(moves: List<GameMove>, flipped: Boolean) {
        if (srcX == null || srcY == null || isPromotionScreen) {
            return
        }

        val toCoordinates = moves.filter { m -> m.displayFrom == getPieceCoordinateFromMousePosition(srcX!!, srcY!!) }
            .map { m -> m.displayTo }

//        if (toCoordinates.isEmpty()) {
//            resetClicks()
//            return
//        }

        /* Draw toCoordinates dots for a selected piece. */
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = possibleMoveColour
        val position = squareWidth / 2
        for (c in toCoordinates) {
            if (flipped) {
                shapeRenderer.circle(squareWidth * (columns - c.x - 1) + position, squareWidth * (rows - c.y - 1) + position, possibleMoveCircleRadius)
            } else {
                shapeRenderer.circle(squareWidth * c.x + position, squareWidth * (c.y) + position, possibleMoveCircleRadius)
            }
        }

        shapeRenderer.end()
    }

    private fun getPieceCoordinateFromMousePosition(srcX: Int, srcY: Int) =
        Coordinate(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())



    private fun drawPanel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.BLUE
        shapeRenderer.rect(windowWidth.toFloat(), 0f, panelWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
    }
}

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
import main.kotlin.GameMove
import main.kotlin.gameTypes.GameType
import main.kotlin.gameTypes.xiangqi.Janggi
import main.kotlin.gameTypes.xiangqi.Xiangqi
import main.kotlin.players.HumanPlayer
import main.kotlin.players.Player

class GameScreen(val game: MyGdxGame, val gameEngine: GameType) : KtxScreen {
    private val textures = Textures(game.assets)
    private val windowHeight: Int = 800
    private var windowWidth: Int = 800

    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var moves: List<GameMove>

    private var panelWidth: Int = 300

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

//    val gameType = gameEngine.gameType
    var board = gameEngine.board
    val rows = board.n
    val columns = board.m

    lateinit var guiBoard: GUIBoard

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    var currPlayer: Player? = null
    var playerMapping: Map<Player, Color>? = null

    var isPromotionScreen = false
    lateinit var promotableMoves: List<GameMove>

    fun startGame() {
        if (!gameEngine.checkValidGame()) {
            // TODO
        }

        gameEngine.initGame()
    }

    override fun show() {
        if (rows != columns) {
            windowWidth = (windowHeight * columns) / rows
        }
        Gdx.graphics.setWindowedMode(windowWidth + panelWidth, windowHeight)
        game.batch.projectionMatrix.setToOrtho2D(0f, 0f, windowWidth.toFloat() + panelWidth, windowHeight.toFloat())

        shapeRenderer = ShapeRenderer()

        currPlayer = gameEngine.getCurrentPlayer()
        playerMapping = mapOf(currPlayer!! to Color.WHITE, gameEngine.getNextPlayer() to Color.BLACK)
        Gdx.input.inputProcessor = Stage()
        startGame()
        moves = gameEngine.getValidMoves(currPlayer!!)

        guiBoard =  when (gameEngine) {
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

        shapeRenderer.end()

        for ((i, m) in moves.withIndex()) {
            val p = m.displayPiecePromotedTo

            val texture = textures.getTextureFromPiece(p!!, playerMapping!![p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2

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
        currPlayer = gameEngine.getCurrentPlayer()

        if (gameEngine.isOver()) {
            switchToGameOverScreen(currPlayer!!)
        }

        gameEngine.turn()

        if (gameEngine.getCurrentPlayer() != currPlayer) {
            currPlayer = gameEngine.getCurrentPlayer()
            moves = gameEngine.getValidMoves(currPlayer!!)
            resetClicks()
        }

        val flip = (playerMapping?.get(currPlayer!!) == Color.BLACK && currPlayer!! is HumanPlayer)
                || (playerMapping?.get(currPlayer!!) == Color.WHITE && currPlayer!! !is HumanPlayer && gameEngine.getNextPlayer() is HumanPlayer)

        guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
        controls(flip)
        drawPanel()

        if (isPromotionScreen) {
            showPromotionScreen(promotableMoves)
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

    private fun getPieceCoordinateFromMousePosition(srcX: Int, srcY: Int) =
        Coordinate(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())

    private fun drawPanel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.BLUE
        shapeRenderer.rect(windowWidth.toFloat(), 0f, panelWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
    }
}

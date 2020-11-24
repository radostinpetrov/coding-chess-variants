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
import Coordinate
import GameMove
import gameTypes.GameType
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import players.HumanPlayer
import players.Player
import players.SignalPlayer

class GameScreen(val game: MyGdxGame, val gameEngine: GameType, val clockList: List<Int>) : KtxScreen {
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

    var board = gameEngine.board
    val rows = board.n
    val columns = board.m

    lateinit var guiBoard: GUIBoard

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    // TODO maybe don't need this?
    var currPlayer: Player? = null

    // TODO can we put color in player?
    var playerMapping: Map<Player, Color>? = null
    // TODO and this?
    var playerMappingInitialClock: MutableMap<Player, Int>? = null
    var playerMappingEndClock: Map<Player, Int>? = null
    val initialTime = System.currentTimeMillis() / 1000L

    var isPromotionScreen = false
    lateinit var promotableMoves: List<GameMove>

    fun startGame() {
        if (!gameEngine.checkValidGame()) {
            TODO("INVALID GAME")
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

        playerMappingInitialClock = mutableMapOf(currPlayer!! to 0, gameEngine.getNextPlayer() to 0)
        playerMappingEndClock = mapOf(currPlayer!! to clockList[0], gameEngine.getNextPlayer() to clockList[1])

        Gdx.input.inputProcessor = Stage()
        startGame()
        moves = gameEngine.getValidMoves(currPlayer!!)

        // TODO remove this soon as possible
        (currPlayer!! as SignalPlayer).signalTurn()
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
                if (coordinateMap[coordinate.x] != null) {
                    (currPlayer!! as HumanPlayer).makeMove(coordinateMap[coordinate.x]!!)
                }
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

    // Function to be called when current player plays a turn
    fun processTurn(nextMove: GameMove) {
        gameEngine.playerMakeMove(nextMove)
        currPlayer = gameEngine.getCurrentPlayer()
        moves = gameEngine.getValidMoves(currPlayer!!)
        resetClicks()

        if (gameEngine.isOver()) {
            switchToGameOverScreen(currPlayer!!)
        }

        (currPlayer as SignalPlayer).signalTurn()
    }

    override fun render(delta: Float) {
        val flip = (playerMapping?.get(currPlayer!!) == Color.BLACK && currPlayer!! is HumanPlayer)
                || (playerMapping?.get(currPlayer!!) == Color.WHITE && currPlayer!! !is HumanPlayer && gameEngine.getNextPlayer() is HumanPlayer)

        guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
        controls(flip)
        // TODO why does draw panel come after controls?
        drawPanel()

        if (isPromotionScreen) {
            showPromotionScreen(promotableMoves)
        }
    }

    private fun controls(flipped: Boolean) {
        if (currPlayer!! !is HumanPlayer) {
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
                // TODO fix this -> either via changing fe player to be composition or otherwise
                val signalPlayer = currPlayer!!
                if (signalPlayer is HumanPlayer) {
                    val nextMove = getMove(
                        getPieceCoordinateFromMousePosition(srcX!!, srcY!!),
                        getPieceCoordinateFromMousePosition(dstX!!, dstY!!),
                        moves
                    )
                    if (nextMove != null) {
                        signalPlayer.makeMove(nextMove)
                    }
                }
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

    private fun resetClicks() {
        srcX = null
        srcY = null
        dstX = null
        dstY = null
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

    private fun getPieceCoordinateFromMousePosition(srcX: Int, srcY: Int) =
        Coordinate(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())

    private fun drawPanel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.BLUE
        shapeRenderer.rect(windowWidth.toFloat(), 0f, panelWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
    }

    private fun drawClocks(flipped: Boolean): Boolean {
        val currTime = System.currentTimeMillis()
        val otherPlayerTime = playerMappingInitialClock!![gameEngine.getNextPlayer()]!!

        val playerTime = currTime - initialTime - otherPlayerTime

        val displayTimeCurr = playerMappingEndClock!![currPlayer!!]!! - playerTime
        val displayTimeOther = playerMappingEndClock!![gameEngine.getNextPlayer()]!! - otherPlayerTime

        playerMappingInitialClock!![currPlayer!!] = playerTime.toInt()

        if (displayTimeCurr.toInt() <= 0) {
            return false
        }

        if (flipped) {
            // currtime bottom, other time top.
        } else {

        }


        return true
    }
}

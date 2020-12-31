package screens

import coordinates.Coordinate2D
import gameMoves.GameMove2D
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
import com.badlogic.gdx.utils.Align
import com.mygdx.game.MyGdxGame
import com.mygdx.game.assets.Textures
import gameTypes.GameType2D
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import ktx.app.KtxScreen
import players.*

class GameScreen(val game: MyGdxGame, val gameEngine: GameType2D, val clockList: List<Int>?) : KtxScreen {
    private lateinit var frontendPlayers: List<FrontendPlayer>
    private val textures = Textures(game.assets)
    private val windowHeight: Int = 800
    private var windowWidth: Int = 800

    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var moves: List<GameMove2D>

    private var panelWidth: Int = 300

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    var board = gameEngine.board
    val rows = board.rows
    val columns = board.cols

    lateinit var guiBoard: GUIBoard

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    // TODO maybe don't need this?
    var currPlayer: Player? = null

    // TODO put color in player?
    var playerColorMapping: Map<Player, Color>? = null

    lateinit var libToFrontendPlayer: Map<Player, FrontendPlayer>
    lateinit var humanPlayerSet: Set<Player>

    // TODO and this?
    var playerMappingInitialClock: MutableMap<Player, Int>? = null
    var playerMappingEndClock: Map<Player, Int>? = null
    val initialTime = System.currentTimeMillis() / 1000L

    var isPromotionScreen = false
    lateinit var promotableMoves: List<GameMove2D>

    fun initPlayers(inputFrontendPlayers: List<FrontendPlayer>) {
        val tempLibToFrontendPlayer: MutableMap<Player, FrontendPlayer> = mutableMapOf()
        val tempHumanPlayerSet: MutableSet<Player> = mutableSetOf()

        gameEngine.players.indices.forEach { i ->
            if (inputFrontendPlayers[i] is HumanPlayer) {
                tempHumanPlayerSet.add(gameEngine.players[i])
            }
            inputFrontendPlayers[i].libPlayer = gameEngine.players[i]
            tempLibToFrontendPlayer[gameEngine.players[i]] = inputFrontendPlayers[i]
        }

        frontendPlayers = inputFrontendPlayers.toList()
        libToFrontendPlayer = tempLibToFrontendPlayer.toMap()
        humanPlayerSet = tempHumanPlayerSet.toSet()
    }

    fun startGame() {
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
        playerColorMapping = mapOf(currPlayer!! to Color.WHITE, gameEngine.getNextPlayer() to Color.BLACK)

        if (clockList != null) {
            playerMappingInitialClock = mutableMapOf(currPlayer!! to 0, gameEngine.getNextPlayer() to 0)
            playerMappingEndClock = mapOf(currPlayer!! to clockList[0], gameEngine.getNextPlayer() to clockList[1])
        }

        Gdx.input.inputProcessor = Stage()
        startGame()
        moves = gameEngine.getValidMoves(currPlayer!!)

        guiBoard = when (gameEngine) {
            is Xiangqi, is Janggi -> XiangqiBoard(shapeRenderer, board, game.batch, squareWidth, textures, playerColorMapping!!)
            else -> ChessBoard(shapeRenderer, board, game.batch, squareWidth, textures, playerColorMapping!!)
        }

        libToFrontendPlayer[currPlayer!!]!!.signalTurn()
    }

    private fun showPromotionScreen(moves: List<GameMove2D>) {
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

        val coordinateMap = mutableMapOf<Int, GameMove2D>()

        val xCoordinate = (rows - moves.size) / 2
        val yCoordinate = (columns - 1) / 2

        val x = xCoordinate * squareWidth
        val y = yCoordinate * squareWidth

        shapeRenderer.end()

        for ((i, m) in moves.withIndex()) {
            val p = m.displayPiecePromotedTo

            val texture = textures.getTextureFromPiece(p!!, playerColorMapping!![p.player]!!)
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
                    (libToFrontendPlayer[currPlayer!!] as HumanPlayer).makeMove(coordinateMap[coordinate.x]!!)
                }
                isPromotionScreen = false
            }
        }
    }

    fun switchToGameOverScreen(player: Player) {
        game.removeScreen<GameOverScreen>()
        // change this.
        val playerName = playerColorMapping?.get(player)!!.toString()
        // White player reports result

        if (playerName == "fffffff") {
            if (frontendPlayers[0] is NetworkHumanPlayer) {
                (frontendPlayers[0] as NetworkHumanPlayer).websocketClientManager.sendResult(0f)
            }
            game.addScreen(GameOverScreen(game, gameEngine, "White"))
        } else {
            if (frontendPlayers[0] is NetworkHumanPlayer) {
                (frontendPlayers[0] as NetworkHumanPlayer).websocketClientManager.sendResult(1f)
            }
            game.addScreen(GameOverScreen(game, gameEngine, "Black"))
        }

        game.setScreen<GameOverScreen>()
    }

    // Function to be called when current player plays a turn
    fun processTurn(nextMove: GameMove2D) {
        synchronized(this) {
            gameEngine.playerMakeMove(nextMove)
            currPlayer = gameEngine.getCurrentPlayer()
            moves = gameEngine.getValidMoves(currPlayer!!)
            resetClicks()
            if (gameEngine.isOver()) {
                Gdx.app.postRunnable {
                    switchToGameOverScreen(currPlayer!!)
                }
            }

            libToFrontendPlayer[currPlayer]!!.signalTurn()
        }
    }

    override fun render(delta: Float) {
        val flip = (playerColorMapping?.get(currPlayer!!) == Color.BLACK && humanPlayerSet.contains(currPlayer!!) && !humanPlayerSet.contains(gameEngine.getNextPlayer())) ||
            (playerColorMapping?.get(currPlayer!!) == Color.WHITE && !humanPlayerSet.contains(currPlayer!!) && humanPlayerSet.contains(gameEngine.getNextPlayer()))

        synchronized(this) {
            guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
            controls(flip)
        }

        // TODO why does draw panel come after controls?
        drawPanel()
        drawHistoryBox()

        if (clockList != null && !drawClocks(flip)) {
            switchToGameOverScreen(gameEngine.getNextPlayer())
        }

        if (isPromotionScreen) {
            showPromotionScreen(promotableMoves)
        }
    }

    private fun controls(flipped: Boolean) {
        if (!humanPlayerSet.contains(currPlayer!!)) {
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
                moves.any { m ->
                    m.displayFrom == getPieceCoordinateFromMousePosition(srcX!!, srcY!!) &&
                        m.displayTo == getPieceCoordinateFromMousePosition(x, y)
                }
            ) {
                dstX = x
                dstY = y
                // TODO fix this -> either via changing fe player to be composition or otherwise
                val signalPlayer = libToFrontendPlayer[currPlayer!!]
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

    private fun getMove(from: Coordinate2D, to: Coordinate2D, moves: List<GameMove2D>): GameMove2D? {
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
        Coordinate2D(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())

    private fun drawPanel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.LIGHT_GRAY
        shapeRenderer.rect(windowWidth.toFloat(), 0f, panelWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
    }

    private fun drawClocks(flipped: Boolean): Boolean {
        val currTime = System.currentTimeMillis() / 1000L
        val otherPlayerTime = playerMappingInitialClock!![gameEngine.getNextPlayer()]!!

        val playerTime = (currTime - initialTime - otherPlayerTime).toInt()

        val displayTimeCurr = playerMappingEndClock!![currPlayer!!]!! - playerTime
        val displayTimeOther = playerMappingEndClock!![gameEngine.getNextPlayer()]!! - otherPlayerTime

        playerMappingInitialClock!![currPlayer!!] = playerTime

        if (displayTimeCurr <= 0) {
            return false
        }

        val currStr = "${displayTimeCurr / 60}:${"%02d".format(displayTimeCurr % 60)}"
        val otherStr = "${displayTimeOther / 60}:${"%02d".format(displayTimeOther % 60)}"

        val str1: String
        val str2: String

        if (flipped.xor(playerColorMapping!![currPlayer!!] == Color.WHITE)) {
            str1 = currStr
            str2 = otherStr
        } else {
            str1 = otherStr
            str2 = currStr
        }

        val batch = game.batch
        val font = game.font

        batch.begin()

        font.color = Color.BLACK
        font.data.setScale(2.0f)
        font.draw(batch, str2, windowWidth.toFloat(), windowHeight.toFloat() * 15 / 16, panelWidth.toFloat(), Align.center, false)
        font.draw(batch, str1, windowWidth.toFloat(), windowHeight.toFloat() * 1 / 16, panelWidth.toFloat(), Align.center, false)
        font.data.setScale(1.0f)
        batch.end()

        return true
    }

    private fun drawHistoryBox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.WHITE
        shapeRenderer.rect(windowWidth.toFloat() + panelWidth.toFloat() * 1 / 12, 0f + windowHeight.toFloat() * 1 / 8, panelWidth.toFloat() * 10 / 12, windowHeight.toFloat() * 6 / 8)
        shapeRenderer.end()

        val batch = game.batch
        val font = game.font

        var i = 0
        var history: List<GameMove2D> = gameEngine.moveLog.toList()
        val len = gameEngine.moveLog.size

        var offset = 0
        batch.begin()
        if (len >= 40) {
            if (len % 2 == 0) {
                history = history.subList(len - 40, len)
                offset += (len - 40) / 2
            } else {
                history = history.subList(len - 40 + 1, len)
                offset += (len - 40 + 1) / 2
            }
        }

        for (move in history) {
            val coor = move.displayTo
            if (i % 2 == 0) {
                font.setColor(Color.GRAY)
                val str = "TURN ${offset + i / 2 + 1} : (${(coor.x + 65).toChar()},${coor.y + 1})"
                font.draw(batch, str, windowWidth.toFloat() + panelWidth.toFloat() * 2 / 12, windowHeight.toFloat() * 7 / 8 - 10 - (15 * i))
            } else {
                font.setColor(Color.BLACK)
                val str = "(${(coor.x + 65).toChar()},${coor.y + 1})"
                font.draw(batch, str, windowWidth.toFloat() + panelWidth.toFloat() * 7 / 12, windowHeight.toFloat() * 7 / 8 - 10 - (15 * (i - 1)))
            }
            i++
        }
        batch.end()
    }
}

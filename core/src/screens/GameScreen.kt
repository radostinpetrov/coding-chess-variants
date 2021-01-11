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
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import com.mygdx.game.MyGdxGame
import com.mygdx.game.assets.Textures
import coordinates.Coordinate2D
import endconditions.Outcome
import gameTypes.GameType2D
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import ktx.app.KtxScreen
import moves.Move2D
import players.*

/**
 * Displays the game screen during play. ie. the board pieces, clock, history and takes user input.
 */
class GameScreen(val game: MyGdxGame, val gameEngine: GameType2D, val clockFlag: Boolean, val isOnline: Boolean) : KtxScreen {
    private lateinit var frontendPlayers: List<FrontendPlayer>
    private val textures = Textures(game.assets)
    private val windowHeight: Int = 800
    private var windowWidth: Int = 800

    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var moves: List<Move2D>

    private var panelWidth: Int = 300

    private val font = game.font
    private val batch = game.batch

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    var board = gameEngine.board
    val rows = board.rows
    val columns = board.cols

    private lateinit var stage: Stage

    lateinit var guiBoard: GUIBoard

    private var squareWidth: Float = (windowHeight / rows).toFloat()
    private val pieceWidth: Float = squareWidth * 0.85f

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
    private val forfeitButton = TextButton("Forfeit", skin)
    private val forfeitButtonPosY = 23f

    // TODO maybe don't need this?
    var currPlayer: Player? = null

    lateinit var libToFrontendPlayer: Map<Player, FrontendPlayer>
    lateinit var humanPlayerSet: Set<Player>

    var networkHumanPlayer: NetworkHumanPlayer? = null

    // TODO and this?
    val initialTime = System.currentTimeMillis() / 1000L

    var isPromotionScreen = false
    lateinit var promotableMoves: List<Move2D>

    var gameOverPopUp: GameOverPopUp? = null

    /**
     * Maps the front end players to the players on the game engine
     * @param inputFrontendPlayers a list of front end players created on the previous screen
     */
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

    /**
     * Calls the chess engine to start the game
     */
    fun startGame() {
        gameEngine.initGame()
    }

    /**
     * This is called when the class is created before render.
     * Initialises the outcome message and playAgainButton.
     */
    override fun show() {
        /* Initialise the display size. */
        if (rows != columns) {
            windowWidth = (windowHeight * columns) / rows
        }
        Gdx.graphics.setWindowedMode(windowWidth + panelWidth, windowHeight)
        game.batch.projectionMatrix.setToOrtho2D(0f, 0f, windowWidth.toFloat() + panelWidth, windowHeight.toFloat())
        shapeRenderer = ShapeRenderer()

        /* Initialise the starting game logic. */
        currPlayer = gameEngine.getCurrentPlayer()
        startGame()
        moves = gameEngine.getValidMoves(currPlayer!!)
        libToFrontendPlayer[currPlayer!!]!!.signalTurn()

        /* Initialize the clocks. */
        if (clockFlag) {
            libToFrontendPlayer[currPlayer]!!.stopwatch.start()
        }

        /* Initialise the GUIBoard. */
        guiBoard = when (gameEngine) {
            is Xiangqi, is Janggi -> XiangqiBoard(shapeRenderer, board, game.batch, squareWidth, textures, libToFrontendPlayer)
            else -> ChessBoard(shapeRenderer, board, game.batch, squareWidth, textures, libToFrontendPlayer, game.font)
        }

        /* Initialise the forfeitButton. */
        stage = Stage()
        forfeitButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (!isOnline) {
                    processConcede(gameEngine.getCurrentPlayer())
                } else {
                    networkHumanPlayer!!.websocketClientManager.sendConcede()
                    processConcede(networkHumanPlayer!!.libPlayer)
                }
            }
        })

        forfeitButton.setPosition(windowWidth.toFloat() + panelWidth.toFloat() - 90f, forfeitButtonPosY)
        stage.addActor(forfeitButton)
        Gdx.input.inputProcessor = stage
    }

    /**
     * Displays a transparent promotion screen when a piece has the option to promote.
     */
    private fun showPromotionScreen(moves: List<Move2D>) {

        /* Set the background to transparent. */
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(1f, 1f, 1f, 0.5f)
        shapeRenderer.rect(0f, 0f, windowWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)

        /* Finds the position to display the promotion pieces. */
        val batch = game.batch
        batch.begin()
        val coordinateMap = mutableMapOf<Int, Move2D>()
        val xCoordinate = (columns - moves.size) / 2
        val yCoordinate = (rows - 1) / 2
        val x = xCoordinate * squareWidth
        val y = yCoordinate * squareWidth

        /* Iterate over the possible promotion pieces and displays them. */
        moves.forEach {
            if (it.displayPiecePromotedTo == null) {
                it.displayPiecePromotedTo = it.displayPieceMoved
            }
        }
        for ((i, m) in moves.withIndex()) {
            val p = m.displayPiecePromotedTo

            val texture = textures.getTextureFromPiece(p!!, libToFrontendPlayer[p.player]!!.colour)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2

            sprite.setPosition(x + (i * squareWidth) + posWithinSquare, y + posWithinSquare)
            coordinateMap[i + xCoordinate] = m

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
        }
        batch.end()

        /* Monitors users mouse input to select the promotion piece. */
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

    /**
     * This is called when the current player plays a turn.
     * @param nextMove the move the current player will make on the game engine
     */
    fun processTurn(nextMove: Move2D) {
        switchClocks()
        synchronized(this) {
            gameEngine.playerMakeMove(nextMove)
            currPlayer = gameEngine.getCurrentPlayer()
            moves = gameEngine.getValidMoves(currPlayer!!)
            resetClicks()
            if (gameEngine.isOver()) {
                val outcome = gameEngine.getOutcome()!!
                val winnerName = if (outcome is Outcome.Win) {
                    libToFrontendPlayer[outcome.winner]!!.name
                } else {
                    null
                }
                Gdx.app.postRunnable {
//                    switchToGameOverScreen(gameEngine.getOutcome()!!)
                    gameOverPopUp = GameOverPopUp(
                        game,
                        stage,
                        this,
                        outcome,
                        shapeRenderer,
                        windowWidth,
                        windowHeight,
                        winnerName
                    )
                    forfeitButton.remove()
                }
            }

            libToFrontendPlayer[currPlayer]!!.signalTurn()
        }
    }

    /**
     * Swap running clock.
     */
    private fun switchClocks() {
        frontendPlayers.forEach {
            it.flipClock()
        }
    }

    /**
     * This is called when a player concedes.
     * @param player the player who conceded
     */
    fun processConcede(player: Player) {
        gameEngine.concede(player)
        Gdx.app.postRunnable {
//            switchToGameOverScreen(gameEngine.getOutcome()!!)
            val outcome = gameEngine.getOutcome()!!
            val winnerName = if (outcome is Outcome.Win) {
                libToFrontendPlayer[outcome.winner]!!.name
            } else {
                null
            }
            gameOverPopUp = GameOverPopUp(game, stage, this, gameEngine.getOutcome()!!, shapeRenderer, windowWidth, windowHeight, winnerName)
            forfeitButton.remove()
        }
    }

    /**
     * This is called manually when a player runs out of time.
     * @param player the player who won
     */
    fun processTimeoutWin(player: Player) {
        val outcome = Outcome.Win(player, "by time")
        Gdx.app.postRunnable {
//            switchToGameOverScreen(outcome)

            gameOverPopUp = GameOverPopUp(game, stage, this, outcome, shapeRenderer, windowWidth, windowHeight, libToFrontendPlayer[outcome.winner]!!.name)
            forfeitButton.remove()
        }
    }

    /**
     * Draws the display and detects user input.
     */
    override fun render(delta: Float) {
        libToFrontendPlayer[currPlayer!!]!!.colour
        val nextPlayer = gameEngine.getNextPlayer()

        /* Determines whether or not to flip the board orientation. */
        val flip = (
            libToFrontendPlayer[currPlayer!!]!!.colour == Color.BLACK && humanPlayerSet.contains(currPlayer!!) && !humanPlayerSet.contains(
                nextPlayer
            )
            ) ||
            (
                libToFrontendPlayer[currPlayer]!!.colour == Color.WHITE && !humanPlayerSet.contains(currPlayer!!) && humanPlayerSet.contains(
                    nextPlayer
                )
                )

        if (gameOverPopUp != null) {
            guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
            drawPanel()
            drawHistoryBox()
            drawUsers(flip)
            gameOverPopUp!!.show()
        } else {
            /* Draws the board and detects user input. */
            synchronized(this) {
                guiBoard.draw(srcX, srcY, moves, flip, isPromotionScreen)
                controls(!isPromotionScreen && flip)
            }

            /* Draws the side bar. */
            drawPanel()
            drawHistoryBox()
            drawUsers(flip)
            if (clockFlag && !drawClocks(flip)) {
                if (!isOnline) {
                    processTimeoutWin(nextPlayer)
                }
                // Wait for network message to process timeout win if online mode
            }

            /* Show the promotion screen */
            if (isPromotionScreen) {
                showPromotionScreen(promotableMoves)
            }
        }

        stage.draw()
        stage.act()
    }

    /**
     * Detects the users mouse input.
     */
    private fun controls(flipped: Boolean) {
        if (!humanPlayerSet.contains(currPlayer!!)) {
            return
        }

        /* Workout the mouse coordinates. */
        val input = Gdx.input
        val graphics = Gdx.graphics
        var x = input.x
        var y = graphics.height - input.y
        if (flipped) {
            x = ((columns * squareWidth) - x).toInt()
            y = graphics.height - y
        }

        /* Processes the user's clicks. Gets the piece at the coordinate. */
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

    /**
     * Finds the move in the valid move list corresponding to the users input.
     * @param from the coordinate of the selected piece
     * @param to the coordinate of the selected destination
     * @param moves list of valid moves for the selected piece
     */
    private fun getMove(from: Coordinate2D, to: Coordinate2D, moves: List<Move2D>): Move2D? {
        val playerMoves = moves.filter { m -> m.displayFrom == from && m.displayTo == to }
        if (playerMoves.isEmpty()) {
            return null
        }

        if (playerMoves.any { m -> m.displayPiecePromotedTo != null }) {
            isPromotionScreen = true
            promotableMoves = playerMoves
            resetClicks()
            return null
        }

        return playerMoves[0]
    }

    private fun getPieceCoordinateFromMousePosition(srcX: Int, srcY: Int) =
        Coordinate2D(srcX / squareWidth.toInt(), srcY / squareWidth.toInt())

    /**
     * Draws the side bar.
     */
    private fun drawPanel() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.LIGHT_GRAY
        shapeRenderer.rect(windowWidth.toFloat(), 0f, panelWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
    }

    /**
     * Draws the clocks on the side bar.
     * @param flipped determines which side the clocks are on. Different for white and black
     */
    private fun drawClocks(flipped: Boolean): Boolean {
        val nextPlayer = gameEngine.getNextPlayer()

        val displayTimeCurr = libToFrontendPlayer[currPlayer!!]!!.getRemainingTime()
        val displayTimeOther = libToFrontendPlayer[nextPlayer]!!.getRemainingTime()

        if (displayTimeCurr <= 0 && !isOnline) {
            return false
        }

        /* Initialise the clock time text. */
        val currStr = "${displayTimeCurr / 60}:${"%02d".format(displayTimeCurr % 60)}"
        val otherStr = "${displayTimeOther / 60}:${"%02d".format(displayTimeOther % 60)}"

        val str1: String
        val str2: String

        if (flipped.xor(libToFrontendPlayer[currPlayer!!]!!.colour == Color.WHITE)) {
            str1 = currStr
            str2 = otherStr
        } else {
            str1 = otherStr
            str2 = currStr
        }

        /* Draw the clocks. */
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

    /**
     * Draws the history of moves on the side bar.
     */
    private fun drawHistoryBox() {

        /* Draw the history box. */
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.WHITE
        shapeRenderer.rect(windowWidth.toFloat() + panelWidth.toFloat() * 1 / 12, 0f + windowHeight.toFloat() * 1 / 8, panelWidth.toFloat() * 10 / 12, windowHeight.toFloat() * 6 / 8)
        shapeRenderer.end()

        var history: List<Move2D> = gameEngine.moveLog.toList()

        /* Get the last 40 moves from the history. */
        val len = gameEngine.moveLog.size
        var offset = 0
        if (len >= 40) {
            if (len % 2 == 0) {
                history = history.subList(len - 40, len)
                offset += (len - 40) / 2
            } else {
                history = history.subList(len - 40 + 1, len)
                offset += (len - 40 + 1) / 2
            }
        }

        /* Iterate over the moves in the history and display them. */
        batch.begin()
        for ((i, move) in history.withIndex()) {
            val coor = move.displayTo
            if (i % 2 == 0) {
                font.setColor(Color.GRAY)
                val str = "TURN ${offset + i / 2 + 1} :  ${move.displayPieceMoved.getSymbol() + "-" + (coor!!.x + 97).toChar().toUpperCase() + (coor.y + 1)}"
                font.draw(batch, str, windowWidth.toFloat() + panelWidth.toFloat() * 2 / 12, windowHeight.toFloat() * 7 / 8 - 10 - (15 * i))
            } else {
                font.setColor(Color.BLACK)
                val str = " ${move.displayPieceMoved.getSymbol() + "-" + (coor!!.x + 97).toChar().toUpperCase() + (coor.y + 1)}"
                font.draw(batch, str, windowWidth.toFloat() + panelWidth.toFloat() * 7 / 12, windowHeight.toFloat() * 7 / 8 - 10 - (15 * (i - 1)))
            }
        }
        batch.end()
    }

    /**
     * Draws the user names and elo of players.
     */
    private fun drawUsers(flipped: Boolean) {
        batch.begin()
        // TODO add user1 user2
        val user1: String
        val user2: String

        if (isOnline) {
            user1 = "Username: ${frontendPlayers[0].username}, Elo: ${frontendPlayers[0].elo}"
            user2 = "Username: ${frontendPlayers[1].username}, Elo: ${frontendPlayers[1].elo}"
        } else {
            user1 = "Name: ${frontendPlayers[0].username}"
            user2 = "Name: ${frontendPlayers[1].username}"
        }
        font.setColor(Color.BLACK)
        if (flipped) {
            font.draw(batch, user1, windowWidth + 10f, windowHeight.toFloat() * 15 / 16 + 20f)
            font.draw(batch, user2, windowWidth + 10f, windowHeight.toFloat() * 1 / 16 + 20f)
        } else {
            font.draw(batch, user2, windowWidth + 10f, windowHeight.toFloat() * 15 / 16 + 20f)
            font.draw(batch, user1, windowWidth + 10f, windowHeight.toFloat() * 1 / 16 + 20f)
        }
        batch.end()
    }
}

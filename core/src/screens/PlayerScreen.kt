package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.MyGdxGame
import com.mygdx.game.PlayerType
import gameTypes.GameType
import gameTypes.GameType2D
import gameTypes.chess.AbstractChess2D
import gameTypes.hex.HexagonalChess
import ktx.app.KtxScreen
import players.ComputerPlayer
import players.ComputerPlayerHex
import players.FrontendPlayer
import players.FrontendPlayerHex
import players.HumanPlayer
import players.HumanPlayerHex

/**
 * Displays the Player select screen, where the human and computer users are set for local play.
 */
class PlayerScreen(val game: MyGdxGame, val gameType: GameType<*, *, *, *>, val clockList: List<Long>?) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

    val title = Label("Select Players", skin)

    val humanPlayer1Button = TextButton("Human Player", skin)
    val computerPlayer1Button = TextButton("Computer Player", skin)

    val humanPlayer2Button = TextButton("Human Player", skin)
    val computerPlayer2Button = TextButton("Computer Player", skin)

    val startButton = TextButton("Start", skin)
    val backButton = TextButton("Go Back", skin)

    val titlePlayer1 = Label("Add player1", skin)
    val titlePlayer2 = Label("Add player2", skin)

    val playerTypes = mutableListOf(PlayerType.HUMAN, PlayerType.HUMAN)

    /**
     * This is called when the class is created, before render.
     * Initialises the buttons on the menu.
     */
    override fun show() {
        humanPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                playerTypes[0] = PlayerType.HUMAN
                humanPlayer1Button.setColor(200f, 0f, 0f, 100f)
                computerPlayer1Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        humanPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                playerTypes[1] = PlayerType.HUMAN
                humanPlayer2Button.setColor(200f, 0f, 0f, 100f)
                computerPlayer2Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        computerPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                playerTypes[0] = PlayerType.COMPUTER
                computerPlayer1Button.setColor(200f, 0f, 0f, 100f)
                humanPlayer1Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        computerPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                playerTypes[1] = PlayerType.COMPUTER
                computerPlayer2Button.setColor(200f, 0f, 0f, 100f)
                humanPlayer2Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        startButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToGameScreen(gameType)
            }
        })

        backButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToMenuScreen()
            }
        })

        humanPlayer1Button.setColor(200f, 0f, 0f, 100f)
        humanPlayer2Button.setColor(200f, 0f, 0f, 100f)
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).top()
        table.row()
        table.add(titlePlayer1).colspan(6).padBottom(20f)
        table.row()
        table.add(humanPlayer1Button).colspan(3).padBottom(50f)
        table.add(computerPlayer1Button).colspan(3).padBottom(50f)
        table.row()
        table.add(titlePlayer2).colspan(6).padBottom(20f)
        table.row()
        table.add(humanPlayer2Button).colspan(3).padBottom(50f)
        table.add(computerPlayer2Button).colspan(3).padBottom(50f)
        table.row()
        table.add(startButton).colspan(6).padBottom(20f).center()
        table.row()
        table.add(backButton).colspan(6).padBottom(20f).center()
        stage.addActor(table)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    /**
     * Initialises the players selected and switches to the gameScreen.
     */
    private fun switchToGameScreen(gameType: GameType<*,*,*,*>) {
        val clockFlag = clockList != null


        if (gameType is HexagonalChess) {
            val gameScreen = GameScreenHexagonal(game, gameType, clockFlag, false)
            game.removeScreen<GameScreenHexagonal>()
            val players = mutableListOf<FrontendPlayerHex>()
            players.add(createPlayerHex(playerTypes[0], gameScreen, Color.WHITE, "White"))
            players.add(createPlayerHex(playerTypes[1], gameScreen, Color.BLACK, "Black"))

            if (clockFlag) {
                players[0].endClock = clockList!![0]
                players[1].endClock = clockList[1]
            }

            gameScreen.initPlayers(players)

            game.addScreen(gameScreen)
            dispose()
            game.setScreen<GameScreenHexagonal>()
        } else if (gameType is AbstractChess2D) {
            val gameScreen = GameScreen(game, gameType, clockFlag, false)
            game.removeScreen<GameScreen>()
            val players = mutableListOf<FrontendPlayer>()
            players.add(createPlayer(playerTypes[0], gameScreen, Color.WHITE, "White"))
            players.add(createPlayer(playerTypes[1], gameScreen, Color.BLACK, "Black"))

            if (clockFlag) {
                players[0].endClock = clockList!![0]
                players[1].endClock = clockList[1]
            }

            gameScreen.initPlayers(players)

            game.addScreen(gameScreen)
            dispose()
            game.setScreen<GameScreen>()
        }

    }

    /**
     * Creates a Player instance given the PlayerType Enum.
     * @param player enum representing human or computer
     * @param gameScreen gives the gameScreen to the player instance
     * @param colour the colour of the created player
     */
    private fun createPlayer(player: PlayerType, gameScreen: GameScreen, colour: Color, colourName: String): FrontendPlayer {
        return when (player) {
            PlayerType.HUMAN -> HumanPlayer(gameScreen, colour, colourName)
            PlayerType.COMPUTER -> ComputerPlayer(gameScreen, 200, colour, colourName)
        }
    }

    private fun createPlayerHex(player: PlayerType, gameScreen: GameScreenHexagonal, colour: Color, colourName: String): FrontendPlayerHex {
        return when (player) {
            PlayerType.HUMAN -> HumanPlayerHex(gameScreen, colour, colourName)
            PlayerType.COMPUTER -> ComputerPlayerHex(gameScreen, 200, colour, colourName)
        }
    }

    private fun switchToMenuScreen() {
        game.removeScreen<MenuScreen>()
        game.addScreen(MenuScreen(game))
        dispose()
        game.setScreen<MenuScreen>()
    }
}

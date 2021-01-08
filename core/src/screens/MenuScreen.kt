package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.MyGdxGame
import gameTypes.GameType2D
import gameTypes.checkers.Checkers
import gameTypes.chess.*
import gameTypes.xiangqi.Janggi
import gameTypes.xiangqi.Xiangqi
import ktx.app.KtxScreen
import screens.leaderboard.LeaderboardScreen

/**
 * Displays the menu screen, where the game config is set by the user.
 */
class MenuScreen(val game: MyGdxGame) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
    val standardChessButton = TextButton("Standard Chess", skin)
    val grandChessButton = TextButton("Grand Chess", skin)
    val capablancaChessButton = TextButton("Capablanca Chess", skin)
    val chess960Button = TextButton("Chess960", skin)
    val janggiButton = TextButton("Janggi", skin)
    val xiangqiButton = TextButton("Xiangqi", skin)
    val antiChessButton = TextButton("AntiChess", skin)
    val miniChessButton = TextButton("MiniChess", skin)
    val balbosGameButton = TextButton("Balbo's Game", skin)
    val checkersGameButton = TextButton("Checkers", skin)
    val playgroundButton = TextButton("Playground", skin)
    val title = Label("Welcome to Chess", skin)

    val gameModeTitle = Label("Select Game Mode", skin)
    val onlineModeButton = TextButton("Play Online", skin)
    val localModeButton = TextButton("Play Local", skin)
    var isOnline = false

    val clockTitle = Label("Select Clock Options", skin)
    val noClockButton = TextButton("None", skin)
    val fiveClockButton = TextButton("5 + 0", skin)
    val tenClockButton = TextButton("10 + 0", skin)
    val fifteenClockButton = TextButton("15 + 0", skin)
    var clockList: List<Int>? = null

    val leaderboardButton = TextButton("Leaderboard", skin)

    val usernameLabel = Label("Online Username: ", skin)
    val usernameTextField = TextField("Guest", skin)

    val startButton = TextButton("Start", skin)

    var chessType: GameType2D = StandardChess()
    val chessTypes = mapOf(
        standardChessButton to StandardChess(),
        grandChessButton to GrandChess(),
        capablancaChessButton to CapablancaChess(),
        chess960Button to Chess960(),
        janggiButton to Janggi(),
        xiangqiButton to Xiangqi(),
        antiChessButton to AntiChess(),
        miniChessButton to MiniChess(),
        balbosGameButton to BalbosGame(),
        checkersGameButton to Checkers(),
        playgroundButton to ChessPlayground()
    )

    /**
     * This is called when the class is created, before render.
     * Initialises the buttons on the menu. Add button here for new variant.
     */
    override fun show() {

        /* Local/Online play buttons. */
        localModeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                isOnline = false
                localModeButton.setColor(200f, 0f, 0f, 100f)
                onlineModeButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        onlineModeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                isOnline = true
                onlineModeButton.setColor(200f, 0f, 0f, 100f)
                localModeButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        /* Clock buttons. */
        noClockButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                clockList = null
                noClockButton.setColor(200f, 0f, 0f, 100f)
                fiveClockButton.setColor(255f, 255f, 255f, 100f)
                tenClockButton.setColor(255f, 255f, 255f, 100f)
                fifteenClockButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        fiveClockButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                clockList = mutableListOf(300, 300)
                noClockButton.setColor(255f, 255f, 255f, 100f)
                fiveClockButton.setColor(200f, 0f, 0f, 100f)
                tenClockButton.setColor(255f, 255f, 255f, 100f)
                fifteenClockButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        tenClockButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                clockList = mutableListOf(600, 600)
                noClockButton.setColor(255f, 255f, 255f, 100f)
                fiveClockButton.setColor(255f, 255f, 255f, 100f)
                tenClockButton.setColor(200f, 0f, 0f, 100f)
                fifteenClockButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        fifteenClockButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                clockList = mutableListOf(900, 900)
                noClockButton.setColor(255f, 255f, 255f, 100f)
                fiveClockButton.setColor(255f, 255f, 255f, 100f)
                tenClockButton.setColor(255f, 255f, 255f, 100f)
                fifteenClockButton.setColor(200f, 0f, 0f, 100f)
            }
        })

        /* Chess variant buttons. */
        standardChessButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                selectChessType(standardChessButton)
            }
        })

        grandChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(grandChessButton)
            }
        })

        capablancaChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(capablancaChessButton)
            }
        })

        chess960Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(chess960Button)
            }
        })

        janggiButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(janggiButton)
            }
        })

        xiangqiButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(xiangqiButton)
            }
        })

        antiChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(antiChessButton)
            }
        })

        miniChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(miniChessButton)
            }
        })

        balbosGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(balbosGameButton)
            }
        })

        checkersGameButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(checkersGameButton)
            }
        })

        playgroundButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                selectChessType(playgroundButton)
            }
        })

        /* Start and leaderboard buttons. */
        startButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(chessType, isOnline)
            }
        })

        leaderboardButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToLeaderboardScreen()
            }
        })

        noClockButton.setColor(200f, 0f, 0f, 100f)
        localModeButton.setColor(200f, 0f, 0f, 100f)
        standardChessButton.setColor(200f, 0f, 0f, 100f)

        /* Put the buttons in a table to display. */
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 0f)
        table.add(title).colspan(12).padBottom(20f).top()
        table.row()
        table.add(localModeButton).colspan(6).padBottom(50f)
        table.add(onlineModeButton).colspan(6).padBottom(50f)
        table.row()
        table.add(usernameLabel).colspan(6).padBottom(50f)
        table.add(usernameTextField).colspan(6).padBottom(50f)
        table.row()
        table.add(clockTitle).colspan(12).padBottom(20f)
        table.row()
        table.add(noClockButton).colspan(3).padBottom(20f)
        table.add(fiveClockButton).colspan(3).padBottom(20f)
        table.add(tenClockButton).colspan(3).padBottom(20f)
        table.add(fifteenClockButton).colspan(3).padBottom(20f)
        table.row()
        table.add(gameModeTitle).colspan(12).padBottom(20f)
        table.row()
        table.add(standardChessButton).colspan(4).padBottom(20f)
        table.add(grandChessButton).colspan(4).padBottom(20f)
        table.add(capablancaChessButton).colspan(4).padBottom(20f)
        table.row()
        table.add(chess960Button).colspan(4).padBottom(20f)
        table.add(janggiButton).colspan(4).padBottom(20f)
        table.add(xiangqiButton).colspan(4).padBottom(20f)
        table.row()
        table.add(antiChessButton).colspan(4).padBottom(20f)
        table.add(miniChessButton).colspan(4).padBottom(20f)
        table.add(balbosGameButton).colspan(4).padBottom(20f)
        table.row()
        table.add(checkersGameButton).colspan(6).padBottom(40f)
        table.add(playgroundButton).colspan(6).padBottom(40f)
        table.row()
        table.add(startButton).colspan(12).padBottom(30f).center()
        table.row()
        table.add(leaderboardButton).colspan(12).padTop(50f).padBottom(20f).center()
        table.row()
        stage.addActor(table)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    /**
     * Switches to pregame screen. Different for online and local play.
     * Online goes to OnlineScreen.
     * Local goes to PlayerScreen
     */
    private fun switchToPreGameScreen(gameType: GameType2D, isOnline: Boolean) {
        if (!isOnline) {
            game.removeScreen<PlayerScreen>()
            game.addScreen(PlayerScreen(game, gameType, clockList))
            game.setScreen<PlayerScreen>()
            dispose()
        } else {
            game.removeScreen<OnlineScreen>()
            game.addScreen(OnlineScreen(game, usernameTextField.text, gameType, clockList))
            game.setScreen<OnlineScreen>()
            dispose()
        }
    }

    private fun switchToLeaderboardScreen() {
        game.removeScreen<LeaderboardScreen>()
        game.addScreen(LeaderboardScreen(game))
        game.setScreen<LeaderboardScreen>()
        dispose()
    }

    /**
     * Displays the selected chess variant button is selected.
     * @param button the selected button
     */
    private fun selectChessType(button: TextButton) {
        chessType = chessTypes[button]!!
        for ((b, t) in chessTypes) {
            if (t == chessType) {
                b.setColor(200f, 0f, 0f, 100f)
            } else {
                b.setColor(255f, 255f, 255f, 100f)
            }
        }
    }
}

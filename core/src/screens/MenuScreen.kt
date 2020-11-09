package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.MyGdxGame
import com.mygdx.game.PlayerType
import ktx.app.KtxScreen
import main.kotlin.Game
import main.kotlin.gameTypes.GameType
import main.kotlin.gameTypes.chess.CapablancaChess
import main.kotlin.gameTypes.chess.Chess960
import main.kotlin.gameTypes.chess.GrandChess
import main.kotlin.gameTypes.chess.StandardChess

class MenuScreen(val game: MyGdxGame) : KtxScreen {
//    class CustomButton(text: String, skin: Skin) : TextButton(text, skin) {
//        
//    }
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))
    val standardChessButton = TextButton("Standard Chess", skin)
    val grandChessButton = TextButton("Grand Chess", skin)
    val capablancaChessButton = TextButton("Capablanca Chess", skin)
    val chess960Button = TextButton("Chess960", skin)
    val title = Label("Welcome to Chess", skin)

    val humanPlayer1Button = TextButton("Human Player", skin)
    val computerPlayer1Button = TextButton("Computer Player", skin)

    val humanPlayer2Button = TextButton("Human Player", skin)
    val computerPlayer2Button = TextButton("Computer Player", skin)

    val startButton = TextButton("Start", skin)

    val titlePlayer1 = Label("Add player1", skin)
    val titlePlayer2 = Label("Add player2", skin)

    val players = mutableListOf(PlayerType.HUMAN, PlayerType.HUMAN)

    override fun show() {
        humanPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[0] = PlayerType.HUMAN
                humanPlayer1Button.setColor(200f, 0f, 0f, 100f)
                computerPlayer1Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        humanPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[1] = PlayerType.HUMAN
                humanPlayer2Button.setColor(200f, 0f, 0f, 100f)
                computerPlayer2Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        computerPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[0] = PlayerType.COMPUTER
                computerPlayer1Button.setColor(200f, 0f, 0f, 100f)
                humanPlayer1Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        computerPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[1] = PlayerType.COMPUTER
                computerPlayer2Button.setColor(200f, 0f, 0f, 100f)
                humanPlayer2Button.setColor(255f, 255f, 255f, 100f)
            }
        })

        standardChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToSetupScreen(StandardChess())
            }
        })

        grandChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToSetupScreen(GrandChess())
            }
        })

        capablancaChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToSetupScreen(CapablancaChess())
            }
        })

        chess960Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToSetupScreen(Chess960())
            }
        })

//        startButton.addListener(object : ChangeListener() {
//            override fun changed(event: ChangeEvent?, actor: Actor?) {
//                switchToGameScreen()
//            }
//        })

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
        table.add(standardChessButton).colspan(2).padBottom(20f)
        table.add(grandChessButton).colspan(2).padBottom(20f)
        table.add(capablancaChessButton).colspan(2).padBottom(20f)
        table.row()
        table.add(chess960Button).colspan(2).padBottom(30f)
        table.row()
//        table.add(startButton).colspan(6).padBottom(20f).center()
//        table.row()
        stage.addActor(table)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    private fun switchToSetupScreen(gameType: GameType) {
        val gameEngine = Game(gameType)
//        game.removeScreen<SetupScreen>() // idk why we need this line
//        game.addScreen(SetupScreen(game, gameEngine))
//        game.setScreen<SetupScreen>()

        game.removeScreen<GameScreen>()
        game.addScreen(GameScreen(game, gameEngine, players))
        dispose()
        game.setScreen<GameScreen>()

//        game.removeScreen<GameOverScreen>()
//        val playerName = "pepe"
//        game.addScreen(GameOverScreen(game, gameEngine, playerName))
//        dispose()
//        game.setScreen<GameOverScreen>()
    }

//    private fun switchToGameScreen() {
//        game.removeScreen<GameScreen>() // idk why we need this line
//        val gameScreen = GameScreen(game, gameEngine, players)
//        game.addScreen(gameScreen)
//        game.setScreen<GameScreen>()
//        dispose()
//    }
}

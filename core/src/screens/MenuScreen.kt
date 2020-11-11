package screens

import Janggi
import Xiangqi
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
    val janggiButton = TextButton("Janggi", skin)
    val xiangqiButton = TextButton("Xiangqi", skin)
    val title = Label("Welcome to Chess", skin)
    var isOnline = false

    val onlineModeButton = TextButton("Play Online", skin)
    val localModeButton = TextButton("Play Local/Against Computer", skin)


    val startButton = TextButton("Start", skin)

    val gameModeTitle = Label("Select Game Mode", skin)

    override fun show() {
        onlineModeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                isOnline = true
                onlineModeButton.setColor(200f, 0f, 0f, 100f)
                localModeButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        localModeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                isOnline = false
                localModeButton.setColor(200f, 0f, 0f, 100f)
                onlineModeButton.setColor(255f, 255f, 255f, 100f)
            }
        })

        standardChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(StandardChess(), isOnline)
            }
        })

        grandChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(GrandChess(), isOnline)
            }
        })

        capablancaChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(CapablancaChess(), isOnline)
            }
        })

        chess960Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(Chess960(), isOnline)
            }
        })

        janggiButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(Janggi(), isOnline)
            }
        })

        xiangqiButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToPreGameScreen(Xiangqi(), isOnline)
            }
        })

//        startButton.addListener(object : ChangeListener() {
//            override fun changed(event: ChangeEvent?, actor: Actor?) {
//                switchToGameScreen()
//            }
//        })

        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).top()
        table.row()
        table.add(onlineModeButton).colspan(3).padBottom(50f)
        table.add(localModeButton).colspan(3).padBottom(50f)
        table.row()
        table.add(gameModeTitle).colspan(6).padBottom(20f)
        table.row()
        table.add(standardChessButton).colspan(2).padBottom(20f)
        table.add(grandChessButton).colspan(2).padBottom(20f)
        table.add(capablancaChessButton).colspan(2).padBottom(20f)
        table.row()
        table.add(chess960Button).colspan(2).padBottom(30f)
        table.add(janggiButton).colspan(2).padBottom(30f)
        table.add(xiangqiButton).colspan(2).padBottom(30f)
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

    private fun switchToPreGameScreen(gameType: GameType, isOnline: Boolean) {
        if (!isOnline) {
            game.removeScreen<PlayerScreen>()
            game.addScreen(PlayerScreen(game, gameType))
            game.setScreen<PlayerScreen>()
            dispose()
        } else {
            game.removeScreen<OnlineScreen>()
            game.addScreen(OnlineScreen(game, gameType))
            game.setScreen<OnlineScreen>()
            dispose()
        }
    }
}

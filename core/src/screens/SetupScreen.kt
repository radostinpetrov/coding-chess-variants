package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.MyGdxGame
import com.mygdx.game.PlayerType
import ktx.app.KtxScreen
import main.kotlin.Game
import main.kotlin.gameTypes.chess.CapablancaChess

class SetupScreen(val game: MyGdxGame, val gameEngine: Game) : KtxScreen {
    val stage = Stage()
    val table = Table()
    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))

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
            }
        })

        humanPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[1] = PlayerType.HUMAN
            }
        })

        computerPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[0] = PlayerType.COMPUTER
            }
        })

        computerPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                players[1] = PlayerType.COMPUTER
            }
        })

        startButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToGameScreen()
            }
        })

        table.width = 800f
        table.add(titlePlayer1).colspan(2).center().padBottom(20f)
        table.row()
        table.add(humanPlayer1Button).colspan(1).center().padBottom(50f)
        table.add(computerPlayer1Button).colspan(1).center().center().padBottom(50f)
        table.row()
        table.add(titlePlayer2).colspan(2).center().padBottom(20f)
        table.row()
        table.add(humanPlayer2Button).colspan(1).center().padBottom(50f)
        table.add(computerPlayer2Button).colspan(1).center().padBottom(50f)
        table.row()
        table.add(startButton).colspan(2).center()
        table.row()
        table.setFillParent(true)
        stage.addActor(table)
        Gdx.input.setInputProcessor(stage)
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    private fun switchToGameScreen() {
        game.removeScreen<GameScreen>() // idk why we need this line
        val gameScreen = GameScreen(game, gameEngine, players)
        game.addScreen(gameScreen)
        game.setScreen<GameScreen>()
        dispose()
    }
}

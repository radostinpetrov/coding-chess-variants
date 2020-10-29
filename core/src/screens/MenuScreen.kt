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
import ktx.app.KtxScreen
import main.kotlin.Game
import main.kotlin.GameMove
import main.kotlin.gameTypes.GameType
import main.kotlin.gameTypes.chess.StandardChess
import main.kotlin.players.HumanPlayer

class MenuScreen(val game: MyGdxGame) : KtxScreen {
    val stage = Stage()
    val table = Table()
    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))
    val standardChessButton = TextButton("Standard Chess", skin)
    val grandChessButton = TextButton("Grand Chess", skin)
    val capablancaChessButton = TextButton("Capablanca Chess", skin)
    val title = Label("Welcome to Chess, try not to cringe too hard!", skin)

    override fun show() {
        standardChessButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToSetupScreen(StandardChess())
            }
        })

        table.width = 800f
        table.add(title).colspan(3).center().padBottom(50f)
        table.row()
        table.add(standardChessButton).colspan(1).center()
        table.add(grandChessButton).colspan(1).center()
        table.add(capablancaChessButton).colspan(1).center()
        table.row()
        table.setFillParent(true)
        stage.addActor(table)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    private fun switchToSetupScreen(gameType: GameType) {
        val humanPlayer = object : HumanPlayer() {
            override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
                return choiceOfMoves[0]
            }
        }
        // gameType.addPlayer(humanPlayer)
        // gameType.addPlayer(ComputerPlayer(200))
        // gameType.addPlayer(ComputerPlayer(200))

        val gameEngine = Game(gameType)
        game.removeScreen<SetupScreen>() // idk why we need this line
        game.addScreen(SetupScreen(game, gameEngine))
        game.setScreen<SetupScreen>()
        dispose()
    }
}

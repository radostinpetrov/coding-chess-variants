package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.MyGdxGame
import com.mygdx.game.PlayerType
import ktx.app.KtxScreen
import main.kotlin.Game
import main.kotlin.gameTypes.GameType

class OnlineScreen(val game: MyGdxGame, val gameType: GameType) : KtxScreen {

    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))

    val title = Label("Select Players", skin)




    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    private fun switchToGameScreen(gameType: GameType, players: MutableList<PlayerType>) {
        val gameEngine = Game(gameType)
        game.removeScreen<GameScreen>()
        game.addScreen(GameScreen(game, gameEngine, players))
        dispose()
        game.setScreen<GameScreen>()
    }
}

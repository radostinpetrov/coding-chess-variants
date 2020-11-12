package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.mygdx.game.MyGdxGame
import ktx.app.KtxScreen
import main.kotlin.Game
import main.kotlin.gameTypes.GameType
import main.kotlin.players.NetworkEnemyPlayer
import main.kotlin.players.NetworkHumanPlayer
import main.kotlin.players.WebsocketClientManager

class OnlineScreen(val game: MyGdxGame, val gameType: GameType) : KtxScreen {

    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))

    val title = Label("Looking for Players...", skin)

    val startButton = TextButton("Start", skin)

    val websocketClientManager = WebsocketClientManager { m: Int -> humanPlayer = m }

    var humanPlayer: Int? = null

    override fun show() {
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).top()
        table.row()

        stage.addActor(table)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()

        if (humanPlayer != null) {
            switchToGameScreen()
        }
    }

    private fun switchToGameScreen() {
        val gameEngine = Game(gameType)
        when (humanPlayer) {
            1 -> {
                gameEngine.gameType.addPlayer(NetworkHumanPlayer(websocketClientManager))
                val enemyPlayer = NetworkEnemyPlayer()
                gameEngine.gameType.addPlayer(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
            }
            2 -> {
                val enemyPlayer = NetworkEnemyPlayer()
                gameEngine.gameType.addPlayer(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
                gameEngine.gameType.addPlayer(NetworkHumanPlayer(websocketClientManager))
            }
        }
        game.removeScreen<GameScreen>()
        game.addScreen(GameScreen(game, gameEngine))
        dispose()
        game.setScreen<GameScreen>()
    }
}
package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.mygdx.game.MyGdxGame
import ktx.app.KtxScreen
import gameTypes.GameType
import players.NetworkEnemyPlayer
import players.NetworkHumanPlayer
import players.WebsocketClientManager

class OnlineScreen(val game: MyGdxGame, val gameType: GameType, val clockList: List<Int>?) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))

    val title = Label("Looking for Players...", skin)

    val startButton = TextButton("Start", skin)

    val websocketClientManager = WebsocketClientManager({ m: Int, seed: Double ->
        humanPlayer = m
        gameType.seed = seed
    }, gameType::class.simpleName, if (clockList != null) (clockList[0]).toString() else "")

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
        val gameScreen = GameScreen(game, gameType, clockList)
        when (humanPlayer) {
            1 -> {
                gameType.addPlayer(NetworkHumanPlayer(gameScreen, websocketClientManager))
                val enemyPlayer = NetworkEnemyPlayer(gameScreen)
                gameType.addPlayer(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
            }
            2 -> {
                val enemyPlayer = NetworkEnemyPlayer(gameScreen)
                gameType.addPlayer(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
                gameType.addPlayer(NetworkHumanPlayer(gameScreen, websocketClientManager))
            }
        }
        game.removeScreen<GameScreen>()
        game.addScreen(gameScreen)
        dispose()
        game.setScreen<GameScreen>()
    }
}

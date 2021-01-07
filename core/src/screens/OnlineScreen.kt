package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.mygdx.game.MyGdxGame
import ktx.app.KtxScreen
import gameTypes.GameType2D
import players.FrontendPlayer
import players.NetworkEnemyPlayer
import players.NetworkHumanPlayer
import players.WebsocketClientManager

class OnlineScreen(val game: MyGdxGame, username: String, val gameType: GameType2D, val clockList: List<Int>?) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

    val title = Label("Looking for Players...", skin)

    val websocketClientManager = WebsocketClientManager (
        { m: Int, seed: Double ->
            humanPlayer = m
            gameType.seed = seed
        },
        username,
        gameType::class.simpleName, if (clockList != null) (clockList[0]).toString() else ""
    )

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
        val clockFlag = clockList != null
        val gameScreen = GameScreen(game, gameType, clockFlag, true)
        val players = mutableListOf<FrontendPlayer>()
        when (humanPlayer) {
            1 -> {
                players.add(NetworkHumanPlayer(gameScreen, websocketClientManager, Color.WHITE, "White"))
                val enemyPlayer = NetworkEnemyPlayer(gameScreen, Color.BLACK,"Black")
                players.add(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
            }
            2 -> {
                val enemyPlayer = NetworkEnemyPlayer(gameScreen, Color.WHITE, "White")
                players.add(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
                players.add(NetworkHumanPlayer(gameScreen, websocketClientManager, Color.BLACK, "Black"))
            }
        }
        if (clockFlag) {
            players[0].endClock = clockList!![0]
            players[1].endClock = clockList[1]
        }

        gameScreen.initPlayers(players)
        game.removeScreen<GameScreen>()
        game.addScreen(gameScreen)
        dispose()
        game.setScreen<GameScreen>()
    }
}

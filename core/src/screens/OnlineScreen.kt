package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.MyGdxGame
import ktx.app.KtxScreen
import gameTypes.GameType2D
import org.json.JSONObject
import players.FrontendPlayer
import players.NetworkEnemyPlayer
import players.NetworkHumanPlayer
import players.WebsocketClientManager

/**
 * Displays the Online waiting screen, where the player waits to be connected to another user.
 */
class OnlineScreen(val game: MyGdxGame, username: String, val gameType: GameType2D, val clockList: List<Int>?) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

    val title = Label("Looking for Players...", skin)

    val websocketClientManager = WebsocketClientManager (
        { jsonMessage: JSONObject ->
            humanPlayer = jsonMessage.getInt("player")
            gameType.seed = jsonMessage.getDouble("seed")
            playerUsername = jsonMessage.getString("playerUsername")
            opponentUsername = jsonMessage.getString("opponentUsername")
            playerElo = jsonMessage.getInt("playerElo")
            opponentElo = jsonMessage.getInt("opponentElo")

        },
        username,
        gameType::class.simpleName, if (clockList != null) (clockList[0]).toString() else ""
    )

    var humanPlayer: Int? = null
    var playerUsername: String = ""
    var opponentUsername: String = ""
    var playerElo: Int? = null
    var opponentElo: Int? = null

    /**
     * This is called when the class is created, before render.
     * Initialises the waiting text.
     */
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

    /**
     * Switches to the game screen. Sets the colour of the players.
     */
    private fun switchToGameScreen() {
        val clockFlag = clockList != null
        val gameScreen = GameScreen(game, gameType, clockFlag, true)
        val players = mutableListOf<FrontendPlayer>()
        val networkHumanPlayer: NetworkHumanPlayer
        when (humanPlayer) {
            1 -> {
                networkHumanPlayer = NetworkHumanPlayer(
                    gameScreen, websocketClientManager, Color.WHITE, "White", playerUsername, playerElo
                )
                players.add(networkHumanPlayer)
                val enemyPlayer = NetworkEnemyPlayer(
                    gameScreen, Color.BLACK,"Black", opponentUsername, opponentElo
                )
                players.add(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
            }
            2 -> {
                val enemyPlayer = NetworkEnemyPlayer(
                    gameScreen, Color.WHITE, "White", opponentUsername, opponentElo
                )
                players.add(enemyPlayer)
                websocketClientManager.networkEnemyPlayer = enemyPlayer
                networkHumanPlayer = NetworkHumanPlayer(
                    gameScreen, websocketClientManager, Color.BLACK, "Black", playerUsername, playerElo
                )
                players.add(networkHumanPlayer)
            }
            else -> {
                // This should not happen, print error
                return
            }
        }
        if (clockFlag) {
            players[0].endClock = clockList!![0]
            players[1].endClock = clockList[1]
        }

        gameScreen.initPlayers(players)
        gameScreen.networkHumanPlayer = networkHumanPlayer
        game.removeScreen<GameScreen>()
        game.addScreen(gameScreen)
        dispose()
        game.setScreen<GameScreen>()
    }
}

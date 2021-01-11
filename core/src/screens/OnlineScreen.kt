package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.mygdx.game.MyGdxGame
import gameTypes.GameType
import gameTypes.chess.AbstractChess2D
import gameTypes.hex.HexagonalChess
import ktx.app.KtxScreen
import org.json.JSONObject
import players.FrontendPlayer
import players.FrontendPlayerHex
import players.NetworkEnemyPlayer
import players.NetworkEnemyPlayerHex
import players.NetworkHumanPlayer
import players.NetworkHumanPlayerHex
import players.WebsocketClientManager
import players.WebsocketClientManagerHex
import java.lang.Exception

/**
 * Displays the Online waiting screen, where the player waits to be connected to another user.
 */
class OnlineScreen(val game: MyGdxGame, username: String, val gameType: GameType<*, *, *, *>, val clockList: List<Long>?) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))

    val title = Label("Looking for Players...", skin)

    val websocketClientManager = WebsocketClientManager(
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

    val websocketClientManagerHex = WebsocketClientManagerHex(
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
        if (gameType is HexagonalChess) {
            val gameScreenHex = GameScreenHexagonal(game, gameType, clockFlag, true)
            websocketClientManagerHex.gameScreen = gameScreenHex
            val players = mutableListOf<FrontendPlayerHex>()
            val networkEnemyPlayer: NetworkEnemyPlayerHex
            val networkHumanPlayer: NetworkHumanPlayerHex
            when (humanPlayer) {
                1 -> {
                    networkHumanPlayer = NetworkHumanPlayerHex(
                        gameScreenHex, websocketClientManagerHex, Color.WHITE, "White", playerUsername, playerElo
                    )
                    players.add(networkHumanPlayer)
                    networkEnemyPlayer = NetworkEnemyPlayerHex(
                        gameScreenHex, Color.BLACK, "Black", opponentUsername, opponentElo
                    )
                    players.add(networkEnemyPlayer)
                    websocketClientManagerHex.networkEnemyPlayer = networkEnemyPlayer
                }
                2 -> {
                    networkEnemyPlayer = NetworkEnemyPlayerHex(
                        gameScreenHex, Color.WHITE, "White", opponentUsername, opponentElo
                    )
                    players.add(networkEnemyPlayer)

                    networkHumanPlayer = NetworkHumanPlayerHex(
                        gameScreenHex, websocketClientManagerHex, Color.BLACK, "Black", playerUsername, playerElo
                    )
                    players.add(networkHumanPlayer)
                }
                else -> {
                    // This should not happen, print error
                    return
                }
            }

            websocketClientManagerHex.networkHumanPlayer = networkHumanPlayer
            websocketClientManagerHex.networkEnemyPlayer = networkEnemyPlayer

            if (clockFlag) {
                players[0].endClock = clockList!![0]
                players[1].endClock = clockList[1]
            }

            gameScreenHex.initPlayers(players)
            gameScreenHex.networkHumanPlayer = networkHumanPlayer
            game.removeScreen<GameScreenHexagonal>()
            game.addScreen(gameScreenHex)
            dispose()
            game.setScreen<GameScreenHexagonal>()
        } else if (gameType is AbstractChess2D) {
            val gameScreen = GameScreen(game, gameType, clockFlag, true)
            websocketClientManager.gameScreen = gameScreen
            val players = mutableListOf<FrontendPlayer>()
            val networkEnemyPlayer: NetworkEnemyPlayer
            val networkHumanPlayer: NetworkHumanPlayer
            when (humanPlayer) {
                1 -> {
                    networkHumanPlayer = NetworkHumanPlayer(
                        gameScreen, websocketClientManager, Color.WHITE, "White", playerUsername, playerElo
                    )
                    players.add(networkHumanPlayer)
                    networkEnemyPlayer = NetworkEnemyPlayer(
                        gameScreen, Color.BLACK, "Black", opponentUsername, opponentElo
                    )
                    players.add(networkEnemyPlayer)
                    websocketClientManager.networkEnemyPlayer = networkEnemyPlayer
                }
                2 -> {
                    networkEnemyPlayer = NetworkEnemyPlayer(
                        gameScreen, Color.WHITE, "White", opponentUsername, opponentElo
                    )
                    players.add(networkEnemyPlayer)

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

            websocketClientManager.networkHumanPlayer = networkHumanPlayer
            websocketClientManager.networkEnemyPlayer = networkEnemyPlayer

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
        } else {
            throw Exception("Unsupported game type")
        }
    }
}

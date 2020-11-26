package screens.leaderboard

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
import org.json.JSONArray
import org.json.JSONObject
import screens.MenuScreen

class LeaderboardScreen(val game: MyGdxGame) : KtxScreen {
    val stage = Stage()
    val table = Table()
    val leaderboardTable = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))
    val menuButton = TextButton("Menu Screen", skin)

    val websocketClient = LeaderboardWebsocketClient(this)
    val title = Label("Leaderboard", skin)

    override fun show() {
        menuButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToMenuScreen()
            }
        })
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).top()
        table.row()
        leaderboardTable.width = 500f
        leaderboardTable.height = 500f
        table.add(leaderboardTable).colspan(6).padBottom(20f)
        table.row()
        table.add(menuButton).colspan(6).padBottom(20f)
        table.row()
//        table.add(startButton).colspan(6).padBottom(20f).center()
//        table.row()
        stage.addActor(table)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    private fun switchToMenuScreen() {
        game.removeScreen<MenuScreen>()
        game.addScreen(MenuScreen(game))
        dispose()
        game.setScreen<MenuScreen>()
    }

    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    fun loadLeaderboard(leaderboardArr: JSONArray) {
        for (i in 0 until leaderboardArr.length()) {
            val entry = leaderboardArr.getJSONObject(i)
            val label = Label(
                "Rank: ${i + 1} | Username: ${entry.getString("username")} | Elo: ${entry.getInt("elo")}",
                skin
            )
            leaderboardTable.add(label)
            leaderboardTable.row()
        }

    }
}

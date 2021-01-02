package screens

import Outcome
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
import players.FrontendPlayer
import players.Player

class GameOverScreen(val game: MyGdxGame, val outcome: Outcome, val libToFrontEndPlayer: Map<Player, FrontendPlayer>) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))
    val playAgainButton = TextButton("Play Again?", skin)

    override fun show() {
        val sb = StringBuilder()

        when (outcome) {
            is Outcome.Win -> {
                val colourString = libToFrontEndPlayer[outcome.winner]!!.name
                sb.append("$colourString wins ")
            }
            is Outcome.Draw -> {
                sb.append("Stalemate ")
            }
        }

        sb.append(outcome.message)

        val title = Label(sb.toString(), skin)

        playAgainButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToMenuScreen()
            }
        })
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).center().top()
        table.row()
        table.add(playAgainButton).colspan(6).padBottom(20f)
        table.row()
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
}

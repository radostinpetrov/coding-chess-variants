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

/**
 * Displays the game over screen when a player wins or stalemate and the game ends.
 */
class GameOverScreen(val game: MyGdxGame, val outcome: Outcome, val libToFrontEndPlayer: Map<Player, FrontendPlayer>) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
    val playAgainButton = TextButton("Play Again?", skin)

    /**
     * This is called when the class is created before render.
     * Initialises the outcome message and playAgainButton.
     */
    override fun show() {

        /* Initialise the outcome message */
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

        /* Initialise the playAgainButton */
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

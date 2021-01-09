package screens

import endconditions.Outcome
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.MyGdxGame
import players.FrontendPlayer
import players.Player

/**
 * Displays the game over screen when a player wins or stalemate and the game ends.
 */
class GameOverPopUp(val game: MyGdxGame, val stage: Stage, val screen: Screen, val outcome: Outcome, val shapeRenderer: ShapeRenderer, val windowWidth: Int, val windowHeight: Int, val libToFrontEndPlayer: Map<Player, FrontendPlayer>) {
    val table = Table()
    val skin = Skin(Gdx.files.internal("skin/uiskin.json"))
    val playAgainButton = TextButton("Play Again?", skin)
    var title: Label

    /**
     * This is called when the class is created before render.
     * Initialises the outcome message and playAgainButton.
     */
    init {
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

        title = Label(sb.toString(), skin)

        /* Initialise the playAgainButton */
        playAgainButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToMenuScreen()
            }
        })

        stage.addActor(title)
        stage.addActor(playAgainButton)
    }

    fun show() {
        /* Set the background to transparent. */
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 0f, 0f, 0.7f)
        shapeRenderer.rect(0f, 0f, windowWidth.toFloat(), windowHeight.toFloat())
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)

        title.setPosition(windowWidth.toFloat()/2 - title.width/2, windowHeight.toFloat() * 2/3)
        playAgainButton.setPosition(windowWidth.toFloat()/2 - playAgainButton.width/2, windowWidth.toFloat() * 1/2)
    }


    private fun switchToMenuScreen() {
        game.removeScreen<MenuScreen>()
        game.addScreen(MenuScreen(game))
        screen.dispose()
        game.setScreen<MenuScreen>()
    }
}

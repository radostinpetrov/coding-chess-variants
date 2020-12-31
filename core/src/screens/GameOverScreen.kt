package screens

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
import gameTypes.GameType2D

class GameOverScreen(val game: MyGdxGame, val gameEngine: GameType2D, val winner: String) : KtxScreen {
    val stage = Stage()
    val table = Table()

    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))
    val playAgainButton = TextButton("Play Again?", skin)
    val title = Label("GAME OVER.\n\n $winner wins!", skin)

    override fun show() {
        playAgainButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToMenuScreen()
            }
        })
        table.width = 800f
        table.height = 800f
        table.setPosition(0f, 150f)
        table.add(title).colspan(6).padBottom(20f).top()
        table.row()
        table.add(playAgainButton).colspan(6).padBottom(20f)
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
}

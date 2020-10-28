package screens

import Game
import GameMove
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.mygdx.game.MyGdxGame
import gameTypes.GameType
import gameTypes.chess.StandardChess
import ktx.app.KtxScreen
import players.ComputerPlayer
import players.HumanPlayer

class SetupScreen(val game: MyGdxGame, val gameEngine: Game) : KtxScreen {
    val stage = Stage()
    val table = Table()
    val skin = Skin(Gdx.files.internal("core/assets/skin/uiskin.json"))

    val humanPlayer1Button = TextButton("Human Player", skin)
    val computerPlayer1Button = TextButton("Computer Player", skin)

    val humanPlayer2Button = TextButton("Human Player", skin)
    val computerPlayer2Button = TextButton("Computer Player", skin)

    val startButton = TextButton("Start", skin)

    val titlePlayer1 = Label("Add player1", skin)
    val titlePlayer2 = Label("Add player2", skin)

    override fun show() {
        val human1 = object : HumanPlayer() {
            override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
                return choiceOfMoves[0]
            }
        }
        val human2 = object : HumanPlayer() {
            override fun getTurn(choiceOfMoves: List<GameMove>): GameMove {
                return choiceOfMoves[0]
            }
        }
        humanPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameEngine.gameType.players.add(0, human1)
            }
        })
        computerPlayer1Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameEngine.gameType.addPlayer(ComputerPlayer(500))
            }
        })
        humanPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameEngine.gameType.addPlayer(human2)
            }
        })
        computerPlayer2Button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                gameEngine.gameType.addPlayer(ComputerPlayer(500))
            }
        })
        startButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                switchToGameScreen()
            }
        })
        table.width = 800f
        table.add(titlePlayer1).colspan(2).center().padBottom(20f)
        table.row()
        table.add(humanPlayer1Button).colspan(1).center().padBottom(50f)
        table.add(computerPlayer1Button).colspan(1).center().center().padBottom(50f)
        table.row()
        table.add(titlePlayer2).colspan(2).center().padBottom(20f)
        table.row()
        table.add(humanPlayer2Button).colspan(1).center().padBottom(50f)
        table.add(computerPlayer2Button).colspan(1).center().padBottom(50f)
        table.row()
        table.add(startButton).colspan(2).center()
        table.row()
        table.setFillParent(true)
        stage.addActor(table)
        Gdx.input.setInputProcessor(stage)
    }


    override fun render(delta: Float) {
        stage.draw()
        stage.act()
    }

    private fun switchToGameScreen() {
        game.removeScreen<GameScreen>() //idk why we need this line
        val gameScreen = GameScreen(game, gameEngine)
        gameEngine.addObserver(gameScreen)
        game.addScreen(gameScreen)
        game.setScreen<GameScreen>()
        dispose()
    }
}
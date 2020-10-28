package screens

import Coordinate
import Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.MyGdxGame
import com.mygdx.game.assets.Textures
import ktx.app.KtxScreen
import main.kotlin.Observer

class GameScreen(val game: MyGdxGame, val gameEngine: Game) : KtxScreen, Observer{

    private val squareWidth: Float = 100f
    private val pieceWidth: Float = 70f
    private val shapeRenderer  = ShapeRenderer()

    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null
    var board = gameEngine.gameType.board
    val rows = board.m
    val columns = board.n

    var currPlayer = gameEngine.gameType.players[0]

    private val textures= Textures(game.assets)

    // todo: hard coded, enum for colour ?
    val playerMapping = mapOf(gameEngine.gameType.players[0] to Color.WHITE, gameEngine.gameType.players[1] to Color.BLACK)
    override fun show() {
         gameEngine.start()
    }

    override fun render(delta: Float) {
        board = gameEngine.gameType.board
        drawBoard()
        drawPieces()
        controls()
//        if (!gameEngine.turn()) {
//            // game over
//            TODO()
//        }
        currPlayer = gameEngine.gameType.getCurrPlayer()
    }

    private fun reverseRow(index: Int) {
        val b = board.board

        for (i in 0 until columns / 2) {
            val tmpPiece = b[index][i]
            b[index][i] = b[index][columns - i - 1]
            b[index][columns - i - 1] = tmpPiece
        }
    }

    private fun flipBoard() {
        val b = board.board
        if (rows % 2 != 0) {
            reverseRow(rows / 2)
        }

        for (i in 0 until (rows / 2) - 1) {
            for (j in 0 until columns) {
                val tmpPiece = b[i][j]
                b[i][j] = b[rows - i - 1][columns - j -1]
                b[rows -  i - 1][columns - j - 1] = tmpPiece
            }
        }
    }

    private fun controls() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (srcX == null) {
                srcX = Gdx.input.getX()
                srcY = Gdx.graphics.getHeight() - Gdx.input.getY()
            }

            else if (srcX != null && dstX == null) {
                dstX = Gdx.input.getX()
                dstY = Gdx.graphics.getHeight() - Gdx.input.getY()
            }

            else {
                srcX = Gdx.input.getX()
                srcY = Gdx.graphics.getHeight() - Gdx.input.getY()
                dstX = null
                dstY = null
            }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            srcX = null
            srcY = null
            dstX = null
            dstY = null
        }
    }

    fun highlightValidMoves(coordinate: Coordinate) {
        val moves = gameEngine.gameType.getValidMoves(currPlayer)
        val toCoordinates = moves.filter { m -> m.from == coordinate }.map{ m -> m.to }


        for (c in toCoordinates) {
            print(c)
            shapeRenderer.color = Color.PINK
            shapeRenderer.rect(squareWidth * c.x,squareWidth * (7 - c.y), squareWidth, squareWidth)
        }
        println()
    }

    private fun drawBoard(flipped: Boolean = false) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        var colour1: Color = Color.TAN
        var colour2: Color = Color.BROWN

        if (flipped) {
            colour1 = Color.BROWN
            colour2 = Color.TAN
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        var toCoordinates = listOf<Coordinate>()

        for (i in 0 until columns) {
            for (j in 0 until rows) {
                if ((i + j) % 2 == 0) {
                    shapeRenderer.setColor(colour1)
                }
                else {
                    shapeRenderer.setColor(colour2)
                }

                if (srcX != null && srcY != null) {
                    if (squareWidth * i <= srcX!! && srcX!! < squareWidth * (i + 1) && squareWidth * j <= srcY!! && srcY!! < squareWidth * (j + 1)) {
                        val moves = gameEngine.gameType.getValidMoves(currPlayer)
                        toCoordinates = moves.filter { m -> m.from == Coordinate(i, 7 - j) }.map{ m -> m.to }
                        shapeRenderer.setColor(Color.FOREST)
                    }
                }
                if (dstX != null && dstY != null) {
                    if (squareWidth * i <= dstX!! && dstX!! < squareWidth * (i + 1) && squareWidth * j <= dstY!! && dstY!! < squareWidth * (j + 1)) {
                        shapeRenderer.setColor(Color.GREEN)
                    }
                }
                shapeRenderer.rect(squareWidth * i,squareWidth * j, squareWidth, squareWidth)
            }
        }

        shapeRenderer.color = Color(Color.rgba4444(30f, 76f,63f, 0.75f))

        for (c in toCoordinates) {
            shapeRenderer.circle(squareWidth * c.x + 50,squareWidth * (7 - c.y) + 50, 12f)

        }

        shapeRenderer.end()
    }

    private fun drawPieces() {
        val batch = game.batch
        batch.begin()
        val pieces = board.getPieces()

        for ((p, c) in pieces) {
            val texture = textures.getTextureFromPiece(p, playerMapping[p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2
            sprite.setPosition(squareWidth * c.x + posWithinSquare, squareWidth * c.y + posWithinSquare)

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
            sprite.draw(game.batch)
        }

        batch.end()
    }

    fun render2() {
        drawBoard()
        drawPieces()
        controls()
    }

    override fun update() {
        this.render2()
    }
}

package boards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import main.kotlin.GameMove
import main.kotlin.boards.Board2D
import main.kotlin.players.Player

class ChessBoard(
    shapeRenderer: ShapeRenderer, board: Board2D, batch: Batch, squareWidth: Float, textures: Textures,
    playerMapping: Map<Player, Color>
) : GUIBoard(shapeRenderer, board, batch,
    squareWidth,
    textures, playerMapping
) {


    override fun drawBoard(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped: Boolean, isPromotionScreen: Boolean) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        var colour2: Color = Color.TAN
        var colour1: Color = Color.BROWN

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        for (i in 0 until columns) {
            for (j in 0 until rows) {
                if ((i + j) % 2 == 0) {
                    shapeRenderer.color = colour1
                } else {
                    shapeRenderer.color = colour2
                }
                if (!isPromotionScreen) {

                    if (srcX != null && srcY != null) {
                        if (squareWidth * i <= srcX!! && srcX!! < squareWidth * (i + 1) && squareWidth * j <= srcY!! && srcY!! < squareWidth * (j + 1)) {
                            shapeRenderer.color = Color.FOREST
                        }
                    }


                }

                shapeRenderer.rect(squareWidth * i, squareWidth * j, squareWidth, squareWidth)
            }
        }

        shapeRenderer.end()
    }

}
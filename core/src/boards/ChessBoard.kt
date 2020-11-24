package boards

import GameMove
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import players.Player

class ChessBoard(
    shapeRenderer: ShapeRenderer,
    board: Board2D,
    batch: Batch,
    squareWidth: Float,
    textures: Textures,
    playerMapping: Map<Player, Color>
) : GUIBoard(
    shapeRenderer, board, batch,
    squareWidth,
    textures, playerMapping
) {

    override fun drawBoard(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped: Boolean, isPromotionScreen: Boolean) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        val colour2: Color = Color.TAN
        val colour1: Color = Color.BROWN

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
                        if (squareWidth * i <= srcX && srcX < squareWidth * (i + 1) && squareWidth * j <= srcY && srcY < squareWidth * (j + 1)) {
                            shapeRenderer.color = Color.FOREST
                        }
                    }
                }

                val x = if (flipped) (columns - i - 1) else i
                val y = if (flipped) (rows - j - 1) else j

                shapeRenderer.rect(squareWidth * x, squareWidth * y, squareWidth, squareWidth)
            }
        }

        shapeRenderer.end()
    }
}

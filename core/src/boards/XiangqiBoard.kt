package boards

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import players.FrontendPlayer
import players.Player

/**
 * Class to draw the board for the asian chess variants Xiangqi and Janggi.
 * Extends GUIBoard.
 */
class XiangqiBoard(
    shapeRenderer: ShapeRenderer,
    board: Board2D,
    batch: Batch,
    squareWidth: Float,
    textures: Textures,
    playerMapping: Map<Player, FrontendPlayer>
) : GUIBoard(
    shapeRenderer, board, batch,
    squareWidth,
    textures, playerMapping, font = null
) {

    private fun drawLineWithCenterOffset(x1: Int, y1: Int, x2: Int, y2: Int, width: Float) {
        val offset = squareWidth / 2
        shapeRenderer.rectLine(squareWidth * x1 + offset, squareWidth * y1 + offset, squareWidth * x2 + offset, squareWidth * y2 + offset, width)
    }

    /**
     * Draws the Xiangqi board.
     * Since board is symmetrical it does not need to be flipped.
     * @param srcX highlighted square x coord
     * @param srcY highlighted square y coord
     */
    override fun drawBoard(srcX: Int?, srcY: Int?, flipped: Boolean, isPromotionScreen: Boolean) {
        val lineWidth = 4f
        Gdx.gl.glClearColor(1f, 0.7f, 0.3f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        shapeRenderer.color = Color.BROWN
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        for (j in 0 until rows) {
            drawLineWithCenterOffset(0, j, columns - 1, j, lineWidth)
        }

        for (j in 0 until columns) {
            drawLineWithCenterOffset(j, 0, j, 4, lineWidth)
            drawLineWithCenterOffset(j, 5, j, 9, lineWidth)
        }

        drawLineWithCenterOffset(3, 0, 5, 2, lineWidth)
        drawLineWithCenterOffset(5, 0, 3, 2, lineWidth)
        drawLineWithCenterOffset(3, 7, 5, 9, lineWidth)
        drawLineWithCenterOffset(5, 7, 3, 9, lineWidth)

        shapeRenderer.end()
    }
}

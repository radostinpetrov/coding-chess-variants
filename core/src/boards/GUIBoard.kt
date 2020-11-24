package boards

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import Coordinate
import GameMove
import players.Player

abstract class GUIBoard(val shapeRenderer: ShapeRenderer, val board: Board2D, val batch: Batch, val squareWidth: Float, val textures: Textures, val playerMapping: Map<Player, Color>) {

    val rows = board.n
    val columns = board.m
    private val pieceWidth: Float = squareWidth * 0.85f
    private val possibleMoveCircleRadius = 8f
    private val possibleMoveColour = Color.FOREST

    fun draw(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped : Boolean, isPromotionScreen: Boolean) {
        drawBoard(srcX, srcY, moves, flipped, isPromotionScreen)
        drawPieces(batch, flipped)
        drawDots(srcX, srcY, isPromotionScreen, moves, flipped)
    }

    abstract fun drawBoard(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped : Boolean, isPromotionScreen: Boolean)

    private fun drawPieces(batch: Batch, flipped: Boolean) {
        batch.begin()
        val pieces = board.getPieces()

        for ((p, c) in pieces) {
            val texture = textures.getTextureFromPiece(p, playerMapping[p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2

            val x = if (flipped) (columns - c.x - 1) else c.x
            val y = if (flipped) (rows - c.y - 1) else c.y

            sprite.setPosition(squareWidth * x + posWithinSquare, squareWidth * y + posWithinSquare)

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
        }

        batch.end()
    }

    private fun drawDots(srcX: Int?, srcY: Int?, isPromotionScreen: Boolean, moves: List<GameMove>, flipped: Boolean) {
        if (srcX == null || srcY == null || isPromotionScreen) {
            return
        }

        var toCoordinates = moves.filter { m -> m.displayFrom == Coordinate(
            srcX / squareWidth.toInt(),
            srcY / squareWidth.toInt()
        )
        }
            .map { m -> m.displayTo }

        if (flipped) {
            toCoordinates = toCoordinates.map{c -> Coordinate(columns - c.x - 1, rows - c.y - 1) }
        }

        /* Draw toCoordinates dots for a selected piece. */
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = possibleMoveColour
        val position = squareWidth / 2
        for (c in toCoordinates) {
            shapeRenderer.circle(squareWidth * c.x + position, squareWidth * c.y + position, possibleMoveCircleRadius)
        }

        shapeRenderer.end()
    }
}
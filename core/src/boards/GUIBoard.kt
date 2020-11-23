package boards

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import main.kotlin.GameMove
import main.kotlin.boards.Board2D
import main.kotlin.players.Player

abstract class GUIBoard(val shapeRenderer: ShapeRenderer, val board: Board2D, val batch: Batch, val squareWidth: Float, val textures: Textures, val playerMapping: Map<Player, Color>) {

    val rows = board.n
    val columns = board.m
    private val pieceWidth: Float = squareWidth * 0.85f


    fun draw(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped : Boolean, isPromotionScreen: Boolean) {
        drawBoard(srcX, srcY, moves, flipped, isPromotionScreen)
        drawPieces(flipped, batch)
    }

    abstract fun drawBoard(srcX: Int?, srcY: Int?, moves: List<GameMove>, flipped : Boolean, isPromotionScreen: Boolean)

    fun drawPieces(flipped : Boolean, batch: Batch) {
        batch.begin()
        val pieces = board.getPieces()

        for ((p, c) in pieces) {
            val texture = textures.getTextureFromPiece(p, playerMapping!![p.player]!!)
            val sprite = Sprite(texture)

            val posWithinSquare = (squareWidth - pieceWidth) / 2


            if (flipped) {
                sprite.setPosition(squareWidth * (rows - c.x - 1) + posWithinSquare, squareWidth * (columns - c.y - 1) + posWithinSquare)
            } else {
                sprite.setPosition(squareWidth * c.x + posWithinSquare, squareWidth * c.y + posWithinSquare)
            }


            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
        }

        batch.end()
    }

}
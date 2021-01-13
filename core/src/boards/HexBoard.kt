package boards

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.Textures
import coordinates.Coordinate2D
import moves.MoveHex
import players.FrontendPlayerHex
import players.Player
import screens.GameScreenHexagonal
import kotlin.math.pow

/**
 * Class to draw a hexagonal board 3 different colours of squares.
 */
class HexBoard(
    val shapeRenderer: ShapeRenderer,
    val board: BoardHex,
    val batch: Batch,
    val squareWidth: Float,
    val textures: Textures,
    val libToFrontEndPlayer: Map<Player, FrontendPlayerHex>,
    val font: BitmapFont,
    val gameScreenHexagonal: GameScreenHexagonal
) {

    val hexagonRadius = squareWidth / 2
    private val possibleMoveCircleRadius = 8f
    private val possibleMoveColour = Color.FOREST

    /**
     * Draws the board, pieces and possible moves for a selected piece.
     * @param srcX highlighted square x coord
     * @param srcY highlighted square y coord
     * @param moves list of moves the selected piece can make
     * @param flipped decides if the board should be drawn flipped. eg. for blacks turn.
     * @param isPromotionScreen decides if the highlighted square should be drawn
     */
    fun draw(srcX: Int?, srcY: Int?, moves: List<MoveHex>, flipped: Boolean, isPromotionScreen: Boolean) {
        drawBoard(srcX, srcY, flipped, isPromotionScreen)
        drawPieces(batch, flipped)
        drawDots(srcX, srcY, isPromotionScreen, moves, flipped)
    }

    /**
     * Return a hexagon polygon region with x, y as the center filled in with a solid colour.
     * @param x x coordinate of the center of hexagon.
     * @param y y coordinate of the center of hexagon.
     * @param colour colour type to fill the hexagon.
     * @return PolygonRegion
     */
    fun getPolyRegion(x: Float, y: Float, colour: Int): PolygonRegion {
        val rootThree = (3).toFloat().pow((1.0 / 2.0).toFloat())
        val ax: Float = x + hexagonRadius
        val ay: Float = y.toFloat()
        val bx: Float = x + hexagonRadius / 2
        val by: Float = (y + (rootThree * hexagonRadius) / 2).toFloat()
        val cx: Float = x - (hexagonRadius / 2)
        val cy: Float = (y + (rootThree * hexagonRadius) / 2).toFloat()
        val dx: Float = x - hexagonRadius
        val dy: Float = y.toFloat()
        val ex: Float = x - (hexagonRadius / 2)
        val ey: Float = (y - ((rootThree * hexagonRadius) / 2)).toFloat()
        val fx: Float = x + hexagonRadius / 2
        val fy: Float = (y - ((rootThree * hexagonRadius) / 2)).toFloat()

        val pix = Pixmap(1, 1, Pixmap.Format.RGBA8888)

        when (colour) {
            0 -> {
                pix.setColor(Color.TAN)
            }
            1 -> {
                pix.setColor(Color.BROWN)
            }
            2 -> {
                pix.setColor(Color.ORANGE)
            }
            else -> {
                pix.setColor(Color.FOREST)
            }
        }
        pix.fill()
        val textureSolid = Texture(pix)

        val vertices = floatArrayOf(ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy)
        val triangles = shortArrayOf(0, 1, 2, 0, 2, 3, 0, 3, 4, 0, 4, 5)
        return PolygonRegion(TextureRegion(textureSolid), vertices, triangles)
    }

    /**
     * Draws the Hex chess board.
     * @param srcX highlighted square x coord
     * @param srcY highlighted square y coord
     * @param flipped decides if the board should be drawn flipped. eg. for blacks turn.
     * @param isPromotionScreen decides if the highlighted square should be drawn
     */
    fun drawBoard(srcX: Int?, srcY: Int?, flipped: Boolean, isPromotionScreen: Boolean) {

        val polyBatch = PolygonSpriteBatch()
        polyBatch.begin()
        var coordinate: Coordinate2D? = null
        if (srcX != null && srcY != null) {
            coordinate = gameScreenHexagonal.getPieceCoordinateFromMousePosition(srcX, srcY)
        }
        for (i in 0 until board.rows) {
            for (j in 0 until board.cols) {
                if (board.isInBounds(Coordinate2D(j, i))) {
                    var colour = i % 3

                    if (!isPromotionScreen) {
                        if (srcX != null && srcY != null) {
                            if (coordinate!! == Coordinate2D(j, i)) {
                                colour = 3
                            }
                        }
                    }

                    val x = if (flipped) (board.cols - j - 1) else j
                    val y = if (flipped) (board.rows - i - 1) else i

                    val poly = PolygonSprite(getPolyRegion(offsetx + x * dx, (offsety + y * dy).toFloat(), colour))
                    poly.draw(polyBatch)
                }
            }
        }

        polyBatch.end()
    }

    val rootThree = (3).toFloat().pow((1.0 / 2.0).toFloat())
    val dx = hexagonRadius + hexagonRadius / 2
    val dy = (hexagonRadius * rootThree) / 2
    val offsetx = hexagonRadius
    val offsety = (rootThree * hexagonRadius) / 2.0

    /**
     * Draws the pieces sprites.
     * @param flipped decides if the pieces should be drawn flipped. eg. for blacks turn.
     */
    fun drawPieces(batch: Batch, flipped: Boolean) {
        batch.begin()
        val pieces = board.getPieces()

        for ((p, c) in pieces) {
            val texture = textures.getTextureFromPiece(p, libToFrontEndPlayer[p.player]!!.colour)
            val sprite = Sprite(texture)

            val indx = if (flipped) (board.cols - c.x - 1) else c.x
            val indy = if (flipped) (board.rows - c.y - 1) else c.y

            val pieceWidth = squareWidth * 0.7f

            val x = offsetx + indx * dx - pieceWidth / 2
            val y = offsety + indy * dy - pieceWidth / 2

            sprite.setPosition(x, y.toFloat())

            sprite.setSize(pieceWidth, pieceWidth)
            sprite.draw(batch)
        }

        batch.end()
    }

    /**
     * Draws the possible moves of the selected piece in the form of dots on the valid hexagons
     * the piece can move to.
     * @param flipped decides if the pieces should be drawn flipped. eg. for blacks turn.
     * @param moves list of possible moves the selected piece can make.
     * @param isPromotionScreen does not draw on promotion screen.
     */
    fun drawDots(srcX: Int?, srcY: Int?, isPromotionScreen: Boolean, moves: List<MoveHex>, flipped: Boolean) {
        if (srcX == null || srcY == null || isPromotionScreen) {
            return
        }
        val coordinate = gameScreenHexagonal.getPieceCoordinateFromMousePosition(srcX, srcY)

        var toCoordinates = moves.filter { m ->
            m.displayFrom == coordinate
        }
            .map { m -> m.displayTo }

        if (flipped) {
            toCoordinates = toCoordinates.map { c -> Coordinate2D(board.cols - c!!.x - 1, board.rows - c.y - 1) }
        }

        /* Draw toCoordinates dots for a selected piece. */
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = possibleMoveColour

        for (c in toCoordinates) {
            shapeRenderer.circle(
                offsetx + c!!.x * dx,
                (offsety + c!!.y * dy).toFloat(),
                possibleMoveCircleRadius
            )
        }

        shapeRenderer.end()
    }
}

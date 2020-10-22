package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class MyGdxGame : ApplicationAdapter() {
    lateinit internal var batch: SpriteBatch
    lateinit internal var img: Texture
    lateinit internal var shapeRenderer : ShapeRenderer
    private val squareWidth: Float = 100f
    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    override fun create() {
        batch = SpriteBatch()
        shapeRenderer = ShapeRenderer()
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

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

        for (i in 0 until 8) {
            for (j in 0 until 8) {



                if ((i + j) % 2 == 0) {
                    shapeRenderer.setColor(Color.BLACK)
                }
                else {
                    shapeRenderer.setColor(Color.WHITE)
                }

                if (srcX != null && srcY != null) {
                    if (squareWidth * i <= srcX!! && srcX!! < squareWidth * (i + 1) && squareWidth * j <= srcY!! && srcY!! < squareWidth * (j + 1)) {
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

        shapeRenderer.end()


        batch.begin()

        batch.end()


    }

    override fun dispose() {
        batch.dispose()
    }
}


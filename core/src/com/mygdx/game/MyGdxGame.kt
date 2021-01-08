package com.mygdx.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.TextureAssets
import com.mygdx.game.assets.load
import ktx.app.KtxGame
import ktx.app.KtxScreen
import screens.MenuScreen

/**
 * The front end game class.
 */
class MyGdxGame : KtxGame<KtxScreen>() {

    internal lateinit var batch: SpriteBatch
    internal lateinit var font: BitmapFont
    internal lateinit var shapeRenderer: ShapeRenderer

    val assets by lazy { AssetManager() }

    /**
     * Creates the first screen, initialises batch, font, shaperenderer used to display text and images.
     * Calls TextureAssets to load in textures of the pieces from the assets folder
     */
    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        shapeRenderer = ShapeRenderer()

        TextureAssets.values().forEach { assets.load(it) }
        assets.finishLoading()

        addScreen(MenuScreen(this))
        setScreen<MenuScreen>()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        super.dispose()
        assets.dispose()
    }
}

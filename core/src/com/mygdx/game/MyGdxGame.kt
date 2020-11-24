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

class MyGdxGame : KtxGame<KtxScreen>() {

    internal lateinit var batch: SpriteBatch
//    internal lateinit var img: Texture
    internal lateinit var font: BitmapFont
    internal lateinit var shapeRenderer: ShapeRenderer

    val assets by lazy { AssetManager() }

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        shapeRenderer = ShapeRenderer()

        TextureAssets.values().forEach { assets.load(it) }
        assets.finishLoading()

        addScreen(MenuScreen(this))
        setScreen<MenuScreen>()
//        super.create()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        super.dispose()
        assets.dispose()
    }
}

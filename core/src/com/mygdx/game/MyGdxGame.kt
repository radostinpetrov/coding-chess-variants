package com.mygdx.game

import Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.game.assets.TextureAssets
import com.mygdx.game.assets.Textures
import com.mygdx.game.assets.load
import gameTypes.chess.StandardChess
import ktx.app.KtxGame
import ktx.app.KtxScreen
import players.ComputerPlayer
import screens.GameScreen
import screens.MenuScreen

class MyGdxGame : KtxGame<KtxScreen>() {

    lateinit internal var batch: SpriteBatch
    lateinit internal var img: Texture
    lateinit internal var font: BitmapFont
    lateinit internal var shapeRenderer : ShapeRenderer

    val assets by lazy { AssetManager() }
    var srcX: Int? = null
    var srcY: Int? = null
    var dstX: Int? = null
    var dstY: Int? = null

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        shapeRenderer = ShapeRenderer()


        TextureAssets.values().forEach { assets.load(it) }
        assets.finishLoading()

        addScreen(MenuScreen(this))
        setScreen<MenuScreen>()
        // addScreen(GameScreen(this, Game(chess)))
        // setScreen<GameScreen>()
        super.create()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        super.dispose()
        assets.dispose()
    }
}


package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = 800
        config.width = 800
        // LwjglApplication(MyGdxGame(), config)
    }
}

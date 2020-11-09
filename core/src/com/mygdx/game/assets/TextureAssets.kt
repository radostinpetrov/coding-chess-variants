package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

enum class TextureAssets(val path: String) {
    WhitePawn("core/assets/WhitePawn.png"),
    WhiteRook("core/assets/WhiteRook.png"),
    WhiteKnight("core/assets/WhiteKnight.png"),
    WhiteBishop("core/assets/WhiteBishop.png"),
    WhiteQueen("core/assets/WhiteQueen.png"),
    WhiteKing("core/assets/WhiteKing.png"),
    WhiteMarshal("core/assets/WhiteMarshal.png"),
    WhiteCardinal("core/assets/WhiteCardinal.png"),
    BlackPawn("core/assets/BlackPawn.png"),
    BlackRook("core/assets/BlackRook.png"),
    BlackKnight("core/assets/BlackKnight.png"),
    BlackBishop("core/assets/BlackBishop.png"),
    BlackQueen("core/assets/BlackQueen.png"),
    BlackKing("core/assets/BlackKing.png"),
    BlackMarshal("core/assets/BlackMarshal.png"),
    BlackCardinal("core/assets/BlackCardinal.png"),
    WhiteAdvisor("core/assets/WhiteAdvisor.png"),
    WhiteCannon("core/assets/WhiteCannon.png"),
    WhiteChariot("core/assets/WhiteChariot.png"),
    WhiteElephant("core/assets/WhiteElephant.png"),
    WhiteGeneral("core/assets/WhiteGeneral.png"),
    WhiteHorse("core/assets/WhiteHorse.png"),
    WhiteSoldier("core/assets/WhiteSoldier.png"),
    BlackAdvisor("core/assets/BlackAdvisor.png"),
    BlackCannon("core/assets/BlackCannon.png"),
    BlackChariot("core/assets/BlackChariot.png"),
    BlackElephant("core/assets/BlackElephant.png"),
    BlackGeneral("core/assets/BlackGeneral.png"),
    BlackHorse("core/assets/BlackHorse.png"),
    BlackSoldier("core/assets/BlackSoldier.png"),
}

inline fun AssetManager.load(asset: TextureAssets) = load<Texture>(asset.path)
inline operator fun AssetManager.get(asset: TextureAssets) = getAsset<Texture>(asset.path)

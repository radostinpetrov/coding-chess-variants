package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.getAsset
import ktx.assets.load

enum class TextureAssets(val path: String) {
    WhitePawn("white_pawn.png"),
    WhiteRook("WhiteRook.png"),
    WhiteKnight("WhiteKnight.png"),
    WhiteBishop("WhiteBishop.png"),
    WhiteQueen("WhiteQueen.png"),
    WhiteKing("WhiteKing.png"),
    WhiteMarshal("WhiteMarshal.png"),
    WhiteCardinal("WhiteCardinal.png"),
    BlackPawn("BlackPawn.png"),
    BlackRook("BlackRook.png"),
    BlackKnight("BlackKnight.png"),
    BlackBishop("BlackBishop.png"),
    BlackQueen("BlackQueen.png"),
    BlackKing("BlackKing.png"),
    BlackMarshal("BlackMarshal.png"),
    BlackCardinal("BlackCardinal.png"),
    WhiteAdvisor("WhiteAdvisor.png"),
    WhiteCannon("WhiteCannon.png"),
    WhiteChariot("WhiteChariot.png"),
    WhiteElephant("WhiteElephant.png"),
    WhiteGeneral("WhiteGeneral.png"),
    WhiteHorse("WhiteHorse.png"),
    WhiteSoldier("WhiteSoldier.png"),
    BlackAdvisor("BlackAdvisor.png"),
    BlackCannon("BlackCannon.png"),
    BlackChariot("BlackChariot.png"),
    BlackElephant("BlackElephant.png"),
    BlackGeneral("BlackGeneral.png"),
    BlackHorse("BlackHorse.png"),
    BlackSoldier("BlackSoldier.png"),
}

inline fun AssetManager.load(asset: TextureAssets) = load<Texture>(asset.path)
inline operator fun AssetManager.get(asset: TextureAssets) = getAsset<Texture>(asset.path)

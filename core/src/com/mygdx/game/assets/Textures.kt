package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import pieces.Piece
import pieces.Piece2D

class Textures(assets: AssetManager) {
    val whitePawn = assets[TextureAssets.WhitePawn]
    val whiteRook = assets[TextureAssets.WhiteRook]
    val whiteKnight = assets[TextureAssets.WhiteKnight]
    val whiteBishop = assets[TextureAssets.WhiteBishop]
    val whiteQueen = assets[TextureAssets.WhiteQueen]
    val whiteKing = assets[TextureAssets.WhiteKing]
    val whiteCardinal = assets[TextureAssets.WhiteCardinal]
    val whiteMarshal = assets[TextureAssets.WhiteMarshal]
    val blackPawn = assets[TextureAssets.BlackPawn]
    val blackRook = assets[TextureAssets.BlackRook]
    val blackKnight = assets[TextureAssets.BlackKnight]
    val blackBishop = assets[TextureAssets.BlackBishop]
    val blackQueen = assets[TextureAssets.BlackQueen]
    val blackKing = assets[TextureAssets.BlackKing]
    val blackCardinal = assets[TextureAssets.BlackCardinal]
    val blackMarshal = assets[TextureAssets.BlackMarshal]
    val whiteAdvisor = assets[TextureAssets.WhiteAdvisor]
    val whiteCannon = assets[TextureAssets.WhiteCannon]
    val whiteChariot = assets[TextureAssets.WhiteChariot]
    val whiteElephant = assets[TextureAssets.WhiteElephant]
    val whiteGeneral = assets[TextureAssets.WhiteGeneral]
    val whiteHorse = assets[TextureAssets.WhiteHorse]
    val whiteSoldier = assets[TextureAssets.WhiteSoldier]
    val blackAdvisor = assets[TextureAssets.BlackAdvisor]
    val blackCannon = assets[TextureAssets.BlackCannon]
    val blackChariot = assets[TextureAssets.BlackChariot]
    val blackElephant = assets[TextureAssets.BlackElephant]
    val blackGeneral = assets[TextureAssets.BlackGeneral]
    val blackHorse = assets[TextureAssets.BlackHorse]
    val blackSoldier = assets[TextureAssets.BlackSoldier]

    // instead of symbol, use something else...
    val whites = mapOf(
        "StandardWhitePawn" to whitePawn, "GrandWhitePawn" to whitePawn, "Rook" to whiteRook,
        "Knight" to whiteKnight, "Bishop" to whiteBishop, "Queen" to whiteQueen, "King" to whiteKing,
        "Cardinal" to whiteCardinal, "Marshal" to whiteMarshal, "Advisor" to whiteAdvisor,
        "Cannon" to whiteCannon, "Chariot" to whiteChariot, "Elephant" to whiteElephant,
        "General" to whiteGeneral, "Horse" to whiteHorse, "RedSoldier" to whiteSoldier,
        "XiangqiAdvisor" to whiteAdvisor, "XiangqiCannon" to whiteCannon, "XiangqiChariot" to whiteChariot,
        "XiangqiRedElephant" to whiteElephant, "XiangqiGeneral" to whiteGeneral, "XiangqiHorse" to whiteHorse,
        "XiangqiRedSoldier" to whiteSoldier, "AntiChessWhitePawn" to whitePawn, "AntiChessKing" to whiteKing
    )

    val blacks = mapOf(
        "StandardBlackPawn" to blackPawn, "GrandBlackPawn" to blackPawn, "Rook" to blackRook,
        "Knight" to blackKnight, "Bishop" to blackBishop, "Queen" to blackQueen, "King" to blackKing,
        "Cardinal" to blackCardinal, "Marshal" to blackMarshal, "Advisor" to blackAdvisor,
        "Cannon" to blackCannon, "Chariot" to blackChariot, "Elephant" to blackElephant,
        "General" to blackGeneral, "Horse" to blackHorse, "BlueSoldier" to blackSoldier,
        "XiangqiAdvisor" to blackAdvisor, "XiangqiCannon" to blackCannon, "XiangqiChariot" to blackChariot,
        "XiangqiBlueElephant" to blackElephant, "XiangqiGeneral" to blackGeneral, "XiangqiHorse" to blackHorse,
        "XiangqiBlueSoldier" to blackSoldier, "AntiChessBlackPawn" to blackPawn, "AntiChessKing" to blackKing
    )

    fun getTextureFromPiece(piece: Piece2D, playerColour: Color): Texture? {
        val col = when (playerColour) {
            Color.WHITE -> whites
            Color.BLACK -> blacks
            else -> TODO()
        }
        return col["${piece::class.simpleName}"]
    }
}

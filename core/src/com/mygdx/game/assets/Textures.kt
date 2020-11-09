package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import main.kotlin.pieces.Piece

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

    // instead of symbol, use something else...
    val whites = mapOf(
        "P" to whitePawn, "R" to whiteRook, "N" to whiteKnight,
        "B" to whiteBishop, "Q" to whiteQueen, "K" to whiteKing,
        "C" to whiteCardinal, "M" to whiteMarshal
    )

    val blacks = mapOf(
        "P" to blackPawn, "R" to blackRook, "N" to blackKnight,
        "B" to blackBishop, "Q" to blackQueen, "K" to blackKing,
        "C" to blackCardinal, "M" to blackMarshal
    )

    fun getTextureFromPiece(piece: Piece, playerColour: Color): Texture? {
        val col = when (playerColour) {
            Color.WHITE -> whites
            Color.BLACK -> blacks
            else -> TODO()
        }

        return col[piece.getSymbol()]
    }
}

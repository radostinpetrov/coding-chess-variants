package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import gameTypes.checkers.Checkers
import gameTypes.chess.BalbosGame
import pieces.Piece2D
import pieces.antichess.*
import pieces.chess.*
import pieces.janggi.*
import pieces.xiangqi.*
import java.lang.UnsupportedOperationException
import kotlin.reflect.KClass

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
    val redChecker = assets[TextureAssets.RedChecker]
    val whiteChecker = assets[TextureAssets.WhiteChecker]

    val whites = mapOf<KClass<*>, Texture>(
        WhitePawn::class to whitePawn, StandardWhitePawn::class to whitePawn, Rook::class to whiteRook,
        Knight::class to whiteKnight, Bishop::class to whiteBishop, Queen::class to whiteQueen, King::class to whiteKing,
        GrandWhitePawn::class to whitePawn, Cardinal::class to whiteCardinal, Marshal::class to whiteMarshal, Advisor::class to whiteAdvisor,
        Cannon::class to whiteCannon, Chariot::class to whiteChariot, Elephant::class to whiteElephant,
        General::class to whiteGeneral, Horse::class to whiteHorse, RedSoldier::class to whiteSoldier,
        XiangqiAdvisor::class to whiteAdvisor, XiangqiCannon::class to whiteCannon, XiangqiChariot::class to whiteChariot,
        XiangqiRedElephant::class to whiteElephant, XiangqiGeneral::class to whiteGeneral, XiangqiHorse::class to whiteHorse,
        XiangqiRedSoldier::class to whiteSoldier, AntiChessWhitePawn::class to whitePawn, AntiChessWhitePawn::class to whitePawn, AntiChessKing::class to whiteKing,
        CapablancaWhitePawn::class to whitePawn, BalbosGame.BalboWhitePawn::class to whitePawn, Checkers.WhiteChecker::class to whiteChecker,
        Checkers.CheckerKing::class to whiteKing
    )

    val blacks = mapOf(
        BlackPawn::class to blackPawn, StandardBlackPawn::class to blackPawn, GrandBlackPawn::class to blackPawn, Rook::class to blackRook,
        Knight::class to blackKnight, Bishop::class to blackBishop, Queen::class to blackQueen, King::class to blackKing,
        Cardinal::class to blackCardinal, Marshal::class to blackMarshal, Advisor::class to blackAdvisor,
        Cannon::class to blackCannon, Chariot::class to blackChariot, Elephant::class to blackElephant,
        General::class to blackGeneral, Horse::class to blackHorse, BlueSoldier::class to blackSoldier,
        XiangqiAdvisor::class to blackAdvisor, XiangqiCannon::class to blackCannon, XiangqiChariot::class to blackChariot,
        XiangqiBlueElephant::class to blackElephant, XiangqiGeneral::class to blackGeneral, XiangqiHorse::class to blackHorse,
        XiangqiBlueSoldier::class to blackSoldier, AntiChessBlackPawn::class to blackPawn, AntiChessKing::class to blackKing,
        CapablancaBlackPawn::class to blackPawn, BalbosGame.BalboBlackPawn::class to blackPawn, Checkers.BlackChecker::class to redChecker,
        Checkers.CheckerKing::class to blackKing
    )

    fun getTextureFromPiece(piece: Piece2D, playerColour: Color): Texture? {
        val col = when (playerColour) {
            Color.WHITE -> whites
            Color.BLACK -> blacks
            else -> {
                throw UnsupportedOperationException("Only two players are supported")
            }
        }
        return col[piece::class]
    }
}

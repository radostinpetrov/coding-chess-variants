package com.mygdx.game.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import gameTypes.checkers.Checkers
import gameTypes.chess.BalbosGame
import gameTypes.chess.ChessPlayground
import pieces.Piece2D
import pieces.antichess.*
import pieces.chess.*
import pieces.janggi.*
import pieces.xiangqi.*
import pieces.janggi.Elephant
import tutorial.Alfil
import tutorial.BerlinBlackPawn
import tutorial.BerlinWhitePawn
import java.lang.UnsupportedOperationException
import kotlin.reflect.KClass

/**
 * Contains the textures from the asset manager.
 */
class Textures(assets: AssetManager) {
    /* --- Standard chess --- */
    /* White pieces. */
    val whitePawn = assets[TextureAssets.WhitePawn]
    val whiteRook = assets[TextureAssets.WhiteRook]
    val whiteKnight = assets[TextureAssets.WhiteKnight]
    val whiteBishop = assets[TextureAssets.WhiteBishop]
    val whiteQueen = assets[TextureAssets.WhiteQueen]
    val whiteKing = assets[TextureAssets.WhiteKing]

    /* Black pieces. */
    val blackPawn = assets[TextureAssets.BlackPawn]
    val blackRook = assets[TextureAssets.BlackRook]
    val blackKnight = assets[TextureAssets.BlackKnight]
    val blackBishop = assets[TextureAssets.BlackBishop]
    val blackQueen = assets[TextureAssets.BlackQueen]
    val blackKing = assets[TextureAssets.BlackKing]


    /* --- Grand chess and Capablanca chess --- */
    /* White pieces. */
    val whiteCardinal = assets[TextureAssets.WhiteCardinal]
    val whiteMarshal = assets[TextureAssets.WhiteMarshal]

    /* Black pieces. */
    val blackCardinal = assets[TextureAssets.BlackCardinal]
    val blackMarshal = assets[TextureAssets.BlackMarshal]


    /* --- Xiangqi and Janggi --- */
    /* White pieces. */
    val whiteAdvisor = assets[TextureAssets.WhiteAdvisor]
    val whiteCannon = assets[TextureAssets.WhiteCannon]
    val whiteChariot = assets[TextureAssets.WhiteChariot]
    val whiteElephant = assets[TextureAssets.WhiteElephant]
    val whiteGeneral = assets[TextureAssets.WhiteGeneral]
    val whiteHorse = assets[TextureAssets.WhiteHorse]
    val whiteSoldier = assets[TextureAssets.WhiteSoldier]

    /* Black pieces. */
    val blackAdvisor = assets[TextureAssets.BlackAdvisor]
    val blackCannon = assets[TextureAssets.BlackCannon]
    val blackChariot = assets[TextureAssets.BlackChariot]
    val blackElephant = assets[TextureAssets.BlackElephant]
    val blackGeneral = assets[TextureAssets.BlackGeneral]
    val blackHorse = assets[TextureAssets.BlackHorse]
    val blackSoldier = assets[TextureAssets.BlackSoldier]

    /* Checkers. */
    val redChecker = assets[TextureAssets.RedChecker]
    val whiteChecker = assets[TextureAssets.WhiteChecker]

    /* Tutorial. */
    val blackAlfil = assets[TextureAssets.BlackAlfil]
    val whiteAlfil = assets[TextureAssets.WhiteAlfil]

    /* Playground. */
    val playgroundPiece = assets[TextureAssets.PlaygroundPiece]

    /* Contains the mappings to the textures for white pieces. */
    val whites = mapOf<KClass<*>, Texture>(
        /* Standard chess. */
        WhitePawn::class to whitePawn, StandardWhitePawn::class to whitePawn, Rook::class to whiteRook,
        Knight::class to whiteKnight, Bishop::class to whiteBishop, Queen::class to whiteQueen, King::class to whiteKing,

        /* Capablanca and Grand chess. */
        CapablancaWhitePawn::class to whitePawn, GrandWhitePawn::class to whitePawn,
        Cardinal::class to whiteCardinal, Marshal::class to whiteMarshal,

        /* Xiangqi and Janggi. */
        Advisor::class to whiteAdvisor, Cannon::class to whiteCannon, Chariot::class to whiteChariot,
        Elephant::class to whiteElephant, General::class to whiteGeneral, Horse::class to whiteHorse,
        RedSoldier::class to whiteSoldier, XiangqiAdvisor::class to whiteAdvisor, XiangqiCannon::class to whiteCannon,
        XiangqiChariot::class to whiteChariot, XiangqiRedElephant::class to whiteElephant, XiangqiGeneral::class to whiteGeneral,
        XiangqiHorse::class to whiteHorse, XiangqiRedSoldier::class to whiteSoldier,

        /* Antichess. */
        AntiChessWhitePawn::class to whitePawn, AntiChessWhitePawn::class to whitePawn, AntiChessKing::class to whiteKing,

        /* Balbo's game. */
        BalbosGame.BalboWhitePawn::class to whitePawn,

        /* Checkers. */
        Checkers.WhiteChecker::class to whiteChecker, Checkers.CheckerKing::class to whiteKing,

        /* Tutorial.*/
        Alfil::class to whiteAlfil,
        BerlinWhitePawn::class to whitePawn,

        /* Playground. */
        ChessPlayground.PlaygroundPiece::class to playgroundPiece
        )

    /* Contains the mappings to the textures for black pieces. */
    val blacks = mapOf(
        /* Standard chess. */
        BlackPawn::class to blackPawn, StandardBlackPawn::class to blackPawn, Rook::class to blackRook,
        Knight::class to blackKnight, Bishop::class to blackBishop, Queen::class to blackQueen, King::class to blackKing,

        /* Capablanca and Grand Chess. */
        CapablancaBlackPawn::class to blackPawn, GrandBlackPawn::class to blackPawn,
        Cardinal::class to blackCardinal, Marshal::class to blackMarshal,

        /* Xiangqi and Janggi. */
        Advisor::class to blackAdvisor, Cannon::class to blackCannon, Chariot::class to blackChariot,
        Elephant::class to blackElephant, General::class to blackGeneral, Horse::class to blackHorse,
        BlueSoldier::class to blackSoldier, XiangqiAdvisor::class to blackAdvisor, XiangqiCannon::class to blackCannon,
        XiangqiChariot::class to blackChariot, XiangqiBlueElephant::class to blackElephant, XiangqiGeneral::class to blackGeneral,
        XiangqiHorse::class to blackHorse, XiangqiBlueSoldier::class to blackSoldier,

        /* Antichess. */
        AntiChessBlackPawn::class to blackPawn, AntiChessKing::class to blackKing,

        /* Balbo's Game. */
        BalbosGame.BalboBlackPawn::class to blackPawn,

        /* Checkers. */
        Checkers.BlackChecker::class to redChecker, Checkers.CheckerKing::class to blackKing,

        /* Tutorial. */
        Alfil::class to blackAlfil,
        BerlinBlackPawn::class to blackPawn,

        /* Playground. */
        ChessPlayground.PlaygroundPiece::class to playgroundPiece
    )

    /* This method returns the texture associated to a piece, and null if it does not exist. */
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

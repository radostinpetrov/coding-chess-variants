package rules

import gameTypes.chess.StandardChess

/**
 * Castling in standard chess.
 *
 * Moves the king two squares towards a rook on the player's first rank,
 * then moving the rook to the square that the king crossed.
 *
 * Can only occur provided all of the following conditions hold:
 *  1. The castling must be kingside or queenside
 *  2. Neither the king nor the chosen rook has previously moved.
 *  3. There are no pieces between the king and the chosen rook.
 *  4. The king is not currently in check.
 *  5. The king does not pass through a square that is attacked by an enemy piece.
 *  6. The king does not end up in check. (True of any legal move.)
 */
class StandardCastling(
    p1CanCastleLeft: Boolean = true, p1CanCastleRight: Boolean = true,
    p2CanCastleLeft: Boolean = true, p2CanCastleRight: Boolean = true)
    : AbstractCastling<StandardChess>(3, p1CanCastleLeft, p1CanCastleRight, p2CanCastleLeft, p2CanCastleRight)

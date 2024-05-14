package game;

import board.Board;
import board.Position;
import pieces.*;

/**
 * The Player class represents a player in the chess game.
 */
public class Player {
    private final PieceColor color;
    private final Board board;

    /**
     * Constructs a new Player object with a specified color and chess board.
     *
     * @param color The color of the player ("White" or "Black").
     * @param board The chess board.
     */
    public Player(PieceColor color, Board board) {
        this.color = color;
        this.board = board;
    }

    /**
     * Gets the color of the player.
     * @return The color of the player.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Moves a piece from one position to another if the move is valid and the piece belongs to the player.
     *
     * @param from The starting position of the piece.
     * @param to   The destination position of the piece.
     * @return true if the move is successfully executed, false otherwise.
     */
    public boolean makeMove(Position from, Position to) {
        Piece piece = board.getPiece(from);
        if (piece != null && piece.getColor() == this.color) {
            return board.movePiece(from, to);
        }
        return false;
    }
}
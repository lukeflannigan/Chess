package pieces;

import board.Position;
import board.Board;

import java.util.List;

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * The Piece class is an abstract class representing a chess piece.
 * This class defines the basic properties and functionalities that all chess pieces share.
 */
public abstract class Piece {

    /** The color of the piece. */
    protected final PieceColor color;

    /** The current position of the piece on the board. */
    protected Position position;

    /**
     * Constructs a new Piece with the specified color and position.
     *
     * @param color    The color of the piece, cannot be null.
     * @param position The initial position of the piece on the board.
     * @throws IllegalArgumentException if the color is null.
     */
    public Piece(PieceColor color, Position position) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        this.color = color;
        this.position = position;
    }

    /**
     * Gets the color of this piece.
     *
     * @return The color of the piece.
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Gets the current position of this piece on the board.
     *
     * @return The current position of the piece.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of this piece on the board.
     *
     * @param position The new position of the piece.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Loads the image icon for this piece based on its name and color.
     * If the image is not found, prints an error and returns null.
     *
     * @param pieceName The name of the piece (e.g., "pawn", "king").
     * @return The ImageIcon for the piece, or null if the image is not found.
     */
    protected ImageIcon loadImageIcon(String pieceName) {
        String imageName = this.color.toString().toLowerCase() + "_" + pieceName + ".png";
        URL url = getClass().getClassLoader().getResource(imageName);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("Image not found: " + imageName);
            return null;
        }
    }

    /**
     * Gets the ImageIcon representing this piece.
     * Implemented by subclasses to get correct image.
     *
     * @return The ImageIcon representing this piece.
     */
    public abstract ImageIcon getImageIcon();

    /**
     * Calculates and returns a list of valid moves for this piece from its current position on the given board.
     * This method is implemented by each subclass with unique movement rules.
     *
     * @param board The board on which the piece is placed.
     * @return A list of valid positions that this piece can move to.
     */
    public abstract List<Position> getValidMoves(Board board);
}

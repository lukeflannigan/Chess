package pieces;

import board.Board;
import board.Position;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a knight chess piece, extending the Piece class.
 * Handles the specific movement rules and image representation for a knight.
 */
public class Knight extends Piece {

    /**
     * Constructs a new Knight with the specified color and position.
     *
     * @param color    The color of the knight.
     * @param position The initial position of the knight.
     */
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another Knight.
     *
     * @param other The Knight to copy.
     */
    public Knight(Knight other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this knight.
     *
     * @return The ImageIcon for this knight.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("knight");
    }

    /**
     * Calculates and returns a list of valid moves for this knight from its current position on the given board.
     *
     * @param board The board on which this knight is placed.
     * @return A list of valid positions that this knight can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] offsets = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

        for (int[] offset : offsets) {
            int newRow = this.position.getRow() + offset[0];
            int newColumn = this.position.getColumn() + offset[1];
            if (isValidPosition(newRow, newColumn, board)) {
                validMoves.add(new Position(newRow, newColumn));
            }
        }
        return validMoves;
    }

    /**
     * Checks if the specified position is valid for the knight to move to.
     * A position is valid if it is within the bounds of the board and either empty or occupied by an opponent's piece.
     *
     * @param row   The row index of the position to check.
     * @param col   The column index of the position to check.
     * @param board The board to check the position against.
     * @return true if the position is valid for the knight false otherwise.
     */
    private boolean isValidPosition(int row, int col, Board board) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false;
        }

        Piece pieceAtPosition = board.getPiece(new Position(row, col));
        if (pieceAtPosition == null || pieceAtPosition.getColor() != this.color) {
            return true;
        }
        return false;
    }
}

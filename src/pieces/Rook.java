package pieces;

import board.Board;
import board.Position;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rook chess piece, extending the Piece class.
 * This class handles the specific movement rules and image representation for a rook.
 */
public class Rook extends Piece {

    /**
     * Constructs a new Rook with the specified color and position.
     *
     * @param color    The color of the rook.
     * @param position The initial position of the rook.
     */
    public Rook(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another Rook.
     *
     * @param other The Rook to copy.
     */
    public Rook(Rook other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this rook.
     *
     * @return The ImageIcon for this rook.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("rook");
    }

    /**
     * Calculates and returns a list of valid moves for this rook from its current position on the given board.
     * This includes all vertical and horizontal moves until another piece is encountered or the edge of the board.
     *
     * @param board The board on which this rook is placed.
     * @return A list of valid positions that this rook can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        addLineMoves(validMoves, board, 1, 0);  // Moving vertically down
        addLineMoves(validMoves, board, -1, 0); // Moving vertically up
        addLineMoves(validMoves, board, 0, 1);  // Moving horizontally right
        addLineMoves(validMoves, board, 0, -1); // Moving horizontally left
        return validMoves;
    }

    /**
     * Adds linear moves for this rook to the list of valid moves in a specified direction.
     * Continues adding moves along the line until it encounters another piece or the edge of the board.
     *
     * @param moves        The list of valid moves.
     * @param board        The board to check for valid moves.
     * @param rowIncrement The row increment for moving linearly (can be -1, 0, or 1).
     * @param colIncrement The column increment for moving linearly (can be -1, 0, or 1).
     */
    private void addLineMoves(List<Position> moves, Board board, int rowIncrement, int colIncrement) {
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();
        while (isWithinBoard(currentRow + rowIncrement, currentCol + colIncrement)) {
            currentRow += rowIncrement;
            currentCol += colIncrement;
            Position newPosition = new Position(currentRow, currentCol);
            Piece encounteredPiece = board.getPiece(newPosition);
            if (encounteredPiece == null) {
                moves.add(newPosition);
            } else {
                if (encounteredPiece.getColor() != this.getColor()) {
                    moves.add(newPosition);
                }
                break;
            }
        }
    }

    /**
     * Checks if the specified row and column are within the bounds of the chess board.
     *
     * @param row The row index of the position to check.
     * @param col The column index of the position to check.
     * @return true if the row and column are within the board, false otherwise.
     */
    private boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}


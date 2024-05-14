package pieces;

import board.Board;
import board.Position;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bishop chess piece, extending the Piece class.
 * Handles the specific movement rules and image representation for a bishop.
 */
public class Bishop extends Piece {

    /**
     * Constructs a new Bishop with the specified color and position.
     *
     * @param color    The color of the bishop.
     * @param position The initial position of the bishop.
     */
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another Bishop.
     *
     * @param other The Bishop to copy.
     */
    public Bishop(Bishop other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this bishop.
     *
     * @return The ImageIcon for this bishop.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("bishop");
    }

    /**
     * Calculates and returns a list of valid moves for this bishop from its current position on the given board.
     *
     * @param board The board on which this bishop is placed.
     * @return A list of valid positions that this bishop can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        addDiagonalMoves(validMoves, board, 1, 1);   // Moving diagonally up-right
        addDiagonalMoves(validMoves, board, 1, -1);  // Moving diagonally up-left
        addDiagonalMoves(validMoves, board, -1, 1);  // Moving diagonally down-right
        addDiagonalMoves(validMoves, board, -1, -1); // Moving diagonally down-left
        return validMoves;
    }

    /**
     * Adds diagonal moves for this bishop to the list of valid moves in a specified direction.
     * Continues adding moves along the diagonal until it encounters another piece or the edge of the board.
     *
     * @param moves        The list of valid moves.
     * @param board        The board to check for valid moves.
     * @param rowIncrement The row increment for moving diagonally (can be -1 or 1).
     * @param colIncrement The column increment for moving diagonally (can be -1 or 1).
     */
    private void addDiagonalMoves(List<Position> moves, Board board, int rowIncrement, int colIncrement) {
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


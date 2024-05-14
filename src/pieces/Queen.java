package pieces;

import board.Board;
import board.Position;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a queen chess piece, extending the Piece class.
 * This class handles the specific movement rules and image representation for a queen.
 */
public class Queen extends Piece {

    /**
     * Constructs a new Queen with the specified color and position.
     *
     * @param color    The color of the queen.
     * @param position The initial position of the queen.
     */
    public Queen(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another Queen.
     *
     * @param other The Queen to copy.
     */
    public Queen(Queen other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this queen.
     *
     * @return The ImageIcon for this queen.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("queen");
    }

    /**
     * Calculates and returns a list of valid moves for this queen from its current position on the given board.
     * This includes all vertical, horizontal, and diagonal moves until another piece is encountered or the edge of the board.
     *
     * @param board The board on which this queen is placed.
     * @return A list of valid positions that this queen can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        // Horizontal and Vertical Moves (like Rook)
        addLineMoves(validMoves, board, 1, 0);  // Moving vertically down
        addLineMoves(validMoves, board, -1, 0); // Moving vertically up
        addLineMoves(validMoves, board, 0, 1);  // Moving horizontally right
        addLineMoves(validMoves, board, 0, -1); // Moving horizontally left

        // Diagonal Moves (like Bishop)
        addDiagonalMoves(validMoves, board, 1, 1);   // Moving diagonally up-right
        addDiagonalMoves(validMoves, board, 1, -1);  // Moving diagonally up-left
        addDiagonalMoves(validMoves, board, -1, 1);  // Moving diagonally down-right
        addDiagonalMoves(validMoves, board, -1, -1); // Moving diagonally down-left

        return validMoves;
    }

    /**
     * Adds linear moves for this queen to the list of valid moves in a specified direction.
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
                break; // Stop moving in this direction
            }
        }
    }

    /**
     * Adds diagonal moves for this queen to the list of valid moves in a specified direction.
     * Continues adding moves along the diagonal until it encounters another piece or the edge of the board.
     *
     * @param moves        The list of valid moves.
     * @param board        The board to check for valid moves.
     * @param rowIncrement The row increment for moving diagonally (can be -1, 0, or 1).
     * @param colIncrement The column increment for moving diagonally (can be -1, 0, or 1).
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

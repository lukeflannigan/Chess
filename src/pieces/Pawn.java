package pieces;

import board.Board;
import board.Position;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * Represents a pawn chess piece, extending the abstract Piece class.
 * This class handles the specific movement rules and image representation for a pawn.
 */
public class Pawn extends Piece {

    /**
     * Constructs a new Pawn with the specified color and position.
     *
     * @param color    The color of the pawn.
     * @param position The initial position of the pawn.
     */
    public Pawn(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another existing Pawn.
     *
     * @param other The Pawn to copy.
     */
    public Pawn(Pawn other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this pawn.
     *
     * @return The ImageIcon for this pawn.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("pawn");
    }

    /**
     * Calculates and returns a list of valid moves for this pawn from its current position on the given board.
     *
     * @param board The board on which this pawn is placed.
     * @return A list of valid positions that this pawn can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int direction;

        if (getColor() == PieceColor.WHITE) {
            direction = -1;
        } else {
            direction = 1;
        }

        // Forward movement
        addForwardMoves(validMoves, board, direction);

        // Diagonal captures
        addDiagonalCaptures(validMoves, board, direction);

        return validMoves;
    }

    /**
     * Adds forward moves for this pawn to the list of valid moves.
     *
     * @param moves     The list of valid moves.
     * @param board     The board to check for valid moves.
     * @param direction The direction of movement (1 for Black, -1 for White).
     */
    private void addForwardMoves(List<Position> moves, Board board, int direction) {
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();
        int newRow = currentRow + direction;

        if (isWithinBoard(newRow, currentCol) && board.getPiece(new Position(newRow, currentCol)) == null) {
            moves.add(new Position(newRow, currentCol));

            if (isPawnOnStartRow(currentRow) && board.getPiece(new Position(newRow + direction, currentCol)) == null) {
                moves.add(new Position(newRow + direction, currentCol));
            }
        }
    }

    /**
     * Adds diagonal capture moves for this pawn to the list of valid moves.
     *
     * @param moves     The list of valid moves.
     * @param board     The board to check for valid moves.
     * @param direction The direction of movement (1 for Black, -1 for White).
     */
    private void addDiagonalCaptures(List<Position> moves, Board board, int direction) {
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();

        // Left and right diagonal capture
        int[] cols = {currentCol - 1, currentCol + 1};
        for (int col : cols) {
            if (isWithinBoard(currentRow + direction, col)) {
                Position newPosition = new Position(currentRow + direction, col);
                Piece piece = board.getPiece(newPosition);
                if (piece != null && piece.getColor() != this.getColor()) {
                    moves.add(newPosition);
                }
            }
        }
    }

    /**
     * Checks if the pawn is on its starting row.
     *
     * @param currentRow The current row of the pawn.
     * @return true if the pawn is on its starting row, false otherwise.
     */
    private boolean isPawnOnStartRow(int currentRow) {
        PieceColor color = getColor();
        return (color == PieceColor.WHITE && currentRow == 6) || (color == PieceColor.BLACK && currentRow == 1);
    }

    /**
     * Checks if the specified row and column are within the bounds of the chess board.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return true if the row and column are within the board, false otherwise.
     */
    private boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

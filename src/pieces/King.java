package pieces;

import board.Board;
import board.Position;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a king chess piece, extending the Piece class.
 * This class handles the specific movement rules and image representation for a king.
 */
public class King extends Piece {

    /**
     * Constructs a new King with the specified color and position.
     *
     * @param color    The color of the king.
     * @param position The initial position of the king.
     */
    public King(PieceColor color, Position position) {
        super(color, position);
    }

    /**
     * Constructs a copy of another King.
     *
     * @param other The King to copy.
     */
    public King(King other) {
        super(other.getColor(), other.getPosition());
    }

    /**
     * Gets the image icon representing this king.
     *
     * @return The ImageIcon for this king.
     */
    @Override
    public ImageIcon getImageIcon() {
        return loadImageIcon("king");
    }

    /**
     * Calculates and returns a list of valid moves for this king from its current position on the given board.
     * This includes all one-square movements in any direction that are not under attack.
     *
     * @param board The board on which this king is placed.
     * @return A list of valid positions that this king can move to.
     */
    @Override
    public List<Position> getValidMoves(Board board) {
        List<Position> validMoves = new ArrayList<>();
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

        PieceColor opponentColor;
        if (getColor() == PieceColor.WHITE) {
            opponentColor = PieceColor.BLACK;
        } else {
            opponentColor = PieceColor.WHITE;
        }

        for (int[] dir : directions) {
            int newRow = getPosition().getRow() + dir[0];
            int newCol = getPosition().getColumn() + dir[1];
            if (isWithinBoard(newRow, newCol)) {
                Position newPosition = new Position(newRow, newCol);
                Piece pieceAtNewPosition = board.getPiece(newPosition);
                if (pieceAtNewPosition == null || pieceAtNewPosition.getColor() != this.getColor()) {
                    if (!board.isPositionUnderAttack(newPosition, opponentColor)) {
                        validMoves.add(newPosition);
                    }
                }
            }
        }
        return validMoves;
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

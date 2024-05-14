package board;

/**
 * Represents a position on a chessboard using row and column indices.
 */
public class Position {

    /** The row index of the position. */
    private final int row;

    /** The column index of the position. */
    private final int column;

    /**
     * Constructs a new Position object with specified row and column indices.
     *
     * @param row    The row index of the position. Must be between 0 and 7 (inclusive).
     * @param column The column index of the position. Must be between 0 and 7 (inclusive).
     * @throws IllegalArgumentException if the row or column indices are not within the valid range.
     */
    public Position(int row, int column) {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new IllegalArgumentException("Row and column must be between 0 and 7.");
        }
        this.row = row;
        this.column = column;
    }

    /**
     * Gets the row index of this position.
     *
     * @return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index of this position.
     *
     * @return The column index.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Compares this position with another position for equality.
     * Two positions are considered equal if they have the same row and column indices.
     *
     * @param obj The object (position) to be compared with this position.
     * @return true if the given object represents an equivalent position.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position otherPosition = (Position) obj;
        return (this.row == otherPosition.row && this.column == otherPosition.column);
    }
}

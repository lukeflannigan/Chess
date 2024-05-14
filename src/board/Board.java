package board;

import pieces.*;
import java.util.List;

/**
 * Manages the chessboard for a chess game, handling piece placement, movement, and game state checks.
 * Utilizes a two-dimensional array of Piece objects to represent the board.
 */
public class Board {

    /**
     * The two-dimensional array representing the chess pieces on the board.
     * Each cell of the array can hold a Piece object, representing a chess piece, or null if the cell is empty.
     */
    private final Piece[][] board;

    /**
     * Constructs a new Board and initializes it with the starting positions of all the chess pieces.
     */
    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    /**
     * Creates a copy of an existing Board instance.
     * This constructor is used for creating a deep copy of a board, allowing for move simulations and state evaluations
     * without altering the original board state.
     *
     * @param other The Board instance to copy from.
     */
    public Board(Board other) {
        this.board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece originalPiece = other.board[i][j];
                if (originalPiece != null) {
                    this.board[i][j] = copyPiece(originalPiece);
                } else {
                    this.board[i][j] = null;
                }
            }
        }
    }

    /**
     * Sets up the initial setup of the chess board with pieces.
     */
    private void setupBoard() {
        // Set black pieces
        board[0][0] = new Rook(PieceColor.BLACK, new Position(0, 0));
        board[0][1] = new Knight(PieceColor.BLACK, new Position(0, 1));
        board[0][2] = new Bishop(PieceColor.BLACK, new Position(0, 2));
        board[0][3] = new Queen(PieceColor.BLACK, new Position(0, 3));
        board[0][4] = new King(PieceColor.BLACK, new Position(0, 4));
        board[0][5] = new Bishop(PieceColor.BLACK, new Position(0, 5));
        board[0][6] = new Knight(PieceColor.BLACK, new Position(0, 6));
        board[0][7] = new Rook(PieceColor.BLACK, new Position(0, 7));

        // Set white pieces
        board[7][0] = new Rook(PieceColor.WHITE, new Position(7, 0));
        board[7][1] = new Knight(PieceColor.WHITE, new Position(7, 1));
        board[7][2] = new Bishop(PieceColor.WHITE, new Position(7, 2));
        board[7][3] = new Queen(PieceColor.WHITE, new Position(7, 3));
        board[7][4] = new King(PieceColor.WHITE, new Position(7, 4));
        board[7][5] = new Bishop(PieceColor.WHITE, new Position(7, 5));
        board[7][6] = new Knight(PieceColor.WHITE, new Position(7, 6));
        board[7][7] = new Rook(PieceColor.WHITE, new Position(7, 7));

        // Set pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(PieceColor.BLACK, new Position(1, i));
            board[6][i] = new Pawn(PieceColor.WHITE, new Position(6, i));
        }
    }

    /**
     * Gets the chess piece at a specified position on the board.
     *
     * @param position The position on the board.
     * @return The chess piece at the specified position.
     */
    public Piece getPiece(Position position) {
        return board[position.getRow()][position.getColumn()];
    }

    /**
     * Places a chess piece at a specified position on the board.
     *
     * @param position The position on the board where the piece is to be placed.
     * @param piece    The chess piece to be placed.
     */
    public void setPiece(Position position, Piece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }


    /**
     * Attempts to move a chess piece from one position to another.
     * Validates the move based on the piece's allowed movements and checks if the move places the moving side's king in check.
     *
     * @param from The starting position of the piece.
     * @param to   The destination position of the piece.
     * @return true if the move is valid and successful, false otherwise.
     */
    public boolean movePiece(Position from, Position to) {
        Piece pieceToMove = getPiece(from);
        if (pieceToMove != null) {
            List<Position> validMoves = pieceToMove.getValidMoves(this);

            for (Position validMove : validMoves) {
                if (validMove.equals(to)) {
                    // Create a board state to alter without affecting real board
                    // Might want to optimize this later so it doesnt have to create a full on board all the time
                    Board tempBoard = new Board(this);
                    tempBoard.setPiece(to, tempBoard.getPiece(from));
                    tempBoard.setPiece(from, null);

                    // Make sure move didnt put king in check which is not allowed in chess.
                    if (!tempBoard.isKingInCheck(pieceToMove.getColor())) {
                        setPiece(to, pieceToMove);
                        setPiece(from, null);
                        pieceToMove.setPosition(to);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the king of the specified color is in check.
     * This method finds the king's position and then determines if it is under attack by any opponent's pieces.
     *
     * @param color The color of the king to check (WHITE or BLACK).
     * @return true if the king is in check, false otherwise.
     */
    public boolean isKingInCheck(PieceColor color) {
        Position kingPosition = findKingPosition(color);

        PieceColor opponentColor;
        if (color == PieceColor.WHITE) {
            opponentColor = PieceColor.BLACK;
        } else {
            opponentColor = PieceColor.WHITE;
        }

        return isPositionUnderAttack(kingPosition, opponentColor);

    }

    /**
     * Finds the position of the king of a specified color on the chessboard.
     * Iterates through the board to locate the king piece matching the given color.
     *
     * @param color The color of the king to find (WHITE or BLACK).
     * @return The position of the king on the board or null if the king is not found (does not make sense).
     */
    private Position findKingPosition(PieceColor color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getPiece(new Position(i, j));
                if (piece instanceof King && piece.getColor() == color) {
                    return piece.getPosition();
                }
            }
        }
        return null; // should never happen
    }

    /**
     * Determines if a given position on the board is under attack by pieces of a specified color.
     *
     * @param position      The position to check for threats.
     * @param opponentColor The color of the opponent's pieces.
     * @return true if the position is under attack, false otherwise.
     */
    public boolean isPositionUnderAttack(Position position, PieceColor opponentColor) {
        return isAttackedByLinePieces(position, opponentColor) ||
                isAttackedByKnights(position, opponentColor) ||
                isAttackedByPawns(position, opponentColor) ||
                isAttackedByKing(position, opponentColor);
    }

    /**
     * Checks if a position is under threat from line-moving pieces (rooks, bishops, and queens) of the opponent.
     *
     * @param position      The position to evaluate for threats.
     * @param opponentColor The color of the opponent's pieces.
     * @return true if the position is threatened by a line-moving piece, false otherwise.
     */
    private boolean isAttackedByLinePieces(Position position, PieceColor opponentColor) {
        return checkLine(position, opponentColor, 1, 0) ||  // Horizontal right
                checkLine(position, opponentColor, -1, 0) || // Horizontal left
                checkLine(position, opponentColor, 0, 1) ||  // Vertical down
                checkLine(position, opponentColor, 0, -1) || // Vertical up
                checkLine(position, opponentColor, 1, 1) ||  // Diagonal down-right
                checkLine(position, opponentColor, 1, -1) || // Diagonal down-left
                checkLine(position, opponentColor, -1, 1) || // Diagonal up-right
                checkLine(position, opponentColor, -1, -1);  // Diagonal up-left
    }

    /**
     * Checks a line (straight or diagonal) from a starting position to see if it is attacked by line-moving pieces
     * of a specified color. Method iterates over board in direction specified (row, col) until it finds a piece or
     * reaches end of the board.
     *
     * @param start         The starting position to check from.
     * @param opponentColor The color of the opponent's pieces to check for.
     * @param rowIncrement  The row increment for each step (can be -1, 0, or 1).
     * @param colIncrement  The column increment for each step (can be -1, 0, or 1).
     * @return true if the line is attacked by an opponent's line-moving piece, false otherwise.
     */
    private boolean checkLine(Position start, PieceColor opponentColor, int rowIncrement, int colIncrement) {
        int currentRow = start.getRow();
        int currentCol = start.getColumn();

        while (true) {
            currentRow += rowIncrement;
            currentCol += colIncrement;

            if (!isWithinBoard(currentRow, currentCol)) {
                break;
            }

            Piece piece = getPiece(new Position(currentRow, currentCol));
            if (piece != null) {
                if (piece.getColor() == opponentColor &&
                        (piece instanceof Rook && (rowIncrement == 0 || colIncrement == 0) ||
                                piece instanceof Bishop && (rowIncrement != 0 && colIncrement != 0) ||
                                piece instanceof Queen)) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    /**
     * Checks if a given position is under attack from knights of the specified color.
     *
     * @param position      The position to check.
     * @param opponentColor The color of the opponent's knights.
     * @return true if the position is under attack by a knight, false otherwise.
     */
    private boolean isAttackedByKnights(Position position, PieceColor opponentColor) {
        // Knight move patterns (L-shapes)
        int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int[] move : knightMoves) {
            int newRow = position.getRow() + move[0];
            int newCol = position.getColumn() + move[1];
            if (isWithinBoard(newRow, newCol)) {
                Piece piece = getPiece(new Position(newRow, newCol));
                if (piece instanceof Knight && piece.getColor() == opponentColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a given position is threatened by opponent pawns.
     * This method determines the squares diagonally adjacent to the given position where an attacking pawn could be,
     * based on the pawn's color and movement direction.
     *
     * @param position      The position to be checked for threats from pawns.
     * @param opponentColor The color of the opponent's pawns.
     * @return true if the position is under attack by an opponent's pawn, false otherwise.
     */
    private boolean isAttackedByPawns(Position position, PieceColor opponentColor) {
        // Determine the direction of pawn attacks based on their color
        int pawnRowOffset; // Opposite to pawn movement direction
        if (opponentColor == PieceColor.WHITE) {
            pawnRowOffset = 1;
        } else {
            pawnRowOffset = -1;
        }

        // Check the two diagonal squares where an opponent pawn could be
        int[] cols = {position.getColumn() - 1, position.getColumn() + 1};
        int pawnRow = position.getRow() + pawnRowOffset;

        for (int col : cols) {
            if (isWithinBoard(pawnRow, col)) {
                Position potentialPawnPosition = new Position(pawnRow, col);
                Piece piece = getPiece(potentialPawnPosition);
                if (piece != null && piece instanceof Pawn && piece.getColor() == opponentColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines if a given position is under attack by an opposing king.
     * This method checks the squares immediately surrounding the given position to see if an opposing king occupies any of them.
     *
     * @param position      The position to check for threats from a king.
     * @param opponentColor The color of the opponent's king.
     * @return true if the position is adjacent to an opposing king, false otherwise.
     */
    private boolean isAttackedByKing(Position position, PieceColor opponentColor) {
        // Check surrounding squares for an opposing king
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (row == 0 && col == 0) continue; // Skip the position itself
                int newRow = position.getRow() + row;
                int newCol = position.getColumn() + col;
                if (isWithinBoard(newRow, newCol)) {
                    Piece piece = getPiece(new Position(newRow, newCol));
                    if (piece instanceof King && piece.getColor() == opponentColor) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the specified row and column indices are within the boundaries of the chessboard.
     * The chessboard is assumed to be 8x8, so valid row and column indices range from 0 to 7.
     *
     * @param row The row index to check.
     * @param col The column index to check.
     * @return true if both row and column are within the range of 0 to 7 (inclusive), false otherwise.
     */
    private boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }


    /**
     * Creates a deep copy of a given chess piece.
     * This method is used to duplicate a piece when simulating moves or creating a temporary board state.
     *
     * @param original The piece to be copied.
     * @return A new instance of the same type as the original piece, with the same state, or null if the original is null.
     */
    private Piece copyPiece(Piece original) {
        if (original instanceof King) {
            return new King((King) original);
        } else if (original instanceof Queen) {
            return new Queen((Queen) original);
        } else if (original instanceof Rook) {
            return new Rook((Rook) original);
        } else if (original instanceof Bishop) {
            return new Bishop((Bishop) original);
        } else if (original instanceof Knight) {
            return new Knight((Knight) original);
        } else if (original instanceof Pawn) {
            return new Pawn((Pawn) original);
        }
        return null;
    }


    /**
     * Determines if the player of a specified color is in checkmate.
     * Checkmate occurs when the player's king is in check and there are no legal moves available to escape check.
     * This method first checks if the king is in check, and then tests all possible moves for all pieces of the given color
     * to see if any move can get the king out of check.
     *
     * @param color The color of the player to check for checkmate (WHITE or BLACK).
     * @return true if the player is in checkmate, false otherwise.
     */
    public boolean isCheckmate(PieceColor color) {
        if (!isKingInCheck(color)) {
            return false; // Not in check, so can't be checkmate
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getPiece(new Position(i, j));

                if (piece != null && piece.getColor() == color) {
                    for (Position move : piece.getValidMoves(this)) {
                        Board tempBoard = new Board(this);
                        Piece clonedPiece = tempBoard.getPiece(new Position(i, j)); // Get the corresponding piece on the tempBoard

                        // Simulate the move on the tempBoard
                        tempBoard.setPiece(move, clonedPiece);
                        tempBoard.setPiece(clonedPiece.getPosition(), null);
                        clonedPiece.setPosition(move); // Update the cloned piece's position


                        // Check if the move gets the king out of check
                        if (!tempBoard.isKingInCheck(color)) {
                            return false; // Found a move that gets the king out of check
                        }
                    }

                }
            }
        }
        return true; // No moves available to get out of check, hence checkmate
    }
}



package gui;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.sound.sampled.*;

import board.Board;
import board.Position;
import game.Game;
import game.Player;
import pieces.*;

import java.util.Map;
import java.util.HashMap;



/**
 * Represents the graphical user interface for a chess game.
 * Manages the display and interaction with the chessboard and game state.
 */
public class ChessGUI {
    // Constants for square color configuration
    private static final Color LIGHT_COLOR = new Color(222, 217, 195);
    private static final Color DARK_COLOR = new Color(140, 100, 70);
    private static final Color CHECK_COLOR = new Color(184, 67, 67);
    private static final Color SELECTED_COLOR = new Color(184, 134, 11);
    private Game game;
    private Board board;
    private Player currentTurn;
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    private JButton selectedButton = null;
    private Map<JButton, Position> buttonPositionMap = new HashMap<>();



    /**
     * Initializes the ChessGUI with the specified game.
     *
     * @param game The Game instance to be used in this GUI.
     */
    public ChessGUI(Game game) {
        this.game = game;
        this.board = game.getBoard();
        this.currentTurn = game.getCurrentTurn();
        initializeGUI();
    }

    /**
     * Initializes and configures the GUI components of the chess game.
     */
    private void initializeGUI() {
        frame = new JFrame("Chess Game");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        boardPanel = new JPanel(new GridLayout(8, 8));
        initializeBoard();
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    /**
     * Plays a sound effect from a given file name.
     *
     * @param soundFileName The path of the sound file to play.
     */
    public void playSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(soundFileName)));
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the chessboard layout within the GUI.
     */
    private void initializeBoard() {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                squares[i][j] = createSquare(i, j);
                updateSquareIcon(squares[i][j], board.getPiece(new Position(i, j)));
                buttonPositionMap.put(squares[i][j], new Position(i, j));
                boardPanel.add(squares[i][j]);
            }
        }
    }

    /**
     * Creates and configures a chessboard square (JButton) for the given row and column.
     *
     * @param row The row index of the square.
     * @param col The column index of the square.
     * @return The configured JButton instance for the square.
     */
    private JButton createSquare(int row, int col) {
        JButton square = new JButton();
        configureSquare(square, row, col);
        return square;
    }

    /**
     * Configures the appearance and behavior of a chessboard square.
     *
     * @param square The JButton instance to configure.
     * @param row    The row index of the square.
     * @param col    The column index of the square.
     */
    private void configureSquare(JButton square, int row, int col) {
        square.setOpaque(true);
        square.setBorderPainted(false);
        square.setContentAreaFilled(true);
        square.setFocusPainted(false);

        // Adjust color based on row and column
        boolean isLightSquare = (row + col) % 2 == 0;
        square.setBackground(isLightSquare ? LIGHT_COLOR : DARK_COLOR);
        square.addActionListener(e -> handleSquareClick(square, row, col));
    }

    /**
     * Handles click events on the chessboard squares.
     *
     * @param clickedButton The JButton that was clicked.
     * @param row           The row index of the clicked square.
     * @param col           The column index of the clicked square.
     */
    private void handleSquareClick(JButton clickedButton, int row, int col) {
        if (selectedButton == null && clickedButton.getIcon() != null) {
            // Highlight the new selection
            highlightSquare(clickedButton, true);
            selectedButton = clickedButton;
        } else if (selectedButton != null) {
            // Process the move if a different square is clicked
            if (selectedButton != clickedButton) {
                processMove(selectedButton, clickedButton, row, col);
            }
            // Unhighlight the previous selection regardless of whether the same or a different square is clicked
            highlightSquare(selectedButton, false);
            selectedButton = null;
        }
    }

    /**
     * Highlights or resets the background color of a chessboard square.
     *
     * @param square    The JButton to highlight.
     * @param isSelected True to highlight the square, false to reset its color.
     */
    private void highlightSquare(JButton square, boolean isSelected) {
        if (isSelected) {
            square.setBackground(SELECTED_COLOR);
        } else {
            // Reset to the original color
            Position pos = getButtonPosition(square);
            boolean isLightSquare = (pos.getRow() + pos.getColumn()) % 2 == 0;
            Piece piece = board.getPiece(pos);

            // Check if this is the king's position and if the king is in check
            if (piece instanceof King && piece.getColor() == currentTurn.getColor() && board.isKingInCheck(currentTurn.getColor())) {
                square.setBackground(CHECK_COLOR);
            } else {
                square.setBackground(isLightSquare ? LIGHT_COLOR : DARK_COLOR);
            }
        }
    }


    /**
     * Processes a move from one square to another in the chess game.
     *
     * @param fromButton The JButton representing the starting square.
     * @param toButton   The JButton representing the target square.
     * @param targetRow  The row index of the target square.
     * @param targetCol  The column index of the target square.
     */
    private void processMove(JButton fromButton, JButton toButton, int targetRow, int targetCol) {
        Position sourcePos = getButtonPosition(fromButton);
        Position targetPos = new Position(targetRow, targetCol);

        if (game.makeMove(sourcePos, targetPos)) {
            Piece movedPiece = board.getPiece(targetPos);
            updateSquareIcon(fromButton, null);
            updateSquareIcon(toButton, movedPiece);
            resetButtonColor(fromButton);
            resetButtonColor(toButton);
            currentTurn = game.getCurrentTurn();
            highlightKingInCheck();
            playSound("/move_sound.wav");

        }
    }


    /**
     * Gets the position associated with a given JButton.
     *
     * @param button The JButton to get the position for.
     * @return The Position object associated with the JButton.
     */
    private Position getButtonPosition(JButton button) {
        return buttonPositionMap.get(button);
    }

    /**
     * Resets the background color of a given JButton to its default based on its position.
     *
     * @param button The JButton to reset the color for.
     */
    private void resetButtonColor(JButton button) {
        Position pos = getButtonPosition(button);
        boolean isLightSquare = (pos.getRow() + pos.getColumn()) % 2 == 0;
        button.setBackground(isLightSquare ? LIGHT_COLOR : DARK_COLOR);
    }

    /**
     * Updates the icon of a square based on the chess piece it contains.
     *
     * @param button The JButton to update the icon for.
     * @param piece  The Piece to represent with an icon.
     */
    private void updateSquareIcon(JButton button, Piece piece) {
        button.setIcon(piece != null ? piece.getImageIcon() : null);
    }

    /**
     * Highlights the king in check.
     */
    public void highlightKingInCheck() {
        PieceColor currentTurnColor = currentTurn.getColor();
        Position kingPosition = findKingPosition(currentTurnColor);

        if (kingPosition != null && board.isKingInCheck(currentTurnColor)) {
            JButton kingSquare = squares[kingPosition.getRow()][kingPosition.getColumn()];
            kingSquare.setBackground(CHECK_COLOR);
        } else {
            // Reset the color of the previously highlighted king square, if any
            resetKingSquareColor(kingPosition);
        }
    }

    /**
     * Finds the position of the king for a given player color.
     *
     * @param color The color of the player's king to find.
     * @return The Position of the king, or null if not found.
     */
    private Position findKingPosition(PieceColor color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(i, j));
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Resets the color of the square containing the king.
     *
     * @param kingPosition The Position of the king.
     */
    private void resetKingSquareColor(Position kingPosition) {
        if (kingPosition != null) {
            JButton kingSquare = squares[kingPosition.getRow()][kingPosition.getColumn()];
            boolean isLightSquare = (kingPosition.getRow() + kingPosition.getColumn()) % 2 == 0;
            kingSquare.setBackground(isLightSquare ? LIGHT_COLOR : DARK_COLOR);
        }
    }

    /**
     * Displays a message indicating checkmate.
     *
     * @param message The checkmate message to display.
     */
    public void displayCheckmateMessage(String message) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, message));
    }

}
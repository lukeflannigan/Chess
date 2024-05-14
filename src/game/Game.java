package game;

import pieces.*;
import board.*;
import gui.ChessGUI;

/**
 * a two-player chess game, managing the game state, players, and the graphical user interface.
 */
public class Game {
    private final Board board;
    private boolean isGameOver = false;
    private ChessGUI chessGUI;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentTurn;

    /**
     * Constructs a new Game instance initializing the chess board and two players (white and black).
     */
    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player(PieceColor.WHITE, board);
        this.blackPlayer = new Player(PieceColor.BLACK, board);
        this.currentTurn = whitePlayer; // White goes first
    }

    /**
     * Connects the graphical user interface with the game.
     *
     * @param gui The ChessGUI instance to be used with this game.
     */
    public void setChessGUI(ChessGUI gui) {
        this.chessGUI = gui;
    }



    /**
     * Switches the current turn between the white and black players.
     */
    public void switchPlayer() {
        if (currentTurn == whitePlayer) {
            currentTurn = blackPlayer;
        } else {
            currentTurn = whitePlayer;
        }

    }

    /**
     * Returns the current game board.
     *
     * @return The Board object representing the current state of the game.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the player whose turn it is currently.
     *
     * @return The current turn's Player object.
     */
    public Player getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Main method to start the chess game. Initializes the game and the GUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Game game = new Game();
        ChessGUI gui = new ChessGUI(game);
        game.setChessGUI(gui); // Set the GUI instance in the game
    }

    /**
     * Attempts to make a move in the chess game.
     * The move is executed if it's valid and if the game is not over.
     *
     * @param from The starting position of the move.
     * @param to   The destination position of the move.
     * @return true if the move is successful, false otherwise.
     */
    public boolean makeMove(Position from, Position to) {
        if (isGameOver) {
            return false; // Prevent moves after game over
        }

        if (currentTurn.makeMove(from, to)) {
            chessGUI.highlightKingInCheck();
            Player opponent;
            if (currentTurn == whitePlayer) {
                opponent = blackPlayer;
            } else {
                opponent = whitePlayer;
            }

            if (isCheckmate(opponent.getColor())) {
                handleCheckmate();
                isGameOver = true; // Set game over state
            }
            switchPlayer();
            return true;
        }
        return false;
    }

    /**
     * Checks if the specified player is in checkmate.
     *
     * @param color The color of the player to check for checkmate.
     * @return true if the player is in checkmate, false otherwise.
     */
    private boolean isCheckmate(PieceColor color) {
        boolean checkmate = board.isCheckmate(color);
        return checkmate;
    }

    /**
     * Handles the endgame scenario when checkmate is detected.
     * Displays the checkmate message and updates the game state.
     */
    private void handleCheckmate() {
        String message;
        if (currentTurn == whitePlayer) {
            message = "Checkmate! White wins!";
        } else {
            message = "Checkmate! Black wins!";
        }
        chessGUI.displayCheckmateMessage(message);
    }

}
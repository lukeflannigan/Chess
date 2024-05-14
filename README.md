## Introduction
This project is a Java implementation of the board game chess, featuring an object-oriented design and a graphical user interface. The game includes standard rules for chess including specific piece logic, turn based logic, and check/checkmate detection. It is implemented through several distinct classes that work together along with a swing based GUI to allow two people to play chess against each other.  

## Structure

### Board Package
- **Position**: Handles chessboard positions with row and column indices.
- **Board**: Manages the chessboard's state, including piece placement and movement rules.

### Pieces Package
- **Piece (Abstract Class)**: Common base class for all chess pieces.
- **Specific Pieces**: Subclasses like Pawn, Rook, Knight, Bishop, Queen, and King, each with unique movement logic rules.

### Game Package
- **Player**: Represents a chess player, managing their moves.
- **Game**: Oversees the game's flow, including turn management and checking for game-ending conditions. It is the entry point for the program, including the main method. 

### GUI Package
- **ChessGUI**: Provides the graphical interface for the game, handling user interactions and changes to the board. The GUI is swing based.

## Resources
- **Images**: Chess piece images are sourced from Wikipedia user [Cburnett](https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces).
- **Resource Organization**: All images and sound effects are located in the `resources` folder.

## Running JavaJedis Chess Game

## Method 1: Using Command Line

### Prerequisites
- Java must be installed on your device. [Download Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
- Clone or download the `JavaJedis` project from GitHub.

### Steps
1. **Open Command Prompt/Terminal:**
   - Windows: Open Command Prompt.
   - macOS/Linux: Open Terminal.

2. **Navigate to the src Directory:**
   - Use `cd` to go to the `src` directory within `JavaJedis`.
   - `cd path/to/JavaJedis/src`.

3. **Compile All Java Files:**
   - For Windows:
     ```
     javac -cp .;../resources */*.java
     ```
   - For macOS/Linux:
     ```
     javac -cp .:../resources */*.java
     ```

4. **Run the Main Class:**
   - For Windows:
     ```
     java -cp .;../resources game.Game
     ```
   - For macOS/Linux:
     ```
     java -cp .:../resources game.Game
     ```

## Method 2: Using an IDE (e.g., Visual Studio Code)

### Prerequisites
- Install your preferred IDE, such as Visual Studio Code.
- Java extension for Visual Studio Code (if using VSCode).
- Clone or download the `JavaJedis` project from GitHub.

### Steps
1. **Open the Project:**
   - Open your IDE and load the `JavaJedis` project folder.

2. **Configure the Resources Folder:**
   - In Visual Studio Code, right-click on the `resources` folder in the Explorer pane.
   - Select 'Add Folder to Java Source Path'.

3. **Locate and Run the Main Class:**
   - Find the `Game.java` file within the `game` package.
   - Right-click and select 'Run Java' or use the run button in the IDE.

### Note
- If using another IDE, ensure that the `resources` folder is recognized as a part of the project's resource directories.
// package tictactoe;

import java.util.Scanner;
import java.util.Random;

/**
 * This is the 4th stage of developing the game of tic-tac-toe.
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-14
 */

/**
 * Description
 * Let's write the medium difficulty level now. To do this, we need to add awareness to our AI.
 *
 * This level will be a lot harder to beat than easy, even though the initial moves are still random.
 * When the AI is playing at medium level, it wins when it can because of its first rule, and stops
 * all simple attempts to beat it due to its second.
 *
 * You can see these rules below.
 *
 * Objectives
 * When the AI is playing at medium difficulty level, it makes moves using the following logic:
 *
 * 1. If it already has two in a row and can win with one further move, it does so.
 * 2. If its opponent can win with one move, it plays the move necessary to block this.
 * 3. Otherwise, it makes a random move.
 *
 * You should add a medium parameter so that you can play against this level. It should also be possible
 * to make AIs using easy and medium levels play against each other!
 *
 * Example
 * The example below shows how your program should work.
 * The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.
 *
 * Input command: > start user medium
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 * Enter the coordinates: > 2 2
 * ---------
 * |       |
 * |   X   |
 * |       |
 * ---------
 * Making move level "medium"
 * ---------
 * |       |
 * |   X   |
 * | O     |
 * ---------
 * Enter the coordinates: > 1 1
 * ---------
 * | X     |
 * |   X   |
 * | O     |
 * ---------
 * Making move level "medium"
 * ---------
 * | X     |
 * |   X   |
 * | O   O |
 * ---------
 * Enter the coordinates: > 3 3
 * ---------
 * | X     |
 * |   X   |
 * | O X O |
 * ---------
 * Making move level "medium"
 * ---------
 * | X O   |
 * |   X   |
 * | O X O |
 * ---------
 * Enter the coordinates: > 2 1
 * ---------
 * | X O   |
 * | X X   |
 * | O X O |
 * ---------
 * Making move level "medium"
 * ---------
 * | X O   |
 * | X X O |
 * | O X O |
 * ---------
 * Enter the coordinates: > 1 3
 * ---------
 * | X O X |
 * | X X O |
 * | O X O |
 * ---------
 * Draw
 *
 * Input command: > start medium user
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 * Making move level "medium"
 * ---------
 * |       |
 * |       |
 * |   X   |
 * ---------
 * Enter the coordinates: > 2 2
 * ---------
 * |       |
 * |   O   |
 * |   X   |
 * ---------
 * Making move level "medium"
 * ---------
 * |       |
 * |   O   |
 * | X X   |
 * ---------
 * Enter the coordinates: > 3 3
 * ---------
 * |       |
 * |   O   |
 * | X X O |
 * ---------
 * Making move level "medium"
 * ---------
 * | X     |
 * |   O   |
 * | X X O |
 * ---------
 * Enter the coordinates: > 2 1
 * ---------
 * | X     |
 * | O O   |
 * | X X O |
 * ---------
 * Making move level "medium"
 * ---------
 * | X     |
 * | O O X |
 * | X X O |
 * ---------
 * Enter the coordinates: > 1 3
 * ---------
 * | X   O |
 * | O O X |
 * | X X O |
 * ---------
 * Making move level "medium"
 * ---------
 * | X X O |
 * | O O X |
 * | X X O |
 * ---------
 * Draw
 *
 * Input command: > exit
 */

public class GameTTT4 {
    /**
     * Define the constants
     */
    // number of cells in a row
    final static int SIZE = 3;

    // which cell in the current position
    final static int EMPTY = 0;
    final static int XCELL = 1;
    final static int OCELL = 2;

    // game state
    final static int INVALID = -1;
    final static int DRAW = 0;
    final static int XWIN = 1;
    final static int OWIN = 2;
    final static int NOTFINISHED = 3;

    // who is playing now
    final static int ROBOT = 0;
    final static int HUMAN = 1;

    // who is moving now
    static boolean isFirstPlayerMove = true;

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    static int player1Level = EASY;    // initialize the level for robot 1 to be easy
    static int player2Level = EASY;    // initialize the level for robot 2 to be easy

    static int numOfX = 0; // keep track of the number of X on the initial board
    static int numOfO = 0; // keep track of the number of O on the initial board

    /**
     * The entrance to the game
     *
     * @param args string parameters from the command line
     */
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int[][] board = new int[SIZE][SIZE];

        gameControl(scanner, board);    // the menu loop controlling the game
    }

    /**
     * The menu control loop of the game
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    public static void gameControl(Scanner scanner, int[][] board) {
        String strCommand;  // command string: "start", "exit"
        String strParam;    // parameter string
        int player1 = ROBOT;        // first player initialized to robot
        int player2 = ROBOT;        // second player initialized to robot

        do {
            System.out.print("Input command: > ");

            strCommand = scanner.next();
            if (strCommand.equals("start")) {
                strParam = scanner.nextLine();

                // When using the method nextLine() to get the rest of the input, the array length is 3 in this case.
                // And the first element of this array is " ".
                String[] arrayParam = strParam.split(" ");

                if (arrayParam.length != 3) {
                    System.out.println("Bad parameters!");
                } else {    // correct inputs and proceed the normal flow of the game
                    if (arrayParam[1].equals("easy") || arrayParam[1].equals("medium") || arrayParam[1].equals("user") ||
                            arrayParam[2].equals("easy") || arrayParam[2].equals("medium") || arrayParam[2].equals("user")) {

                        switch (arrayParam[1]) {
                            case "easy":
                                player1 = ROBOT;
                                player1Level = EASY;
                                break;
                            case "medium":
                                player1 = ROBOT;
                                player1Level = MEDIUM;
                                break;
                            case "user":
                                player1 = HUMAN;
                                break;
                        };

                        switch (arrayParam[2]) {
                            case "easy":
                                player2 = ROBOT;
                                player2Level = EASY;
                                break;
                            case "medium":
                                player2 = ROBOT;
                                player2Level = MEDIUM;
                                break;
                            case "user":
                                player2 = HUMAN;
                                break;
                        };

                        drawEmptyBoard();
                        playGame(scanner, board, player1, player2); // game playing starts here!
                        clearBoard(board);  // reset the board

                    } else {
                        System.out.println("Bad parameters!");
                    }
                }
            } else if (strCommand.equals("exit")) {
                break;  // break out of the do-while loop, and game ends
            } else {
                System.out.println("Bad parameters!");
            }
        } while (true);
    }

    /**
     * The game logic
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     * @param player1 the first player
     * @param player2 the second player
     */
    public static void playGame(Scanner scanner, int[][] board, int player1, int player2) {
        int state = INVALID;  // initialize the game state

        // infinite loop until one side win or draw
        while (true) {

            if (player1 == ROBOT && isFirstPlayerMove || player2 == ROBOT && !isFirstPlayerMove) {
                robotMove(board);
            } else {
                humanMove(scanner, board);
            }

            drawBoard(board);

            state = checkGameState(board);  // check game state
            switch (state) {
                case XWIN:
                    System.out.println("X wins");
                    return;
                case OWIN:
                    System.out.println("O wins");
                    return;
                case DRAW:
                    System.out.println("Draw");
                    return;
                case NOTFINISHED:
                    isFirstPlayerMove = !isFirstPlayerMove; // continue playing the game by switching the side
                    break;
            }
        }
    }

    /**
     * robot makes the move
     *
     * @param board game board
     */
    public static void robotMove(int[][] board) {
        if (player1Level == MEDIUM && isFirstPlayerMove || player2Level == MEDIUM && !isFirstPlayerMove) {
            System.out.println("Making move level \"medium\"");

            if (!robotMoveMedium(board)) {
                robotMoveEasy(board);   // Otherwise, it makes a random move.
            }
        } else {
            System.out.println("Making move level \"easy\"");
            robotMoveEasy(board);
        }
    }

    /**
     * robot makes the easy level move
     * @param board game board
     */
    public static void robotMoveEasy(int[][] board) {
        Random rand = new Random();
        int row;
        int col;

        // loop until robot makes valid move
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (board[row][col] != EMPTY);

        board[row][col] = isFirstPlayerMove ? XCELL : OCELL;    // make the robot move
    }

    /**
     * robot makes the medium level move
     * @param board game board
     */
    public static boolean robotMoveMedium(int[][] board) {

        int myCell = isFirstPlayerMove ? XCELL : OCELL;     // current player
        int oppCell = isFirstPlayerMove ? OCELL : XCELL;    // opponent player

        /**
         * If it already has two in a row and can win with one further move, it does so.
         */
        // check the first row
        if (board[0][0] == myCell && board[0][1] == myCell && board[0][2] == EMPTY) {
            board[0][2] = myCell;
            return true;
        }
        if (board[0][0] == myCell && board[0][1] == EMPTY && board[0][2] ==  myCell) {
            board[0][1] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[0][1] == myCell && board[0][2] ==  myCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the second row
        if (board[1][0] == myCell && board[1][1] == myCell && board[1][2] == EMPTY) {
            board[1][2] = myCell;
            return true;
        }
        if (board[1][0] == myCell && board[1][1] == EMPTY && board[1][2] ==  myCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[1][0] == EMPTY && board[1][1] == myCell && board[1][2] ==  myCell) {
            board[1][0] = myCell;
            return true;
        }

        // check the third row
        if (board[2][0] == myCell && board[2][1] == myCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[2][0] == myCell && board[2][1] == EMPTY && board[2][2] ==  myCell) {
            board[2][1] = myCell;
            return true;
        }
        if (board[2][0] == EMPTY && board[2][1] == myCell && board[2][2] ==  myCell) {
            board[2][0] = myCell;
            return true;
        }

        // check the first col
        if (board[0][0] == myCell && board[1][0] == myCell && board[2][0] == EMPTY) {
            board[2][0] = myCell;
            return true;
        }
        if (board[0][0] == myCell && board[1][0] == EMPTY && board[2][0] ==  myCell) {
            board[1][0] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[1][0] == myCell && board[2][0] ==  myCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the second col
        if (board[0][1] == myCell && board[1][1] == myCell && board[2][1] == EMPTY) {
            board[2][1] = myCell;
            return true;
        }
        if (board[0][1] == myCell && board[1][1] == EMPTY && board[2][1] ==  myCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][1] == EMPTY && board[1][1] == myCell && board[2][1] ==  myCell) {
            board[0][1] = myCell;
            return true;
        }

        // check the third col
        if (board[0][2] == myCell && board[1][2] == myCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[0][2] == myCell && board[1][2] == EMPTY && board[2][2] ==  myCell) {
            board[1][2] = myCell;
            return true;
        }
        if (board[0][2] == EMPTY && board[1][2] == myCell && board[2][2] ==  myCell) {
            board[0][2] = myCell;
            return true;
        }

        // check the diagonal
        if (board[0][0] == myCell && board[1][1] == myCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[0][0] == myCell && board[1][1] == EMPTY && board[2][2] ==  myCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[1][1] == myCell && board[2][2] ==  myCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the reverse diagonal
        if (board[0][2] == myCell && board[1][1] == myCell && board[2][0] == EMPTY) {
            board[2][0] = myCell;
            return true;
        }
        if (board[0][2] == myCell && board[1][1] == EMPTY && board[2][0] ==  myCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][2] == EMPTY && board[1][1] == myCell && board[2][0] ==  myCell) {
            board[0][2] = myCell;
            return true;
        }

        /**
         * If its opponent can win with one move, it plays the move necessary to block this.
         */
        // check the first row
        if (board[0][0] == oppCell && board[0][1] == oppCell && board[0][2] == EMPTY) {
            board[0][2] = myCell;
            return true;
        }
        if (board[0][0] == oppCell && board[0][1] == EMPTY && board[0][2] ==  oppCell) {
            board[0][1] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[0][1] == oppCell && board[0][2] ==  oppCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the second row
        if (board[1][0] == oppCell && board[1][1] == oppCell && board[1][2] == EMPTY) {
            board[1][2] = myCell;
            return true;
        }
        if (board[1][0] == oppCell && board[1][1] == EMPTY && board[1][2] ==  oppCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[1][0] == EMPTY && board[1][1] == oppCell && board[1][2] ==  oppCell) {
            board[1][0] = myCell;
            return true;
        }

        // check the third row
        if (board[2][0] == oppCell && board[2][1] == oppCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[2][0] == oppCell && board[2][1] == EMPTY && board[2][2] ==  oppCell) {
            board[2][1] = myCell;
            return true;
        }
        if (board[2][0] == EMPTY && board[2][1] == oppCell && board[2][2] ==  oppCell) {
            board[2][0] = myCell;
            return true;
        }

        // check the first col
        if (board[0][0] == oppCell && board[1][0] == oppCell && board[2][0] == EMPTY) {
            board[2][0] = myCell;
            return true;
        }
        if (board[0][0] == oppCell && board[1][0] == EMPTY && board[2][0] ==  oppCell) {
            board[1][0] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[1][0] == oppCell && board[2][0] ==  oppCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the second col
        if (board[0][1] == oppCell && board[1][1] == oppCell && board[2][1] == EMPTY) {
            board[2][1] = myCell;
            return true;
        }
        if (board[0][1] == oppCell && board[1][1] == EMPTY && board[2][1] ==  oppCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][1] == EMPTY && board[1][1] == oppCell && board[2][1] ==  oppCell) {
            board[0][1] = myCell;
            return true;
        }

        // check the third col
        if (board[0][2] == oppCell && board[1][2] == oppCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[0][2] == oppCell && board[1][2] == EMPTY && board[2][2] ==  oppCell) {
            board[1][2] = myCell;
            return true;
        }
        if (board[0][2] == EMPTY && board[1][2] == oppCell && board[2][2] ==  oppCell) {
            board[0][2] = myCell;
            return true;
        }

        // check the diagonal
        if (board[0][0] == oppCell && board[1][1] == oppCell && board[2][2] == EMPTY) {
            board[2][2] = myCell;
            return true;
        }
        if (board[0][0] == oppCell && board[1][1] == EMPTY && board[2][2] ==  oppCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][0] == EMPTY && board[1][1] == oppCell && board[2][2] ==  oppCell) {
            board[0][0] = myCell;
            return true;
        }

        // check the reverse diagonal
        if (board[0][2] == oppCell && board[1][1] == oppCell && board[2][0] == EMPTY) {
            board[2][0] = myCell;
            return true;
        }
        if (board[0][2] == oppCell && board[1][1] == EMPTY && board[2][0] ==  oppCell) {
            board[1][1] = myCell;
            return true;
        }
        if (board[0][2] == EMPTY && board[1][1] == oppCell && board[2][0] ==  oppCell) {
            board[0][2] = myCell;
            return true;
        }

        return false;
    }

    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    public static void humanMove(Scanner scanner, int[][] board) {
        int row;    // x coordinate
        int col;    // y coordinate

        String strRow;
        String strCol;

        // infinite loop until human makes valid move
        while (true) {
            System.out.print("Enter the coordinates: > ");
            strRow = scanner.next();

            if (strRow.length() > 1 || strRow.toCharArray()[0] < '0' || strRow.toCharArray()[0] > '9') {
                System.out.println("You should enter numbers!");

                scanner.nextLine(); // Important! Skip the rest of the line to get rid of the carriage return
                continue;
            }

            strCol = scanner.next();
            if (strCol.length() > 1 || strCol.toCharArray()[0] < '0' || strCol.toCharArray()[0] > '9') {
                System.out.println("You should enter numbers!");
                continue;
            }

            row = strRow.toCharArray()[0] - '1';
            col = strCol.toCharArray()[0] - '1';
            if (row < 0 || row > 2 || col < 0 || col > 2) { // convert to the board coordinates
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (board[row][col] != EMPTY) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                board[row][col] = isFirstPlayerMove ? XCELL : OCELL;    // make the human move
                break;  // break out of the while loop when human enters the valid move
            }
        }
    }

    /**
     * check the game state
     *
     * @param board game board
     * @return game state
     */
    public static int checkGameState(int[][] board) {
        if (checkWin(board, XCELL) == XWIN) {
            return XWIN;
        }

        if (checkWin(board, OCELL) == OWIN) {
            return OWIN;
        }

        boolean isBoardFull = true;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    isBoardFull = false;
                    break;  // break out of the inner for loop
                }

                if (!isBoardFull) { // need to break out of the outer for loop
                    break;
                }
            }
        }

        return isBoardFull ? DRAW : NOTFINISHED;
    }

    /**
     * reset the board for the next game
     *
     * @param board game board
     */
    public static void clearBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = EMPTY;
            }
        }
    }

    /**
     * draw the board onto the screen
     *
     * @param board game board
     */
    public static void drawBoard(int[][] board) {
        System.out.println("---------");

        for (int row = 0; row < SIZE; row++) {
            System.out.print('|');

            for (int col = 0; col < SIZE; col++) {
                switch (board[row][col]) {
                    case EMPTY:
                        System.out.print("  ");
                        break;
                    case XCELL:
                        System.out.print(" X");
                        break;
                    case OCELL:
                        System.out.print(" O");
                        break;
                }
            }

            System.out.println(" |");
        }

        System.out.println("---------");
    }

    /**
     * check who wins the game
     *
     * @param board  game board
     * @param player who is playing
     * @return who wins the game
     */
    public static int checkWin(int[][] board, int player) {

        // check the game state standing at the cell (0, 0)
        if (board[0][0] == player && board[0][1] == player && board[0][2] == player)
            return player == XCELL ? XWIN : OWIN;
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return player == XCELL ? XWIN : OWIN;
        if (board[0][0] == player && board[1][0] == player && board[2][0] == player)
            return player == XCELL ? XWIN : OWIN;

        // check the game state standing at the cell (0, 1)
        if (board[0][1] == player && board[1][1] == player && board[2][1] == player)
            return player == XCELL ? XWIN : OWIN;

        // check the game state standing at the cell (0, 2)
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return player == XCELL ? XWIN : OWIN;
        if (board[0][2] == player && board[1][2] == player && board[2][2] == player)
            return player == XCELL ? XWIN : OWIN;

        // check the game state standing at the cell (1, 0)
        if (board[1][0] == player && board[1][1] == player && board[1][2] == player)
            return player == XCELL ? XWIN : OWIN;

        // check the game state standing at the cell (2, 0)
        if (board[2][0] == player && board[2][1] == player && board[2][2] == player)
            return player == XCELL ? XWIN : OWIN;

        return DRAW;
    }

    /**
     * draw the empty board
     */
    public static void drawEmptyBoard() {
        System.out.println("---------");
        for (int row = 0; row < SIZE; row++) {
            System.out.println("|       |");
        }
        System.out.println("---------");
    }

    @Deprecated
    /**
     * Setup the board with initial string input.
     * Note: this method actually used in the first stage of this game development, and it's not used anymore.
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    public static void setupBoard(Scanner scanner, int[][] board) {
        int row;    // x coordinate
        int col;    // y coordinate

        System.out.print("Enter the cells: > ");
        String strCells = scanner.next();

        row = 0;

        // print out the initial board and set up the tic-tac-toe board
        System.out.println("---------");

        for (int k = 0; k < strCells.length(); k += 3) {
            System.out.print('|');

            for (col = 0; col < SIZE; col++) {
                if (strCells.charAt(k + col) == '_') {
                    System.out.print("  ");
                    board[row][col] = EMPTY;
                } else {
                    System.out.print(" " + strCells.charAt(k + col));
                    if (strCells.charAt(k + col) == 'X') {
                        board[row][col] = XCELL;
                        numOfX++;
                    } else {
                        board[row][col] = OCELL;
                        numOfO++;
                    }
                }
            }

            System.out.println(" |");
            row++;  // move to the next row
        }

        System.out.println("---------");
    }
}

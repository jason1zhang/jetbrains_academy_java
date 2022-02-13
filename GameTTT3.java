// package tictactoe;

import java.util.Scanner;
import java.util.Random;

/**
 * This is the 3rd stage of developing the game of tic-tac-toe.
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-13
 */

/**
 * Description
 * It's time to make things more interesting by adding some game variations. What if you want to play
 * against a friend instead of the AI? How about if you get tired of playing the game and want to see
 * a match between two AIs? You also need to give the user the option of going first or second when
 * playing against the AI.
 *
 * It should be possible for the user to quit the game after the result is displayed as well.
 *
 * Objectives
 * Your tasks for this stage are:
 *
 * 1. Write a menu loop, which can interpret two commands: start and exit.
 * 2. Implement the command start. It should take two parameters: who will play X and who will play O.
 * Two options are possible for now: user to play as a human, and easy to play as an AI.
 * 3. The exit command should simply end the program.
 *
 * In later steps, you will add the medium and hard levels.
 *
 * Don't forget to handle incorrect input! The message Bad parameters! should be displayed if what the
 * user enters is invalid.
 *
 * Example
 * The example below shows how your program should work.
 * The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part
 * of the input.
 *
 * Input command: > start
 * Bad parameters!
 * Input command: > start easy
 * Bad parameters!
 * Input command: > start easy easy
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * |     X |
 * |       |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * | O   X |
 * |       |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * | O   X |
 * |     X |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * | O   X |
 * |   O X |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * | O X X |
 * |   O X |
 * ---------
 * Making move level "easy"
 * ---------
 * |     O |
 * | O X X |
 * |   O X |
 * ---------
 * Making move level "easy"
 * ---------
 * | X   O |
 * | O X X |
 * |   O X |
 * ---------
 * X wins
 *
 * Input command: > start easy user
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 * Making move level "easy"
 * ---------
 * |       |
 * |       |
 * |     X |
 * ---------
 * Enter the coordinates: > 2 2
 * ---------
 * |       |
 * |   O   |
 * |     X |
 * ---------
 * Making move level "easy"
 * ---------
 * |   X   |
 * |   O   |
 * |     X |
 * ---------
 * Enter the coordinates: > 3 1
 * ---------
 * |   X   |
 * |   O   |
 * | O   X |
 * ---------
 * Making move level "easy"
 * ---------
 * |   X X |
 * |   O   |
 * | O   X |
 * ---------
 * Enter the coordinates: > 2 3
 * ---------
 * |   X X |
 * |   O O |
 * | O   X |
 * ---------
 * Making move level "easy"
 * ---------
 * | X X X |
 * |   O O |
 * | O   X |
 * ---------
 * X wins
 *
 * Input command: > start user user
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 * Enter the coordinates: > 3 1
 * ---------
 * |       |
 * |       |
 * | X     |
 * ---------
 * Enter the coordinates: > 2 2
 * ---------
 * |       |
 * |   O   |
 * | X     |
 * ---------
 * Enter the coordinates: > 2 1
 * ---------
 * |       |
 * | X O   |
 * | X     |
 * ---------
 * Enter the coordinates: > 3 2
 * ---------
 * |       |
 * | X O   |
 * | X O   |
 * ---------
 * Enter the coordinates: > 1 1
 * ---------
 * | X     |
 * | X O   |
 * | X O   |
 * ---------
 * X wins
 *
 * Input command: > exit
 */

public class GameTTT3 {
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

    // game level
    // final static int EASY = 0;

    static int numOfX = 0; // keep track of the number of X on the initial board
    static int numOfO = 0; // keep track of the number of O on the initial board

    /**
     * The entrance to the game
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
     * @param scanner java.util.Scanner
     * @param board game board
     */
    public static void gameControl(Scanner scanner, int[][] board) {
        String strCommand;  // command string: "start", "exit"
        String strParam;    // parameter string
        int player1;        // first player
        int player2;        // second player

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
                } else {
                    if ((arrayParam[1].equals("easy") || arrayParam[1].equals("user")) || arrayParam[2].equals("easy") || arrayParam[2].equals("user")) {
                        player1 = arrayParam[1].equals("easy") ? ROBOT : HUMAN;
                        player2 = arrayParam[2].equals("easy") ? ROBOT : HUMAN;

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
     * @param scanner java.util.Scanner
     * @param board game board
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
     * @param board game board
     */
    public static void robotMove(int[][] board) {
        Random rand = new Random();
        int row;
        int col;

        System.out.println("Making move level \"easy\"");

        // loop until robot makes valid move
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (board[row][col] != EMPTY);

        board[row][col] = isFirstPlayerMove ? XCELL : OCELL;    // make the robot move
    }

    /**
     * human makes the move
     * @param scanner java.util.Scanner
     * @param board game board
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
     * @param board game board
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

    /**
     * Setup the board with initial string input.
     * Note: this method actually used in the first stage of this game development, and it's not used anymore.
     *
     * @param scanner java.util.Scanner
     * @param board game board
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

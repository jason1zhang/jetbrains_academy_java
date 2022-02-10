package tictactoe;

import java.util.Scanner;
import java.util.Random;

/**
 * This is the 2nd step of developing the game of tic-tac-toe.
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-11
 */

/**
 * Description
 * Now it's time to make a working game, so let's create our first opponent! In this version of the program, 
 * the user will be playing as X, and the computer will be playing as O at easy level. This will be our first 
 * small step towards creating the AI!
 * 
 * Let's design it so that at this level the computer makes random moves. This should be perfect for people who 
 * have never played the game before!
 * 
 * If you want, you could make the game even simpler by including a difficulty level where the computer never 
 * wins. Feel free to create this along with the easy level if you like, but it won't be tested.
 * 
 * Objectives
 * In this stage, you should implement the following:
 * 1. Display an empty table when the program starts.
 * 2. The user plays first as X, and the program should ask the user to enter cell coordinates.
 * 3. Next, the computer makes its move as O, and the players then move in turn until someone wins or the game 
 * results in a draw.
 * 4. Print the final outcome at the very end of the game.
 * 
 * Example
 * The example below shows how your program should work.
 * The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.
 * 
 * ---------
 * |       |
 * |       |
 * |       |
 * ---------
 *  Enter the coordinates: > 2 2
 * ---------
 * |       |
 * |   X   |
 * |       |
 * ---------
 * Making move level "easy"
 * ---------
 * | O     |
 * |   X   |
 * |       |
 * ---------
 * Enter the coordinates: > 3 3
 * ---------
 * | O     |
 * |   X   |
 * |     X |
 * ---------
 * Making move level "easy"
 * ---------
 * | O     |
 * | O X   |
 * |     X |
 * ---------
 * Enter the coordinates: > 3 1
 * ---------
 * | O     |
 * | O X   |
 * | X   X |
 * ---------
 * Making move level "easy"
 * ---------
 * | O     |
 * | O X O |
 * | X   X |
 * ---------
 * Enter the coordinates: > 3 2
 * ---------
 * | O     |
 * | O X O |
 * | X X X |
 * ---------
 * X wins
 */

public class GameTTT2 {
    // Define the constants
    final static int SIZE = 3;     // number of cells in a row

    // which cell in the position
    final static int EMPTY = 0;
    final static int XCELL = 1;
    final static int OCELL = 2;

    // game state
    final static int INVALID = -1;
    final static int DRAW = 0;
    final static int XWIN = 1;
    final static int OWIN = 2;
    final static int NOTFINISHED = 3;

    // game level
    // final static int EASY = 0;

    static int numOfX = 0; // keep track of the number of X on the initial board
    static int numOfO = 0; // keep track of the number of O on the initial board

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int[][] board = new int[SIZE][SIZE];

        drawEmptyBoard();
        // setupBoard(scanner, board);
        playGame(scanner, board);

    }

    public static void drawEmptyBoard() {
        System.out.println("---------");
        for (int row = 0; row < SIZE; row++) {
            System.out.println("|       |");
        }
        System.out.println("---------");
    }



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
                    if (strCells.charAt(k + col)  == 'X') {
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

    public static void playGame(Scanner scanner, int[][] board) {
        // infinite loop until win or draw
        int row;    // x coordinate
        int col;    // y coordinate
        String strRow;
        String strCol;
        int state = INVALID;  // game state

        while (true) {
            System.out.print("Enter the coordinates: > ");
            strRow = scanner.next();

            if (strRow.length() > 1 || strRow.toCharArray()[0] < '0' || strRow.toCharArray()[0] > '9') {
                System.out.println("You should enter numbers!");

                scanner.nextLine(); // skip the rest of the line
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
                continue;
            }

            if (numOfX == numOfO) {
                board[row][col] = XCELL;
                state = checkGameState(board);
            } else {
                board[row][col] = OCELL;
                state = checkGameState(board);
            }

            drawBoard(board);   // draw the board before printing out the result

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
                    // System.out.println("Game not finished");
                    // return;
                    System.out.println("Making move level \"easy\"");
                    robotMove(board);
                    drawBoard(board);   // redraw the board
                    break;
            }
        }
    }

    public static void robotMove(int[][] board) {
        Random rand = new Random();
        int row;
        int col;

        // robot makes valid move
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (board[row][col] != EMPTY);

        board[row][col] = OCELL;    // robot always plays as O
    }

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
                    break;
                }

                // need to break out the outer loop
                if (!isBoardFull) {
                    break;
                }
            }
        }

        return isBoardFull ? DRAW : NOTFINISHED;
    }

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
}

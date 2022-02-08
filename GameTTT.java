package tictactoe;

import java.util.Scanner;

/**
 * Description
 * In this project, you'll write a game called Tic-Tac-Toe that you can play against your computer.
 * The computer will have three levels of difficulty — easy, medium, and hard.
 *
 * To begin with, let's write a program that knows how to work with coordinates and determine the
 * state of the game.
 *
 * The top-left cell will have the coordinates (1, 1) and the bottom-right cell will have the
 * coordinates (3, 3), as shown in this table:
 *
 * (1, 1) (1, 2) (1, 3)
 * (2, 1) (2, 2) (2, 3)
 * (3, 1) (3, 2) (3, 3)
 *
 * The program should ask the user to enter the coordinates of the cell where they want to make a move.
 *
 * Keep in mind that the first coordinate goes from top to bottom, and the second coordinate goes from
 * left to right. Also, notice that coordinates start with 1 and can be 1, 2, or 3.
 *
 * But what if the user attempts to enter invalid coordinates? This could happen if they try to enter
 * letters or symbols instead of numbers, or the coordinates of an already occupied cell. Your program
 * needs to prevent these things from happening by checking the user's input and catching possible exceptions.
 *
 *
 * Objectives
 * The program should work in the following way:
 *
 * 1. Ask the user to provide the initial state of the 3x3 table with the first input line. This must include
 * nine symbols that can be X, O or _ (the latter represents an empty cell).
 *
 * 2. Output the specified 3x3 table before the user makes a move.
 *
 * 3. Request that the user enters the coordinates of the move they wish to make.
 *
 * 4. The user then inputs two numbers representing the cell in which they wish to place their X or O. The
 * game always starts with X, so the user's move should be made with this symbol if there are an equal
 * number of X's and O's in the table. If the table contains an extra X, the move should be made with O.
 *
 * 5. Analyze the user input and show messages in the following situations:
 * • This cell is occupied! Choose another one! — if the cell is not empty;
 * • You should enter numbers! — if the user tries to enter letters or symbols instead of numbers;
 * • Coordinates should be from 1 to 3! — if the user attempts to enter coordinates outside of the table's range.
 *
 * 6. Display the table again with the user's most recent move included.
 *
 * 7. Output the state of the game.
 *
 * The possible states are:
 *
 *  • Game not finished — when no side has three in a row, but the table still has empty cells;
 *  • Draw — when no side has three in a row, and the table is complete;
 *  • X wins — when there are three X's in a row (up, down, across, or diagonally);
 *  • O wins — when there are three O's in a row (up, down, across, or diagonally).
 *
 * If the user provides invalid coordinates, the program should repeat the request until numbers that
 * represent an empty cell on the table are supplied. You should ensure that the program only outputs
 * the table twice — before the move and after the user makes a legal move.
 *
 *
 * Examples
 * The examples below show how your program should work.
 * The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part
 * of the input.
 *
 * Example 1:
 *
 * Enter the cells: > _XXOO_OX_
 * ---------
 * |   X X |
 * | O O   |
 * | O X   |
 * ---------
 * Enter the coordinates: > 3 1
 * This cell is occupied! Choose another one!
 * Enter the coordinates: > one
 * You should enter numbers!
 * Enter the coordinates: > one three
 * You should enter numbers!
 * Enter the coordinates: > 4 1
 * Coordinates should be from 1 to 3!
 * Enter the coordinates: > 1 1
 * ---------
 * | X X X |
 * | O O   |
 * | O X   |
 * ---------
 * X wins
 *
 *
 * Example 2:
 *
 * Enter the cells: > XX_XOXOO_
 * ---------
 * | X X   |
 * | X O X |
 * | O O   |
 * ---------
 * Enter the coordinates: > 3 3
 * ---------
 * | X X   |
 * | X O X |
 * | O O O |
 * ---------
 * O wins
 *
 *
 * Example 3:
 *
 * Enter the cells: > XX_XOXOO_
 * ---------
 * | X X   |
 * | X O X |
 * | O O   |
 * ---------
 * Enter the coordinates: > 1 3
 * ---------
 * | X X O |
 * | X O X |
 * | O O   |
 * ---------
 * O wins
 *
 *
 * Example 4:
 *
 * Enter the cells: > OX_XOOOXX
 * ---------
 * | O X   |
 * | X O O |
 * | O X X |
 * ---------
 * Enter the coordinates: > 1 3
 * ---------
 * | O X X |
 * | X O O |
 * | O X X |
 * ---------
 * Draw
 *
 * 
 * Example 5:
 *
 * Enter the cells: >  _XO_OX___
 * ---------
 * |   X O |
 * |   O X |
 * |       |
 * ---------
 * Enter the coordinates: > 3 1
 * ---------
 * |   X O |
 * |   O X |
 * | X     |
 * ---------
 * Game not finished
 */

public class GameTTT {
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

    static int numOfX = 0; // keep track of the number of X on the initial board
    static int numOfO = 0; // keep track of the number of O on the initial board

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int[][] board = new int[SIZE][SIZE];

        setupBoard(scanner, board);
        playGame(scanner, board);

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
                    System.out.println("Game not finished");
                    return;
            }
        }
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

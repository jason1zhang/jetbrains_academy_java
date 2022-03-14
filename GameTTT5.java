// package tictactoe;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 * The 5th development stage of the game tic-tac-toe.
 *
 * Step 1: re-code the game with OOP approach, and pass the tests
 *
 * step 2: rewrite the program with the following sub-steps:
 *    2.1: use 1D array to represent 2D board;
 *    2.2: clean up the code to make it more compact and beautiful
 *    2.3: pass the tests in the jetbrains academy again
 *
 * step 3: implement the hard level of robot player, using minimax algorithm
 *  A Minimax algorithm can be best defined as a recursive function that does the following things:
 *      1. return a value if a terminal state is found (+10, 0, -10)
 *      2. go through available spots on the board
 *      3. call the minimax function on each available spot (recursion)
 *      4. evaluate returning values from function calls
 *      5. and return the best value
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-19
 */

public class GameTTT5 {
    /**
     * The entrance to the game
     *
     * @param args string parameters from the command line
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        // game.testMiniMax();
    }
}


class Game {
    /**
     * Define the constants
     */

    // number of cells in a row on a sqaured board
    final static int SIZE = 3;

    // cell type at the position
    final static int CELL_EMPTY = 0;
    final static int CELL_X = 1;
    final static int CELL_O = 2;

    // game state
    final static int INVALID = 0;
    final static int X_WIN = 1;
    final static int O_WIN = 2;
    final static int DRAW = 3;
    final static int NOT_FINISHED = 4;

    // who is in the move now
    final static int ROBOT = 0;
    final static int HUMAN = 1;

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    final static int HARD = 2;

    // score for the minmax function
    final static int SCORE_WIN = 10;
    final static int SCORE_LOSS = -10;
    final static int SCORE_DRAW = 0;

    /**
     * member variables
     */
    private boolean isPlayerOneMove;    // who is moving now
    private int fc;                     // keep track of the function calls for the minimax function
    private int state;                  // game state

    public Game() {
        resetGame();
    }

    /**
     * reset the game parameters
     */
    private void resetGame() {
        this.isPlayerOneMove = true;
        this.fc = 0;
        this.state = INVALID;
    }

    /**
     * test the functionality of minimaxMove method
     */
    public void testMiniMax() {
        String strBoard = "O_XX_X_OO";
        GameTTTBoard board = new GameTTTBoard(strBoard);

        PlayerRobot playerOne = new PlayerRobot(); // default constructor: first player and easy level
        PlayerHuman playerTwo = new PlayerHuman(); // default constructor: second player

        for (Cell cell : board.getBoard()) {
            switch(cell.getType()) {
                case Game.CELL_X:
                    playerOne.makeMove(new Move(0, cell));
                    break;
                case Game.CELL_O:
                    playerTwo.makeMove(new Move(0, cell));
                    break;
                default:
                    break;
            }
        }

        // System.out.println("player one's moves: > " + playerOne.getMoves());
        // System.out.println("player two's moves: > " + playerTwo.getMoves());

        Move bestMove = playerOne.miniMaxMove(board, playerOne);
        System.out.println("next best move: " + bestMove);
    }



    /**
     * The main control loop of the game
     */
    protected void start() {
        Scanner scanner = new Scanner(System.in);

        String strCommand = null;   // command string: "start", "exit"
        String strParam = null;     // parameter string after command
        Player playerOne = null;    // first player
        Player playerTwo = null;    // second player

        do {
            System.out.print("Input command: > ");

            strCommand = scanner.next();
            if (strCommand.equals("start")) {
                strParam = scanner.nextLine();

                // When using the method nextLine() to get the rest of the input,
                // the array length is 3, aAnd the first element at index 0 in this array is " ".
                String[] arrayParam = strParam.split(" ");

                if (arrayParam.length != 3) {
                    System.out.println("Bad parameters!");
                } else { // input correctly and proceed to the normal flow of the game
                    if (arrayParam[1].equals("easy") || arrayParam[1].equals("medium") ||
                            arrayParam[1].equals("hard") || arrayParam[1].equals("user") ||
                            arrayParam[2].equals("easy") || arrayParam[2].equals("medium") ||
                            arrayParam[2].equals("hard") || arrayParam[2].equals("user")) {

                        switch (arrayParam[1]) {
                            case "easy":
                                playerOne = new PlayerRobot(); // default constructor: first player and easy level
                                break;
                            case "medium":
                                playerOne = new PlayerRobot(Game.MEDIUM);
                                break;
                            case "hard":
                                playerOne = new PlayerRobot(Game.HARD);
                                break;
                            case "user":
                                playerOne = new PlayerHuman(); // default constructor: second player
                                break;
                        }

                        switch (arrayParam[2]) {
                            case "easy":
                                playerTwo = new PlayerRobot(false);
                                break;
                            case "medium":
                                playerTwo = new PlayerRobot(false, Game.MEDIUM);
                                break;
                            case "hard":
                                playerTwo = new PlayerRobot(false, Game.HARD);
                                break;
                            case "user":
                                playerTwo = new PlayerHuman(false);
                                break;
                        }

                        GameTTTBoard board = new GameTTTBoard();
                        board.draw(scanner); // draw the empty board

                        play(scanner, board, playerOne, playerTwo); // game starts here!

                        board.clear();  // reset the board
                        resetGame();    // reset the game parameters

                        strCommand = null;
                        strParam = null;
                        playerOne = null;
                        playerTwo = null;

                    } else {
                        System.out.println("Bad parameters!");
                    }
                }
            } else if (strCommand.equals("exit")) {
                break; // break out of the do-while loop, and end the game
            } else {
                System.out.println("Bad parameters!");
            }
        } while (true);

        scanner.close();
    }

    /**
     * The game main logic
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     * @param playerOne the first player
     * @param playerTwo the second player
     */
    private void play(Scanner scanner, GameTTTBoard board, Player playerOne, Player playerTwo) {
        // infinite loop until one side win or draw
        while (true) {
            if (this.isPlayerOneMove) {
                playerOne.MoveNext(scanner, board);
            } else {
                playerTwo.MoveNext(scanner, board);
            }

            board.draw(scanner);

            this.state = checkState(board, playerOne, playerTwo); // check game state
            switch (this.state) {
                case X_WIN:
                    System.out.println("X wins");
                    return;
                case O_WIN:
                    System.out.println("O wins");
                    return;
                case DRAW:
                    System.out.println("Draw");
                    return;
                case NOT_FINISHED:
                    isPlayerOneMove = !isPlayerOneMove; // continue playing the game by switching the side
                    break;
            }
        }
    }

    /**
     * check the game state
     *
     * @param board game board
     * @param playerOne first player 'X'
     * @param playerTwo second player 'O'
     * @return game state
     */
    private int checkState(GameTTTBoard board, Player playerOne, Player playerTwo) {
        if (board.checkWin(playerOne)) {
            return X_WIN;
        }

        if (board.checkWin(playerTwo)) {
            return O_WIN;
        }

        boolean isBoardFull = true;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getBoard()[row * SIZE + col].getType() == CELL_EMPTY) {
                    isBoardFull = false;
                    break; // break out of the inner for loop
                }

                if (!isBoardFull) { // break out of the outer for loop
                    break;
                }
            }
        }

        return isBoardFull? DRAW : NOT_FINISHED;
    }
}

abstract class Player implements Cloneable{
    protected boolean isFirst; // first player flag
    protected int type;
    protected Move curMove;
    protected Move nextMove;
    protected ArrayList<Move> moves;

    public Player() {
        this.isFirst = true;
        this.type = Game.CELL_X;
        this.curMove = new Move();
        this.nextMove = new Move();
        this.moves = new ArrayList<Move>();
    }

    public Player(boolean isFirst) {
        this.isFirst = isFirst;
        this.type = isFirst ? Game.CELL_X : Game.CELL_O;
        this.curMove = new Move();
        this.nextMove = new Move();
        this.moves = new ArrayList<Move>();
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Move getCurMove() {
        return this.curMove;
    }

    public void setCurMove(Move move) {
        this.curMove = (Move) move.clone();
    }

    /**
     * Get the opponent's type     *
     * @return the opponent's type
     */
    public int getOppType() {
        switch (this.type) {
            case Game.CELL_X:
                return Game.CELL_O;
            case Game.CELL_O:
                return Game.CELL_X;
            default:
                return Game.CELL_EMPTY;
        }
    }

    public ArrayList<Move> getMoves() {
        return this.moves;
    }

    /**
     * player make one move
     * @param move current move
     */
    public void makeMove(Move move) {
        this.curMove = move;
        this.moves.add(move);
    }

    @Override
    public String toString() {
        String player = this.isFirst ? "playerOne" : "playerTwo";
        return player + " at " + this.curMove;
    }

    /**
     * implement deep clone
     * @return the cloned cell ojbect
     */
    public Object clone() {
        Player copy = null;

        try {
            copy = (Player) super.clone();
            copy.curMove = (Move) this.curMove.clone();
            copy.nextMove = (Move) this.nextMove.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }

    abstract protected void MoveNext(Scanner scanner, GameTTTBoard board);
}

class PlayerHuman extends Player {
    public PlayerHuman() {
        super();
    }

    public PlayerHuman(boolean isFirst) {
        super(isFirst);
    }

    @Override
    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    protected void MoveNext(Scanner scanner, GameTTTBoard board) {
        int row; // x coordinate
        int col; // y coordinate

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

            if (board.getBoard()[row * Game.SIZE + col].getType() != Game.CELL_EMPTY) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                Cell cell = this.isFirst() ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
                board.getBoard()[row * Game.SIZE + col] = cell;

                moves.add(new Move(0, cell));

                break; // break out of the while loop when human enters the valid move
            }
        }
    }
}

class PlayerRobot extends Player {
    private final int level;

    public PlayerRobot() {
        super();
        this.level = Game.EASY;
    }

    public PlayerRobot(boolean isFirst) {
        super(isFirst);
        this.level = Game.EASY;
    }

    public PlayerRobot(int level) {
        super();
        this.level = level;
    }

    public PlayerRobot(boolean isFirst, int level) {
        super(isFirst);
        this.level = level;
    }

    @Override
    protected void MoveNext(Scanner scanner, GameTTTBoard board) {
        switch (this.level) {
            case Game.EASY:
                System.out.println("Making move level \"easy\"");
                MoveNextEasy(scanner, board);
                break;
            case Game.MEDIUM:
                System.out.println("Making move level \"medium\"");
                MoveNextMedium(scanner, board);
                break;
            case Game.HARD:
                System.out.println("Making move level \"hard\"");
                MoveNextHard(scanner, board);
                break;
        }
    }

    /**
     * robot makes the move at the easy level
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextEasy(Scanner scanner, GameTTTBoard board) {
        Random rand = new Random();
        int row;
        int col;

        // make the random move, and loop until robot makes valid move
        do {
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        } while (board.getBoard()[row * Game.SIZE + col].getType() != Game.CELL_EMPTY);

        Cell cell = this.isFirst ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
        // this.nextMove = new Move(cell);
        board.getBoard()[row * Game.SIZE + col] = cell; // make the robot move

        moves.add(new Move(0, cell));
    }

    /**
     * robot makes the move at the medium level
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextMedium(Scanner scanner, GameTTTBoard board) {
        Cell oppCell = isFirst() ? new Cell(Game.CELL_O) : new Cell(Game.CELL_X); // opponent player

        /**
         * If it already has two in a row and can win with one further move, it does so.
         */
        // check the first row
        if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[1].getType() == getType() &&
                board.getBoard()[2].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[1].getType() == Game.CELL_EMPTY &&
                board.getBoard()[2].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[1].getType() == getType() &&
                board.getBoard()[2].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second row
        else if (board.getBoard()[3].getType() == getType() &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[5].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getType() == getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[5].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third row
        else if (board.getBoard()[6].getType() == getType() &&
                board.getBoard()[7].getType() == getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getType() == getType() &&
                board.getBoard()[7].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getType() == getType() &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the first col
        if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[3].getType() == getType() &&
                board.getBoard()[6].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[3].getType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[3].getType() == getType() &&
                board.getBoard()[6].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second col
        else if (board.getBoard()[1].getType() == getType() &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[7].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getType() == getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[7].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third col
        else if (board.getBoard()[2].getType() == getType() &&
                board.getBoard()[5].getType() == getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == getType() &&
                board.getBoard()[5].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getType() == getType() &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the diagonal
        else if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[8].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the reverse diagonal
        else if (board.getBoard()[2].getType() == getType() &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[6].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == getType() &&
                board.getBoard()[6].getType() == getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        /**
         * If its opponent can win with one move, it plays the move necessary to block this.
         */
        // check the first row
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[1].getType() == oppCell.getType() &&
                board.getBoard()[2].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[1].getType() == Game.CELL_EMPTY &&
                board.getBoard()[2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[1].getType() == oppCell.getType() &&
                board.getBoard()[2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second row
        else if (board.getBoard()[3].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[5].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[5].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third row
        else if (board.getBoard()[6].getType() == oppCell.getType() &&
                board.getBoard()[7].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getType() == oppCell.getType() &&
                board.getBoard()[7].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the first col
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[3].getType() == oppCell.getType() &&
                board.getBoard()[6].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[3].getType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[3].getType() == oppCell.getType() &&
                board.getBoard()[6].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second col
        else if (board.getBoard()[1].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[7].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[7].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third col
        else if (board.getBoard()[2].getType() == oppCell.getType() &&
                board.getBoard()[5].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == oppCell.getType() &&
                board.getBoard()[5].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the diagonal
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[8].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[0] = cell; // make the robot move
            moves.add(new Move(0, cell));
        }

        // check the reverse diagonal
        else if (board.getBoard()[2].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[6].getType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == oppCell.getType() &&
                board.getBoard()[4].getType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getType() == oppCell.getType() &&
                board.getBoard()[6].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            // this.nextMove = new Move(cell);
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        } else {
            MoveNextEasy(scanner, board); // Otherwise, it makes a easy random move.
        }
    }

    /**
     * robot makes the move at the hard level
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextHard(Scanner scanner, GameTTTBoard board) {
        // finding the ultimate move that favors the computer
        Move bestMove = miniMaxMove(board, this);

        int row = bestMove.getCell().getRow();
        int col = bestMove.getCell().getCol();

        Cell cell = this.isFirst? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
        // this.nextMove = new Move(cell);
        board.getBoard()[row * Game.SIZE + col] = cell; // make the robot move
    }

    /**
     * Implement the minimax algorithm to find the next best move
     *
     * @param board  game board
     * @param player current player
     * @return next best move
     */
    public Move miniMaxMove(GameTTTBoard board, Player player) {
        ArrayList<Cell> emptyCells = board.getEmptyCells();
        ArrayList<Move> moves = new ArrayList<>(); // an array to collect all the moves

        // checks for the terminal states such as win, lose, and draw and returning a correspoonding value
        if (board.checkWin(player)) {
            if (player.getClass().getName() == "PlayerHuman") {
                Move move = (Move) (player.getCurMove().clone());
                move.setScore(Game.SCORE_LOSS);

                return move;
            } else {
                Move move = (Move) (player.getCurMove().clone());
                move.setScore(Game.SCORE_WIN);
                return move;
            }
        } else if (emptyCells.size() == 0) {
            return new Move();
        }

        // loop through available spots
        for (Cell spot : emptyCells) {
            Move move = new Move(spot);

            // set the type of the empty spot to the current player's type
            board.getBoard()[spot.getRow() * Game.SIZE + spot.getCol()].setType(player.getType());

            // collect the score resulted from calling minimax on the opponent of the current player
            Move result = null;
            // Cell oppCell = new Cell(player.getCurMove().getCell().getRow(), player.getCurMove().getCell().getCol(), player.getOppType());

            if (player.getClass().getName() == "PlayerRobot") {
                result = miniMaxMove(board, new PlayerHuman(player.isFirst() ));

            } else {
                result = miniMaxMove(board, new PlayerRobot(player.isFirst(), Game.HARD));
            }

            move.setScore(result.getScore());

            // reset the type of the spot to empty
            board.getBoard()[spot.getRow() * Game.SIZE + spot.getCol()].setType(Game.CELL_EMPTY);

            moves.add(move); // push the object to the ArrayList
        }

        // if it is the computer's turn loop over the moves and choose the move with the highest score
        Move bestMove = new Move();
        if (player.getClass().getName() == "PlayerRobot") {
            int bestScore = -10000;
            for (Move move : moves) {
                if (move.getScore() > bestScore) {
                    bestScore = move.getScore();
                    bestMove = move;
                }
            }
        } else { // else loop over the moves and choose the move with the lowest score
            int bestScore = 10000;
            for (Move move : moves) {
                if (move.getScore() < bestScore) {
                    bestScore = move.getScore();
                    bestMove = move;
                }
            }
        }

        return bestMove; // return the chosen move from the array to the higher depth
    }
}

class Cell implements Cloneable{
    private int row;
    private int col;
    private int type;

    public Cell() {
        this.row = -1;
        this.col = -1;
        this.type = Game.CELL_EMPTY;
    }

    public Cell(int type) {
        this.row = -1;
        this.col = -1;
        this.type = type;
    }

    public Cell(int row, int col, int type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public Cell(Cell cell) {
        this.row = cell.getRow();
        this.col = cell.getCol();
        this.type = cell.getType();
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void draw() {
        System.out.print(this);
    }

    // @Override
    public String toString() {
        String type;

        switch (this.type) {
            case Game.CELL_X:
                type = " X";
                break;
            case Game.CELL_O:
                type = " O";
                break;
            default:
                // type = "_ ";
                type = "  ";
                break;
        }

        // return type + " @ (" + this.row + "," + this.col + ")";
        return type;
    }

    /**
     * implement deep clone
     * @return the cloned cell ojbect
     */
    public Object clone() {
        Cell copy = null;

        try {
            copy = (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }
}

abstract class Board {
    protected int size;         // number cells on the horizontal or vertical direction for a squared board
    protected Cell[] board;

    public Board(int size) {
        this.size = size;
        this.board = new Cell[this.size * this.size];

        initBoardCells();
    }

    /**
     * an empty constructor to compile sub-class GameTTTBoard successfully
     * @param strBoard
     */
    public Board(String strBoard) {}

    /**
     * helper function to constructing the cells on the game board
     */
    protected void initBoardCells() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i *this.size + j] = new Cell(i, j, Game.CELL_EMPTY);
            }
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Cell[] getBoard() {
        return this.board;
    }

    /**
     * get the empty cells on the board
     *
     * @return an ArrayList with empty cells
     */
    public ArrayList<Cell> getEmptyCells() {
        ArrayList<Cell> emptyCells = new ArrayList<>();

        for (Cell cell : this.board) {
            if (cell.getType() == Game.CELL_EMPTY) {
                emptyCells.add(cell);
            }
        }

        return emptyCells;
    }

    /**
     * reset the board for a new game
     *
     */
    public void clear() {
        for (Cell cell : this.board) {
            cell.setType(Game.CELL_EMPTY);;
        }
    }

    /**
     * draw the board onto the screen
     *
     */
    public void draw(Scanner scanner) {
        System.out.print(this);
    }

    @Override
    public String toString() {
        String strBoard = "";

        strBoard += "---------\n";

        for (int i = 0; i < Game.SIZE; i++) {
            strBoard += "|";

            for (int j = 0; j < Game.SIZE; j++) {
                strBoard += this.board[i * Game.SIZE + j];
            }

            strBoard += " |\n";
        }

        strBoard += "---------\n";

        return strBoard;
    }

    abstract public String getStrGameBoard();
}

/**
 * utility class providing various common methods for ease use
 */
class JUtil {
    /**
     * check if a positive number is the perfect square number
     * @param num a positive number
     * @return true if it is the perfect square number
     */
    public static boolean isPerfectSquareNumber(int num) {
        double sqrtNum = Math.sqrt(num);

        return (sqrtNum - Math.floor(sqrtNum) == 0);
    }
}

class GameTTTBoard extends Board {
    protected int numOfX;       // track the number of X cell on the board
    protected int numOfO;       // track the number of O cell on the board
    protected int numOfEmpty;   // track the number of EMPTY cell on the board

    protected int state;

    public GameTTTBoard() {
        super(Game.SIZE);

        this.numOfX = 0;
        this.numOfO = 0;
        this.numOfEmpty = this.size * this.size;

        this.state = Game.NOT_FINISHED;
    }

    public GameTTTBoard(String strBoard) {
        super(strBoard);

        this.size = (int) Math.sqrt(strBoard.length());
        this.board = new Cell[this.size * this.size];

        initBoardCells();

        int i = 0;
        for (char ch : strBoard.toCharArray()) {
            switch(ch) {
                case 'X':
                    this.board[i++].setType(Game.CELL_X);
                    break;
                case 'O':
                    this.board[i++].setType(Game.CELL_O);
                    break;
                case '_':
                    this.board[i++].setType(Game.CELL_EMPTY);
                    break;
                default:
                    break;
            }
        }

        calcNumOfCellType();

        this.state = Game.NOT_FINISHED;
    }

    /**
     * Get the initial game board in String format from the standard input
     * @return the valid game board in String format
     */
    public String getStrGameBoard() {
        System.out.print("Input the starting game board: > ");

        Scanner scanner = new Scanner(System.in);
        String strBoard = scanner.nextLine();

        int len = strBoard.length();

        /*
         * check:
         *    1) if the length of the game board string is a perfect squared number
         *    2) if the board cells are valid
         */
        while (!JUtil.isPerfectSquareNumber(len) && !checkBoardCells(strBoard)) {
            System.out.println("\nPlease input the correct game board string!\n");

            strBoard = scanner.nextLine();
            len = strBoard.length();
        }

        scanner.close();

        return strBoard;
    }

    /**
     * check the validity of the board cells
     * @param strBoard game board in String format
     * @return true if the game board is valid, false otherwise
     */
    private boolean checkBoardCells(String strBoard) {
        for (char cell : strBoard.toUpperCase().toCharArray()) {
            if (cell != 'X' || cell != 'O' || cell != '_') {
                return false;
            }
        }

        return true;
    }

    public int getNumOfX() {
        return this.numOfX;
    }

    public void setNumOfX(int num) {
        this.numOfX = num;
    }

    public int getNumOfO() {
        return this.numOfO;
    }

    public void setNumOfO(int num) {
        this.numOfO = num;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * helper method: calculate the number of different types respectively
     */
    private void calcNumOfCellType() {
        for (Cell cell : this.board) {
            this.numOfX += cell.getType() == Game.CELL_X ? 1 : 0;
            this.numOfO += cell.getType() == Game.CELL_O ? 1 : 0;
        }

        this.numOfEmpty = this.size * this.size - (this.numOfX + this.numOfO);
    }

    /**
     * check if player wins the game
     *
     * @param player player to check if wins
     * @return true if this player wins otherwise false
     */
    public boolean checkWin(Player player) {
        int playerType = player.getType();

        return (this.board[0].getType() == playerType && this.board[1].getType() == playerType && this.board[2].getType() == playerType) ||
                (this.board[3].getType() == playerType && this.board[4].getType() == playerType && this.board[5].getType() == playerType) ||
                (this.board[6].getType() == playerType && this.board[7].getType() == playerType && this.board[8].getType() == playerType) ||
                (this.board[0].getType() == playerType && this.board[3].getType() == playerType && this.board[6].getType() == playerType) ||
                (this.board[1].getType() == playerType && this.board[4].getType() == playerType && this.board[7].getType() == playerType) ||
                (this.board[2].getType() == playerType && this.board[5].getType() == playerType && this.board[8].getType() == playerType) ||
                (this.board[0].getType() == playerType && this.board[4].getType() == playerType && this.board[8].getType() == playerType) ||
                (this.board[2].getType() == playerType && this.board[4].getType() == playerType && this.board[6].getType() == playerType);
    }
}

class Move implements Cloneable{
    private int score;
    private Cell cell;

    public Move() {
        this.score = Game.SCORE_DRAW;
        this.cell = new Cell();
    }

    public Move(Cell cell) {
        this.score = Game.SCORE_DRAW;
        this.cell = (Cell) cell.clone();
    }

    public Move(int score, Cell cell) {
        this.score = score;
        this.cell = (Cell) cell.clone();
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = (Cell) cell.clone();
    }

    // @Override
    public String toString() {
        return "[" + this.cell + ", score: " + this.score + "]";
    }

    public Object clone() {
        Move copy = null;

        try {
            copy = (Move) super.clone();
            copy.cell = (Cell) this.cell.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }
}
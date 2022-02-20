// package tictactoe;

import java.util.Scanner;
import java.util.Random;

/**
 * The 5th development stage of game tic-tac-toe.
 *
 * Step 1: reimplement the game with OOP approach.
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
        // write your code here
        Game game = new Game();
        game.start();
    }
}

class Game {
    /**
     * Define the constants
     */
    // number of cells in a row
    final static int SIZE = 3;

    // which cell in the current position
    final static int EMPTY_CELL = 0;
    final static int X_CELL = 1;
    final static int O_CELL = 2;

    // game state
    final static int INVALID = -1;
    final static int DRAW = 0;
    final static int X_WIN = 1;
    final static int O_WIN = 2;
    final static int NOT_FINISHED = 3;

    // who is playing now
    final static int ROBOT = 0;
    final static int HUMAN = 1;

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    final static int HARD = 2;
    static int player1Level = EASY; // initialize the level for robot 1 to be easy
    static int player2Level = EASY; // initialize the level for robot 2 to be easy

    // minmax function scores
    final static int WIN_SCORE = 10;
    final static int LOSS_SCORE = -10;
    final static int DRAW_SCORE = 0;

    /**
     * member variables
     */

    private boolean isPlayer1Move; // who is moving now
    private int fc; // keep track of the function calls of the minmax function

    public Game() {
        this.isPlayer1Move = true;
        this.fc = 0;
    }

    /**
     * The main control loop of the game
     */
    protected void start() {
        Scanner scanner = new Scanner(System.in);

        String strCommand; // command string: "start", "exit"
        String strParam; // parameter string
        Player player1 = null; // first player
        Player player2 = null; // second player

        do {
            System.out.print("Input command: > ");

            strCommand = scanner.next();
            if (strCommand.equals("start")) {
                strParam = scanner.nextLine();

                // When using the method nextLine() to get the rest of the input, the array
                // length
                // is 3 in this case. And the first element of this array is " ".
                String[] arrayParam = strParam.split(" ");

                if (arrayParam.length != 3) {
                    System.out.println("Bad parameters!");
                } else { // correct inputs and proceed the normal flow of the game
                    if (arrayParam[1].equals("easy") || arrayParam[1].equals("medium") ||
                            arrayParam[1].equals("hard") || arrayParam[1].equals("user") ||
                            arrayParam[2].equals("easy") || arrayParam[2].equals("medium") ||
                            arrayParam[2].equals("hard") || arrayParam[2].equals("user")) {

                        switch (arrayParam[1]) {
                            case "easy":
                                player1 = new PlayerRobot(); // default constructor: first player and easy level
                                break;
                            case "medium":
                                player1 = new PlayerRobot(Game.MEDIUM);
                                break;
                            case "hard":
                                player1 = new PlayerRobot(Game.HARD);
                                break;
                            case "user":
                                player1 = new PlayerHuman(); // default constructor: first player
                                break;
                        }

                        switch (arrayParam[2]) {
                            case "easy":
                                player2 = new PlayerRobot(false);
                                break;
                            case "medium":
                                player2 = new PlayerRobot(false, Game.MEDIUM);
                                break;
                            case "hard":
                                player2 = new PlayerRobot(false, Game.HARD);
                                break;
                            case "user":
                                player2 = new PlayerHuman();
                                ;
                                break;
                        }

                        GameTTTBoard board = new GameTTTBoard();
                        board.draw(scanner); // draw the empty board
                        play(scanner, board, player1, player2); // game playing starts here!
                        board.clear(); // reset the board

                    } else {
                        System.out.println("Bad parameters!");
                    }
                }
            } else if (strCommand.equals("exit")) {
                break; // break out of the do-while loop, and game ends
            } else {
                System.out.println("Bad parameters!");
            }
        } while (true);

        scanner.close();
    }

    /**
     * The game logic
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     * @param player1 the first player
     * @param player2 the second player
     */
    private void play(Scanner scanner, Board board, Player player1, Player player2) {
        int state = INVALID; // initialize the game state

        // infinite loop until one side win or draw
        while (true) {
            // System.out.println("*** " + player1 + "***");

            if (this.isPlayer1Move) {
                player1.MoveNext(scanner, board);
            } else {
                // System.out.println("*** " + player2 + "***");

                player2.MoveNext(scanner, board);
            }
            
            board.draw(scanner);

            state = checkState(board, player1, player2); // check game state
            switch (state) {
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
                    isPlayer1Move = !isPlayer1Move; // continue playing the game by switching the side
                    break;
            }
        }
    }

    /**
     * check who wins the game
     * 
     * @param board  game board
     * @param player player to check
     * @return true if wins otherwise false
     */
    private boolean checkWin(Board board, Player player) {
        // System.out.printf("*** board -> (%s), player -> (%d) *** \n",
        // Arrays.toString(board), player);
        board.boardConversion();
        Cell[] board_1d = board.getBoard1d();
        int playerType = player.getCell().getType();

        return (board_1d[0].getType() == playerType && board_1d[1].getType() == playerType
                && board_1d[2].getType() == playerType) ||
                (board_1d[3].getType() == playerType && board_1d[4].getType() == playerType
                        && board_1d[5].getType() == playerType)
                ||
                (board_1d[6].getType() == playerType && board_1d[7].getType() == playerType
                        && board_1d[8].getType() == playerType)
                ||
                (board_1d[0].getType() == playerType && board_1d[3].getType() == playerType
                        && board_1d[6].getType() == playerType)
                ||
                (board_1d[1].getType() == playerType && board_1d[4].getType() == playerType
                        && board_1d[7].getType() == playerType)
                ||
                (board_1d[2].getType() == playerType && board_1d[5].getType() == playerType
                        && board_1d[8].getType() == playerType)
                ||
                (board_1d[0].getType() == playerType && board_1d[4].getType() == playerType
                        && board_1d[8].getType() == playerType)
                ||
                (board_1d[2].getType() == playerType && board_1d[4].getType() == playerType
                        && board_1d[6].getType() == playerType);
    }

    /**
     * check the game state
     *
     * @param board game board
     * @return game state
     */
    private int checkState(Board board, Player player1, Player player2) {
        if (checkWin(board, player1)) {
            return X_WIN;
        }

        if (checkWin(board, player2)) {
            return O_WIN;
        }

        boolean isBoardFull = true;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getBoard2d()[row][col].getType() == Game.EMPTY_CELL) {
                    isBoardFull = false;
                    break; // break out of the inner for loop
                }

                if (!isBoardFull) { // need to break out of the outer for loop
                    break;
                }
            }
        }

        return isBoardFull ? DRAW : NOT_FINISHED;
    }
}

abstract class Player {
    protected boolean isFirst; // first player flag
    protected Cell cell;
    protected Move currMove;
    protected Move nextMove;

    public Player() {
        this.isFirst = true;
        this.cell = new Cell(Game.X_CELL);

        this.currMove = null;
        this.nextMove = null;
    }

    public Player(boolean isFirst) {
        this.isFirst = isFirst;
        this.cell = isFirst ? new Cell(Game.X_CELL) : new Cell(Game.O_CELL);
        this.currMove = null;
        this.nextMove = null;

    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Move getCurrMove() {
        return this.currMove;
    }

    // @Override
    public String to_String() {
        return this.isFirst ? "player1" : "player2";
    }

    abstract protected void MoveNext(Scanner scanner, Board board);
}

class PlayerHuman extends Player {
    public PlayerHuman() {
        super();
    }

    @Override
    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    protected void MoveNext(Scanner scanner, Board board) {
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

            if (board.getBoard2d()[row][col].getType() != Game.EMPTY_CELL) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                Cell cell = this.isFirst() ? new Cell(row, col, Game.X_CELL) : new Cell(row, col, Game.O_CELL);
                board.getBoard2d()[row][col] = cell;
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
    protected void MoveNext(Scanner scanner, Board board) {
        switch (this.level) {
            case Game.EASY:
                System.out.println("Making move level \"easy\"");
                MoveNextEasy(scanner, board);
                break;
            case Game.MEDIUM:
                System.out.println("Making move level \"medium\"");
                MoveNextMedium(scanner, board);
                break;
        }
    }

    /**
     * robot makes the move at the easy level
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextEasy(Scanner scanner, Board board) {
        Random rand = new Random();
        int row;
        int col;

        // make the random move, and loop until robot makes valid move
        do {
            row = rand.nextInt(board.getSize());
            col = rand.nextInt(board.getSize());
        } while (board.getBoard2d()[row][col].getType() != Game.EMPTY_CELL);

        Cell cell = this.isFirst ? new Cell(row, col, Game.X_CELL) : new Cell(row, col, Game.O_CELL);
        this.nextMove = new Move(cell);
        board.getBoard2d()[row][col] = cell; // make the robot move
    }

    /**
     * robot makes the move at the medium level
     * 
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextMedium(Scanner scanner, Board board) {
        Cell oppCell = isFirst() ? new Cell(Game.O_CELL) : new Cell(Game.X_CELL); // opponent player

        /**
         * If it already has two in a row and can win with one further move, it does so.
         */
        // check the first row
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[0][1].getType() == getCell().getType() &&
                board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[0][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[0][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.X_CELL) : new Cell(0, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[0][1].getType() == getCell().getType() &&
                board.getBoard2d()[0][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the second row
        if (board.getBoard2d()[1][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[1][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.X_CELL) : new Cell(1, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[1][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[1][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[1][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.X_CELL) : new Cell(1, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][0] = cell; // make the robot move
            return;
        }

        // check the third row
        if (board.getBoard2d()[2][0].getType() == getCell().getType() &&
                board.getBoard2d()[2][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[2][0].getType() == getCell().getType() &&
                board.getBoard2d()[2][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.X_CELL) : new Cell(2, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }

        // check the first col
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][0].getType() == getCell().getType() &&
                board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][0].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.X_CELL) : new Cell(1, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][0].getType() == getCell().getType() &&
                board.getBoard2d()[2][0].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the second col
        if (board.getBoard2d()[0][1].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][1].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.X_CELL) : new Cell(2, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][1].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][1].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][1].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.X_CELL) : new Cell(0, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][1] = cell; // make the robot move
            return;
        }

        // check the third col
        if (board.getBoard2d()[0][2].getType() == getCell().getType() &&
                board.getBoard2d()[1][2].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == getCell().getType() &&
                board.getBoard2d()[1][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.X_CELL) : new Cell(1, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][2].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }

        // check the diagonal
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][2].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the reverse diagonal
        if (board.getBoard2d()[0][2].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == getCell().getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][0].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == getCell().getType() &&
                board.getBoard2d()[2][0].getType() == getCell().getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }

        /**
         * If its opponent can win with one move, it plays the move necessary to block
         * this.
         */
        // check the first row
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[0][1].getType() == oppCell.getType() &&
                board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[0][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[0][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.X_CELL) : new Cell(0, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[0][1].getType() == oppCell.getType() &&
                board.getBoard2d()[0][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the second row
        if (board.getBoard2d()[1][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[1][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.X_CELL) : new Cell(1, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[1][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[1][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[1][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.X_CELL) : new Cell(1, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][0] = cell; // make the robot move
            return;
        }

        // check the third row
        if (board.getBoard2d()[2][0].getType() == oppCell.getType() &&
                board.getBoard2d()[2][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[2][0].getType() == oppCell.getType() &&
                board.getBoard2d()[2][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.X_CELL) : new Cell(2, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }

        // check the first col
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][0].getType() == oppCell.getType() &&
                board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][0].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.X_CELL) : new Cell(1, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][0].getType() == oppCell.getType() &&
                board.getBoard2d()[2][0].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the second col
        if (board.getBoard2d()[0][1].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][1].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.X_CELL) : new Cell(2, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][1].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][1].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][1].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.X_CELL) : new Cell(0, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][1] = cell; // make the robot move
            return;
        }

        // check the third col
        if (board.getBoard2d()[0][2].getType() == oppCell.getType() &&
                board.getBoard2d()[1][2].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == oppCell.getType() &&
                board.getBoard2d()[1][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.X_CELL) : new Cell(1, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][2].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }

        // check the diagonal
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.X_CELL) : new Cell(2, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][2] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][0].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][2].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.X_CELL) : new Cell(0, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][0] = cell; // make the robot move
            return;
        }

        // check the reverse diagonal
        if (board.getBoard2d()[0][2].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][0].getType() == Game.EMPTY_CELL) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.X_CELL) : new Cell(2, 0, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[2][0] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == oppCell.getType() &&
                board.getBoard2d()[1][1].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[2][0].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.X_CELL) : new Cell(1, 1, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[1][1] = cell; // make the robot move
            return;
        }
        if (board.getBoard2d()[0][2].getType() == Game.EMPTY_CELL &&
                board.getBoard2d()[1][1].getType() == oppCell.getType() &&
                board.getBoard2d()[2][0].getType() == oppCell.getType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.X_CELL) : new Cell(0, 2, Game.O_CELL);
            this.nextMove = new Move(cell);
            board.getBoard2d()[0][2] = cell; // make the robot move
            return;
        }

        MoveNextEasy(scanner, board); // Otherwise, it makes a random move.
    }

    /**
     * robot makes the move
     *
     * @param board  game board
     * 
     *               private void robotMove(int[][] board) {
     *               if (player1Level == HARD && isFirstPlayerMove || player2Level
     *               == HARD && !isFirstPlayerMove) {
     *               System.out.println("Making move level \"hard\"");
     * 
     *               robotMoveHard(board);
     * 
     *               } else if (player1Level == MEDIUM && isFirstPlayerMove ||
     *               player2Level == MEDIUM && !isFirstPlayerMove) {
     *               System.out.println("Making move level \"medium\"");
     * 
     *               if (!robotMoveMedium(board)) {
     *               robotMoveEasy(board); // Otherwise, it makes a random move.
     *               }
     * 
     *               } else {
     *               System.out.println("Making move level \"easy\"");
     *               robotMoveEasy(board);
     *               }
     *               }
     * 
     *               robot makes the move at the hard level
     *
     * @param board  game board
     * 
     *               private void MoveNextHard(int[][] board) {
     *               // finding the ultimate play on the game that favors the
     *               computer
     *               Move bestSpot = minMaxMove(boardConversion(board), ROBOT);
     * 
     *               System.out.printf("bestSpot's index -> (%d)\n",
     *               bestSpot.index);
     * 
     *               int row = bestSpot.index / SIZE;
     *               int col = row * SIZE + bestSpot.index % SIZE;
     *               board[row][col] = isFirstPlayerMove ? XCELL : OCELL; // make
     *               the robot move
     *               }
     * 
     *               /**
     *               the main minmax function
     *
     * @param board  game board
     * @param player current player: ROBOT or HUMAN
     * 
     *               private Move minMaxMove(int[] board, int player) {
     *               String availSpots = emptyIndexies(board);
     * 
     *               System.out.printf("*** availSpots -> (%s) ***\n", availSpots);
     * 
     *               // an array to collect all the moves
     *               LinkedList<Move> moves = new LinkedList<>();
     * 
     *               // checks for the terminal states such as win, lose, and draw
     *               // and returning a value accordingly
     *               if (checkWin(board, HUMAN)) {
     *               System.out.println("*** checkWin -> Human ***");
     *               return new Move(LOSS_SCORE);
     *               } else if (checkWin(board, ROBOT)) {
     *               System.out.println("*** checkWin -> ROBOT ***");
     *               return new Move(WIN_SCORE);
     *               } else if (availSpots.length() == 0) {
     *               System.out.println("*** checkWin -> DRAW ***");
     *               return new Move(DRAW_SCORE);
     *               }
     * 
     *               // loop through available spots
     *               for (char spot : availSpots.toCharArray()) {
     *               //create an object for each and store the index of that spot
     *               // that was stored as a number in the object's index key
     *               Move move = new Move();
     *               move.index = board[spot - '0'];
     * 
     *               System.out.printf("*** move.index -> (%d) ***\n", move.index);
     * 
     *               // set the empty spot to the current player
     *               board[spot - '0'] = player;
     * 
     *               // collect the score resulted from calling minimax on the
     *               opponent of the current player
     *               Move result;
     *               if (player == ROBOT) {
     *               result = minMaxMove(board, HUMAN);
     * 
     *               } else {
     *               result = minMaxMove(board, ROBOT);
     *               }
     *               move.score = result.score;
     * 
     *               //reset the spot to empty
     *               board[spot - '0'] = move.index;
     * 
     *               // push the object to the linkedlist
     *               moves.push(move);
     *               }
     * 
     *               // if it is the computer's turn loop over the moves and choose
     *               the move with the highest score
     *               Move bestMove = new Move();
     *               if (player == ROBOT) {
     *               int bestScore = -10000;
     *               for (Move move : moves) {
     *               if (move.score > bestScore) {
     *               bestScore = move.score;
     *               bestMove = move;
     *               }
     *               }
     *               } else { // else loop over the moves and choose the move with
     *               the lowest score
     *               int bestScore = 10000;
     *               for (Move move : moves) {
     *               if (move.score < bestScore) {
     *               bestScore = move.score;
     *               bestMove = move;
     *               }
     *               }
     *               }
     * 
     *               // return the chosen move (object) from the array to the higher
     *               depth
     *               return bestMove;
     *               }
     */
}

class Cell {
    private int row;
    private int col;
    private int type;

    public Cell() {
        this.row = -1;
        this.col = -1;
        this.type = Game.EMPTY_CELL;
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
        switch (this.type) {
            case Game.EMPTY_CELL:
                System.out.print("  ");
                break;
            case Game.X_CELL:
                System.out.print(" X");
                break;
            case Game.O_CELL:
                System.out.print(" O");
                break;
        }
    }

    // @Override
    public String to_String() {
        return String.format("cell (%d, %d, %d)", this.row, this.col, this.type);
    }
}

abstract class Board {
    protected int size;
    protected Cell[][] board_2d;
    protected Cell[] board_1d;
    protected int state;

    protected int numOfX; // keep track of the number of X on the initial board
    protected int numOfO; // keep track of the number of O on the initial board

    public Board() {
        this.size = Game.SIZE;

        this.board_2d = new Cell[Game.SIZE][Game.SIZE];
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                this.board_2d[i][j] = new Cell(i, j, Game.EMPTY_CELL);
            }
        }

        this.board_1d = new Cell[Game.SIZE * Game.SIZE];
        boardConversion();

        this.state = Game.NOT_FINISHED;

        this.numOfX = 0;
        this.numOfO = 0;
    }

    public Board(int size) {
        this.size = size;

        this.board_2d = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board_2d[i][j] = new Cell(i, j, Game.EMPTY_CELL);
            }
        }

        this.board_1d = new Cell[size * size];
        boardConversion();

        this.state = Game.NOT_FINISHED;

        this.numOfX = 0;
        this.numOfO = 0;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Cell[][] getBoard2d() {
        return this.board_2d;
    }

    public void setBoard2d(Cell[][] board) {
        this.board_2d = board;
    }

    public Cell[] getBoard1d() {
        return this.board_1d;
    }

    public void setBoard1d(Cell[] board) {
        this.board_1d = board;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    abstract public void draw(Scanner scanner);

    /**
     * convert a two-dimensional board to one dimension
     *
     * @return game board in one dimension
     */
    public void boardConversion() {

        int i = 0;
        for (Cell[] row : this.board_2d) {
            for (Cell cell : row) {
                board_1d[i++] = cell;
            }
        }
    }

    /**
     * get the available spots on the board
     *
     * @param board game board
     * @return a string with available spots
     */
    private String emptyIndexies(int[] board) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            if (board[i] == Game.EMPTY_CELL) {
                sb.append(i);
            }
        }

        return sb.toString();
    }

    /**
     * reset the board for the next game
     *
     */
    public void clear() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.board_2d[row][col].setType(Game.EMPTY_CELL);
            }
        }

        boardConversion();
    }

    @Override
    public String toString() {
        return null;
    }

    /*
     * @Deprecated
     * 
     * Setup the board with initial string input from System.in.
     * Note: this method actually used in the first stage of this game development,
     * and it's not used anymore.
     *
     * @param scanner java.util.Scanner
     * 
     * @param board game board
     * 
     * private void setupBoard(Scanner scanner) {
     * int row; // x coordinate
     * int col; // y coordinate
     * 
     * System.out.print("Enter the cells: > ");
     * String strCells = scanner.next();
     * 
     * row = 0;
     * 
     * // print out the initial board and set up the tic-tac-toe board
     * System.out.println("---------");
     * 
     * for (int k = 0; k < strCells.length(); k += 3) {
     * System.out.print('|');
     * 
     * for (col = 0; col < this.size; col++) {
     * if (strCells.charAt(k + col) == '_') {
     * System.out.print("  ");
     * this.board_2d[row][col] = Game.EMPTY_CELL;
     * } else {
     * System.out.print(" " + strCells.charAt(k + col));
     * if (strCells.charAt(k + col) == 'X') {
     * this.board_2d[row][col] = Game.X_CELL;
     * this.numOfX++;
     * } else {
     * this.board_2d[row][col] = Game.O_CELL;
     * this.numOfO++;
     * }
     * }
     * }
     * 
     * System.out.println(" |");
     * row++; // move to the next row
     * }
     * 
     * System.out.println("---------");
     * }
     */
}

class GameTTTBoard extends Board {
    public GameTTTBoard() {
        super(Game.SIZE);
    }

    /**
     * draw the board onto the screen
     *
     */
    @Override
    public void draw(Scanner scanner) {
        System.out.println("---------");

        for (int row = 0; row < this.size; row++) {
            System.out.print('|');

            for (int col = 0; col < this.size; col++) {
                this.board_2d[row][col].draw();
            }

            System.out.println(" |");
        }

        System.out.println("---------");
    }

    public int evaluate() {
        return 0;
    }
}

class Move {
    private int score;
    private Cell cell;

    public Move() {
        this.score = Game.DRAW_SCORE;
        this.cell = new Cell();
    }

    public Move(Cell cell) {
        this.score = Game.DRAW_SCORE;
        this.cell = cell;
    }

    public Move(int score, Cell cell) {
        this.score = score;
        this.cell = cell;
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
        this.cell = cell;
    }
}
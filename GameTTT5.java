// package tictactoe;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Random;

/**
 * The 5th development stage of the game tic-tac-toe.
 *
 * Step 1: re-code the game with OOP approach, and re-test the code such that it can pass all the tests on the stage 4/5.
 *
 * step 2: rewrite the program with the following sub-steps:
 *      - 2.1: use a flattened 1D game board  to represent 2D board;
 *      - 2.2: clean up the code to make it more compact and beautiful
 *      - 2.3: think more deeply about object oriented design, and apply it into the code
 *      - 2.4：keep improving the code with major or minor updates
 *      - 2.5: pass the tests in the jetbrains academy again
 *      - 2.6: debug the recurisve minimax algorithm, and not give up easily
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
 * 
 * Done on 2022-04-03
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
     * constants definition
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

    // who is playing
    final static int ROBOT = 1;
    final static int HUMAN = 2;

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    final static int HARD = 2;

    // score for the minmax function
    final static int SCORE_WIN = 10;
    final static int SCORE_LOSS = -10;
    final static int SCORE_DRAW = 0;
    final static int SCORE_MAX = 10000;
    final static int SCORE_MIN = -10000;

    /**
     * member variables
     */
    private Player playerOne;
    private Player playerTwo;

    private Board board;

    private boolean isPlayerOneMove;    // who is moving now
    private int state;                  // game state

    public Game() {
        resetGame();
    }

    /**
     * reset the game parameters
     */
    private void resetGame() {
        this.playerOne = null;
        this.playerTwo = null;
        this.board = null;

        this.isPlayerOneMove = true;
        this.state = Game.INVALID;     
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    /**
     * Get current player
     * @return player who is making the current move
     */
    public Player getCurPlayer() {
        return this.isPlayerOneMove ? this.playerOne : this.playerTwo;
    }

    /**
     * Get the other player
     * @return player who is not making the move
     */
    public Player getOtherPlayer() {
        return this.isPlayerOneMove ? this.playerTwo : this.playerOne;
    }

    /**
     * test the functionality of minimaxMove method
     * 
     * visualize the robot moves starting from the sample game state
     * 
                                   O |   | X
                                   ---------
                                   X |   | X
                                   ---------
                                     | O | O
                             //       ||        \\
                O | X | X          O |   | X        O |   | X
                ---------          ---------        ---------
                X |   | X          X | X | X        X |   | X
                ---------          ---------        ---------
                  | O | O            | O | O        X | O | O
              //          \\                     //          \\
        O | X | X          O | X | X        O | O | X       O |   | X
        ---------          ---------        ---------       ---------
        X | O | X          X |   | X        X |   | X       X | O | X
        ---------          ---------        ---------       ---------
          | O | O          O | O | O        X | O | O       X | O | O
                                        //
                                   O | O | X
                                   ---------
                                   X | X | X
                                   ---------
                                   O | O | O
     *
     */
    public void testMiniMax() {
        String strBoard = "O_XX_X_OO";
        
        this.board = new GameTTTBoard(strBoard);        // construct the board object with the inital board setup

        this.playerOne = new PlayerRobot(Game.HARD);    // Robot player moves first in hard level
        this.playerTwo = new PlayerHuman(false);        // Human plaer moves second

        for (Cell cell : board.getBoard()) {
            switch(cell.getCellType()) {
                case Game.CELL_X:
                    this.playerOne.makeMove(new Move(0, cell));
                    this.playerTwo.setMoving(false);
                    break;
                case Game.CELL_O:
                    this.playerTwo.makeMove(new Move(0, cell));
                    this.playerOne.setMoving(false);
                    break;
                default:
                    break;
            }
        }

        // System.out.println("player one's moves: > " + playerOne.getMoves());
        // System.out.println("player two's moves: > " + playerTwo.getMoves());

        /*
        Move bestMove = ((PlayerRobot)playerOne).miniMaxMove(this.board, playerOne);
        System.out.println("next best move: " + bestMove);
        System.out.println("number of minimax function calls: " + ((PlayerRobot)playerOne).getFc());
        System.out.println("depth of minimax game tree: " + ((PlayerRobot)playerOne).getDepth());
        */
        
        // Move move = new Move(0, new Cell(1, 2, 0));
        // playerOne.makeMove(move);

        Move bestMove =  ((PlayerRobot)playerOne).getNextBestMove(this.board);
        System.out.println("The next best move is " + bestMove);

        // int bestScore = ((PlayerRobot)playerOne).miniMaxMove(this.board, 2, true);
        // System.out.printf("The best score is {%d}\n", bestScore);
    
    }

    /**
     * The main control loop of the game
     */
    protected void start() {
        Scanner scanner = new Scanner(System.in);

        String strCommand = null;   // command string: "start", "exit"
        String strParam = null;     // parameter string after command

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
                                this.playerOne = new PlayerRobot(); // default constructor: first player and easy level
                                break;
                            case "medium":
                                this.playerOne = new PlayerRobot(Game.MEDIUM);
                                break;
                            case "hard":
                                this.playerOne = new PlayerRobot(Game.HARD);
                                break;
                            case "user":
                                this.playerOne = new PlayerHuman(); // default constructor: second player
                                break;
                        }

                        switch (arrayParam[2]) {
                            case "easy":
                                this.playerTwo = new PlayerRobot(false);
                                break;
                            case "medium":
                                this.playerTwo = new PlayerRobot(false, Game.MEDIUM);
                                break;
                            case "hard":
                                this.playerTwo = new PlayerRobot(false, Game.HARD);
                                break;
                            case "user":
                                this.playerTwo = new PlayerHuman(false);
                                break;
                        }

                        this.board = new GameTTTBoard();
                        this.board.draw(scanner); // draw the empty board

                        play(scanner); // game starts here!

                        this.board.clear();  // reset the board
                        resetGame();    // reset the game parameters

                        /*
                        if (this.playerOne.getClass().getName() == "PlayerRobot") {
                            this.playerOne.resetFc();
                        }

                        if (this.playerTwo.getClass().getName() == "PlayerRobot") {
                            this.playerTwo.resetFc();
                        }
                        */

                        strCommand = null;
                        strParam = null;
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
     */
    private void play(Scanner scanner) {
        // infinite loop until one side win or draw
        while (true) {
            if (this.isPlayerOneMove) {
                this.playerOne.MoveNext(scanner, this.board);
            } else {
                this.playerTwo.MoveNext(scanner, this.board);
            }

            this.board.draw(scanner);

            this.state = checkState(); // check game state
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
                    this.isPlayerOneMove = !this.isPlayerOneMove; // continue playing the game by switching the side
                    break;
            }
        }
    }

    /**
     * check the game state
     *
     * @return game state
     */
    private int checkState() {
        if (((GameTTTBoard)this.board).checkWin(this.playerOne)) {
            return X_WIN;
        }

        if (((GameTTTBoard)this.board).checkWin(this.playerTwo)) {
            return O_WIN;
        }

        boolean isBoardFull = true;

        for (int row = 0; row < this.board.size; row++) {
            for (int col = 0; col < this.board.size; col++) {
                if (this.board.getBoard()[row * this.board.size + col].getCellType() == CELL_EMPTY) {
                    isBoardFull = false;
                    break; // break out of the inner for loop
                }

                if (!isBoardFull) { // break out of the outer for loop
                    break;
                }
            }
        }

        return isBoardFull? Game.DRAW : Game.NOT_FINISHED;
    }
}

abstract class Player implements Cloneable{
    protected boolean isFirst;      // if it's first player
    protected boolean isMoving;     // if currently making move, but this flag is not used for now

    protected int playerCellType;   // 0: CELL_EMPTY; 1: CELL_X; 2: CELL_O
    protected int playerType;       // 1: ROBOT; 2: HUMAN

    protected Move curMove;
    protected LinkedList<Move> moves;

    public Player() {
        this.isFirst = true;
        this.isMoving = false;

        this.playerCellType = Game.CELL_X;
        this.playerType = Game.ROBOT;

        this.curMove = new Move();
        this.moves = new LinkedList<Move>();
    }

    public Player(boolean isFirst) {
        this.isFirst = isFirst;
        this.isMoving = false;

        this.playerCellType = isFirst ? Game.CELL_X : Game.CELL_O;
        this.playerType = Game.ROBOT;

        this.curMove = new Move();
        this.moves = new LinkedList<Move>();
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public int getPlayerCellType() {
        return this.playerCellType;
    }

    public void setPlayerCellType(int playerCellType) {
        this.playerCellType = playerCellType;
    }

    public int getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public Move getCurMove() {
        return this.curMove;
    }

    public void setCurMove(Move move) {
        this.curMove = (Move) move.clone();
    }

    /**
     * Get the opponent's player cell type     *
     * @return the opponent's player cell type
     */
    public int getOppPlayerCellType() {
        switch (this.playerCellType) {
            case Game.CELL_X:
                return Game.CELL_O;
            case Game.CELL_O:
                return Game.CELL_X;
            default:
                return Game.CELL_EMPTY;
        }
    }

    public LinkedList<Move> getMoves() {
        return this.moves;
    }

    /**
     * player make one move
     * @param move current move
     */
    public void makeMove(Move move) {
        this.isMoving = true;
        this.curMove = move;

        this.moves.add(move);
    }

    @Override
    public String toString() {
        String player = this.isFirst ? "playerOne" : "playerTwo";
        return player + " @ " + this.curMove;
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
            copy.moves = (LinkedList<Move>) this.moves.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }

    abstract protected void MoveNext(Scanner scanner, Board board);
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

            row = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            col = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0

            if (row < 0 || row > 2 || col < 0 || col > 2) { // convert to the board coordinates
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (board.getBoard()[row * Game.SIZE + col].getCellType() != Game.CELL_EMPTY) {
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
    private int fc = 0;     // keep track of the function calls for the minimax function

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

    public int getFc() {
        return this.fc;
    }

    public void resetFc() {
        this.fc = 0;
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
    private void MoveNextEasy(Scanner scanner, Board board) {
        Random rand = new Random();
        int row;
        int col;

        // make the random move, and loop until robot makes valid move
        do {
            row = rand.nextInt(board.getSize());    
            col = rand.nextInt(board.getSize());
        } while (board.getBoard()[row * Game.SIZE + col].getCellType() != Game.CELL_EMPTY);

        Cell cell = this.isFirst ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
        
        board.getBoard()[row * Game.SIZE + col] = cell; // make the robot move

        moves.add(new Move(0, cell));
    }

    /**
     * robot makes the move at the medium level
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    private void MoveNextMedium(Scanner scanner, Board board) {
        Cell oppCell = isFirst() ? new Cell(Game.CELL_O) : new Cell(Game.CELL_X); // opponent player

        /**
         * If it already has two in a row and can win with one further move, it does so.
         */
        // check the first row
        if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[1].getCellType() == getPlayerCellType() &&
                board.getBoard()[2].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[1].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[2].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[1].getCellType() == getPlayerCellType() &&
                board.getBoard()[2].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second row
        else if (board.getBoard()[3].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[5].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[5].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third row
        else if (board.getBoard()[6].getCellType() == getPlayerCellType() &&
                board.getBoard()[7].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getCellType() == getPlayerCellType() &&
                board.getBoard()[7].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the first col
        if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[3].getCellType() == getPlayerCellType() &&
                board.getBoard()[6].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[3].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[3].getCellType() == getPlayerCellType() &&
                board.getBoard()[6].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second col
        else if (board.getBoard()[1].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[7].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[7].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third col
        else if (board.getBoard()[2].getCellType() == getPlayerCellType() &&
                board.getBoard()[5].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == getPlayerCellType() &&
                board.getBoard()[5].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the diagonal
        else if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[8].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the reverse diagonal
        else if (board.getBoard()[2].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[6].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == getPlayerCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == getPlayerCellType() &&
                board.getBoard()[6].getCellType() == getPlayerCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        /**
         * If its opponent can win with one move, it plays the move necessary to block this.
         */
        // check the first row
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[1].getCellType() == oppCell.getCellType() &&
                board.getBoard()[2].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[1].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[2].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[1].getCellType() == oppCell.getCellType() &&
                board.getBoard()[2].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second row
        else if (board.getBoard()[3].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[5].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[3].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[5].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third row
        else if (board.getBoard()[6].getCellType() == oppCell.getCellType() &&
                board.getBoard()[7].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getCellType() == oppCell.getCellType() &&
                board.getBoard()[7].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[6].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the first col
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[3].getCellType() == oppCell.getCellType() &&
                board.getBoard()[6].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[3].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 0, Game.CELL_X) : new Cell(1, 0, Game.CELL_O);
            
            board.getBoard()[3] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[3].getCellType() == oppCell.getCellType() &&
                board.getBoard()[6].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the second col
        else if (board.getBoard()[1].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[7].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 1, Game.CELL_X) : new Cell(2, 1, Game.CELL_O);
            
            board.getBoard()[7] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[7].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[1].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[7].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 1, Game.CELL_X) : new Cell(0, 1, Game.CELL_O);
            
            board.getBoard()[1] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the third col
        else if (board.getBoard()[2].getCellType() == oppCell.getCellType() &&
                board.getBoard()[5].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == oppCell.getCellType() &&
                board.getBoard()[5].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 2, Game.CELL_X) : new Cell(1, 2, Game.CELL_O);
            
            board.getBoard()[5] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[5].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
            board.getBoard()[2] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }

        // check the diagonal
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 2, Game.CELL_X) : new Cell(2, 2, Game.CELL_O);
            
            board.getBoard()[8] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[0].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[8].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 0, Game.CELL_X) : new Cell(0, 0, Game.CELL_O);
            
            board.getBoard()[0] = cell; // make the robot move
            moves.add(new Move(0, cell));
        }

        // check the reverse diagonal
        else if (board.getBoard()[2].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[6].getCellType() == Game.CELL_EMPTY) {

            Cell cell = this.isFirst ? new Cell(2, 0, Game.CELL_X) : new Cell(2, 0, Game.CELL_O);
            
            board.getBoard()[6] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == oppCell.getCellType() &&
                board.getBoard()[4].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[6].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(1, 1, Game.CELL_X) : new Cell(1, 1, Game.CELL_O);
            
            board.getBoard()[4] = cell; // make the robot move

            moves.add(new Move(0, cell));
        }
        else if (board.getBoard()[2].getCellType() == Game.CELL_EMPTY &&
                board.getBoard()[4].getCellType() == oppCell.getCellType() &&
                board.getBoard()[6].getCellType() == oppCell.getCellType()) {

            Cell cell = this.isFirst ? new Cell(0, 2, Game.CELL_X) : new Cell(0, 2, Game.CELL_O);
            
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
    private void MoveNextHard(Scanner scanner, Board board) {
        // finding the ultimate move that favors the computer
        // Move bestMove = miniMaxMove(board, this);
        Move bestMove = getNextBestMove(board);

        int row = bestMove.getCell().getRow();
        int col = bestMove.getCell().getCol();

        Cell cell = this.isFirst ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
        board.getBoard()[row * Game.SIZE + col] = cell; // make the robot move
    }

    /**
     * Get the next best move
     * @param board game board
     * @return next best move
     */
    public Move getNextBestMove(Board board) {
        LinkedList<Move> nextMoves = new LinkedList<>();        // a LinkedList to collect all the moves    

        LinkedList<Cell> availSpots = board.getEmptyCells();    // available spots

        for (Cell spot : availSpots) {
            Move nextMove = new Move(spot);
            
            int curIndex = spot.getRow() * board.getSize() + spot.getCol(); // for easy debug purpose
            
            // set the type of the availble spot to the current player's type
            board.getBoard()[curIndex].setCellType(getPlayerCellType());

            // This is the AI player, and the next move is not maximizing. 
            // So the "isMaximizing" flag should be set to false.
            nextMove.setScore(miniMaxMove(board, 0, false));

            // reset the spot type back to empty
            board.getBoard()[curIndex].setCellType(Game.CELL_EMPTY);

            nextMoves.add(nextMove); // push the object to the LinkedList
        }

        // loop over the moves and choose the move with the highest score
        Move bestMove = new Move();

        int bestScore = Game.SCORE_MIN;
        for (Move move : nextMoves) {
            if (move.getScore() > bestScore) {
                bestScore = move.getScore();
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * minimax algorithm implementation 
     * 
     * @param board  game board
     * @param depth how many levels down
     * @param isMaximizing if it's maximizing player moving
     * @return the score for the current position
     */
    public int miniMaxMove(Board board, int depth, boolean isMaximizing) {

        LinkedList<Cell> availSpots = board.getEmptyCells();    // available spots 
 
        if (((GameTTTBoard)board).checkWin(Game.CELL_X) || ((GameTTTBoard)board).checkWin(Game.CELL_O)) {
            if (isMaximizing) {
                return Game.SCORE_LOSS;
            } else {
                return Game.SCORE_WIN;
            }
        } else if (availSpots.size() == 0) {
            return Game.SCORE_DRAW;
        }

        if (isMaximizing) {
            int maxEval = Game.SCORE_MIN;

            // loop through available spots
            for (Cell spot : availSpots) {
                // set the type of the availble spot to the current player's type
                int curIndex = spot.getRow() * board.getSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].setCellType(getPlayerCellType());
                
                // after maximizing player's turn, it's minimizing player's turn
                int eval = miniMaxMove(board, depth + 1, false);

                board.getBoard()[curIndex].setCellType(Game.CELL_EMPTY);

                maxEval = maxEval >= eval ? maxEval : eval;             
            }

            return maxEval;

        } else {
            int minEval = Game.SCORE_MAX;

            // loop through available spots
            for (Cell spot : availSpots) {
                // set the type of the availble spot to the opponent player's type
                int curIndex = spot.getRow() * board.getSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].setCellType(getOppPlayerCellType());

                // after minimizing player's turn, it's maximizing player's turn
                int eval = miniMaxMove(board, depth + 1, true);

                board.getBoard()[curIndex].setCellType(Game.CELL_EMPTY);

                minEval = minEval <= eval ? minEval : eval;    
            }

            return minEval;            
        }
    }    

    /**
     * 
     * Note: This function is the very first implementation of minimax function, but it's buggy.
     * 
     * Implement the minimax algorithm to find the ultimate play on the game that favors the computer
     *
     * @param board  game board
     * @param player current player
     * @return next best move
     */
    /*
    @Deprecated
    public Move miniMaxMove_deprecated(Board board, Player player) {
        this.fc++;

        LinkedList<Cell> availSpots = board.getEmptyCells();    // available spots
        
        // checks for the terminal states such as win, lose, and draw, and return a value accordingly        
        Move curMove = (Move) (player.getCurMove().clone());

        if (((GameTTTBoard)board).checkWin(Game.CELL_X) || ((GameTTTBoard)board).checkWin(Game.CELL_O)) {
            if (player.getClass().getName() == "PlayerHuman") {
                curMove.setScore(Game.SCORE_WIN);
            } else {
                curMove.setScore(Game.SCORE_LOSS);
            }

            return curMove;

        } else if (availSpots.size() == 0) {
            curMove.setScore(Game.SCORE_DRAW);

            return curMove; 
        }

        LinkedList<Move> nextMoves = new LinkedList<>();        // a LinkedList to collect all the moves        

        // loop through available spots
        for (Cell spot : availSpots) {
            Move nextMove = new Move(spot);  // nextMove ->> [score: -10000, cell: (spot.clone())]

            // set the type of the availble spot to the current player's type
            int curIndex = spot.getRow() * board.getSize() + spot.getCol(); // for easy debug purpose
            int playerCellType = player.getPlayerCellType();  // for easy debug purpose

            board.getBoard()[curIndex].setCellType(playerCellType);

            // collect the score resulted from calling minimax on the opponent of the current player
            Move result = null;
            
            if (player.getClass().getName() == "PlayerRobot") {
                PlayerHuman otherPlayer = new PlayerHuman();
                otherPlayer.curMove = (Move) nextMove.clone();

                otherPlayer.setPlayerCellType(player.getOppPlayerCellType());

                result = miniMaxMove(board, otherPlayer);

            } else {
                PlayerRobot otherPlayer = new PlayerRobot(Game.HARD);
                otherPlayer.curMove = (Move) nextMove.clone();

                otherPlayer.setPlayerCellType(player.getOppPlayerCellType());

                result = miniMaxMove(board, otherPlayer);             
            }
        
            nextMove.setScore(result.getScore());

            // reset the spot type back to empty
            board.getBoard()[curIndex].setCellType(Game.CELL_EMPTY);

            nextMoves.add(nextMove); // push the object to the LinkedList
        }

        // if it is the computer's turn, loop over the moves and choose the move with the highest score
        Move bestMove = new Move();
        if (player.getClass().getName() == "PlayerRobot") {
            int bestScore = Game.SCORE_MIN;
            for (Move move : nextMoves) {
                if (move.getScore() > bestScore) {
                    bestScore = move.getScore();
                    bestMove = move;
                }
            }
        } else { // else loop over the moves and choose the move with the lowest score
            int bestScore = Game.SCORE_MAX;
            for (Move move : nextMoves) {
                if (move.getScore() < bestScore) {
                    bestScore = move.getScore();
                    bestMove = move;
                }
            }
        }

        return bestMove; // return the chosen move object from the Linkedlist to the higher level
    }
    */
}

class Cell implements Cloneable{
    private int row;
    private int col;
    private int cellType;   // 0: CELL_EMPTY; 1: CELL_X; 2: CELL_O

    public Cell() {
        this.row = -1;
        this.col = -1;
        this.cellType = Game.CELL_EMPTY;
    }

    public Cell(int type) {
        this.row = -1;
        this.col = -1;
        this.cellType = type;
    }

    public Cell(int row, int col, int cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    public Cell(Cell cell) {
        this.row = cell.getRow();
        this.col = cell.getCol();
        this.cellType = cell.getCellType();
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

    public int getCellType() {
        return this.cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public void draw() {
        System.out.print(this);
    }

    // @Override
    public String toString() {
        String strType;

        switch (this.cellType) {
            case Game.CELL_X:
                strType = " X";
                break;
            case Game.CELL_O:
                strType = " O";
                break;
            default:
                // strType = "_ ";
                strType = "  ";
                break;
        }

        // return strType + " @ (" + this.row + "," + this.col + ")";  // for debug purpose
        return strType;
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
    protected int size;     // number cells on the horizontal or vertical direction for a squared board
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
     * @return an LinkedList with empty cells
     */
    public LinkedList<Cell> getEmptyCells() {
        LinkedList<Cell> emptyCells = new LinkedList<>();

        for (Cell cell : this.board) {
            if (cell.getCellType() == Game.CELL_EMPTY) {
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
            cell.setCellType(Game.CELL_EMPTY);;
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
                    this.board[i++].setCellType(Game.CELL_X);
                    break;
                case 'O':
                    this.board[i++].setCellType(Game.CELL_O);
                    break;
                case '_':
                    this.board[i++].setCellType(Game.CELL_EMPTY);
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
            this.numOfX += cell.getCellType() == Game.CELL_X ? 1 : 0;
            this.numOfO += cell.getCellType() == Game.CELL_O ? 1 : 0;
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
        int playerCellType = player.getPlayerCellType();

        return (this.board[0].getCellType() == playerCellType && this.board[1].getCellType() == playerCellType && this.board[2].getCellType() == playerCellType) ||
                (this.board[3].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[5].getCellType() == playerCellType) ||
                (this.board[6].getCellType() == playerCellType && this.board[7].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[0].getCellType() == playerCellType && this.board[3].getCellType() == playerCellType && this.board[6].getCellType() == playerCellType) ||
                (this.board[1].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[7].getCellType() == playerCellType) ||
                (this.board[2].getCellType() == playerCellType && this.board[5].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[0].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[2].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[6].getCellType() == playerCellType);
    }

    /**
     * check if specified player cell type wins the game.
     * 
     * This function is used for minimax algorithm. Though it's not an optimal solution to the problem.
     * 
     * @param playerCellType player's cell type
     * @return true if the specified player cell type wins otherwise false
     */
    public boolean checkWin(int playerCellType) {       
        return (this.board[0].getCellType() == playerCellType && this.board[1].getCellType() == playerCellType && this.board[2].getCellType() == playerCellType) ||
                (this.board[3].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[5].getCellType() == playerCellType) ||
                (this.board[6].getCellType() == playerCellType && this.board[7].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[0].getCellType() == playerCellType && this.board[3].getCellType() == playerCellType && this.board[6].getCellType() == playerCellType) ||
                (this.board[1].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[7].getCellType() == playerCellType) ||
                (this.board[2].getCellType() == playerCellType && this.board[5].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[0].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[8].getCellType() == playerCellType) ||
                (this.board[2].getCellType() == playerCellType && this.board[4].getCellType() == playerCellType && this.board[6].getCellType() == playerCellType);
    }
}

class Move implements Cloneable{
    private int score;
    private Cell cell;

    public Move() {
        this.score = Game.SCORE_MIN;
        this.cell = new Cell();
    }

    public Move(Cell cell) {
        this.score = Game.SCORE_MIN;
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
        return "[" + this.cell + ", score -> (" + this.score + ")]";
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
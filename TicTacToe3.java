// package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * This program is from Jetbrains Academy project - Desktop tic-tac-toe.
 * 
 * Stage 3/4 - Rise of the machines
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-13
 *
 */

public class TicTacToe3 {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        new TicTacToe();
    }
}

class TicTacToe extends JFrame {
    /**
     * member variables
     */
    private Player playerOne;
    private Player playerTwo;
    private Player currPlayer;  // current player

    private Board board;

    private JLabel labelStatus;
    private JButton buttonStartReset;
    private JButton buttonPlayer1;
    private JButton buttonPlayer2;

    private int state;
    private final int secondsToSleep;

    public TicTacToe() {

        this.playerOne = null;
        this.playerTwo = null;

        this.state = Game.NOT_STARTED;

        this.secondsToSleep = 1;    // delay between movements

        initComponents();

        actOnCellButton();
        actOnPlayerOneButton();
        actOnPlayerTwoButton();
        actOnStartResetButton();

        setVisible(true);
    }

    /**
     * design the game layout
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // construct a panel to hold  2 player button and start/reset button
        JPanel panelButtons = new JPanel();
        this.buttonPlayer1 = new JButton(Game.STR_HUMAN);
        this.buttonPlayer1.setName("ButtonPlayer1");
        this.buttonPlayer1.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonPlayer2 = new JButton(Game.STR_HUMAN);
        this.buttonPlayer2.setName("ButtonPlayer2");
        this.buttonPlayer2.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonStartReset = new JButton(Game.STR_START);
        this.buttonStartReset.setName("ButtonStartReset");
        this.buttonStartReset.setHorizontalAlignment(SwingConstants.CENTER);

        panelButtons.setLayout(new GridLayout(1, 3));
        panelButtons.add(buttonPlayer1);
        panelButtons.add(buttonStartReset);
        panelButtons.add(buttonPlayer2);

        // construct a panel to hold status label and reset button
        JPanel panelStatus = new JPanel();

        this.labelStatus = new JLabel(Game.STR_NOT_STARTED);
        this.labelStatus.setName("LabelStatus");
        this.labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

        panelStatus.setLayout(new BorderLayout(10, 10));
        panelStatus.add(labelStatus, BorderLayout.WEST);

        // initialize the board
        this.board = new Board();

        // add the components to the frame
        setLayout(new BorderLayout(0, 0));

        add(panelButtons, BorderLayout.NORTH);
        add(this.board, BorderLayout.CENTER);
        add(panelStatus, BorderLayout.SOUTH);
    }

    /**
     * add a listener for each cell on the field
     */
    public void actOnCellButton() {
        int size = this.board.getBoardSize();
        CellButton[] cells = this.board.getBoard();

        for (int i = 0; i < size * size; i++) {
            CellButton cell = cells[i];

            cell.getCell().setRow(i / size);
            cell.getCell().setCol(i % size);

            cell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    cell.setEnabled(true);  // enable the button

                    if (state == Game.NOT_STARTED  || state == Game.GAME_OVER) {
                        return;
                    }

                    // human make the move
                    if (currPlayer.getPlayerType() == Game.HUMAN) {
                        if (cell.getText() == Game.STR_CELL_EMPTY) {
                            if (currPlayer.getPlayerCellType() == Game.CELL_X) {
                                cell.setText(Game.STR_CELL_X);
                                cell.getCell().setCellType(Game.CELL_X);
                            } else {
                                cell.setText(Game.STR_CELL_O);
                                cell.getCell().setCellType(Game.CELL_O);
                            }
                        }

                        pause();

                        currPlayer = currPlayer == playerOne ? playerTwo : playerOne;

                        // human play against computer
                        if (currPlayer.getPlayerType() == Game.ROBOT) {

                            checkGameState();
                            pause();

                            currPlayer.MoveNext(board);
                            currPlayer = currPlayer == playerOne ? playerTwo : playerOne;
                        }

                        setVisible(true);
                    }

                    checkGameState();
                }
            });
        }
    }

    /**
     * add delay between movements
     */
    private void pause() {
        try {
            TimeUnit.SECONDS.sleep(secondsToSleep);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * add the listener for reset button
     */
    public void actOnStartResetButton() {

        this.buttonStartReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (state == Game.NOT_STARTED) {
                    state = Game.IN_PROGRESS;

                    buttonPlayer1.setEnabled(false);
                    buttonPlayer2.setEnabled(false);

                    // create player 1
                    if (buttonPlayer1.getText() == Game.STR_HUMAN) {
                        playerOne = new PlayerHuman();
                    } else {
                        playerOne = new PlayerRobot(Game.HARD);
                    }

                    // create player 2
                    if (buttonPlayer2.getText() == Game.STR_HUMAN) {
                        playerTwo = new PlayerHuman(false);
                    } else {
                        playerTwo = new PlayerRobot(false, Game.HARD);
                    }

                    buttonStartReset.setText(Game.STR_RESET);
                    labelStatus.setText(Game.STR_IN_PROGRESS);
                    setVisible(true);

                    board.enableBoard();

                    pause();

                    currPlayer = playerOne;

                    // do while loop for computer playing against computer
                    do {
                        if (currPlayer.getPlayerType() == Game.ROBOT) {
                            currPlayer.MoveNext(board);
                        } else {
                            break;  // if player is human, break out the do while loop
                        }

                        board.setVisible(true);
                        setVisible(true);
                        pause();

                        currPlayer = currPlayer == playerOne ? playerTwo : playerOne;

                        checkGameState();

                        if (state == Game.GAME_OVER) {
                            break;  // break out the do while loop
                        }

                    } while (currPlayer.getPlayerType() == Game.ROBOT);

                }
                // else if (state == Game.IN_PROGRESS) {
                // do nothing
                // pause();

                // }
                else if (state == Game.IN_PROGRESS || state == Game.GAME_OVER) {
                    state = Game.NOT_STARTED;

                    playerOne = null;
                    playerTwo = null;

                    board.clear();

                    buttonStartReset.setText(Game.STR_START);
                    labelStatus.setText(Game.STR_NOT_STARTED);

                    buttonPlayer1.setEnabled(true);
                    buttonPlayer2.setEnabled(true);
                }
            }
        });
    }

    /**
     * add the listener for player1 button
     */
    public void actOnPlayerOneButton() {
        this.buttonPlayer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state == Game.NOT_STARTED) {
                    if (buttonPlayer1.getText() == Game.STR_HUMAN) {
                        buttonPlayer1.setText(Game.STR_COMPUTER);
                    } else {
                        buttonPlayer1.setText(Game.STR_HUMAN);
                    }
                }
            }
        });
    }

    /**
     * add the listener for player2 button
     */
    public void actOnPlayerTwoButton() {
        this.buttonPlayer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state == Game.NOT_STARTED) {
                    if (buttonPlayer2.getText() == Game.STR_HUMAN) {
                        buttonPlayer2.setText(Game.STR_COMPUTER);
                    } else {
                        buttonPlayer2.setText(Game.STR_HUMAN);
                    }
                }
            }
        });
    }

    /**
     * check the game state
     *
     */
    private void checkGameState() {
        if (this.board.checkWin(Game.CELL_X)) {
            this.state = Game.X_WIN;

        } else if (this.board.checkWin(Game.CELL_O)) {
            this.state = Game.O_WIN;

        } else {
            // check if board is full or not
            boolean isBoardFull = true;
            for (int row = 0; row < this.board.size; row++) {
                for (int col = 0; col < this.board.size; col++) {
                    if (this.board.getBoard()[row * this.board.size + col].getCell().getCellType() == Game.CELL_EMPTY) {
                        isBoardFull = false;
                        break; // break out of the inner for loop
                    }

                    if (!isBoardFull) { // break out of the outer for loop
                        break;
                    }
                }
            }

            this.state = isBoardFull? Game.DRAW : Game.IN_PROGRESS;
        }

        switch (this.state) {
            case Game.X_WIN:
                this.labelStatus.setText(Game.STR_X_WIN);
                this.state = Game.GAME_OVER;
                break;
            case Game.O_WIN:
                this.labelStatus.setText(Game.STR_O_WIN);
                this.state = Game.GAME_OVER;
                break;
            case Game.DRAW:
                this.labelStatus.setText(Game.STR_DRAW);
                this.state = Game.GAME_OVER;
                break;
            case Game.IN_PROGRESS:
                labelStatus.setText(Game.STR_IN_PROGRESS);
                break;
        }
    }
}

class Board extends JPanel{
    protected int size;
    protected CellButton[] board;

    public Board() {
        super();

        this.size = Game.SIZE;
        this.board = new CellButton[this.size * this.size];

        initComponents();

        setLayout(new GridLayout(3, 3));
        setVisible(true);
    }

    /**
     * initialize the cell buttons
     */
    private void initComponents() {
        char chAlpha = 'A';
        char chNum = '3';
        String text = null;

        for (int i = 0; i < this.board.length; i++) {
            text = "" + chAlpha + chNum;
            // this.board[i] = new Cell(text, "Button" + text);
            this.board[i] = new CellButton(Game.STR_CELL_EMPTY, "Button" + text);
            this.board[i].getCell().setCellType(Game.CELL_EMPTY);   // initialize to empty cell

            this.board[i].setFocusPainted(false);   // remove the border around the text of the active cell

            add(this.board[i]);

            chAlpha++;
            if (chAlpha == 'D') {
                chNum--;
                chAlpha = 'A';
            }
        }
    }

    public CellButton[] getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.size;
    }

    /**
     * get the empty cells on the board
     *
     * @return an LinkedList with empty cells
     */
    public LinkedList<Cell> getEmptyCells() {
        LinkedList<Cell> emptyCells = new LinkedList<>();

        for (CellButton cellButton : this.board) {
            if (cellButton.getCell().getCellType() == Game.CELL_EMPTY) {
                emptyCells.add(cellButton.getCell());
            }
        }

        return emptyCells;
    }

    /**
     * reset the board for a new game
     *
     */
    public void clear() {
        for (CellButton cellButton : this.board) {
            cellButton.getCell().setCellType(Game.CELL_EMPTY);
            cellButton.setText(Game.STR_CELL_EMPTY);
        }
    }

    public void enableBoard() {
        for (CellButton cellButton : this.board) {
            cellButton.setEnabled(true);
        }
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
        return (this.board[0].getCell().getCellType() == playerCellType && this.board[1].getCell().getCellType() == playerCellType && this.board[2].getCell().getCellType() == playerCellType) ||
                (this.board[3].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType) ||
                (this.board[6].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[3].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType) ||
                (this.board[1].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType);
    }
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
                strType = Game.STR_CELL_X;
                break;
            case Game.CELL_O:
                strType = Game.STR_CELL_O;
                break;
            default:
                strType = Game.STR_CELL_EMPTY;
                break;
        }

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
            copy.setCellType(this.cellType);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }
}

class CellButton extends JButton {

    private Cell cell;          // each CellButton contains one Cell object
    private boolean isClicked;

    public CellButton() {
        super();

        this.cell = new Cell();
        this.setBackground(Color.LIGHT_GRAY);

        this.isClicked = false;

        this.setEnabled(false);
    }

    public CellButton(String text, String name) {
        super(text);

        this.setName(name);
        this.setFont(new Font("Arial", Font.BOLD, 40));
        this.setBackground(Color.LIGHT_GRAY);

        this.cell = new Cell();
        this.isClicked = false;

        this.setEnabled(false);
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = (Cell)cell.clone();
    }

    public boolean getIsClicked() {
        return this.isClicked;
    }

    public void setIsclicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
}

class Game {
    /**
     * constants definition
     */

    // number of cells in a row on a sqaured board
    final static int SIZE = 3;

    // game state
    final static int NOT_STARTED = 0;
    final static int IN_PROGRESS = 1;
    final static int X_WIN = 2;
    final static int O_WIN = 3;
    final static int DRAW = 4;
    final static int GAME_OVER = 5;

    final static String STR_NOT_STARTED = "Game is not started";
    final static String STR_IN_PROGRESS = "Game in progress";
    final static String STR_X_WIN = "X wins";
    final static String STR_O_WIN = "O wins";
    final static String STR_DRAW = "Draw";

    // cell type at the position
    final static int CELL_EMPTY = 0;
    final static int CELL_X = 1;
    final static int CELL_O = 2;

    final static String STR_CELL_EMPTY = " ";
    final static String STR_CELL_X = "X";
    final static String STR_CELL_O = "O";

    // who is playing
    final static int ROBOT = 1;
    final static int HUMAN = 2;

    // String for human and computer
    final static String STR_HUMAN = "Human";
    final static String STR_COMPUTER = "Robot";

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    final static int HARD = 2;

    // String for start or reset the game
    final static String STR_START = "Start";
    final static String STR_RESET = "Reset";

    // score for the minmax function
    final static int SCORE_WIN = 10;
    final static int SCORE_LOSS = -10;
    final static int SCORE_DRAW = 0;
    final static int SCORE_MAX = 10000;
    final static int SCORE_MIN = -10000;
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

abstract class Player implements Cloneable{
    protected boolean isFirst;      // if it's first player

    protected int playerCellType;   // 0: CELL_EMPTY; 1: CELL_X; 2: CELL_O
    protected int playerType;       // 1: ROBOT; 2: HUMAN

    protected Move curMove;
    protected LinkedList<Move> moves;

    public Player() {
        this.isFirst = true;

        this.playerCellType = Game.CELL_X;
        this.playerType = Game.ROBOT;

        this.curMove = new Move();
        this.moves = new LinkedList<Move>();
    }

    public Player(boolean isFirst) {
        this.isFirst = isFirst;

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

    abstract protected void MoveNext(Board board);
}

class PlayerHuman extends Player {
    public PlayerHuman() {
        super();

        setPlayerType(Game.HUMAN);
    }

    public PlayerHuman(boolean isFirst) {
        super(isFirst);

        setPlayerType(Game.HUMAN);
    }

    @Override
    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    protected void MoveNext(Board board) {

    }
}

class PlayerRobot extends Player {
    private final int level;
    private int fc = 0;     // keep track of the function calls for the minimax function

    public PlayerRobot() {
        super();

        setPlayerType(Game.ROBOT);
        this.level = Game.EASY;
    }

    public PlayerRobot(boolean isFirst) {
        super(isFirst);

        setPlayerType(Game.ROBOT);
        this.level = Game.EASY;
    }

    public PlayerRobot(int level) {
        super();

        setPlayerType(Game.ROBOT);
        this.level = level;
    }

    public PlayerRobot(boolean isFirst, int level) {
        super(isFirst);

        setPlayerType(Game.ROBOT);
        this.level = level;
    }

    public int getFc() {
        return this.fc;
    }

    public void resetFc() {
        this.fc = 0;
    }

    @Override
    protected void MoveNext(Board board) {
        switch (this.level) {
            case Game.EASY:
                // System.out.println("Making move level \"easy\"");
                // MoveNextEasy(board);
                // break;
            case Game.MEDIUM:
                // System.out.println("Making move level \"medium\"");
                // MoveNextMedium(board);
                break;
            case Game.HARD:
                // System.out.println("Making move level \"hard\"");
                MoveNextHard(board);
                break;
        }
    }


    /**
     * robot makes the move at the hard level
     *
     * @param board game board
     */
    private void MoveNextHard(Board board) {
        // finding the ultimate move that favors the computer
        Move bestMove = getNextBestMove(board);

        int row = bestMove.getCell().getRow();
        int col = bestMove.getCell().getCol();

        Cell cell = this.isFirst ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);

        CellButton cellButton = board.getBoard()[row * Game.SIZE + col];
        cellButton.setCell(cell);  // make the robot move

        cellButton.setEnabled(true);

        // draw the cell
        cellButton.setText(this.isFirst ? Game.STR_CELL_X : Game.STR_CELL_O);
        cellButton.setVisible(true);
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

            int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose

            // set the type of the availble spot to the current player's type
            board.getBoard()[curIndex].getCell().setCellType(getPlayerCellType());

            // This is the AI player, and the next move is not maximizing.
            // So the "isMaximizing" flag should be set to false.
            nextMove.setScore(miniMaxMove(board, 0, false));

            // reset the spot type back to empty
            board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

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

        if (board.checkWin(Game.CELL_X) || (board.checkWin(Game.CELL_O))) {
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
                int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].getCell().setCellType(getPlayerCellType());

                // after maximizing player's turn, it's minimizing player's turn
                int eval = miniMaxMove(board, depth + 1, false);

                board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

                maxEval = Math.max(maxEval, eval);
            }

            return maxEval;

        } else {
            int minEval = Game.SCORE_MAX;

            // loop through available spots
            for (Cell spot : availSpots) {
                // set the type of the availble spot to the opponent player's type
                int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].getCell().setCellType(getOppPlayerCellType());

                // after minimizing player's turn, it's maximizing player's turn
                int eval = miniMaxMove(board, depth + 1, true);

                board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

                minEval = Math.min(minEval, eval);
            }

            return minEval;
        }
    }
}

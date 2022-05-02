// package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * This program is from Jetbrains Academy project - Desktop tic-tac-toe.
 * 
 * Stage 2/4 - The game is on!
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-02
 *
 */

public class TicTacToe2 {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        new TicTacToe();
    }
}

class TicTacToe extends JFrame {

    // private Player playerOne;
    // private Player playerTwo;

    private boolean isPlayerOneMove;    // who is moving now

    private Board board;
    
    private JLabel labelStatus;
    private JButton buttonReset;    

    private int state;

    public TicTacToe() {

        resetGame();

        initComponents();

        actOnCellButton();

        actOnResetButton();

        setVisible(true);

        // setLayout(null);       
    }

    /**
     * reset the game parameters
     */
    private void resetGame() {
        // this.playerOne = null;
        // this.playerTwo = null;

        this.isPlayerOneMove = true;
        this.state = Game.NOT_STARTED;     
    }

    private void initComponents() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // construct a panel to hold status label and reset button
        JPanel panelStatus = new JPanel();

        this.labelStatus = new JLabel(Game.STR_NOT_STARTED);
        this.labelStatus.setName("LabelStatus");
        this.labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonReset = new JButton("Reset");
        this.buttonReset.setName("ButtonReset");
        this.buttonReset.setHorizontalAlignment(SwingConstants.CENTER);

        panelStatus.setLayout(new BorderLayout(10, 10));
        panelStatus.add(labelStatus, BorderLayout.WEST);
        panelStatus.add(buttonReset, BorderLayout.EAST);
        
        // initialize the board
        this.board = new Board();        

        setLayout(new BorderLayout(10, 10));

        add(this.board, BorderLayout.CENTER);            
        add(panelStatus, BorderLayout.SOUTH);
    }    

    /**
     * add a listener for each cell of the field
     */
    public void actOnCellButton() {
        int size = this.board.getBoardSize();
        CellButton[] cells = this.board.getBoard();

        for (int i = 0; i < size * size; i++) {
            CellButton cell = cells[i];

            cell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (state != Game.GAME_OVER) {
                        if (cell.getIsClicked() == false) {

                            if (isPlayerOneMove) {
                                cell.setText(Game.STR_CELL_X);
                                cell.getCell().setCellType(Game.CELL_X);

                                isPlayerOneMove = false;
                            } else {
                                cell.setText(Game.STR_CELL_O);
                                cell.getCell().setCellType(Game.CELL_O);

                                isPlayerOneMove = true;
                            }

                        } else {
                            cell.setIsclicked(true);
                        }
                    }

                    state = checkState(); // check game state
                    switch (state) {
                        case Game.X_WIN:
                            labelStatus.setText(Game.STR_X_WIN);
                            state = Game.GAME_OVER;
                            break;
                        case Game.O_WIN:
                            labelStatus.setText(Game.STR_O_WIN);
                            state = Game.GAME_OVER;
                            break;
                        case Game.DRAW:
                            labelStatus.setText(Game.STR_DRAW);
                            state = Game.GAME_OVER;
                            break;
                        case Game.IN_PROGRESS:
                            labelStatus.setText(Game.STR_IN_PROGRESS);
                            break;
                    }                    
                }
            });
        }
    }

    /**
     * add the listener for reset button
     */
    public void actOnResetButton() {
        this.buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneMove = true;
                state = Game.NOT_STARTED;                 
                
                board.clear();

                labelStatus.setText(Game.STR_NOT_STARTED);
            }
        });
    }


    /**
     * check the game state
     *
     * @return game state
     */
    private int checkState() {
        if (this.board.checkWin(Game.CELL_X)) {
            return Game.X_WIN;
        }

        if (this.board.checkWin(Game.CELL_O)) {
            return Game.O_WIN;
        }

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

        return isBoardFull? Game.DRAW : Game.IN_PROGRESS;
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
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
     * reset the board for a new game
     *
     */
    public void clear() {
        for (CellButton cellButton : this.board) {
            cellButton.getCell().setCellType(Game.CELL_EMPTY);
            cellButton.setText(Game.STR_CELL_EMPTY);
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

    /**
     * check if player wins the game
     *
     * @param player player to check if wins
     * @return true if this player wins otherwise false
     */
    /*
    public boolean checkWin(Player player) {
        int playerCellType = player.getPlayerCellType();

        return (this.board[0].getCell().getCellType() == playerCellType && this.board[1].getCell().getCellType() == playerCellType && this.board[2].getCell().getCellType() == playerCellType) ||
                (this.board[3].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType) ||
                (this.board[6].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[3].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType) ||
                (this.board[1].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType);
    }    
    */
}

class CellButton extends JButton {

    private Cell cell;          // each CellButton contains one Cell object
    private boolean isClicked;

    public CellButton() {
        super();

        this.cell = new Cell();
        this.isClicked = false;
    }

    public CellButton(String text, String name) {
        super(text);
        
        this.setName(name);     
        this.setFont(new Font("Arial", Font.BOLD, 40));   

        this.cell = new Cell();
        this.isClicked = false;
    } 

    public Cell getCell() {
        return this.cell;
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

    // score for the minmax function
    final static int SCORE_WIN = 10;
    final static int SCORE_LOSS = -10;
    final static int SCORE_DRAW = 0;
    final static int SCORE_MAX = 10000;
    final static int SCORE_MIN = -10000;    
}

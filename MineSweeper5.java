// package minesweeper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Scanner;

/**
 * The 5th development stage of the game Minesweeper.
 *
 * The detailed steps built on top of previous development.
 *      - Step 1: Add more state in the enum CellState
 *
 *      - step 2: Use "flood fill" algorithm to automatically explore the adjacent area if the cell is empty and has no mines around.
 * 
 *      - step 3: build test mine field
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-10 
 */

public class MineSweeper5 {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int mines;

        System.out.print("How many mines do you want on the field? > ");
        mines = scanner.nextInt();

        MineField mineField = new MineField(MineField.SIZE, mines); // construct a minefield with all empty cells

        System.out.println(mineField);  // draw the empty minefile

        // mineField.placeAllMines();      // place the given number of mines into the field

        mineField.buildTestMineField();

        mineField.startSweeping(scanner);   // start the game

        scanner.close();
    }
}

class MineField {
    /**
     * constants definition
     */
    final static int SIZE = 9;

    private int mines;

    private int size;

    private final MineCell[][] field;

    private final ArrayList<MineCell> mineArray;  // an ArrayList contains all the mines

    private boolean isFirstMove;
    private boolean isGameOver;

    public MineField(int size, int mines) {
        this.mines = mines;
        this.size = size;
        this.field = new MineCell[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.field[i][j] = new MineCell(MineCell.CELL_EMPTY);
                this.field[i][j].setState(CellState.UN_EXPLORED);
            }
        }

        mineArray = new ArrayList<>();

        isFirstMove = true;
        isGameOver = false;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * build the test mine field
     */
    public void buildTestMineField() {
        /*
         |123456789|
        -|---------|
        1|11///1X..|
        2|X1//12...|
        3|11//1X...|
        4|////1....|
        5|11111....|
        6|.X..X....|
        7|.3X...X..|
        8|.X..X211.|
        9|...X.....|
        -|---------|
        */
        placeOneMine(1, 7);
        placeOneMine(2, 1);
        
        placeOneMine(3, 6);
        placeOneMine(6, 2);
        
        placeOneMine(6, 5);
        placeOneMine(7, 3);

        placeOneMine(7, 7);
        placeOneMine(8, 2);

        placeOneMine(8, 5);
        placeOneMine(9, 4);

        calcAdjMines();
    }

    /**
     * place the specified number of mines into the field, and calculate the adjacent mines for each empty cell
     *
     */
    public void placeAllMines() {
        // generate the k mines, uniformly at random
        for (int mine = 0; mine < this.mines; mine++) {
            placeOneMine();
        }

        calcAdjMines();
    }

    /**
     * calculate the calcuate adjacent mines for all empty cells on the mine field.
     */
    private void calcAdjMines() {
        int minesAround; // number of mines around a cell

        // calculate the adjacent mines for each empty cell
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                minesAround = calcAdjMines(i, j);

                this.field[i][j].setMinesAdj(minesAround);             
            }
        }
    }
    
    /**
     * place a mine on the specified location for buiding the test mine field
     * 
     * @param i row index
     * @param j col index
     */
    private void placeOneMine(int i, int j) {
        MineCell curCell = this.field[i - 1][j - 1];

        curCell.setType(MineCell.CELL_MINE);
        this.mineArray.add(curCell);
    }


    /**
     * place a mine on the mine field randomly
     */
    private void placeOneMine() {
        int i;
        int j;
        int randNum;

        while (true) {
            randNum = (int) (Math.random() * (this.size * this.size));

            i = randNum / this.size;
            j = randNum % this.size;
            MineCell curCell = this.field[i][j];

            if (curCell.getType() == MineCell.CELL_EMPTY && curCell.getState() == CellState.UN_EXPLORED) {
                curCell.setType(MineCell.CELL_MINE);
                this.mineArray.add(curCell);

                break;
            }
        }
    }

    /**
     * calcuate the mines around a cell.
     * If the cell has mine in it, always return 0.
     * 
     * @param i the x coordinate (row)
     * @param j the y coordinate (col)
     * @return the number of adjacent mines
     */
    private int calcAdjMines(int i, int j) {
        int minesAround = 0;

        if (this.field[i][j].getType() == MineCell.CELL_EMPTY) {
            if (i - 1 >= 0) {
                minesAround += this.field[i-1][j].getType();      // above
            }

            if (i + 1 < this.size) {
                minesAround += this.field[i+1][j].getType();      // below
            }

            if (j - 1 >= 0) {
                minesAround += this.field[i][j-1].getType();      // left
            }

            if (j + 1 < this.size) {
                minesAround += this.field[i][j+1].getType();      // right
            }

            if (i - 1 >= 0 && j - 1 >= 0) {
                minesAround += this.field[i-1][j-1].getType();    // above and left diagnoal
            }

            if (i - 1 >= 0 && j + 1 < this.size) {
                minesAround += this.field[i-1][j+1].getType();    // above and right diagnoal
            }

            if (i + 1 < this.size && j - 1 >= 0) {
                minesAround += this.field[i+1][j-1].getType();    // below and left diagnoal
            }
            if (i + 1 < this.size && j + 1 < this.size) {
                minesAround += this.field[i+1][j+1].getType();    // below and right diagnoal
            }
        }

        return minesAround;
    }

    /**
     * Start sweeping the mines
     *
     * @param scanner Scanner
     */
    public void startSweeping(Scanner scanner) {
        int row; // x coordinate
        int col; // y coordinate

        String strRow;
        String strCol;
        String strCmd;

        ArrayList<MineCell> MarkedWrongCells = new ArrayList<>();

        MineCell curCell = null;

        while (true) {
            System.out.print("Set/unset mines marks or claim a cell as free: > ");
            strRow = scanner.next();

            if (strRow.length() > 1 || strRow.toCharArray()[0] - '0' < 1 || strRow.toCharArray()[0] - '0' > this.size) {
                System.out.println("You should enter valid row numbers!");

                scanner.nextLine(); // Important! Skip the rest of the line to get rid of the carriage return
                continue;
            }

            strCol = scanner.next();
            if (strCol.length() > 1 || strCol.toCharArray()[0] - '0' < 1 || strCol.toCharArray()[0] - '0' > this.size) {
                System.out.println("You should enter valid col numbers!");

                scanner.nextLine();
                continue;
            }

            strCmd = scanner.next();    // "free" or "mine"
            if (!(strCmd.equals("free") || strCmd.equals("mine"))) {
                System.out.println("You should enter valid command - \"free\" or \"mine\"!");

                scanner.nextLine();
                continue;                
            }

            // row = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            // col = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0
        
            // Actually the tests in hyperskill is wrong. 
            // But to pass the test, have to exchange row and col
            col = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            row = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0

            curCell = this.field[row][col];

            if (this.isFirstMove) {
                if (strCmd.equals("mine")) {
                    if (curCell.getType() == MineCell.CELL_MINE) {
                        curCell.setState(CellState.MARKED_MINE_RIGHT);
                    } else {
                        curCell.setState(CellState.MARKED_MINE_WRONG);
                        MarkedWrongCells.add(curCell);
                    }
                } else {
                    if ((curCell.getType() == MineCell.CELL_MINE)) {
                        curCell.setType(MineCell.CELL_EMPTY);

                        // set the state to EXPLORED_FREE for now, so that the call to placeOneMine() won't place the mine in the cell again.
                        // and the state may change according to the number of adjacent mines.
                        curCell.setState(CellState.EXPLORED_FREE);  
    
                        placeOneMine();

                        if (curCell.getMinesAdj() == 0) {
                            curCell.setState(CellState.EXPLORED_FREE);
                        } else {
                            curCell.setState(CellState.EXPLORED_NUMBER);
                        }

                    } else {
                        if (curCell.getMinesAdj() == 0) {
                            curCell.setState(CellState.EXPLORED_FREE);
                        } else {
                            curCell.setState(CellState.EXPLORED_NUMBER);
                        }                        
                    }
                }

                this.isFirstMove = false;

            } else {
                if (strCmd.equals("mine")) {
                    if (curCell.getState() == CellState.UN_EXPLORED) {
                        if (curCell.getType() == MineCell.CELL_MINE) {
                            curCell.setState(CellState.MARKED_MINE_RIGHT);
                        } else {
                            curCell.setState(CellState.MARKED_MINE_WRONG);
                            MarkedWrongCells.add(curCell);
                        }                        
                    } else if (curCell.getState() == CellState.MARKED_MINE_RIGHT) {
                        curCell.setState(CellState.UN_EXPLORED);
                    } else if (curCell.getState() == CellState.MARKED_MINE_WRONG) {
                        curCell.setState(CellState.UN_EXPLORED);
                        MarkedWrongCells.remove(curCell);
                    } else {
                        System.out.printf("The coordinates {%d, %d} you entered are invalid, please re-enter the command!\n", row, col);
                        break;
                    }
                } else {
                    if (curCell.getType() == MineCell.CELL_MINE) {
                        curCell.setState(CellState.EXPLORED_MINE);

                        for (MineCell cell : mineArray) {
                            cell.setState(CellState.EXPLORED_MINE);
                        }

                        isGameOver = true;
                    } else {
                        if (curCell.getMinesAdj() == 0) {
                            curCell.setState(CellState.EXPLORED_FREE);
                        } else {
                            curCell.setState(CellState.EXPLORED_NUMBER);
                        }
                    }
                }
            }

            if (curCell.getState() == CellState.EXPLORED_FREE) {
                floodEmptyArea(row, col);
            }

            System.out.println(this);  // draw the mine field
            if (isGameOver) {
                    System.out.println("You stepped on a mine and failed!");
                    return;
            }

            boolean markedAllCorrect = true;
            for (MineCell cell : mineArray) {
                if (cell.getState() != CellState.MARKED_MINE_RIGHT) {
                    markedAllCorrect = false;
                    break;
                }
            }

            if (markedAllCorrect && MarkedWrongCells.isEmpty()) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
    }    

    /**
     * apply "Flood Fill" algorithm to expand the empty area if the current cell is empty and has no mines around it.
     * 
     * @param i row index
     * @param j col index
     */
    private void floodEmptyArea(int i, int j) {
        if (i < 0 || i >= this.size || j < 0 || j >= this.size) {
            return;
        }
        
        MineCell curCell = this.field[i][j];
        if (curCell.getIsVisited() || curCell.getType() == MineCell.CELL_MINE 
            || curCell.getState() == CellState.MARKED_MINE_RIGHT || curCell.getState() == CellState.MARKED_MINE_WRONG) {
            return;
        }

        // If a cell is empty and has mines around it, only that cell is explored, revealing the number of mines around it.
        if (curCell.getType() == MineCell.CELL_EMPTY && curCell.getMinesAdj() != 0) {
            curCell.setState(CellState.EXPLORED_NUMBER);
            curCell.setIsVisited(true);
            return;
        }

        curCell.setIsVisited(true);
        curCell.setState(CellState.EXPLORED_FREE);
    
        floodEmptyArea(i - 1, j);
        floodEmptyArea(i + 1, j);
        floodEmptyArea(i, j - 1);
        floodEmptyArea(i, j + 1);        

        return;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(" |123456789|\n");
        sb.append("-|---------|\n");

        for (int i = 0; i < this.size; i++) {
            sb.append(i + 1).append("|");

            for (int j = 0; j < this.size; j++) {
                sb.append(field[i][j]);
            }
            sb.append("|\n");
        }

        sb.append("-|---------|\n");

        return sb.toString();
    }
}

class MineCell {
    /**
     * constants definition
     */

    // setting CELL_EMPTY to 0 and CELL_MINE to 1 is essential, 
    // especially when calculating the adjacent mines for each empty cell.
    final public static int CELL_EMPTY = 0;
    final public static int CELL_MINE = 1;

    final static String STR_UN_EXPLORED = ".";              // unexplored cell
    final static String STR_EXPLORED_FREE = "/";            // explored free cell without mines around it
    final static String STR_EXPLORED_MINE = "X";            // explored cell with mine in it
    final static String STR_UN_EXPLORED_MARKED = "*";       // unexplored marked cell

    private int row;
    private int col;
    private int type;
    private int minesAdj;

    private CellState state;

    private boolean isVisited;  // for "Flood Fill" algorithm

    public MineCell() {
        this.row = -1;
        this.col = -1;
        this.type = CELL_EMPTY;
        this.minesAdj = -1;

        this.state = CellState.UN_EXPLORED;
        this.isVisited = false;
    }

    public MineCell(int type) {
        this.row = -1;
        this.col = -1;
        this.type = type;
        this.minesAdj = -1;

        this.state = CellState.UN_EXPLORED;
        this.isVisited = false;
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

    public int getMinesAdj() {
        return this.minesAdj;
    }

    public void setMinesAdj(int minesAdj) {
        this.minesAdj = minesAdj;
    }

    public CellState getState() {
        return this.state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public boolean getIsVisited() {
        return this.isVisited;
    }

    public void setIsVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    @Override
    public String toString() {
        switch (this.state) {
            case UN_EXPLORED:
                return STR_UN_EXPLORED;

            case EXPLORED_FREE:
                return STR_EXPLORED_FREE;

            case EXPLORED_NUMBER:
                return Integer.toString(this.minesAdj);

            case EXPLORED_MINE:
                return STR_EXPLORED_MINE;

            case MARKED_MINE_WRONG:
                return STR_UN_EXPLORED_MARKED;
                
            case MARKED_MINE_RIGHT:
                return STR_UN_EXPLORED_MARKED;
        }

        return null;
    }
}

enum CellState {
    UN_EXPLORED(0),             // cell unexplored

    EXPLORED_FREE(1),           // cell is empty, and explored with no mines around
    EXPLORED_NUMBER(2),         // cell is empty, and explored with some mines around
    EXPLORED_MINE(3),           // cell has mine in it, and explore it, and then game over

    MARKED_MINE_WRONG(4),       // cell is empty, and mark it with "mine"
    MARKED_MINE_RIGHT(5);       // cell has mine in it, and mark it with "mine"

    final int state;

    CellState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
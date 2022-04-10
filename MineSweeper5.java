// package minesweeper;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The 5th development stage of the game Minesweeper.
 *
 * The detailed steps built on top of previous development.
 *      - Step 1: Add more state in the enum CellState
 *
 *      - step 2: Use "flood fill" algorithm to automatically explore the adjacent area if the cell is empty and has no mines around.
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

        MineField mineField = new MineField(MineField.SIZE);

        System.out.println(mineField);  // draw the empty minefile

        mineField.generateMineField(mines);

        mineField.startSweeping(scanner);

        scanner.close();
    }
}

class MineField {
    /**
     * constants definition
     */
    final static int SIZE = 9;

    private int size;

    private final MineCell[][] field;

    private final ArrayList<MineCell> mineArray;  // an ArrayList contains all the mines

    private boolean firstMove;

    public MineField(int size) {
        this.size = size;
        this.field = new MineCell[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.field[i][j] = new MineCell(MineCell.CELL_EMPTY);
            }
        }

        mineArray = new ArrayList<>();

        firstMove = true;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * generate the mine field with specified number of mines, and calculate the adjacent mines for each empty cell
     *
     * @param mines number of mines
     */
    public void generateMineField(int mines) {
        int i = 0;
        int j = 0;

        // generate the k mines, uniformly at random
        for (int mine = 0; mine < mines; mine++) {
            placeMine();
        }

        int minesAround; // number of mines around a cell

        // calculate the adjacent mines for each empty cell
        for (i = 0; i < this.size; i++) {
            for (j = 0; j < this.size; j++) {
                minesAround = calcAdjMines(i, j);

                this.field[i][j].setMinesAdj(minesAround);

                if (minesAround > 0) {
                    this.field[i][j].setState(CellState.EXPLORED_NUMBER);         
                } 
            }
        }
    }

    /**
     * place a mine on the mine field
     */
    private void placeMine() {
        while (true) {
            randNum = (int) (Math.random() * (this.size * this.size));

            i = randNum / this.size;
            j = randNum % this.size;

            if (this.field[i][j].getType() == MineCell.CELL_EMPTY) {
                this.field[i][j].setType(MineCell.CELL_MINE);

                this.mineArray.add(this.field[i][j]);

                break;
            }
        }
    }

    /**
     * calcuate the mines around an empty cell
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

        ArrayList<MineCell> MarkedWrongCells = new ArrayList<>();

        MineCell curCell = null;

        // System.out.println(this);  // draw the mine field

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
                continue;
            }

            // row = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            // col = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0
        
            // Actually the tests in hyperskill is wrong. 
            // But to pass the test, have to exchange row and col
            col = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            row = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0

            curCell = this.field[row][col];

            // Important! below code is not finished, and needs to continue working on it
            if (firstMove) {
                if (curCell.getType() == MineCell.CELL_MINE) {
                    curCell.setType(MineCell.CELL_EMPTY);
                    placeMine();
                }
            }

            switch (curCell.getState()) {
                case UN_EXPLORED:
                    // curCell.setState(CellState.MARKED);                    
                    if (curCell.getType() == MineCell.CELL_MINE) {
                        curCell.setState(CellState.EXPLORED_MINE_CORRECT);

                        MarkedWrongCells.remove(curCell);
                    } else {
                        curCell.setState(CellState.EXPLORED_MINE_WRONG);
                        MarkedWrongCells.add(curCell);
                    }
                    break;

                case EXPLORED_MINE_CORRECT:
                    curCell.setState(CellState.UN_EXPLORED);
                    break;

                case EXPLORED_MINE_WRONG:
                    curCell.setState(CellState.UN_EXPLORED);
                    MarkedWrongCells.remove(curCell);   // Important! remember to remove it from the "MarkedWrongCells" ArrayList
                    break;

                case EXPLORED_NUMBER:
                    System.out.println("There is a number here!");
                    continue;
            }

            System.out.println(this);  // draw the mine field

            boolean markedAllCorrect = true;
            for (MineCell cell : mineArray) {
                if (cell.getState() == CellState.UN_EXPLORED) {
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

    final static String STR_EMPTY = ".";
    final static String STR_MINE = "*";

    private int row;
    private int col;
    private int type;
    private int minesAdj;

    private CellState state;

    public MineCell() {
        this.row = -1;
        this.col = -1;
        this.type = CELL_EMPTY;
        this.minesAdj = -1;

        this.state = CellState.UN_EXPLORED;
    }

    public MineCell(int type) {
        this.row = -1;
        this.col = -1;
        this.type = type;
        this.minesAdj = -1;

        this.state = CellState.UN_EXPLORED;
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

    @Override
    public String toString() {
        switch (this.state) {
            case EXPLORED_MINE_CORRECT:

            case EXPLORED_MINE_WRONG:
                return STR_MINE;

            case EXPLORED_NUMBER:
                return Integer.toString(this.minesAdj);

            default:
                return STR_EMPTY;
        }
    }
}

enum CellState {
    UN_EXPLORED(0),
    EXPLORED_FREE(1),
    EXPLORED_NUMBER(2),
    EXPLORED_MINE_WRONG(3),
    EXPLORED_MINE_CORRECT(4);

    final int state;

    CellState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
// package minesweeper;

import java.util.ArrayList;
import java.util.Scanner;

public class MineSweeper {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int mines;

        System.out.print("How many mines do you want on the field? > ");
        mines = scanner.nextInt();

        MineField mineField = new MineField(MineField.SIZE);

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

    private MineCell[][] field;

    private ArrayList<MineCell> mineArray;  // an ArrayList contains all the mines

    public MineField(int size) {
        this.size = size;
        this.field = new MineCell[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.field[i][j] = new MineCell(MineCell.CELL_EMPTY);
            }
        }

        mineArray = new ArrayList<>();
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
        int randNum;
        int i = 0;
        int j = 0;

        // generate the k mines, uniformly at random
        for (int mine = 0; mine < mines; mine++) {
            while (true) {
                randNum = (int) (Math.random() * (this.size * this.size));

                i = randNum / this.size;
                j = randNum % this.size;

                if (this.field[i][j].getType() == MineCell.CELL_EMPTY) {
                    this.field[i][j].setType(MineCell.CELL_MINE);
                    
                    mineArray.add(this.field[i][j]);

                    break;
                }
            }
        }

        int minesAround; // number of mines around a cell

        // calculate the adjacent mines for each empty cell
        for (i = 0; i < this.size; i++) {
            for (j = 0; j < this.size; j++) {
                minesAround = 0;

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

                    this.field[i][j].setMinesAdj(minesAround);
                    
                    if (minesAround > 0) {
                        this.field[i][j].setState(CellState.MARKED_NUMBER);
                    }
                }
            }
        }
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

        System.out.println(this);  // draw the mine field

        while (true) {
            System.out.print("Set/delete mines marks (x and y coordinates): > ");
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

            row = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            col = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0

            curCell = this.field[row][col];

            switch (curCell.getState()) {
                case UNMARKED:
                    // curCell.setState(CellState.MARKED);                    
                    if (curCell.getType() == MineCell.CELL_MINE) {
                        curCell.setState(CellState.MARKED_RIGHT);

                        if (MarkedWrongCells.contains(curCell)) {
                            MarkedWrongCells.remove(curCell);
                        }
                    } else {
                        curCell.setState(CellState.MARKED_WRONG);
                        MarkedWrongCells.add(curCell);
                    }
                    break;

                case MARKED_RIGHT:
                    curCell.setState(CellState.UNMARKED);
                    break;

                case MARKED_WRONG:
                    curCell.setState(CellState.UNMARKED);
                    MarkedWrongCells.remove(curCell);   // Important! remember to remove it from the "MarkedWrongCells" ArrayList
                    break;

                case MARKED_NUMBER:
                    System.out.println("There is a number here!");
                    continue;
            }

            System.out.println(this);  // draw the mine field

            boolean markedAllCorrect = true;
            for (MineCell cell : mineArray) {
                if (cell.getState() == CellState.UNMARKED) {
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
            sb.append((i + 1)  + "|");

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
    final static String STR_MINE = "X";

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

        this.state = CellState.UNMARKED;
    }

    public MineCell(int type) {
        this.row = -1;
        this.col = -1;
        this.type = type;
        this.minesAdj = -1;

        this.state = CellState.UNMARKED;
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
            case MARKED_RIGHT:
                return STR_MINE;

            case MARKED_WRONG:
                return STR_MINE;

            case MARKED_NUMBER:
                return Integer.toString(this.minesAdj);

            default:
                return STR_EMPTY;
        }
    }
}

enum CellState {
    UNMARKED(0),
    // MARKED(1),
    MARKED_WRONG(1),
    MARKED_RIGHT(2),
    MARKED_NUMBER(3);    

    int state;

    CellState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }
}
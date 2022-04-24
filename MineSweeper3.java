// package minesweeper;

import java.util.ArrayList;
import java.util.Scanner;

public class MineSweeper3 {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int mines;

        System.out.print("How many mines do you want on the field? > ");
        mines = scanner.nextInt();

        MineField mineField = new MineField(MineField.SIZE);

        mineField.generateMineField(mines);

        System.out.println(mineField);

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

    public MineField(int size) {
        this.size = size;
        this.field = new MineCell[size][size];

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.field[i][j] = new MineCell(MineCell.CELL_EMPTY);
            }
        }
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
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                sb.append(this.field[i][j]);
            }
            sb.append("\n");
        }

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

    public MineCell() {
        this.row = -1;
        this.col = -1;
        this.type = CELL_EMPTY;
        this.minesAdj = -1;
    }

    public MineCell(int type) {
        this.row = -1;
        this.col = -1;
        this.type = type;
        this.minesAdj = -1;
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

    @Override
    public String toString() {
        if (this.type == CELL_MINE) {
            return STR_MINE;
        } else if (this.minesAdj > 0) {
            return Integer.toString(this.minesAdj);
        } else {
            return STR_EMPTY;
        }
    }
}
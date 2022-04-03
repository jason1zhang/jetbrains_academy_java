// package minesweeper;

public class MineSweeper {
    public static void main(String[] args) {
        // write your code here
        MineField mineField = new MineField(MineField.SIZE);
        System.out.println(mineField);
    }
}

class MineField {
    /**
     * constants definition
     */
    final static int SIZE = 9;

    private int size;
    private Cell[][] field;

    public MineField(int size) {
        this.size = size;
        this.field = new Cell[size][size];

        generateMineField();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * generate a random mine field
     */
    public void generateMineField() {
        int type;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                type = Math.random() <= 0.5 ? Cell.CELL_EMPTY : Cell.CELL_MINE;
                this.field[i][j] = new Cell(type);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                sb.append(field[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

class Cell {
    /**
     * constants definition
     */
    final public static int CELL_MINE = 1;
    final public static int CELL_EMPTY = 2;

    final static String STR_MINE = "X";
    final static String STR_EMPTY = ".";

    private int type;

    public Cell() {
        this.type = CELL_EMPTY;
    }

    public Cell(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type == CELL_EMPTY ? STR_EMPTY : STR_MINE;
    }
}

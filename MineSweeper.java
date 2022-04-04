import java.util.Scanner;

// package minesweeper;

public class MineSweeper {
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
     * generate the mine field with specified number of mines
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

class MineCell {
    /**
     * constants definition
     */
    final public static int CELL_MINE = 1;
    final public static int CELL_EMPTY = 2;

    final static String STR_MINE = "X";
    final static String STR_EMPTY = ".";

    private int type;

    public MineCell() {
        this.type = CELL_EMPTY;
    }

    public MineCell(int type) {
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

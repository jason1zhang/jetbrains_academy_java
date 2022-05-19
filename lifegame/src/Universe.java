import java.util.Random;

public class Universe {
    private Cell[][] board;

    public Universe(int size) {

        Random random = new Random();

        this.board = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    board[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    board[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }
    }


    public Universe(int size, int seed) {

        Random random = new Random(seed);

        this.board = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    board[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    board[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }
    }

    public Universe(Cell[][] board) {
        int size = board.length;
        this.board = new Cell[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = (Cell) board[i][j].clone();
            }
        }        
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public int getLiveCells() {
        int size = this.board.length;
        int count = 0;

        for (Cell[] cells : this.board) {
            for (int j = 0; j < size; j++) {
                if (cells[j].getState() == CellState.ALIVE) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int size = this.board.length;

        for (Cell[] cells : this.board) {
            for (int j = 0; j < size; j++) {
                sb.append(cells[j]);
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}

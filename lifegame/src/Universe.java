import java.util.Random;

public class Universe {
    private Cell[][] board;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int size = this.board.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(this.board[i][j]);
            }
            
            sb.append("\n");
        }

        return sb.toString();
    }
}

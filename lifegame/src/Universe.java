import java.util.Random;

public class Universe {
    private final static int SIZE = 50;
    private int generation;

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

        this.generation = 1;
    }

    public Universe() {
        this(SIZE);
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

        this.generation = 1;
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

    public void generate() {
        Universe nextUniverse = null;

        int generation = 1;
        int liveCells = 0;
        int LIMIT = 20;

        while (generation < LIMIT) {
            nextUniverse = generateNext();
            liveCells = nextUniverse.getLiveCells();

            System.out.println("Generation #" + generation++);
            System.out.println("Alive: " + liveCells);
            System.out.println(nextUniverse);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }    

    public Universe generate(int generations) {
        if (generations == 0) {
            return this;
        }

        Universe nextUniverse = null;

        for (int i = 0; i < generations; i++) {
            nextUniverse = generateNext();
        }

        return nextUniverse;
    }     

    public Universe generateNext() {
        int size = this.board.length;

        Cell[][] nextBoard = new Cell[size][size];

        int adjCells;
 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjCells = calcAdjCells(i, j);

                Cell cell = this.board[i][j];

                if ((cell.getState() == CellState.ALIVE && (adjCells == 2 || adjCells == 3))
                        || (cell.getState() == CellState.DEAD && adjCells == 3)) {
                    nextBoard[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    nextBoard[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }

        this.generation++;

        return new Universe(nextBoard);
    }    

    public int getGeneration() {
        return this.generation;
    }

    /**
     * calcuate the adjacent alive cells in a position.
     * If the cell has mine in it, always return 0.
     * 
     * @param i the x coordinate (row)
     * @param j the y coordinate (col)
     * @return the number of adjacent mines
     */
    private int calcAdjCells(int i, int j) {
        int size = this.board.length;
        
        int adjCells = 0;

        // north
        adjCells += this.board[(i - 1 + size) % size][j].getState().getValue();

        // south
        adjCells += this.board[(i + 1 + size) % size][j].getState().getValue();

        // west
        adjCells += this.board[i][(j - 1 + size) % size].getState().getValue();

        // east
        adjCells += this.board[i][(j + 1 + size) % size].getState().getValue();

        // north west
        adjCells += this.board[(i - 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // north east
        adjCells += this.board[(i - 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        // south west
        adjCells += this.board[(i + 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // south east
        adjCells += this.board[(i + 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        return adjCells;
    }

    
    public int getSize() {
        return SIZE;
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

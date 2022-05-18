import java.util.*;

/**
 * The 2nd development stage of the Jetbrains Academy project "Game of Life"
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-18 
 * 
 */

public class GameOfLife2 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        int seed = scanner.nextInt();
        int generations = scanner.nextInt();

        Universe currUniverse = new Universe(size, seed);

        Universe nextUniverse = generate(currUniverse, generations);

        System.out.println(nextUniverse);

        scanner.close();
    }

    public static Universe generate(Universe currUniverse, int generations) {
        if (generations == 0) {
            return currUniverse;
        }

        Universe universe = new Universe(currUniverse.getBoard());
        Universe nextUniverse = null;

        for (int i = 0; i < generations; i++) {
            nextUniverse = generateNext(universe);
            universe = new Universe(nextUniverse.getBoard());
        }

        return nextUniverse;
    }

    public static Universe generateNext(Universe currUniverse) {
        Cell[][] board = currUniverse.getBoard();
        int size = board.length;

        Cell[][] nextBoard = new Cell[size][size];

        int adjCells;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjCells = calcAdjCells(i, j, board);
                Cell cell = board[i][j];

                if (cell.getState() == CellState.ALIVE) {
                    if (adjCells == 2 || adjCells == 3) {
                        nextBoard[i][j] = new Cell(CellState.ALIVE, i, j);
                    } else {
                        nextBoard[i][j] = new Cell(CellState.DEAD, i, j);
                    }
                } else {
                    if (adjCells == 3) {
                        nextBoard[i][j] = new Cell(CellState.ALIVE, i, j);
                    } else {
                        nextBoard[i][j] = new Cell(CellState.DEAD, i, j);
                    }
                }
            }
        }

        return new Universe(nextBoard);
    }

    /**
     * calcuate the adjacent alive cells in a position.
     * If the cell has mine in it, always return 0.
     *
     * @param i the x coordinate (row)
     * @param j the y coordinate (col)
     * @return the number of adjacent mines
     */
    private static int calcAdjCells(int i, int j, Cell[][] board) {
        int size = board.length;

        int adjCells = 0;

        // north
        adjCells += board[(i - 1 + size) % size][j].getState().getValue();

        // south
        adjCells += board[(i + 1 + size) % size][j].getState().getValue();

        // west
        adjCells += board[i][(j - 1 + size) % size].getState().getValue();

        // east
        adjCells += board[i][(j + 1 + size) % size].getState().getValue();

        // north west
        adjCells += board[(i - 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // north east
        adjCells += board[(i - 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        // south west
        adjCells += board[(i + 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // south east
        adjCells += board[(i + 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        return adjCells;
    }
}

class Universe {
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

class Cell implements Cloneable {
    private CellState state;
    private int row;
    private int col;
    private int numNeighbor;

    public Cell() {
        this.state = CellState.DEAD;
        this.row = -1;
        this.col = -1;
        this.numNeighbor = 0;
    }

    public Cell(CellState state, int row, int col) {
        this.state = state;
        this.row = row;
        this.col = col;
        this.numNeighbor = 0;
    }

    public CellState getState() {
        return this.state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public int getNumNeighbor() {
        return this.numNeighbor;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setNumNeighbor(int numNeighbor) {
        this.numNeighbor = numNeighbor;
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

    @Override
    public String toString() {
        if (this.state == CellState.ALIVE) {
            return "O";
        } else {
            return " ";
        }
    }
}

enum CellState {
    ALIVE(1),
    DEAD(0);

    final int value;

    private CellState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
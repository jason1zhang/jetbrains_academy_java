import java.util.*;

public class GameOfLife {
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

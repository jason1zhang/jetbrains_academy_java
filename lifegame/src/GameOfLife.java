import java.io.IOException;
import java.util.*;

public class GameOfLife {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        // int seed = scanner.nextInt();
        // int generations = scanner.nextInt();

        // Universe currUniverse = new Universe(size, seed);
        Universe currUniverse = new Universe(size);

        // Universe nextUniverse = generate(currUniverse, generations);
        generate(currUniverse);

        // System.out.println(nextUniverse);

        scanner.close();
    }

    public static void generate(Universe currUniverse) {
        Universe universe = new Universe(currUniverse.getBoard());
        Universe nextUniverse = null;

        int generation = 1;
        int liveCells = 0;
        int LIMIT = 20;

        while (generation < LIMIT) {

            nextUniverse = generateNext(universe);
            liveCells = nextUniverse.getLiveCells();

            System.out.println("Generation #" + generation++);
            System.out.println("Alive: " + liveCells);
            System.out.println(nextUniverse);

            /*
            try {
                if (System.getProperty("os.name").contains("Windows"))
                    new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
                else
                    Runtime.getRuntime().exec("clear");
            }
            catch (IOException | InterruptedException e) {}
            */

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            universe = new Universe(nextUniverse.getBoard());
        }
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

                if ((cell.getState() == CellState.ALIVE && (adjCells == 2 || adjCells == 3))
                        || (cell.getState() == CellState.DEAD && adjCells == 3)) {
                    nextBoard[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    nextBoard[i][j] = new Cell(CellState.DEAD, i, j);
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

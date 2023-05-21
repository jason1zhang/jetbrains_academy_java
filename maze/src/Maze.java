import java.util.*;

public class Maze {
    private int width;
    private int height;
    private Cell[] grid;

    public Maze() {
        this.width = 10;
        this.height = 10;
        grid = new Cell[this.width *this.height];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                grid[getIndex(i, j)] = new Cell(i, j);
            }
        }
    }

    private int getIndex(int row, int col) {
        return row * this.width + col;
    }


    public Map<Cell, ArrayList<Cell>> buildAdjList() {
        Map<Cell, ArrayList<Cell>> adjList = new HashMap<>();
        
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Cell current = this.grid[getIndex(i, j)];
                adjList.put(current, new ArrayList<Cell>());

                // top
                if (i - 1 >= 0) {
                    adjList.get(current).add(this.grid[getIndex(i - 1, j)]);
                }

                // right
                if (j + 1 < this.width) {
                    adjList.get(current).add(this.grid[getIndex(i, j + 1)]);
                }

                // bottom
                if (i + 1 < this.height ) {
                    adjList.get(current).add(this.grid[getIndex(i + 1, j)]);
                }

                // left
                if (j - 1 >= 0) {
                    adjList.get(current).add(this.grid[getIndex(i, j - 1)]);
                }                

            }
        }

        return adjList;
    }

    /**
     * Set a random entry cell on any of the four borders
     * 
     * @param where
     * @return the entry cell
     */
    private Cell getEntryCell(int where) {
        Random rand = new Random();
        int row = 0;
        int col = 0;

        // on the left
        if (where == 0) {
            row = rand.nextInt(this.height - 2) + 1;
            col = 0;
        }

        // on the top
        if (where == 1) {
            row  = 0;
            col = rand.nextInt(this.width - 2) + 1;
        }

        // on the right
        if (where == 2) {
            row = rand.nextInt(this.height - 2) + 1;
            col = this.width - 1;
        }

        // on the bottom
        if (where == 3) {
            row  = this.height - 1;
            col = rand.nextInt(this.width - 2) + 1;
        }

        return new Cell(row, col);
    }

    public static int[][] generateSampleMaze() {
        int[][] gridSample = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 1, 0, 1, 0, 1, 1, 0 },
                { 0, 1, 0, 1, 1, 1, 0, 1, 0, 0 },
                { 0, 1, 1, 1, 0, 0, 0, 1, 1, 1 },
                { 0, 1, 0, 1, 1, 1, 1, 1, 0, 0 },
                { 0, 1, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 1, 0, 1, 0, 1, 1, 1, 0, 0 },
                { 0, 1, 0, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 1, 0, 1, 1, 1, 0, 1, 1, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        return gridSample;
    }

    public void draw() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                System.out.print(grid[getIndex(i, j)]);
            }

            System.out.println();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Cell[] getGrid() {
        return this.grid;
    }

    public void setGrid(Cell[] grid) {
        this.grid = grid;
    }
}

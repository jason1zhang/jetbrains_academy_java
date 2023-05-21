import java.util.Objects;

public class Cell {
    private int row;
    private int col;
    private boolean visited;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
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

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Cell cell = (Cell) obj;
        return this.row == cell.getRow() && this.col == cell.getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.col);
    }

    @Override
    public String toString() {
        return this.visited ? "  " : "\u2588\u2588";
    }
}

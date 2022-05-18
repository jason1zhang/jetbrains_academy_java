public class Cell implements Cloneable {
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

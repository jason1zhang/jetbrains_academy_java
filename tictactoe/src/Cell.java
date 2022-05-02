
public class Cell implements Cloneable{
    private int row;
    private int col;
    private int cellType;   // 0: CELL_EMPTY; 1: CELL_X; 2: CELL_O

    public Cell() {
        this.row = -1;
        this.col = -1;
        this.cellType = Game.CELL_EMPTY;
    }

    public Cell(int type) {
        this.row = -1;
        this.col = -1;
        this.cellType = type;
    }

    public Cell(int row, int col, int cellType) {
        this.row = row;
        this.col = col;
        this.cellType = cellType;
    }

    public Cell(Cell cell) {
        this.row = cell.getRow();
        this.col = cell.getCol();
        this.cellType = cell.getCellType();
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

    public int getCellType() {
        return this.cellType;
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
    }

    public void draw() {
        System.out.print(this);
    }

    // @Override
    public String toString() {
        String strType;

        switch (this.cellType) {
            case Game.CELL_X:
                strType = Game.STR_CELL_X;
                break;
            case Game.CELL_O:
                strType = Game.STR_CELL_O;
                break;
            default:
                strType = Game.STR_CELL_EMPTY;
                break;
        }

        return strType;
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
}

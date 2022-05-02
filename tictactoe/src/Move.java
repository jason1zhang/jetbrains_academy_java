
public class Move implements Cloneable{
    private int score;
    private Cell cell;

    public Move() {
        this.score = Game.SCORE_MIN;
        this.cell = new Cell();
    }

    public Move(Cell cell) {
        this.score = Game.SCORE_MIN;
        this.cell = (Cell) cell.clone();
    }

    public Move(int score, Cell cell) {
        this.score = score;
        this.cell = (Cell) cell.clone();
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = (Cell) cell.clone();
    }

    // @Override
    public String toString() {
        return "[" + this.cell + ", score -> (" + this.score + ")]";
    }

    public Object clone() {
        Move copy = null;

        try {
            copy = (Move) super.clone();
            copy.cell = (Cell) this.cell.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }
}
import javax.swing.JButton;
import java.awt.*;

class CellButton extends JButton {

    private Cell cell;          // each CellButton contains one Cell object
    private boolean isClicked;

    public CellButton() {
        super();

        this.cell = new Cell();        
        this.setBackground(Color.LIGHT_GRAY);

        this.isClicked = false;

        this.setEnabled(false);        
    }

    public CellButton(String text, String name) {
        super(text);

        this.setName(name);
        this.setFont(new Font("Arial", Font.BOLD, 40));
        this.setBackground(Color.LIGHT_GRAY);

        this.cell = new Cell();
        this.isClicked = false;

        this.setEnabled(false);
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = (Cell)cell.clone();
    }

    public boolean getIsClicked() {
        return this.isClicked;
    }

    public void setIsclicked(boolean isClicked) {
        this.isClicked = isClicked;
    }
}

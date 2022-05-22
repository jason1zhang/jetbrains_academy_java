import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel{
    private Universe universe;

    public CellPanel(Universe universe) {
        super();

        this.universe = universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.GRAY);

        int PAD = 20;
        int posX = PAD;
        int posY = PAD;
        int universeSize = universe.getSize();
        int xSize = (getWidth() - 2 * PAD) / universeSize;
        int ySize = (getHeight() - 2 * PAD) / universeSize;

        // draw the rows
        for (int i = 0; i <= universeSize; i++) {
            g2.drawLine(posX, posY + i * ySize, posX + xSize * universeSize, posY + i * ySize);
        }

        // draw the columns
        for (int i = 0; i <= universeSize; i++) {
            g2.drawLine(posX + i * xSize, posY, posX + i * xSize, posY + ySize * universeSize);
        }         

        g2.setColor(Color.DARK_GRAY);
        for (Cell[] row : universe.getBoard()) {
            posX = PAD;
            for (Cell cell : row) {
                if (cell.getState() == CellState.ALIVE) {                    
                    g2.fillRect(posX, posY, xSize, ySize);
                }                
                posX += xSize;
            }
            posY += ySize;
        }
    }
}

import javax.swing.JPanel;
import java.awt.*;

public class Board extends JPanel{
    protected int size;
    protected Cell[] board;

    public Board() {
        super();

        this.size = Game.SIZE;
        this.board = new Cell[this.size * this.size];

        initComponents();

        setLayout(new GridLayout(3, 3));
        setVisible(true);
    }

    private void initComponents() {
        char chAlpha = 'A';
        char chNum = '3';
        String text = null;

        for (int i = 0; i < this.board.length; i++) {
            text = "" + chAlpha + chNum;
            this.board[i] = new Cell(text, "Button" + text);
            add(this.board[i]);
            
            chAlpha++;
            if (chAlpha == 'D') {
                chNum--;
                chAlpha = 'A';
            }
        }
    }    
}

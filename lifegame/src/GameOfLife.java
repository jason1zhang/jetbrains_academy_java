import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame{
    private JLabel labelGeneation;
    private JLabel labelAlive;

    private final static int WIDTH = 600;
    private final static int HEIGHT = 700;

    private Universe universe;    
    private int generation;

    public static void main(String[] args) throws Exception {
        GameOfLife game = new GameOfLife();
        game.startGame();
    }      

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Of Life");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        setLayout(new BorderLayout());

        initComponents();

        this.universe = new Universe();
        this.generation = 0;
    }

    private void initComponents() {
        // construct a panel to hold the "Generation" label and "Alive" label
        JPanel panelStatus = new JPanel();        
        this.labelGeneation = new JLabel("   Generation #");
        this.labelGeneation.setName("GenerationLabel");
        this.labelGeneation.setFont(new Font("Courier", Font.BOLD, 16));
        this.labelGeneation.setForeground(Color.BLACK);
        this.labelGeneation.setHorizontalAlignment(SwingConstants.LEFT);

        this.labelAlive = new JLabel("   Alive: ");
        this.labelAlive.setName("AliveLabel");
        this.labelAlive.setFont(new Font("Courier", Font.BOLD, 16));
        this.labelAlive.setForeground(Color.BLACK);
        this.labelAlive.setHorizontalAlignment(SwingConstants.LEFT);

        panelStatus.setLayout(new BorderLayout(5, 5));
        panelStatus.add(this.labelGeneation, BorderLayout.NORTH);
        panelStatus.add(this.labelAlive, BorderLayout.SOUTH);

        this.add(panelStatus, BorderLayout.NORTH);
    }

    public void startGame() {
        int LIMIT = 20;
        while (this.generation <= LIMIT) {
            repaint();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.universe = this.universe.generateNext();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2.0f));
        g2.setColor(Color.GRAY);

        int startX = 30;
        int startY = 120;
        int length = (int)(HEIGHT - 1.5 * startY);
        int size = this.universe.getSize();
        int gridSize = length / (size);

        // draw the rows
        for (int i = 0; i <= size; i++) {
            g2.drawLine(startX, startY + i * gridSize, startX + length, startY + i * gridSize);
        }
        
        // draw the columns
        for (int i = 0; i <= size; i++) {
            g2.drawLine(startX + i * gridSize, startY, startX + i * gridSize, startY + length);
        }

        g2.setColor(Color.BLACK);
        Cell[][] board = this.universe.getBoard();
        int liveCells = this.universe.getLiveCells();
        this.generation++;

        this.labelGeneation.setText("   Generation #" + this.generation);
        this.labelAlive.setText("   Alive: " + liveCells);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getState() == CellState.ALIVE) {
                    g2.fillRect(startX + j * gridSize, startY + i * gridSize, gridSize, gridSize);
                }
            }
        }
    }
}

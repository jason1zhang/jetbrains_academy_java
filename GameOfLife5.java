import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * The 5th development stage of the Jetbrains Academy project "Game of Life"
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-24
 * 
 */

public class GameOfLife5 extends JFrame{
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 900;

    private JLabel geneationLabel;
    private JLabel aliveLabel;

    private JButton buttonReset;
    private JToggleButton buttonPlayToggle;

    private boolean isPaused;
    private CellPanel cellPanel;
    private UniverseEvolution universeEvolution;

    private Universe universe;
    private int generation;

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(GameOfLife5::new);
    }

    public GameOfLife5() {
        super("Game of Life");

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(2, 2));
        setResizable(false);

        // set up control panel
        JPanel controlPanel = new JPanel(new BorderLayout(2, 2));
        controlPanel.setSize(WIDTH/4, HEIGHT/4);

        // set up button panel
        JPanel buttonPanel = new JPanel(new BorderLayout(2, 2));
        buttonPanel.setSize(WIDTH/4, HEIGHT/16);

        this.buttonPlayToggle = new JToggleButton("Resume");
        this.buttonPlayToggle.setName(("PlayToggleButton"));
        actOnToggleButton();

        this.buttonReset = new JButton("Reset");
        this.buttonReset.setName("ResetButton");
        this.buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                universeEvolution.interrupt();
            }
        });

        buttonPanel.add(this.buttonPlayToggle, BorderLayout.WEST);
        buttonPanel.add(this.buttonReset, BorderLayout.EAST);
        controlPanel.add(buttonPanel, BorderLayout.NORTH);

        // set up labels panel
        JPanel labelPanel = new JPanel();
        labelPanel.setSize(WIDTH/4, HEIGHT/16);

        this.geneationLabel = new JLabel();
        this.geneationLabel.setName("GenerationLabel");
        this.geneationLabel.setFont(new Font("Courier", Font.BOLD, 15));
        this.geneationLabel.setForeground(Color.BLACK);
        this.geneationLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.geneationLabel.setBounds(0, 0, WIDTH/4, 50);

        this.aliveLabel = new JLabel();
        this.aliveLabel.setName("AliveLabel");
        this.aliveLabel.setFont(new Font("Courier", Font.BOLD, 15));
        this.aliveLabel.setForeground(Color.BLACK);
        this.aliveLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.aliveLabel.setBounds(50, 50, WIDTH/4, 50);

        labelPanel.add(this.geneationLabel);
        labelPanel.add(this.aliveLabel);
        controlPanel.add(labelPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.WEST);

        this.isPaused = true;

        // set up cell panel
        this.universe = new Universe();
        this.generation = 1;
        setLabels();

        cellPanel = new CellPanel(this.universe);
        cellPanel.setSize(new Dimension(getWidth() - controlPanel.getWidth(), getHeight()));
        add(cellPanel, BorderLayout.CENTER);    // Important: do NOT set to BorderLayout.EAST
        setVisible(true);

        universeEvolution = new UniverseEvolution();
        universeEvolution.start();
    }

    private class UniverseEvolution extends Thread {
        public void resumeThread() {
            synchronized(this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (universe.getLiveCells() > 0) {
                try {
                    synchronized(this) {
                        while (isPaused) {
                            wait();
                        }
                    }

                    universe = universe.generateNext();
                    generation++;
                    cellPanel.setUniverse(universe);

                    Thread.sleep(150L);
                    setLabels();
                    cellPanel.repaint();
                } catch (InterruptedException e) {
                    universe = new Universe();
                    generation = 1;
                    setLabels();
                }
            }
        }
    }

    private void setLabels() {
        geneationLabel.setText(String.format("   Generation #%d", generation));
        aliveLabel.setText(String.format("   Alive: %d", universe.getLiveCells()));
    }

    private void actOnToggleButton() {
        ItemListener ItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();

                if (state == ItemEvent.SELECTED) {
                    isPaused = false;
                    buttonPlayToggle.setText("Pause");
                    universeEvolution.resumeThread();
                } else {
                    isPaused = true;
                    buttonPlayToggle.setText("Resume");
                }
            }
        };

        this.buttonPlayToggle.addItemListener(ItemListener);
    }
}

class Universe {
    private final static int SIZE = 50;
    private int generation;

    private Cell[][] board;

    public Universe(int size) {

        Random random = new Random();

        this.board = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    board[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    board[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }

        this.generation = 1;
    }

    public Universe() {
        this(SIZE);
    }

    public Universe(int size, int seed) {

        Random random = new Random(seed);

        this.board = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    board[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    board[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }

        this.generation = 1;
    }

    public Universe(Cell[][] board) {
        int size = board.length;
        this.board = new Cell[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = (Cell) board[i][j].clone();
            }
        }        
    }

    public Cell[][] getBoard() {
        return this.board;
    }

    public int getLiveCells() {
        int size = this.board.length;
        int count = 0;

        for (Cell[] cells : this.board) {
            for (int j = 0; j < size; j++) {
                if (cells[j].getState() == CellState.ALIVE) {
                    count++;
                }
            }
        }

        return count;
    }

    public void generate() {
        Universe nextUniverse = null;

        int generation = 1;
        int liveCells = 0;
        int LIMIT = 20;

        while (generation < LIMIT) {
            nextUniverse = generateNext();
            liveCells = nextUniverse.getLiveCells();

            System.out.println("Generation #" + generation++);
            System.out.println("Alive: " + liveCells);
            System.out.println(nextUniverse);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }    

    public Universe generate(int generations) {
        if (generations == 0) {
            return this;
        }

        Universe nextUniverse = null;

        for (int i = 0; i < generations; i++) {
            nextUniverse = generateNext();
        }

        return nextUniverse;
    }     

    public Universe generateNext() {
        int size = this.board.length;

        Cell[][] nextBoard = new Cell[size][size];

        int adjCells;
 
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjCells = calcAdjCells(i, j);

                Cell cell = this.board[i][j];

                if ((cell.getState() == CellState.ALIVE && (adjCells == 2 || adjCells == 3))
                        || (cell.getState() == CellState.DEAD && adjCells == 3)) {
                    nextBoard[i][j] = new Cell(CellState.ALIVE, i, j);
                } else {
                    nextBoard[i][j] = new Cell(CellState.DEAD, i, j);
                }
            }
        }

        this.generation++;

        return new Universe(nextBoard);
    }    

    public int getGeneration() {
        return this.generation;
    }

    /**
     * calcuate the adjacent alive cells in a position.
     * If the cell has mine in it, always return 0.
     * 
     * @param i the x coordinate (row)
     * @param j the y coordinate (col)
     * @return the number of adjacent mines
     */
    private int calcAdjCells(int i, int j) {
        int size = this.board.length;
        
        int adjCells = 0;

        // north
        adjCells += this.board[(i - 1 + size) % size][j].getState().getValue();

        // south
        adjCells += this.board[(i + 1 + size) % size][j].getState().getValue();

        // west
        adjCells += this.board[i][(j - 1 + size) % size].getState().getValue();

        // east
        adjCells += this.board[i][(j + 1 + size) % size].getState().getValue();

        // north west
        adjCells += this.board[(i - 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // north east
        adjCells += this.board[(i - 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        // south west
        adjCells += this.board[(i + 1 + size) % size][(j - 1 + size) % size].getState().getValue();

        // south east
        adjCells += this.board[(i + 1 + size) % size][(j + 1 + size) % size].getState().getValue();

        return adjCells;
    }

    
    public int getSize() {
        return SIZE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int size = this.board.length;

        for (Cell[] cells : this.board) {
            for (int j = 0; j < size; j++) {
                sb.append(cells[j]);
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}

class CellPanel extends JPanel{
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

class Cell implements Cloneable {
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

enum CellState {
    ALIVE(1),
    DEAD(0);

    final int value;

    private CellState(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}


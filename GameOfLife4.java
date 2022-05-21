import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The 4th development stage of the Jetbrains Academy project "Game of Life"
 *
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-21
 * 
 */

public class GameOfLife4 extends JFrame{
    private JLabel labelGeneation;
    private JLabel labelAlive;

    private final static int WIDTH = 600;
    private final static int HEIGHT = 700;

    private Universe universe;    
    private int generation;

    public static void main(String[] args) throws Exception {
        GameOfLife4 game = new GameOfLife4();
        game.startGame();
    }      

    public GameOfLife4() {
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

class Universe {
    private final static int SIZE = 10;

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

        return new Universe(nextBoard);
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
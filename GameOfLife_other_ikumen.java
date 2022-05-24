import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 
 * This program is from Jetbrains Academy project - Game of Life.
 * 
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-24
 *
 * Note: the code below is taken from other submission written by ikumen, as a learning sample.
 */

public class GameOfLife_other_ikumen extends JFrame implements GenerationObserver {

  static final String GEN_LABEL = "Generation #1";
  static final String POP_LABEL = "Alive: 0";

  private JLabel generationLabel;
  private JLabel populationLabel;
  private Cells cellsPanel;
  private final Engine engine;

  public static void main(String[] args) {
    new GameOfLife_other_ikumen();
  }

  public GameOfLife_other_ikumen() {
    engine = new Engine()
      .addGenerationObserver(this);

    final JToggleButton pauseResumeBtn = new JToggleButton("Play");
    pauseResumeBtn.setName("PlayToggleButton");
    pauseResumeBtn.addActionListener(e -> {
      engine.pauseResume();
      if (engine.stopped) pauseResumeBtn.setText("Play");
      else pauseResumeBtn.setText("Pause");
    });
    JButton restartBtn = new JButton("Restart");
    restartBtn.setName("ResetButton");
    restartBtn.addActionListener(e -> engine.restart());

    JPanel controls = new JPanel();
    controls.setSize(200, 50);
    controls.setLayout(new BoxLayout(controls, BoxLayout.X_AXIS));
    controls.add(pauseResumeBtn);
    controls.add(restartBtn);

    generationLabel = new JLabel(GEN_LABEL);
    generationLabel.setName("GenerationLabel");
    populationLabel = new JLabel(POP_LABEL);
    populationLabel.setName("AliveLabel");

    JSlider slider = new JSlider(0, engine.delays.length-1, engine.delay);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(1);
    slider.addChangeListener(e -> {
      JSlider source = (JSlider) e.getSource();
      if (!source.getValueIsAdjusting()) {
        engine.delay = (int) source.getValue();
      }
    });
    JPanel leftPane = new JPanel();
    leftPane.setSize(200, 500);
    leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
    leftPane.add(controls);
    leftPane.add(generationLabel);
    leftPane.add(populationLabel);
    leftPane.add(slider);

    cellsPanel = new Cells(engine.size, engine.generation.cells);

    pack();
    setTitle("Game of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    add(leftPane, BorderLayout.WEST);
    add(cellsPanel, BorderLayout.CENTER);

    //setLayout(new GridLayout(3, 1, 0, 0));
    setLocationByPlatform(true);
    pack();
    setSize(700, 500);
    setVisible(true);

    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        engine.close();
      }
    });
  }

  @Override
  public void onEvolve(Generation generation) {
    generationLabel.setText(GEN_LABEL + generation.year);
    populationLabel.setText(POP_LABEL + generation.population);
    cellsPanel.cells = generation.cells;
    repaint();
  }

  /**
   * Class representing the cells in our game.
   */
  static class Cells extends JPanel {
    Boolean[][] cells;
    int size;

    public Cells(int size, Boolean[][] cells) {
      setSize(size * size, size * size);
      this.size = size;
      this.cells = cells;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      for (int r=0; r < cells.length; r++) {
        for (int c=0; c < cells[0].length; c++) {
          if (cells[r][c]) {
            g.fillRect(r * size, c * size, size, size);
          } else {
            g.drawRect(r * size, c * size, size, size);
          }
        }
      }
    }
  }

  /**
   * Class representing the engine that drives our game.
   */
  static class Engine implements Runnable {
    /** Number of cells (size x size) */
    public static final int DEFAULT_SIZE = 20;

    final int size;
    final long seed;
    final long[] delays = {50, 100, 225, 375, 500, 700, 900, 1200, 1500, 2000};
    int delay = 0;
    Generation generation; // current generation
    Thread thread;

    private final List<GenerationObserver> observers = new ArrayList<>();
    private final Random random;
    private boolean stopped = true;

    void close() {
      if (thread != null && !thread.isInterrupted())
        thread.interrupt();
    }
    /**
     * Construct engine with default size, and random seed.
     */
    Engine() {
      this(DEFAULT_SIZE, System.currentTimeMillis());
    }

    /**
     * Construct engine with given size and seed for generating
     * initial population.
     * @param size
     * @param seed
     */
    Engine(int size, long seed) {
      this.size = size;
      this.seed = seed;
      this.random = new Random(seed);
      this.generation = initGeneration();
    }

    /**
     * Create an initial {@link Generation} with no population.
     */
    private Generation initGeneration() {
      Boolean[][] cells = IntStream.range(0, size)
        .mapToObj(row -> IntStream.range(0, size)
          .mapToObj(col -> false)
          .toArray(Boolean[]::new))
        .toArray(Boolean[][]::new);
      return new Generation(cells, 0, 0);
    }

    /**
     * Add a {@link GenerationObserver}, later to be notified of
     * {@link Generation} updates.
     * @return
     */
    public Engine addGenerationObserver(GenerationObserver observer) {
      observers.add(observer);
      return this; // for fluent-ness
    }

    /**
     * Notify all {@link GenerationObserver}s of new generation.
     */
    void notifyObservers() {
      for (GenerationObserver observer: observers)
        observer.onEvolve(generation);
    }

    @Override
    public void run() {
      try {
        while (true) {
          Thread.sleep(delays[delay]);
          if (!stopped)
            evolve();
        }
      } catch (InterruptedException e) {
        //e.printStackTrace();
      }
    }

    private void restart() {
      stopped = true;
      if (thread != null)
        thread.interrupt();
      seedGeneration();
      notifyObservers();
      thread = new Thread(this);
      thread.start();
      stopped = false;
    }

    public void pauseResume() {
      if (!stopped) {
        stopped = true;
      } else {
        if (generation.year == 0) {
          restart();
        } else {
          stopped = false;
        }
      }
    }

    /**
     * Seed a completely new {@link Generation}.
     */
    void seedGeneration() {
      int population = 0;
      Boolean[][] cells = new Boolean[size][size];
      for (int r=0; r < size; r++) {
        for (int c=0; c < size; c++) {
          cells[r][c] = random.nextBoolean();
          if (cells[r][c]) population++;
        }
      }
      this.generation = new Generation(cells, 1, population);
    }

    /**
     * @return a new {@link Generation} given the current generation.
     */
    void evolve() {
      // No generation to base off, seed a new one.
      if (generation == null || generation.year == 0) {
        seedGeneration();
      } else {
        int population = 0;
        Boolean[][] nextCells = new Boolean[generation.cells.length][generation.cells[0].length];
        for (int r = 0; r < generation.cells.length; r++) {
          for (int c = 0; c < generation.cells[0].length; c++) {
            int liveNeighbors = getLiveNeighbors(generation.cells, r, c);
            nextCells[r][c] = generation.cells[r][c]
              // if alive, continue if not bored or over populated
              ? (liveNeighbors == 2 || liveNeighbors == 3)
              // if dead, come back fomo
              : (liveNeighbors == 3);
            if (nextCells[r][c])
              population++;
          }
        }
        this.generation = new Generation(nextCells, generation.year + 1, population);
        notifyObservers();
      }
    }

    /**
     * @return count of live neighbors given a matrix and cell.
     */
    private static int getLiveNeighbors(Boolean[][] cells, int row, int col) {
      int live = 0;
      for (int[] n : getNeighbors(cells, row, col))
        if (cells[n[0]][n[1]])
          live++;
      return live;
    }

    /**
     * @return list of neighbors given a matrix and cell.
     */
    private static int[][] getNeighbors(Boolean[][] cells, int row, int col) {
      int n = row > 0 ? row - 1 : cells.length - 1;
      int s = row == cells.length - 1 ? 0 : row + 1;
      int e = col == cells[0].length - 1 ? 0 : col + 1;
      int w = col > 0 ? col - 1 : cells[0].length - 1;
      return new int[][]{{n, w}, {n, col}, {n, e}, {row, e},
        {s, e}, {s, col}, {s, w}, {row, w}};
    }
  }
}

class Generation {
    final Boolean[][] cells;
    final int year;
    final int population;
  
    public Generation(Boolean[][] cells, int year, int population) {
      this.cells = cells;
      this.year = year;
      this.population = population;
    }
}

interface GenerationObserver {
    void onEvolve(Generation generation);
}
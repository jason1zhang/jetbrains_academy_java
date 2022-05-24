import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * 
 * This program is from Jetbrains Academy project - Game of Life.
 * 
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-24
 *
 * Note: the code below is taken from other submission written by Dominik Grochowski, as a learning sample.
 */

public class GameOfLife_other_dominik_grochowski {
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
//        game.run();
    }
}


class WorldController {
    private final GameOfLife gameOfLifeView;
    private final ViewWorld worldMap;
    private ModelWorld originalWorld;
    private ModelWorld worldModel;
    private int actualGeneration = 0;
    private int waitMilliSec;
    private boolean isPlay = false;
    private int numberOFGenerations = 100;
    private boolean isReset = false;
    private boolean isNotEndGeneration = true;

    WorldController(GameOfLife gameOfLifeView, ModelWorld worldModel) {
        originalWorld = new ModelWorld(worldModel);
        this.gameOfLifeView = gameOfLifeView;
        this.worldModel = worldModel;
        waitMilliSec = gameOfLifeView.getsSpeedEvolutionSlide().getValue();
        worldMap = gameOfLifeView.getViewWorld();
        updateView();
        initListener();
    }

    void initListener() {
        gameOfLifeView.getsSpeedEvolutionSlide().addChangeListener(e -> setMilliSec(gameOfLifeView
                .getsSpeedEvolutionSlide().getValue()));
        gameOfLifeView.getBPauseButton().addActionListener(e -> isPlay = false);
        gameOfLifeView.getBPlayButton().addActionListener(e -> isPlay = true);
        gameOfLifeView.getBResetButton().addActionListener(e -> resetGame());
        gameOfLifeView.getBAcceptParamNewWorld().addActionListener(e -> {
            isPlay = false;
            gameOfLifeView.getBPlayButton().setSelected(false);
            try {
                long seed = Long.parseLong(gameOfLifeView.getTSeedWorld().getText());
                int size = Integer.parseInt(gameOfLifeView.getTSizeWorld().getText());
                int generations = Integer.parseInt(gameOfLifeView.getTNumberOfGenerations().getText());
                if (size > 50) {
                    JOptionPane.showMessageDialog(gameOfLifeView, "Size should less than or equals 50.");
                } else {
                    setParamsNewWorld(size, seed, generations);
                    JOptionPane.showMessageDialog(gameOfLifeView, "All params correct :)");
                }

            } catch (NumberFormatException a) {
                JOptionPane.showMessageDialog(gameOfLifeView, "All params must be integers!!");
            }
        });

        gameOfLifeView.getBChoseColor().addActionListener(e -> {
            isPlay = false;
            gameOfLifeView.getBPlayButton().setSelected(false);
            worldMap.setCellColor(JColorChooser.
                    showDialog(gameOfLifeView, "Choose", Color.CYAN));
            worldMap.repaint();
        });
    }

    void run() {
        while (gameOfLifeView.isActive()) {
            while (actualGeneration < numberOFGenerations && isNotEndGeneration) {
                if (isPlay) {
                    ++actualGeneration;
                    updateView();
                    generateNextWorld();
                    sleepMode();
                } else if (isReset) {
                    isReset = false;
                    worldModel = originalWorld;
                    actualGeneration = 0;
                    updateView();
                } else {
                    sleepMode();
                }
            }
            isNotEndGeneration = false;
            actualGeneration = 0;
            sleepMode();
        }
    }

    void setParamsNewWorld(int sizeWorld, long seedWorld, int numberOFGenerations) {
        originalWorld = new ModelWorld(sizeWorld, seedWorld);
        worldModel = new ModelWorld(sizeWorld, seedWorld);
        this.numberOFGenerations = numberOFGenerations;
        actualGeneration = 0;
        updateView();
    }

    void setMilliSec(int waitMilliSec) {
        this.waitMilliSec = waitMilliSec;
    }

    private void sleepMode() {
        try {
            Thread.sleep(waitMilliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void generateNextWorld() {
        NextGenerationsWorld.nextGenerationWorld(worldModel);
    }

    void updateView() {
        gameOfLifeView.setNumGenerationLabel(actualGeneration);
        gameOfLifeView.setNumAliveCells(worldModel.getLiveCells());
        worldMap.setWorldModel(worldModel);
        worldMap.repaint();
    }

    void resetGame() {
        isPlay = false;
        isNotEndGeneration = true;
        actualGeneration = 0;
        gameOfLifeView.getsSpeedEvolutionSlide().setValue(50);
        worldModel.setMatrixWorldModel(originalWorld.getMatrix());
        updateView();
    }
}

class NextGenerationsWorld {

    private static boolean setStatusCell(boolean[][] worldToCheck, int actualRow, int actualColumn) {
        int maxNum = worldToCheck.length;
        int liveCell = 0;

        for (int i = 0, row; i < 3; i++) {
            for (int j = 0, col; j < 3; j++) {

                if (!(i - 1 == 0 && j - 1 == 0)) {
                    row = actualRow + i - 1;
                    row = row < 0 ? maxNum - 1 : row >= maxNum ? 0 : row;

                    col = actualColumn + j - 1;
                    col = col < 0 ? maxNum - 1 : col >= maxNum ? 0 : col;

                    liveCell += worldToCheck[row][col] ? 1 : 0;
                }
            }
        }

        if (!worldToCheck[actualRow][actualColumn] && liveCell == 3) {
            return true;
        }
        return worldToCheck[actualRow][actualColumn] && (liveCell == 2 | liveCell == 3);
    }

    static void nextGenerationWorld(ModelWorld worldModel) {
        boolean[][] newMatrix = new boolean[worldModel.getSizeWorld()][worldModel.getSizeWorld()];
        for (int i = 0; i < worldModel.getSizeWorld(); i++) {
            for (int j = 0; j < worldModel.getSizeWorld(); j++) {
                newMatrix[i][j] = setStatusCell(worldModel.getMatrix(), i, j);
            }
        }
        worldModel.setMatrixWorldModel(newMatrix);
    }
}

class GameOfLife extends JFrame {
    private final JLabel lGenerationLabel;
    private final JLabel lAliveCellsLabel;
    private final JSlider sSpeedEvolutionSlide;
    private final JTextField tSizeWorld;
    private final JTextField tSeedWorld;
    private final JTextField tNumberOfGenerations;
    private final JToggleButton bPlayButton;
    private final JToggleButton bPauseButton;
    private final JButton bChoseColor;
    private final JButton bResetButton;
    private final JButton bAcceptParamNewWorld;
    private final ViewWorld viewWorld = new ViewWorld(new ModelWorld(50));

    public GameOfLife() {
        super("Game of life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        //Minor panels
        JPanel pButtonPanel = new JPanel();
        pButtonPanel.setLayout(new GridLayout(3, 2));
        pButtonPanel.setMaximumSize(new Dimension(300, 100));

        JPanel pLabelPanel = new JPanel();
        pLabelPanel.setLayout(new GridLayout(2, 1));
        pLabelPanel.setMaximumSize(new Dimension(150, 30));

        JPanel pToolsPanel = new JPanel();
        pToolsPanel.setLayout(new BoxLayout(pToolsPanel, BoxLayout.Y_AXIS));

        //Add to west panel section
        westPanel.add(pButtonPanel);
        westPanel.add(pLabelPanel);
        westPanel.add(pToolsPanel);

        //Label section
        lGenerationLabel = new JLabel("Generation #0");
        lGenerationLabel.setName("GenerationLabel");

        lAliveCellsLabel = new JLabel("Alive: 0");
        lAliveCellsLabel.setName("AliveLabel");

        //button panel Section
        bPlayButton = new JToggleButton("Play");

        bPauseButton = new JToggleButton("Pause");

        bPlayButton.setName("PlayToggleButton");
        bPlayButton.addActionListener(e -> {
            bPlayButton.setSelected(true);
            bPauseButton.setSelected(false);
        });

        bPauseButton.addActionListener(e -> {
            bPlayButton.setSelected(false);
            bPauseButton.setSelected(true);
        });

        bResetButton = new JButton("Reset");
        bResetButton.setName("ResetButton");
        bResetButton.addActionListener(e -> {
            bPlayButton.setSelected(false);
            bPauseButton.setSelected(true);
        });

        bChoseColor = new JButton("Chose color");

        //Tools button section
        JLabel lSpeedModeLabel = new JLabel("Speed mode");

        sSpeedEvolutionSlide = new JSlider(0, 2000, 50);
        sSpeedEvolutionSlide.setName("Speed mode");
        sSpeedEvolutionSlide.setFont(new Font("Monospaced", Font.PLAIN, 10));
        sSpeedEvolutionSlide.setMajorTickSpacing(500);
        sSpeedEvolutionSlide.setMinorTickSpacing(100);
        sSpeedEvolutionSlide.setPaintTrack(true);
        sSpeedEvolutionSlide.setPaintTicks(true);
        sSpeedEvolutionSlide.setPaintLabels(true);

        JPanel pInputPanel = new JPanel();
        pInputPanel.setLayout(new GridLayout(4, 2));
        pInputPanel.setMaximumSize(new Dimension(200, 110));

        //pInputPanel in tool
        tSizeWorld = new JTextField("Numbers only");
        tSizeWorld.setSize(new Dimension(50, 30));
        tSizeWorld.setMaximumSize(new Dimension(50, 30));

        tSeedWorld = new JTextField("Numbers only");
        tSeedWorld.setMaximumSize(new Dimension(50, 30));

        tNumberOfGenerations = new JTextField("Numbers only");
        tNumberOfGenerations.setSize(new Dimension(50, 30));

        bAcceptParamNewWorld = new JButton("Set world");

        JLabel lSizeWorldLabel = new JLabel("Size world");
        JLabel lSeedRandom = new JLabel("Seed random");
        JLabel lGenerationSetLabel = new JLabel("Generations");

        //Add to minor panels
        pButtonPanel.add(bPlayButton);
        pButtonPanel.add(bPauseButton);
        pButtonPanel.add(bResetButton);
        pButtonPanel.add(bChoseColor);

        pLabelPanel.add(lGenerationLabel);
        pLabelPanel.add(lAliveCellsLabel);

        pInputPanel.add(lSizeWorldLabel);
        pInputPanel.add(tSizeWorld);
        pInputPanel.add(lSeedRandom);
        pInputPanel.add(tSeedWorld);
        pInputPanel.add(lGenerationSetLabel);
        pInputPanel.add(tNumberOfGenerations);
        pInputPanel.add(bAcceptParamNewWorld);

        pToolsPanel.add(lSpeedModeLabel);
        pToolsPanel.add(sSpeedEvolutionSlide);
        pToolsPanel.add(pInputPanel);

        //Add to main Layout
        add(westPanel, BorderLayout.WEST);
        add(viewWorld, BorderLayout.CENTER);
        pack();
        run();
    }

    void run() {
        WorldController worldController = new WorldController(this, new ModelWorld(50));
        worldController.run();
    }

    JButton getBChoseColor() {
        return bChoseColor;
    }

    JSlider getsSpeedEvolutionSlide() {
        return sSpeedEvolutionSlide;
    }

    JTextField getTSizeWorld() {
        return tSizeWorld;
    }

    JTextField getTSeedWorld() {
        return tSeedWorld;
    }

    JTextField getTNumberOfGenerations() {
        return tNumberOfGenerations;
    }

    JButton getBAcceptParamNewWorld() {
        return bAcceptParamNewWorld;
    }

    JToggleButton getBPlayButton() {
        return bPlayButton;
    }

    JToggleButton getBPauseButton() {
        return bPauseButton;
    }

    JButton getBResetButton() {
        return bResetButton;
    }

    void setNumGenerationLabel(long generation) {
        lGenerationLabel.setText("Generation # " + generation);
    }

    void setNumAliveCells(long cells) {
        lAliveCellsLabel.setText("Alive:" + cells);
    }

    ViewWorld getViewWorld() {
        return viewWorld;
    }
}

class ViewWorld extends JPanel {
    private static final int sizeCell = 10;
    private ModelWorld wolWorldModel;
    private boolean[][] worldMatrix;
    private Color cellColor = Color.BLACK;

    ViewWorld(ModelWorld wolWorldModel) {
        this.wolWorldModel = wolWorldModel;
        int size = wolWorldModel.getSizeWorld() * 10 + 10;
        this.setPreferredSize(new Dimension(size, size));
    }

    void setCellColor(Color cellColor) {
        this.cellColor = cellColor;
    }

    public void paint(Graphics g) {
        super.paint(g);
        worldMatrix = wolWorldModel.getMatrix();
        drawCells(g);
        drawGrid(g);
    }

    void drawGrid(Graphics g) {
        g.setColor(g.getColor() == Color.BLACK ? Color.gray : Color.BLACK);
        for (int i = 0; i < worldMatrix.length * sizeCell + sizeCell; i += sizeCell) {
            //worldMatrix.length * sizeCell == length draw line
            g.drawLine(i, 0, i, worldMatrix.length * sizeCell);
            g.drawLine(0, i, worldMatrix.length * sizeCell, i);
        }
    }

    void drawCells(Graphics g) {
        g.setColor(cellColor);
        for (int i = 0, r = 0; i < worldMatrix.length * sizeCell; i += sizeCell, r++) {
            for (int k = 0, c = 0; k < worldMatrix.length * sizeCell; k += 10, c++) {
                //draw live cells
                if (worldMatrix[r][c]) {
                    g.fillRect(k, i, sizeCell, sizeCell);
                }
            }
        }

    }

    void setWorldModel(ModelWorld wolWorldModel) {
        this.wolWorldModel = wolWorldModel;
    }
}

class ModelWorld {
    private int sizeWorld;
    private boolean[][] worldMatrix;
    private long seedWorld;
    private long liveCells;

    ModelWorld(int size) {
        this.sizeWorld = size;
        worldMatrix = new boolean[size][size];
        randomFillWorld();
        liveCells = countLiveCells();
    }

    ModelWorld(ModelWorld model) {
        sizeWorld = model.sizeWorld;
        worldMatrix = model.getMatrix();
        seedWorld = model.getSeedWorld();
        liveCells = model.getLiveCells();
    }

    ModelWorld(int sizeWorld, long seedWorld) {
        this.seedWorld = seedWorld;
        this.sizeWorld = sizeWorld;
        worldMatrix = new boolean[sizeWorld][sizeWorld];
        randomFillWorld();
        liveCells = countLiveCells();
    }

    public long getSeedWorld() {
        return seedWorld;
    }

    private long countLiveCells() {
        int count = 0;
        for (int i = 0; i < sizeWorld; i++) {
            for (int j = 0; j < sizeWorld; j++) {
                count += worldMatrix[i][j] ? 1 : 0;
            }
        }
        return count;
    }

    void setMatrixWorldModel(boolean[][] world) {
        this.worldMatrix = world;
        sizeWorld = world.length;
        liveCells = countLiveCells();
    }

    private void randomFillWorld() {
        Random rnd = new Random(seedWorld);
        for (int i = 0; i < sizeWorld; i++) {
            Arrays.fill(worldMatrix[i], false);
            for (int j = 0; j < sizeWorld; j++) {
                worldMatrix[i][j] = rnd.nextBoolean();
            }
        }
    }

    int getSizeWorld() {
        return sizeWorld;
    }

    boolean[][] getMatrix() {
        return worldMatrix;
    }

    long getLiveCells() {
        return liveCells;
    }
}
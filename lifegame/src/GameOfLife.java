import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLife extends JFrame{
    private JLabel geneationLabel;
    private JLabel aliveLabel;

    private JToggleButton buttonPlayToggle;
    private boolean isResumed;

    private JButton buttonReset;
    private boolean isStarted;

    private final static int WIDTH = 1200;
    private final static int HEIGHT = 900;

    private Universe universe;    
    private int generation;

    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(GameOfLife::new);
    }      

    public GameOfLife() {
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

        this.buttonReset = new JButton("Start");
        this.buttonReset.setName("ResetButton");
        this.buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStarted = !isStarted;
                System.out.println("Start Button pressed!");

                startGame();
            }
        });

        buttonPanel.add(this.buttonPlayToggle, BorderLayout.WEST);
        buttonPanel.add(this.buttonReset, BorderLayout.EAST);
        controlPanel.add(buttonPanel, BorderLayout.NORTH);

        // set up labels panel
        JPanel labelPanel = new JPanel(new BorderLayout(5, 5));        
        labelPanel.setSize(WIDTH/4, HEIGHT/16);

        this.geneationLabel = new JLabel();
        this.geneationLabel.setName("GenerationLabel");
        this.geneationLabel.setFont(new Font("Courier", Font.BOLD, 15));
        this.geneationLabel.setForeground(Color.BLACK);
        this.geneationLabel.setHorizontalAlignment(SwingConstants.LEFT);

        this.aliveLabel = new JLabel();
        this.aliveLabel.setName("AliveLabel");
        this.aliveLabel.setFont(new Font("Courier", Font.BOLD, 15));
        this.aliveLabel.setForeground(Color.BLACK);
        this.aliveLabel.setHorizontalAlignment(SwingConstants.LEFT);

        labelPanel.add(this.geneationLabel, BorderLayout.NORTH);
        labelPanel.add(this.aliveLabel, BorderLayout.CENTER);
        controlPanel.add(labelPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.WEST);

        this.isResumed = false;
        this.isStarted = false;

        // set up cell panel

        this.universe = new Universe();
        this.generation = 1;
        setLabels();
        
        CellPanel cellPanel = new CellPanel(this.universe);
        cellPanel.setSize(new Dimension(getWidth() - controlPanel.getWidth(), getHeight()));
        add(cellPanel, BorderLayout.CENTER);    // Important: do NOT set to BorderLayout.EAST
        setVisible(true);

        Thread universeEvolution = new Thread(() -> {
            while (universe.getLiveCells() > 0) {
                try {
                    universe = universe.generateNext();
                    generation++;
                    cellPanel.setUniverse(universe);

                    Thread.sleep(2000L);
                    setLabels();
                    cellPanel.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        universeEvolution.start();
    }

    private void setLabels() {
        geneationLabel.setText(String.format("   Generation #%d", generation));
        aliveLabel.setText(String.format("   Alive: %d", universe.getLiveCells()));
    }

    private void actOnToggleButton() {
        ItemListener ItemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();

                if (state == itemEvent.SELECTED) {
                    isResumed = true;
                    System.out.println("Toggle button selected");
                } else {
                    isResumed = false;
                    System.out.println("Toggle button deselected");
                }
            }
        };

        this.buttonPlayToggle.addItemListener(ItemListener);
    }

    public void startGame() {
        // int LIMIT = 20;
        System.out.println("Starting......game!");
        while (this.isStarted == true) {
            repaint();           

            System.out.println("Starting......startgame() -> repaint!");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.universe = this.universe.generateNext();
        }
    }
}

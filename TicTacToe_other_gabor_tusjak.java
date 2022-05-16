// package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * 
 * This program is from Jetbrains Academy project - Desktop tic-tac-toe.
 * 
 * Stage 3/4 - Rise of the machines
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-15
 *
 * Note: the code below is taken from other submission written by Gábor Tusjak, as a learning sample.
 */

public class TicTacToe_other_gabor_tusjak {
    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        Runnable ticTacToe = TicTacToe::new;

        SwingUtilities.invokeAndWait(ticTacToe);
    }
}

class TicTacToe extends JFrame {
    /**
     * member variables
     */
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";

    private GameStatus gameStatus = GameStatus.NOT_STARTED;

    private final Board board;
    private final StatusBar statusBar;

    private Player player1 = new Player(PlayerType.HUMAN, PLAYER_X);
    private Player player2 = new Player(PlayerType.HUMAN, PLAYER_O);


    public TicTacToe() {
        super("Tic Tac Toe");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);

        Toolbar toolbar = new Toolbar(this);
        this.board = new Board(this);
        this.statusBar = new StatusBar(this.board);

        add(toolbar, BorderLayout.NORTH);
        add(this.board, BorderLayout.CENTER);
        add(this.statusBar, BorderLayout.SOUTH);

        setVisible(true);
        setLayout(new BorderLayout());
    }

    Player getPlayer1() {
        return this.player1;
    }

    Player getPlayer2() {
        return this.player2;
    }

    GameStatus getGameStatus() {
        return this.gameStatus;
    }

    void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.statusBar.setStatus(gameStatus);
    }

    boolean isGameFinished() {
        return this.gameStatus != GameStatus.NOT_STARTED && this.gameStatus != GameStatus.IN_PROGRESS;
    }

    void reset() {
        this.board.reset();
        this.setGameStatus(GameStatus.NOT_STARTED);
    }

    void start() {
        this.board.start();
        this.setGameStatus(GameStatus.IN_PROGRESS);
    }
}

class Board extends JPanel {
    final TicTacToe game;

    private Player activePlayer;

    private final Button[] buttons = new Button[] {
            new Button("A3"), new Button("B3"), new Button("C3"),
            new Button("A2"), new Button("B2"), new Button("C2"),
            new Button("A1"), new Button("B1"), new Button("C1")
    };

    Board(TicTacToe game) {
        this.game = game;
        this.activePlayer = this.game.getPlayer1();
 
        this.addButtons();
        this.setLayout(new GridLayout(3, 3));
        this.setVisible(true);
    }

    Player getActivePlayer() {
        return this.activePlayer;
    }

    void changePlayer() {
        if (this.checkWinner() || !this.checkEmptyButton()) {
            return;
        }

        this.activePlayer = this.activePlayer == this.game.getPlayer1() ? this.game.getPlayer2() : this.game.getPlayer1();

        this.move();
    }

    private boolean checkEmptyButton() {
        for (Button button : this.buttons) {
            if (" ".equals(button.getText())) {
                return true;
            }
        }

        this.game.setGameStatus(GameStatus.DRAW);
        this.disableButtons();

        return false;
    }

    private boolean checkWinner() {
        String[][] versions = new String[][] {
                {"A3", "B3", "C3"},
                {"A2", "B2", "C2"},
                {"A1", "B1", "C1"},
                {"A3", "A2", "A1"},
                {"B3", "B2", "B1"},
                {"C3", "C2", "C1"},
                {"A3", "B2", "C1"},
                {"A1", "B2", "C3"},
        };

        for (String[] version : versions) {
            if (this.checkEquality(version)) {
                this.game.setGameStatus("X".equals(this.activePlayer.getSymbol()) ? GameStatus.X_WINS : GameStatus.O_WINS);
                this.disableButtons();

                return true;
            }
        }

        return false;
    }

    private boolean checkEquality(String[] buttonLabels) {
        Button firstButton = this.getButtonByName("Button" + buttonLabels[0]);

        if (firstButton == null || " ".equals(firstButton.getText())) {
            return false;
        }

        for (int i = 1; i < buttonLabels.length; i++) {
            Button button = this.getButtonByName("Button" + buttonLabels[i]);

            if (button == null || " ".equals(button.getText())) {
                return false;
            }

            if (!firstButton.getText().equals(button.getText())) {
                return false;
            }
        }

        return true;
    }
    
    private Button getButtonByName(String name) {
        for (Button button : this.buttons) {
            if (button.getName().equals(name)) {
                return button;
            }
        }

        return null;
    }

    void reset() {
        Arrays.stream(this.buttons).forEach(b -> {
            b.setText(" ");
            b.setClicked(false);
            b.setEnabled(false);
        });

        this.game.setGameStatus(GameStatus.NOT_STARTED);
        this.activePlayer = this.game.getPlayer1();
    }

    void start() {
        Arrays.stream(this.buttons).forEach(b -> b.setEnabled(true));
        move();
    }

    void disableButtons() {
        Arrays.stream(this.buttons).forEach(b -> b.setEnabled(false));
    }

    private void addButtons() {
        Arrays.stream(this.buttons).forEach(b -> {
            b.addActionListener(new ButtonActionListener(this, b));
            add(b);
        });
    }

    private void move() {
        if (this.activePlayer.getType() == PlayerType.HUMAN) {
            return;
        }

        Button[] unclickedButtons = Arrays.stream(this.buttons).filter(b -> !b.isClicked()).toArray(Button[]::new);

        Optional<Button> optionalButton = Arrays.stream(unclickedButtons)
                .skip(new Random().nextInt(unclickedButtons.length))
                .findFirst();

        /*
        if (optionalButton.isEmpty()) {
            return;
        }
        */

        if (optionalButton.isPresent()) {
            return;
        }

        Timer timer = new Timer();

        Board board = this;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                optionalButton.get().setClicked(true);
                optionalButton.get().setText(board.activePlayer.getSymbol());

                board.changePlayer();
            }
        };

        timer.schedule(task, 500);
    }
}

class Button extends JButton {
    private boolean isClicked = false;

    Button(String label) {
        this.setName("Button" + label);
        this.setText(" ");
        this.setFocusPainted(false);
        this.setFont(new Font("Roboto Thin", Font.PLAIN, 100));
        this.setEnabled(false);
    }

    boolean isClicked() {
        return this.isClicked;
    }

    void setClicked(boolean clicked) {
        this.isClicked = clicked;
    }
}

class ButtonActionListener implements ActionListener {
    private final Board board;
    private final Button button;

    ButtonActionListener(Board board, Button button) {
        this.board = board;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (
                this.button.isClicked()
                || this.board.game.isGameFinished()
                || this.board.getActivePlayer().getType() == PlayerType.ROBOT
        ) {
            return;
        }

        this.button.setText(this.board.getActivePlayer().getSymbol());
        this.button.setClicked(true);
        this.board.game.setGameStatus(GameStatus.IN_PROGRESS);
        this.board.changePlayer();
    }
}

class StatusBar extends JPanel {
    private final Board board;
    private GameStatus status = GameStatus.NOT_STARTED;
    private final JLabel statusLabel = new JLabel(this.status.getLabel());

    StatusBar(Board board) {
        this.board = board;
        this.addStatusLabel();
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }

    void setStatus(GameStatus status) {
        this.status = status;
        this.statusLabel.setText(status.getLabel());
    }

    GameStatus getStatus() {
        return this.status;
    }

    void addStatusLabel() {
        this.statusLabel.setHorizontalAlignment(JLabel.CENTER);
        this.statusLabel.setName("LabelStatus");
        add(this.statusLabel);
    }
}

enum GameStatus {
    NOT_STARTED("Game is not started"),
    IN_PROGRESS("Game in progress"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    DRAW("Draw");

    private final String label;

    GameStatus(String label) {
        this.label = label;
    }

    String getLabel() {
        return this.label;
    }
}

class Toolbar extends JPanel {
    private final TicTacToe game;
    private final JButton playerButton1 = new JButton();
    private final JButton playerButton2 = new JButton();

    Toolbar(TicTacToe game) {
        this.game = game;
        addButtons();
        this.setLayout(new GridLayout(1, 3));
        this.setVisible(true);
    }

    private void addButtons() {
        this.addPlayerButton(this.playerButton1, "ButtonPlayer1", this.game.getPlayer1());
        this.addStartButton();
        this.addPlayerButton(this.playerButton2, "ButtonPlayer2", this.game.getPlayer2());
    }

    private void addPlayerButton(JButton button, String name, Player player) {
        button.setName(name);
        button.setText(player.getType().getLabel());

        button.addActionListener(actionEvent -> {
            if (this.game.getGameStatus() != GameStatus.NOT_STARTED) {
                return;
            }

            player.toggleType();
            button.setText(player.getType().getLabel());
        });

        add(button);
    }

    private void addStartButton() {
        JButton button = new JButton();
        button.setName("ButtonStartReset");
        button.setText("Start");

        button.addActionListener(actionEvent -> {
            if ("Reset".equals(button.getText())) {
                this.game.reset();
                button.setText("Start");
                this.playerButton1.setEnabled(true);
                this.playerButton2.setEnabled(true);
                return;
            }
            this.game.start();
            button.setText("Reset");
            this.playerButton1.setEnabled(false);
            this.playerButton2.setEnabled(false);
        });

        add(button);
    }
}

class Player {
    private PlayerType type;
    private final String symbol;

    Player(PlayerType type, String symbol) {
        this.type = type;
        this.symbol = symbol;
    }

    PlayerType getType() {
        return this.type;
    }

    String getSymbol() {
        return this.symbol;
    }

    void toggleType() {
        this.type = this.type == PlayerType.HUMAN ? PlayerType.ROBOT : PlayerType.HUMAN;
    }
}

enum PlayerType {
    HUMAN("Human"),
    ROBOT("Robot");

    private final String label;

    PlayerType(String label) {
        this.label = label;
    }

    String getLabel() {
        return this.label;
    }
}
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * This program is from Jetbrains Academy project - Desktop tic-tac-toe.
 * 
 * Stage 4/4 - Can I have the menu
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-17
 *
 * Note: the code below is taken from other submission written by Ayush, as a learning sample.
 */

public class TicTacToe_other_ayush {
    public static void main(String[] args) {
        EventQueue.invokeLater(TicTacToe::new);
    }
}

class TicTacToe extends JFrame {

    private enum State { START, RESET }

    private enum Turn {
        P1(null),
        P2(null);

        RobotMove rm;
        Turn(RobotMove rm) {
            this.rm = rm;
        }
        void setMove(RobotMove rm) {
            this.rm = rm;
        }
        RobotMove getMove() { return rm; }
    }

    JLabel log;
    JButton reset;
    JPanel jPanel;
    JButton player1;
    JButton player2;
    State state = State.START;
    Turn turn = Turn.P1;
    GameInterface gi;

    public TicTacToe() {
        setTitle("Tic Tae Toe");
        setSize(314, 466);
        setLayout(null);
        setResizable(false);
        setBackground(new java.awt.Color(0, 0, 204));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gi = new GameInterface();

        log = new JLabel();
        log.setName("LabelStatus");
        log.setFocusable(false);
        log.setText("Game is not started");
        log.setBounds(0, 350, 300, 50);
        log.setFont(new java.awt.Font("Consolas", Font.PLAIN, 16));
        log.setForeground(Color.RED);

        add(log);

        reset = new JButton();
        reset.setName("ButtonStartReset");
        reset.setText("Start");
        reset.setBackground(new java.awt.Color(104, 205, 55));
        reset.setFont(new java.awt.Font("Consolas", Font.PLAIN, 14));
        reset.setForeground(new java.awt.Color(0, 0, 102));
        reset.setBounds(100, 0, 100, 50);
        reset.setFocusPainted(false);

        add(reset);

        player1 = new JButton();
        player1.setName("ButtonPlayer1");
        player1.setText("Human");
        player1.setBackground(new java.awt.Color(104, 205, 55));
        player1.setFont(new java.awt.Font("Consolas", Font.PLAIN, 14)); // NOI18N
        player1.setForeground(new java.awt.Color(0, 0, 102));
        player1.setBounds(0, 0, 100, 50);
        player1.setFocusPainted(false);

        add(player1);

        player2 = new JButton();
        player2.setName("ButtonPlayer2");
        player2.setText("Human");
        player2.setBackground(new java.awt.Color(104, 205, 55));
        player2.setFont(new java.awt.Font("Consolas", Font.PLAIN, 14)); // NOI18N
        player2.setForeground(new java.awt.Color(0, 0, 102));
        player2.setBounds(200, 0, 100, 50);
        player2.setFocusPainted(false);

        add(player2);

        add(jPanel = new GameBoard());

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Game");
        menu.setEnabled(true);
        menu.setName("MenuGame");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.setFont(new java.awt.Font("Consolas", Font.BOLD, 15));

        JMenuItem menuHumanHuman = new MenuItem("Human vs Human", "MenuHumanHuman");
        JMenuItem menuHumanRobot = new MenuItem("Human vs Robot", "MenuHumanRobot");
        JMenuItem menuRobotHuman = new MenuItem("Robot vs Human", "MenuRobotHuman");
        JMenuItem menuRobotRobot = new MenuItem("Robot vs Robot", "MenuRobotRobot");
        JMenuItem menuExit = new MenuItem("Exit", "MenuExit");

        menuExit.addActionListener(e -> System.exit(0));

        menu.add(menuHumanHuman);
        menu.add(menuHumanRobot);
        menu.add(menuRobotHuman);
        menu.add(menuRobotRobot);
        menu.addSeparator();
        menu.add(menuExit);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        setVisible(true);

        addCells();

        reset.addActionListener(e -> {
            if (state == State.START) {
                reset.setText("Reset");
                log.setText(String.format("The turn of %s Player (X)", getCurrentPlayer()));
                state = State.RESET;
                player1.setEnabled(false);
                player2.setEnabled(false);
                System.out.println(log.getText());
                makeButtonsAvailable();
                moveNext();
            } else {
                reset.setText("Start");
                player1.setText("Human");
                player2.setText("Human");
                player1.setEnabled(true);
                player2.setEnabled(true);
                turn = Turn.P1;
                Turn.P1.setMove(null);
                Turn.P2.setMove(null);
                resetGameBoard();
                state = State.START;
                log.setText("Game is not started");
            }
        });

        player1.addActionListener(e -> {
            if (state == State.START) {
                if (Turn.P1.rm == null) {
                    player1.setText("Robot");
                    Turn.P1.setMove(new RobotMove());
                } else {
                    player1.setText("Human");
                    Turn.P1.setMove(null);
                }
            }
        });

        player2.addActionListener(e -> {
            if (state == State.START) {
                if (Turn.P2.rm == null) {
                    player2.setText("Robot");
                    Turn.P2.setMove(new RobotMove());
                } else {
                    player2.setText("Human");
                    Turn.P2.setMove(null);
                }
            }
        });

        menuHumanHuman.addActionListener(e -> {
            if (state == State.RESET)
                reset.doClick();
            if (!"Human".equals(player1.getText()))
                player1.doClick();

            if (!"Human".equals(player2.getText()))
                player2.doClick();

            reset.doClick();
        });

        menuHumanRobot.addActionListener(e -> {
            if (state == State.RESET)
                reset.doClick();
            if (!"Human".equals(player1.getText()))
                player1.doClick();

            if (!"Robot".equals(player2.getText()))
                player2.doClick();

            reset.doClick();

        });

        menuRobotHuman.addActionListener(e -> {
            if (state == State.RESET)
                reset.doClick();
            if (!"Robot".equals(player1.getText()))
                player1.doClick();

            if (!"Human".equals(player2.getText()))
                player2.doClick();

            reset.doClick();
        });

        menuRobotRobot.addActionListener(e -> {
            if (state == State.RESET)
                reset.doClick();
            if (!"Robot".equals(player1.getText()))
                player1.doClick();

            if (!"Robot".equals(player2.getText()))
                player2.doClick();

            reset.doClick();
        });
    }

    private void moveNext() {
        String status;
        if ((status = gi.checkForWinner()).length() == 0) {
            log.setText(String.format("The turn of %s Player (%s)",
                    getCurrentPlayer(),
                    gi.getTurn()));

            if (turn.rm != null) {
                Timer timer = new Timer();
                TimerTask setRobotMove = new TimerTask() {
                    @Override public void run() {
                        new Thread(() -> {
                            int move;
                            ((JButton) jPanel.getComponents()[move =
                                    turn.rm.getMove(gi.getBoard())]).setText("" + gi.TakeTurn(move));
                            changeTurns();
                            moveNext();
                        }).start();
                    }
                };
                timer.schedule(setRobotMove, 1000);
            }
        } else {
            log.setText(!status.equals("Draw") ? String.format("The %s Player (%s) wins",
                    getWinnerPlayer(),
                    status)
                    : status);
        }
    }    

    private void addCells() {
        int i = 0;
        for (Component c : jPanel.getComponents()) {
            final int j = i++;
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(e -> {
                    if (gi.checkForWinner().length() == 0
                            && " ".equals(((JButton) c).getText())
                            && state == State.RESET
                            && checkLegalTurn()) {
                        ((JButton) c).setText("" + gi.TakeTurn(j));
                        changeTurns();
                        moveNext();
                    }
                });
            }
        }
    }    

    private String getCurrentPlayer() {
        return turn.getMove() == null ? "Human" : "Robot";
    }

    private String getWinnerPlayer() {
        if (turn == Turn.P1) {
            return Turn.P2.rm == null ? "Human" : "Robot";
        }
        return Turn.P1.rm == null ? "Human" : "Robot";
    }

    private void resetGameBoard() {
        gi.resetBoard();
        for (Component c : jPanel.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).setText(" ");
                c.setEnabled(false);
            }
        }
    }

    private void makeButtonsAvailable() {
        for (Component c : jPanel.getComponents()) {
            if (c instanceof JButton) {
                c.setEnabled(true);
            }
        }
    }

    private void changeTurns() {
        if (turn == Turn.P1)
            turn = Turn.P2;
        else
            turn = Turn.P1;
    }

    private boolean checkLegalTurn() {
        return turn.rm == null;
    }
}

class Board {
    private final char[] BD = new char[9];
    private char turn = 'X';

    public Board() {}
    public Board(char[] bd, char turn) {
        setTurn(turn);
        System.arraycopy(bd, 0, BD, 0, 9);
    }

    public char getTurn() {
        return turn;
    }

    public void setTurn(char turn) {
        this.turn = turn;
    }

    public char getNextTurn() {
        if (turn == 'X')
            return 'O';
        return 'X';
    }

    public void changeTurn() {
        if (turn == 'X')
            turn = 'O';
        else
            turn = 'X';
    }

    public char[] getClone() {
        char[] temp = new char[9];
        for (int i = 0; i < 9; i++)
            temp[i] = BD[i];
        return temp;
    }

    public void newBoard() {
        for (int i = 0; i < 9; i++) {
            BD[i] = '\u0000';
        }
        setTurn('X');
    }

    public boolean isPosEmpty(int x) {
        return BD[x] == '\u0000';
    }

    public void print() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int k = 0; k < 3; k++) {
                if (BD[i * 3 + k] != '\u0000')
                    System.out.printf("%s ", BD[i * 3 + k]);
                else
                    System.out.print("  ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }

    public void putMove(int x) {
        BD[x] = turn;
        changeTurn();
    }

    public void setValue(int x, char c) {
        BD[x] = c;
    }

    public boolean isFull() {
        for (Character c : BD)
            if (c == '\u0000')
                return false;
        return true;
    }

    public Character getWinner() {
        for (int i = 0; i < 3; i++) {
            if (BD[i * 3] == BD[i * 3 + 1]
                    && BD[i * 3 + 1] == BD[i * 3 + 2]
                    && BD[i * 3 + 2] != '\u0000') return BD[i * 3];
            else if (BD[i] == BD[i + 3]
                    && BD[i + 3] == BD[i + 6]
                    && BD[i + 6] != '\u0000') return BD[i];
        }
        if (BD[0] == BD[4]
                && BD[4] == BD[8]
                && BD[8] != '\u0000') return BD[0];
        else if (BD[2] == BD[4]
                && BD[4] == BD[6]
                && BD[6] != '\u0000') return BD[2];
        else if (isFull()) return 'D';
        return 'n';
    }
}

class RobotMove {
    char[] tb;
    char max = 'X';
    char min = 'O';

    public int getMove(Board b) {
        Board bh = new Board(b.getClone(), b.getTurn());
        max = b.getTurn();
        min = b.getNextTurn();
        tb = bh.getClone();
        return minimax(bh, true).getKey();
    }

    private Map.Entry<Integer, Long> minimax(Board b, boolean isMax) {
        char winner = b.getWinner();
        if (winner == max)
            return new AbstractMap.SimpleEntry<>(0, 10L);
        else if (winner == min)
            return new AbstractMap.SimpleEntry<>(0, -10L);
        else if (winner == 'D')
            return new AbstractMap.SimpleEntry<>(0, 0L);

        Map<Integer, Long> bestMove = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            if (b.isPosEmpty(i)) {
                b.setValue(i, isMax ? max : min);
                Map.Entry<Integer, Long> temp =
                        new AbstractMap.SimpleEntry<>(i,
                                minimax(b, !isMax).getValue() - 1L);
                bestMove.put(temp.getKey(), temp.getValue());
                b.setValue(i, '\u0000');
            }
        }

        return Collections.max(bestMove.entrySet(), (e1, e2) -> isMax ? e1.getValue().compareTo(e2.getValue())
                : e2.getValue().compareTo(e1.getValue()));
    }
}

@SuppressWarnings("unused")
public class GameBoard extends JPanel {
    public GameBoard() {
        super();
        this.setName("Board");
        this.setBackground(new java.awt.Color(0, 0, 204));
        this.setBounds(0, 50, 300, 300);
        this.setLayout(new GridLayout(3, 3));
        for (int i = 3, k = 0; i > 0; i--)
            for (int j = 'A'; j <= 'C'; j++)
                this.add(new Cell("Button" + Character.toString(j) + i));
    }
}

class GameInterface {
    private final Board b;

    public GameInterface() {
        b = new Board();
    }

    public char TakeTurn(int i) {
        char curr = b.getTurn();
        b.putMove(i);
        return curr;
    }

    public String checkForWinner() {
        char winner;
        if ((winner = b.getWinner()) != 'n')
            if (winner == 'D')
                return "Draw";
            else
                return "" + winner;
        return "";
    }

    public void resetBoard() {
        b.newBoard();
    }
    public Board getBoard() { return b; }
    public Character getTurn() { return b.getTurn(); }
}

@SuppressWarnings("unused")
public class Cell extends JButton {

    public Cell(String name) {
        super();
        this.setName(name);
        this.setBackground(new java.awt.Color(204, 255, 255));
        this.setFont(new java.awt.Font("Consolas", Font.PLAIN, 24));
        this.setForeground(new java.awt.Color(0, 0, 102));
        this.setSize(100, 100);
        this.setText(" ");
        this.setEnabled(false);
        this.setFocusPainted(false);
    }
}

class MenuItem extends JMenuItem {

    public MenuItem(String text, String name) {
        super(text);
        setName(name);
        setFont(new java.awt.Font("Courier", Font.BOLD, 15));
    }
}
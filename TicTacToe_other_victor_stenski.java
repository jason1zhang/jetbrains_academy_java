// package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
 * Note: the code below is taken from other submission written by victor stenski, as a learning sample.
 */

public class TicTacToe_other_victor_stenski {
    public static void main(String[] args) {
        new TicTacToe();
    }
}

class TicTacToe extends JFrame {
    /**
     * member variables
     */
    static String nextMove = "X";
    private static final JButton[] grid = new JButton[9];
    private static String letter = "A";
    private static int number = 3;
    private String playerOne = "Human";
    private String playerTwo = "Human";
    private static final JLabel statusLabel = new JLabel("Game is not started");

    public TicTacToe() {
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());

        // create the start panel with 3 buttons - "player one", "start / reset", and "player two"
        JPanel startPanel = new JPanel(new GridLayout());
        startPanel.setPreferredSize(new Dimension(400, 30));
        startPanel.setSize(400, 30);

        JButton buttonPlayer1 = new JButton("Human");
        buttonPlayer1.setName("ButtonPlayer1");
        buttonPlayer1.addActionListener(e -> {
            playerButtonLogic(buttonPlayer1);
        });

        JButton buttonPlayer2 = new JButton("Human");
        buttonPlayer2.setName("ButtonPlayer2");
        buttonPlayer2.addActionListener(e -> {
            playerButtonLogic(buttonPlayer2);
        });

        JButton buttonStartReset = new JButton("Start");
        buttonStartReset.addActionListener(e -> {
            buttonStartResetLogic(buttonPlayer1, buttonPlayer2, buttonStartReset);
        });
        buttonStartReset.setName("ButtonStartReset");

        startPanel.add(buttonPlayer1);
        startPanel.add(buttonStartReset);
        startPanel.add(buttonPlayer2);
        add(startPanel, BorderLayout.NORTH);

        // create the game board panel
        JPanel gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(400, 370));
        gamePanel.setSize(400, 370);

        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(400, 30));
        statusPanel.setSize(400, 30);
        add(gamePanel);
        add(statusPanel, BorderLayout.SOUTH);

        statusPanel.setLayout(new BorderLayout(2, 2));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setName("LabelStatus");
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusLabel.setVisible(true);

        gamePanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton(" ");
            button.setFocusPainted(false);
            button.setEnabled(false);
            grid[i] = button;
            button.setName(getButtonName());
            button.setFont(new Font("Arial", Font.BOLD, 40));
            button.addActionListener(e -> {
                button.setText(getNextMove());
                checkForTerminal();
                makeMove();
            });
            gamePanel.add(button);
        }
        setVisible(true);
    }

    private void buttonStartResetLogic(JButton buttonPlayer1, JButton buttonPlayer2, JButton buttonStartReset) {
        if (buttonStartReset.getText().equals("Start")) {
            buttonPlayer1.setEnabled(false);
            buttonPlayer2.setEnabled(false);

            switchGrid(true);

            buttonStartReset.setText("Reset");
            playerOne = buttonPlayer1.getText();
            playerTwo = buttonPlayer2.getText();
            statusLabel.setText("Game in progress");

            makeMove();

        } else {
            buttonPlayer1.setEnabled(true);
            buttonPlayer1.setText("Human");
            buttonPlayer2.setEnabled(true);
            buttonPlayer2.setText("Human");

            for (JButton button : grid) {
                button.setText(" ");
            }

            switchGrid(false);

            statusLabel.setText("Game is not started");
            nextMove = "X";
            buttonStartReset.setText("Start");
        }
    }

    private void playerButtonLogic(JButton buttonPlayer) {
        if (buttonPlayer.getText().equals("Human")) {
            buttonPlayer.setText("Robot");
        } else {
            buttonPlayer.setText("Human");
        }
    }

    private void makeMove() {
        if (!((TicTacToe.nextMove.equals("X") && playerOne.equals("Human"))
                || (TicTacToe.nextMove.equals("O") && playerTwo.equals("Human"))) && grid[0].isEnabled()) {

            SwingWorker<Object, Object> worker = new SwingWorker<>() {
                @Override
                protected Ojbect doInBackground() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    grid[Minimax.bestMove(getBoard(), nextMove.charAt(0), true)].doClick();
                    
                    return null;
                }
            };
            worker.execute();
        }
    }

    private char[] getBoard() {
        char[] result = new char[9];
        for (int i = 0; i < 9; i++) {
            result[i] = grid[i].getText().charAt(0);
        }
        return result;
    }

    private String getButtonName() {
        String temp = "Button";
        if (!letter.equals("C")) {
            temp += letter + number;
            letter = "" + (char) (letter.charAt(0) + 1);
        } else {
            temp += letter + number;
            letter = "A";
            number--;
        }
        return temp;
    }

    private String getNextMove() {
        if (TicTacToe.nextMove.equals("X")) {
            TicTacToe.nextMove = "O";
            return "X";
        } else {
            TicTacToe.nextMove = "X";
            return "O";
        }
    }

    private void checkForTerminal() {
        for (int i = 0; i < 3; i++) {
            if ((grid[i].getText().equals("X") && grid[i + 3].getText().equals("X") && grid[i + 6].getText().equals("X"))) {
                switchGrid(false);
                statusLabel.setText("X wins");
                return;
            }
            if ((grid[i].getText().equals("O") && grid[i + 3].getText().equals("O") && grid[i + 6].getText().equals("O"))) {
                switchGrid(false);
                statusLabel.setText("O wins");
                return;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            if (grid[i].getText().equals("X") && grid[i + 1].getText().equals("X") && grid[i + 2].getText().equals("X")) {
                switchGrid(false);
                statusLabel.setText("X wins");
                return;
            }
            if (grid[i].getText().equals("O") && grid[i + 1].getText().equals("O") && grid[i + 2].getText().equals("O")) {
                switchGrid(false);
                statusLabel.setText("O wins");
                return;
            }
        }
        
        if (grid[0].getText().equals("X") && grid[4].getText().equals("X") && grid[8].getText().equals("X")) {
            switchGrid(false);
            statusLabel.setText("X wins");
            return;
        }
        
        if (grid[0].getText().equals("O") && grid[4].getText().equals("O") && grid[8].getText().equals("O")) {
            switchGrid(false);
            statusLabel.setText("O wins");
            return;
        }
        
        if (grid[2].getText().equals("X") && grid[4].getText().equals("X") && grid[6].getText().equals("X")) {
            switchGrid(false);
            statusLabel.setText("X wins");
            return;
        }
        
        if (grid[2].getText().equals("O") && grid[4].getText().equals("O") && grid[6].getText().equals("O")) {
            switchGrid(false);
            statusLabel.setText("O wins");
            return;
        }
        
        if (!grid[0].getText().equals(" ") && !grid[1].getText().equals(" ") && !grid[2].getText().equals(" ")
                && !grid[3].getText().equals(" ") && !grid[4].getText().equals(" ") && !grid[5].getText().equals(" ")
                && !grid[6].getText().equals(" ") && !grid[7].getText().equals(" ") && !grid[8].getText().equals(" ")) {
            switchGrid(false);
            statusLabel.setText("Draw");
        }
    }    

    private void switchGrid(boolean isOn) {
        for (JButton button : grid) {
            button.setEnabled(isOn);
        }
    }
}

public class Minimax {

    char[] botBoard;
    char nextTurnSymbol;

    Minimax(char[] board, char nextTurnSymbol) {
        this.botBoard = board.clone();
        this.nextTurnSymbol = nextTurnSymbol;
    }

    private int minimax(char[] board, char nextTurnSymbol, boolean isAI) {
        var availIndexes = emptyIndexes(board);

        if (isAI && isWin(board, nextTurnSymbol)) {
            return 10;
        } else if (!isAI && isWin(board, nextTurnSymbol)) {
            return -10;
        } else if (availIndexes.isEmpty()) {
            return 0;
        }

        int bestScore;
        if (isAI) {
            bestScore = -10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndex] = ' ';
                bestScore = Math.max(score, bestScore);
            }
        } else {
            bestScore = 10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = minimax(board.clone(), anotherTurnSymbol(nextTurnSymbol), true);
                board[availIndex] = ' ';
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    private ArrayList<Integer> emptyIndexes(char[] board) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') result.add(i);
        }
        return result;
    }

    private char anotherTurnSymbol(char currentSymbol) {
        if (currentSymbol == 'X') {
            return 'O';
        } else {
            return 'X';
        } 
    }

    public static int bestMove(char[] board, char nextTurnSymbol, boolean isAI) {
        int bestScore;
        int result = -1;
        Minimax newBoard = new Minimax(board, nextTurnSymbol);
        var availIndexes = newBoard.emptyIndexes(board);

        if (isAI) {
            bestScore = -10000;
            for (Integer availIndex : availIndexes) {
                board[availIndex] = nextTurnSymbol;
                int score = newBoard.minimax(board.clone(), newBoard.anotherTurnSymbol(nextTurnSymbol), false);
                board[availIndex] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    result = availIndex;
                }
            }
        }
        return result;
    }

    protected static boolean isWin(char[] grid, char nextTurnSymbol) {
        for (int i = 0; i < 3; i++) {
            if (grid[i * 3] == nextTurnSymbol && grid[i * 3 + 1] == nextTurnSymbol && grid[i * 3 + 2] == nextTurnSymbol) {
                return true;
            }

            if (grid[i] == nextTurnSymbol && grid[i + 3] == nextTurnSymbol && grid[i + 6] == nextTurnSymbol) { 
                return true;
            }
        }

        if (grid[0] == nextTurnSymbol && grid[4] == nextTurnSymbol && grid[8] == nextTurnSymbol) { 
            return true;
        }

        return grid[2] == nextTurnSymbol && grid[4] == nextTurnSymbol && grid[6] == nextTurnSymbol;
    }
}
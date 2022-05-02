import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame {

    // private Player playerOne;
    // private Player playerTwo;

    private boolean isPlayerOneMove;    // who is moving now

    private Board board;

    private JLabel labelStatus;
    private JButton buttonReset;

    private int state;

    public TicTacToe() {

        resetGame();

        initComponents();

        actOnCellButton();

        actOnResetButton();

        setVisible(true);

        // setLayout(null);
    }

    /**
     * reset the game parameters
     */
    private void resetGame() {
        // this.playerOne = null;
        // this.playerTwo = null;

        this.isPlayerOneMove = true;
        this.state = Game.NOT_STARTED;
    }

    private void initComponents() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // construct a panel to hold status label and reset button
        JPanel panelStatus = new JPanel();

        this.labelStatus = new JLabel(Game.STR_NOT_STARTED);
        this.labelStatus.setName("LabelStatus");
        this.labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonReset = new JButton("Reset");
        this.buttonReset.setName("ButtonReset");
        this.buttonReset.setHorizontalAlignment(SwingConstants.CENTER);

        panelStatus.setLayout(new BorderLayout(10, 10));
        panelStatus.add(labelStatus, BorderLayout.WEST);
        panelStatus.add(buttonReset, BorderLayout.EAST);

        // initialize the board
        this.board = new Board();

        setLayout(new BorderLayout(10, 10));

        add(this.board, BorderLayout.CENTER);
        add(panelStatus, BorderLayout.SOUTH);
    }

    /**
     * add a listener for each cell of the field
     */
    public void actOnCellButton() {
        int size = this.board.getBoardSize();
        CellButton[] cells = this.board.getBoard();

        for (int i = 0; i < size * size; i++) {
            CellButton cell = cells[i];

            cell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (state != Game.GAME_OVER) {
                        if (cell.getIsClicked() == false) {

                            if (isPlayerOneMove) {
                                cell.setText(Game.STR_CELL_X);
                                cell.getCell().setCellType(Game.CELL_X);

                                isPlayerOneMove = false;
                            } else {
                                cell.setText(Game.STR_CELL_O);
                                cell.getCell().setCellType(Game.CELL_O);

                                isPlayerOneMove = true;
                            }

                        } else {
                            cell.setIsclicked(true);
                        }
                    }

                    state = checkState(); // check game state
                    switch (state) {
                        case Game.X_WIN:
                            labelStatus.setText(Game.STR_X_WIN);
                            state = Game.GAME_OVER;
                            break;
                        case Game.O_WIN:
                            labelStatus.setText(Game.STR_O_WIN);
                            state = Game.GAME_OVER;
                            break;
                        case Game.DRAW:
                            labelStatus.setText(Game.STR_DRAW);
                            state = Game.GAME_OVER;
                            break;
                        case Game.IN_PROGRESS:
                            labelStatus.setText(Game.STR_IN_PROGRESS);
                            break;
                    }
                }
            });
        }
    }

    /**
     * add the listener for reset button
     */
    public void actOnResetButton() {
        this.buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerOneMove = true;
                state = Game.NOT_STARTED;

                board.clear();

                labelStatus.setText(Game.STR_NOT_STARTED);
            }
        });
    }


    /**
     * check the game state
     *
     * @return game state
     */
    private int checkState() {
        if (this.board.checkWin(Game.CELL_X)) {
            return Game.X_WIN;
        }

        if (this.board.checkWin(Game.CELL_O)) {
            return Game.O_WIN;
        }

        boolean isBoardFull = true;

        for (int row = 0; row < this.board.size; row++) {
            for (int col = 0; col < this.board.size; col++) {
                if (this.board.getBoard()[row * this.board.size + col].getCell().getCellType() == Game.CELL_EMPTY) {
                    isBoardFull = false;
                    break; // break out of the inner for loop
                }

                if (!isBoardFull) { // break out of the outer for loop
                    break;
                }
            }
        }

        return isBoardFull? Game.DRAW : Game.IN_PROGRESS;
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class TicTacToe extends JFrame {
    /**
     * member variables
     */    
    private Player playerOne;
    private Player playerTwo;
    private Player currPlayer;  // current player

    private Board board;

    private JLabel labelStatus;
    private JButton buttonStartReset;
    private JButton buttonPlayer1;
    private JButton buttonPlayer2;

    private int state;
    private final int secondsToSleep;

    public TicTacToe() {

        this.playerOne = null;
        this.playerTwo = null;

        this.state = Game.NOT_STARTED;

        this.secondsToSleep = 1;    // delay between movements

        initComponents();

        actOnCellButton();
        actOnPlayerOneButton();
        actOnPlayerTwoButton();
        actOnStartResetButton();

        setVisible(true);
    }

    /**
     * design the game layout
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // construct a panel to hold  2 player button and start/reset button
        JPanel panelButtons = new JPanel();
        this.buttonPlayer1 = new JButton(Game.STR_HUMAN);
        this.buttonPlayer1.setName("ButtonPlayer1");
        this.buttonPlayer1.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonPlayer2 = new JButton(Game.STR_HUMAN);
        this.buttonPlayer2.setName("ButtonPlayer2");
        this.buttonPlayer2.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonStartReset = new JButton(Game.STR_START);
        this.buttonStartReset.setName("ButtonStartReset");
        this.buttonStartReset.setHorizontalAlignment(SwingConstants.CENTER);

        panelButtons.setLayout(new GridLayout(1, 3));
        panelButtons.add(buttonPlayer1);
        panelButtons.add(buttonStartReset);
        panelButtons.add(buttonPlayer2);
        
        // construct a panel to hold status label and reset button
        JPanel panelStatus = new JPanel();

        this.labelStatus = new JLabel(Game.STR_NOT_STARTED);
        this.labelStatus.setName("LabelStatus");
        this.labelStatus.setHorizontalAlignment(SwingConstants.CENTER);

        panelStatus.setLayout(new BorderLayout(10, 10));
        panelStatus.add(labelStatus, BorderLayout.WEST);
        
        // initialize the board
        this.board = new Board();

        // add the components to the frame
        setLayout(new BorderLayout(0, 0));

        add(panelButtons, BorderLayout.NORTH);
        add(this.board, BorderLayout.CENTER);
        add(panelStatus, BorderLayout.SOUTH);
    }

    /**
     * add a listener for each cell on the field
     */
    public void actOnCellButton() {
        int size = this.board.getBoardSize();
        CellButton[] cells = this.board.getBoard();

        for (int i = 0; i < size * size; i++) {
            CellButton cell = cells[i];

            cell.getCell().setRow(i / size);
            cell.getCell().setCol(i % size);

            cell.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    cell.setEnabled(true);  // enable the button

                    if (state == Game.NOT_STARTED  || state == Game.GAME_OVER) {
                        return;
                    }

                    // human make the move
                    if (currPlayer.getPlayerType() == Game.HUMAN) {                    
                        if (cell.getText() == Game.STR_CELL_EMPTY) {
                            if (currPlayer.getPlayerCellType() == Game.CELL_X) {
                                cell.setText(Game.STR_CELL_X);
                                cell.getCell().setCellType(Game.CELL_X);
                            } else {
                                cell.setText(Game.STR_CELL_O);
                                cell.getCell().setCellType(Game.CELL_O);
                            }
                        }

                        pause();

                        currPlayer = currPlayer == playerOne ? playerTwo : playerOne;                        

                        // human play against computer
                        if (currPlayer.getPlayerType() == Game.ROBOT) {

                            checkGameState();
                            pause();

                            currPlayer.MoveNext(board);     
                            currPlayer = currPlayer == playerOne ? playerTwo : playerOne;                                                                           
                        }

                        setVisible(true);
                    }

                    checkGameState();
                }
            });
        }
    }

    /**
     * add delay between movements
     */
    private void pause() {
        try {
            TimeUnit.SECONDS.sleep(secondsToSleep);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * add the listener for reset button
     */
    public void actOnStartResetButton() {

        this.buttonStartReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (state == Game.NOT_STARTED) {
                    state = Game.IN_PROGRESS;

                    buttonPlayer1.setEnabled(false);
                    buttonPlayer2.setEnabled(false);
                    
                    // create player 1
                    if (buttonPlayer1.getText() == Game.STR_HUMAN) {
                        playerOne = new PlayerHuman();
                    } else {
                        playerOne = new PlayerRobot(Game.HARD);
                    }

                    // create player 2
                    if (buttonPlayer2.getText() == Game.STR_HUMAN) {
                        playerTwo = new PlayerHuman(false);
                    } else {
                        playerTwo = new PlayerRobot(false, Game.HARD);
                    }

                    buttonStartReset.setText(Game.STR_RESET);
                    labelStatus.setText(Game.STR_IN_PROGRESS);
                    setVisible(true);

                    board.enableBoard();

                    pause();
                    
                    currPlayer = playerOne;

                    // do while loop for computer playing against computer
                    do {
                        if (currPlayer.getPlayerType() == Game.ROBOT) {
                            currPlayer.MoveNext(board);                            
                        } else {
                            break;  // if player is human, break out the do while loop
                        }

                        board.setVisible(true);
                        setVisible(true);
                        pause();  

                        currPlayer = currPlayer == playerOne ? playerTwo : playerOne;

                        checkGameState();
                        
                        if (state == Game.GAME_OVER) {
                            break;  // break out the do while loop
                        }
                        
                    } while (currPlayer.getPlayerType() == Game.ROBOT);

                } else if (state == Game.IN_PROGRESS) {
                    // do nothing
                    pause();

                } else if (state == Game.GAME_OVER) {
                    state = Game.NOT_STARTED;

                    playerOne = null;
                    playerTwo = null;

                    board.clear();

                    buttonStartReset.setText(Game.STR_START);
                    labelStatus.setText(Game.STR_NOT_STARTED);

                    buttonPlayer1.setEnabled(true);
                    buttonPlayer2.setEnabled(true);                    
                }
            }
        });
    }

    /**
     * add the listener for player1 button
     */
    public void actOnPlayerOneButton() {
        this.buttonPlayer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state == Game.NOT_STARTED) {
                    if (buttonPlayer1.getText() == Game.STR_HUMAN) {
                        buttonPlayer1.setText(Game.STR_COMPUTER);
                    } else {
                        buttonPlayer1.setText(Game.STR_HUMAN);
                    }
                }
            }
        });
    }

    /**
     * add the listener for player2 button
     */
    public void actOnPlayerTwoButton() {
        this.buttonPlayer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (state == Game.NOT_STARTED) {
                    if (buttonPlayer2.getText() == Game.STR_HUMAN) {
                        buttonPlayer2.setText(Game.STR_COMPUTER);
                    } else {
                        buttonPlayer2.setText(Game.STR_HUMAN);
                    }
                }
            }
        });
    }

    /**
     * check the game state
     *
     */
    private void checkGameState() {
        if (this.board.checkWin(Game.CELL_X)) {
            this.state = Game.X_WIN;

        } else if (this.board.checkWin(Game.CELL_O)) {
            this.state = Game.O_WIN;

        } else {
            // check if board is full or not
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

            this.state = isBoardFull? Game.DRAW : Game.IN_PROGRESS;
        }        
        
        switch (this.state) {
            case Game.X_WIN:
                this.labelStatus.setText(Game.STR_X_WIN);
                this.state = Game.GAME_OVER;
                break;
            case Game.O_WIN:
                this.labelStatus.setText(Game.STR_O_WIN);
                this.state = Game.GAME_OVER;
                break;
            case Game.DRAW:
                this.labelStatus.setText(Game.STR_DRAW);
                this.state = Game.GAME_OVER;
                break;
            case Game.IN_PROGRESS:
                labelStatus.setText(Game.STR_IN_PROGRESS);                            
                break;
        }
    }    
}
import javax.swing.JPanel;
import java.awt.*;
import java.util.*;

public class Board extends JPanel{
    protected int size;
    protected CellButton[] board;

    public Board() {
        super();

        this.size = Game.SIZE;
        this.board = new CellButton[this.size * this.size];

        initComponents();

        setLayout(new GridLayout(3, 3));
        setVisible(true);
    }

    /**
     * initialize the cell buttons
     */
    private void initComponents() {
        char chAlpha = 'A';
        char chNum = '3';
        String text = null;

        for (int i = 0; i < this.board.length; i++) {
            text = "" + chAlpha + chNum;
            // this.board[i] = new Cell(text, "Button" + text);
            this.board[i] = new CellButton(Game.STR_CELL_EMPTY, "Button" + text);
            this.board[i].setFocusPainted(false);   // remove the border around the text of the active cell

            add(this.board[i]);

            chAlpha++;
            if (chAlpha == 'D') {
                chNum--;
                chAlpha = 'A';
            }
        }
    }

    public CellButton[] getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.size;
    }

    /**
     * get the empty cells on the board
     *
     * @return an LinkedList with empty cells
     */
    public LinkedList<Cell> getEmptyCells() {
        LinkedList<Cell> emptyCells = new LinkedList<>();

        for (CellButton cellButton : this.board) {
            if (cellButton.getCell().getCellType() == Game.CELL_EMPTY) {
                emptyCells.add(cellButton.getCell());
            }
        }

        return emptyCells;
    }

    /**
     * reset the board for a new game
     *
     */
    public void clear() {
        for (CellButton cellButton : this.board) {
            cellButton.getCell().setCellType(Game.CELL_EMPTY);
            cellButton.setText(Game.STR_CELL_EMPTY);
        }
    }

    /**
     * check if specified player cell type wins the game.
     *
     * This function is used for minimax algorithm. Though it's not an optimal solution to the problem.
     *
     * @param playerCellType player's cell type
     * @return true if the specified player cell type wins otherwise false
     */
    public boolean checkWin(int playerCellType) {
        return (this.board[0].getCell().getCellType() == playerCellType && this.board[1].getCell().getCellType() == playerCellType && this.board[2].getCell().getCellType() == playerCellType) ||
                (this.board[3].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType) ||
                (this.board[6].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[3].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType) ||
                (this.board[1].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType);
    }

    /**
     * check if player wins the game
     *
     * @param player player to check if wins
     * @return true if this player wins otherwise false
     */
    /*
    public boolean checkWin(Player player) {
        int playerCellType = player.getPlayerCellType();

        return (this.board[0].getCell().getCellType() == playerCellType && this.board[1].getCell().getCellType() == playerCellType && this.board[2].getCell().getCellType() == playerCellType) ||
                (this.board[3].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType) ||
                (this.board[6].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[3].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType) ||
                (this.board[1].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[7].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[5].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[0].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[8].getCell().getCellType() == playerCellType) ||
                (this.board[2].getCell().getCellType() == playerCellType && this.board[4].getCell().getCellType() == playerCellType && this.board[6].getCell().getCellType() == playerCellType);
    }
    */
}

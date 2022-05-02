import java.util.*;

abstract class Player implements Cloneable{
    protected boolean isFirst;      // if it's first player

    protected int playerCellType;   // 0: CELL_EMPTY; 1: CELL_X; 2: CELL_O
    protected int playerType;       // 1: ROBOT; 2: HUMAN

    protected Move curMove;
    protected LinkedList<Move> moves;

    public Player() {
        this.isFirst = true;

        this.playerCellType = Game.CELL_X;
        this.playerType = Game.ROBOT;

        this.curMove = new Move();
        this.moves = new LinkedList<Move>();
    }

    public Player(boolean isFirst) {
        this.isFirst = isFirst;

        this.playerCellType = isFirst ? Game.CELL_X : Game.CELL_O;
        this.playerType = Game.ROBOT;

        this.curMove = new Move();
        this.moves = new LinkedList<Move>();
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int getPlayerCellType() {
        return this.playerCellType;
    }

    public void setPlayerCellType(int playerCellType) {
        this.playerCellType = playerCellType;
    }

    public int getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public Move getCurMove() {
        return this.curMove;
    }

    public void setCurMove(Move move) {
        this.curMove = (Move) move.clone();
    }

    /**
     * Get the opponent's player cell type     *
     * @return the opponent's player cell type
     */
    public int getOppPlayerCellType() {
        switch (this.playerCellType) {
            case Game.CELL_X:
                return Game.CELL_O;
            case Game.CELL_O:
                return Game.CELL_X;
            default:
                return Game.CELL_EMPTY;
        }
    }

    public LinkedList<Move> getMoves() {
        return this.moves;
    }

    /**
     * player make one move
     * @param move current move
     */
    public void makeMove(Move move) {
        this.curMove = move;
        this.moves.add(move);
    }

    @Override
    public String toString() {
        String player = this.isFirst ? "playerOne" : "playerTwo";
        return player + " @ " + this.curMove;
    }

    /**
     * implement deep clone
     * @return the cloned cell ojbect
     */
    public Object clone() {
        Player copy = null;

        try {
            copy = (Player) super.clone();
            copy.curMove = (Move) this.curMove.clone();
            copy.moves = (LinkedList<Move>) this.moves.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return copy;
    }

    // abstract protected void MoveNext(Scanner scanner, Board board);
}
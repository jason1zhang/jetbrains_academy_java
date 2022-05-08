import java.util.*;

class PlayerHuman extends Player {
    public PlayerHuman() {
        super();

        setPlayerType(Game.HUMAN);
    }

    public PlayerHuman(boolean isFirst) {
        super(isFirst);

        setPlayerType(Game.HUMAN);
    }

    @Override
    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */    
    protected void MoveNext(Board board) {

    }
}
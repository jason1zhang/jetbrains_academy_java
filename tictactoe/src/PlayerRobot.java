import java.util.*;

class PlayerRobot extends Player {
    private final int level;
    private int fc = 0;     // keep track of the function calls for the minimax function

    public PlayerRobot() {
        super();
        
        setPlayerType(Game.ROBOT);
        this.level = Game.EASY;
    }

    public PlayerRobot(boolean isFirst) {
        super(isFirst);

        setPlayerType(Game.ROBOT);
        this.level = Game.EASY;
    }

    public PlayerRobot(int level) {
        super();

        setPlayerType(Game.ROBOT);
        this.level = level;
    }

    public PlayerRobot(boolean isFirst, int level) {
        super(isFirst);

        setPlayerType(Game.ROBOT);
        this.level = level;
    }

    public int getFc() {
        return this.fc;
    }

    public void resetFc() {
        this.fc = 0;
    }

    @Override
    protected void MoveNext(Board board) {
        switch (this.level) {
            case Game.EASY:
                // System.out.println("Making move level \"easy\"");
                // MoveNextEasy(board);
                // break;
            case Game.MEDIUM:
                // System.out.println("Making move level \"medium\"");
                // MoveNextMedium(board);
                break;
            case Game.HARD:
                // System.out.println("Making move level \"hard\"");
                MoveNextHard(board);
                break;
        }
    }


    /**
     * robot makes the move at the hard level
     *
     * @param board   game board
     */
    private void MoveNextHard(Board board) {
        // finding the ultimate move that favors the computer
        Move bestMove = getNextBestMove(board);

        int row = bestMove.getCell().getRow();
        int col = bestMove.getCell().getCol();

        Cell cell = this.isFirst ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);

        CellButton cellButton = board.getBoard()[row * Game.SIZE + col];
        cellButton.setCell(cell);  // make the robot move

        cellButton.setEnabled(true);

        // draw the cell
        cellButton.setText(this.isFirst ? Game.STR_CELL_X : Game.STR_CELL_O);
        cellButton.setVisible(true);
    }

    /**
     * Get the next best move
     * @param board game board
     * @return next best move
     */
    public Move getNextBestMove(Board board) {
        LinkedList<Move> nextMoves = new LinkedList<>();        // a LinkedList to collect all the moves    

        LinkedList<Cell> availSpots = board.getEmptyCells();    // available spots

        for (Cell spot : availSpots) {
            Move nextMove = new Move(spot);
            
            int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose
            
            // set the type of the availble spot to the current player's type
            board.getBoard()[curIndex].getCell().setCellType(getPlayerCellType());

            // This is the AI player, and the next move is not maximizing. 
            // So the "isMaximizing" flag should be set to false.
            nextMove.setScore(miniMaxMove(board, 0, false));

            // reset the spot type back to empty
            board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

            nextMoves.add(nextMove); // push the object to the LinkedList
        }

        // loop over the moves and choose the move with the highest score
        Move bestMove = new Move();

        int bestScore = Game.SCORE_MIN;
        for (Move move : nextMoves) {
            if (move.getScore() > bestScore) {
                bestScore = move.getScore();
                bestMove = move;
            }
        }

        return bestMove;
    }

    /**
     * minimax algorithm implementation 
     * 
     * @param board  game board
     * @param depth how many levels down
     * @param isMaximizing if it's maximizing player moving
     * @return the score for the current position
     */
    public int miniMaxMove(Board board, int depth, boolean isMaximizing) {

        LinkedList<Cell> availSpots = board.getEmptyCells();    // available spots 
 
        if (board.checkWin(Game.CELL_X) || (board.checkWin(Game.CELL_O))) {
            if (isMaximizing) {
                return Game.SCORE_LOSS;
            } else {
                return Game.SCORE_WIN;
            }
        } else if (availSpots.size() == 0) {
            return Game.SCORE_DRAW;
        }

        if (isMaximizing) {
            int maxEval = Game.SCORE_MIN;

            // loop through available spots
            for (Cell spot : availSpots) {
                // set the type of the availble spot to the current player's type
                int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].getCell().setCellType(getPlayerCellType());
                
                // after maximizing player's turn, it's minimizing player's turn
                int eval = miniMaxMove(board, depth + 1, false);

                board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

                maxEval = Math.max(maxEval, eval);            
            }

            return maxEval;

        } else {
            int minEval = Game.SCORE_MAX;

            // loop through available spots
            for (Cell spot : availSpots) {
                // set the type of the availble spot to the opponent player's type
                int curIndex = spot.getRow() * board.getBoardSize() + spot.getCol(); // for easy debug purpose
                board.getBoard()[curIndex].getCell().setCellType(getOppPlayerCellType());

                // after minimizing player's turn, it's maximizing player's turn
                int eval = miniMaxMove(board, depth + 1, true);

                board.getBoard()[curIndex].getCell().setCellType(Game.CELL_EMPTY);

                minEval = Math.min(minEval, eval);   
            }

            return minEval;            
        }
    }    
}

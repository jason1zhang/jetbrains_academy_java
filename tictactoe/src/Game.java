class Game {
    /**
     * constants definition
     */

    // number of cells in a row on a sqaured board
    final static int SIZE = 3;

    // game state
    final static int NOT_STARTED = 0;
    final static int IN_PROGRESS = 1;
    final static int X_WIN = 2;
    final static int O_WIN = 3;
    final static int DRAW = 4;
    final static int GAME_OVER = 5;

    final static String STR_NOT_STARTED = "Game is not started";
    final static String STR_IN_PROGRESS = "Game in progress";
    final static String STR_X_WIN = "X wins";
    final static String STR_O_WIN = "O wins";
    final static String STR_DRAW = "Draw";

    // cell type at the position
    final static int CELL_EMPTY = 0;
    final static int CELL_X = 1;
    final static int CELL_O = 2;

    final static String STR_CELL_EMPTY = " ";
    final static String STR_CELL_X = "X";
    final static String STR_CELL_O = "O";

    // who is playing
    final static int ROBOT = 1;
    final static int HUMAN = 2;

    // String for human and computer
    final static String STR_HUMAN = "Human";
    final static String STR_COMPUTER = "Computer";

    // robot level
    final static int EASY = 0;
    final static int MEDIUM = 1;
    final static int HARD = 2;
        
    // String for start or reset the game
    final static String STR_START = "Start";
    final static String STR_RESET = "Reset";

    // score for the minmax function
    final static int SCORE_WIN = 10;
    final static int SCORE_LOSS = -10;
    final static int SCORE_DRAW = 0;
    final static int SCORE_MAX = 10000;
    final static int SCORE_MIN = -10000;
}

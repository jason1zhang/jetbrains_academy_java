import java.util.*;

class PlayerHuman extends Player {
    public PlayerHuman() {
        super();
    }

    public PlayerHuman(boolean isFirst) {
        super(isFirst);
    }

    // @Override
    /**
     * human makes the move
     *
     * @param scanner java.util.Scanner
     * @param board   game board
     */
    /*
    protected void MoveNext(Scanner scanner, Board board) {
        int row; // x coordinate
        int col; // y coordinate

        String strRow;
        String strCol;

        // infinite loop until human makes valid move
        while (true) {
            System.out.print("Enter the coordinates: > ");
            strRow = scanner.next();

            if (strRow.length() > 1 || strRow.toCharArray()[0] < '0' || strRow.toCharArray()[0] > '9') {
                System.out.println("You should enter numbers!");

                scanner.nextLine(); // Important! Skip the rest of the line to get rid of the carriage return
                continue;
            }

            strCol = scanner.next();
            if (strCol.length() > 1 || strCol.toCharArray()[0] < '0' || strCol.toCharArray()[0] > '9') {
                System.out.println("You should enter numbers!");
                continue;
            }

            row = strRow.toCharArray()[0] - '1';    // x coordinate starts from 0
            col = strCol.toCharArray()[0] - '1';    // y coordinate starts from 0

            if (row < 0 || row > 2 || col < 0 || col > 2) { // convert to the board coordinates
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (board.getBoard()[row * Game.SIZE + col].getCellType() != Game.CELL_EMPTY) {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                Cell cell = this.isFirst() ? new Cell(row, col, Game.CELL_X) : new Cell(row, col, Game.CELL_O);
                board.getBoard()[row * Game.SIZE + col] = cell;

                moves.add(new Move(0, cell));

                break; // break out of the while loop when human enters the valid move
            }
        }
    }
    */
}
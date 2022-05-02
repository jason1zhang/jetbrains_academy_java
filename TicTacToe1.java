// package tictactoe;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * This program is from Jetbrains Academy project - Desktop tic-tac-toe.
 * 
 * Stage 1/4 - Setup
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-30
 *
 */

public class TicTacToe1 {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        new TicTacToe();
    }
}

class TicTacToe extends JFrame {
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(450, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        // setLayout(null);

        add(new Board());
    }
}

class Game {
    /**
     * constants definition
     */

    // number of cells in a row on a sqaured board
    final static int SIZE = 3;
}


class Board extends JPanel{
    protected int size;
    protected Cell[] board;

    public Board() {
        super();

        this.size = Game.SIZE;
        this.board = new Cell[this.size * this.size];

        initComponents();

        setLayout(new GridLayout(3, 3));
        setVisible(true);
    }

    private void initComponents() {
        char chAlpha = 'A';
        char chNum = '3';
        String text = null;

        for (int i = 0; i < this.board.length; i++) {
            text = "" + chAlpha + chNum;
            this.board[i] = new Cell(text, "Button" + text);
            add(this.board[i]);

            chAlpha++;
            if (chAlpha == 'D') {
                chNum--;
                chAlpha = 'A';
            }
        }
    }
}

class Cell extends JButton {
    public Cell(String text, String name) {
        super(text);

        this.setName(name);
        this.setFont(new Font("Arial", Font.BOLD, 40));
    }
}
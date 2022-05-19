import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - The Graphics class
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-19
 *
 * Problem statement:
 *      drawing a rectangle, and allowing the user to drag it around the screen.
*/

public class Hyperskill_Graphics_Oval extends JFrame implements MouseMotionListener {
    private int rectX = 50;
    private int rectY = 50;

    public static void main(String[] args) {
        Hyperskill_Graphics_Oval drawing = new Hyperskill_Graphics_Oval();
        drawing.setVisible(true);
    }

    public Hyperskill_Graphics_Oval() {
        super("Oval Picture");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        addMouseMotionListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.drawRect(rectX, rectY, 50, 50);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        rectX = e.getX();
        rectY = e.getY();
        repaint();
    }

    @Override 
    public void mouseMoved(MouseEvent e) { }
}
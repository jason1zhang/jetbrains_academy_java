import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - Scroll bars
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-25
 *
 * Problem statement: Basics of Scroll Bars
 */

public class Hyperskill_JScrollBar_Basics {
    public static void main(String[] args) {
        JLabel label = new JLabel();
        JFrame frame = new JFrame("Scroll Bars");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL, 0, 100, 0, 500);
        horizontal.addAdjustmentListener(new MyAdjustmentListener(label, frame));
        frame.getContentPane().add(horizontal, BorderLayout.SOUTH);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}

class MyAdjustmentListener implements AdjustmentListener {
    private final JLabel label;
    private final JFrame frame;

    public MyAdjustmentListener(JLabel label, JFrame frame) {
        this.label = label;
        this.frame = frame;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        label.setText("Slider's position is " + e.getValue());
        frame.repaint();
    }
}

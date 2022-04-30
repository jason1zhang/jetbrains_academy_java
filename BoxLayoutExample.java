import javax.swing.*;
import java.awt.*;

/**
 * 
 * This program is from Jetbrains Academy topic - Layout Managers
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-30
 *
 */

 public class BoxLayoutExample extends JFrame {
     
    public BoxLayoutExample() {
        super("Box Layout Example");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        add(new JButton("First"));
        add(new JButton("Second"));
        add(new JButton("Third"));
        add(new JButton("Fourth"));
        add(new JButton("Fifth"));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setVisible(true);
    }

    public static void main(final String[] args) {
        new BoxLayoutExample();
    }
 }
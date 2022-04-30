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

 public class FlowLayoutExample extends JFrame {
     
    public FlowLayoutExample() {
        super("Flow Layout Example");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        add(new JButton("First"));
        add(new JButton("Second"));
        add(new JTextField("Enter your text here"));
        add(new JLabel("This is a long label"));
        add(new JButton("Third"));

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setVisible(true);
    }

    public static void main(final String[] args) {
        new FlowLayoutExample();
    }
 }
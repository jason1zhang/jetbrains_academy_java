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

 public class BorderLayoutExample extends JFrame {
     
    public BorderLayoutExample() {
        super("Border Layout");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // add(new JButton("North"), BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.add(new JButton("One"));
        panel.add(new JButton("Two"));
        add(panel, BorderLayout.NORTH);

        add(new JButton("East"), BorderLayout.EAST);
        add(new JButton("South"), BorderLayout.SOUTH);
        add(new JButton("West"), BorderLayout.WEST);
        add(new JButton("Center"), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(final String[] args) {
        new BorderLayoutExample();
    }
 }
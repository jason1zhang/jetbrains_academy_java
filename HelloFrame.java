import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;

import javax.swing.*;

/**
 * 
 * This program is from Jetbrains Academy topic - Swing Components
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-04-27
 *
 */

public class HelloFrame extends JFrame {

    public HelloFrame() {
        super("Hello App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        initComponents();

        setLayout(null);    // set absolute positioning of components
        setVisible(true);

    }

    private void initComponents() {
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Your name");
        nameLabel.setBounds(40, 20, 100, 30);
        add(nameLabel);

        JTextField namTextField = new JTextField();
        namTextField.setBounds(160, 20, 100, 30);
        add(namTextField);

        JPanel greenPanel = new JPanel();
        greenPanel.setBounds(40, 150, 220, 70);
        greenPanel.setLayout(new BorderLayout());
        greenPanel.setBackground(Color.GREEN);
        add(greenPanel);

        JLabel helloLabel = new JLabel();
        helloLabel.setBounds(50, 20, 100, 30);
        helloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        helloLabel.setVerticalAlignment(SwingConstants.CENTER);

        Font font = new Font("Courier", Font.BOLD, 12);
        helloLabel.setFont(font);
        helloLabel.setFont(helloLabel.getFont().deriveFont(16f));

        greenPanel.add(helloLabel); // add label to the panel

        JButton accepButton = new JButton("Accept");
        accepButton.setBounds(100, 70, 100, 30);
        add(accepButton);

        accepButton.addActionListener(e -> {
            String helloText = "Hello";
            String name = namTextField.getText();
            if (name != null & name.trim().length() > 0) {
                helloText += String.format(", %s", name);
            }

            helloLabel.setText(helloText);
        });
    }

    public static void main(String[] args) throws Exception {
        Runnable initFrame = new Runnable() {
            @Override
            public void run() {
                new HelloFrame();
            }
        };

        SwingUtilities.invokeAndWait(initFrame);
    }
}
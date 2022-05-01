import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * This program is from Jetbrains Academy topic - Window Listeners
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-01
 *
 */

 public class CheckExitExample extends JFrame {
     private class CheckOnExit extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            ConfirmWindow checker = new ConfirmWindow();
            checker.setVisible(true);
        }
     }

     private class ConfirmWindow extends JFrame implements ActionListener {
        public ConfirmWindow() {
            setSize(250, 100);
            setLayout(new BorderLayout());

            JLabel confirmLabel = new JLabel("Are you sure you want to exit?", SwingConstants.CENTER);
            add(confirmLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());

            JButton exitButton = new JButton("Yes");
            exitButton.addActionListener(this);
            buttonPanel.add(exitButton);

            JButton cancelButton = new JButton("No");
            cancelButton.addActionListener(this);
            buttonPanel.add(cancelButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            if (action.equals("Yes")) {
                System.exit(0);
            } else if (action.equals("No")) {
                dispose();
            }
            
        }
    }

    public static void main(String[] args) {
        CheckExitExample demoWindow = new CheckExitExample();
        demoWindow.setVisible(true);
    }

    public CheckExitExample() {
        setSize(300, 300);
        setTitle("Window Listener");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CheckOnExit());
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * 
 * This problem is from Jetbrains Academy topic - JComboBox
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-25
 *
 * Problem statement: Editable JComboBox
 */

public class Hyperskill_JComboBox_TimeFormattingDemo {
    public static void main(String[] args) {
        TimeFormatting timeFormatting = new TimeFormatting();
        timeFormatting.start();
    }
}

class TimeFormatting {
    private static final String[] DATE_TIME_PATTERNS = {
            "dd.MM.yyyy HH:mm:ss",
            "dd.MM.yyyy, EEEE, HH:mm:ss",
            "yyyy MMMMM dd GGG hh:mm:ss aaa",
    };

    public void start() {
        JFrame frame = createFrame();

        JComboBox<String> comboBox = createComboBox();
        frame.add(comboBox);

        JLabel label = createLabel();
        frame.add(label);

        comboBox.addActionListener(actionEvent -> onComboBoxChanged(comboBox, actionEvent));

        // Timer periodically (with the interval of 1 sec) updates the current time
        Timer timer = new Timer(1000, actionEvent -> updateCurrentTime(label, comboBox));
        timer.start();

        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Current date and time");
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    private JComboBox<String> createComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(DATE_TIME_PATTERNS);
        JComboBox<String> comboBox = new JComboBox<>(model);
        comboBox.setEditable(true);

        return comboBox;
    }

    private JLabel createLabel() {
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    private void onComboBoxChanged(JComboBox<String> comboBox, ActionEvent actionEvent) {
        // Getting the command that the combo box generates
        String command = actionEvent.getActionCommand();
        // Detecting whether the action command is "comboBoxEdited"
        if ("comboBoxEdited".equals(command)) {
            // If the item has been changed
            if (comboBox.getSelectedIndex() == -1) {
                // Getting the value of the changed element
                String newValue = (String) comboBox.getSelectedItem();
                // Adding a new item to the list
                comboBox.addItem(newValue);
            }
        }
    }

    private void updateCurrentTime(JLabel label, JComboBox<String> comboBox) {
        // Get the selected time format from the combo box
        String selectedFormat = (String) comboBox.getSelectedItem();
        if (selectedFormat != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(selectedFormat, Locale.US);
            String currentTimeFormatted = dateFormat.format(new Date());
            label.setText(currentTimeFormatted);
        }
    }
}
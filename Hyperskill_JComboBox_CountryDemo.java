import javax.swing.*;
import java.awt.*;

/**
 * 
 * This problem is from Jetbrains Academy topic - JComboBox
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-25
 *
 * Problem statement: Uneditable JComboBox
 *  create a combo box based on an array of strings representing different countries.
*/
class Hyperskill_JComboBox_CountryDemo {
    public static void main(String[] args) {
        Country country = new Country();
        country.start();
    }
}

class Country {
    private final static String[] COUNTRIES = { "Ethiopia", "Turkey", "Greece", "Iraq", "Serbia", "Colombia" };

    public void start() {
        JFrame frame = createFrame();

        JComboBox<String> comboBox = createComboBox();
        frame.add(comboBox);

        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Country");
        frame.setLayout(new FlowLayout());
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    private JComboBox<String> createComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(COUNTRIES);
        JComboBox<String> comboBox = new JComboBox<>(model);
        comboBox.setSelectedIndex(0); // choose the default option

        return comboBox;
    }
}
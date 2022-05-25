
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
 * Problem statement: Detect combo box value changes
 *  Rewrite the example by displaying the capital city of the selected country.
 *  Here we have created an array of capitals put in the same order as the countries. 
*/

class Hyperskill_JComboBox_CountryCapitalDemo {
    public static void main(String[] args) {
        CountryCapital countryCapital = new CountryCapital();
        countryCapital.start();
    }
}

class CountryCapital {
    private final static String[] COUNTRIES = { "Ethiopia", "Turkey", "Greece", "Iraq", "Serbia", "Colombia" };
    private final static String[] CAPITALS = { "Addis Ababa", "Ankara", "Athens", "Baghdad", "Belgrade", "Bogota" };

    public void start() {
        JFrame frame = createFrame();

        JComboBox<String> comboBox = createComboBox();
        frame.add(comboBox);

        JLabel label = createLabel(comboBox.getSelectedIndex());
        frame.add(label);

        comboBox.addActionListener(actionEvent -> label.setText(CAPITALS[comboBox.getSelectedIndex()]));

        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Country and Capital");
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

    private JLabel createLabel(int selectedIndex) {
        JLabel label = new JLabel();
        label.setText(CAPITALS[selectedIndex]);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }
}
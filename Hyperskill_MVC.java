import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * 
 * This problem is from Jetbrains Academy topic - Model-View-Controller (MVC) in Swing
 * 
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-05-20
 *
 * Problem statement:
 *  
*/

public class Hyperskill_MVC{
    public static void main(String[] args) {
        int number = 1;

        Model model = new Model(number);
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.control();
    }
}

class Model {
    private int number;

    public Model(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void increment() {
        this.number++;
    }
}

class View {
    private JFrame frame;
    private JLabel label;
    private JButton button;

    public View() {
        frame = new JFrame("View");
        label = new JLabel();
        button = new JButton("Button");
        frame.getContentPane().setLayout(new BorderLayout());

        // close on exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // window size
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
    }

    public void setText(String str) {
        label.setText(str);
    }

    public JButton getButton() {
        return button;
    }
}

class Controller {
    private Model model;
    private View view;
    private ActionListener actionListener;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void control() {
        actionListener = actionEvent -> {
            model.increment();
            view.setText(Integer.toString(model.getNumber()));
        };
        
        view.getButton().addActionListener(actionListener);
    }
}
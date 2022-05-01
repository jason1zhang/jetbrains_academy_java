import javax.swing.JButton;
import java.awt.*;

public class Cell extends JButton {
    public Cell(String text, String name) {
        super(text);
        
        this.setName(name);     
        this.setFont(new Font("Arial", Font.BOLD, 40));   
    }
}

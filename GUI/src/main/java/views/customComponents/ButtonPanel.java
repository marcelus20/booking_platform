package views.customComponents;

import javax.swing.*;
import java.awt.*;

/**
 * This class class is a Panel with a button inside.
 * Used to create JButtons with a pre configured class.
 *
 */
public class ButtonPanel extends JPanel {

    /**
     * The JButton attribute
     */
    private final MyCustomJButton button;

    /**
     * Constructor
     * @param buttonLabel
     */
    public ButtonPanel(String buttonLabel) {
        this.button = new MyCustomJButton(buttonLabel);//setting up button
        setBorder(BorderFactory.createLineBorder(Color.lightGray)); //creating border
        Integer cols = 2;
        setLayout(new GridLayout(1,cols)); // seting to grid layout manager
        add(button.getButton());
    }

    public MyCustomJButton getButton() {
        return button;
    }
}

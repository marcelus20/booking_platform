package views.customComponents;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private final MyCustomJButton button;

    public ButtonPanel(String buttonLabel) {

        this.button = new MyCustomJButton(buttonLabel);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
        Integer cols = 2;

        setLayout(new GridLayout(1,cols));
        add(button.getButton());
    }

    public MyCustomJButton getButton() {
        return button;
    }
}

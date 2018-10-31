package views.inputPanel;

import javax.swing.*;

public class InputPasswordPanel extends InputPanel<JPasswordField>{

    public InputPasswordPanel(String inputLabel) {
        super(inputLabel);
        input = new JPasswordField(20);
    }

}

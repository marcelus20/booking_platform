package views.inputPanel;

import javax.swing.*;

public class TextFieldPanel extends InputPanel<JTextField> {
    public TextFieldPanel(String label) {
        super(label);
        input = new JTextField();
    }
}

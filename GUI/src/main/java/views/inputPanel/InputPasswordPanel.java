package views.inputPanel;

import javax.swing.*;

public class InputPasswordPanel extends JPanel{



    private JLabel inputLabel;
    private JPasswordField password;

    public InputPasswordPanel(String inputLabel) {
        this.inputLabel = new JLabel(inputLabel);
        this.password = new JPasswordField(20);
    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(JLabel inputLabel) {
        this.inputLabel = inputLabel;
    }

    public JTextField getPassword() {
        return password;
    }

    public void setPassword(JPasswordField password) {
        this.password = password;
    }
}

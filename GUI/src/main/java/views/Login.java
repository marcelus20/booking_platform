package views;

import views.customComponents.ButtonPanel;
import views.customComponents.InputPanel;
import views.customComponents.MyCustomJFrame;
import views.customComponents.MyCustomJLabel;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.*;

public class Login extends MyCustomJFrame {

    private final MyCustomJLabel title;
    private final InputPanel email;
    private final InputPanel password;
    private final ButtonPanel login;
    private final ButtonPanel signUp;

    public Login() {
        super("Barbers Booking Platform System", 500,350);
        this.title = new MyCustomJLabel("Enter your credentials", 30);
        this.email = new InputPanel("Email: ", new JTextField());
        this.password = new InputPanel("Password", new JPasswordField());
        this.login = new ButtonPanel("","Login");
        this.signUp = new ButtonPanel("", "Sign up");

        getFramePanel().setLayout(new BorderLayout());
        JPanel credentials = new JPanel();
        credentials.setLayout(new GridLayout(3,1));

        getFramePanel().add(title.getLabel(), BorderLayout.NORTH);
        getFramePanel().add(credentials, BorderLayout.CENTER);
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(login); buttons.add(signUp);

        credentials.add(email); credentials.add(password); credentials.add(buttons);

        validadeAndRepaint();
    }
}

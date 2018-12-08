package views.login;

import static models.utils.ArrayListBuilder.arrayListBuilder;

import views.InputPanelContainer;
import views.captcha.MyCaptcha;
import views.customComponents.ButtonPanel;
import views.customComponents.InputPanel;
import views.customComponents.MyCustomJFrame;
import views.customComponents.MyCustomJLabel;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;


public class Login extends MyCustomJFrame implements InputPanelContainer {

    private final MyCustomJLabel pageTitle;
    private final InputPanel email;
    private final InputPanel password;
    private final ButtonPanel login;
    private final ButtonPanel signUp;
    private MyCaptcha myCaptcha;

    public Login() {
        super("Barbers Booking Platform System", 500,550);
        this.pageTitle = new MyCustomJLabel("Enter your credentials", 30);
        this.email = new InputPanel("Email: ", new JTextField());
        this.password = new InputPanel("Password", new JPasswordField());
        this.login = new ButtonPanel("Login");
        this.signUp = new ButtonPanel( "Sign up");
        this.myCaptcha = new MyCaptcha();

//        pack();
        JFrame.setDefaultLookAndFeelDecorated(true);

        getFramePanel().setLayout(new BorderLayout());
        JPanel credentials = new JPanel();
        credentials.setLayout(new GridLayout(3,1));

        getFramePanel().add(pageTitle.getLabel(), BorderLayout.NORTH);
        JPanel centerPanel = new JPanel();
        getFramePanel().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridLayout(2,1));
        centerPanel.add(credentials);
        centerPanel.add(myCaptcha);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2));
        buttons.add(login); buttons.add(signUp);

        getFramePanel().add(buttons, BorderLayout.SOUTH);
        credentials.add(email); credentials.add(password); ;

        validadeAndRepaint();
    }

    public JLabel getPageTitle() {
        return pageTitle.getLabel();
    }

    public String getEmail() {
        return email.getInput().getInput().getText();
    }

    public String getPassword() {
        return password.getInput().getInput().getText();
    }

    public JButton getLogin() {
        return login.getButton().getButton();
    }

    public JButton getSignUp() {
        return signUp.getButton().getButton();
    }

    @Override
    public List<InputPanel> getInputsPanel() {
        return arrayListBuilder(new ArrayList<InputPanel>()).add(email).add(password).build();
    }

    public MyCaptcha getMyCaptcha() {
        return myCaptcha;
    }

    public void setMyCaptcha(MyCaptcha myCaptcha) {
        this.myCaptcha = myCaptcha;
    }
}

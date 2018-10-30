package controllers.loginControllers;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import views.login.LoginSignUpInvitation;

import javax.swing.*;
import java.awt.*;

public class LoginSignUpInvitationController implements Controlls, ViewsObjectGetter<LoginSignUpInvitation> {

    private LoginSignUpInvitation loginSignUpInvitation;

    private LoginSignUpInvitationController() {
        loginSignUpInvitation = new LoginSignUpInvitation();
        config();
        setSizes();
        build();
    }

    public static LoginSignUpInvitationController makeLoginSignUpInvitationController(){
        return new LoginSignUpInvitationController();
    }


    @Override
    public void config() {

        JPanel c = loginSignUpInvitation.getContainer();
        c.setLayout(new GridLayout(0,1));

        c.add(loginSignUpInvitation.getInvitationLabel());
        c.add(loginSignUpInvitation.getSignUpButton());
    }

    @Override
    public void build() {
        JPanel c = loginSignUpInvitation.getContainer();
        c.add(loginSignUpInvitation.getInvitationLabel());
        c.add(loginSignUpInvitation.getSignUpButton());
        loginSignUpInvitation.add(c);
    }

    @Override
    public void setSizes() {
        JPanel c = loginSignUpInvitation.getContainer();
        loginSignUpInvitation.getInvitationLabel().setFont(new Font("Courier", Font.BOLD,15));
        c.setPreferredSize(new Dimension(410,80));

    }
    @Override
    public LoginSignUpInvitation getViewObject() {
        return loginSignUpInvitation;
    }
}

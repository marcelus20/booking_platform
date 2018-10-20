package controllers.loginControllers;

import interfaces.Controlls;
import views.login.LoginSignUpInvitation;

import javax.swing.*;
import java.awt.*;

public class LoginSignUpInvitationController implements Controlls {

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
        c.setPreferredSize(new Dimension(410,80));

    }
    public LoginSignUpInvitation buildLoginSignUpInvitation(){
        return loginSignUpInvitation;
    }
}

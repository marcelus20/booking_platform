package views.login;

import utils.ArrayListBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginSignUpInvitation extends JPanel{

    private JPanel container;
    private JLabel invitationLabel;
    private JButton signUpButton;

    public LoginSignUpInvitation(JPanel container, JLabel invitationLabel, JButton signUpButton) {
        this.container = container;
        this.invitationLabel = invitationLabel;
        this.signUpButton = signUpButton;
    }

    public LoginSignUpInvitation() {
        container = new JPanel();
        invitationLabel = new JLabel("New to the System? Sign up for free!");
        signUpButton = new JButton("Sinup");
    }

    public JPanel getContainer() {
        return container;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }

    public JLabel getInvitationLabel() {
        return invitationLabel;
    }

    public void setInvitationLabel(JLabel invitationLabel) {
        this.invitationLabel = invitationLabel;
    }

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    public List<Component> getAllPanels(){
        return new ArrayListBuilder().add(container).build();
    }

}

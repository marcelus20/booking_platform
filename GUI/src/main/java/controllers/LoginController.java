package controllers;

import interfaces.Controlls;
import interfaces.Repository;
import models.users.AbstractUser;
import repository.LoginRepository;
import views.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements Controlls{

    private Login login;
    private Repository<LoginRepository, AbstractUser> loginRep;

    public LoginController() {
        login = new Login(this);
        loginRep = new LoginRepository();

        config();
        setSizes();
        build();
    }

    @Override
    public void config() {

        login.setVisible(true);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.repaint();
        login.validate();
    }

    @Override
    public void build() {
        JPanel p = login.getMainPanel();
        p.add(login.getWelcomeMessage());p.add(login.getLabelUsername()); p.add(login.getEmail());
        p.add(login.getLabelPassword());p.add(login.getPassword()); p.add(login.getErrorMessage());
        p.add(login.getLoginButton());

        login.add(p);
    }

    @Override
    public void setSizes() {
        login.setSize(350,350);

    }


    public void addEventListener(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = login.getEmail().getText();
                char[] password = login.getPassword().getPassword();

                AbstractUser admin = loginRep.selectObj(email, new String(password));

                if(admin == null){
                    login.getErrorMessage().setText("email or password not correct!");
                    login.getErrorMessage().setBackground(Color.CYAN);
                    login.getErrorMessage().setOpaque(true);
                }else{

                }
                System.out.println(admin);

                //System.out.println(new LoginRepository().selectData());
            }
        });
    }
}
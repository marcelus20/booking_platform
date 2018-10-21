package controllers.loginControllers;

import interfaces.Controlls;
import interfaces.Repository;
import models.users.AbstractUser;
import models.users.Customer;
import repository.LoginRepository;
import views.login.Login;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements Controlls{

    private final Integer WIDTH = 350;

    private Login login;
    private Repository<LoginRepository, AbstractUser> loginRep;



    public LoginController() {
        loginRep = new LoginRepository();
        login = new Login(this);
        config();
        setSizes();
        build();
    }

    @Override
    public void config() {
        JPanel mainPanel = login.getMainPanel();
        mainPanel.setLayout(new BorderLayout());



        login.setVisible(true);
        login.repaint();
        login.validate();

    }

    @Override
    public void build() {
        JPanel mainPanel = login.getMainPanel();
        mainPanel.add(login.getLoginCredentials(), BorderLayout.CENTER);
        mainPanel.add(login.getLoginSignUpInvitation(), BorderLayout.SOUTH);
        login.add(mainPanel);


    }

    @Override
    public void setSizes() {
        JPanel c = login.getLoginCredentials();
        JPanel s = login.getLoginSignUpInvitation();
        c.setPreferredSize(new Dimension(WIDTH,250));
        s.setPreferredSize(new Dimension(WIDTH, 80));
        login.setSize(WIDTH,350);
    }

    public void addEventListener(JButton button){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = login.getEmail().getText();
                char[] password = login.getPassword().getPassword();
                //System.out.println(email);
                //System.out.println(password);

                AbstractUser user = loginRep.selectObj(email, new String(password));


                if(user == null){
                    login.getErrorMessage().setText("email or password not correct!");
                    login.getErrorMessage().setBackground(Color.CYAN);
                    login.getErrorMessage().setOpaque(true);

                }else{
                    login.getErrorMessage().setText("");
                    login.getErrorMessage().setOpaque(false);
                }
                System.out.println(user);



            }
        });
    }

    /*
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
        login.setLayout(new BorderLayout());
        login.getSignUpPanel().setPreferredSize(new Dimension(410,80));
        login.getMainPanel().setLayout(new GridLayout(2,3));

        login.setVisible(true);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.repaint();
        login.validate();
    }

    @Override
    public void build() {
        JPanel p = login.getMainPanel();
        JPanel s = login.getSignUpPanel();
        p.add(login.getWelcomeMessage());p.add(login.getLabelUsername()); p.add(login.getEmail());
        p.add(login.getLabelPassword());p.add(login.getPassword()); p.add(login.getErrorMessage());
        p.add(login.getLoginButton());s.add(login.getSignUpLabel()); s.add(login.getSignUpButton());


        login.add(p, BorderLayout.CENTER);
        login.add(s, BorderLayout.SOUTH);
    }

    @Override
    public void setSizes() {
        login.setSize(410,350);

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
    */
}
package controllers;

import models.Database;
import models.utils.Tools;
import models.users.AbstraticUser;
import views.login.Login;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class LoginController implements Control{

    private Login login;
    private List<Boolean> validator;

    public LoginController() {
        this.login = new Login();
        validator = Arrays.asList(false, false);
        switchOffLoginButton();
        addButtonsAFunction();
        addInputsAListener();
    }


    private void assignLoginButtonAFunction(){
        login.getLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database db = new Database();
                    AbstraticUser user = db.login(login.getEmail(), login.getPassword());
                    if(user == null){
                        Tools.alertError(login, "Email or password not correct!", "Wrong Credentials");
                    }else{
                        System.out.println(user);
                    }
                } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
                    e1.printStackTrace();
                }
                System.out.println(validator);

            }
        });
    }
    private void assignSignUpAButtonAFunction(){
        login.getSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(login, "redirection to Forms");
            }
        });
    }

    private void switchOffLoginButton(){
        login.getLogin().setEnabled(false);
    }

    private void switchOnLoginButton(){
        login.getLogin().setEnabled(true);
    }

    @Override
    public void addButtonsAFunction() {
        assignLoginButtonAFunction();
        assignSignUpAButtonAFunction();
    }

    @Override
    public void addInputsAListener() {
        login.getInputPanels().forEach(input->{
            input.getInput().getInput().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    validate(e);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validate(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    validate(e);
                }
                public void validate(DocumentEvent e){

                    String inputTxt = input.getInput().getInput().getText();
                    String label = input.getLabel().getText();


                    if(inputTxt.length() > 3){
                        if(label.contains("Email")){
                            validator.set(0, true);
                        }else{
                            validator.set(1, true);
                        }
                    }else{
                        if(label.contains("Email")){
                            validator.set(0, false);
                        }else{
                            validator.set(1, false);
                        }
                    }
//
                    if(validator.stream().reduce(true, (current, next)->current && next)){
                        switchOnLoginButton();
                    }else{
                        switchOffLoginButton();
                    }

                }
            });
        });
    }
}

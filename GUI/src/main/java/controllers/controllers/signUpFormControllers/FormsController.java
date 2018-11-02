package controllers.controllers.signUpFormControllers;

import controllers.Application;
import controllers.controllers.loginControllers.LoginController;
import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import views.signUpForms.Forms;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FormsController implements Controlls<Forms>, ViewsObjectGetter<Forms> {

    private Forms forms;
    private Application app;

    private FormsController(Application app) throws SQLException {
        this.forms = new Forms();
        this.app = app;
        config();
        setSizes();
        build();
        displayDefaultPanel();
    }

    public static FormsController initFormsController(Application app) throws SQLException {
        return new FormsController(app);
    }

    @Override
    public void config() {
        assignButtonsAnAction();
        forms.setLayout(new BorderLayout());
        forms.getFormView().setLayout(new BorderLayout());
        forms.getSideBar().setLayout(new GridLayout(0,1));
        forms.setVisible(false);
        forms.validate();
        forms.repaint();

    }

    @Override
    public void build() {
        forms.add(forms.getSideBar(), BorderLayout.LINE_START);
        forms.add(forms.getFormView(), BorderLayout.LINE_END);
        forms.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        forms.getSideBar().add(forms.getToggleCustomerButton());
        forms.getSideBar().add(forms.getToggleServiceProviderButton());

    }

    @Override
    public void setSizes() {
        forms.getSideBar().setPreferredSize(new Dimension(250,500));
        forms.getFormView().setPreferredSize(new Dimension(520,500));
        forms.setSize(770,500);
    }

    @Override
    public Forms getViewObject() {
        return forms;
    }

    private void displayPanel(JPanel panel){
        System.out.println("aqui");
        forms.getFormView().removeAll();
        forms.getFormView().add(panel, BorderLayout.CENTER);
        forms.validate();
        forms.repaint();
    }

    private void displayDefaultPanel(){
        forms.getFormView().removeAll();
        forms.getFormView().add(forms.getCustomerSignUpForm());
        forms.validate();
        forms.repaint();
    }

    public void setVisible(){
        forms.setVisible(true);
    }

    public void setVisible(Boolean flag){
        forms.setVisible(flag);
    }

    public void setInvisible(){
        forms.setVisible(false);
    }

    private void assignButtonsAnAction(){
        forms.getToggleCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDefaultPanel();
            }
        });

        forms.getToggleServiceProviderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayPanel(ServiceProviderSignUpFormController
                            .initServiceProviderSignUpFormController().getViewObject());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        forms.getCustomerSignUpForm().getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.getForms().setInvisible();
                app.setForms(null);
                try {
                    app.setLogin(LoginController.initLoginController(app));
                    forms.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }




}

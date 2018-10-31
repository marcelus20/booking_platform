package controllers.signUpFormControllers;

import interfaces.Controlls;
import interfaces.ViewsObjectGetter;
import models.users.Admin;
import repository.AdminSignUpFormRepository;
import views.signUpForms.AdminSignUpForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminSignUpFormController implements Controlls, ViewsObjectGetter {

    private AdminSignUpForm adminSignUpForm;
    private Admin user;
    private AdminSignUpFormRepository admSR;




    private AdminSignUpFormController() throws SQLException {
        this.adminSignUpForm = new AdminSignUpForm();
        user = new Admin();
        admSR = new AdminSignUpFormRepository();
        config();
        setSizes();
        build();
    }

    public static AdminSignUpFormController initAdminSignUpController() throws SQLException {
        return new AdminSignUpFormController();
    }

    @Override
    public void config() {
        //adminSignUpForm.setLayout(new BoxLayout(adminSignUpForm, BoxLayout.Y_AXIS));
        assignButtonsAFunction();
    }

    @Override
    public void build() {
        adminSignUpForm.add(adminSignUpForm.getFormName());
        adminSignUpForm.add(adminSignUpForm.getEmail());
        adminSignUpForm.add(adminSignUpForm.getPassword());
        adminSignUpForm.add(adminSignUpForm.getSubmit());
        adminSignUpForm.add(adminSignUpForm.getCancel());
    }

    @Override
    public void setSizes() {
        adminSignUpForm.getFormName().setFont(new Font("Courier", Font.BOLD,30));
    }

    @Override
    public AdminSignUpForm getViewObject() {
        return adminSignUpForm;
    }

    private void assignButtonsAFunction(){
        adminSignUpForm.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.withEmail(adminSignUpForm.getEmail().getInput().getText());
                user.withPassword(new String(adminSignUpForm.getPassword().getInput().getPassword()));
                System.out.println(user);
                admSR.insertData(user);

            }
        });
    }
}

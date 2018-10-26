package main;

import controllers.signUpFormControllers.AdminSignUpFormController;
import controllers.signUpFormControllers.ServiceProviderSignUpFormController;


import javax.swing.*;
import java.sql.SQLException;


public class Main extends JFrame {

    private Main() throws SQLException {



        this.add(ServiceProviderSignUpFormController.initServiceProviderSignUpFormController().getViewObject());
        //this.add(CustomerSignUpFormController.initCustomerSignUpController().getViewObject());
        //this.add(AdminSignUpFormController.initAdminSignUpController().getViewObject());
        this.setVisible(true);
        this.setSize(550,500);
        this.repaint();
        this.validate();


    }

    Main(int a){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        System.out.println(date);
    }

    public static void main(String... args) throws SQLException {
        new Main();
        //new Main(1);

        //new LoginController();


    }
}

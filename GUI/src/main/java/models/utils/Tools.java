package models.utils;

import models.entitiesRepresentation.Admin;
import models.entitiesRepresentation.Customer;
import models.entitiesRepresentation.ServiceProvider;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Tools {

    public static ServiceProvider serviceProviderMapper(ResultSet rs) throws SQLException {
        ServiceProvider serviceProvider = new ServiceProvider();

            serviceProvider.withId(rs.getString("s_id"));
            serviceProvider.withEmail(rs.getString("email"));
            serviceProvider.withDateCreated(Date.valueOf(rs.getString("date_of_account_creation")));
            serviceProvider.withPhone("phone");
            serviceProvider.withCompanyFullName(rs.getString("company_full_name"));
            serviceProvider.withApprovedStatus(rs.getString("approved_status"));

        return serviceProvider;
    }

    public static Admin adminMapper(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
            admin.withId(rs.getString("id"));
            admin.withEmail(rs.getString("email"));
        return admin;
    }

    public static Customer customerMapper(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.withId(rs.getString("customer_id"));
        customer.withEmail(rs.getString("email"));
        customer.withPhone(rs.getString("Phone"));
        customer.withFirstName(rs.getString("first_name"));
        customer.withLastName(rs.getString("last_name"));
        customer.withDateCreated(rs.getDate("date_of_account_creation"));


        return customer;
    }

    public static void alertError(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void alertWarning( JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void alertMsg(JFrame frame, String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public static void formValidator(Callable f) throws Exception {
        f.call();
    }

    public static Boolean validatePasswordsInput(String pass, String confirmPass){
        return pass.equals(confirmPass);
    }

    public static Boolean validatePasswordCriteria(String pass){
        return pass.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,20})");
    }


    public static Boolean validateEmail(String email) {
        return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email).find();
    }

    public static Boolean validateStringsNonSmallerEqualsThan3(String str){
        return str.length() > 3;
    }

    public static Boolean validateNotNumericStrings(String str){

        return str.matches("[A-Za-z ]+") && validateStringsNonSmallerEqualsThan3(str);
    }

    //Hashing password to MD5 using the DigestUtils from apache.commons
    public static String hashingPassword(String pass){
        return DigestUtils.md5Hex(pass).toUpperCase();
    }
}

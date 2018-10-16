package main;

import models.users.Customer;

import java.sql.*;


public class Main {

    private Main(){
        Customer cs = new Customer();
        cs.withId((long)1).withEmail("blah@gmail.com").withPassword("12345");
        cs.withFirstName("Felipe").withLastName("Mantovani");
        System.out.println(cs);

    }


    public static void main(String... args){
        new Main();
        /*
        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");


            Statement myStmt = myConn.createStatement();

            String query = new StringBuilder().append("SELECT * FROM admin").toString();

            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("eMail"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        */

    }
}

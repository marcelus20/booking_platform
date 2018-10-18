package repository;

import interfaces.Repository;
import models.users.AbstractUser;
import models.users.Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginRepository implements Repository {



    @Override
    public void insertData() {

    }

    @Override
    public List<List> selectData(String query) {
        return null;
    }

    @Override
    public AbstractUser selectObj(String email, String password) {
        List<List> result= new ArrayList<>();
        AbstractUser user;
        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
            Statement myStmt = myConn.createStatement();

            String query = new StringBuilder().append("SELECT * FROM admin WHERE email = '")
                    .append(email).append("' && password='").append(password).append("';").toString();
            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){

                List<String> line = new ArrayList<>();
                line.add(rs.getString("id"));
                line.add(rs.getString("email"));
                line.add(rs.getString("password"));

                result.add(line);
                /*
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("eMail"));
                */
            }

            myStmt.close();
            myConn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result.size() == 0){
            return null;
        }else{
            user = new Admin();
            user.withId(Long.valueOf((String) result.get(0).get(0)) );
            user.withPassword((String) result.get(0).get(1));
            user.withEmail((String) result.get(0).get(2));

            return user;
        }

    }

    @Override
    public void updateData() {

    }
}

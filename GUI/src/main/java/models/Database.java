package models;

import models.users.AbstraticUser;
import models.utils.Tools;


import java.sql.*;

public class Database {

    protected Connection conn;
    protected Statement stmt;


    public Database() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance() ;

        init();
    }

    public void init() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/booking_platform", "root", "");
        stmt = conn.createStatement();
    }

    public void close() throws SQLException {
        conn.close();
        stmt.close();
    }

    public AbstraticUser login(String email, String password) throws SQLException {
        init();
        AbstraticUser user = null;

        String[] tables = {"admin", "customers", "service_provider"};


        for(String table : tables){
            String query  = new StringBuilder().append("SELECT * FROM ").append(table)
                    .append(" WHERE email = ").append("'").append(email).append("'")
                    .append(" AND password = '").append(password).append("' ;").toString();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                if(table.equalsIgnoreCase("admin")){
                    user = Tools.adminMapper(rs);
                }else if (table.equalsIgnoreCase("customers")){
                    user = Tools.customerMapper(rs);
                }else {
                    user = Tools.serviceProviderMapper(rs);
                }
            }
        }
        close();
        return user;
    }

}

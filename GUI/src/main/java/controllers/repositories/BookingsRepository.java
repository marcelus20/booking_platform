package controllers.repositories;

import models.users.AbstractUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsRepository extends Database{
    public BookingsRepository() throws SQLException {
    }

    @Override
    public List<List> selectAll() {
        List<List> data = new ArrayList<>();
        try{
            initConnAndStatement();
            String query = new StringBuilder("SELECT ")
                    .append("b.time_stamp, b.complaint, ")
                    .append("c.customer_id, s.s_id, c.first_name, c.last_name, ")
                    .append("s.company_full_name ")
                    .append("FROM booking AS b ")
                    .append("JOIN customers AS c ")
                    .append("ON b.customer_id = c.customer_id ")
                    .append("JOIN service_provider AS s ")
                    .append("ON b.s_id = s.s_id")
                    .append(";").toString();
            System.out.println(query);
            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                List<String> line = new ArrayList<>();
                line.add(rs.getString("time_stamp"));
                line.add(rs.getString("complaint"));
                line.add(rs.getString("customer_id"));
                line.add(rs.getString("s_id"));
                line.add(rs.getString("first_name"));
                //line.add(rs.getString("password"));
                line.add(rs.getString("last_name"));
                line.add(rs.getString("company_full_name"));
                //line.add(rs.getString("company_full_name"));
                data.add(line);
            }
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return data;

    }

    @Override
    public List<List> executeSelectQuery(String query) {
        return null;
    }

    @Override
    public Long getId(AbstractUser user) {
        return null;
    }

    @Override
    public void executeUpdateQuery(String query) {
        try{
            initConnAndStatement();
            myStmt.executeUpdate(query);
            closeConnAndStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}

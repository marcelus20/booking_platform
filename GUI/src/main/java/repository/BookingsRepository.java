package repository;

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
                    .append("c.customer_id, s.s_id, c.first_name, c.last_name, ")
                    .append("s.company_full_name, ")
                    .append(" b.time ")
                    .append("FROM booking AS b ")
                    .append("JOIN customers AS c JOIN service_provider AS s ON b.customer_id = c.customer_id ")
                    .append(" AND b.s_id = s.s_id ")
                    .append(";").toString();
            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                List<String> line = new ArrayList<>();
                line.add(rs.getString("customer_id"));
                line.add(rs.getString("s_id"));
                line.add(rs.getString("first_name"));
                //line.add(rs.getString("password"));
                line.add(rs.getString("last_name"));
                line.add(rs.getString("company_full_name"));
                line.add(rs.getString("time"));
                line.add(rs.getString("company_full_name"));

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

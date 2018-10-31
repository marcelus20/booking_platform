package repository;

import models.users.AbstractUser;
import models.users.Admin;

import java.sql.SQLException;
import java.util.List;


public class AdminSignUpFormRepository extends Database {

    public AdminSignUpFormRepository() throws SQLException {
    }

    @Override
    public void insertData(Object obj) {
        Admin user = (Admin) obj;
        try{
            String queryInsertionToServiceProviderTable = new StringBuilder()
                    .append("INSERT INTO admin ")
                    .append("(password, email) ")
                    .append("VALUES( ")
                    .append("'").append(user.getPassword()).append("'")
                    .append(", ")
                    .append("'").append(user.geteMail()).append("'")
                    .append(");")
                    .toString();

            System.out.println(queryInsertionToServiceProviderTable);


            myStmt.executeUpdate(queryInsertionToServiceProviderTable);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public List<List> selectAll() {
        return null;
    }

    @Override
    public List<List> executeSelectQuery(String query) {
        return null;
    }


    @Override
    public Long getId(AbstractUser user) {
        return null;
    }

}

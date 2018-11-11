package models.repositories;

import models.Database;
import models.entitiesRepresentation.Customer;

import java.sql.SQLException;

public class CustomerRepository extends Database implements Repository {

    public CustomerRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }


    @Override
    public void addToDB(Object obj) throws SQLException {
        init();
        Customer c = (Customer)obj;

        String query = new StringBuilder()
                .append("INSERT INTO customers (password, email,")
                .append(" phone, first_name, last_name, date_of_account_creation) VALUES ( ")
                .append("'").append(c.getPassword()).append("', ")
                .append("'").append(c.getEmail()).append("', ")
                .append("'").append(c.getPhone()).append("', ")
                .append("'").append(c.getFirstName()).append("', ")
                .append("'").append(c.getLastName()).append("', ")
                .append("'").append(c.getDateCreated()).append("'); ").toString();
        stmt.executeUpdate(query);

        close();
    }
}

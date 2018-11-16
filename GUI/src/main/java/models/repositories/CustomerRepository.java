package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Customer;
import models.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerRepository extends Database implements Repository<Customer>{

    public CustomerRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }


    @Override
    public void addToDB(Customer obj) throws SQLException {
        init();
        Customer c = obj;

        c.withPassword(Tools.hashingPassword(c.getPassword()));

        String query = "INSERT INTO users (user_type, email, password, date_created)" +
                "VALUES ('"+c.getUserType()+"','"+c.getEmail()+"','"+c.getPassword()+"','"+c.getDateCreated()+"')";

        stmt.executeUpdate(query);

        String id = selectIdOfUser(c.getEmail());

        query = "INSERT INTO customers ( c_id, first_name, last_name) " +
                "VALUES ('"+id+"','"+c.getFirstName()+"','"+c.getLastName()+"');";

        stmt.executeUpdate(query);

        query = "INSERT INTO phone_list VALUES ('"+id+"','"+c.getPhone().getPhone()+"')";

        stmt.executeUpdate(query);

        close();
    }

    @Override
    public Customer selectObjById(Object id) throws SQLException {
        Customer customer = new Customer();
        init();



        close();
        return customer;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        init();

        ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE email = '"+email+"'");
        while (rs.next()){
            return rs.getString("id");
        }

        close();
        return null;
    }

    @Override
    public List<Customer> getList(AbstraticUser user) throws SQLException {
        return null;
    }


}

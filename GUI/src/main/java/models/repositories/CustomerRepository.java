package models.repositories;

import models.Database;
import models.enums.BookingStatus;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Customer;
import models.tuples.entitiesRepresentation.Phone;
import models.utils.Tools;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CustomerRepository implements Repository<Customer>{

    public CustomerRepository(){
    }


    @Override
    public void addToDB(Customer obj) throws SQLException {

        Customer c = obj;

        c.withPassword(Tools.hashingPassword(c.getPassword()));

        String query = "INSERT INTO users (user_type, email, password, date_created)" +
                "VALUES ('"+c.getUserType()+"','"+c.getEmail()+"','"+c.getPassword()+"','"+c.getDateCreated()+"')";

        Database.database().getStmt().executeUpdate(query);

        String id = selectIdOfUser(c.getEmail());

        query = "INSERT INTO customers ( c_id, first_name, last_name) " +
                "VALUES ('"+id+"','"+c.getFirstName()+"','"+c.getLastName()+"');";

        Database.database().getStmt().executeUpdate(query);

        query = "INSERT INTO phone_list (id, phone)VALUES ('"+id+"','"+c.getPhone().getPhone()+"')";

        Database.database().getStmt().executeUpdate(query);

    }

    @Override
    public Customer selectObjById(Object id) throws SQLException {
        Customer customer = new Customer();

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM customers c JOIN phone_list p ON c.c_id = p.id WHERE id = '"+id+"';");
        while (rs.next()){
            customer.withUserType(UserType.CUSTOMER);
            customer.withId(rs.getString("c_id"));
            customer.withFirstName(rs.getString("first_name"));
            customer.withLastName(rs.getString("last_name"));
            customer.withListOfPhones(new Phone((String) id,rs.getString("phone")));
        }

        return customer;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT id FROM users WHERE email = '"+email+"'");
        while (rs.next()){
            return rs.getString("id");
        }
        return null;
    }

    @Override
    public List<Customer> getList(AbstraticUser user) throws SQLException {
        return null;
    }


}

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

/**
 * This class will handle the interaction with database done by a customer object.
 * Implements Repository<Customer>
 */
public class CustomerRepository implements Repository<Customer>{

    /**
     * empty constructor
     */
    public CustomerRepository(){
    }


    /**
     * Adding a customer to DB. this will fill table users, then record Customers then records the phone Table
     * @param obj
     * @throws SQLException
     */
    @Override
    public void addToDB(Customer obj) throws SQLException {
        Customer c = obj;
        c.withPassword(Tools.hashingPassword(c.getPassword()));// hashing password before inserting it to DB
        String query = "INSERT INTO users (user_type, email, password, date_created)" +// inserting to users table
                "VALUES ('"+c.getUserType()+"','"+c.getEmail()+"','"+c.getPassword()+"','"+c.getDateCreated()+"')";
        Database.database().getStmt().executeUpdate(query);
        String id = selectIdOfUser(c.getEmail()); // geting the new ID of this user
        query = "INSERT INTO customers ( c_id, first_name, last_name) " +// inserting into customer table
                "VALUES ('"+id+"','"+c.getFirstName()+"','"+c.getLastName()+"');";
        Database.database().getStmt().executeUpdate(query);
        //recording into phoe_list table the phone of user
        query = "INSERT INTO phone_list (id, phone)VALUES ('"+id+"','"+c.getPhone().getPhone()+"')";
        Database.database().getStmt().executeUpdate(query);
    }

    /**
     * This  function will get the Customer ID and, find this user in the database and return the entire customer mapped
     * to a Customer class.
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Customer selectObjById(Object id) throws SQLException {
        Customer customer = new Customer();

        ResultSet rs = Database.database().getStmt().executeQuery("SELECT * FROM customers c JOIN phone_list p ON c.c_id = p.id WHERE id = '"+id+"';");
        while (rs.next()){
            // mapping columns to their respective attributes
            customer.withUserType(UserType.CUSTOMER);
            customer.withId(rs.getString("c_id"));
            customer.withFirstName(rs.getString("first_name"));
            customer.withLastName(rs.getString("last_name"));
            customer.withListOfPhones(new Phone((String) id,rs.getString("phone")));
        }
        return customer;
    }

    /**
     * returns the id of the Customer by passing the email as parameter
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public String selectIdOfUser(String email) throws SQLException {
        // loging for the id with that email in the record
        ResultSet rs = Database.database().getStmt().executeQuery("SELECT id FROM users WHERE email = '"+email+"'");
        while (rs.next()){
            return rs.getString("id");
        }
        //if result set has no results, it will return null - so there is no id correspondent to the given email
        return null;
    }

    //
    @Override
    public List<Customer> getList(AbstraticUser user) throws SQLException {
        return null;
    }


}

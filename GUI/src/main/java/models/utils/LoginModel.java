package models.utils;

import models.Database;
import models.enums.ServiceProviderStatus;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * login Model class. It will handle the database interaction for the login proccess.
 * Similarly with the repositories, but it does not represent any entity.
 */
public class LoginModel {

    /**
     * function login in
     * @param email
     * @param password
     * @return
     */
    public AbstraticUser login(String email, String password){
        try {
            AbstraticUser user = null;

            /**
             * preparing query
             */
            String query  = new StringBuilder().append("SELECT * FROM ").append("users")
                    .append(" WHERE email = ").append("'").append(email).append("'")
                    .append(" AND password = '").append(password).append("' ;").toString();

            ResultSet rs;
            rs = Database.database().getStmt().executeQuery(query);
            while (rs.next()){
                /**
                 * if a record is found in users table, it will check the user_type column and assign the user
                 * object to its corresponding type
                 */
                if(UserType.valueOf(rs.getString("user_type")).equals(UserType.ADMIN)){
                    user = new Admin();// if admin
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.ADMIN);
                }else if (UserType.valueOf(rs.getString("user_type")).equals(UserType.SERVICE_PROVIDER)){
                    user = new ServiceProvider();// if service provider
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.SERVICE_PROVIDER);
                }else if(UserType.valueOf(rs.getString("user_type")).equals(UserType.CUSTOMER)){
                    user = new Customer();// if customer
                    user.withId(rs.getString("id"));
                    user.withUserType(UserType.CUSTOMER);
                }
                /**
                 * after the user type has ben assigned to user, it will fill up with the rest of the columns in the tble
                 * the email, password and date of registration
                 */
                user.withEmail(rs.getString("email"));
                user.withPassword(rs.getString("password"));
                user.withDateCreated(rs.getDate("date_created"));
            }
            /**
             * If no records is found, then user is null, so we cant populate user with the rest of the attributes from
             * the others tables
             */
            if(user != null) {
                user = populateTheRestofAttributes(user);
                Log log = new Log(user.getId(), user.getEmail()+" has just logged in!");
                Tools.recordALogToDB(log); // log the operation
            }
            //return the user
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This function is part of the login process. It assigns the correct attribute population accordingly to the user type.
     * Eg: if user is a Customer, then this method will download from customer table, if user is service,
     * it will download from service_provider and location table. If admin, it will just return the admin, cause
     * admin has no information in other tables aside from users.
     * @param user
     * @return
     * @throws SQLException
     */
    private AbstraticUser populateTheRestofAttributes(AbstraticUser user) throws SQLException {
        // return admin stright away cause it does not need to be populated somewhereelse
        if(user.getUserType() == UserType.ADMIN){
            return user;
        }
        String query = "";
        //if customer,
        if(user.getUserType().equals(UserType.CUSTOMER)){
            query = "SELECT * FROM customers WHERE c_id = '"+user.getId()+"'";
        }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){ // if service provider
            query = "SELECT * FROM service_provider WHERE s_id = '"+user.getId()+"'";
        }
        /**
         * populating the user object to the rest of relevant information.
         * Eg: customer with firstname and lasname attribute...
         */
        ResultSet rs = Database.database().getStmt().executeQuery(query);
        while(rs.next()){
            if(user.getUserType().equals(UserType.CUSTOMER)){
                ((Customer) user).withFirstName(rs.getString("first_name"));
                ((Customer) user).withLastName(rs.getString("last_name"));
            }else if (user.getUserType().equals(UserType.SERVICE_PROVIDER)){
                ((ServiceProvider) user).withCompanyFullName(rs.getString("company_full_name"));
                ((ServiceProvider) user).withServiceProviderStatus(ServiceProviderStatus
                        .valueOf(rs.getString("approved_status")));
            }
        }
        // returning user with more information encapsulated
        return user;
    }
}

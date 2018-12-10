/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */

package models.repositories;

import models.tuples.entitiesRepresentation.AbstraticUser;
import java.sql.SQLException;
import java.util.List;

/**
 * The repositories classes are the ones responsible to have the closest interaction with the database.
 * It means that these are the classes that will contain the sql statements, write , update and read information from database.
 * As per the massive amount of tables in the database, each table will be interacted to a different Repository class.
 * Eg: ServiceProviderRepository, CustomerRepository  and AdminRepository class will only look after the interaction with the
 * users, service_provider tables in the database schema, whereas BookingRepository will interact with the table booking and so on.
 *
 * The common that all of these repositories classe have is the functions bellow declared. Thats the why keeping in an
 * interface implementation.
 * @param <T>
 */
public interface Repository <T>{ //T is generic to any entity representation. could be Repository<Admin>

    /**
     * Adding the object to a db. If T is Admin, for example, it will add the admin to the table of users.
     * @param obj
     * @throws SQLException
     */
    void addToDB(T obj) throws SQLException;

    /**
     * The id is an object in this case, because few tables in the database has a compose key which turns out
     * that sometimes, the id should be a tuple of 2 elements instead of single value string or integer.
     * @param id
     * @return
     * @throws SQLException
     */
    T selectObjById(Object id) throws SQLException; // method that selects the object by its given id.

    /**
     * selects id of user by a given email address -> this may not be implemented by all repositories classes.
     * @param email
     * @return
     * @throws SQLException
     */
    String selectIdOfUser(String email) throws SQLException;

    /**
     * Gets a list of all the results sets belonged to that user.
     * if Repository<Booking>, then will return a List<Booking> related to the user.
     * @param user
     * @return
     * @throws SQLException
     */
    List<T> getList(AbstraticUser user) throws SQLException;

}

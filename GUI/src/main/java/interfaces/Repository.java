package interfaces;

import models.users.AbstractUser;

import java.sql.ResultSet;
import java.util.List;

public interface Repository <T> {

    void insertData(Object obj);

    List<List> selectData(String query);

    AbstractUser login(String email, String password);

    void updateData();

    /**
     * this method will take the User as a parameter, look for this user in the database and return its id.
     * @param user
     * @return
     */
    Long getId(AbstractUser user);


}

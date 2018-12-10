/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */


package models.repositories;

import models.Database;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Admin;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * This class is an implementation of the Repository interface.
 * It tuned out that for the porpuses of the project, the only function implemented was the addToDB
 * when an admin is registering another admin
 */
public class AdminRepository implements Repository<Admin> {
    public AdminRepository() {
    }

    @Override
    public void addToDB(Admin obj) {
        Admin admin = obj; // declaring admin object
        try {
            /**
             * creatingg an statement from Database Singleton.
             */
            Database.database().getStmt().executeUpdate(//executing query.
                    "INSERT INTO users (email, password, user_type, date_created)" +
                            " VALUES ('"+admin.getEmail()
                    +"', '"+admin.getPassword()+"', '"+ UserType.ADMIN +"', '"+new Date(System.currentTimeMillis()) +"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Admin selectObjById(Object id){
        return null;
    }

    @Override
    public String selectIdOfUser(String email){
        return null;
    }

    @Override
    public List<Admin> getList(AbstraticUser user){
        return null;
    }
}

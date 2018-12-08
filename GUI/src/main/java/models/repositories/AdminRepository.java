package models.repositories;

import models.Database;
import models.enums.UserType;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Admin;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdminRepository implements Repository<Admin> {
    public AdminRepository() {
    }

    @Override
    public void addToDB(Admin obj) {
        Admin admin = (Admin)obj;
        try {
            Database.database().getStmt().executeUpdate(
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

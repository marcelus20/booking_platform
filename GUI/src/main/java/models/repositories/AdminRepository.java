package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.AbstraticUser;
import models.tuples.entitiesRepresentation.Admin;

import java.sql.SQLException;
import java.util.List;

public class AdminRepository extends Database implements Repository<Admin> {
    public AdminRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }

    @Override
    public void addToDB(Admin obj) throws SQLException {
        Admin admin = (Admin)obj;
        init();
        stmt.executeUpdate("INSERT INTO users (email, password, user_type, date_created) VALUES ('"+admin.getEmail()
                +"', '"+admin.getPassword()+"', '"+admin.getUserType()+"', '"+admin.getDateCreated()+"')");
        close();

    }


    @Override
    public Admin selectObjById(Object id) throws SQLException {
        return null;
    }

    @Override
    public String selectIdOfUser(String email) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> getList(AbstraticUser user) throws SQLException {
        return null;
    }
}

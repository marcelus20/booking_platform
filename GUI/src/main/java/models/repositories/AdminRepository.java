package models.repositories;

import models.Database;
import models.tuples.entitiesRepresentation.Admin;

import java.sql.SQLException;

public class AdminRepository extends Database implements Repository<Admin> {
    public AdminRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
    }

    @Override
    public void addToDB(Object obj) throws SQLException {
        Admin admin = (Admin)obj;
        init();
        stmt.executeUpdate("INSERT INTO admin (email, password) VALUES ('"+admin.getEmail()
                +"', '"+admin.getPassword()+"')");
        close();

    }

    @Override
    public Object selectObjById(Object id) throws SQLException {
        return null;
    }
}

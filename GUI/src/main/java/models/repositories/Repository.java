package models.repositories;

import models.users.User;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{

    void addToDB(Object obj) throws SQLException;

    Object selectObjById(Object id) throws SQLException;

    List<Object> selectAll();
}

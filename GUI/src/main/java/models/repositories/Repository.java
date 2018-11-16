package models.repositories;

import models.tuples.entitiesRepresentation.AbstraticUser;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T>{

    void addToDB(T obj) throws SQLException;

    T selectObjById(Object id) throws SQLException;

    String selectIdOfUser(String email) throws SQLException;

    List<T> getList(AbstraticUser user) throws SQLException;

}

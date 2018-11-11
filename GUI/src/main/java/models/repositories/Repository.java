package models.repositories;

import java.sql.SQLException;

public interface Repository <T>{

    void addToDB(Object obj) throws SQLException;
}

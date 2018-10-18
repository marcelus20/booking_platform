package interfaces;

import java.sql.ResultSet;
import java.util.List;

public interface Repository <T, G> {

    void insertData();

    List<List> selectData(String query);

    G selectObj(String email, String password);

    void updateData();


}

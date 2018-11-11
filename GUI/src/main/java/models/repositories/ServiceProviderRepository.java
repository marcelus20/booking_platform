package models.repositories;

import models.Database;
import models.entitiesRepresentation.Location;
import models.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceProviderRepository extends Database implements Repository{


    public ServiceProviderRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    }

    @Override
    public void addToDB(Object obj) throws SQLException {
        init();
        ServiceProvider s = (ServiceProvider)obj;
        Location l = s.getLocation();

        s.withPassword(Tools.hashingPassword(s.getPassword()));

        //FOR THE NEW service provider
        String query = new StringBuilder()
                .append("INSERT INTO service_provider (password, email, date_of_account_creation,")
                .append(" phone, company_full_name, approved_status) VALUES ( ")
                .append("'").append(s.getPassword()).append("', ")
                .append("'").append(s.getEmail()).append("', ")
                .append("'").append(s.getDateCreated()).append("', ")
                .append("'").append(s.getPhone()).append("', ")
                .append("'").append(s.getCompanyFullName()).append("', ")
                .append("'").append(s.getApprovedStatus()).append("') ;").toString();


        stmt.executeUpdate(query);

        String newId = "";
        ResultSet rs = stmt.executeQuery("SELECT s_id FROM service_provider WHERE email = '"+s.getEmail() + "'; ");
        while (rs.next()){
            newId = rs.getString("s_id");
        }

        //FOR THE LOCATION WISE
        query = new StringBuilder()
                .append("INSERT INTO location (s_id, first_line_address, eir_code,")
                .append(" city, second_line_address) VALUES ( ")
                .append("'").append(newId).append("', ")
                .append("'").append(l.getFirstLineAddress()).append("', ")
                .append("'").append(l.getEirCode()).append("', ")
                .append("'").append(l.getCity()).append("', ")
                .append("'").append(l.getSecondLineAddress()).append("'); ")
                .toString();

        stmt.executeUpdate(query);

        close();
    }
}

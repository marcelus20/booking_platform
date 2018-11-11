package models.repositories;

import models.Database;
import models.entitiesRepresentation.ServiceProvider;
import models.utils.Tools;

import java.sql.SQLException;

public class ServiceProviderRepository extends Database implements Repository{


    public ServiceProviderRepository() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {

    }

    @Override
    public void addToDB(Object obj) throws SQLException {
        init();
        ServiceProvider s = (ServiceProvider)obj;

        s.withPassword(Tools.hashingPassword(s.getPassword()));

        String query = new StringBuilder()
                .append("INSERT INTO service_provider (password, email, date_of_account_creation,")
                .append(" phone, company_full_name, approved_status) VALUES ( ")
                .append("'").append(s.getPassword()).append("', ")
                .append("'").append(s.getEmail()).append("', ")
                .append("'").append(s.getDateCreated()).append("', ")
                .append("'").append(s.getPhone()).append("', ")
                .append("'").append(s.getCompanyFullName()).append("', ")
                .append("'").append(s.getApprovedStatus()).append("') ;").toString();
        System.out.println(query);

        stmt.executeUpdate(query);
        close();
    }
}

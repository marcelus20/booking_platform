package controllers.controllers.dashboardController.adminControlls;

import models.entities.Customer;
import models.repositories.CustomerRepository;
import utils.Tools;
import views.dashboard.adminView.ListOfCustomers;
import javax.swing.*;
import java.sql.SQLException;

public class TablesOfCustomers
        extends TableOfEntitiesController<ListOfCustomers, CustomerRepository, Customer> {
    public TablesOfCustomers(ListOfCustomers tableOfEntities, CustomerRepository repository) throws SQLException {
        super(tableOfEntities, repository);
        config();
        build();
        setSizes();
    }

    @Override
    public void build() {
        super.build();
        String[] columns = new String[]{"id", "email",  "phone", "first name", "last name","date_of_account_creation"};
        tableOfEntities.setTable(new JTable(Tools.convertListToArray(results), columns));
        tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
        tableOfEntities.add(tableOfEntities.getScrollPane());
    }

}

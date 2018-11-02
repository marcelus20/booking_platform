package controllers.controllers.dashboardController.adminControlls;

import controllers.controllers.dashboardController.adminControlls.statusChangers.ChangingServiceProvider;
import models.users.ServiceProvider;
import controllers.repositories.ServiceProviderRepository;
import utils.Tools;
import views.dashboard.adminView.ListOfServices;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableOfServiceProviders
        extends TableOfEntitiesController<ListOfServices, ServiceProviderRepository,
        ServiceProvider> {


    public TableOfServiceProviders(ListOfServices tableOfEntities, ServiceProviderRepository repository) throws SQLException {
        super(tableOfEntities, repository);
        config();
        build();
        setSizes();
    }

    @Override
    public void build() {
        super.build();
        String[] columns;
        mappedResults = mapResultsToObjects(results);
        columns = new String[]{"id", "email",  "date of registration", "phone",
                "Name of Company","Approved Status", "first line address", "Eir code",
                "city", "Second Line Address"};
        if(results.size() == 0){
            tableOfEntities.add(new JLabel("NO RESULTS TO BE SHOWN"));
        }else{
            tableOfEntities.setTable(new JTable(Tools.convertListToArray(results), columns));
            tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
            tableOfEntities.add(tableOfEntities.getScrollPane());
            tableOfEntities.getTable().setAutoCreateRowSorter(true);
            tableOfEntities.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if(!e.getValueIsAdjusting()){
                        Integer rowIndex = tableOfEntities.getTable().getSelectedRow();

                        String id = tableOfEntities.getTable().getModel().getValueAt(rowIndex, 0).toString();
                        mappedResults.forEach(serviceProvider -> {
                            if (serviceProvider.getId() == Long.parseLong(id)){
                                new ChangingServiceProvider(serviceProvider, repository)
                                                                                                                .getViewObject();
                            }
                        });
                    }
                }
            }
            );
        }

    }

    @Override
    public void setSizes() {
        super.setSizes();
    }

    @Override
    protected List<ServiceProvider> mapResultsToObjects(List<List> data) {
        System.out.println(data);
        List mappedList = new ArrayList<>();
        data.forEach(line->mappedList.add(Tools.serviceMapper(line)));
        System.out.println(mappedList);
        return mappedList;
    }
}

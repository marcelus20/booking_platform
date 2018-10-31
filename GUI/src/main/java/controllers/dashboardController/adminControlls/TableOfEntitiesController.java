package controllers.dashboardController.adminControlls;

import controllers.dashboardController.DashboardController;
import interfaces.Controlls;
import interfaces.Repository;
import interfaces.ViewsObjectGetter;

import models.users.ServiceProvider;
import repository.ServiceProviderRepository;
import utils.Tools;

import views.dashboard.Dashboard;
import views.dashboard.adminView.ListOfServices;
import views.dashboard.adminView.TableOfEntities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class TableOfEntitiesController
        <T extends TableOfEntities, G extends Repository> implements Controlls<T>,
        ViewsObjectGetter<T> {


    private final T tableOfEntities;
    private Repository<G> repository;
    private List<List> results;
    private List<ServiceProvider> mappedResults;



    private TableOfEntitiesController(T tableOfEntities, G repository) throws SQLException {
        this.tableOfEntities = tableOfEntities;
        this.repository = repository;
        config();
        build();
        setSizes();
    }

    public static <T extends TableOfEntities, G extends Repository> TableOfEntitiesController
            <T, G>initTableOfEntitiesController
            (T tableOfEntities, G repository) throws SQLException {
        return new TableOfEntitiesController<T, G>(tableOfEntities, repository);
    }


    @Override
    public void config() {
        tableOfEntities.setLayout(new BorderLayout());
    }

    @Override
    public void build() {
        results = repository.selectAll();

        tableOfEntities.add(tableOfEntities.getTitle(), BorderLayout.NORTH);
        String[] columns;
        if(repository instanceof ServiceProviderRepository){
             mappedResults = mapResultsToObjects(results);
             columns = new String[]{"id", "email",  "date of registration", "phone",
                    "Name of Company","Approved Status", "first line address", "Eir code",
                    "city", "Second Line Address"};
        }else{
            columns = new String[]{"id", "email",  "phone", "first name", "last name","date_of_account_creation"};
        }

        tableOfEntities.setTable(new JTable(Tools.convertListToArray(results), columns));
        tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
        tableOfEntities.add(tableOfEntities.getTitle());
        tableOfEntities.add(tableOfEntities.getScrollPane(),BorderLayout.SOUTH);
        tableOfEntities.getTable().setAutoCreateRowSorter(true);
        tableOfEntities.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    Integer rowIndex = tableOfEntities.getTable().getSelectedRow();
                    String id = tableOfEntities.getTable().getModel().getValueAt(rowIndex, 0).toString();
                    mappedResults.forEach(serviceProvider -> {
                        if (serviceProvider.getId() == Long.parseLong(id)){
                            new ChanginStatusWindowController(serviceProvider, repository)
                                    .getViewObject();
                        }
                    });

                }

            }
        });


    }


    @Override
    public void setSizes() {
        tableOfEntities.getScrollPane().getVerticalScrollBar()
                .setPreferredSize(new Dimension(20,0));
        tableOfEntities.getScrollPane().getHorizontalScrollBar()
                .setPreferredSize(new Dimension(0,20));
        tableOfEntities.getTable()
                .setPreferredScrollableViewportSize(new Dimension(900,500));
    }

    @Override
    public T getViewObject() {
        return tableOfEntities;
    }

    private List<ServiceProvider> mapResultsToObjects(List<List> data){
        System.out.println(data);
        List<ServiceProvider> mappedList = new ArrayList<>();
        data.forEach(line->mappedList.add(Tools.serviceMapper(line)));
        return mappedList;
    }

//    @Override
//    public void config() {
//        tableOfEntities.setLayout(new BorderLayout());
//
//    }
//
//    @Override
//    public void build() {
//        tableOfEntities.add(tableOfEntities.getTitle(), BorderLayout.NORTH);
//
//        String[][] data;
//        String[] columns;
//        if(tableOfEntities instanceof ListOfCustomers){
//            results = cr.selectAll();
//            data = Tools.convertListToArray(results);
//            columns = new String[]{"id", "email",  "phone", "first name", "last name","date_of_account_creation"};
//        }else{
//            if(results == null){
//                results = sr.selectAll();
//            }
//            data = Tools.convertListToArray(sr.selectAll());
//            columns = new String[]{"id", "email",  "date of registration", "phone",
//                    "Name of Company","Approved Status", "first line address", "Eir code",
//                    "city", "Second Line Address"};
//            handleServices(results, columns);
//        }
//        tableOfEntities.setTable(new JTable(data, columns));
//        tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
//        tableOfEntities.add(tableOfEntities.getScrollPane(),BorderLayout.SOUTH);
//    }
//
//    @Override
//    public void setSizes() {
//        tableOfEntities.getScrollPane().getVerticalScrollBar()
//                .setPreferredSize(new Dimension(20,0));
//        tableOfEntities.getScrollPane().getHorizontalScrollBar().setPreferredSize(new Dimension(0,20));
//        tableOfEntities.getTable().setPreferredScrollableViewportSize(new Dimension(900,500));
//    }
//
//    @Override
//    public T getViewObject() {
//        return tableOfEntities;
//    }
//
//    private void handleServices(List<List> data, String[] columns){
//        ListOfServices listOfServices = (ListOfServices) tableOfEntities;
//        List<String> names = new ArrayList<>();
//        names.add("All");
//        mapResultsToObjects(data).forEach(serviceProvider -> names.add(serviceProvider.getCompanyFullName()));
//        Collections.sort(names);
//        names.forEach(name->listOfServices.getNameOfCompanyFilter().getInput().addItem(name));
//        listOfServices.getFilterPanel().add(listOfServices.getNameOfCompanyFilter());
//        listOfServices.getFilterPanel().add(listOfServices.getLocationFilter());
//        listOfServices.add(listOfServices.getFilterPanel(), BorderLayout.CENTER);
//        listOfServices.repaint();
//        listOfServices.validate();
//    }
//
//    private List<ServiceProvider> mapResultsToObjects(List<List> data){
//        List<ServiceProvider> mappedList = new ArrayList<>();
//        data.forEach(line->mappedList.add(Tools.serviceMapper(line)));
//        return mappedList;
//    }
//
//    public List<List> getResults() {
//        return results;
//    }
}

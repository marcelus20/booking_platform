package controllers.controllers.dashboardController.adminControlls;

import controllers.controllers.dashboardController.adminControlls.statusChangers.ChangingBookings;
import models.Bookings;
import controllers.repositories.BookingsRepository;
import utils.Tools;
import utils.Tuple;
import views.dashboard.adminView.ListOfBookings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableOfBookings extends TableOfEntitiesController<ListOfBookings, BookingsRepository, Bookings> {
    public TableOfBookings(ListOfBookings tableOfEntities, BookingsRepository repository) throws SQLException {
        super(tableOfEntities, repository);
        config();
        build();
        setSizes();
    }

    @Override
    public void build() {
        super.build();
        mappedResults = mapResultsToObjects(results);
        String[] columns = new String[]{"Customer id","service id","Customer Name",
                "Customer last name", "Company", "Time"};
        if (results.size() == 0){
            tableOfEntities.add(new JLabel("NO RESULTS TO BE SHOWN"));
        }else{
            tableOfEntities.setTable(new JTable(Tools.convertListToArray(results), columns));
            tableOfEntities.setScrollPane(new JScrollPane(tableOfEntities.getTable()));
            tableOfEntities.add(tableOfEntities.getScrollPane());
            tableOfEntities.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Integer rowIndex = tableOfEntities.getTable().getSelectedRow();
                    if(!e.getValueIsAdjusting()){
                        Tuple<String, String> id = Tuple
                                .tuple(tableOfEntities.getTable().getModel().getValueAt(rowIndex, 0).toString(),
                                        tableOfEntities.getTable().getModel().getValueAt(rowIndex, 1).toString());
                        mappedResults.forEach(bookings -> {
                            if (bookings.getCustomerId() == Long.parseLong(id.getX())
                                    && bookings.getServiceId() == Long.parseLong(id.getY())){
                                new ChangingBookings(bookings, repository)
                                        .getViewObject();
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    protected List<Bookings> mapResultsToObjects(List<List> data) {
        System.out.println(data);
        List mappedList = new ArrayList<>();
        data.forEach(line->mappedList.add(Tools.bookingsMapper(line)));
        System.out.println(mappedList);
        return mappedList;
    }
}

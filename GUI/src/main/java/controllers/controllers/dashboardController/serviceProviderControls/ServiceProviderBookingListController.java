package controllers.controllers.dashboardController.serviceProviderControls;

import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import models.entities.Bookings;
import models.entities.ServiceProvider;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import utils.Tools;
import views.dashboard.serviceProviderView.bookingList.ServiceProviderBookingList;

import java.awt.*;
import java.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceProviderBookingListController
        implements Controlls<ServiceProviderBookingList>, ViewsObjectGetter<ServiceProviderBookingList> {

    private ServiceProviderBookingList serviceProviderBookingList;
    private ServiceProvider user;
    private Repository<ServiceProviderRepository> svpRep;

    private ServiceProviderBookingListController(ServiceProvider user) throws SQLException {
        svpRep = new ServiceProviderRepository();
        serviceProviderBookingList = new ServiceProviderBookingList();
        this.user = ((ServiceProviderRepository)svpRep).populateBookingsAttribute(user);

        config();
        setSizes();
        build();

    }

    public static ServiceProviderBookingListController
    initServiceProviderBookingListController(ServiceProvider user) throws SQLException {
        return new ServiceProviderBookingListController(user);
    }


    private void settingUpTable() {

        List<Bookings> bookingsList = user.getBookings();


        List<List> data = bookingsList.stream().map(booking->{
            List<String> line = new ArrayList<>();
            line.add(String.valueOf(booking.getTimestamp()));
            line.add(String.valueOf(booking.getCustomerId()));
            line.add(booking.getBookingStatus());
            line.add(booking.getComplaint());
            return line;
        }).collect(Collectors.toList());

        String[][] dataArray = Tools.convertListToArray(data);

        String[] columnsNames = new String[]{"time_stamp", "customer_id", "booking_status", "complaint"};
        serviceProviderBookingList.setTable(dataArray, columnsNames);


    }

    @Override
    public void config() {
        serviceProviderBookingList.getHeader().setLayout(new GridLayout(2,1));
        serviceProviderBookingList.setLayout(new BorderLayout());
        settingUpTable();
    }



    @Override
    public void build() {
        serviceProviderBookingList.getHeader().add(serviceProviderBookingList.getTitle());
        serviceProviderBookingList.getHeader().add(serviceProviderBookingList.getInstructions());

        serviceProviderBookingList.setScrollPane(serviceProviderBookingList.getTable());

        serviceProviderBookingList.add(serviceProviderBookingList.getHeader(), BorderLayout.NORTH);
        serviceProviderBookingList.add(serviceProviderBookingList.getScrollPane(), BorderLayout.CENTER);

        serviceProviderBookingList.repaint();
        serviceProviderBookingList.validate();

    }

    @Override
    public void setSizes() {

    }

    @Override
    public ServiceProviderBookingList getViewObject() {
        return serviceProviderBookingList;
    }
}

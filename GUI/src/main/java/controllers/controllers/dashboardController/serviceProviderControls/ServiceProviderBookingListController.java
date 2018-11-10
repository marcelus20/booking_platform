package controllers.controllers.dashboardController.serviceProviderControls;

import controllers.controllers.Controlls;
import controllers.controllers.ViewsObjectGetter;
import models.entities.Bookings;
import models.entities.ServiceProvider;
import models.repositories.CustomerRepository;
import models.repositories.Repository;
import models.repositories.ServiceProviderRepository;
import utils.Tools;
import utils.Tuple;
import views.dashboard.serviceProviderView.bookingList.ServiceProviderBookingList;

import java.awt.*;
import java.util.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceProviderBookingListController
        implements Controlls<ServiceProviderBookingList>, ViewsObjectGetter<ServiceProviderBookingList> {

    private ServiceProviderBookingList serviceProviderBookingList;
    private ServiceProviderBookingList serviceProviderNotCompleteBookingList;
    private ServiceProvider user;
    private Repository<ServiceProviderRepository> svpRep;
    private Repository<CustomerRepository> cusRep;

    private ServiceProviderBookingListController(ServiceProvider user) throws SQLException {
        svpRep = new ServiceProviderRepository();
        cusRep = new CustomerRepository();
        serviceProviderBookingList = new ServiceProviderBookingList();
        serviceProviderNotCompleteBookingList = new ServiceProviderBookingList("Bookings Comming Up");
        this.user = ((ServiceProviderRepository)svpRep).populateBookingsAttribute(user);
        config();
        setSizes();
        build();

    }

    public static ServiceProviderBookingListController
    initServiceProviderBookingListController(ServiceProvider user) throws SQLException {
        return new ServiceProviderBookingListController(user);
    }


//    private List<Bookings> generateBookingsTable(){
//        final List<Bookings> bookingsList = new ArrayList<>();
//
//        if(viewingOnlyNotCompleteBookings){
//
//            user.getBookings().forEach(booking ->{
//
//                if(booking.getBookingStatus().equalsIgnoreCase("not complete")){
//                    bookingsList.add(booking);
//                }
//            });
//        }else{
//
//            bookingsList.addAll(user.getBookings());
//        }
//
//        return bookingsList;
//    }

    private List<List> generateAlldata(List<Bookings> bookingsList){
        System.out.println(bookingsList);
        return bookingsList.stream().map(booking->{

            System.out.println(bookingsList);


            Tuple<String, String> name = Tuple.tuple("","");
            try {
                name = ((CustomerRepository)cusRep).getCustomerName(booking.getCustomerId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            List<String> line = new ArrayList<>();
            line.add(String.valueOf(booking.getTimestamp()));
            line.add(name.getX());  line.add(name.getY());
            line.add(booking.getBookingStatus());
            line.add(booking.getComplaint());
            return line;

        }).collect(Collectors.toList());
    }


    public void settingUpTable() {
        //updating bookings
      List<Bookings> bookingsList = user.getBookings();

      List<Bookings> notCompleteBookings = user.getBookings().stream()
              .map(b->{
                  if(b.getBookingStatus().equalsIgnoreCase("not complete")){
                      return b;
                  }
                  return null;
              }).collect(Collectors.toList());

      List<List> data = generateAlldata(bookingsList);
      List<List> dataOfNotCompleteBookings = generateAlldata(Tools.getRidOfNullElementInLists(notCompleteBookings));
//      List<List> data = bookingsList.stream().map(booking->{
//
//            Tuple<String, String> name = Tuple.tuple("","");
//            try {
//                name = ((CustomerRepository)cusRep).getCustomerName(booking.getCustomerId());
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            List<String> line = new ArrayList<>();
//            line.add(String.valueOf(booking.getTimestamp()));
//            line.add(name.getX());  line.add(name.getY());
//            line.add(booking.getBookingStatus());
//            line.add(booking.getComplaint());
//            return line;
//        }).collect(Collectors.toList());

        String[][] dataArray = Tools.convertListToArray(data);
        String[][] dataArrayOfNotCompleteBooking = Tools.convertListToArray(dataOfNotCompleteBookings);

        String[] columnsNames = new String[]{"time_stamp", "customer name", "customer surname","booking_status", "complaint"};
        serviceProviderBookingList.setTable(dataArray, columnsNames);
        serviceProviderNotCompleteBookingList.setTable(dataArrayOfNotCompleteBooking, columnsNames);


        serviceProviderBookingList.getTable().repaint();
        serviceProviderBookingList.getTable().validate();

        serviceProviderNotCompleteBookingList.repaint();
        serviceProviderNotCompleteBookingList.validate();
    }

    @Override
    public void config() {
        serviceProviderBookingList.getHeader().setLayout(new GridLayout(2,1));
        serviceProviderBookingList.setLayout(new BorderLayout());
        serviceProviderNotCompleteBookingList.getHeader().setLayout(new GridLayout(2,1));
        serviceProviderNotCompleteBookingList.setLayout(new BorderLayout());
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

    public ServiceProviderBookingList getServiceProviderBookingList() {
        return serviceProviderBookingList;
    }

    public ServiceProviderBookingList getServiceProviderNotCompleteBookingList() {
        return serviceProviderNotCompleteBookingList;
    }
}

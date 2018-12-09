package main;

import controllers.Application;
import models.repositories.BookingRepository;

public class Main {

    /**
     * Before initialising the application. The function BookingRepository.updateAllOldBookingsToComplete will
     * delete from table booking slots all slots older than now that had been booked, and also to set all
     * bookings older than now to complete.
     *
     * After that the appplication itself will start and user will be prompted to enter credentials
     * @param args
     */
    public static void main(String... args) {
        BookingRepository.updateAllOldBookingsToComplete();//=>flushing database
        new Application();// calling the application instance
    }
}

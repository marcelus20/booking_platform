package main;

import controllers.Application;
import models.repositories.BookingRepository;


public class Main {



    public static void main(String... args) {
        BookingRepository.updateAllOldBookingsToComplete();
        new Application();
    }
}

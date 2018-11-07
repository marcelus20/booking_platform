package utils;

import models.entities.Bookings;
import models.entities.Location;
import models.entities.ServiceProvider;

import javax.swing.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Tools {

    public static String[][] convertListToArray(List<List> list){
        if(list.size() == 0){
            return new String[0][0];
        }
        final Integer lineSize = list.get(0).size();
        final Integer listSize = list.size();

        String[][] convertedList = new String[listSize][lineSize];

        for(int i = 0; i < listSize; i++){
            for(int j = 0; j < lineSize; j++){
                convertedList[i][j] = String.valueOf(list.get(i).get(j));
            }
        }
        return convertedList;

    }

    public static ServiceProvider serviceMapper(List<String> line){

        ServiceProvider serviceProvider = new ServiceProvider();
        Location location = new Location();
        serviceProvider.withId(Long.parseLong(line.get(0)));
        serviceProvider.withEmail(line.get(1));
        serviceProvider.withDateOfAccountCreation(Date.valueOf(line.get(2)));
        serviceProvider.withPhone(line.get(3));
        serviceProvider.withCompanyFullName(line.get(4));
        serviceProvider.withApprovedStatus(line.get(5));

        location.withFirstLineAddress(line.get(6));
        location.withEirCode(line.get(7));
        location.withCity(line.get(8));
        location.withSecondLineAddress(line.get(9));
        serviceProvider.withLocation(location);
        return serviceProvider;
    }

    public static Bookings bookingsMapper(List<String> line){
        Bookings bookings = new Bookings();



        bookings.withTimestamp(Timestamp.valueOf(line.get(0)));
        bookings.withServiceId(Long.parseLong(line.get(1)));
        bookings.withBookingStatus(line.get(2));
        bookings.withComplaint(line.get(3));
        bookings.withCustomerId(Long.parseLong(line.get(4)));



        return bookings;
    }



    public static class MyCustomDateTime {

        Time time;

        Timestamp timestamp;

        public MyCustomDateTime(Timestamp timestamp) {

            this.timestamp = timestamp;
            time = Time.valueOf(String.valueOf(timestamp).split(" ")[0]);
        }

        public MyCustomDateTime(Time time) {
            timestamp = formatDateTime(Timestamp.valueOf(getCurrentDate() + " " + time));
            time = time;
        }

        public MyCustomDateTime(String dateString, String timeString) {
            time = Time.valueOf(timeString);
            timestamp = Timestamp.valueOf(dateString + " "+ timeString);
        }

        public MyCustomDateTime() {
            time = Time.valueOf("08:00:00");
            timestamp = Timestamp.valueOf(getCurrentDate() + " "+ time);
        }

        public Date getCurrentDate(){
            return new Date(System.currentTimeMillis());
        }

        private Timestamp getCurrentDatetime(){
            return formatDateTime(new Timestamp(System.currentTimeMillis()));
        }

        private Timestamp formatDateTime(Timestamp timestamp){
            return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));
        }

        public MyCustomDateTime add30(){
            timestamp = new Timestamp(timestamp.getTime()+1800000);
            time = new Time(timestamp.getTime());
            return this;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public Time getTime() {
            return time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyCustomDateTime dateTime = (MyCustomDateTime) o;
            return Objects.equals(time, dateTime.time) &&
                    Objects.equals(timestamp, dateTime.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, timestamp);
        }

        @Override
        public String toString() {
            return "MyCustomDateTime{" +
                    "timestamp=" + timestamp +
                    '}';
        }
    }

    public static List<JButton> sortJButtonsByText(List<JButton> buttons){
        List<Time> times = buttons.stream().map(b->Time.valueOf(b.getText())).collect(Collectors.toList());
        Collections.sort(times);
        buttons = times.stream().map(time->new JButton(String.valueOf(time))).collect(Collectors.toList());
        return buttons;
    }


}

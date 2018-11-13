package models.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class MyCustomDateAndTime {
    private final Date date;
    private final Time time;
    private final Timestamp timestamp;

    public MyCustomDateAndTime(Date date, Time time, Timestamp timestamp) {
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
    }

    public MyCustomDateAndTime() {
        date = new Date(System.currentTimeMillis());
        time = Time.valueOf("08:00:00");
        timestamp = Timestamp.valueOf(String.valueOf(date)+" "+String.valueOf(time));
    }

    public MyCustomDateAndTime(Date date) {
        this.date = date;
        time = Time.valueOf("08:00:00");
        timestamp = Timestamp.valueOf(String.valueOf(date) + " "+ String.valueOf(time));
    }

    public MyCustomDateAndTime(Date date, Time time) {
        this.date = date;
        this.time = time;
        timestamp = Timestamp.valueOf(String.valueOf(date) + " "+ String.valueOf(time));
    }


    public MyCustomDateAndTime add30MinutesToTime(){
        Timestamp t = new Timestamp(timestamp.getTime() + 1800000);
        Date date = Date.valueOf(String.valueOf(t).split(" ")[0]);
        Time time = Time.valueOf(String.valueOf(t).split(" ")[1]
                .substring(0,String.valueOf(t).split(" ")[1].length()-2 ));
        return new MyCustomDateAndTime(date, time, t);
    }

    public MyCustomDateAndTime withTime(Time time) {
        return new MyCustomDateAndTime(date, time);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCustomDateAndTime that = (MyCustomDateAndTime) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, timestamp);
    }

    @Override
    public String toString() {
        return "MyCustomDateAndTime{" +
                "date=" + date +
                ", time=" + time +
                ", timestamp=" + timestamp +
                '}';
    }

    public Time getTime() {
        return time;
    }



}

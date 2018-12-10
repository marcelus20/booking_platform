package models.utils;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * This is class is a model of a Data object, containing just relevant attributes and methods for this project.
 * The attributes are date, time and timestamp.
 *
 * This class turns out to be handy when creating the buttons in service Provider Controller for adding
 * available slots. The method makes use of this class to label the buttons with the correspondent times.
 *
 * The add30Minutes methods makes possible to create the buttons from 8 oclock until 20pm
 */
public class MyCustomDateAndTime {
    /**
     * LIST OF ATTRIBUTES
     */
    private final Date date;
    private final Time time;
    private final Timestamp timestamp;

    /**
     * FULL CONSTRUCTOR
     * @param date
     * @param time
     * @param timestamp
     */
    public MyCustomDateAndTime(Date date, Time time, Timestamp timestamp) {
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
    }

    /**
     * EMPTY CONSTRUCOTORS
     */
    public MyCustomDateAndTime() {
        date = new Date(System.currentTimeMillis());
        time = Time.valueOf("08:00:00");
        timestamp = Timestamp.valueOf(String.valueOf(date)+" "+String.valueOf(time));
    }

    /**
     * CONSTRUCOTR JUST WITH DATE. THE TIME ATTRIBUTE IS BY DEFAUL SET TO 8 AM
     * @param date
     */
    public MyCustomDateAndTime(Date date) {
        this.date = date;
        time = Time.valueOf("08:00:00");
        timestamp = Timestamp.valueOf(String.valueOf(date) + " "+ String.valueOf(time));
    }

    /**
     * CONSTRUCTOR WITH DATE AND TIME.
     * @param date
     * @param time
     */
    public MyCustomDateAndTime(Date date, Time time) {
        this.date = date;
        this.time = time;
        timestamp = Timestamp.valueOf(String.valueOf(date) + " "+ String.valueOf(time));
    }


    /**
     * Returns another instance of MyCustomDateAndTime with the time of this current class added 30 minutes
     * @return
     */
    public MyCustomDateAndTime add30MinutesToTime(){
        Timestamp t = new Timestamp(timestamp.getTime() + 1800000);// adding 30 minutes
        Date date = Date.valueOf(String.valueOf(t).split(" ")[0]);
        Time time = Time.valueOf(String.valueOf(t).split(" ")[1]
                .substring(0,String.valueOf(t).split(" ")[1].length()-2 ));
        return new MyCustomDateAndTime(date, time, t); // recalling constructor
    }

    /**
     * SETTERS AND GETTERS
     * @param time
     * @return
     */
    public MyCustomDateAndTime withTime(Time time) {
        return new MyCustomDateAndTime(date, time);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * EQUALS
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCustomDateAndTime that = (MyCustomDateAndTime) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(time, that.time) &&
                Objects.equals(timestamp, that.timestamp);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(date, time, timestamp);
    }

    /**
     * TOSTRING
     * @return
     */
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

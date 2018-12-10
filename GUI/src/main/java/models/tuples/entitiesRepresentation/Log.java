package models.tuples.entitiesRepresentation;

import java.util.Objects;

/**
 * Entity representation of the Log table in the database.
 */
public class Log {
    /**
     * LIST OF ATTRIBUTE - COLUMNS
     */
    private String userId;
    private String activityLog;

    /**
     * FULL CONSTRUCTOR
     * @param userId COLUMN
     * @param activityLog COLUMN
     */
    public Log(String userId, String activityLog) {
        this.userId = userId;
        this.activityLog = activityLog;
    }

    /**
     * GETTERS AND SETTERS
     * @return
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(String activityLog) {
        this.activityLog = activityLog;
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
        Log log = (Log) o;
        return Objects.equals(userId, log.userId) &&
                Objects.equals(activityLog, log.activityLog);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, activityLog);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "Log{" +
                "userId='" + userId + '\'' +
                ", activityLog='" + activityLog + '\'' +
                '}';
    }
}

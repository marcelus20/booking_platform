package models.tuples.entitiesRepresentation;

import java.util.Objects;

public class Log {
    private String userId;
    private String activityLog;

    public Log(String userId, String activityLog) {
        this.userId = userId;
        this.activityLog = activityLog;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(userId, log.userId) &&
                Objects.equals(activityLog, log.activityLog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityLog);
    }

    @Override
    public String toString() {
        return "Log{" +
                "userId='" + userId + '\'' +
                ", activityLog='" + activityLog + '\'' +
                '}';
    }
}

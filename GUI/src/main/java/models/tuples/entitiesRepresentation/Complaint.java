package models.tuples.entitiesRepresentation;

import models.enums.ComplaintStatus;
import java.util.Objects;

/**
 * Entity representation of the complaint table in database.
 */


public class Complaint {
    /**
     * LIST OF COLUMNS TABLE/ ATTRIBUTES
     */
    private String complaintID;
    private String serviceId;
    private String customerId;
    private ComplaintStatus complaintStatus; // ENUM ComplaintStatus
    private String complaint;

    /**
     * FULL CONSTRUCTOR
     * @param complaintID COLUMN
     * @param serviceId COLUMN
     * @param customerId COLUMN
     * @param complaintStatus COLUMN
     * @param complaint COLUMN
     */
    public Complaint(String complaintID, String serviceId, String customerId, ComplaintStatus complaintStatus, String complaint) {
        this.complaintID = complaintID;
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.complaintStatus = complaintStatus;
        this.complaint = complaint;
    }

    /**
     * EMPTY CONSTRUCTOR
     */
    public Complaint() {
    }

    /**
     * GETTERS AND SETTERS
     * @return
     */
    public String getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(String complaintID) {
        this.complaintID = complaintID;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
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
        Complaint complaint1 = (Complaint) o;
        return Objects.equals(complaintID, complaint1.complaintID) &&
                Objects.equals(serviceId, complaint1.serviceId) &&
                Objects.equals(customerId, complaint1.customerId) &&
                complaintStatus == complaint1.complaintStatus &&
                Objects.equals(complaint, complaint1.complaint);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(complaintID, serviceId, customerId, complaintStatus, complaint);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "Complaint{" +
                "complaintID='" + complaintID + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", complaintStatus=" + complaintStatus +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}

package models.tuples.entitiesRepresentation;

import models.enums.ComplaitStatus;

import java.util.Objects;

public class Complaint {
    private String complaintID;
    private String serviceId;
    private String customerId;
    private ComplaitStatus complaitStatus;
    private String complaint;

    public Complaint(String complaintID, String serviceId, String customerId, ComplaitStatus complaitStatus, String complaint) {
        this.complaintID = complaintID;
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.complaitStatus = complaitStatus;
        this.complaint = complaint;
    }

    public Complaint() {
    }

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

    public ComplaitStatus getComplaitStatus() {
        return complaitStatus;
    }

    public void setComplaitStatus(ComplaitStatus complaitStatus) {
        this.complaitStatus = complaitStatus;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint1 = (Complaint) o;
        return Objects.equals(complaintID, complaint1.complaintID) &&
                Objects.equals(serviceId, complaint1.serviceId) &&
                Objects.equals(customerId, complaint1.customerId) &&
                complaitStatus == complaint1.complaitStatus &&
                Objects.equals(complaint, complaint1.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(complaintID, serviceId, customerId, complaitStatus, complaint);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintID='" + complaintID + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", complaitStatus=" + complaitStatus +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}

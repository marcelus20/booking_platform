package models.tuples.entitiesRepresentation;

import java.util.Objects;

public class Complaint {
    private String serviceId;
    private String customerId;
    private String complaint;

    public Complaint(String serviceId, String customerId, String complaint) {
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.complaint = complaint;
    }

    public Complaint() {
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
        return Objects.equals(serviceId, complaint1.serviceId) &&
                Objects.equals(customerId, complaint1.customerId) &&
                Objects.equals(complaint, complaint1.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, customerId, complaint);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "serviceId='" + serviceId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}

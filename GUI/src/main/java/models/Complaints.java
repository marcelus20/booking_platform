package models;

import java.util.Objects;

public class Complaints {

    private Long customerId;
    private Long serviceId;
    private String complaint;

    public Complaints(Long customerId, Long serviceId, String complaint) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.complaint = complaint;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getComplaint() {
        return complaint;
    }

    public Complaints withCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Complaints withServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Complaints withComplaint(String complaint) {
        this.complaint = complaint;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaints that = (Complaints) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(complaint, that.complaint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, serviceId, complaint);
    }

    @Override
    public String toString() {
        return "Complaints{" +
                "customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", complaint='" + complaint + '\'' +
                '}';
    }
}

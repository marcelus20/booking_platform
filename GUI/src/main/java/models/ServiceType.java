package models;

import java.sql.Time;
import java.util.Objects;

public class ServiceType {

    private Long customerId;
    private Long serviceId;
    private String type;

    public ServiceType(Long customerId, Long serviceId, String type) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.type = type;
    }

    public ServiceType() {
    }


    public Long getCustomerId() {
        return customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public String getType() {
        return type;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceType that = (ServiceType) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, serviceId, type);
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", type='" + type + '\'' +
                '}';
    }
}

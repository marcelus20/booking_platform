package models.entities;

import models.ServiceType;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

public class Bookings {

    private Long customerId;
    private Long serviceId;
    private Time time;
    private List<ServiceType> services_type;

    public Bookings(Long customerId, Long serviceId, Time time, List<ServiceType> services_type) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.time = time;
        this.services_type = services_type;
    }

    public Bookings() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Time getTime() {
        return time;
    }

    public List<ServiceType> getServices_type() {
        return services_type;
    }

    public Bookings withCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Bookings withServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public Bookings withTime(Time time) {
        this.time = time;
        return this;
    }

    public Bookings withTime(List<ServiceType> services_type) {
        this.services_type = services_type;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookings bookings = (Bookings) o;
        return Objects.equals(customerId, bookings.customerId) &&
                Objects.equals(serviceId, bookings.serviceId) &&
                Objects.equals(time, bookings.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, serviceId, time);
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", time=" + time +
                '}';
    }
}

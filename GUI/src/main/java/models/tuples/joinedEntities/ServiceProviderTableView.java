/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package models.tuples.joinedEntities;

import java.util.Objects;

/**
 * This is a view representation of the entity service_provider for the JTable creation.
 */
public class ServiceProviderTableView {
    /**
     * list of columns
     */
    private String serviceId;
    private String serviceEmail;
    private String serviceName;
    private String address;
    private String city;
    private String phone;

    /**
     * empty constructor
     */
    public ServiceProviderTableView() {
    }

    /**
     * GETTERS AND SETTERS
     * @return
     */
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceEmail() {
        return serviceEmail;
    }

    public void setServiceEmail(String serviceEmail) {
        this.serviceEmail = serviceEmail;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        ServiceProviderTableView that = (ServiceProviderTableView) o;
        return Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(serviceEmail, that.serviceEmail) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(phone, that.phone);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(serviceId, serviceEmail, serviceName, address, city, phone);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "ServiceProviderTableView{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceEmail='" + serviceEmail + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

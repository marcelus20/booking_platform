package models.tuples.entitiesRepresentation;

import java.util.Objects;

public class Phone {
    private String userId;
    private String phone;

    public Phone(String userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    public Phone() {
    }

    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone1 = (Phone) o;
        return Objects.equals(userId, phone1.userId) &&
                Objects.equals(phone, phone1.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, phone);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

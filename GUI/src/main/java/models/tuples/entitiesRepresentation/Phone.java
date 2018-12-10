/**
 * FELIPE MANTOVANI ------ ID: 2017192 -------- GROUP A ---------PROFESSOR AMILCAR APONTE ------- AMILCAR APONTE
 */
package models.tuples.entitiesRepresentation;

import java.util.Objects;

/**
 * Entity representation of the phoNe_list table in database
 * RELATED ONE CUSTOMER TO MANY PHONES
 */
public class Phone {
    /**
     * list of columns/attributes
     */
    private String userId;
    private String phone;

    /**
     * full constructor
     * @param userId column
     * @param phone column
     */
    public Phone(String userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    /**
     * empty constructor
     */
    public Phone() {
    }

    /**
     * getters
     * @return
     */
    public String getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    // setters
    public void setUserId(String userId) {
        this.userId = userId;
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
        Phone phone1 = (Phone) o;
        return Objects.equals(userId, phone1.userId) &&
                Objects.equals(phone, phone1.phone);
    }

    /**
     * HASHCODE
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, phone);
    }

    /**
     * TOSTRING
     * @return
     */
    @Override
    public String toString() {
        return "Phone{" +
                "userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

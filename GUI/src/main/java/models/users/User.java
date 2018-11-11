package models.users;

import java.sql.Date;
import java.util.Objects;

public class User extends AbstraticUser{


    private String phone;
    private Date dateCreated;

    public User(String id, String name, String password, String phone, Date dateCreated) {
        super(id, name, password);
        this.phone = phone;
        this.dateCreated = dateCreated;
    }

    public User() {

    }



    public String getPhone() {
        return phone;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public User withPhone(String newPhone) {
        phone = newPhone;
        return this;
    }

    public User withDateCreated(Date newDate) {
        dateCreated = newDate;
        return this;
    }

    @Override
    public AbstraticUser withId(String newId) {
        id = newId;
        return this;
    }
    @Override
    public AbstraticUser withEmail(String newEmail) {
        email = newEmail;
        return this;
    }

    @Override
    public AbstraticUser withPassword(String newPassword) {
        password = newPassword;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return Objects.equals(phone, that.phone) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, dateCreated);
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}

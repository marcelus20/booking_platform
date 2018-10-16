package models.users;

import java.util.Date;
import java.util.Objects;

public class User extends AbstractUser {

    protected String phone;
    protected Date dateOfAccountCreation;


    public User(Long id, String eMail, String password, String phone, Date dateOfAccountCreation) {
        super(id, eMail, password);
        this.phone = phone;
        this.dateOfAccountCreation = dateOfAccountCreation;

    }

    public User() {
    }

    public String getPhone() {
        return phone;
    }

    public Date getDateOfAccountCreation() {
        return dateOfAccountCreation;
    }

    @Override
    public AbstractUser withId(Long id) {
        this.setId(id);
        return this;
    }

    @Override
    public AbstractUser withEmail(String email) {
        this.seteMail(email);
        return this;
    }

    @Override
    public AbstractUser withPassword(String password) {
        this.setPassword(password);
        return this;
    }

    public User withPhone(String password){
        this.password = password;
        return this;
    }

    public User withDateOfAccountCreation(Date dateOfAccountCreation){
        this.dateOfAccountCreation = dateOfAccountCreation;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(phone, user.phone) &&
                Objects.equals(dateOfAccountCreation, user.dateOfAccountCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phone, dateOfAccountCreation);
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", dateOfAccountCreation=" + dateOfAccountCreation +
                ", id=" + id +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

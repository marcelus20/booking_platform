package models.users;

import java.util.Objects;

public abstract class AbstraticUser {

    protected String id;
    protected String email;
    protected String password;

    public AbstraticUser(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public AbstraticUser() {
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public abstract AbstraticUser withId(String newId);
    public abstract AbstraticUser withEmail(String newEmail);
    public abstract AbstraticUser withPassword(String newPassword);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstraticUser that = (AbstraticUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "AbstraticUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

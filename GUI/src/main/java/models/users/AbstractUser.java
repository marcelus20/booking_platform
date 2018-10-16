package models.users;

import java.util.Objects;

public abstract class AbstractUser {

    protected Long id;
    protected String eMail;
    protected String password;

    public AbstractUser(Long id, String eMail, String password) {
        this.id = id;
        this.eMail = eMail;
        this.password = password;
    }

    public AbstractUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract AbstractUser withId(Long id);
    public abstract AbstractUser withEmail(String email);
    public abstract AbstractUser withPassword(String password);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUser that = (AbstractUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(eMail, that.eMail) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eMail, password);
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "id=" + id +
                ", eMail='" + eMail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

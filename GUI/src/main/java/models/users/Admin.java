package models.users;

import java.util.Objects;

public class Admin {

    private Long id;
    private String password;
    private String email;

    public Admin(Long id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public Admin() {
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Admin withId(Long id) {
        this.id = id;
        return this;
    }

    public Admin withPassword(String password) {
        this.password = password;
        return this;
    }

    public Admin withEmail(String email) {
        this.email = email;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(email, admin.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", eMail='" + email + '\'' +
                '}';
    }
}

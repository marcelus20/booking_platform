package models;

import java.util.Objects;

public class Location {

    private Long id;
    private String first_line_address;
    private String eir_code;
    private String city;
    private String second_line_address;

    public Location(long id, String first_line_address, String eir_code, String city, String secondLineAddress) {
        this.id = id;
        this.first_line_address = first_line_address;
        this.eir_code = eir_code;
        this.city = city;
        this.second_line_address = secondLineAddress;
    }

    public Location() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                Objects.equals(first_line_address, location.first_line_address) &&
                Objects.equals(eir_code, location.eir_code) &&
                Objects.equals(city, location.city) &&
                Objects.equals(second_line_address, location.second_line_address);
    }

    public long getId() {
        return id;
    }

    public String getFirst_line_address() {
        return first_line_address;
    }

    public String getEir_code() {
        return eir_code;
    }

    public String getCity() {
        return city;
    }

    public String getSecond_line_address() {
        return second_line_address;
    }

    public Location withId(Long id){
        this.id = id;
        return this;
    }

    public Location withFirstLineAddress(String first_line_address){
        this.first_line_address = first_line_address;
        return this;
    }

    public Location withEirCode(String eir_code){
        this.eir_code = eir_code;
        return this;
    }

    public Location withCity(String city){
        this.city = city;
        return this;
    }

    public Location withSecondLineAddress(String second_line_address){
        this.second_line_address = second_line_address;
        return this;
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, first_line_address, eir_code, city, second_line_address);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", first_line_address='" + first_line_address + '\'' +
                ", eir_code='" + eir_code + '\'' +
                ", city='" + city + '\'' +
                ", second_line_address='" + second_line_address + '\'' +
                '}';
    }
}

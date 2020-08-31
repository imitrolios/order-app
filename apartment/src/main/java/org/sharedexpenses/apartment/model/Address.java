package org.sharedexpenses.apartment.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue()
    private Long id;
    @Column(name = "street", length = 30, nullable = false)
    private String street;
    @Column(name = "number", length = 30, nullable = false)
    private String number;
    @Column(name = "country", length = 30, nullable = false)
    private String country;
    @Column(name = "municipality", length = 30, nullable = false)
    private String municipality;
    @Column(name = "city", length = 30, nullable = false)
    private String city;
    @Column(name = "postal_code", length = 30, nullable = false)
    private String postalCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

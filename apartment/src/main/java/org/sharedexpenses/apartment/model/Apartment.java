package org.sharedexpenses.apartment.model;

import javax.persistence.*;

@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue()
    private Long id;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Address address;
    @Column(name = "floor_number", length = 30, nullable = false)
    private String floorNumber;
    @Column(name = "block_of_flats_id", length = 30, nullable = false)
    private String blockOfFlatsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getBlockOfFlatsId() {
        return blockOfFlatsId;
    }

    public void setBlockOfFlatsId(String blockOfFlatsId) {
        this.blockOfFlatsId = blockOfFlatsId;
    }
}

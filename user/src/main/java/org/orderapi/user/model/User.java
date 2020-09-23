package org.orderapi.user.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue()
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

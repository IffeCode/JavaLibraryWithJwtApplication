package org.example.javalibrarywithjwt.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname cannot be empty!")
    @Size(max = 50, message = "Firstname cannot exceed 50 characters!")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Lastname cannot be empty!")
    @Size(max = 50, message = "Lastname cannot exceed 50 characters!")
    @Column(nullable = false)
    private String lastName;

    public Author() {}

    public Author(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

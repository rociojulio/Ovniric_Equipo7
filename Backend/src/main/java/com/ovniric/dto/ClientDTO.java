package com.ovniric.dto;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;

    private String RoleName;

    public ClientDTO() {}

    public ClientDTO(Long id, String firstName, String lastName, String email, String city, String roleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        RoleName = roleName;
    }

    public ClientDTO(String firstName, String lastName, String email, String city, String roleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        RoleName = roleName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
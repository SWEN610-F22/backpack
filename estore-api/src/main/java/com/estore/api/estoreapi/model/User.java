package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a user
 */
public class User {
    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("isAdmin") private boolean isAdmin;
    @JsonProperty("firstName") private String firstName;
    @JsonProperty("lastName") private String lastName;

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("isAdmin") boolean isAdmin, @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username){
        this.id = id;
        this.username = username;
        this.isAdmin = false;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", isAdmin=" + isAdmin + ", firstName=" + firstName
                + ", lastName=" + lastName + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }
   
}

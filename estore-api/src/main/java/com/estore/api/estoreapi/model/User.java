package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a user
 */
public class User {
    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("admin") private boolean admin;

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("admin") boolean admin) {
        this.id = id;
        this.username = username;
        this.admin = admin;
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

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User [id=" + id +", username=" + username+ ", admin=" + admin +"]" ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id){
            return false;
        } else if (!username.equals(other.username))
            return false;
        if (admin != other.admin)
            return false;
        return true;
    }
   
}

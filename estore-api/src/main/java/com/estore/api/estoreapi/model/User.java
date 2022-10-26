package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a user
 */
public class User {
    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("isAdmin") private boolean isAdmin;

    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("isAdmin") boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
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

    @Override
    public String toString() {
       return "User [id=" + id +", username=" + username+ ", admin=" + isAdmin+"]";
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
        if (isAdmin != other.isAdmin)
            return false;
        return true;
    }
   
}

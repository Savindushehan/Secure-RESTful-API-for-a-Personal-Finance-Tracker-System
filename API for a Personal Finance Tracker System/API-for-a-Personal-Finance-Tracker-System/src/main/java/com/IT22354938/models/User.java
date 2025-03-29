package com.IT22354938.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.util.Date;

@Data // Generates Getters, Setters, toString(), equals(), and hashCode()
@NoArgsConstructor // Generates a no-argument constructor
@Document(collection = "users") // For MongoDB
public class User {

    @Id
    private String id;  // String for MongoDB; use Long for SQL databases


    private String name;
    private String nic;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private Date birthDay;
    private String userName;
    private String password;
    private String role;


    public User(String id, String name, String nic, Date birthDay, String userName, String password, String role) {
        this.id = id;  // This will be set from the request
        this.name = name;
        this.nic = nic;
        this.birthDay = birthDay;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

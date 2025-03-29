package com.IT22354938.dto;

import java.util.Date;

public record UserRequest(String id, String name, String nic, Date birthDay, String userName, String password, String role) {
}

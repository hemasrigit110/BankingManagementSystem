// package com.example.auth.dto;

// import jakarta.validation.constraints.NotBlank;
// import lombok.Data;

// @Data
// public class LoginRequest {

//     @NotBlank
//     private String username;

//     @NotBlank
//     private String password;

//     // No need for constructor or manual getters/setters
// }
package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

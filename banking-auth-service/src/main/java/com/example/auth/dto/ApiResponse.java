// package com.example.auth.dto;

// import lombok.AllArgsConstructor;
// import lombok.Data;

// @Data
// @AllArgsConstructor
// public class ApiResponse {
// //     private Boolean success;
// //     private String message;
// }
package com.example.auth.dto;

public class ApiResponse {
    private boolean success;
    private String message;

    // ✅ Add this constructor
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // No-arg constructor
    public ApiResponse() {
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}



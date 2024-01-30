package com.example.retrofittutorial.ModelResponse;

public class LoginResponse {

    String error , message;
    User user;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(String error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }
}

package com.example.retrofittutorial.ModelResponse;

public class RegisterResponse {

    String error , message ;

    public RegisterResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}

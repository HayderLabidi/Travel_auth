package com.example.travel_auth.checkuser;

public class CheckUserResponse {
    private boolean usernameExists;
    private boolean emailExists;

    // Getters and setters
    public boolean isUsernameExists() {
        return usernameExists;
    }

    public void setUsernameExists(boolean usernameExists) {
        this.usernameExists = usernameExists;
    }

    public boolean isEmailExists() {
        return emailExists;
    }

    public void setEmailExists(boolean emailExists) {
        this.emailExists = emailExists;
    }
}

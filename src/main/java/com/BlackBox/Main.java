package com.BlackBox;

import com.BlackBox.Password.PasswordUtils;

public class Main {
    private static final int MAX_ATTEMPTS = 3;
    private int failedAttempts = 0;
    private String storedPasswordHash; // Store the hashed password here

    // Method to create a new password for the user
    public void createPassword(String newPassword) {
        // Generate a salt and hash the password
//        this.storedPasswordHash = PasswordUtils.hashPasswordWithSalt(newPassword);
    }

    // Method to validate the user's password
    public boolean validatePassword(String enteredPassword) {
        if (PasswordUtils.verifyPassword(enteredPassword, storedPasswordHash)) {
            failedAttempts = 0; // Reset failed attempts if the password is correct
            return true;
        } else {
            failedAttempts++;
            return false;
        }
    }

    // Method to check if the user is temporarily locked out
    public boolean isLockedOut() {
        return failedAttempts >= MAX_ATTEMPTS;
    }

    // Method to reset the failed attempts counter (e.g., after a successful login)
    public void resetFailedAttempts() {
        failedAttempts = 0;
    }

    // Other methods for password reset, email verification, etc. can be added here
}

package com.BlackBox.UserManager;

import com.BlackBox.Components.PathResolver;
import com.BlackBox.CredentialsFileReader.CredentialsFileReader;
import com.BlackBox.CredentialsFileWriter.CredentialsFileWriter;

import javax.swing.*;

public class UserManager {

    // Method to add a new user with username and password to the txt file
    public void addUser(String username, String hashedPassword, String hashedQuestion1, String hashedAnswer1, String hashedQuestion2, String hashedAnswer2) {
        // Check if the hashed username already exists in the credentials file
        if (CredentialsFileReader.anyAccountsExist(PathResolver.resolveAbsolutePath("credentials.txt"))) {
            // If the username exists, display an error message and return
            JOptionPane.showMessageDialog(null, "An account already exists. Please login.", "Account Creation Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create an instance of CredentialsFileWriter and write credentials to file
        CredentialsFileWriter credentialsFileWriter = new CredentialsFileWriter();
        credentialsFileWriter.writeCredentialsToFile(username, hashedPassword, hashedQuestion1, hashedAnswer1, hashedQuestion2, hashedAnswer2, PathResolver.resolveAbsolutePath("credentials.txt"));
    }

}

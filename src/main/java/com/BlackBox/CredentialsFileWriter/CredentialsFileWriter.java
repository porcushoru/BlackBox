package com.BlackBox.CredentialsFileWriter;

import com.BlackBox.UserManager.UserManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// CredentialsFileWriter class
public class CredentialsFileWriter {
    private UserManager userManager;

    public CredentialsFileWriter() {
        // Constructor without any parameters
    }

    // Method to write credentials to file
    public void writeCredentialsToFile(String username, String hashedPassword, String hashedQuestion1, String hashedAnswer1, String hashedQuestion2, String hashedAnswer2, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { // Append mode to add new entries without overwriting existing ones
            writer.write(username);
            writer.newLine();
            writer.write(hashedPassword);
            writer.newLine();
            writer.write(hashedQuestion1);
            writer.newLine();
            writer.write(hashedAnswer1);
            writer.newLine();
            writer.write(hashedQuestion2);
            writer.newLine();
            writer.write(hashedAnswer2);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing errors
        }
    }

    public void resetCredentialsToFile(String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing errors
        }
    }
}

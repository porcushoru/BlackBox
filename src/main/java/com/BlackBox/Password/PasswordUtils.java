package com.BlackBox.Password;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {


    // Method to hash a password using a salt
    public static String hashPasswordWithSalt(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(salt) + "$" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to verify a credential against its hashed version
    public static boolean verifyPassword(String enteredPassword, String storedHashedLine) {
        String[] parts = storedHashedLine.split("\\$");   // Split hashed line into 2 parts
        byte[] salt = Base64.getDecoder().decode(parts[0]);   // Decode the first part => decoded salt
        String hashedEnteredPassword = hashPasswordWithSalt(enteredPassword, salt);
        System.out.println(Arrays.toString(salt));
        return hashedEnteredPassword.equals(storedHashedLine);
    }

    // Method to generate a random salt
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    // Method to read one line from the stored credentials
    // in the txt file. returns a raw hashed line
    public static String readStoredCredentials(String filePath, int lineNumber) {
        StringBuilder storedCredentials = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = br.readLine()) != null) {
                currentLine++;
                if (currentLine == lineNumber) {
                    storedCredentials.append(line);
                    break; // Stop reading after finding the desired line
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storedCredentials.toString();
    }

}

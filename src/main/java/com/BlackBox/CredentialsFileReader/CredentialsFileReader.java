package com.BlackBox.CredentialsFileReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CredentialsFileReader {

    // Method to check if the credentials file exists
    public static boolean credentialsFileExists() {
        String currentDir = System.getProperty("user.dir");
        String relativeFilePath = currentDir + File.separator + "src" + File.separator + "com" + File.separator + "vaultapp" + File.separator + "Credentials" + File.separator + "credentials.txt";
        File file = new File(relativeFilePath);
        System.out.println("Checking if file exists at path: " + relativeFilePath); // Debug statement
        boolean fileExists = file.exists() && !file.isDirectory();
        if (fileExists) {
            System.out.println("Credentials file found at path: " + relativeFilePath); // Debug statement
        } else {
            System.out.println("Credentials file not found at path: " + relativeFilePath); // Debug statement
        }
        return fileExists;
    }



    // Method to check if the credentials file contains any accounts
    public static boolean anyAccountsExist(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Check if the file contains any text
            return br.readLine() != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to verify if entered credentials match stored credentials
    public static boolean verifyCredentials(String enteredCredentials, List<String> decryptedCredentials) {
        for (String storedCredential : decryptedCredentials) {
            if (enteredCredentials.equals(storedCredential)) {
                return true;
            }
        }
        return false;
    }
}

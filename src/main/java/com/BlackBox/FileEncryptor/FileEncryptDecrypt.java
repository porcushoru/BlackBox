package com.BlackBox.FileEncryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileEncryptDecrypt {


    // Method to generate a random salt of length 16 bytes
    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    // Method to derive an encryption/decryption key from the passphrase and salt
    public static byte[] deriveKey(String passphrase, byte[] salt) {
        try {
            // Use PBKDF2 with SHA-256 for key derivation
            String algorithm = "PBKDF2WithHmacSHA256";
            int keyLength = 256; // Length of the derived key in bits

            // Use PBKDF2 to derive the key from the passphrase and salt
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            KeySpec keySpec = new PBEKeySpec(passphrase.toCharArray(), salt, 65536, keyLength);
            return secretKeyFactory.generateSecret(keySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            // Handle any errors or exceptions
            ex.printStackTrace();
            return null;
        }
    }

    // Method to encrypt data using the provided key
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            // Create a AES key from the provided key bytes
            Key aesKey = new SecretKeySpec(key, "AES");

            // Initialize the cipher for encryption
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);

            // Perform encryption
            return cipher.doFinal(data);
        } catch (Exception ex) {
            // Handle any errors or exceptions
            ex.printStackTrace();
            return null;
        }
    }

    // Method to write salt and encrypted data to the output file
    public static void writeToFileWithSalt(String outputFile, byte[] salt, byte[] encryptedData) {
        try {
            // Create the output directory if it doesn't exist
            File outputDirectory = new File(outputFile).getParentFile();
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                // Write salt and encrypted data to the output file
                oos.writeObject(salt);
                oos.writeObject(encryptedData);

                System.out.println("Salt and encrypted data written to file: " + outputFile);
                }
            } catch (IOException e) {
                // Handle any IO exceptions
                e.printStackTrace();
            }
        }

    // Method to decrypt data using the provided key
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // Create a AES key from the provided key bytes
            Key aesKey = new SecretKeySpec(key, "AES");

            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);

            // Perform decryption
            return cipher.doFinal(data);
        } catch (Exception ex) {
            // Handle any errors or exceptions
            ex.printStackTrace();
            return null;
        }
    }

    // Method to read salt from the input file
    public static byte[] readSaltFromFile(String inputFile) {
        try (FileInputStream fis = new FileInputStream(inputFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Read salt from the input file
            byte[] salt = (byte[]) ois.readObject();
            System.out.println("Salt read from file: " + inputFile);
            return salt;

        } catch (IOException | ClassNotFoundException e) {
            // Handle any IO or Class Not Found exceptions
            e.printStackTrace();
            return null;
        }
    }

    // Method to read encrypted data from the input file
    public static byte[] readEncryptedDataFromFile(String inputFile) {
        try (FileInputStream fis = new FileInputStream(inputFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Skip the salt by reading it first
            ois.readObject();

            // Read encrypted data from the input file
            byte[] encryptedData = (byte[]) ois.readObject();
            System.out.println("Encrypted data read from file: " + inputFile);
            return encryptedData;

        } catch (IOException | ClassNotFoundException e) {
            // Handle any IO or Class Not Found exceptions
            e.printStackTrace();
            return null;
        }
    }

    // Method to write decrypted data to the output file
    public static void writeDecryptedDataToFile(String outputFile, byte[] decryptedData) {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {

            // Write decrypted data to the output file
            fos.write(decryptedData);
            System.out.println("Decrypted data written to file: " + outputFile);

        } catch (IOException e) {
            // Handle any IO exceptions
            e.printStackTrace();
        }
    }



    public static String generateOutputFileName(String originalFileName, boolean isEncrypted) {
        // Remove any directory structure from the original file name
        String fileNameWithoutDirectories = new File(originalFileName).getName();

        // Extract the original filename without any additional information
        int lastUnderscoreIndex = fileNameWithoutDirectories.lastIndexOf('_');
        if (lastUnderscoreIndex != -1) {
            fileNameWithoutDirectories = fileNameWithoutDirectories.substring(lastUnderscoreIndex + 1);
        }

        // Determine the prefix based on whether the file is encrypted or decrypted
        String prefix = isEncrypted ? "encrypted_" : "decrypted_";

        // If the file is encrypted, preserve the original file extension
        if (isEncrypted) {
            return prefix + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + fileNameWithoutDirectories + ".enc";
        } else {
            // Check if the file has the ".enc" extension
            if (fileNameWithoutDirectories.endsWith(".enc")) {
                // Remove the ".enc" extension for decrypted files
                fileNameWithoutDirectories = fileNameWithoutDirectories.substring(0, fileNameWithoutDirectories.length() - 4);
            }
            // Retrieve the original file extension
            String fileExtension = "";
            int extensionIndex = fileNameWithoutDirectories.lastIndexOf('.');
            if (extensionIndex != -1) {
                fileExtension = fileNameWithoutDirectories.substring(extensionIndex);
                fileNameWithoutDirectories = fileNameWithoutDirectories.substring(0, extensionIndex);
            }

            // Construct the output file name with the original file extension
            return prefix + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + fileNameWithoutDirectories + fileExtension;
        }
    }
}

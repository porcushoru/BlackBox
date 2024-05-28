package com.BlackBox.Components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResolver {

    private static final String DIRECTORY = "data";
    private static final String FILE_PATH = DIRECTORY + File.separator + "credentials.txt";


    public static String resolveAbsolutePath(String relativeFilePath) {
        // Get the base directory dynamically
        String baseDir = System.getProperty("user.dir");

        // Construct the absolute path
        Path absolutePath = Paths.get(baseDir, relativeFilePath);

        return absolutePath.toString();
    }

    public static void main(String[] args) {
        // Ensure the directory exists
        createDirectoryIfNotExists(DIRECTORY);

        // Create or write to the credentials.txt file
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write("username: yourUsername\npassword: yourPassword");
            writer.close();
            System.out.println("Credentials saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}

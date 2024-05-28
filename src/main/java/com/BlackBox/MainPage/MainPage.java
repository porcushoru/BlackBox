package com.BlackBox.MainPage;

import com.BlackBox.Components.WhiteListWindow;
import com.BlackBox.FileEncryptor.FileEncryptDecrypt;
import com.BlackBox.Sounds.SoundPlayer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPage extends JPanel {
    private JPanel mainPanel;
    private List<JLabel> fileIcons;
    private JPanel radioPanel;
    private JRadioButton viewFilesRadioButton;
    private JRadioButton viewEncryptedFilesRadioButton;
    private JButton addFilesButton1;
    private JButton addFilesButton2;
    private JButton deleteFromListButton1;
    private JButton deleteFromListButton2;
    private JButton encryptFilesButton;
    private JButton decryptFilesButton;
    private JButton exitButton;
    private JButton credits;
    private JButton soundButton;
    private JLabel noSoundLabel;
    private JLabel filesLabel;
    private CardLayout cardLayout;
    private JPanel encryptedFilesPanel;
    private WhiteListWindow whiteListWindow;


    public MainPage() {
        setLayout(new BorderLayout());

        // Create main panel
        mainPanel = new JPanel(new GridLayout(0, 4)); // 4 columns, variable rows
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        whiteListWindow = new WhiteListWindow();
        // Add the whiteListWindow1 to the mainPanel
        mainPanel.add(whiteListWindow, BorderLayout.NORTH); // or any other region you prefer

        // Initialize list to store file icons
        fileIcons = new ArrayList<>();

        // Populate the main panel with file icons (placeholders for now)
        for (int i = 0; i < 20; i++) {
            JLabel fileIcon = new JLabel("File " + i);
            fileIcon.setHorizontalAlignment(SwingConstants.CENTER);
            fileIcon.setVerticalAlignment(SwingConstants.CENTER);
            mainPanel.add(fileIcon);
            fileIcons.add(fileIcon);
        }

        // Create a new instance of WhiteListWindow
        WhiteListWindow whiteListWindow1 = new WhiteListWindow();
        // Set size and position
        whiteListWindow1.setBounds(160, 42, 625, 510);
        // Add the WhiteListWindow to the main frame
        add(whiteListWindow1);

        WhiteListWindow whiteListWindow2 = new WhiteListWindow();
        whiteListWindow2.setBounds(160, 42, 625, 510);
        add(whiteListWindow2);

        // Create a JPanel for the radio buttons
        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());

        // Create the radio buttons for "View Files" and "View Encrypted Files"
        viewFilesRadioButton = new JRadioButton("View Files");
        viewEncryptedFilesRadioButton = new JRadioButton("View Encrypted Files");

        // Set the initial selection for the radio buttons
        viewFilesRadioButton.setSelected(true); // This will select "View Files" by default

        // Set the background color for the radio buttons
        viewFilesRadioButton.setBackground(Color.LIGHT_GRAY);
        viewEncryptedFilesRadioButton.setBackground(Color.BLACK);
        viewEncryptedFilesRadioButton.setForeground(Color.LIGHT_GRAY);

        // Add the radio buttons to a ButtonGroup
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(viewFilesRadioButton);
        buttonGroup.add(viewEncryptedFilesRadioButton);

        // Set the width of the radio buttons to match the width of the workspace (800 pixels)
        Dimension buttonDimension = new Dimension(200, viewFilesRadioButton.getPreferredSize().height);
        viewFilesRadioButton.setPreferredSize(buttonDimension);
        viewEncryptedFilesRadioButton.setPreferredSize(buttonDimension);

        // Add action listeners to the radio buttons (replace ActionListener implementation with your desired actions)
        viewFilesRadioButton.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            addFilesButton1.setVisible(true);
            addFilesButton2.setVisible(false);
            encryptFilesButton.setVisible(true);
            encryptFilesButton.setVisible(true);
            if (viewFilesRadioButton.isSelected()) {
                // Show the WhiteListWindow (wLW)
                whiteListWindow1.setVisible(true);
                whiteListWindow2.setVisible(false);
            } else {
                // Hide the WhiteListWindow (wLW)
                whiteListWindow1.setVisible(false);
                whiteListWindow2.setVisible(true);
            }

            if (viewFilesRadioButton.isSelected()) {
                deleteFromListButton2.setVisible(false);
                deleteFromListButton1.setVisible(true);
            }
            System.out.println("View Files selected!");
        });

        viewEncryptedFilesRadioButton.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            if (viewEncryptedFilesRadioButton.isSelected()) {
                addFilesButton1.setVisible(false);
                addFilesButton2.setVisible(true);
                encryptFilesButton.setVisible(false);
                decryptFilesButton.setVisible(true);
                whiteListWindow1.setVisible(false);
                whiteListWindow2.setVisible(true);
            } else {
                whiteListWindow1.setVisible(true);
                whiteListWindow2.setVisible(false);
            }

            if (viewEncryptedFilesRadioButton.isSelected()) {
                deleteFromListButton1.setVisible(false);
                deleteFromListButton2.setVisible(true);
            }
            System.out.println("View Encrypted Files selected!");
        });

        // Add the radio buttons to the radio panel
        radioPanel.add(viewFilesRadioButton);
        radioPanel.add(viewEncryptedFilesRadioButton);

        // Add the radio panel to the top of the MainPage panel
        add(radioPanel, BorderLayout.NORTH);


        // Add Files, Delete Files, Encrypt Files, Exit Buttons
        addFilesButton1 = new JButton("Add Files");
        addFilesButton1.setBackground(Color.GRAY);
        addFilesButton1.setBounds(10, 80, 150, 60); // Set position and size

        addFilesButton1.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            // Create a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true); // Allow multiple file selection
            int result = fileChooser.showOpenDialog(this);

            // Check if files are selected
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected files
                File[] selectedFiles = fileChooser.getSelectedFiles();

                // Process each selected file
                for (File file : selectedFiles) {
                    // Create an icon for the file
                    Icon fileIcon = getFileIcon(file);

                    // Create a custom JLabel to display the file name and icon
                    JLabel fileLabel = new JLabel(file.getName(), fileIcon, JLabel.CENTER);
                    fileLabel.setVerticalTextPosition(JLabel.BOTTOM);
                    fileLabel.setHorizontalTextPosition(JLabel.CENTER);

                    // Add the file to the whiteListWindow1
                    whiteListWindow1.addFileLabel(fileLabel, file);
                }
                // Refresh the whiteListWindow1 to reflect the changes
                whiteListWindow1.revalidate();
                whiteListWindow1.repaint();
            }
        });
        add(addFilesButton1);

        // Add Files, Delete Files, Encrypt Files, Exit Buttons
        addFilesButton2 = new JButton("Add Files");
        addFilesButton2.setBackground(Color.BLACK);
        addFilesButton2.setForeground(Color.LIGHT_GRAY);
        addFilesButton2.setBounds(10, 80, 150, 60); // Set position and size

        addFilesButton2.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            // Create a file chooser dialog
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Encrypted Files (*.enc)", "enc");
            fileChooser.setFileFilter(filter);
            fileChooser.setMultiSelectionEnabled(true); // Allow multiple file selection
            int result = fileChooser.showOpenDialog(this);

            // Check if files are selected
            if (result == JFileChooser.APPROVE_OPTION) {
                // Get the selected files
                File[] selectedFiles = fileChooser.getSelectedFiles();

                // Process each selected file
                for (File file : selectedFiles) {
                    // Create an icon for the file
                    Icon fileIcon = getFileIcon(file);

                    // Create a custom JLabel to display the file name and icon
                    JLabel fileLabel = new JLabel(file.getName(), fileIcon, JLabel.CENTER);
                    fileLabel.setVerticalTextPosition(JLabel.BOTTOM);
                    fileLabel.setHorizontalTextPosition(JLabel.CENTER);

                    // Add the file to the whiteListWindow1
                    whiteListWindow2.addFileLabel(fileLabel, file);
                }
                // Refresh the whiteListWindow1 to reflect the changes
                whiteListWindow2.revalidate();
                whiteListWindow2.repaint();
            }
        });
        add(addFilesButton2);

        deleteFromListButton1 = new JButton("<html>Delete Files<br>&nbsp;&nbsp;From List</html>");
        deleteFromListButton1.setBackground(Color.GRAY);
        deleteFromListButton1.setBounds(10, 140, 150, 60); // Set position and size
        deleteFromListButton1.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            // Get the indices of the selected files
            int[] selectedIndices = whiteListWindow1.fileList.getSelectedIndices();

            // Get the list model
            DefaultListModel<File> listModel = (DefaultListModel<File>) whiteListWindow1.fileList.getModel();

            // Remove the selected files from the list model
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.remove(selectedIndices[i]);
            }
        });

        add(deleteFromListButton1);

        deleteFromListButton2 = new JButton("<html>Delete Files<br>&nbsp;&nbsp;From List</html>");
        deleteFromListButton2.setForeground(Color.LIGHT_GRAY);
        deleteFromListButton2.setBackground(Color.BLACK);
        deleteFromListButton2.setBounds(10, 140, 150, 60); // Set position and size

        deleteFromListButton2.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            // Get the indices of the selected files
            int[] selectedIndices = whiteListWindow2.fileList.getSelectedIndices();

            // Get the list model
            DefaultListModel<File> listModel = (DefaultListModel<File>) whiteListWindow2.fileList.getModel();

            // Remove the selected files from the list model
            for (int i = selectedIndices.length - 1; i >= 0; i--) {
                listModel.remove(selectedIndices[i]);
            }
        });

        add(deleteFromListButton2);

        encryptFilesButton = new JButton("Encrypt Files");
        encryptFilesButton.setBackground(Color.GRAY);
        encryptFilesButton.setBounds(10, 200, 150, 60); // Set position and size

        encryptFilesButton.addActionListener(e -> {
            SoundPlayer.playSoundFile("lightning1_1.wav");

            // Check if no files are selected
            if (whiteListWindow1.fileList.getModel().getSize() == 0) {
                JOptionPane.showMessageDialog(this, "No files selected for encryption!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Prompt the user to input a passphrase
            String passphrase = JOptionPane.showInputDialog(this, "Enter your passphrase:");

            // Check if the passphrase is not empty
            if (!passphrase.isEmpty()) {
                // Prompt the user to select an output directory
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Output Directory");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userSelection = fileChooser.showSaveDialog(this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected output directory
                    File outputDirectory = fileChooser.getSelectedFile();
                    System.out.println("Selected output directory: " + outputDirectory.getAbsolutePath());

                    // Create the -ENCRYPTED- folder if it doesn't exist
                    Path encryptedFolderPath = Paths.get(outputDirectory.getAbsolutePath(), "-ENCRYPTED-");
                    System.out.println("Expected -ENCRYPTED- folder path: " + encryptedFolderPath); // Debug statement
                    if (!Files.exists(encryptedFolderPath)) {
                        try {
                            // Attempt to create the directory
                            Files.createDirectory(encryptedFolderPath);
                            System.out.println("Successfully created the -ENCRYPTED- folder."); // Debug statement
                        } catch (IOException a) {
                            // Handle the error if directory creation fails
                            System.err.println("Failed to create the -ENCRYPTED- folder.");
                            System.err.println("Error message: " + a.getMessage());
                            // Handle the error (e.g., display an error message to the user)
                            return;
                        }
                    }

                    // Get the selected files from the "View Files" window (whiteListWindow1)
                    int[] selectedFilesIndices = whiteListWindow1.fileList.getSelectedIndices();
                    List<File> selectedFiles = new ArrayList<>();
                    for (int index : selectedFilesIndices) {
                        String filePath = whiteListWindow1.fileList.getModel().getElementAt(index).toString();
                        System.out.println("Selected file path: " + filePath); // Debug statement
                        selectedFiles.add(new File(filePath));
                    }

                    try {
                        byte[] salt = FileEncryptDecrypt.generateSalt();
                        // Derive the encryption key from the passphrase and salt using PBKDF2
                        byte[] key = FileEncryptDecrypt.deriveKey(passphrase, salt);
                        System.out.println("Encryption key: " + Arrays.toString(key));
                        // Iterate over each selected file and encrypt them individually
                        for (File inputFile : selectedFiles) {
                            System.out.println("Encrypting file: " + inputFile.getAbsolutePath()); // Debug statement
                            byte[] fileData = Files.readAllBytes(inputFile.toPath());
                            // Generate the output file name
                            String outputFileName = FileEncryptDecrypt.generateOutputFileName(inputFile.getName(), true);
                            String outputFileAbsolutePath = encryptedFolderPath.toAbsolutePath() + File.separator + outputFileName;
                            System.out.println("Output file path: " + outputFileAbsolutePath); // Debug statement
                            // Call the encryptFile method with the output file path
                            byte[] keyEncrypt = FileEncryptDecrypt.encrypt(fileData, key);
                            FileEncryptDecrypt.writeToFileWithSalt(outputFileAbsolutePath, salt, keyEncrypt);
                        }

                        // Notify the user that files have been encrypted successfully
                        JOptionPane.showMessageDialog(this, "Files encrypted successfully!", "Encryption Complete", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        // Handle any errors or exceptions
                        ex.printStackTrace(); // Print the stack trace
                        JOptionPane.showMessageDialog(this, "Error encrypting files: " + ex.getMessage(), "Encryption Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } else {
                // Show an error message if the passphrase is empty
                JOptionPane.showMessageDialog(this, "Passphrase cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(encryptFilesButton);

        decryptFilesButton = new JButton("Decrypt files");
        decryptFilesButton.setBackground(Color.BLACK);
        decryptFilesButton.setForeground(Color.LIGHT_GRAY);
        decryptFilesButton.setBounds(10, 200, 150, 60); // Set position and size

        decryptFilesButton.addActionListener(e -> {
            SoundPlayer.playSoundFile("lightning1_1.wav");
            // Check if no files are selected
            if (whiteListWindow2.fileList.getModel().getSize() == 0) {
                JOptionPane.showMessageDialog(this, "No files selected for decryption!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Prompt the user to enter the passphrase
            String passphrase = JOptionPane.showInputDialog(this, "Enter the passphrase:");

            // Check if the passphrase is not empty
            if (!passphrase.isEmpty()) {
                // Prompt the user to select an output directory
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Output Directory");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int userSelection = fileChooser.showSaveDialog(this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    // Get the selected output directory
                    File outputDirectory = fileChooser.getSelectedFile();
                    System.out.println("Selected output directory: " + outputDirectory.getAbsolutePath());

                    // Create the [DECRYPTED] folder if it doesn't exist
                    Path decryptedFolderPath = Paths.get(outputDirectory.getAbsolutePath(), "[DECRYPTED]");
                    System.out.println("Expected [DECRYPTED] folder path: " + decryptedFolderPath); // Debug statement
                    if (!Files.exists(decryptedFolderPath)) {
                        try {
                            // Attempt to create the directory if it doesn't exist
                            Files.createDirectory(decryptedFolderPath);
                            System.out.println("Successfully created the [DECRYPTED] folder.");
                        } catch (IOException a) {
                            // Handle the error if directory creation fails
                            System.err.println("Failed to create the [DECRYPTED] folder.");
                            System.err.println("Error message: " + a.getMessage());
                            // Handle the error (e.g., display an error message to the user)
                            return;
                        }
                    }


                    // Get the selected files from the "View Files" window (whiteListWindow1)
                    int[] selectedFilesIndices = whiteListWindow2.fileList.getSelectedIndices();
                    List<File> selectedFiles = new ArrayList<>();
                    for (int index : selectedFilesIndices) {
                        String filePath = whiteListWindow2.fileList.getModel().getElementAt(index).toString();
                        System.out.println("Selected file path: " + filePath); // Debug statement
                        selectedFiles.add(new File(filePath));
                    }

                    try {
                        // Derive the encryption key from the passphrase using PBKDF2

                        // Iterate over each selected file and encrypt them individually
                        for (File inputFile : selectedFiles) {
                            byte[] salt = FileEncryptDecrypt.readSaltFromFile(inputFile.getAbsolutePath());
                            byte[] key = FileEncryptDecrypt.deriveKey(passphrase, salt);
                            byte[] encryptedData = FileEncryptDecrypt.readEncryptedDataFromFile(inputFile.getAbsolutePath());
                            byte[] decryptedData = FileEncryptDecrypt.decrypt(encryptedData, key);
                            System.out.println("Decryption key: " + Arrays.toString(key));
                            System.out.println("Decrypting file: " + inputFile.getAbsolutePath()); // Debug statement
                            // Generate the output file name
                            String outputFileName = FileEncryptDecrypt.generateOutputFileName(inputFile.getName(), false);
                            String outputFileAbsolutePath = decryptedFolderPath + File.separator + outputFileName;
                            System.out.println("Output file path: " + decryptedFolderPath ); // Debug statement

                            // Call the encryptFile method with the output file path
                            FileEncryptDecrypt.writeDecryptedDataToFile(outputFileAbsolutePath , decryptedData);
                        }
                        // Notify the user that files have been encrypted successfully
                        JOptionPane.showMessageDialog(this, "File(s) decrypted successfully!", "Decryption Complete", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        // Handle any errors or exceptions
                        ex.printStackTrace(); // Print the stack trace
                        JOptionPane.showMessageDialog(this, "Error decrypting file(s): Wrong passphrase!", "Decryption Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                } else {
                    // Show an error message if whiteListWindow2 is not initialized or visible
                    JOptionPane.showMessageDialog(MainPage.this, "Passphrase cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(decryptFilesButton);

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(150, 150, 150));
        exitButton.setBounds(10, 260, 150, 30); // Set position and size
        exitButton.addActionListener(e -> {
            SoundPlayer.playSoundFile("spenter_1.wav");

            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                SoundPlayer.stopSound();
                SoundPlayer.playSoundFileAndWait("Windows_XP_Shutdown.wav");
                System.exit(0); // Exit the application
            }
        });

        add(exitButton);

        credits = new JButton("Credits");
        credits.setForeground(Color.GRAY);
        credits.setBackground(Color.WHITE);
        credits.setBounds(10, 290, 150, 20); // Set position and size
        // Inside the action listener for the Credits button
        credits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSoundFile("spenter_1.wav");

                // Create a new JFrame for the credits window
                JFrame creditsFrame = new JFrame("Credits");
                creditsFrame.setSize(400, 400); // Set size of the frame
                creditsFrame.setLocationRelativeTo(null); // Center the frame on the screen

                // Create a JLabel to display the credits text
                JLabel creditsLabel = new JLabel("<html><center>Credits<br><br>Dev: Munteanu Marius Eduard<br>" +
                        "BlackBox project written using \"Java JVM Technologies Inc.\"<br>" +
                        "Under the guidance of ChatGPT, aka \"Dexter\"<br>" +
                        "Associate and Sustainer: Dev Art Beckett<br><br>" +
                        "Version 1.0 (Hopefully more to come)<br><br>" +
                        "Free to use and distribute<br><br>" +
                        "Contact Information:<br>" +
                        "Email: porcushoru.agresiv@yahoo.com<br>" +
                        "Phone: 0040747858134<br><br>" +
                        "ENJOY!</center></html>");
                creditsLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the text

                // Add the label to the frame
                creditsFrame.add(creditsLabel);

                // Set the frame to be visible
                creditsFrame.setVisible(true);
            }
        });

        add(credits);

        soundButton = new JButton("\uD83D\uDD07");
        soundButton.setForeground(Color.RED);
        soundButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Set custom font and size
        soundButton.setBackground(Color.DARK_GRAY);
        soundButton.setBounds(10, 310, 150, 60);
        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopSound(); // Stop the sound
                soundButton.setVisible(false); // Hide the button
            }
        });
        add(soundButton);

// Files Label
        noSoundLabel = new JLabel("\uD83D\uDD08X");
        noSoundLabel.setForeground(Color.GRAY);
        noSoundLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20)); // Set custom font and size
        noSoundLabel.setForeground(Color.WHITE);
        noSoundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        noSoundLabel.setBounds(10, 315, 150, 50); // Set position and size
        add(noSoundLabel);

        // Files Label
        filesLabel = new JLabel("ADD FILES HERE");
        filesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filesLabel.setForeground(Color.LIGHT_GRAY);
        filesLabel.setBounds(10, 240, 780, 30); // Set position and size
        add(filesLabel);

        // Initialize CardLayout
        cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);


        whiteListWindow1.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = dtde.getTransferable();
                try {
                    DataFlavor fileListFlavor = DataFlavor.javaFileListFlavor;
                    if (transferable.isDataFlavorSupported(fileListFlavor)) {
                        List<File> files = (List<File>) transferable.getTransferData(fileListFlavor);
                        whiteListWindow1.setFiles(files.toArray(new File[0])); // Add dropped files to the white list window
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Initialize encrypted files panel
        encryptedFilesPanel = new JPanel(new BorderLayout());
        JLabel encryptedFilesLabel = new JLabel();
        encryptedFilesPanel.setBackground(Color.DARK_GRAY);
        encryptedFilesPanel.add(encryptedFilesLabel, BorderLayout.CENTER);

        // Add encrypted files panel to mainPanel
        mainPanel.add(encryptedFilesPanel, "EncryptedFilesPanel");

        // Add mainPanel to MainPage
        add(mainPanel, BorderLayout.CENTER);

        // Create buttons to switch between regular and encrypted files
        JButton regularFilesButton = new JButton("View Regular Files");
        JButton encryptedFilesButton = new JButton("View Encrypted Files");

        regularFilesButton.addActionListener(e -> cardLayout.show(mainPanel, "RegularFilesPanel"));
        encryptedFilesButton.addActionListener(e -> cardLayout.show(mainPanel, "EncryptedFilesPanel"));

    }

    private Icon getFileIcon(File file) {
        // Check file type and return corresponding icon
        if (file.isDirectory()) {
            // Return folder icon
            return UIManager.getIcon("FileView.directoryIcon");
        } else {
            // Get file extension
            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

            // Load icon based on file extension
            // For demonstration, let's assume icon files are named after their extensions (e.g., "pdf.png" for PDF files)
            String iconFileName = fileExtension + ".png";
            URL iconURL = getClass().getResource(iconFileName);
            if (iconURL != null) {
                return new ImageIcon(iconURL);
            } else {
                // Return default file icon if no specific icon found
                return UIManager.getIcon("FileView.fileIcon");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainPage mainpage = new MainPage();
            mainpage.setBackground(Color.DARK_GRAY);
            JFrame frame = new JFrame("WELCOME TO YOUR BLACKBOX, YOUR SECRETS ARE SAFE WITH US!");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set BorderLayout for the JFrame
            frame.setLayout(new BorderLayout());
            // Add the main panel to the center of the JFrame
            frame.add(mainpage, BorderLayout.CENTER);

            // Set the size of the JFrame
            frame.setSize(800, 600);

            // Center the JFrame on the screen
            frame.setLocationRelativeTo(null);

            // Make the JFrame visible
            frame.setVisible(true);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Override default close operation

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        System.exit(0); // Exit the application only if "Yes" is selected
                    }
                }
            });

        });
    }




}


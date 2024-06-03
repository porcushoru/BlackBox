package com.BlackBox.PasswordEntryPage;

import com.BlackBox.Components.PathResolver;
import com.BlackBox.ForgottenPasswordPage.ForgottenPasswordPage;
import com.BlackBox.LoginPage.LoginPage;
import com.BlackBox.MainPage.MainPage;
import com.BlackBox.Password.PasswordUtils;
import com.BlackBox.Sounds.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class PasswordEntryPage extends JPanel {
    private JLabel titleLabel;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JButton forgottenPasswordButton;

    public PasswordEntryPage(String RELATIVE_FILE_PATH) {
        setLayout(new GridBagLayout());

        // Title
        titleLabel = new JLabel("Enter Password");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, createGridBagConstraints(0, 0, 2, 1, GridBagConstraints.CENTER));

        // Password Field
        passwordField = new JPasswordField(20); // Increase preferred width
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        add(passwordField, createGridBagConstraints(0, 1, 2, 1, GridBagConstraints.CENTER));

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBackground(Color.LIGHT_GRAY);
        submitButton.addActionListener(e -> {
            // Retrieve the entered password from the password field
            String enteredPassword = new String(passwordField.getPassword()); // Obtain the entered password

            // Retrieve encrypted password from encrypted credentials
            String encryptedPassword = PasswordUtils.readStoredCredentials("Credentials.txt", 2);
            String nickName = PasswordUtils.readStoredCredentials("Credentials.txt", 1);

            if (PasswordUtils.verifyPassword(enteredPassword, encryptedPassword)) {
                // Password approved
                String message = "<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Password approved&nbsp;&nbsp;&nbsp;&nbsp;</html>";
                JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);

                // Close the PasswordEntryPage window
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                currentFrame.dispose();

                // Make the LoginPage frame invisible

                LoginPage.loginFrame.setVisible(false);


                // Open the MainPage window
                SwingUtilities.invokeLater(() -> {
                    MainPage mainPage = new MainPage();
                    JFrame mainFrame = new JFrame("Welcome to your BlackBox, " + nickName + "! Your secrets are safe with us!");
                    mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Override default close operation
                    mainPage.setBackground(Color.DARK_GRAY);
                    mainFrame.getContentPane().add(mainPage);
                    mainFrame.setSize(800, 600);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);

                    mainFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            int option = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                SoundPlayer.stopSound();
                                SoundPlayer.playSoundFileAndWait("Windows_XP_Shutdown.wav");
                                System.exit(0); // Exit the application only if "Yes" is selected
                            }
                        }
                    });
                });

            } else {
                // Wrong password
                JOptionPane.showMessageDialog(null, "Wrong password. Try again or click the \"Forgotten Password\" button.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(submitButton, createGridBagConstraints(1, 2, 1, 1, GridBagConstraints.WEST));

        // Forgotten Password Button
        forgottenPasswordButton = new JButton("Forgotten Password");
        forgottenPasswordButton.setBackground(Color.LIGHT_GRAY);
        forgottenPasswordButton.addActionListener(e -> {
            // Get the current frame
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Create a new dialog for the ForgottenPasswordPage
            JDialog dialog = new JDialog(currentFrame, "Forgotten Password", Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            // Create the ForgottenPasswordPage panel and add it to the dialog
            ForgottenPasswordPage forgottenPasswordPage = new ForgottenPasswordPage();
            dialog.getContentPane().add(forgottenPasswordPage);

            // Set the size of the dialog
            dialog.setSize(420, 280);

            // Center the dialog on the screen
            dialog.setLocationRelativeTo(null);

            // Make the dialog visible
            dialog.setVisible(true);
        });



        add(forgottenPasswordButton, createGridBagConstraints(1, 2, 1, 1, GridBagConstraints.EAST));
    }

    // Helper method to create GridBagConstraints
    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.anchor = anchor;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding
        return gbc;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PasswordEntryPage");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PasswordEntryPage(PathResolver.resolveAbsolutePath("credentials.txt"))); // Pass the file path
            frame.setSize(500, 320);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

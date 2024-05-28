package com.BlackBox.LoginPage;


import com.BlackBox.Components.PathResolver;
import com.BlackBox.CreateAccountPage.CreateAccountPage;
import com.BlackBox.CredentialsFileReader.CredentialsFileReader;
import com.BlackBox.PasswordEntryPage.PasswordEntryPage;
import com.BlackBox.Sounds.SoundPlayer;
import com.BlackBox.UserManager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class LoginPage extends JFrame {
    public static JFrame loginFrame;
    private JLabel imageLabel;
    private JButton loginButton;
    private JButton createAccountButton;


    public LoginPage() {
        loginFrame = this; // Store the frame reference

        setTitle("BlackBox Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        initComponents();
        addComponentsToFrame();

        // Add a window listener to stop the sound when the window is closed or loses focus
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                SoundPlayer.stopSound();
            }
        });
        setVisible(true);

        // Add an action listener to the login button
        loginButton.addActionListener(e -> {
            if (!CredentialsFileReader.anyAccountsExist(PathResolver.resolveAbsolutePath("credentials.txt"))) {
                // If accounts exist, redirect to login page
                JOptionPane.showMessageDialog(null, "No existing account detected. Please create new account.", "Account Exists", JOptionPane.INFORMATION_MESSAGE);
                // Here you can navigate to the login page or handle the redirection accordingly
                return;
            }
            // Create an instance of the PasswordEntryPage

            PasswordEntryPage passwordEntryPage = new PasswordEntryPage(PathResolver.resolveAbsolutePath("credentials.txt"));

            // Create a new JFrame to display the PasswordEntryPage
            JFrame passwordEntryFrame = new JFrame("Enter Password");
            passwordEntryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this frame
            passwordEntryFrame.getContentPane().add(passwordEntryPage);
            passwordEntryFrame.setSize(300, 200);
            passwordEntryFrame.setLocationRelativeTo(null);
            passwordEntryFrame.setVisible(true);

            // Add window listener to the passwordEntryFrame
            passwordEntryFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // When the PasswordEntryPage is closed, activate the LoginPage frame
                    LoginPage.this.setEnabled(true);

                    // Bring the LoginPage frame to the front
                    LoginPage.this.toFront();
                }
            });

            // Deactivate the LoginPage frame until the PasswordEntryPage is closed
            LoginPage.this.setEnabled(false);
        });

        createAccountButton.addActionListener(e -> {
            // Check if any accounts exist

            if (CredentialsFileReader.anyAccountsExist(PathResolver.resolveAbsolutePath("credentials.txt"))) {
                // If accounts exist, redirect to login page
                JOptionPane.showMessageDialog(null, "An account already exists. Please login.", "No Account Exists", JOptionPane.INFORMATION_MESSAGE);
                // Here you can navigate to the login page or handle the redirection accordingly
                return;
            }

            // Handle create account button click
            JFrame frame = new JFrame("Create Account");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new CreateAccountPage(new UserManager())); // Add the CreateAccountPage
            frame.setSize(450, 350);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            // Add window listener to the passwordEntryFrame
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // When the PasswordEntryPage is closed, activate the LoginPage frame
                    LoginPage.this.setEnabled(true);

                    // Bring the LoginPage frame to the front
                    LoginPage.this.toFront();
                }
            });

            // Deactivate the LoginPage frame until the PasswordEntryPage is closed
            LoginPage.this.setEnabled(false);
        });
    }

    private void initComponents() {
        SoundPlayer.playSoundFile("Massive Attack feat. Mos Def - I Against I_1.wav");

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Untitled 1.jpg")));
        imageLabel.setIcon(icon);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.LIGHT_GRAY);
        createAccountButton = new JButton("Create New Account");
        createAccountButton.setBackground(Color.LIGHT_GRAY);
    }

    private void addComponentsToFrame() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(imageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        gbc.gridy = 5;
        add(createAccountButton, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
package com.BlackBox.CreateAccountPage;

import com.BlackBox.Components.PathResolver;
import com.BlackBox.CredentialsFileReader.CredentialsFileReader;
import com.BlackBox.Password.PasswordUtils;
import com.BlackBox.UserManager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountPage extends JPanel {
    private JLabel titleLabel;
    private JComboBox<String> securityQuestion1Dropdown;
    private JComboBox<String> securityQuestion2Dropdown;
    private JTextField answerField1;
    private JTextField answerField2;
    private JLabel createUsernameLabel;
    private JLabel createPasswordLabel;
    private static JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;

    private UserManager userManager;

    private String[] securityQuestions = {
            "What is the name of your first pet?",
            "What city were you born in?",
            "What is your mother's maiden name?",
            "What is your favorite food?",
            "How many inches do you have?"
    };

    public CreateAccountPage() {
    }

    public CreateAccountPage(UserManager userManager) {
        setLayout(null); // Use absolute positioning

        this.userManager = userManager;

        // Title
        titleLabel = new JLabel("<html>Write your password, then select the preferred<br> questions and write the respective answers</html>");
        titleLabel.setForeground(Color.DARK_GRAY);
        Font font = titleLabel.getFont();
        titleLabel.setFont(font.deriveFont(9f));
        titleLabel.setBounds(20, 0, 380, 40);
        add(titleLabel);

        // Create Username Label and Field
        createUsernameLabel = new JLabel("Nickname*:");
        createUsernameLabel.setForeground(Color.GRAY);
        createUsernameLabel.setBounds(20, 40, 100, 20);
        add(createUsernameLabel);
        usernameField = new JTextField();
        usernameField.setBounds(130, 40, 250, 20);
        add(usernameField);

        // Create Password Label and Field
        createPasswordLabel = new JLabel("Password:");
        createPasswordLabel.setBounds(20, 70, 100, 20);
        add(createPasswordLabel);
        passwordField = new JPasswordField();
        passwordField.setBounds(130, 70, 250, 20);
        add(passwordField);

        // Security Question 1 Dropdown
        securityQuestion1Dropdown = new JComboBox<>(securityQuestions);
        securityQuestion1Dropdown.setBounds(20, 100, 360, 20);
        add(securityQuestion1Dropdown);

        // Answer Field 1
        answerField1 = new JTextField();
        answerField1.setBounds(20, 130, 360, 20);
        add(answerField1);

        // Security Question 2 Dropdown
        securityQuestion2Dropdown = new JComboBox<>(securityQuestions);
        securityQuestion2Dropdown.setBounds(20, 160, 360, 20);
        add(securityQuestion2Dropdown);

        // Answer Field 2
        answerField2 = new JTextField();
        answerField2.setBounds(20, 190, 360, 20);
        add(answerField2);

        // Create Account Button
        createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(125, 240, 150, 25);
        createAccountButton.setBackground(Color.LIGHT_GRAY);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle create account button click
                String userName = usernameField.getText();
                String password = new String(passwordField.getPassword()); // Obtain the entered password
                String question1 = (String) securityQuestion1Dropdown.getSelectedItem();
                String answer1 = answerField1.getText();
                String question2 = (String) securityQuestion2Dropdown.getSelectedItem();
                String answer2 = answerField2.getText();

                // Generate salt
                byte[] salt = PasswordUtils.generateSalt();

                // Hash each credential individually
//                String username = PasswordUtils.hashPasswordWithSalt(userName, salt);
                String hashedPassword = PasswordUtils.hashPasswordWithSalt(password, salt);
                String hashedQuestion1 = PasswordUtils.hashPasswordWithSalt(question1, salt);
                String hashedAnswer1 = PasswordUtils.hashPasswordWithSalt(answer1, salt);
                String hashedQuestion2 = PasswordUtils.hashPasswordWithSalt(question2, salt);
                String hashedAnswer2 = PasswordUtils.hashPasswordWithSalt(answer2, salt);

                System.out.println(password);

                // Check if username already exists
                if (CredentialsFileReader.anyAccountsExist(PathResolver.resolveAbsolutePath("credentials.txt"))) {
                    JOptionPane.showMessageDialog(null, "An account already exists. Please login.", "Account Creation Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add the user with hashed credentials
                userManager.addUser(userName, hashedPassword, hashedQuestion1, hashedAnswer1, hashedQuestion2, hashedAnswer2);

                // Display success message
                JOptionPane.showMessageDialog(null, "Account created successfully!", "Account Created", JOptionPane.INFORMATION_MESSAGE);

                // Close the current frame
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(createAccountButton);
                currentFrame.dispose();
            }
        });
        add(createAccountButton);
    }

//    public static String getUsernameField() {
//        return usernameField.getText();
//    }

    public void setWindowSize(JFrame frame, int width, int height) {
        frame.setSize(width, height);
    }

    public static void main(String[] args) {
// Create an instance of the UserManager
        UserManager userManager = new UserManager();

        SwingUtilities.invokeLater(() -> {
            CreateAccountPage createAccountPage = new CreateAccountPage(userManager);
            JFrame frame = new JFrame("Create Account");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(createAccountPage);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            createAccountPage.setWindowSize(frame, 450, 350);
        });
    }
}

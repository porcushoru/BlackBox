package com.BlackBox.ForgottenPasswordPage;

import com.BlackBox.Components.PathResolver;
import com.BlackBox.CreateAccountPage.CreateAccountPage;
import com.BlackBox.CredentialsFileWriter.CredentialsFileWriter;
import com.BlackBox.LoginPage.LoginPage;
import com.BlackBox.Password.PasswordUtils;

import javax.swing.*;
import java.awt.*;

public class ForgottenPasswordPage extends JPanel {
    private JLabel titleLabel;
    private JComboBox<String> securityQuestion1Dropdown;
    private JTextField answerField1;
    private JComboBox<String> securityQuestion2Dropdown;
    private JTextField answerField2;
    private JButton restoreAccessButton;
    private String[] securityQuestions = {
            "What is the name of your first pet?",
            "What city were you born in?",
            "What is your mother's maiden name?",
            "What is your favorite food?",
            "How many inches do you have?"
    };


    public ForgottenPasswordPage() {
        setLayout(null);

        titleLabel = new JLabel("<html>Choose the questions in the same order that were used during<br> the account creation stage, and write the respective answers</html>");
        titleLabel.setForeground(Color.DARK_GRAY);
        Font font = titleLabel.getFont();
        titleLabel.setFont(font.deriveFont(9f));
        titleLabel.setBounds(20, 0, 380, 40);
        add(titleLabel);

        securityQuestion1Dropdown = new JComboBox<>(securityQuestions);
        securityQuestion1Dropdown.setBounds(20, 50, 360, 20);
        add(securityQuestion1Dropdown);

        answerField1 = new JTextField();
        answerField1.setBounds(20, 80, 360, 20);
        add(answerField1);

        securityQuestion2Dropdown = new JComboBox<>(securityQuestions);
        securityQuestion2Dropdown.setBounds(20, 110, 360, 20);
        add(securityQuestion2Dropdown);

        answerField2 = new JTextField();
        answerField2.setBounds(20, 140, 360, 20);
        add(answerField2);

        restoreAccessButton = new JButton("Restore Access");
        restoreAccessButton.setBounds(125, 190, 150, 25);
        restoreAccessButton.setBackground(Color.LIGHT_GRAY);
        restoreAccessButton.setHorizontalAlignment(SwingConstants.CENTER);
        restoreAccessButton.addActionListener(e -> {

                String question1 = (String) securityQuestion1Dropdown.getSelectedItem();
                String answer1 = answerField1.getText();
                String question2 = (String) securityQuestion1Dropdown.getSelectedItem();
                String answer2 = answerField1.getText();

                String encryptedQuestion1 = PasswordUtils.readStoredCredentials(PathResolver.resolveAbsolutePath("credentials.txt"), 3);
                String encryptedAnswer1 = PasswordUtils.readStoredCredentials(PathResolver.resolveAbsolutePath("credentials.txt"), 4);
                String encryptedQuestion2 = PasswordUtils.readStoredCredentials(PathResolver.resolveAbsolutePath("credentials.txt"), 5);
                String encryptedAnswer2 = PasswordUtils.readStoredCredentials(PathResolver.resolveAbsolutePath("credentials.txt"), 6);

                if (PasswordUtils.verifyPassword(question1, encryptedQuestion1) &&
                    PasswordUtils.verifyPassword(answer1, encryptedAnswer1) &&
                    PasswordUtils.verifyPassword(question2, encryptedQuestion2) &&
                    PasswordUtils.verifyPassword(answer2, encryptedAnswer2)) {

                    CredentialsFileWriter credentialsFileWriter = new CredentialsFileWriter();
                    credentialsFileWriter.resetCredentialsToFile(PathResolver.resolveAbsolutePath("credentials.txt"));

                    JOptionPane.showMessageDialog(null, "<html>User Confirmed<br> Create New Account</html>", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Close all windows until you reach the LoginPage
                    Window currentWindow = SwingUtilities.getWindowAncestor(this);
                    while (currentWindow != null && !(currentWindow instanceof LoginPage)) {
                        currentWindow.dispose();
                        currentWindow = SwingUtilities.getWindowAncestor(currentWindow);
                    }

                    // If the current window is LoginPage, dispose of it and show the CreateAccountPage
                    if (currentWindow != null) {
                        currentWindow.dispose();
                        CreateAccountPage createAccountPage = new CreateAccountPage();
                        createAccountPage.setVisible(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Attempt failed. Try again!", "Error", JOptionPane.ERROR_MESSAGE);
                }
        });
        add(restoreAccessButton);
    }

    public void setWindowSize(JFrame frame, int width,int height) {
        frame.setSize(width, height);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ForgottenPasswordPage forgottenPasswordPage = new ForgottenPasswordPage();
            JFrame frame = new JFrame("Restore Access");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(forgottenPasswordPage);
//            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            forgottenPasswordPage.setWindowSize(frame, 450, 300);
        });
    }
}

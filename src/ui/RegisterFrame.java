package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegisterFrame() {

        setTitle("Cryptocurrency Clone - Register");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(130, 25, 220, 40);
        panel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 90, 100, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(170, 90, 200, 25);
        panel.add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(60, 135, 100, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(170, 135, 200, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 180, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(170, 180, 200, 25);
        panel.add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 230, 120, 35);
        panel.add(registerButton);

        registerButton.addActionListener(e -> registerUser());

        add(panel);
    }

    private void registerUser() {

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields!"
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.registerUser(
                username,
                email,
                password
        );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!"
            );

            usernameField.setText("");
            emailField.setText("");
            passwordField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!\nEmail or username may already exist."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            RegisterFrame frame = new RegisterFrame();
            frame.setVisible(true);
        });
    }
}
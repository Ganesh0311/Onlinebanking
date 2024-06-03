import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class UserGUI extends JFrame {
    private Connection connection;
    private User user;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JLabel statusLabel;
    private BankingOperationsGUI bog;

    public UserGUI(Connection connection) {
        this.connection = connection;
        this.user = new User(connection);

        setTitle("User Registration/Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        statusLabel = new JLabel();

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");
        panel.add(fullNameLabel);
        panel.add(fullNameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(statusLabel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
        
        

        add(panel);
    }

    private void registerUser() {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        if (user.user_exist(email)) {
            statusLabel.setText("User Already Exists for this Email Address!!");
            JOptionPane.showMessageDialog(null,"User Already Exists for this Email Address!!");
            
            return;
        }
        user.register(fullName, email, password);
        statusLabel.setText("Registration Successful!");
        JOptionPane.showMessageDialog(null,"Registration successful now you can login");

    }

    private void loginUser() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        if (user.login(email, password)) {
            statusLabel.setText("Login Successful!");
            JOptionPane.showMessageDialog(null,"Login Successful!");
            bog = new BankingOperationsGUI(connection, email,fullName);
            bog.setVisible(true);
            dispose();
        } else {
            statusLabel.setText("Login Failed!");
            JOptionPane.showMessageDialog(null,"Login Failed!");

        }
    }

    public static void main(String[] args) {
        // Assuming you have established the connection already
        Connection connection = DatabaseConnection.getConnection();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserGUI(connection).setVisible(true);
            }
        });
    }
}

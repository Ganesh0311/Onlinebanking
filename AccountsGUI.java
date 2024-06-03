

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AccountsGUI extends JFrame {
    private Connection connection;
    private Accounts accounts;
    private JTextField emailField;
    private JTextField fullNameField;
    private JTextField initialAmountField;
    private JTextField securityPinField;

    public AccountsGUI(Connection con) {
        this.connection = con;
        this.accounts = new Accounts(connection);

        setTitle("Open New Account");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }
    public AccountsGUI(Connection con,String email,String fullname) {
        this(con);
        emailField.setText(email);
        fullNameField.setText(fullname);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField();
        JLabel initialAmountLabel = new JLabel("Initial Amount:");
        initialAmountField = new JTextField();
        JLabel securityPinLabel = new JLabel("Security Pin:");
        securityPinField = new JTextField();

        JButton openAccountButton = new JButton("Open Account");
        JButton backButton = new JButton("Back");
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(fullNameLabel);
        panel.add(fullNameField);
        panel.add(initialAmountLabel);
        panel.add(initialAmountField);
        panel.add(securityPinLabel);
        panel.add(securityPinField);
        panel.add(openAccountButton);
        panel.add(backButton);

        openAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAccount();
            }
        });
       backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        add(panel);
    }

    private void openAccount() {
        String email = emailField.getText();
        String fullName = fullNameField.getText();
        double initialAmount = Double.parseDouble(initialAmountField.getText());
        String securityPin = securityPinField.getText();

        try {
            long accountNumber = accounts.open_account(email, fullName, initialAmount, securityPin);
            JOptionPane.showMessageDialog(this, "Account opened successfully! Account number: " + accountNumber);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Assuming you have established the connection already
        Connection connection = DatabaseConnection.getConnection();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AccountsGUI(connection).setVisible(true);
            }
        });
    }
}


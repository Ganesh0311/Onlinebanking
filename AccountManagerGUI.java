import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class AccountManagerGUI extends JFrame implements ActionListener {
    private JTextField accountNumberField, amountField, securityPinField;
    private JButton creditButton, debitButton, transferButton, balanceButton, backButton ;
    private AccountManager accountManager;
    private Accounts account;
    JLabel accountNumberLabel;
   

    public AccountManagerGUI(Connection connection) {
        setTitle("Account Manager");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        accountManager = new AccountManager(connection);

        account = new Accounts(connection);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(20, 20, 120, 25);
        panel.add(accountNumberLabel);

        accountNumberField = new JTextField();
        accountNumberField.setBounds(150, 20, 200, 25);
        panel.add(accountNumberField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(20, 60, 120, 25);
        panel.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 60, 200, 25);
        panel.add(amountField);

        JLabel securityPinLabel = new JLabel("Security Pin:");
        securityPinLabel.setBounds(20, 100, 120, 25);
        panel.add(securityPinLabel);

        securityPinField = new JTextField();
        securityPinField.setBounds(150, 100, 200, 25);
        panel.add(securityPinField);

        creditButton = new JButton("Credit");
        creditButton.setBounds(20, 140, 100, 25);
        creditButton.addActionListener(this);
        panel.add(creditButton);

        debitButton = new JButton("Debit");
        debitButton.setBounds(130, 140, 100, 25);
        debitButton.addActionListener(this);
        panel.add(debitButton);

        transferButton = new JButton("Transfer");
        transferButton.setBounds(240, 140, 100, 25);
        transferButton.addActionListener(this);
        panel.add(transferButton);

        balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(20, 180, 150, 25);
        balanceButton.addActionListener(this);
        panel.add(balanceButton);

        backButton = new JButton("Back");
        backButton.setBounds(180, 180, 150, 25);
        backButton.addActionListener(this);
        panel.add(backButton);

        add(panel);
        setVisible(true);
    }
    public AccountManagerGUI(Connection connection,String email,long account_num)
    {
        this(connection);
        String num = String.valueOf(account_num);
        accountNumberField.setText(num);

    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == creditButton) {
            try {
                long accountNumber = Long.parseLong(accountNumberField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String securityPin = securityPinField.getText();
                accountManager.credit_money(accountNumber, amount, securityPin);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == debitButton) {
            try {
                long accountNumber = Long.parseLong(accountNumberField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String securityPin = securityPinField.getText();
                accountManager.debit_money(accountNumber, amount, securityPin);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == transferButton) {
            try {
                long senderAccountNumber = Long.parseLong(accountNumberField.getText());
                long receiverAccountNumber = Long.parseLong(JOptionPane.showInputDialog(this, "Enter Receiver Account Number:"));
                double amount = Double.parseDouble(amountField.getText());
                String securityPin = securityPinField.getText();
                accountManager.transfer_money(senderAccountNumber, receiverAccountNumber, amount, securityPin);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == balanceButton) {
            long accountNumber = Long.parseLong(accountNumberField.getText());
            String securityPin = securityPinField.getText();
            accountManager.getBalance(accountNumber, securityPin);
        } else if (e.getSource() == backButton) {
           this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        // Replace this with your database connection
        Connection connection = null; // Initialize your database connection here

        SwingUtilities.invokeLater(() -> new AccountManagerGUI(connection));
    }
}

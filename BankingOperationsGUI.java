import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class BankingOperationsGUI extends JFrame {

    private Connection connection;
    private String email;
    private String fullname;
    private Accounts account;

    public BankingOperationsGUI(Connection connection, String email,String fullname) {
        this.connection = connection;
        this.email = email;
        this.fullname=fullname;
        account = new Accounts(connection);
        setTitle("Banking Operations");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true); // Set frame visible after adding components
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 0)); // Adjusted GridLayout

        JButton openAccountButton = new JButton("Open Account");
        JButton debitORcreditButton = new JButton("Debit/Credit Money");
        JButton loanButton = new JButton("Loan");
        JButton transferButton = new JButton("Transfer Money");
        JButton logoutButton  = new JButton("Log Out");
        panel.add(openAccountButton);
        panel.add(debitORcreditButton);
        panel.add(transferButton);
        panel.add(loanButton);
        panel.add(logoutButton);

        openAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement open account logic here
                new AccountsGUI(connection, email,fullname).setVisible(true);
                 // Close current frame
            }
        });

        debitORcreditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement debit money logic here
                new AccountManagerGUI(connection,email, account.getAccount_number(email));
            }
        });

        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement credit money logic here
                new AccountManagerGUI(connection,email, account.getAccount_number(email));

            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement transfer money logic here
                new AccountManagerGUI(connection,email, account.getAccount_number(email));

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement transfer money logic here
                JOptionPane.showMessageDialog(null,"Successfully logout.....!");
                dispose();
            }
        });

        add(panel); // Add panel to the frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // For testing purpose, you can create a dummy connection and email
                Connection connection = null;
                String email = "example@example.com";
                String fullname ="XYZname";
                new BankingOperationsGUI(connection, email,fullname);
            }
        });
    }
}

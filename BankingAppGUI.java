import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BankingAppGUI extends JFrame {

   

    private Connection connection;
    private User user;
    private Accounts accounts;
    private AccountManager accountManager;
    private UserGUI usergui;
    private JTextField emailField;
    private JPasswordField passwordField;

    public BankingAppGUI() throws SQLException {
        initializeDB();
        usergui = new UserGUI(connection);

        setTitle("Banking System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initializeDB() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DatabaseConnection.getConnection();
            user = new User(connection);
            accounts = new Accounts(connection);
            accountManager = new AccountManager(connection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
            System.exit(1);
        }
    }

    private void initComponents() {


        //usergui.setVisible(true);
      

        

        
    }

    private void openBankingOperationsWindow(String email) {
        // Create a new instance of BankingOperationsGUI passing necessary parameters
        BankingOperationsGUI operationsGUI = new BankingOperationsGUI(connection, email);
        operationsGUI.setVisible(true);
        this.dispose(); // Close the login window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new BankingAppGUI().setVisible(true);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}


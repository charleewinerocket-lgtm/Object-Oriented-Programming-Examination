import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class QN6MemberRegistration extends JFrame implements ActionListener {

    JLabel lblID, lblName, lblNIN, lblPhone, lblDeposit;
    JTextField txtID, txtName, txtNIN, txtPhone, txtDeposit;
    JButton btnRegister, btnClear, btnExit;

    Connection con;
    PreparedStatement ps;

    public QN6MemberRegistration() {

        setTitle("Wazalendo SACCO Member Registration");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        lblID = new JLabel("Member ID");
        lblName = new JLabel("Full Name");
        lblNIN = new JLabel("National ID (NIN)");
        lblPhone = new JLabel("Phone Number");
        lblDeposit = new JLabel("Initial Deposit (UGX)");

        txtID = new JTextField();
        txtName = new JTextField();
        txtNIN = new JTextField();
        txtPhone = new JTextField();
        txtDeposit = new JTextField();

        btnRegister = new JButton("Register");
        btnClear = new JButton("Clear");
        btnExit = new JButton("Exit");

        add(lblID);
        add(txtID);
        add(lblName);
        add(txtName);
        add(lblNIN);
        add(txtNIN);
        add(lblPhone);
        add(txtPhone);
        add(lblDeposit);
        add(txtDeposit);
        add(btnRegister);
        add(btnClear);
        add(btnExit);

        btnRegister.addActionListener(this);
        btnClear.addActionListener(this);
        btnExit.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRegister) {

            String id = txtID.getText().trim();
            String name = txtName.getText().trim();
            String nin = txtNIN.getText().trim();
            String phone = txtPhone.getText().trim();
            String deposit = txtDeposit.getText().trim();

            // Validation
            if (id.isEmpty() || name.isEmpty() || nin.isEmpty() ||
                    phone.isEmpty() || deposit.isEmpty()) {

                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            if (nin.length() != 14) {

                JOptionPane.showMessageDialog(this, "NIN must be exactly 14 characters.");
                return;
            }

            if (!phone.matches("\\d{10}")) {

                JOptionPane.showMessageDialog(this, "Phone number must be numeric and exactly 10 digits.");
                return;
            }

            double amount;

            try {

                amount = Double.parseDouble(deposit);

                if (amount <= 0) {

                    JOptionPane.showMessageDialog(this, "Initial Deposit must be a positive number.");
                    return;
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Deposit must be numeric.");
                return;
            }

            try {

                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

                con = DriverManager.getConnection(
                        "jdbc:ucanaccess://C:/Database/Wazalendo.accdb");

                String sql = "INSERT INTO Members(MemberID,FullName,NIN,PhoneNumber,InitialDeposit) VALUES(?,?,?,?,?)";

                ps = con.prepareStatement(sql);

                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, nin);
                ps.setString(4, phone);
                ps.setDouble(5, amount);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Member Registered Successfully.");

                txtID.setText("");
                txtName.setText("");
                txtNIN.setText("");
                txtPhone.setText("");
                txtDeposit.setText("");

                con.close();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage());

            }

        }

        if (e.getSource() == btnClear) {

            txtID.setText("");
            txtName.setText("");
            txtNIN.setText("");
            txtPhone.setText("");
            txtDeposit.setText("");

        }

        if (e.getSource() == btnExit) {

            System.exit(0);

        }

    }

    public static void main(String[] args) {

        new QN6MemberRegistration();

    }

}
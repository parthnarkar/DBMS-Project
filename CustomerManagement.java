package com.bank.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerManagement extends JFrame {
    private JTextField customerIdField, firstNameField, middleNameField, lastNameField;
    private JTextField streetField, areaField, pincodeField, accountNumberField, docsField;

    public CustomerManagement() {
        setTitle("Customer Management");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); // Use GridBagLayout for more control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; // Center align components

        // Add components with grid constraints
        addComponent(gbc, new JLabel("Customer ID:"), 0);
        customerIdField = new JTextField(10); // Set preferred width
        addComponent(gbc, customerIdField, 1);

        addComponent(gbc, new JLabel("First Name:"), 2);
        firstNameField = new JTextField(10); // Set preferred width
        addComponent(gbc, firstNameField, 3);

        addComponent(gbc, new JLabel("Middle Name:"), 4);
        middleNameField = new JTextField(10); // Set preferred width
        addComponent(gbc, middleNameField, 5);

        addComponent(gbc, new JLabel("Last Name:"), 6);
        lastNameField = new JTextField(10); // Set preferred width
        addComponent(gbc, lastNameField, 7);

        addComponent(gbc, new JLabel("Street:"), 8);
        streetField = new JTextField(10); // Set preferred width
        addComponent(gbc, streetField, 9);

        addComponent(gbc, new JLabel("Area:"), 10);
        areaField = new JTextField(10); // Set preferred width
        addComponent(gbc, areaField, 11);

        addComponent(gbc, new JLabel("Pincode:"), 12);
        pincodeField = new JTextField(10); // Set preferred width
        addComponent(gbc, pincodeField, 13);

        addComponent(gbc, new JLabel("Account Number:"), 14);
        accountNumberField = new JTextField(10); // Set preferred width
        addComponent(gbc, accountNumberField, 15);

        addComponent(gbc, new JLabel("Documents:"), 16);
        docsField = new JTextField(10); // Set preferred width
        addComponent(gbc, docsField, 17);

        // Create and add buttons
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new InsertAction());
        addComponent(gbc, insertButton, 18);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchAction());
        addComponent(gbc, searchButton, 19);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteAction());
        addComponent(gbc, deleteButton, 20);
    }

    private void addComponent(GridBagConstraints gbc, Component component, int gridy) {
        gbc.gridx = 0; // Column 0 for labels
        gbc.gridy = gridy; // Row number
        gbc.weightx = 0.5; // Adjust weight to center
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding
        add(component, gbc); // Add the label component

        gbc.gridx = 1; // Move to the next column for text fields/buttons
        gbc.weightx = 1.0; // Allow the text field to take up extra space
        add(component, gbc); // Add the text field component
    }

    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=bank;encrypt=true;trustServerCertificate=true", "sa", "1234")) {
                String sql = "INSERT INTO customer (docs, c_id, street, area, pincode, fnamae, mname, lname, acc_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, docsField.getText());
                stmt.setInt(2, Integer.parseInt(customerIdField.getText()));
                stmt.setString(3, streetField.getText());
                stmt.setString(4, areaField.getText());
                stmt.setInt(5, Integer.parseInt(pincodeField.getText()));
                stmt.setString(6, firstNameField.getText());
                stmt.setString(7, middleNameField.getText());
                stmt.setString(8, lastNameField.getText());
                stmt.setInt(9, Integer.parseInt(accountNumberField.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Customer Inserted Successfully");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class SearchAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=bank;encrypt=true;trustServerCertificate=true", "sa", "1234")) {
                String sql = "SELECT * FROM customer WHERE c_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(customerIdField.getText()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    firstNameField.setText(rs.getString("fnamae"));
                    middleNameField.setText(rs.getString("mname"));
                    lastNameField.setText(rs.getString("lname"));
                    streetField.setText(rs.getString("street"));
                    areaField.setText(rs.getString("area"));
                    pincodeField.setText(String.valueOf(rs.getInt("pincode")));
                    accountNumberField.setText(String.valueOf(rs.getInt("acc_no")));
                    docsField.setText(rs.getString("docs"));
                } else {
                    JOptionPane.showMessageDialog(null, "Customer Not Found");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private class DeleteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=bank;encrypt=true;trustServerCertificate=true", "sa", "1234")) {
                String sql = "DELETE FROM customer WHERE c_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(customerIdField.getText()));
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Customer Deleted Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Customer Not Found");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerManagement frame = new CustomerManagement();
            frame.setVisible(true);
        });
    }
}

explain this frontend in bullet points as we are going to explain it to mam

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm extends JFrame implements ActionListener {
    private JTextField nameField, mobileField, dobField, addressField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JCheckBox termsCheckBox;
    private Connection connection;

    public RegistrationForm() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        add(mobileField);

        add(new JLabel("Gender:"));
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        add(genderPanel);

        add(new JLabel("DOB:"));
        dobField = new JTextField();
        add(dobField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Accept Terms And Conditions:"));
        termsCheckBox = new JCheckBox();
        add(termsCheckBox);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);

        // Database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/your_database_name";
            String username = “root”;
            String password = “password@1”;
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            // Get values from fields
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String dob = dobField.getText();
            String address = addressField.getText();
            boolean termsAccepted = termsCheckBox.isSelected();

            // Insert into database
            try {
                String sql = "INSERT INTO users (name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, name);
                statement.setString(2, mobile);
                statement.setString(3, gender);
                statement.setString(4, dob);
                statement.setString(5, address);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Data submitted successfully");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error submitting data");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationForm::new);
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Pattern;

public class StudentRegistrationForm extends JFrame {
    private StudentDatabase database;
    
    // Form components
    private JTextField nameField, emailField, phoneField, dobField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private ButtonGroup genderGroup;
    private JComboBox<String> courseCombo;
    private JButton submitBtn, clearBtn, viewBtn;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentRegistrationForm() {
        // Initialize database
        database = new StudentDatabase();
        
        // Set up the frame
        setTitle("Student Registration Form");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 245));

        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Create form panel
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);

        // Create table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.SOUTH);

        // Center the frame
        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(100, 150, 200));
        panel.setPreferredSize(new Dimension(900, 50));
        
        JLabel titleLabel = new JLabel("Student Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Name field
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(createLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(nameField, gbc);

        // Email field
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(emailField, gbc);

        // Phone field
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(createLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(phoneField, gbc);

        // Date of Birth field
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(createLabel("Date of Birth:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        dobField = new JTextField(20);
        dobField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(dobField, gbc);

        // Gender radio buttons
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(createLabel("Gender:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(Color.WHITE);
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        mainPanel.add(genderPanel, gbc);

        // Course combo box
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(createLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        String[] courses = {"Select Course", "Computer Science", "Information Technology", 
                           "Mechanical Engineering", "Civil Engineering", "Electronics", 
                           "Business Administration", "Arts"};
        courseCombo = new JComboBox<>(courses);
        courseCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(courseCombo, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setBackground(new Color(50, 150, 50));
        submitBtn.setForeground(Color.WHITE);
        
        clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 14));
        clearBtn.setBackground(new Color(200, 100, 50));
        clearBtn.setForeground(Color.WHITE);
        
        viewBtn = new JButton("View All");
        viewBtn.setFont(new Font("Arial", Font.BOLD, 14));
        viewBtn.setBackground(new Color(80, 120, 180));
        viewBtn.setForeground(Color.WHITE);
        
        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(viewBtn);
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        submitBtn.addActionListener(e -> submitForm());
        clearBtn.addActionListener(e -> clearForm());
        viewBtn.addActionListener(e -> loadStudents());

        return mainPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Create table
        String[] columns = {"ID", "Name", "Email", "Phone", "Gender", "Course", "DOB"};
        tableModel = new DefaultTableModel(columns, 0);
        
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(850, 180));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void submitForm() {
        // Validate inputs
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String dob = dobField.getText().trim();
        String course = (String) courseCombo.getSelectedItem();
        
        String gender = "";
        if (maleRadio.isSelected()) gender = "Male";
        else if (femaleRadio.isSelected()) gender = "Female";
        else if (otherRadio.isSelected()) gender = "Other";

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || 
            dob.isEmpty() || gender.isEmpty() || course.equals("Select Course")) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all fields!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, 
                "Invalid email!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone
        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, 
                "Phone must be 10 digits!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save to database
        boolean success = database.addStudent(name, email, phone, gender, course, dob);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Student registered successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Registration failed!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dobField.setText("");
        genderGroup.clearSelection();
        courseCombo.setSelectedIndex(0);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = database.getAllStudents();
        
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getGender(),
                student.getCourse(),
                student.getDateOfBirth()
            };
            tableModel.addRow(row);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static void main(String[] args) {
        // Create and display the form
        SwingUtilities.invokeLater(() -> {
            StudentRegistrationForm form = new StudentRegistrationForm();
            form.setVisible(true);
        });
    }
}

package IT212prelims_LabProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

// Updated Student Data Class using MyList
class StudentData {
    String userId = "2251234";
    String accountName = "Lin Ling Xiao Lang Bao";
    String dateRegistered = "July 21, 2020";
    String accountType = "Student";

    MyMap generalInfo;
    MyMap contactInfo;
    MyMap contactPersons;

    public StudentData() {
        generalInfo = new MyMap();
        contactInfo = new MyMap();
        contactPersons = new MyMap();

        initializeDefaultData();
    }

    private void initializeDefaultData() {
        // Initialize general information
        generalInfo.put("Gender", "Non-Binary");
        generalInfo.put("Birthday", "February 14, 2002");
        generalInfo.put("Citizenship", "FILIPINO");
        generalInfo.put("Religion", "BUDDHIST MONK");
        generalInfo.put("Civil Status", "SINGLE");
        generalInfo.put("Birthplace", "SHANGHAI CITY");
        generalInfo.put("Nationality", "CHINESE");

        // Initialize contact information
        contactInfo.put("Home Address", "SIOPAO ST. SHANGHAI CITY, CHINA");
        contactInfo.put("Home Telephone No", "N/A");
        contactInfo.put("Baguio Address", "69 EAGLE CREST, BAKAKENG NORTE");
        contactInfo.put("Baguio Telephone No", "N/A");
        contactInfo.put("Cellphone No", "02502380572");
        contactInfo.put("Email Address", "ilovesiopao123@gmail.com");

        // Initialize contact persons
        contactPersons.put("Father's Name", "Shen Rong");
        contactPersons.put("Father's Occupation", "Dragon King");
        contactPersons.put("Mother's Maiden Name", "Yao Yao");
        contactPersons.put("Mother's Occupation", "Jade Empress");
    }
}

class PersonalDetailsPanel extends JPanel {
    private StudentData studentData;
    private JPanel detailsPanel;
    private CardLayout detailsCardLayout;
    private JPanel personalDetailsView;

    public PersonalDetailsPanel(StudentData studentData) {
        this.studentData = studentData;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("User Profile");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(26, 54, 93));
        headerPanel.add(headerLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(245, 245, 245));

        // Left side - Profile section
        JPanel leftPanel = createProfileSection();
        contentArea.add(leftPanel, BorderLayout.WEST);

        // Right side - Details section
        JPanel rightPanel = createDetailsSection();
        contentArea.add(rightPanel, BorderLayout.CENTER);

        add(contentArea, BorderLayout.CENTER);
    }

    private JPanel createProfileSection() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        profilePanel.setPreferredSize(new Dimension(300, 0));

        // Profile picture placeholder
        JPanel picturePanel = new JPanel();
        picturePanel.setBackground(new Color(230, 230, 230));
        picturePanel.setPreferredSize(new Dimension(150, 150));
        picturePanel.setMaximumSize(new Dimension(150, 150));
        JLabel pictureLabel = new JLabel("Student Photo");
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        picturePanel.add(pictureLabel);
        profilePanel.add(picturePanel);

        profilePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JButton personalDetailsBtn = createProfileButton("Personal Details");
        JButton accountInfoBtn = createProfileButton("Account Info");
        JButton changePasswordBtn = createProfileButton("Change Password");

        personalDetailsBtn.addActionListener(e -> showPersonalDetails());
        accountInfoBtn.addActionListener(e -> showAccountInfo());
        changePasswordBtn.addActionListener(e -> showChangePassword());

        profilePanel.add(personalDetailsBtn);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(accountInfoBtn);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        profilePanel.add(changePasswordBtn);

        return profilePanel;
    }

    private JButton createProfileButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(250, 40));
        button.setBackground(new Color(66, 153, 225));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private JPanel createDetailsSection() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(245, 245, 245));

        detailsCardLayout = new CardLayout();
        detailsPanel = new JPanel(detailsCardLayout);

        detailsPanel.add(createPersonalDetailsView(), "personal");
        detailsPanel.add(createAccountInfoView(), "account");
        detailsPanel.add(createChangePasswordView(), "password");

        rightPanel.add(detailsPanel, BorderLayout.CENTER);

        // Show personal details by default
        detailsCardLayout.show(detailsPanel, "personal");

        return rightPanel;
    }

    private JPanel createPersonalDetailsView() {
        JScrollPane scrollPane = new JScrollPane();
        personalDetailsView = new JPanel();
        personalDetailsView.setLayout(new BoxLayout(personalDetailsView, BoxLayout.Y_AXIS));
        personalDetailsView.setBackground(Color.WHITE);
        personalDetailsView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshPersonalDetailsView();

        scrollPane.setViewportView(personalDetailsView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);
        return wrapperPanel;
    }

    private void refreshPersonalDetailsView() {
        personalDetailsView.removeAll();

        // General Information Section
        personalDetailsView.add(createSectionHeader("GENERAL INFORMATION"));
        Object[] generalEntries = studentData.generalInfo.entrySet();
        for (Object obj : generalEntries) {
            if (obj instanceof KeyValuePair) {
                KeyValuePair entry = (KeyValuePair) obj;
                personalDetailsView.add(createInfoFieldWithButtons(entry.getKey(), entry.getValue(), "general"));
            }
        }

        personalDetailsView.add(Box.createRigidArea(new Dimension(0, 20)));

        // Contact Information Section
        personalDetailsView.add(createSectionHeader("CONTACT INFORMATION"));
        Object[] contactEntries = studentData.contactInfo.entrySet();
        for (Object obj : contactEntries) {
            if (obj instanceof KeyValuePair) {
                KeyValuePair entry = (KeyValuePair) obj;
                personalDetailsView.add(createInfoFieldWithButtons(entry.getKey(), entry.getValue(), "contact"));
            }
        }

        personalDetailsView.add(Box.createRigidArea(new Dimension(0, 20)));

        // Contact Persons Section
        personalDetailsView.add(createSectionHeader("CONTACT PERSONS"));
        personalDetailsView.add(createSubSectionHeader("Parents"));
        Object[] personEntries = studentData.contactPersons.entrySet();
        for (Object obj : personEntries) {
            if (obj instanceof KeyValuePair) {
                KeyValuePair entry = (KeyValuePair) obj;
                personalDetailsView.add(createInfoFieldWithButtons(entry.getKey(), entry.getValue(), "persons"));
            }
        }

        personalDetailsView.add(Box.createRigidArea(new Dimension(0, 20)));

        // Note
        JLabel noteLabel = new JLabel("<html><i>NOTE: For corrections please email records@slu.edu.ph</i></html>");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        noteLabel.setForeground(Color.GRAY);
        personalDetailsView.add(noteLabel);

        // Add buttons for each section
        personalDetailsView.add(Box.createRigidArea(new Dimension(0, 20)));
        personalDetailsView.add(createAddButtonsPanel());

        personalDetailsView.revalidate();
        personalDetailsView.repaint();
    }

    private JPanel createAddButtonsPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton addGeneralButton = new JButton("Add General Info");
        JButton addContactButton = new JButton("Add Contact Info");
        JButton addPersonButton = new JButton("Add Contact Person");

        addGeneralButton.setBackground(new Color(72, 187, 120));
        addGeneralButton.setForeground(Color.WHITE);
        addContactButton.setBackground(new Color(72, 187, 120));
        addContactButton.setForeground(Color.WHITE);
        addPersonButton.setBackground(new Color(72, 187, 120));
        addPersonButton.setForeground(Color.WHITE);

        addGeneralButton.addActionListener(e -> showAddInfoDialog("general"));
        addContactButton.addActionListener(e -> showAddInfoDialog("contact"));
        addPersonButton.addActionListener(e -> showAddInfoDialog("persons"));

        buttonPanel.add(addGeneralButton);
        buttonPanel.add(addContactButton);
        buttonPanel.add(addPersonButton);

        return buttonPanel;
    }

    private JPanel createInfoFieldWithButtons(String label, String value, String section) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        labelComponent.setPreferredSize(new Dimension(200, 20));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonsPanel.setBackground(Color.WHITE);

        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        editButton.setPreferredSize(new Dimension(60, 25));
        deleteButton.setPreferredSize(new Dimension(70, 25));

        editButton.setBackground(new Color(66, 153, 225));
        editButton.setForeground(Color.WHITE);
        editButton.setBorderPainted(false);
        editButton.setFont(new Font("Arial", Font.PLAIN, 10));

        deleteButton.setBackground(new Color(245, 101, 101));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorderPainted(false);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 10));

        editButton.addActionListener(e -> editInfoItem(label, value, section));
        deleteButton.addActionListener(e -> deleteInfoItem(label, section));

        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createAccountInfoView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createSectionHeader("ACCOUNT INFORMATION"));
        panel.add(createInfoField("User ID/Login ID:", studentData.userId));
        panel.add(createInfoField("Account Name:", studentData.accountName));
        panel.add(createInfoField("Date Registered:", studentData.dateRegistered));
        panel.add(createInfoField("Account Type:", studentData.accountType));

        return panel;
    }

    private JPanel createChangePasswordView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createSectionHeader("CHANGE PASSWORD"));

        // Old password
        JPanel oldPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        oldPasswordPanel.setBackground(Color.WHITE);
        oldPasswordPanel.add(new JLabel("Old Password:"));
        JPasswordField oldPasswordField = new JPasswordField(20);
        oldPasswordPanel.add(oldPasswordField);
        panel.add(oldPasswordPanel);

        // New password
        JPanel newPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newPasswordPanel.setBackground(Color.WHITE);
        newPasswordPanel.add(new JLabel("New Password:"));
        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordPanel.add(newPasswordField);
        panel.add(newPasswordPanel);

        // Confirm password
        JPanel confirmPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        confirmPasswordPanel.setBackground(Color.WHITE);
        confirmPasswordPanel.add(new JLabel("Confirm Password:"));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        confirmPasswordPanel.add(confirmPasswordField);
        panel.add(confirmPasswordPanel);

        // Change password button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        JButton changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBackground(new Color(66, 153, 225));
        changePasswordBtn.setForeground(Color.WHITE);
        changePasswordBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Password changed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(changePasswordBtn);
        panel.add(buttonPanel);

        return panel;
    }

    private JLabel createSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(26, 54, 93));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    private JLabel createSubSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(45, 90, 135));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private JPanel createInfoField(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        labelComponent.setPreferredSize(new Dimension(200, 20));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.CENTER);

        return panel;
    }

    private void showPersonalDetails() {
        detailsCardLayout.show(detailsPanel, "personal");
    }

    private void showAccountInfo() {
        detailsCardLayout.show(detailsPanel, "account");
    }

    private void showChangePassword() {
        detailsCardLayout.show(detailsPanel, "password");
    }

    private void showAddInfoDialog(String section) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField labelField = new JTextField(20);
        JTextField valueField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Field Name:"), gbc);

        gbc.gridx = 1;
        panel.add(labelField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Value:"), gbc);

        gbc.gridx = 1;
        panel.add(valueField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Information", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String label = labelField.getText().trim();
            String value = valueField.getText().trim();

            if (!label.isEmpty() && !value.isEmpty()) {
                addInfoItem(label, value, section);
                refreshPersonalDetailsView();
                JOptionPane.showMessageDialog(this, "Information added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editInfoItem(String label, String currentValue, String section) {
        String newValue = JOptionPane.showInputDialog(this,
                "Edit " + label + ":", currentValue);

        if (newValue != null && !newValue.trim().isEmpty()) {
            updateInfoItem(label.replace(":", ""), newValue.trim(), section);
            refreshPersonalDetailsView();
            JOptionPane.showMessageDialog(this, "Information updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteInfoItem(String label, String section) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + label + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            removeInfoItem(label.replace(":", ""), section);
            refreshPersonalDetailsView();
            JOptionPane.showMessageDialog(this, "Information deleted successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addInfoItem(String label, String value, String section) {
        switch (section) {
            case "general":
                studentData.generalInfo.put(label, value);
                break;
            case "contact":
                studentData.contactInfo.put(label, value);
                break;
            case "persons":
                studentData.contactPersons.put(label, value);
                break;
        }
    }

    private void updateInfoItem(String label, String value, String section) {
        switch (section) {
            case "general":
                if (studentData.generalInfo.containsKey(label)) {
                    studentData.generalInfo.put(label, value);
                }
                break;
            case "contact":
                if (studentData.contactInfo.containsKey(label)) {
                    studentData.contactInfo.put(label, value);
                }
                break;
            case "persons":
                if (studentData.contactPersons.containsKey(label)) {
                    studentData.contactPersons.put(label, value);
                }
                break;
        }
    }

    private void removeInfoItem(String label, String section) {
        switch (section) {
            case "general":
                studentData.generalInfo.remove(label);
                break;
            case "contact":
                studentData.contactInfo.remove(label);
                break;
            case "persons":
                studentData.contactPersons.remove(label);
                break;
        }
    }
}
package IT212prelims_LabProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Medical Record Panel Class
public class MedicalRecordPanel extends JPanel {
    private MedicalRecordData medicalData;
    private JPanel contentPanel;
    private CardLayout contentCardLayout;

    // Current view panels
    private JPanel personalInfoView;
    private JPanel allergiesView;
    private JPanel pastMedicalView;
    private JPanel childhoodDiseasesView;
    private JPanel personalSocialView;
    private JPanel immunizationView;
    private JPanel othersView;
    private JPanel consultationView;

    public MedicalRecordPanel() {
        this.medicalData = new MedicalRecordData();
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

        JLabel headerLabel = new JLabel("Medical Record");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(26, 54, 93));
        headerPanel.add(headerLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Main content area
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 245, 245));

        // Left sidebar navigation
        JPanel leftNav = createMedicalNavigation();
        mainContent.add(leftNav, BorderLayout.WEST);

        // Right content area
        contentCardLayout = new CardLayout();
        contentPanel = new JPanel(contentCardLayout);

        // Add all views
        contentPanel.add(createPersonalInformationView(), "personal");
        contentPanel.add(createAllergiesView(), "allergies");
        contentPanel.add(createPastMedicalView(), "pastmedical");
        contentPanel.add(createChildhoodDiseasesView(), "childhood");
        contentPanel.add(createPersonalSocialView(), "personalsocial");
        contentPanel.add(createImmunizationView(), "immunization");
        contentPanel.add(createOthersView(), "others");
        contentPanel.add(createConsultationView(), "consultation");

        mainContent.add(contentPanel, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        // Show personal information by default
        contentCardLayout.show(contentPanel, "personal");
    }

    private JPanel createMedicalNavigation() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        navPanel.setPreferredSize(new Dimension(250, 0));

        String[] navItems = {
                "Personal Information", "Allergies", "Past Medical History",
                "Childhood Diseases", "Personal/Social History", "Immunization",
                "Others", "Consultation Record"
        };

        String[] navKeys = {
                "personal", "allergies", "pastmedical", "childhood",
                "personalsocial", "immunization", "others", "consultation"
        };

        for (int i = 0; i < navItems.length; i++) {
            JButton navButton = createMedicalNavButton(navItems[i], navKeys[i]);
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        return navPanel;
    }

    private JButton createMedicalNavButton(String text, String key) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(230, 35));
        button.setBackground(new Color(66, 153, 225));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 11));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 90, 135));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(66, 153, 225));
            }
        });

        button.addActionListener(e -> {
            contentCardLayout.show(contentPanel, key);
            refreshCurrentView(key);
        });

        return button;
    }

    private void refreshCurrentView(String key) {
        switch (key) {
            case "personal":
                refreshPersonalInfoView();
                break;
            case "allergies":
                refreshAllergiesView();
                break;
            case "pastmedical":
                refreshPastMedicalView();
                break;
            case "childhood":
                refreshChildhoodDiseasesView();
                break;
            case "personalsocial":
                refreshPersonalSocialView();
                break;
            case "immunization":
                refreshImmunizationView();
                break;
            case "others":
                refreshOthersView();
                break;
            case "consultation":
                refreshConsultationView();
                break;
        }
    }

    // Helper method to create section headers
    private JLabel createSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(26, 54, 93));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    // Helper method to create small buttons
    private JButton createSmallButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(60, 25));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        return button;
    }

    // Helper method to create editable fields
    private JPanel createEditableField(String label, String value, String section, String fieldName) {
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

        JButton editButton = createSmallButton("Edit", new Color(66, 153, 225));
        editButton.addActionListener(e -> editPersonalField(fieldName, value));

        buttonsPanel.add(editButton);

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    // Personal Information View
    private JScrollPane createPersonalInformationView() {
        personalInfoView = new JPanel();
        personalInfoView.setLayout(new BoxLayout(personalInfoView, BoxLayout.Y_AXIS));
        personalInfoView.setBackground(Color.WHITE);
        personalInfoView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshPersonalInfoView();

        JScrollPane scrollPane = new JScrollPane(personalInfoView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshPersonalInfoView() {
        personalInfoView.removeAll();

        personalInfoView.add(createSectionHeader("PERSONAL INFORMATION"));

        // Name fields
        personalInfoView.add(createEditableField("Last Name", medicalData.lastName, "personal", "lastName"));
        personalInfoView.add(createEditableField("First Name", medicalData.firstName, "personal", "firstName"));
        personalInfoView.add(createEditableField("Middle Name", medicalData.middleName, "personal", "middleName"));
        personalInfoView.add(createEditableField("Date of Birth", medicalData.dateOfBirth, "personal", "dateOfBirth"));
        personalInfoView.add(createEditableField("Gender", medicalData.gender, "personal", "gender"));
        personalInfoView.add(createEditableField("Address", medicalData.address, "personal", "address"));
        personalInfoView.add(createEditableField("Contact Number", medicalData.contactNumber, "personal", "contactNumber"));
        personalInfoView.add(createEditableField("Course/Year", medicalData.courseYear, "personal", "courseYear"));

        personalInfoView.add(Box.createRigidArea(new Dimension(0, 20)));
        personalInfoView.add(createSectionHeader("PERSON TO CONTACT IN CASE OF EMERGENCY"));
        personalInfoView.add(createEditableField("Emergency Contact Name", medicalData.emergencyName, "personal", "emergencyName"));
        personalInfoView.add(createEditableField("Emergency Contact Address", medicalData.emergencyAddress, "personal", "emergencyAddress"));
        personalInfoView.add(createEditableField("Emergency Contact Number", medicalData.emergencyContactNumber, "personal", "emergencyContactNumber"));

        personalInfoView.revalidate();
        personalInfoView.repaint();
    }

    private void editPersonalField(String fieldName, String currentValue) {
        String newValue = JOptionPane.showInputDialog(this,
                "Edit " + fieldName + ":", currentValue);

        if (newValue != null && !newValue.trim().isEmpty()) {
            // Update the data based on field name
            switch (fieldName) {
                case "lastName": medicalData.lastName = newValue.trim(); break;
                case "firstName": medicalData.firstName = newValue.trim(); break;
                case "middleName": medicalData.middleName = newValue.trim(); break;
                case "dateOfBirth": medicalData.dateOfBirth = newValue.trim(); break;
                case "gender": medicalData.gender = newValue.trim(); break;
                case "address": medicalData.address = newValue.trim(); break;
                case "contactNumber": medicalData.contactNumber = newValue.trim(); break;
                case "courseYear": medicalData.courseYear = newValue.trim(); break;
                case "emergencyName": medicalData.emergencyName = newValue.trim(); break;
                case "emergencyAddress": medicalData.emergencyAddress = newValue.trim(); break;
                case "emergencyContactNumber": medicalData.emergencyContactNumber = newValue.trim(); break;
            }
            refreshPersonalInfoView();
            JOptionPane.showMessageDialog(this, "Information updated successfully!");
        }
    }

    // Allergies View
    private JScrollPane createAllergiesView() {
        allergiesView = new JPanel();
        allergiesView.setLayout(new BoxLayout(allergiesView, BoxLayout.Y_AXIS));
        allergiesView.setBackground(Color.WHITE);
        allergiesView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshAllergiesView();

        JScrollPane scrollPane = new JScrollPane(allergiesView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshAllergiesView() {
        allergiesView.removeAll();

        allergiesView.add(createSectionHeader("ALLERGIES (Medication, food, and others)"));

        // Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        JButton addAllergyBtn = new JButton("Add Allergy");
        addAllergyBtn.setBackground(new Color(72, 187, 120));
        addAllergyBtn.setForeground(Color.WHITE);
        addAllergyBtn.addActionListener(e -> addAllergy());
        addButtonPanel.add(addAllergyBtn);
        allergiesView.add(addButtonPanel);

        allergiesView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Table headers
        if (!medicalData.allergies.isEmpty()) {
            JPanel headerPanel = new JPanel(new GridLayout(1, 3));
            headerPanel.setBackground(new Color(230, 230, 230));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel allergyHeader = new JLabel("Allergies");
            allergyHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel detailsHeader = new JLabel("Details");
            detailsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel actionsHeader = new JLabel("Actions");
            actionsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            headerPanel.add(allergyHeader);
            headerPanel.add(detailsHeader);
            headerPanel.add(actionsHeader);
            allergiesView.add(headerPanel);

            // Allergy entries
            for (Map.Entry<String, String> entry : medicalData.allergies.entrySet()) {
                allergiesView.add(createAllergyRow(entry.getKey(), entry.getValue()));
            }
        } else {
            JLabel noDataLabel = new JLabel("No allergies recorded");
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noDataLabel.setForeground(Color.GRAY);
            allergiesView.add(noDataLabel);
        }

        allergiesView.revalidate();
        allergiesView.repaint();
    }

    private JPanel createAllergyRow(String allergy, String details) {
        JPanel row = new JPanel(new GridLayout(1, 3));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        row.add(new JLabel(allergy));
        row.add(new JLabel(details));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
        JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

        editBtn.addActionListener(e -> editAllergy(allergy, details));
        deleteBtn.addActionListener(e -> deleteAllergy(allergy));

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        row.add(buttonPanel);

        return row;
    }

    private void addAllergy() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField allergyField = new JTextField(20);
        JTextField detailsField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Allergy:"), gbc);

        gbc.gridx = 1;
        panel.add(allergyField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Details:"), gbc);

        gbc.gridx = 1;
        panel.add(detailsField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Allergy", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String allergy = allergyField.getText().trim();
            String details = detailsField.getText().trim();

            if (!allergy.isEmpty() && !details.isEmpty()) {
                medicalData.allergies.put(allergy, details);
                refreshAllergiesView();
                JOptionPane.showMessageDialog(this, "Allergy added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editAllergy(String allergy, String currentDetails) {
        String newDetails = JOptionPane.showInputDialog(this,
                "Edit details for " + allergy + ":", currentDetails);

        if (newDetails != null && !newDetails.trim().isEmpty()) {
            medicalData.allergies.put(allergy, newDetails.trim());
            refreshAllergiesView();
            JOptionPane.showMessageDialog(this, "Allergy updated successfully!");
        }
    }

    private void deleteAllergy(String allergy) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the allergy: " + allergy + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            medicalData.allergies.remove(allergy);
            refreshAllergiesView();
            JOptionPane.showMessageDialog(this, "Allergy deleted successfully!");
        }
    }

    // Past Medical History View
    private JScrollPane createPastMedicalView() {
        pastMedicalView = new JPanel();
        pastMedicalView.setLayout(new BoxLayout(pastMedicalView, BoxLayout.Y_AXIS));
        pastMedicalView.setBackground(Color.WHITE);
        pastMedicalView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshPastMedicalView();

        JScrollPane scrollPane = new JScrollPane(pastMedicalView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshPastMedicalView() {
        pastMedicalView.removeAll();

        pastMedicalView.add(createSectionHeader("PAST MEDICAL HISTORY"));
        JLabel questionLabel = new JLabel("Do you have or have been treated for any sickness? Please SELECT");
        questionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        pastMedicalView.add(questionLabel);

        // Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        JButton addSicknessBtn = new JButton("Add Medical History");
        addSicknessBtn.setBackground(new Color(72, 187, 120));
        addSicknessBtn.setForeground(Color.WHITE);
        addSicknessBtn.addActionListener(e -> addMedicalHistory());
        addButtonPanel.add(addSicknessBtn);
        pastMedicalView.add(addButtonPanel);

        pastMedicalView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Table headers and entries
        if (!medicalData.pastMedicalHistory.isEmpty()) {
            JPanel headerPanel = new JPanel(new GridLayout(1, 3));
            headerPanel.setBackground(new Color(230, 230, 230));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel sicknessHeader = new JLabel("Sickness");
            sicknessHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel detailsHeader = new JLabel("Details");
            detailsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel actionsHeader = new JLabel("Actions");
            actionsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            headerPanel.add(sicknessHeader);
            headerPanel.add(detailsHeader);
            headerPanel.add(actionsHeader);
            pastMedicalView.add(headerPanel);

            // Medical history entries
            for (Map.Entry<String, String> entry : medicalData.pastMedicalHistory.entrySet()) {
                pastMedicalView.add(createMedicalHistoryRow(entry.getKey(), entry.getValue()));
            }
        } else {
            JLabel noDataLabel = new JLabel("No past medical history recorded");
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noDataLabel.setForeground(Color.GRAY);
            pastMedicalView.add(noDataLabel);
        }

        pastMedicalView.revalidate();
        pastMedicalView.repaint();
    }

    private JPanel createMedicalHistoryRow(String sickness, String details) {
        JPanel row = new JPanel(new GridLayout(1, 3));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        row.add(new JLabel(sickness));
        row.add(new JLabel(details));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
        JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

        editBtn.addActionListener(e -> editMedicalHistory(sickness, details));
        deleteBtn.addActionListener(e -> deleteMedicalHistory(sickness));

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        row.add(buttonPanel);

        return row;
    }

    private void addMedicalHistory() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField sicknessField = new JTextField(20);
        JTextField detailsField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Sickness:"), gbc);

        gbc.gridx = 1;
        panel.add(sicknessField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Details:"), gbc);

        gbc.gridx = 1;
        panel.add(detailsField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add Medical History", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String sickness = sicknessField.getText().trim();
            String details = detailsField.getText().trim();

            if (!sickness.isEmpty() && !details.isEmpty()) {
                medicalData.pastMedicalHistory.put(sickness, details);
                refreshPastMedicalView();
                JOptionPane.showMessageDialog(this, "Medical history added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editMedicalHistory(String sickness, String currentDetails) {
        String newDetails = JOptionPane.showInputDialog(this,
                "Edit details for " + sickness + ":", currentDetails);

        if (newDetails != null && !newDetails.trim().isEmpty()) {
            medicalData.pastMedicalHistory.put(sickness, newDetails.trim());
            refreshPastMedicalView();
            JOptionPane.showMessageDialog(this, "Medical history updated successfully!");
        }
    }

    private void deleteMedicalHistory(String sickness) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the medical history: " + sickness + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            medicalData.pastMedicalHistory.remove(sickness);
            refreshPastMedicalView();
            JOptionPane.showMessageDialog(this, "Medical history deleted successfully!");
        }
    }

    // Childhood Diseases View
    private JScrollPane createChildhoodDiseasesView() {
        childhoodDiseasesView = new JPanel();
        childhoodDiseasesView.setLayout(new BoxLayout(childhoodDiseasesView, BoxLayout.Y_AXIS));
        childhoodDiseasesView.setBackground(Color.WHITE);
        childhoodDiseasesView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshChildhoodDiseasesView();

        JScrollPane scrollPane = new JScrollPane(childhoodDiseasesView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshChildhoodDiseasesView() {
        childhoodDiseasesView.removeAll();

        childhoodDiseasesView.add(createSectionHeader("CHILDHOOD DISEASES"));

        // Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        JButton addDiseaseBtn = new JButton("Add Childhood Disease");
        addDiseaseBtn.setBackground(new Color(72, 187, 120));
        addDiseaseBtn.setForeground(Color.WHITE);
        addDiseaseBtn.addActionListener(e -> addChildhoodDisease());
        addButtonPanel.add(addDiseaseBtn);
        childhoodDiseasesView.add(addButtonPanel);

        childhoodDiseasesView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Disease entries
        if (!medicalData.childhoodDiseases.isEmpty()) {
            for (String disease : medicalData.childhoodDiseases) {
                childhoodDiseasesView.add(createChildhoodDiseaseRow(disease));
            }
        } else {
            JLabel noDataLabel = new JLabel("No childhood diseases recorded");
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noDataLabel.setForeground(Color.GRAY);
            childhoodDiseasesView.add(noDataLabel);
        }

        childhoodDiseasesView.revalidate();
        childhoodDiseasesView.repaint();
    }

    private JPanel createChildhoodDiseaseRow(String disease) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel diseaseLabel = new JLabel(disease);
        diseaseLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        row.add(diseaseLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
        JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

        editBtn.addActionListener(e -> editChildhoodDisease(disease));
        deleteBtn.addActionListener(e -> deleteChildhoodDisease(disease));

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        row.add(buttonPanel, BorderLayout.EAST);

        return row;
    }

    private void addChildhoodDisease() {
        String disease = JOptionPane.showInputDialog(this, "Enter childhood disease:");

        if (disease != null && !disease.trim().isEmpty()) {
            disease = disease.trim();
            if (!medicalData.childhoodDiseases.contains(disease)) {
                medicalData.childhoodDiseases.add(disease);
                refreshChildhoodDiseasesView();
                JOptionPane.showMessageDialog(this, "Childhood disease added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "This disease is already in the list.",
                        "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void editChildhoodDisease(String oldDisease) {
        String newDisease = JOptionPane.showInputDialog(this,
                "Edit childhood disease:", oldDisease);

        if (newDisease != null && !newDisease.trim().isEmpty()) {
            newDisease = newDisease.trim();
            int index = medicalData.childhoodDiseases.indexOf(oldDisease);
            if (index != -1) {
                medicalData.childhoodDiseases.set(index, newDisease);
                refreshChildhoodDiseasesView();
                JOptionPane.showMessageDialog(this, "Childhood disease updated successfully!");
            }
        }
    }

    private void deleteChildhoodDisease(String disease) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the childhood disease: " + disease + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            medicalData.childhoodDiseases.remove(disease);
            refreshChildhoodDiseasesView();
            JOptionPane.showMessageDialog(this, "Childhood disease deleted successfully!");
        }
    }

    // Personal/Social History View
    private JScrollPane createPersonalSocialView() {
        personalSocialView = new JPanel();
        personalSocialView.setLayout(new BoxLayout(personalSocialView, BoxLayout.Y_AXIS));
        personalSocialView.setBackground(Color.WHITE);
        personalSocialView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshPersonalSocialView();

        JScrollPane scrollPane = new JScrollPane(personalSocialView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshPersonalSocialView() {
        personalSocialView.removeAll();

        personalSocialView.add(createSectionHeader("PERSONAL/SOCIAL HISTORY"));

        // Smoking section
        JPanel smokingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        smokingPanel.setBackground(Color.WHITE);
        JCheckBox smokingCheckbox = new JCheckBox("Cigarette Smoker:");
        smokingCheckbox.setSelected(medicalData.isSmoker);
        smokingCheckbox.setBackground(Color.WHITE);
        JTextField sticksField = new JTextField(medicalData.sticksPerDay, 5);
        JLabel sticksLabel = new JLabel("sticks/day");

        smokingPanel.add(smokingCheckbox);
        smokingPanel.add(sticksField);
        smokingPanel.add(sticksLabel);

        JButton updateSmokingBtn = createSmallButton("Update", new Color(66, 153, 225));
        updateSmokingBtn.addActionListener(e -> {
            medicalData.isSmoker = smokingCheckbox.isSelected();
            medicalData.sticksPerDay = sticksField.getText();
            JOptionPane.showMessageDialog(this, "Smoking information updated successfully!");
        });
        smokingPanel.add(updateSmokingBtn);

        personalSocialView.add(smokingPanel);

        personalSocialView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Drinking section
        JPanel drinkingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        drinkingPanel.setBackground(Color.WHITE);
        JCheckBox drinkingCheckbox = new JCheckBox("Alcoholic Beverage Drinker:");
        drinkingCheckbox.setSelected(medicalData.isDrinker);
        drinkingCheckbox.setBackground(Color.WHITE);

        JButton updateDrinkingBtn = createSmallButton("Update", new Color(66, 153, 225));
        updateDrinkingBtn.addActionListener(e -> {
            medicalData.isDrinker = drinkingCheckbox.isSelected();
            JOptionPane.showMessageDialog(this, "Drinking information updated successfully!");
        });

        drinkingPanel.add(drinkingCheckbox);
        drinkingPanel.add(updateDrinkingBtn);

        personalSocialView.add(drinkingPanel);

        personalSocialView.revalidate();
        personalSocialView.repaint();
    }

    // Immunization View
    private JScrollPane createImmunizationView() {
        immunizationView = new JPanel();
        immunizationView.setLayout(new BoxLayout(immunizationView, BoxLayout.Y_AXIS));
        immunizationView.setBackground(Color.WHITE);
        immunizationView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshImmunizationView();

        JScrollPane scrollPane = new JScrollPane(immunizationView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshImmunizationView() {
        immunizationView.removeAll();

        immunizationView.add(createSectionHeader("IMMUNIZATIONS"));

        // Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        JButton addImmunizationBtn = new JButton("Add Immunization");
        addImmunizationBtn.setBackground(new Color(72, 187, 120));
        addImmunizationBtn.setForeground(Color.WHITE);
        addImmunizationBtn.addActionListener(e -> addImmunization());
        addButtonPanel.add(addImmunizationBtn);
        immunizationView.add(addButtonPanel);

        immunizationView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Table headers and entries
        if (!medicalData.immunizations.isEmpty()) {
            JPanel headerPanel = new JPanel(new GridLayout(1, 3));
            headerPanel.setBackground(new Color(230, 230, 230));
            headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JLabel immunizationHeader = new JLabel("Immunizations");
            immunizationHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel dateHeader = new JLabel("Date");
            dateHeader.setFont(new Font("Arial", Font.BOLD, 12));
            JLabel actionsHeader = new JLabel("Actions");
            actionsHeader.setFont(new Font("Arial", Font.BOLD, 12));
            headerPanel.add(immunizationHeader);
            headerPanel.add(dateHeader);
            headerPanel.add(actionsHeader);
            immunizationView.add(headerPanel);

            // Immunization entries
            for (Map.Entry<String, String> entry : medicalData.immunizations.entrySet()) {
                immunizationView.add(createImmunizationRow(entry.getKey(), entry.getValue()));
            }
        } else {
            JLabel noDataLabel = new JLabel("No immunizations recorded");
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noDataLabel.setForeground(Color.GRAY);
            immunizationView.add(noDataLabel);
        }

        immunizationView.revalidate();
        immunizationView.repaint();
    }

    private JPanel createImmunizationRow(String immunization, String date) {
        JPanel row = new JPanel(new GridLayout(1, 3));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        row.add(new JLabel(immunization));
        row.add(new JLabel(date));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
        JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

        editBtn.addActionListener(e -> editImmunization(immunization, date));
        deleteBtn.addActionListener(e -> deleteImmunization(immunization));

        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        row.add(buttonPanel);

        return row;
    }

    private void addImmunization() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField immunizationField = new JTextField(20);
        JTextField dateField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(new JLabel("Immunization:"), gbc);

        gbc.gridx = 1;
        panel.add(immunizationField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Date:"), gbc);

        gbc.gridx = 1;
        panel.add(dateField, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Add New Immunization", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String immunization = immunizationField.getText().trim();
            String date = dateField.getText().trim();

            if (!immunization.isEmpty() && !date.isEmpty()) {
                medicalData.immunizations.put(immunization, date);
                refreshImmunizationView();
                JOptionPane.showMessageDialog(this, "Immunization added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editImmunization(String immunization, String currentDate) {
        String newDate = JOptionPane.showInputDialog(this,
                "Edit date for " + immunization + ":", currentDate);

        if (newDate != null && !newDate.trim().isEmpty()) {
            medicalData.immunizations.put(immunization, newDate.trim());
            refreshImmunizationView();
            JOptionPane.showMessageDialog(this, "Immunization updated successfully!");
        }
    }

    private void deleteImmunization(String immunization) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the immunization: " + immunization + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            medicalData.immunizations.remove(immunization);
            refreshImmunizationView();
            JOptionPane.showMessageDialog(this, "Immunization deleted successfully!");
        }
    }

    // Others View
    private JScrollPane createOthersView() {
        othersView = new JPanel();
        othersView.setLayout(new BoxLayout(othersView, BoxLayout.Y_AXIS));
        othersView.setBackground(Color.WHITE);
        othersView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshOthersView();

        JScrollPane scrollPane = new JScrollPane(othersView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshOthersView() {
        othersView.removeAll();

        othersView.add(createSectionHeader("OTHERS"));

        // Special medications
        JPanel medicationsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        medicationsPanel.setBackground(Color.WHITE);
        medicationsPanel.add(new JLabel("Any special medications?"));
        JTextField medicationsField = new JTextField(medicalData.specialMedications, 20);
        medicationsPanel.add(medicationsField);
        othersView.add(medicationsPanel);

        othersView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Special care
        JPanel specialCarePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        specialCarePanel.setBackground(Color.WHITE);
        specialCarePanel.add(new JLabel("Requires Special Care?"));
        JTextField specialCareField = new JTextField(medicalData.specialCare, 20);
        specialCarePanel.add(specialCareField);
        othersView.add(specialCarePanel);

        othersView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Others
        JPanel othersFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        othersFieldPanel.setBackground(Color.WHITE);
        othersFieldPanel.add(new JLabel("Others:"));
        JTextField othersField = new JTextField(medicalData.others, 20);
        othersFieldPanel.add(othersField);
        othersView.add(othersFieldPanel);

        othersView.add(Box.createRigidArea(new Dimension(0, 20)));

        // Update button
        JPanel updateButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        updateButtonPanel.setBackground(Color.WHITE);
        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(new Color(66, 153, 225));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.addActionListener(e -> {
            medicalData.specialMedications = medicationsField.getText();
            medicalData.specialCare = specialCareField.getText();
            medicalData.others = othersField.getText();
            JOptionPane.showMessageDialog(this, "Information updated successfully!");
        });
        updateButtonPanel.add(updateBtn);
        othersView.add(updateButtonPanel);

        othersView.revalidate();
        othersView.repaint();
    }

    // Consultation Record View
    private JScrollPane createConsultationView() {
        consultationView = new JPanel();
        consultationView.setLayout(new BoxLayout(consultationView, BoxLayout.Y_AXIS));
        consultationView.setBackground(Color.WHITE);
        consultationView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        refreshConsultationView();

        JScrollPane scrollPane = new JScrollPane(consultationView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void refreshConsultationView() {
        consultationView.removeAll();

        consultationView.add(createSectionHeader("CONSULTATION RECORD"));

        // Add button
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(Color.WHITE);
        JButton addConsultationBtn = new JButton("Add Consultation Record");
        addConsultationBtn.setBackground(new Color(72, 187, 120));
        addConsultationBtn.setForeground(Color.WHITE);
        addConsultationBtn.addActionListener(e -> addConsultationRecord());
        addButtonPanel.add(addConsultationBtn);
        consultationView.add(addButtonPanel);

        consultationView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create table for consultation records
        String[] columnNames = {"Date/Time", "Age", "BMI", "BP", "TEMP", "RR", "PR", "SPO2", "Remarks", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only the Actions column is editable (for buttons)
            }
        };

        // Add consultation records to table
        for (int i = 0; i < medicalData.consultationRecords.size(); i++) {
            MedicalRecordData.ConsultationRecord record = medicalData.consultationRecords.get(i);
            Object[] rowData = {
                    record.dateTime, record.age, record.bmi, record.bloodPressure,
                    record.temperature, record.respiratoryRate, record.pulseRate,
                    record.oxygenSaturation, record.remarks, "Edit | Delete"
            };
            tableModel.addRow(rowData);
        }

        JTable consultationTable = new JTable(tableModel);
        consultationTable.setRowHeight(30);
        consultationTable.getColumnModel().getColumn(9).setPreferredWidth(120);

        // Add mouse listener for edit/delete actions
        consultationTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = consultationTable.rowAtPoint(evt.getPoint());
                int col = consultationTable.columnAtPoint(evt.getPoint());

                if (col == 9 && row >= 0) { // Actions column
                    showConsultationActionsDialog(row);
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(consultationTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 200));
        consultationView.add(tableScrollPane);

        if (medicalData.consultationRecords.isEmpty()) {
            JLabel noDataLabel = new JLabel("No consultation records found");
            noDataLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            noDataLabel.setForeground(Color.GRAY);
            consultationView.add(noDataLabel);
        }

        consultationView.revalidate();
        consultationView.repaint();
    }

    private void showConsultationActionsDialog(int recordIndex) {
        String[] options = {"Edit", "Delete", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
                "What would you like to do with this consultation record?",
                "Consultation Record Actions",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);

        switch (choice) {
            case 0: // Edit
                editConsultationRecord(recordIndex);
                break;
            case 1: // Delete
                deleteConsultationRecord(recordIndex);
                break;
            // case 2 or default: Cancel - do nothing
        }
    }

    private void addConsultationRecord() {
        ConsultationRecordDialog dialog = new ConsultationRecordDialog(null, null);
        dialog.setVisible(true);

        if (dialog.getResult() != null) {
            medicalData.consultationRecords.add(dialog.getResult());
            refreshConsultationView();
            JOptionPane.showMessageDialog(this, "Consultation record added successfully!");
        }
    }

    private void editConsultationRecord(int index) {
        if (index >= 0 && index < medicalData.consultationRecords.size()) {
            MedicalRecordData.ConsultationRecord record = medicalData.consultationRecords.get(index);
            ConsultationRecordDialog dialog = new ConsultationRecordDialog(null, record);
            dialog.setVisible(true);

            if (dialog.getResult() != null) {
                medicalData.consultationRecords.set(index, dialog.getResult());
                refreshConsultationView();
                JOptionPane.showMessageDialog(this, "Consultation record updated successfully!");
            }
        }
    }

    private void deleteConsultationRecord(int index) {
        if (index >= 0 && index < medicalData.consultationRecords.size()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this consultation record?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                medicalData.consultationRecords.remove(index);
                refreshConsultationView();
                JOptionPane.showMessageDialog(this, "Consultation record deleted successfully!");
            }
        }
    }
}
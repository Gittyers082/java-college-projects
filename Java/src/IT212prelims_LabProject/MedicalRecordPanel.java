package IT212prelims_LabProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;


class MedicalRecordData {
    // Personal Information
    public String lastName = "Lang Bao";
    public String firstName = "Lin Ling";
    public String middleName = "Xiao";
    public String dateOfBirth = "February 14, 2002";
    public String gender = "Non-Binary";
    public String address = "SIOPAO ST. SHANGHAI CITY, CHINA";
    public String contactNumber = "02502382057";
    public String courseYear = "BMMA 2";

    // Emergency Contact
    public String emergencyName = "";
    public String emergencyAddress = "";
    public String emergencyContactNumber = "";

    // Using MyMap (which uses MyLinkedList internally)
    public MyMap allergies;
    public MyMap pastMedicalHistory;
    public MyLinkedList<String> childhoodDiseases;
    public MyMap immunizations;
    public MyLinkedList<ConsultationRecord> consultationRecords;

    // Personal/Social History
    public boolean isSmoker = false;
    public String sticksPerDay = "0";
    public boolean isDrinker = false;

    // Others
    public String specialMedications = "";
    public String specialCare = "";
    public String others = "";

    public MedicalRecordData() {
        // Initialize using MyList implementations
        allergies = new MyMap();
        pastMedicalHistory = new MyMap();
        childhoodDiseases = new MyLinkedList<>();
        immunizations = new MyMap();
        consultationRecords = new MyLinkedList<>();

        // Add some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        try {
            // Sample allergies
            allergies.put("Peanuts", "Severe allergic reaction");
            allergies.put("Dust", "Mild sneezing");

            // Sample past medical history
            pastMedicalHistory.put("Asthma", "Childhood asthma, treated with inhaler");

            // Sample childhood diseases
            childhoodDiseases.insert("Chicken Pox");
            childhoodDiseases.insert("Measles");

            // Sample immunizations
            immunizations.put("BCG", "2005-09-15");
            immunizations.put("Hepatitis B", "2005-10-08");
            immunizations.put("DPT", "2005-11-08");

            // Sample consultation record
            ConsultationRecord record = new ConsultationRecord();
            record.dateTime = "2024-01-15 10:30 AM";
            record.age = "18";
            record.bmi = "22.5";
            record.bloodPressure = "120/80";
            record.temperature = "36.5";
            record.respiratoryRate = "16";
            record.pulseRate = "72";
            record.oxygenSaturation = "98%";
            record.remarks = "General check-up, healthy condition";
            consultationRecords.insert(record);

        } catch (ListOverflowException e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }

    // Inner class for consultation records
    public static class ConsultationRecord {
        public String dateTime;
        public String age;
        public String bmi;
        public String bloodPressure;
        public String temperature;
        public String respiratoryRate;
        public String pulseRate;
        public String oxygenSaturation;
        public String remarks;

        public ConsultationRecord() {
            // Default constructor
        }

        public ConsultationRecord(String dateTime, String age, String bmi, String bloodPressure,
                                  String temperature, String respiratoryRate, String pulseRate,
                                  String oxygenSaturation, String remarks) {
            this.dateTime = dateTime;
            this.age = age;
            this.bmi = bmi;
            this.bloodPressure = bloodPressure;
            this.temperature = temperature;
            this.respiratoryRate = respiratoryRate;
            this.pulseRate = pulseRate;
            this.oxygenSaturation = oxygenSaturation;
            this.remarks = remarks;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            ConsultationRecord that = (ConsultationRecord) obj;
            return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
        }

        @Override
        public String toString() {
            return dateTime + " - " + remarks;
        }
    }
}

// Updated Medical Record Panel Class using MyList
class MedicalRecordPanel extends JPanel {
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

    // Helper methods
    private JLabel createSectionHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(26, 54, 93));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

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

    private JPanel createEditableField(String label, String value, String fieldName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));

        JLabel labelComponent = new JLabel(label + ":");
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        labelComponent.setPreferredSize(new Dimension(200, 20));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton editButton = createSmallButton("Edit", new Color(66, 153, 225));
        editButton.addActionListener(e -> editPersonalField(fieldName, value));

        panel.add(labelComponent, BorderLayout.WEST);
        panel.add(valueComponent, BorderLayout.CENTER);
        panel.add(editButton, BorderLayout.EAST);

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
        personalInfoView.add(createEditableField("Last Name", medicalData.lastName, "lastName"));
        personalInfoView.add(createEditableField("First Name", medicalData.firstName, "firstName"));
        personalInfoView.add(createEditableField("Middle Name", medicalData.middleName, "middleName"));
        personalInfoView.add(createEditableField("Date of Birth", medicalData.dateOfBirth, "dateOfBirth"));
        personalInfoView.add(createEditableField("Gender", medicalData.gender, "gender"));
        personalInfoView.add(createEditableField("Address", medicalData.address, "address"));
        personalInfoView.add(createEditableField("Contact Number", medicalData.contactNumber, "contactNumber"));
        personalInfoView.add(createEditableField("Course/Year", medicalData.courseYear, "courseYear"));

        personalInfoView.add(Box.createRigidArea(new Dimension(0, 20)));
        personalInfoView.add(createSectionHeader("PERSON TO CONTACT IN CASE OF EMERGENCY"));
        personalInfoView.add(createEditableField("Emergency Contact Name", medicalData.emergencyName, "emergencyName"));
        personalInfoView.add(createEditableField("Emergency Contact Address", medicalData.emergencyAddress, "emergencyAddress"));
        personalInfoView.add(createEditableField("Emergency Contact Number", medicalData.emergencyContactNumber, "emergencyContactNumber"));

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
        return new JScrollPane(allergiesView);
    }

    private void refreshAllergiesView() {
        allergiesView.removeAll();
        allergiesView.add(createSectionHeader("ALLERGIES (Medication, food, and others)"));

        JButton addBtn = new JButton("Add Allergy");
        addBtn.setBackground(new Color(72, 187, 120));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addAllergy());
        allergiesView.add(addBtn);

        allergiesView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Display allergies using MyMap
        Object[] allergiesList = medicalData.allergies.entrySet();
        if (allergiesList.length > 0) {
            for (Object obj : allergiesList) {
                if (obj instanceof KeyValuePair) {
                    KeyValuePair entry = (KeyValuePair) obj;
                    JPanel row = new JPanel(new BorderLayout());
                    row.setBackground(Color.WHITE);
                    row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    row.add(new JLabel(entry.getKey() + " - " + entry.getValue()), BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(Color.WHITE);

                    JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
                    JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

                    editBtn.addActionListener(e -> editAllergy(entry.getKey(), entry.getValue()));
                    deleteBtn.addActionListener(e -> deleteAllergy(entry.getKey()));

                    buttonPanel.add(editBtn);
                    buttonPanel.add(deleteBtn);
                    row.add(buttonPanel, BorderLayout.EAST);

                    allergiesView.add(row);
                }
            }
        } else {
            allergiesView.add(new JLabel("No allergies recorded"));
        }

        allergiesView.revalidate();
        allergiesView.repaint();
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
        return new JScrollPane(pastMedicalView);
    }

    private void refreshPastMedicalView() {
        pastMedicalView.removeAll();
        pastMedicalView.add(createSectionHeader("PAST MEDICAL HISTORY"));
        JLabel questionLabel = new JLabel("Do you have or have been treated for any sickness? Please SELECT");
        questionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        pastMedicalView.add(questionLabel);

        JButton addBtn = new JButton("Add Medical History");
        addBtn.setBackground(new Color(72, 187, 120));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addMedicalHistory());
        pastMedicalView.add(addBtn);

        pastMedicalView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Display medical history using MyMap
        Object[] historyList = medicalData.pastMedicalHistory.entrySet();
        if (historyList.length > 0) {
            for (Object obj : historyList) {
                if (obj instanceof KeyValuePair) {
                    KeyValuePair entry = (KeyValuePair) obj;
                    JPanel row = new JPanel(new BorderLayout());
                    row.setBackground(Color.WHITE);
                    row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    row.add(new JLabel(entry.getKey() + " - " + entry.getValue()), BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(Color.WHITE);

                    JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
                    JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

                    editBtn.addActionListener(e -> editMedicalHistory(entry.getKey(), entry.getValue()));
                    deleteBtn.addActionListener(e -> deleteMedicalHistory(entry.getKey()));

                    buttonPanel.add(editBtn);
                    buttonPanel.add(deleteBtn);
                    row.add(buttonPanel, BorderLayout.EAST);

                    pastMedicalView.add(row);
                }
            }
        } else {
            pastMedicalView.add(new JLabel("No past medical history recorded"));
        }

        pastMedicalView.revalidate();
        pastMedicalView.repaint();
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
        return new JScrollPane(childhoodDiseasesView);
    }

    private void refreshChildhoodDiseasesView() {
        childhoodDiseasesView.removeAll();
        childhoodDiseasesView.add(createSectionHeader("CHILDHOOD DISEASES"));

        JButton addBtn = new JButton("Add Childhood Disease");
        addBtn.setBackground(new Color(72, 187, 120));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addChildhoodDisease());
        childhoodDiseasesView.add(addBtn);

        childhoodDiseasesView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Display childhood diseases using MyLinkedList
        if (medicalData.childhoodDiseases.getSize() > 0) {
            Object[] diseaseArray = medicalData.childhoodDiseases.toArray();
            for (Object obj : diseaseArray) {
                if (obj instanceof String) {
                    String disease = (String) obj;
                    JPanel row = new JPanel(new BorderLayout());
                    row.setBackground(Color.WHITE);
                    row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    row.add(new JLabel("• " + disease), BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(Color.WHITE);

                    JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
                    JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

                    editBtn.addActionListener(e -> editChildhoodDisease(disease));
                    deleteBtn.addActionListener(e -> deleteChildhoodDisease(disease));

                    buttonPanel.add(editBtn);
                    buttonPanel.add(deleteBtn);
                    row.add(buttonPanel, BorderLayout.EAST);

                    childhoodDiseasesView.add(row);
                }
            }
        } else {
            childhoodDiseasesView.add(new JLabel("No childhood diseases recorded"));
        }

        childhoodDiseasesView.revalidate();
        childhoodDiseasesView.repaint();
    }

    private void addChildhoodDisease() {
        String disease = JOptionPane.showInputDialog(this, "Enter childhood disease:");

        if (disease != null && !disease.trim().isEmpty()) {
            try {
                disease = disease.trim();
                // Check if disease already exists
                if (medicalData.childhoodDiseases.search(disease) == -1) {
                    medicalData.childhoodDiseases.insert(disease);
                    refreshChildhoodDiseasesView();
                    JOptionPane.showMessageDialog(this, "Childhood disease added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "This disease is already in the list.",
                            "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                }
            } catch (ListOverflowException e) {
                JOptionPane.showMessageDialog(this, "Cannot add more diseases: " + e.getMessage(),
                        "List Full", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editChildhoodDisease(String oldDisease) {
        String newDisease = JOptionPane.showInputDialog(this,
                "Edit childhood disease:", oldDisease);

        if (newDisease != null && !newDisease.trim().isEmpty()) {
            newDisease = newDisease.trim();
            try {
                if (medicalData.childhoodDiseases.delete(oldDisease)) {
                    medicalData.childhoodDiseases.insert(newDisease);
                    refreshChildhoodDiseasesView();
                    JOptionPane.showMessageDialog(this, "Childhood disease updated successfully!");
                }
            } catch (ListOverflowException e) {
                JOptionPane.showMessageDialog(this, "Error updating disease: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteChildhoodDisease(String disease) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the childhood disease: " + disease + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            if (medicalData.childhoodDiseases.delete(disease)) {
                refreshChildhoodDiseasesView();
                JOptionPane.showMessageDialog(this, "Childhood disease deleted successfully!");
            }
        }
    }

    // Personal/Social History View
    private JScrollPane createPersonalSocialView() {
        personalSocialView = new JPanel();
        personalSocialView.setLayout(new BoxLayout(personalSocialView, BoxLayout.Y_AXIS));
        personalSocialView.setBackground(Color.WHITE);
        personalSocialView.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        refreshPersonalSocialView();
        return new JScrollPane(personalSocialView);
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
        return new JScrollPane(immunizationView);
    }

    private void refreshImmunizationView() {
        immunizationView.removeAll();
        immunizationView.add(createSectionHeader("IMMUNIZATIONS"));

        JButton addBtn = new JButton("Add Immunization");
        addBtn.setBackground(new Color(72, 187, 120));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addImmunization());
        immunizationView.add(addBtn);

        immunizationView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Display immunizations using MyMap
        Object[] immunizationList = medicalData.immunizations.entrySet();
        if (immunizationList.length > 0) {
            for (Object obj : immunizationList) {
                if (obj instanceof KeyValuePair) {
                    KeyValuePair entry = (KeyValuePair) obj;
                    JPanel row = new JPanel(new BorderLayout());
                    row.setBackground(Color.WHITE);
                    row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    row.add(new JLabel(entry.getKey() + " - " + entry.getValue()), BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.setBackground(Color.WHITE);

                    JButton editBtn = createSmallButton("Edit", new Color(66, 153, 225));
                    JButton deleteBtn = createSmallButton("Delete", new Color(245, 101, 101));

                    editBtn.addActionListener(e -> editImmunization(entry.getKey(), entry.getValue()));
                    deleteBtn.addActionListener(e -> deleteImmunization(entry.getKey()));

                    buttonPanel.add(editBtn);
                    buttonPanel.add(deleteBtn);
                    row.add(buttonPanel, BorderLayout.EAST);

                    immunizationView.add(row);
                }
            }
        } else {
            immunizationView.add(new JLabel("No immunizations recorded"));
        }

        immunizationView.revalidate();
        immunizationView.repaint();
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
        return new JScrollPane(othersView);
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
        return new JScrollPane(consultationView);
    }

    private void refreshConsultationView() {
        consultationView.removeAll();
        consultationView.add(createSectionHeader("CONSULTATION RECORD"));

        JButton addBtn = new JButton("Add Consultation Record");
        addBtn.setBackground(new Color(72, 187, 120));
        addBtn.setForeground(Color.WHITE);
        addBtn.addActionListener(e -> addConsultationRecord());
        consultationView.add(addBtn);

        consultationView.add(Box.createRigidArea(new Dimension(0, 10)));

        // Create table for consultation records using MyLinkedList
        String[] columnNames = {"Date/Time", "Age", "BMI", "BP", "TEMP", "RR", "PR", "SPO2", "Remarks", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only the Actions column is editable (for buttons)
            }
        };

        // Add consultation records to table using MyLinkedList
        Object[] recordsArray = medicalData.consultationRecords.toArray();
        for (int i = 0; i < recordsArray.length; i++) {
            if (recordsArray[i] instanceof MedicalRecordData.ConsultationRecord) {
                MedicalRecordData.ConsultationRecord record = (MedicalRecordData.ConsultationRecord) recordsArray[i];
                Object[] rowData = {
                        record.dateTime, record.age, record.bmi, record.bloodPressure,
                        record.temperature, record.respiratoryRate, record.pulseRate,
                        record.oxygenSaturation, record.remarks, "Edit | Delete"
                };
                tableModel.addRow(rowData);
            }
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

        if (medicalData.consultationRecords.getSize() == 0) {
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
            try {
                medicalData.consultationRecords.insert(dialog.getResult());
                refreshConsultationView();
                JOptionPane.showMessageDialog(this, "Consultation record added successfully!");
            } catch (ListOverflowException e) {
                JOptionPane.showMessageDialog(this, "Cannot add more consultation records: " + e.getMessage(),
                        "List Full", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editConsultationRecord(int index) {
        try {
            if (index >= 0 && index < medicalData.consultationRecords.getSize()) {
                MedicalRecordData.ConsultationRecord record = medicalData.consultationRecords.getElementAt(index);
                ConsultationRecordDialog dialog = new ConsultationRecordDialog(null, record);
                dialog.setVisible(true);

                if (dialog.getResult() != null) {
                    // Remove old record and add updated one
                    medicalData.consultationRecords.delete(record);
                    medicalData.consultationRecords.insert(dialog.getResult());
                    refreshConsultationView();
                    JOptionPane.showMessageDialog(this, "Consultation record updated successfully!");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Invalid record index: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ListOverflowException e) {
            JOptionPane.showMessageDialog(this, "Error updating record: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteConsultationRecord(int index) {
        try {
            if (index >= 0 && index < medicalData.consultationRecords.getSize()) {
                int result = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete this consultation record?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    MedicalRecordData.ConsultationRecord record = medicalData.consultationRecords.getElementAt(index);
                    medicalData.consultationRecords.delete(record);
                    refreshConsultationView();
                    JOptionPane.showMessageDialog(this, "Consultation record deleted successfully!");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Invalid record index: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Consultation Record Dialog for adding/editing consultation records (unchanged from previous version)
class ConsultationRecordDialog extends JDialog {
    private JTextField dateTimeField, ageField, bmiField, bpField;
    private JTextField tempField, rrField, prField, spo2Field, remarksField;
    private MedicalRecordData.ConsultationRecord result;
    private boolean cancelled = true;

    public ConsultationRecordDialog(JFrame parent, MedicalRecordData.ConsultationRecord record) {
        super(parent, record == null ? "Add Consultation Record" : "Edit Consultation Record", true);
        initializeDialog(record);
    }

    private void initializeDialog(MedicalRecordData.ConsultationRecord record) {
        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(getParent());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize fields
        dateTimeField = new JTextField(20);
        ageField = new JTextField(10);
        bmiField = new JTextField(10);
        bpField = new JTextField(10);
        tempField = new JTextField(10);
        rrField = new JTextField(10);
        prField = new JTextField(10);
        spo2Field = new JTextField(10);
        remarksField = new JTextField(30);

        // Populate fields if editing
        if (record != null) {
            dateTimeField.setText(record.dateTime);
            ageField.setText(record.age);
            bmiField.setText(record.bmi);
            bpField.setText(record.bloodPressure);
            tempField.setText(record.temperature);
            rrField.setText(record.respiratoryRate);
            prField.setText(record.pulseRate);
            spo2Field.setText(record.oxygenSaturation);
            remarksField.setText(record.remarks);
        }

        // Add form fields
        int row = 0;
        addFormField(formPanel, gbc, "Date/Time:", dateTimeField, row++);
        addFormField(formPanel, gbc, "Age:", ageField, row++);
        addFormField(formPanel, gbc, "BMI:", bmiField, row++);
        addFormField(formPanel, gbc, "Blood Pressure:", bpField, row++);
        addFormField(formPanel, gbc, "Temperature:", tempField, row++);
        addFormField(formPanel, gbc, "Respiratory Rate:", rrField, row++);
        addFormField(formPanel, gbc, "Pulse Rate:", prField, row++);
        addFormField(formPanel, gbc, "SPO2:", spo2Field, row++);
        addFormField(formPanel, gbc, "Remarks:", remarksField, row++);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            if (validateFields()) {
                result = new MedicalRecordData.ConsultationRecord(
                        dateTimeField.getText().trim(),
                        ageField.getText().trim(),
                        bmiField.getText().trim(),
                        bpField.getText().trim(),
                        tempField.getText().trim(),
                        rrField.getText().trim(),
                        prField.getText().trim(),
                        spo2Field.getText().trim(),
                        remarksField.getText().trim()
                );
                cancelled = false;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            cancelled = true;
            dispose();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private boolean validateFields() {
        if (dateTimeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter date/time.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public MedicalRecordData.ConsultationRecord getResult() {
        return cancelled ? null : result;
    }
}
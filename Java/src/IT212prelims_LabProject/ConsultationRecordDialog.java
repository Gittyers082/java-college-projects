package IT212prelims_LabProject;

import javax.swing.*;
import java.awt.*;

public class ConsultationRecordDialog extends JDialog {
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


        dateTimeField = new JTextField(20);
        ageField = new JTextField(10);
        bmiField = new JTextField(10);
        bpField = new JTextField(10);
        tempField = new JTextField(10);
        rrField = new JTextField(10);
        prField = new JTextField(10);
        spo2Field = new JTextField(10);
        remarksField = new JTextField(30);


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

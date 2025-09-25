package IT212prelims_LabProject;

import java.util.*;
import java.util.List;

// Medical Record Data Class
public class MedicalRecordData {
    // Personal Information
    public String lastName = "Ling";
    public String firstName = "Lin";
    public String middleName = "";
    public String dateOfBirth = "01/01/01";
    public String gender = "Furry";
    public String address = "Wakanda";
    public String contactNumber = "012386739";
    public String courseYear = "BMMA 69";

    // Emergency Contact
    public String emergencyName = "";
    public String emergencyAddress = "";
    public String emergencyContactNumber = "";

    // Allergies - Map of allergy name to details
    public Map<String, String> allergies;

    // Past Medical History - Map of sickness to details
    public Map<String, String> pastMedicalHistory;

    // Childhood Diseases - List of diseases
    public List<String> childhoodDiseases;

    // Personal/Social History
    public boolean isSmoker = false;
    public String sticksPerDay = "0";
    public boolean isDrinker = false;

    // Immunizations - Map of immunization name to date
    public Map<String, String> immunizations;

    // Others
    public String specialMedications = "";
    public String specialCare = "";
    public String others = "";

    // Consultation Records - List of consultation data
    public List<ConsultationRecord> consultationRecords;

    public MedicalRecordData() {
        // Initialize collections
        allergies = new HashMap<>();
        pastMedicalHistory = new HashMap<>();
        childhoodDiseases = new ArrayList<>();
        immunizations = new HashMap<>();
        consultationRecords = new ArrayList<>();

        // Add some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample allergies
        allergies.put("Peanuts", "Severe allergic reaction");
        allergies.put("Dust", "Mild sneezing");

        // Sample past medical history
        pastMedicalHistory.put("Asthma", "Childhood asthma, treated with inhaler");

        // Sample childhood diseases
        childhoodDiseases.add("Chicken Pox");
        childhoodDiseases.add("Measles");

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
        consultationRecords.add(record);
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

        // Convert to Object array for table display
        public Object[] toObjectArray() {
            return new Object[]{dateTime, age, bmi, bloodPressure, temperature,
                    respiratoryRate, pulseRate, oxygenSaturation, remarks};
        }
    }
}

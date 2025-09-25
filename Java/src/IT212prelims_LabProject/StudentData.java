package IT212prelims_LabProject;

import java.util.HashMap;
import java.util.Map;

class StudentData {
    String userId = "2222000";
    String accountName = "Lin Ling";
    String dateRegistered = "July 11, 2024";
    String accountType = "Student";

    Map<String, String> generalInfo;
    Map<String, String> contactInfo;
    Map<String, String> contactPersons;

    public StudentData() {
        // Initialize general information
        generalInfo = new HashMap<>();
        generalInfo.put("Gender", "Gay");
        generalInfo.put("Birthday", "December 1, 2001");
        generalInfo.put("Citizenship", "Chinese");
        generalInfo.put("Religion", "Atheist");
        generalInfo.put("Civil Status", "Single");
        generalInfo.put("Birthplace", "Wakanda");
        generalInfo.put("Nationality", "Chinese");

        // Initialize contact information
        contactInfo = new HashMap<>();
        contactInfo.put("Home Address", " Chiinnnaaa");
        contactInfo.put("Home Telephone No", "N/A");
        contactInfo.put("Baguio Address", "Chinese City, BAKAKENG NORTE");
        contactInfo.put("Baguio Telephone No", "N/A");
        contactInfo.put("Cellphone No", "09994445555");
        contactInfo.put("Email Address", "chinesemanfromchina@gmail.com");

        // Initialize contact persons
        contactPersons = new HashMap<>();
        contactPersons.put("Father's Name", "Shenrong");
        contactPersons.put("Father's Occupation", "Dragon King");
        contactPersons.put("Mother's Maiden Name", "Donatella");
        contactPersons.put("Mother's Occupation", "Jade Empress");
    }
}
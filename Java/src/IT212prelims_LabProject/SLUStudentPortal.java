package IT212prelims_LabProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main GUI Class
public class SLUStudentPortal extends JFrame {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;


    private StudentData studentData;
    private PersonalDetailsPanel personalDetailsPanel;
    private MedicalRecordPanel medicalRecordPanel; // Add this line

    public SLUStudentPortal() {
        studentData = new StudentData();
        personalDetailsPanel = new PersonalDetailsPanel(studentData);
        medicalRecordPanel = new MedicalRecordPanel(); // Add this line
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("SLU Student Portal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);


        setLayout(new BorderLayout());


        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);


        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);


        contentPanel.add(createHomePanel(), "Home");
        contentPanel.add(createPlaceholderPanel("Schedule"), "Schedule");
        contentPanel.add(createPlaceholderPanel("Attendance"), "Attendance");
        contentPanel.add(createPlaceholderPanel("Statement of Accounts"), "Statement of Accounts");
        contentPanel.add(createPlaceholderPanel("Grades"), "Grades");
        contentPanel.add(createPlaceholderPanel("Transcript of Records"), "Transcript of Records");
        contentPanel.add(createPlaceholderPanel("Curriculum Checklist"), "Curriculum Checklist");
        contentPanel.add(medicalRecordPanel, "Medical Record"); // Replace this line
        contentPanel.add(personalDetailsPanel, "Personal Details");
        contentPanel.add(createPlaceholderPanel("Journal/Periodical"), "Journal/Periodical");
        contentPanel.add(createPlaceholderPanel("Downloadables/About SLU"), "Downloadables/About SLU");

        add(contentPanel, BorderLayout.CENTER);


        cardLayout.show(contentPanel, "Home");
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(26, 54, 93));
        sidebar.setPreferredSize(new Dimension(250, 0));

        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        // Navigation items
        String[] navItems = {
                "Home", "Schedule", "Attendance", "Statement of Accounts",
                "Grades", "Transcript of Records", "Curriculum Checklist",
                "Medical Record", "Personal Details", "Journal/Periodical",
                "Downloadables/About SLU"
        };

        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            sidebar.add(navButton);
        }

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(240, 40));
        button.setBackground(new Color(26, 54, 93));
        button.setForeground(new Color(203, 213, 224));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 90, 135));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(26, 54, 93));
            }
        });

        button.addActionListener(e -> cardLayout.show(contentPanel, text));

        return button;
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome to SLU Student Portal", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(26, 54, 93));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        panel.add(welcomeLabel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 54, 93));

        panel.add(titleLabel, BorderLayout.CENTER);

        return panel;
    }

    // Remove the entire createMedicalRecordPanel() method since you're using the separate class

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            new SLUStudentPortal().setVisible(true);
        });
    }
}
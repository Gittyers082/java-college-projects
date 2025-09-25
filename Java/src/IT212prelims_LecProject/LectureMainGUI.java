package IT212prelims_LecProject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * GUI version of the Sorting Algorithm Performance Analysis.
 * Created by Group 2 for IT212 Prelims Project.
 * Features a grey and cyan themed interface for algorithm comparison.
 */
public class LectureMainGUI extends JFrame {

    // Color theme - Grey and Cyan
    private static final Color DARK_GREY = new Color(64, 64, 64);
    private static final Color LIGHT_GREY = new Color(128, 128, 128);
    private static final Color CYAN_PRIMARY = new Color(0, 188, 212);
    private static final Color CYAN_LIGHT = new Color(178, 235, 242);
    private static final Color WHITE = Color.WHITE;

    // GUI Components
    private JComboBox<String> algorithmCombo;
    private JComboBox<String> datasetCombo;
    private JComboBox<String> sizeCombo;
    private JTextArea resultArea;
    private JButton executeButton;
    private JButton clearButton;
    private JProgressBar progressBar;
    private JLabel statusLabel;

    /**
     * Constructor to initialize the GUI
     */
    public LectureMainGUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setGUIProperties();
    }

    /**
     * Initialize all GUI components with styling
     */
    private void initializeComponents() {
        // Combo boxes
        String[] algorithms = {"Bubble Sort", "Insertion Sort", "Selection Sort"};
        algorithmCombo = new JComboBox<>(algorithms);
        styleComboBox(algorithmCombo);

        String[] datasets = {"Best Case (Ascending)", "Worst Case (Descending)", "Average Case (Random)"};
        datasetCombo = new JComboBox<>(datasets);
        styleComboBox(datasetCombo);

        String[] sizes = {"10,000 elements", "50,000 elements", "200,000 elements", "500,000 elements", "1,000,000 elements"};
        sizeCombo = new JComboBox<>(sizes);
        styleComboBox(sizeCombo);

        // Buttons
        executeButton = new JButton("Execute Analysis");
        styleButton(executeButton, CYAN_PRIMARY);

        clearButton = new JButton("Clear Results");
        styleButton(clearButton, LIGHT_GREY);

        // Text area for results
        resultArea = new JTextArea(15, 50);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultArea.setBackground(WHITE);
        resultArea.setForeground(DARK_GREY);
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBackground(LIGHT_GREY);
        progressBar.setForeground(CYAN_PRIMARY);
        progressBar.setVisible(false);

        // Status label
        statusLabel = new JLabel("Ready to analyze sorting algorithms");
        statusLabel.setForeground(DARK_GREY);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
    }

    /**
     * Set up the layout of components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Control Panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.WEST);

        // Results Panel
        JPanel resultsPanel = createResultsPanel();
        add(resultsPanel, BorderLayout.CENTER);

        // Status Panel
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
    }

    /**
     * Create header panel with title
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(DARK_GREY);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel titleLabel = new JLabel("Sorting Algorithm Performance Analyzer");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        panel.add(titleLabel);

        JLabel groupLabel = new JLabel("Group 2 - IT212 Prelims Project");
        groupLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        groupLabel.setForeground(CYAN_LIGHT);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(DARK_GREY);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(groupLabel, BorderLayout.SOUTH);

        panel.add(titlePanel);
        return panel;
    }

    /**
     * Create control panel with selection options
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(LIGHT_GREY);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(300, 400));

        // Algorithm selection
        JPanel algoPanel = createSelectionPanel("Select Algorithm", algorithmCombo);
        panel.add(algoPanel);
        panel.add(Box.createVerticalStrut(15));

        // Dataset selection
        JPanel dataPanel = createSelectionPanel("Select Dataset Type", datasetCombo);
        panel.add(dataPanel);
        panel.add(Box.createVerticalStrut(15));

        // Size selection
        JPanel sizePanel = createSelectionPanel("Select Dataset Size", sizeCombo);
        panel.add(sizePanel);
        panel.add(Box.createVerticalStrut(25));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(LIGHT_GREY);
        buttonPanel.add(executeButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel);

        panel.add(Box.createVerticalStrut(15));
        panel.add(progressBar);

        return panel;
    }

    /**
     * Create results panel with scrollable text area
     */
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.setBorder(createTitledBorder("Analysis Results"));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create status panel
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(CYAN_LIGHT);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.add(statusLabel);
        return panel;
    }

    /**
     * Create a selection panel with label and combo box
     */
    private JPanel createSelectionPanel(String title, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(LIGHT_GREY);
        panel.setBorder(createTitledBorder(title));
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create styled titled border
     */
    private TitledBorder createTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(CYAN_PRIMARY, 2), title);
        border.setTitleColor(DARK_GREY);
        border.setTitleFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        return border;
    }

    /**
     * Style combo boxes
     */
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(WHITE);
        comboBox.setForeground(DARK_GREY);
        comboBox.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Style buttons
     */
    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(WHITE);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Setup event handlers for buttons
     */
    private void setupEventHandlers() {
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeAnalysis();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });
    }

    /**
     * Set GUI properties
     */
    private void setGUIProperties() {
        setTitle("Sorting Algorithm Performance Analyzer - Group 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(WHITE);
    }

    /**
     * Execute the sorting analysis
     */
    private void executeAnalysis() {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                executeButton.setEnabled(false);
                progressBar.setVisible(true);
                progressBar.setValue(0);

                publish("Starting analysis...");

                // Get selections
                int algorithmChoice = algorithmCombo.getSelectedIndex() + 1;
                int datasetChoice = datasetCombo.getSelectedIndex() + 1;
                int sizeChoice = sizeCombo.getSelectedIndex() + 1;

                progressBar.setValue(20);
                publish("Reading data file...");

                // Execute sorting
                String result = performSorting(algorithmChoice, datasetChoice, sizeChoice);

                progressBar.setValue(100);
                publish("Analysis complete!");

                SwingUtilities.invokeLater(() -> {
                    resultArea.append(result);
                    resultArea.append("\n" + "=".repeat(60) + "\n\n");
                    resultArea.setCaretPosition(resultArea.getDocument().getLength());
                });

                Thread.sleep(1000); // Show completion briefly

                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String message : chunks) {
                    statusLabel.setText(message);
                }
            }

            @Override
            protected void done() {
                executeButton.setEnabled(true);
                progressBar.setVisible(false);
                statusLabel.setText("Ready for next analysis");
            }
        };

        worker.execute();
    }

    /**
     * Perform the actual sorting analysis
     */
    private String performSorting(int sortingChoice, int datasetChoice, int sizeChoice) {
        StringBuilder result = new StringBuilder();

        // Mapping choices to descriptive names and sizes
        String[] sortingNames = {"", "Bubble Sort", "Insertion Sort", "Selection Sort"};
        String[] datasetTypes = {"", "Best Case", "Worst Case", "Average Case"};
        int[] sizes = {0, 10000, 50000, 200000, 500000, 1000000};

        String sortingName = sortingNames[sortingChoice];
        String datasetType = datasetTypes[datasetChoice];
        int size = sizes[sizeChoice];

        result.append("ANALYSIS RESULTS\n");
        result.append("================\n");
        result.append("Algorithm: ").append(sortingName).append("\n");
        result.append("Dataset: ").append(datasetType).append("\n");
        result.append("Size: ").append(String.format("%,d", size)).append(" elements\n");
        result.append("Timestamp: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n\n");

        try {
            // Read data from file
            int[] data = readDataFromFile(datasetChoice, size);
            if (data == null) {
                result.append("ERROR: Could not read data from file.\n");
                result.append("Please ensure data files exist in DataSets directory.\n");
                return result.toString();
            }

            // Create a copy of data to sort
            int[] dataToSort = data.clone();

            // Execute sorting and measure time
            long startTime = System.currentTimeMillis();
            long statementCount = 0;

            switch (sortingChoice) {
                case 1: // Bubble Sort
                    statementCount = BubbleSort.bubbleSort(dataToSort);
                    break;
                case 2: // Insertion Sort
                    statementCount = InsertionSort.insertionSort(dataToSort);
                    break;
                case 3: // Selection Sort
                    statementCount = SelectionSort.selectionSort(dataToSort);
                    break;
            }

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            // Build results
            result.append("PERFORMANCE METRICS:\n");
            result.append("Statements executed: ").append(String.format("%,d", statementCount)).append("\n");
            result.append("Execution time: ").append(executionTime).append(" ms\n");

            // Verify sorting
            boolean isSorted = verifySorting(dataToSort);
            result.append("Correctly sorted: ").append(isSorted ? "✓ Yes" : "✗ No").append("\n\n");

            // Sample data
            result.append("SAMPLE DATA:\n");
            result.append("First 10 elements: ");
            for (int i = 0; i < Math.min(10, dataToSort.length); i++) {
                result.append(dataToSort[i]).append(" ");
            }
            result.append("\n");

            if (dataToSort.length > 10) {
                result.append("Last 10 elements:  ");
                for (int i = dataToSort.length - 10; i < dataToSort.length; i++) {
                    result.append(dataToSort[i]).append(" ");
                }
                result.append("\n");
            }

            result.append("\nFOR TECHNICAL REPORT:\n");
            result.append(String.format("%-15s %-12s (%,d elements): %,d statements\n",
                    sortingName, datasetType, size, statementCount));

        } catch (Exception e) {
            result.append("ERROR during analysis: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }

        return result.toString();
    }

    /**
     * Clear the results area
     */
    private void clearResults() {
        resultArea.setText("");
        statusLabel.setText("Results cleared - Ready for new analysis");
    }

    /**
     * Read data from file (same as main class)
     */
    private int[] readDataFromFile(int datasetChoice, int size) {
        String[] datasetTypes = {"", "best", "worst", "avg"};
        String datasetType = datasetTypes[datasetChoice];
        String filename = "DataSets" + File.separator + getDatasetFolderName(datasetChoice) +
                File.separator + "data_" + size + "_" + datasetType + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int[] data = new int[size];
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null && index < size) {
                try {
                    data[index] = Integer.parseInt(line.trim());
                    index++;
                } catch (NumberFormatException e) {
                    // Skip invalid lines
                }
            }

            if (index < size) {
                int[] trimmedData = new int[index];
                System.arraycopy(data, 0, trimmedData, 0, index);
                return trimmedData;
            }

            return data;

        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get dataset folder name (same as main class)
     */
    private String getDatasetFolderName(int datasetChoice) {
        switch (datasetChoice) {
            case 1: return "BestCase";
            case 2: return "WorstCase";
            case 3: return "AverageCase";
            default: return "AverageCase";
        }
    }

    /**
     * Verify if array is sorted (same as main class)
     */
    private boolean verifySorting(int[] array) {
        if (array == null || array.length <= 1) {
            return true;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main method to launch the GUI
     */
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            // Continue with default look and feel
        }

        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LectureMainGUI().setVisible(true);
            }
        });
    }
}
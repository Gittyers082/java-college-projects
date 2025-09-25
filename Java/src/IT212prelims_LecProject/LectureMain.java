package IT212prelims_LecProject;

import java.io.*;
import java.util.Scanner;

/**
 * Main class to run sorting algorithm performance analysis.
 * Allows the user to select a sorting algorithm, dataset type, and dataset size,
 * then executes the sorting and displays performance metrics.
 */
public class LectureMain {

    /**
     * Entry point of the program.
     * Handles user interaction and initiates sorting execution.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sorting Algorithm Performance Analysis ===");
        System.out.println();

        // Choose sorting algorithm
        int sortingChoice = chooseSortingAlgorithm(scanner);

        // Choose dataset type
        int datasetChoice = chooseDatasetType(scanner);

        // Choose dataset size
        int sizeChoice = chooseDatasetSize(scanner);

        // Execute sorting based on user choices
        executeSorting(sortingChoice, datasetChoice, sizeChoice);

        scanner.close();
    }

    /**
     * Prompts the user to choose a sorting algorithm.
     *
     * @param scanner Scanner object for user input
     * @return the user's choice as an integer (1-3)
     */
    private static int chooseSortingAlgorithm(Scanner scanner) {
        System.out.println("Choose a sorting algorithm:");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Insertion Sort");
        System.out.println("3. Selection Sort");
        System.out.print("Enter your choice (1-3): ");

        int choice = 0;
        while (choice < 1 || choice > 3) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.print("Please enter a number between 1 and 3: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number (1-3): ");
            }
        }
        System.out.println();
        return choice;
    }

    /**
     * Prompts the user to choose a dataset type.
     *
     * @param scanner Scanner object for user input
     * @return the user's choice as an integer (1-3)
     */
    private static int chooseDatasetType(Scanner scanner) {
        System.out.println("Choose a dataset type:");
        System.out.println("1. Best Case (Ascending order)");
        System.out.println("2. Worst Case (Descending order)");
        System.out.println("3. Average Case (Random order)");
        System.out.print("Enter your choice (1-3): ");

        int choice = 0;
        while (choice < 1 || choice > 3) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 3) {
                    System.out.print("Please enter a number between 1 and 3: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number (1-3): ");
            }
        }
        System.out.println();
        return choice;
    }

    /**
     * Prompts the user to choose a dataset size.
     *
     * @param scanner Scanner object for user input
     * @return the user's choice as an integer (1-5)
     */
    private static int chooseDatasetSize(Scanner scanner) {
        System.out.println("Choose a dataset size:");
        System.out.println("1. 10,000 elements");
        System.out.println("2. 50,000 elements");
        System.out.println("3. 200,000 elements");
        System.out.println("4. 500,000 elements");
        System.out.println("5. 1,000,000 elements");
        System.out.print("Enter your choice (1-5): ");

        int choice = 0;
        while (choice < 1 || choice > 5) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 5) {
                    System.out.print("Please enter a number between 1 and 5: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number (1-5): ");
            }
        }
        System.out.println();
        return choice;
    }

    /**
     * Executes the selected sorting algorithm on the chosen dataset and size,
     * measures execution time and statement count, and displays results.
     *
     * @param sortingChoice the sorting algorithm choice (1-3)
     * @param datasetChoice the dataset type choice (1-3)
     * @param sizeChoice    the dataset size choice (1-5)
     */
    private static void executeSorting(int sortingChoice, int datasetChoice, int sizeChoice) {
        // Mapping choices to descriptive names and sizes
        String[] sortingNames = {"", "Bubble Sort", "Insertion Sort", "Selection Sort"};
        String[] datasetTypes = {"", "Best Case", "Worst Case", "Average Case"};
        int[] sizes = {0, 10000, 50000, 200000, 500000, 1000000};

        String sortingName = sortingNames[sortingChoice];
        String datasetType = datasetTypes[datasetChoice];
        int size = sizes[sizeChoice];

        System.out.println("Executing " + sortingName + " on " + datasetType + " dataset with " + size + " elements...");
        System.out.println();

        // Read data from file
        int[] data = readDataFromFile(datasetChoice, size);
        if (data == null) {
            System.out.println("Error: Could not read data from file.");
            return;
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

        // Display results - focusing on statement count as required by specifications
        System.out.println("=============== Results ===============");
        System.out.println("Algorithm: " + sortingName);
        System.out.println("Dataset: " + datasetType);
        System.out.println("Size: " + size + " elements");
        System.out.println("Statements executed: " + statementCount);
        System.out.println("Execution time: " + executionTime + " ms");

        // Verify if the array is sorted correctly
        boolean isSorted = verifySorting(dataToSort);
        System.out.println("Correctly sorted: " + (isSorted ? "Yes" : "No"));

        // Display first 10 elements for verification
        System.out.println();
        System.out.print("First 10 elements: ");
        for (int i = 0; i < Math.min(10, dataToSort.length); i++) {
            System.out.print(dataToSort[i] + " ");
        }
        System.out.println();

        // Display last 10 elements if array is larger than 10
        if (dataToSort.length > 10) {
            System.out.print("Last 10 elements: ");
            for (int i = dataToSort.length - 10; i < dataToSort.length; i++) {
                System.out.print(dataToSort[i] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Technical Report:");
        System.out.println(sortingName + " - " + datasetType + " (" + size + " elements): " + statementCount + " statements");
    }

    /**
     * Reads dataset from a file based on dataset type and size.
     *
     * @param datasetChoice the dataset type choice (1-3)
     * @param size          the number of elements to read
     * @return an integer array containing the dataset, or null if error occurs
     */
    private static int[] readDataFromFile(int datasetChoice, int size) {
        String[] datasetTypes = {"", "best", "worst", "avg"};
        String datasetType = datasetTypes[datasetChoice];
        String filename = "DataSets" + File.separator + getDatasetFolderName(datasetChoice) + File.separator + "data_" + size + "_" + datasetType + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int[] data = new int[size];
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null && index < size) {
                try {
                    data[index] = Integer.parseInt(line.trim());
                    index++;
                } catch (NumberFormatException e) {
                    System.out.println("Warning: Skipping invalid line: " + line);
                }
            }

            if (index < size) {
                System.out.println("Warning: Only " + index + " elements were read from file " + filename);
                int[] trimmedData = new int[index];
                System.arraycopy(data, 0, trimmedData, 0, index);
                return trimmedData;
            }

            return data;

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + filename);
            System.out.println("Please ensure the data files exist in the correct directory structure.");
            return null;
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the folder name corresponding to the dataset choice.
     *
     * @param datasetChoice the dataset type choice (1-3)
     * @return the folder name as a String
     */
    private static String getDatasetFolderName(int datasetChoice) {
        switch (datasetChoice) {
            case 1:
                return "BestCase";      // Folder for best case datasets
            case 2:
                return "WorstCase";     // Folder for worst case datasets
            case 3:
                return "AverageCase";   // Folder for average case datasets
            default:
                return "AverageCase";
        }
    }

    /**
     * Verifies if the given array is sorted in non-decreasing order.
     *
     * @param array the array to verify
     * @return true if sorted, false otherwise
     */
    private static boolean verifySorting(int[] array) {
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
}
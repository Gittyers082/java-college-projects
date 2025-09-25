package IT212prelims_LecProject;

/**
 * Implements the Insertion Sort algorithm for sorting an array of integers.
 * This class uses a static counter to track the number of key statements executed,
 * which can be used to analyze the algorithm's performance.
 */
public class InsertionSort {
    /**
     * Static counter to track the number of key statements executed during sorting.
     */
    public static long counter;

    /**
     * Sorts the given array using the Insertion Sort algorithm.
     * Tracks the number of key statements executed during the sort.
     *
     * @param array the array of integers to be sorted
     * @return the number of key statements executed during sorting
     */
    public static long insertionSort(int[] array) {
        resetCounter();

        // Input validation
        if (array == null || array.length <= 1) {
            return counter;
        }

        int n = array.length;
        counter++; // Count: n = array.length assignment

        // Main loop starting from second element
        for (int i = 1; i < n; i++) {
            counter++; // Count: i = 1 (initialization, counted once)
            if (i > 1) counter++; // Count: i++ for subsequent iterations
            counter++; // Count: i < n (condition check)

            int key = array[i];
            counter++; // Count: key = array[i] assignment

            int j = i - 1;
            counter++; // Count: j = i - 1 assignment

            // Shift elements greater than key to one position ahead
            while (j >= 0 && array[j] > key) {
                counter++; // Count: j >= 0 comparison
                counter++; // Count: array[j] > key comparison

                array[j + 1] = array[j];
                counter++; // Count: array[j + 1] = array[j] assignment

                j--;
                counter++; // Count: j-- operation
            }

            // Count the final comparison that breaks the while loop
            if (j >= 0) {
                counter++; // Count: j >= 0 (when this condition fails)
                counter++; // Count: array[j] > key (when this condition fails)
            } else {
                counter++; // Count: j >= 0 (when j < 0)
            }

            array[j + 1] = key;
            counter++; // Count: array[j + 1] = key assignment
        }

        return counter;
    }

    /**
     * Resets the static counter to zero.
     */
    private static void resetCounter() {
        counter = 0;
    }
}
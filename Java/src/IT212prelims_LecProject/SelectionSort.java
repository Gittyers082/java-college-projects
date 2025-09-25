package IT212prelims_LecProject;

/**
 * Implements the Selection Sort algorithm for sorting an array of integers.
 * This class uses a static counter to track the number of key statements executed,
 * which can be used to analyze the algorithm's performance.
 */
public class SelectionSort {
    /**
     * Static counter to track the number of key statements executed during sorting.
     */
    public static long counter;

    /**
     * Sorts the given array using the Selection Sort algorithm.
     * Tracks the number of key statements executed during the sort.
     *
     * @param array the array of integers to be sorted
     * @return the number of key statements executed during sorting
     */
    public static long selectionSort(int[] array) {
        resetCounter();

        // Input validation
        if (array == null || array.length <= 1) {
            return counter;
        }

        int n = array.length;
        counter++; // Count: n = array.length assignment

        // Outer loop - one less than array length
        for (int i = 0; i < n - 1; i++) {
            counter++; // Count: i = 0 (initialization, counted once)
            if (i > 0) counter++; // Count: i++ for subsequent iterations
            counter++; // Count: i < n - 1 (condition check)

            int minIdx = i;
            counter++; // Count: minIdx = i assignment

            // Inner loop - find minimum element in unsorted portion
            for (int j = i + 1; j < n; j++) {
                counter++; // Count: j = i + 1 (initialization, counted once per outer loop)
                if (j > i + 1) counter++; // Count: j++ for subsequent iterations
                counter++; // Count: j < n (condition check)
                counter++; // Count: array[j] < array[minIdx] comparison

                if (array[j] < array[minIdx]) {
                    minIdx = j;
                    counter++; // Count: minIdx = j assignment
                }
            }

            // Swap the found minimum element with the first element if needed
            counter++; // Count: minIdx != i comparison
            if (minIdx != i) {
                swap(array, i, minIdx);
            }
        }

        return counter;
    }

    /**
     * Resets the static counter to zero.
     */
    private static void resetCounter() {
        counter = 0;
    }

    /**
     * Swaps two elements in the array and increments the counter for the swap operations.
     *
     * @param array the array containing elements to swap
     * @param i     the index of the first element
     * @param j     the index of the second element
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        counter++; // Count: temp = array[i]
        array[i] = array[j];
        counter++; // Count: array[i] = array[j]
        array[j] = temp;
        counter++; // Count: array[j] = temp
    }
}
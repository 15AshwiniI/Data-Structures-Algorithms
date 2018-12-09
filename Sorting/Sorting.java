import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr or comparator is null");
        }
        boolean isSwapped = false;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        //while (!isSwapped) {
        do {
            //System.out.println("in while");
            isSwapped = false;
            for (int i = startIndex; i < endIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    //perform swap
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    //swapped
                    isSwapped = true;
                }
            }
            if (!isSwapped) {
                // nothing swapped so we can end early
                return;
            }
            isSwapped = false;

            //decrement end index
            endIndex--;

            for (int j = endIndex - 1; j >= startIndex; j--) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    //perform swap
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSwapped = true;
                }
            }
            startIndex++;
        } while (isSwapped);
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr or comparator is null");
        }
        int length = arr.length;
        //loop skipping first index
        for (int i = 1; i < length; i++) {
            int j = i;
            //compare current and previous
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j--;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array, but they may not
     * necessarily stay in the same relative order.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr or comparator is null");
        }
        int size = arr.length;
        int minIndex = 0;
        for (int i = 0; i < size - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                //swap
                T temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("arr, rand or comparator null");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length);
    }

    /**
     * Performs the recursive quick sort.
     * @param arr the array being sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the random object used to select pivots
     * @param left the smallest index of the array
     * @param right the length of the array
     * @param <T> the type of the data being sorted
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                            Random rand, int left, int right) {
        if (left >= (right - 1)) {
            return;
        }
        // preserve the left and right values
        int i = left;
        int k = right;
        //get random pivot index and value at that index
        int pivotIndex = rand.nextInt((right - left) - 1) + left;
        T pivotValue = arr[pivotIndex];
        // move pivot to the front of the array
        // swap pivot and front of the array
        T temp = arr[pivotIndex];
        arr[pivotIndex] = arr[left];
        arr[left] = temp;
        //increment/decrement left and right
        right--;
        left++;

        //set up left and right
        while (left <= right) {
            //find a value greater than pivot
            //increment while arr[left] <= pivot
            while ((left <= right)
                    && (comparator.compare(arr[left], pivotValue) <= 0)) {
                left++;
            }
            //find a value smaller than pivot
            //increment while arr[right] >= pivot
            while ((left <= right)
                    && (comparator.compare(arr[right], pivotValue) >= 0)) {
                right--;
            }
            //swap left and right if left < right
            if (left < right) {
                T temp1 = arr[left];
                arr[left] = arr[right];
                arr[right] = temp1;
                right--;
                left++;
            }
        }
        //swap right with left which is preserved in i
        T temp2 = arr[i];
        arr[i] = arr[right];
        arr[right] = temp2;

        //recurse
        quickSortHelper(arr, comparator, rand, i, right);
        quickSortHelper(arr, comparator, rand, right + 1, k);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("arr or comparator is null");
        }
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;
        T[] leftArray = (T[]) new Object[midIndex];
        T[] rightArray = (T[]) new Object[length - midIndex];

        //left
        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = arr[i];
        }
        //right
        for (int j = 0; j < (length - midIndex); j++) {
            rightArray[j] = arr[midIndex + j];
        }
        //recurse
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        merge(leftArray, rightArray, arr, comparator);
    }

    /**
     * Recursively performs the merging of the arrays
     * @param leftArray the left array being merged
     * @param rightArray the right array being merged
     * @param finalArray the final merged array
     * @param comparator the Comparator used to compare the data
     * @param <T> the type of the data being sorted
     */
    private static <T> void merge(T[] leftArray, T[] rightArray,
                                  T[] finalArray, Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (comparator.compare(leftArray[leftIndex],
                    rightArray[rightIndex]) <= 0) {
                finalArray[mergedIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                finalArray[mergedIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            mergedIndex++;
        }
        //copy remaining elements from both arrays
        while (leftIndex < leftArray.length) {
            finalArray[mergedIndex] = leftArray[leftIndex];
            mergedIndex++;
            leftIndex++;
        }

        while (rightIndex < rightArray.length) {
            finalArray[mergedIndex] = rightArray[rightIndex];
            mergedIndex++;
            rightIndex++;
        }
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("arr is null");
        }
        int maxPlaces = maxValPlaces(arr);
        //both negative and pos
        ArrayList<LinkedList<Integer>> buckets = new ArrayList<>();
        boolean isSorted = false;
        //initial lsd index, multiplied by 10 after loop
        int lsd = 1;
        //set up each bucket so it can hold multiple values
        for (int i = 0; i < 19; i++) {
            buckets.add(i, new LinkedList<>());
        }
        //current index is used to determine when to stop
        int currentIndex = 0;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < arr.length; i++) {
                int num = arr[i];
                //determine correct bucket and put value in it
                int bucketIndex = ((num / lsd) % 10) + 9;
                if (currentIndex < maxPlaces) {
                    isSorted = false;
                }
                buckets.get(bucketIndex).add(num);
            }
            //increment lsd
            lsd *= 10;
            int arrayIndex = 0;
            currentIndex++;
            //fill array with bucket values
            for (LinkedList<Integer> bucketList : buckets) {
                while (!bucketList.isEmpty() && arrayIndex < arr.length) {
                    arr[arrayIndex] = bucketList.remove();
                    arrayIndex++;
                }
                bucketList.clear();
            }
        }
        return arr;
    }

    /**
     * Finds the maximum number of places in the values in the array
     * @param arr the array being looked through
     * @return the maximum number of places in the array
     */
    private static int maxValPlaces(int[] arr) {
        int maxVal = 0;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > maxVal) {
                maxVal = Math.abs(arr[i]);
            }
        }
        int maxValPlaces = 0;
        while ((maxVal / pow(10, maxValPlaces)) > 0) {
            maxValPlaces++;
        }
        return maxValPlaces;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}

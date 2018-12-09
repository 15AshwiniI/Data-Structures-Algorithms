/**
 * Your implementation of a min heap.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial size of {@code STARTING_SIZE} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MinHeap() {
        size = 0;
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }
        if (size > (backingArray.length - 2)) {
            //resize
            int newLength = (int) (backingArray.length * 1.5);
            T[] newArray = (T[]) new Comparable[newLength];
            for (int i = 0; i < backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        size++;
        backingArray[size] = item;
        int index = size;
        boolean hasSwitched = true;
        while (index > 1 && backingArray[index / 2] != null && hasSwitched) {
            if (backingArray[index / 2].compareTo(backingArray[index]) > 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[index / 2];
                backingArray[index / 2] = temp;
            } else {
                hasSwitched = false;
            }
            index = (index / 2);
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Heap is empty");
        }
        T data = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        int i = 1;
        boolean hasSwitched = true;
        while ((i * 2 < size + 1)
                && backingArray[i * 2] != null && hasSwitched) {
            if (backingArray[ i * 2 + 1] != null) {
                if (backingArray[i].compareTo(backingArray[i * 2]) > 0
                        || backingArray[i]
                        .compareTo(backingArray[i * 2 + 1]) > 0) {
                    if (backingArray[i * 2]
                            .compareTo(backingArray[i * 2 + 1]) < 0) {
                        T temp = backingArray[i * 2];
                        backingArray[i * 2] = backingArray[i];
                        backingArray[i] = temp;
                        i = (i * 2);
                    } else {
                        T temp = backingArray[i * 2 + 1];
                        backingArray[i * 2 + 1] = backingArray[i];
                        backingArray[i] = temp;
                        i = (i * 2 + 1);
                    }
                } else {
                    hasSwitched = false;
                }
            } else if (backingArray[i * 2 + 1] == null
                    && backingArray[i * 2] != null) {
                if (backingArray[i].compareTo(backingArray[i * 2]) > 0) {
                    T temp = backingArray[i * 2];
                    backingArray[i * 2] = backingArray[i];
                    backingArray[i] = temp;
                    i = (i * 2);
                } else {
                    hasSwitched = false;
                }
            } else {
                hasSwitched = false;
            }
        }
        return data;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}

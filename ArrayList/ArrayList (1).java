/**
 * Your implementation of an ArrayList.
 *
 * @author Ashwini Iyer
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) throws IndexOutOfBoundsException,
            IllegalArgumentException {
        boolean added = false;
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index < 0 or > size");
        }
        if (index == size) {
            added = true;
            addToBack(data);
        }
        if (!added) {
            T[] newArray;
            if ((size + 1) > backingArray.length) {
                newArray = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < size; i++) {
                    newArray[i] = backingArray[i];
                }
                backingArray = newArray;
            }
            T currentValue = backingArray[0];
            T valueToBeAdded = backingArray[0];
            for (int i = 0; i < size + 1; i++) {
                if (i == index) {
                    currentValue = backingArray[index];
                    backingArray[index] = data;
                }
                if (i > index) {
                    valueToBeAdded = currentValue;
                    currentValue = backingArray[i];
                    backingArray[i] = valueToBeAdded;
                    if (currentValue == null) {
                        i = size;
                    }
                }
            }
            size++;
        }
    }

    @Override
    public void addToFront(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        T currentValue = backingArray[0];
        T valueToBeAdded = backingArray[0];
        if (size + 1 > backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        for (int i = 0; i < size + 1; i++) {
            if (i == 0) {
                currentValue = backingArray[i];
                backingArray[i] = data;
            } else {
                valueToBeAdded = currentValue;
                currentValue = backingArray[i];
                backingArray[i] = valueToBeAdded;
                if (currentValue == null) {
                    i = size + 1;
                }
            }

        }
        size++;
    }

    @Override
    public void addToBack(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size + 1 > backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        backingArray[size] = data;
        size++;
    }

    @Override
    public T removeAtIndex(int index) throws IndexOutOfBoundsException {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("index < 0 or >= to size");
        }
        if (index == size) {
            removeFromBack();
        }
        if (isEmpty()) {
            return null;
        }
        T indexData = backingArray[index];
        backingArray[index] = null;
        for (int i = index + 1; i < size; i++) {
            backingArray[i - 1] = backingArray[i];
            backingArray[i] = null;
        }
        size--;
        return indexData;
    }

    @Override
    public T removeFromFront() {
        T frontData;
        if (isEmpty()) {
            return null;
        } else {
            frontData = backingArray[0];
        }
        backingArray[0] = null;
        for (int i = 1; i < backingArray.length; i++) {
            backingArray[i - 1] = backingArray[i];
        }
        size--;
        return frontData;
    }

    @Override
    public T removeFromBack() {
        T endData;
        if (isEmpty()) {
            return null;
        } else {
            endData = backingArray[size - 1];
        }
        backingArray[size - 1] = null;
        size--;
        return endData;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("index < 0 or >= to size");
        }
        return backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        T[] newArray = (T[]) new Object[INITIAL_CAPACITY];
        backingArray = newArray;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }
}

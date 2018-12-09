import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed queue.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        //size = 0;
        //back = 0;
        //front = 0;
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you <b>must not</b>
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("queue was empty");
        }
        T data = backingArray[front];
        backingArray[front] = null;
        size--;
        front = (front + 1) % backingArray.length;
        return data;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to 1.5 times the current backing array length, rounding down
     * if necessary. If a regrow is necessary, you should copy elements to the
     * front of the new array and reset front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size < backingArray.length) {
            backingArray[back] = data;
            size++;
            back = (back + 1) % backingArray.length;
        } else {
            //resize
            int j = front;
            double newLength = backingArray.length * 1.5;
            T[] tempArray = (T[]) new Object[(int) newLength];
            for (int i = 0; i < backingArray.length; i++) {
                tempArray[i] = backingArray[j];
                j = (j + 1) % 10;
                if (j == front) {
                    i = backingArray.length;
                }
            }
            backingArray = tempArray;
            front = 0;
            back = size;
            backingArray[back] = data;
            size++;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY!
        return backingArray;
    }
}

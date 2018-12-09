import java.util.NoSuchElementException;

/**
 * Your implementation of a linked stack.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public T pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        LinkedNode<T> value = head;
        if (size == 1) {
            head = null;
            //size--;
        } else {
            head = value.getNext();
        }
        size--;
        return value.getData();
    }

    @Override
    public void push(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (isEmpty()) {
            LinkedNode<T> newNode = new LinkedNode<T>(data);
            head = newNode;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(data, head);
            head = newNode;
        }
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }
}

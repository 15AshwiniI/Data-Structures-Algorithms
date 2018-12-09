/**
 * Your implementation of a DoublyLinkedList
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) throws
            IndexOutOfBoundsException, IllegalArgumentException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index < 0 or index > size");
        }
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size && size != 0) {
            addToBack(data);
        } else {
            LinkedListNode<T> currentNode = head;
            LinkedListNode<T> newNode = new LinkedListNode<T>(data);
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    newNode.setPrevious(currentNode.getPrevious());
                    newNode.setNext(currentNode);
                    currentNode.getPrevious().setNext(newNode);
                    currentNode.setPrevious(newNode);
                    size++;
                }
                if (currentNode.getNext() != null) {
                    currentNode = currentNode.getNext();
                } else {
                    i = size;
                }
            }
        }
    }

    @Override
    public void addToFront(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrevious(null);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    @Override
    public void addToBack(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        LinkedListNode<T> newNode = new LinkedListNode<T>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setPrevious(tail);
            newNode.setNext(null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index) throws IndexOutOfBoundsException {
        T data = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index < 0 or index >= size");
        }
        if (index == 0) {
            data = removeFromFront();
        } else if (index == size - 1 && size != 1) {
            data = removeFromBack();
        }  else {
            int spot = 0;
            LinkedListNode<T> currentNode = head;
            while (spot <= index) {
                if (spot == index) {
                    data = currentNode.getData();
                    LinkedListNode<T> p = currentNode.getPrevious();
                    currentNode.getNext().setPrevious(p);
                    currentNode.getPrevious().setNext(currentNode.getNext());
                    size--;
                }
                currentNode = currentNode.getNext();
                spot++;
            }
        }
        return data;
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        T data = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            LinkedListNode<T> nextNode = head.getNext();
            nextNode.setPrevious(null);
            head = nextNode;
        }
        size--;
        return data;
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        T data = tail.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            LinkedListNode<T> prevNode = tail.getPrevious();
            prevNode.setNext(null);
            tail = prevNode;
        }
        size--;
        return data;
    }

    @Override
    public boolean removeAllOccurrences(T data)
            throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        boolean removed = false;
        if (size == 0) {
            return false;
        }
        LinkedListNode<T> current = head;
        /*
        if (current.getNext() == null) {
            T temp = removeFromBack();
            removed = true;
        }
        */
        while (current.getNext() != null) {
            if (current.getData().equals(data)) {
                if (current == head) {
                    T temp = removeFromFront();
                    removed = true;
                } else if (current == tail) {
                    T temp = removeFromBack();
                    removed = true;
                } else {
                    LinkedListNode<T> prev = current.getPrevious();
                    if (prev != null && current.getNext() != null) {
                        current.getNext().setPrevious(current.getPrevious());
                        current.getPrevious().setNext(current.getNext());
                        removed = true;
                        size--;
                    }
                }
            }
            current = current.getNext();
        }
        if (current.getNext() == null && current.getData().equals(data)) {
            T temp = removeFromBack();
            removed = true;
        }
        return removed;

    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index < 0 or index >= size");
        }
        if (index == 0) {
            return head.getData();
        }
        if (index == size - 1) {
            return tail.getData();
        }
        LinkedListNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode.getData();
            }
            currentNode = currentNode.getNext();
        }
        return head.getData();
    }

    @Override
    public Object[] toArray() {
        T[] array = (T[]) new Object[size];
        LinkedListNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            array[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }
        return array;
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
        head = null;
        tail = null;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}

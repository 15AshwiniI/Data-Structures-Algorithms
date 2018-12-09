import java.util.*;

/**
 * Your implementation of a binary search tree.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        BSTNode<T> current = root;
        //Iterator<T> iterator = data.iterator();
        for (T dataValue: data) {
            if (dataValue == null) {
                throw new IllegalArgumentException("data is null");
            } else {
                add(dataValue);
            }
        }
    }

    @Override
    public void add(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            // add at root, tree must have been empty
            root = new BSTNode<T>(data);
            size++;
        } else {
            boolean add = addData(root, data);
        }
    }


    /**
     * Recursively traverses tree and puts the data in the appropriate location
     * @param current the current node we are inspecting
     * @param data the data we are adding to the tree
     * @return true if data was added, false otherwise
     */
    private boolean addData(BSTNode<T> current, T data) {
        BSTNode<T> newNode = new BSTNode<T>(data);
        if (current.getData().compareTo(data) == 0) {
            //they are the same, do nothing do not add to tree
            // size is not incremented, only when node is added
            return false;
        } else if (current.getData().compareTo(data) < 0) {
            //node data is less than data, add to right tree
            if (current.getRight() == null) {
                //just add to the right of the node
                current.setRight(newNode);
                size++;
                return true;
            } else {
                return addData(current.getRight(), data);
            }
        } else if (current.getData().compareTo(data) > 0) {
            //node data is greater than data, add to left tree
            if (current.getLeft() == null) {
                //add to left node
                current.setLeft(newNode);
                size++;
                return true;
            } else {
                //continue to iterate through the left tree
                return addData(current.getLeft(), data);
            }
        }
        return false;
    }

    @Override
    public T remove(T data) throws IllegalArgumentException,
            java.util.NoSuchElementException {
        BSTNode<T> dummyNode = new BSTNode<T>(null);
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size == 0) {
            throw new java.util.NoSuchElementException("tree is empty");
        }
        if (root.getData().compareTo(data) == 0) {
            size--;
            if (root.getRight() != null && root.getLeft() == null) {
                this.root = root.getRight();
            } else if (root.getRight() == null && root.getLeft() != null) {
                this.root = root.getLeft();
            } else if (root.getRight() == null && root.getLeft() == null) {
                root = null;
            } else {
                BSTNode<T> predecessor = root.getLeft();
                while (predecessor.getRight() != null) {
                    predecessor = predecessor.getRight();
                }
                root.setData(predecessor.getData());
                //predecessor.setData(null);
                predecessor = predecessor.getLeft();
            }
        } else if (root.getData().compareTo(data) > 0) {
            if (root.getLeft() == null) {
                throw new NoSuchElementException("data is not in tree");
            }
            root.setLeft(removeData(root.getLeft(), data, dummyNode));
        } else {
            if (root.getRight() == null) {
                throw new NoSuchElementException("data is not in tree");
            }
            root.setRight(removeData(root.getRight(), data, dummyNode));
        }
        //BSTNode<T> removedNode = removeData(root, data, dummyNode);
        return dummyNode.getData();
    }

    /**
    * Removes data from the tree and saves the data that was removed.
     * @param  current the current node we are inspecting
     * @param  data the data we are looking to remove
     * @param dn  the node that the removed data is saved in
     * @throws java.util.NoSuchElementException if the element is there
     * @return the node that the data was removed from
    *
    */
    private BSTNode<T> removeData(BSTNode<T> current, T data, BSTNode<T> dn)
            throws java.util.NoSuchElementException {
        if (current.getData().compareTo(data) > 0) {
            if (current.getLeft() == null) {
                throw new NoSuchElementException("data is not in tree");
            }
            current.setLeft(removeData(current.getLeft(), data, dn));
        } else if (current.getData().compareTo(data) < 0) {
            if (current.getRight() == null) {
                throw new NoSuchElementException("data is not in tree");
            }
            current.setRight(removeData(current.getRight(), data, dn));
        } else {
            dn.setData(current.getData());
            BSTNode<T> predValue = new BSTNode<T>(null);
            size--;
            if (current.getLeft() == null && current.getRight() == null) {
                current = null;
            } else if (current.getLeft() != null
                    && current.getRight() == null) {
                current = current.getLeft();
            } else if (current.getLeft() == null
                    && current.getRight() != null) {
                current = current.getRight();
            }  else if (current.getLeft() != null
                    && current.getRight() != null) {
                BSTNode<T> filler =
                        getPredecessor(current.getLeft(), predValue);
                current.setData(predValue.getData());
            }
        }
        return current;
    }

    /**
     * Recursively finds the predecessor and removes it.
     * It saves the predecessor for later use.
     * @param current the current node we are inspecting
     * @param dummyNode the node to store the predecessor data
     * @return the node with the predecessor
     */
    private BSTNode<T> getPredecessor(BSTNode<T> current,
                                      BSTNode<T> dummyNode) {
        if (current.getRight() == null) {
            dummyNode.setData(current.getData());
            if (current.getLeft() == null) {
                current.setData(null);
                current = null;
            } else {
                current.setData(current.getLeft().getData());
                current.setLeft(current.getLeft().getLeft());
                current.setRight(current.getLeft().getRight());
            }
        } else {
            //current.setRight(getPredecessor(current.getRight(), dummyNode));
            current = getPredecessor(current.getRight(), dummyNode);
        }
        return current;
    }

    @Override
    public T get(T data) throws IllegalArgumentException,
            java.util.NoSuchElementException {
        T foundData = null;
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            throw new java.util.NoSuchElementException("BST is empty");
        } else {
            foundData = findData(root, data);
        }
        if (foundData == null) {
            throw new java.util.NoSuchElementException("data was not found");
        }
        return foundData;
    }

    /**
     * Recursively traverses the tree to find the data
     * @param current the current node we are inspecting for the data
     * @param data the data we are looking for
     * @return the data found in the node
     */
    private T findData(BSTNode<T> current, T data) {
        T foundData = null;
        if (current.getData().equals(data)) {
            foundData = current.getData();
            return foundData;
        } else if (current.getData().compareTo(data) < 0) {
            if (current.getRight() == null) {
                foundData = null;
                return null;
            } else {
                return findData(current.getRight(), data);
            }
        } else if (current.getData().compareTo(data) > 0) {
            if (current.getLeft() == null) {
                foundData = null;
                return null;
            } else {
                return findData(current.getLeft(), data);
            }
        }
        return foundData;
    }

    @Override
    public boolean contains(T data) throws IllegalArgumentException {
        T foundData = null;
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            return false;
        } else {
            foundData = findData(root, data);
        }
        if (foundData != null) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> preOrderList = new ArrayList<T>();
        return preOrderTraversal(root, preOrderList);
    }

    /**
     * Recursively adds the data to a list in preOrder
     * @param current the current node who's data we are adding to the list
     * @param list the list we are adding the data to in preOrder
     * @return the completed ordered list
     */
    private List<T> preOrderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preOrderTraversal(current.getLeft(), list);
            preOrderTraversal(current.getRight(), list);
        }
        return list;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> postOrderList = new ArrayList<T>();
        return postOrderTraversal(root, postOrderList);
    }

    /**
     * Recursively adds the data to a list in postOrder
     * @param current the current node who's data we are adding to the list
     * @param list the list we are adding the data to in postOrder
     * @return the completed ordered list
     */
    private List<T> postOrderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            postOrderTraversal(current.getLeft(), list);
            postOrderTraversal(current.getRight(), list);
            list.add(current.getData());
        }
        return list;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> inOrderList = new ArrayList<T>();
        return inOrderTraversal(root, inOrderList);
    }

    /**
     * Recursively adds the data to a list in inOrder
     * @param current the current node who's data we are adding to the list
     * @param list the list we are adding the data to in inOrder
     * @return the completed ordered list
     */
    private List<T> inOrderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            inOrderTraversal(current.getLeft(), list);
            list.add(current.getData());
            inOrderTraversal(current.getRight(), list);
        }
        return list;
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> levelOrderList = new ArrayList<T>();
        return levelOrderTraversal(root, levelOrderList);
    }

    /**
     * Iteratively adds the data to a list in level order
     * @param current the current node who's data we are adding to the list
     * @param list the list we are adding the data to in levelOrder
     * @return the completed ordered list
     */
    private List<T> levelOrderTraversal(BSTNode current, List<T> list) {
        if (current != null) {
            ArrayList<BSTNode> queue = new ArrayList<BSTNode>();
            queue.add(current);
            while (!queue.isEmpty()) {
                BSTNode<T> temp = queue.get(0);
                if (temp != null) {
                    queue.add(temp.getLeft());
                    queue.add(temp.getRight());
                    list.add((T) queue.get(0).getData());
                }
                queue.remove(0);
            }

        }
        return list;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        if (size == 0) {
            return -1;
        }
        return calHeight(root);
    }

    /**
     * Calculates the height recursively.
     * @param current the current node we are adding to the height
     * @return the height of the tree
     */
    private int calHeight(BSTNode<T> current) {
        int leftHeight = 0;
        int rightHeight = 0;
        if (current.getLeft() == null && current.getRight() == null) {
            return 0;
        }
        if (current.getLeft() != null) {
            leftHeight = calHeight(current.getLeft());
        }
        if (current.getRight() != null) {
            rightHeight = calHeight(current.getRight());
        }
        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        }
        return rightHeight + 1;
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}

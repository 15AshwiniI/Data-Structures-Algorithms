import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for (T dataValue: data) {
            if (dataValue == null) {
                throw new IllegalArgumentException("data is null");
            } else {
                add(dataValue);
            }
        }
    }

    @Override
    public void add(T data) {
        if (data  == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = addHelper(root, data);
    }

    /**
     * Recursively adds the data to the tree.
     * @param node the current node we are inspecting
     * @param data the data to add to the tree
     * @return the tree with data that is balanced
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            node = new AVLNode<T>(data);
            //data added
            size++;
        } else {
            if (node.getData().compareTo(data) > 0) {
                node.setLeft(addHelper(node.getLeft(), data));
            } else if (node.getData().compareTo(data) < 0) {
                node.setRight(addHelper(node.getRight(), data));
            }
        }
        node = balanceTree(node);
        return node;
    }

    /**
     * Performs a single left rotation and re-balances.
     * @param node the current node we are inspecting
     * @return the balanced left rotated tree
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> nodeRightChild = node.getRight();
        node.setRight(nodeRightChild.getLeft());
        nodeRightChild.setLeft(node);

        //update heights
        node.setHeight(calHeight(node));
        nodeRightChild.setHeight(calHeight(nodeRightChild));

        //update balance factors
        if ((nodeRightChild.getLeft() != null)
                && (nodeRightChild.getRight() != null)) {
            nodeRightChild.setBalanceFactor(calHeight(nodeRightChild.getLeft())
                    - calHeight(nodeRightChild.getRight()));
        } else if ((nodeRightChild.getLeft() != null)
                && (nodeRightChild.getRight() == null)) {
            nodeRightChild.setBalanceFactor(
                    calHeight(nodeRightChild.getLeft()) + 1);
        } else if ((nodeRightChild.getLeft() == null)
                && (nodeRightChild.getRight() != null)) {
            nodeRightChild.setBalanceFactor(-1
                    - calHeight(nodeRightChild.getRight()));
        } else {
            nodeRightChild.setBalanceFactor(0);
        }
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            node.setBalanceFactor(calHeight(node.getLeft())
                    - calHeight(node.getRight()));
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            node.setBalanceFactor(calHeight(node.getLeft()) + 1);
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            node.setBalanceFactor(-1 - calHeight(node.getRight()));
        } else {
            node.setBalanceFactor(0);
        }
        return nodeRightChild;
    }

    /**
     * Performs a single right rotation and re-balances.
     * @param node the current node we are inspecting
     * @return the balanced right rotated tree
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> nodeLeftChild = node.getLeft();
        node.setLeft(nodeLeftChild.getRight());
        nodeLeftChild.setRight(node);

        //update heights
        node.setHeight(calHeight(node));
        nodeLeftChild.setHeight(calHeight(nodeLeftChild));

        //update balance factors
        if ((nodeLeftChild.getLeft() != null)
                && (nodeLeftChild.getRight() != null)) {
            nodeLeftChild.setBalanceFactor(calHeight(nodeLeftChild.getLeft())
                    - calHeight(nodeLeftChild.getRight()));
        } else if ((nodeLeftChild.getLeft() != null)
                && (nodeLeftChild.getRight() == null)) {
            nodeLeftChild.setBalanceFactor(
                    calHeight(nodeLeftChild.getLeft()) + 1);
        } else if ((nodeLeftChild.getLeft() == null)
                && (nodeLeftChild.getRight() != null)) {
            nodeLeftChild.setBalanceFactor(-1
                    - calHeight(nodeLeftChild.getRight()));
        } else {
            nodeLeftChild.setBalanceFactor(0);
        }
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            node.setBalanceFactor(calHeight(node.getLeft())
                    - calHeight(node.getRight()));
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            node.setBalanceFactor(calHeight(node.getLeft()) + 1);
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            node.setBalanceFactor(-1 - calHeight(node.getRight()));
        } else {
            node.setBalanceFactor(0);
        }
        return nodeLeftChild;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("tree is empty");
        }
        if (root == null) {
            throw new NoSuchElementException("root is null");
        }
        AVLNode<T> valueNode = new AVLNode<T>(null);
        root = removeHelper(root, data, valueNode);
        return valueNode.getData();
    }

    /**
     * Recursively removes the data to the tree.
     *
     * @throws java.util.NoSuchElementException if the data is not found
     * @param node the current node we are inspecting
     * @param data the data to remove from the tree
     * @param valueNode the node that stores the value removed from the tree
     * @return the tree with data that is balanced
     */
    private AVLNode<T> removeHelper(AVLNode<T> node,
                                    T data, AVLNode<T> valueNode) {
        AVLNode<T> predValue = new AVLNode<T>(null);
        if (node == null) {
            //item is not found
            // throw exception
            throw new NoSuchElementException("data not found");
            //return node;
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(node.getLeft(), data, valueNode));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(node.getRight(), data, valueNode));
        } else {
            //equal
            valueNode.setData(node.getData());
            size--;
            if (node.getLeft() == null && node.getRight() != null) {
                return node.getRight();
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> temp = getPredecessor(node.getLeft(), predValue);
                node.setData(predValue.getData());
                if (temp == null) {
                    node.setLeft(temp);
                } else {
                    node.getLeft().setRight(temp);
                }
                node = balanceTree(node);
            } else {
                return null;
            }
        }
        node.setHeight(calHeight(node));
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            node.setBalanceFactor(calHeight(node.getLeft())
                    - calHeight(node.getRight()));
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            node.setBalanceFactor(calHeight(node.getLeft()) + 1);
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            node.setBalanceFactor(-1 - calHeight(node.getRight()));
        } else {
            node.setBalanceFactor(0);
        }
        if (node.getBalanceFactor() == 2) {
            //left heavy
            if (node.getLeft() != null) {
                if (node.getLeft().getBalanceFactor() == -1) {
                    //left right rotation
                    node.setLeft(leftRotate(node.getLeft()));
                    node = rightRotate(node);
                } else if ((node.getLeft().getBalanceFactor() == 1)
                        || (node.getLeft().getBalanceFactor() == 0)) {
                    // right rotation
                    node = rightRotate(node);
                }
            }
        } else if (node.getBalanceFactor() == -2) {
            // right heavy
            if (node.getRight() != null) {
                if (node.getRight().getBalanceFactor() == 1) {
                    // right left rotation
                    node.setRight(rightRotate(node.getRight()));
                    node = leftRotate(node);
                } else if ((node.getRight().getBalanceFactor() == -1)
                        || (node.getRight().getBalanceFactor() == 0)) {
                    // left rotation
                    node = leftRotate(node);
                }
            }
        }

        return node;
    }

    /**
     * Recursively balances the tree from the node provided.
     * @param node the current node we are balancing
     * @return the balanced part of the tree (the balanced node)
     */
    private AVLNode<T> balanceTree(AVLNode<T> node) {
        if (node.getLeft() != null) {
            node.setLeft(balanceTree(node.getLeft()));
        }
        if (node.getRight() != null) {
            node.setRight(balanceTree(node.getRight()));
        }
        node.setHeight(calHeight(node));
        if ((node.getLeft() != null) && (node.getRight() != null)) {
            node.setBalanceFactor(calHeight(node.getLeft())
                    - calHeight(node.getRight()));
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            node.setBalanceFactor(calHeight(node.getLeft()) + 1);
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            node.setBalanceFactor(-1 - calHeight(node.getRight()));
        } else {
            node.setBalanceFactor(0);
        }
        if (node.getBalanceFactor() == 2) {
            //left heavy
            if (node.getLeft() != null) {
                if (node.getLeft().getBalanceFactor() == -1) {
                    //left right rotation
                    node.setLeft(leftRotate(node.getLeft()));
                    node = rightRotate(node);
                } else if ((node.getLeft().getBalanceFactor() == 1)
                        || (node.getLeft().getBalanceFactor() == 0)) {
                    // right rotation
                    node = rightRotate(node);
                }
            }
        } else if (node.getBalanceFactor() == -2) {
            // right heavy
            if (node.getRight() != null) {
                if (node.getRight().getBalanceFactor() == 1) {
                    // right left rotation
                    node.setRight(rightRotate(node.getRight()));
                    node = leftRotate(node);
                } else if ((node.getRight().getBalanceFactor() == -1)
                        || (node.getRight().getBalanceFactor() == 0)) {
                    // left rotation
                    node = leftRotate(node);
                }
            }
        }
        return node;
    }
    /**
     * Recursively finds the predecessor and removes it.
     * It saves the predecessor for later use.
     * @param node the current node we are inspecting
     * @param dummyNode the node to store the predecessor data
     * @return the node with the predecessor
     */
    private AVLNode<T> getPredecessor(AVLNode<T> node,
                                      AVLNode<T> dummyNode) {
        if (node.getRight() == null) {
            dummyNode.setData(node.getData());
            node = node.getLeft();
            return node;
        } else {
            node = getPredecessor(node.getRight(), dummyNode);
        }
        return node;
    }
    @Override
    public T get(T data) {
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

    @Override
    public boolean contains(T data) {
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

    /**
     * Recursively traverses the tree to find the data
     * @param current the current node we are inspecting for the data
     * @param data the data we are looking for
     * @return the data found in the node
     */
    private T findData(AVLNode<T> current, T data) {
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
    private List<T> preOrderTraversal(AVLNode<T> current, List<T> list) {
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
    private List<T> postOrderTraversal(AVLNode<T> current, List<T> list) {
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
    private List<T> inOrderTraversal(AVLNode<T> current, List<T> list) {
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
    private List<T> levelOrderTraversal(AVLNode current, List<T> list) {
        if (current != null) {
            List<AVLNode> queue = new ArrayList<AVLNode>();
            queue.add(current);
            while (!queue.isEmpty()) {
                AVLNode<T> temp = queue.get(0);
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
    private int calHeight(AVLNode<T> current) {
        int leftHeight = 0;
        int rightHeight = 0;
        if (current == null) {
            return -1;
        }
        if ((current.getLeft() == null) && (current.getRight() == null)) {
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
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}

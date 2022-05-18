/**
 * @author gsotelo
 * @version 1.0, 04/06/22
 * This class is used to build a binary tree
 * @param <T> takes a class as an argument
 */
public class TreeBuilder<T> {
    /**
     * Creates a binary tree using inputted data
     * @param data A generic array containing data to be stored in the nodes of the tree
     * @return a binary tree representation of the inputted data
     */
    public LinkedBinaryTree<T> buildTree(T[] data) {
        LinkedQueue<T> dataQueue = new LinkedQueue<>();
        LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<>();

        for (int i = 0; i < data.length; i++) {
            dataQueue.enqueue(data[i]);
        }

        LinkedBinaryTree<T> binaryTree = new LinkedBinaryTree<T>(dataQueue.dequeue());
        parentQueue.enqueue(binaryTree.getRoot());

        while (!dataQueue.isEmpty()) {
            T a = dataQueue.dequeue();
            T b = dataQueue.dequeue();
            BinaryTreeNode<T> parent = parentQueue.dequeue();

            if (a != null) {
                parent.setLeft(new BinaryTreeNode<>(a));
                parentQueue.enqueue(parent.getLeft());
            }

            if (b != null) {
                parent.setRight(new BinaryTreeNode<>(b));
                parentQueue.enqueue(parent.getRight());
            }
        }
        return binaryTree;
    }
}
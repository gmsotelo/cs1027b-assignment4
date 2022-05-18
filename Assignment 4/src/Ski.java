/**
 * @author gsotelo
 * @version 1.0, 04/06/22
 * This class finds the best path for the skier to take down the hill
 */
public class Ski {

    // The starting node of the tree/hill
    private BinaryTreeNode<SkiSegment> root;

    /**
     * Constructs a binary tree and stores data from array into the appropriate nodes
     * @param data String array containing data about the hill to be stored
     */
    public Ski(String[] data) {
        SkiSegment[] segments = new SkiSegment[data.length];

        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                if (data[i].contains("jump")) {
                    segments[i] = new JumpSegment(String.valueOf(i), data[i]);
                } else if (data[i].contains("slalom")) {
                    segments[i] = new SlalomSegment(String.valueOf(i), data[i]);
                } else
                    segments[i] = new SkiSegment(String.valueOf(i), data[i]);
            } else
                segments[i] = null;
        }

        // Builds the tree and stores the root node in the instance variable
        TreeBuilder<SkiSegment> tree = new TreeBuilder<>();
        LinkedBinaryTree<SkiSegment> hill = tree.buildTree(segments);
        root = hill.getRoot();
    }

    /**
     * Returns the starting node of the binary tree representation of the hill
     * @return the root node
     */
    public BinaryTreeNode<SkiSegment> getRoot() {
        return root;
    }

    /**
     * Add node to sequence and traverse to the next best node in the tree until the end of the tree is reached
     * @param node the current node in the hill
     * @param sequence the current path taken
     */
    public void skiNextSegment(BinaryTreeNode<SkiSegment> node, ArrayUnorderedList<SkiSegment> sequence) {
        sequence.addToRear(node.getData());
        if (!isLeaf(node)) {
            node = bestNext(node);
            skiNextSegment(node, sequence);
        }
    }

    /**
     * Checks whether the current node is a leaf node
     * @param node the current node
     * @return true if node is a leaf node, false otherwise
     */
    private boolean isLeaf(BinaryTreeNode<SkiSegment> node) {
        if (node == null)
            return true;
        return node.getLeft() == null && node.getRight() == null;
    }

    /**
     * Finds the best child node of the binary tree to travel to
     * @param node the current node
     * @return the next best node to travel to
     */
    private BinaryTreeNode<SkiSegment> bestNext(BinaryTreeNode<SkiSegment> node) {
        // If one child node is null, travel to the other child node regardless of the node
        if (node.getLeft() == null) {
            return node.getRight();
        } else if (node.getRight() == null) {
            return node.getLeft();
        }

        // Check if child nodes contain jumps and return the best node if true
        if (ifJump(node) != null) return ifJump(node);

        // Check if child nodes contain slaloms and return the best node if true
        if (ifSlalom(node) != null) return ifSlalom(node);

        // Travel to the right node as default
        return node.getRight();
    }

    /**
     * Determines the best node to travel to if one or more child nodes are jump segments
     * @param node the current node
     * @return the best node to travel to if one or more children are jump segments
     */
    private BinaryTreeNode<SkiSegment> ifJump(BinaryTreeNode<SkiSegment> node) {

        // If both nodes are jumps, choose the node with greater height or go right if both are equal
        if (node.getLeft().getData() instanceof JumpSegment && node.getRight().getData() instanceof JumpSegment) {
            JumpSegment left = (JumpSegment) node.getLeft().getData();
            JumpSegment right = (JumpSegment) node.getRight().getData();
            if (right.getHeight() == left.getHeight()) {
                return node.getRight();
            } else if (right.getHeight() > left.getHeight()) {
                return node.getRight();
            } else
                return node.getLeft();
        }

        // If exactly one node is a jump, take that path
        if (node.getLeft().getData() instanceof JumpSegment) {
            return node.getLeft();
        } else if (node.getRight().getData() instanceof JumpSegment) {
            return node.getRight();
        }

        // If both child nodes do not have a jump segment
        return null;
    }

    /**
     * Determines the best node to travel to if one or more child nodes are slaloms
     * @param node the current node
     * @return the best node to travel to if one or more child nodes are slalom segments
     */
    private BinaryTreeNode<SkiSegment> ifSlalom(BinaryTreeNode<SkiSegment> node) {
        // If both nodes are slaloms, take the node whose direction is leeward
        if (node.getLeft().getData() instanceof SlalomSegment && node.getRight().getData() instanceof SlalomSegment) {
            SlalomSegment left = (SlalomSegment) node.getLeft().getData();
            if (left.getDirection().equals("L")) {
                return node.getLeft();
            } else {
                return node.getRight();
            }
        }

        // If exactly one node is a slalom, take the slalom node only if its direction is leeward
        if (node.getLeft().getData() instanceof SlalomSegment || node.getRight().getData() instanceof SlalomSegment) {
            if (node.getLeft().getData() instanceof SlalomSegment) {
                SlalomSegment left = (SlalomSegment) node.getLeft().getData();
                if (left.getDirection().equals("L")) {
                    return node.getLeft();
                } else {
                    return node.getRight();
                }

            } else {
                SlalomSegment right = (SlalomSegment) node.getRight().getData();
                if (right.getDirection().equals("L")) {
                    return node.getRight();
                } else {
                    return node.getLeft();
                }
            }
        }

        // If both child nodes do not have a slalom segment
        return null;
    }
}
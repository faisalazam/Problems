package com.algorithms.misc;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTreeOperations {
    private TreeNode root;

    private BinaryTreeOperations() {
        this.root = null;
    }

    public static void main(String[] args) {
        int[] nums = {50, 10, 80, 5, 30, 70, 90, 20, 40, 85};
        BinaryTreeOperations binaryTreeOperations = new BinaryTreeOperations();
        for (int num : nums) {
            binaryTreeOperations.insert(num);
        }

        binaryTreeOperations.preOrderTraversal();
        binaryTreeOperations.inOrderTraversal();
        binaryTreeOperations.inOrderTraversalIteratively();
        binaryTreeOperations.postOrderTraversal();
        binaryTreeOperations.levelOrderTraversal();
        System.out.println("Min Value is => " + binaryTreeOperations.findMin());
        System.out.println("Max Value is => " + binaryTreeOperations.findMax());
        System.out.println("Height is => " + binaryTreeOperations.height());
        System.out.println("No. Of Leaves => " + binaryTreeOperations.countLeaves());
        System.out.println("kth Smallest is => " + binaryTreeOperations.kthSmallestElementInOrder(5));
        System.out.println("Is Binary Search Tree => " + binaryTreeOperations.isBinarySearchTree());
        System.out.println("No. Of BST Nodes in Range are => " + binaryTreeOperations.countBSTNodeInRange(40, 50));
    }


    private void insert(int data) {
        this.root = this.insert(data, this.root);
    }

    private TreeNode insert(int data, TreeNode root) {
        if (root == null) {
            root = new TreeNode(data);
        } else {
            if (data <= root.getData()) {
                root.setLeft(this.insert(data, root.getLeft()));
            } else {
                root.setRight(this.insert(data, root.getRight()));
            }
        }
        return root;
    }

    private void preOrderTraversal() {
        System.out.print("PreOrder Traversal => ");
        preOrderTraversal(this.root);
        System.out.println();
    }

    private void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print(root.getData() + ", ");
        preOrderTraversal(root.getLeft());
        preOrderTraversal(root.getRight());
    }

    private void inOrderTraversal() {
        System.out.print("InOrder Traversal => ");
        inOrderTraversal(this.root);
        System.out.println();
    }

    private void inOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.getLeft());
        System.out.print(root.getData() + ", ");
        inOrderTraversal(root.getRight());
    }

    private void postOrderTraversal() {
        System.out.print("PostOrder Traversal => ");
        postOrderTraversal(this.root);
        System.out.println();
    }

    private void postOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrderTraversal(root.getLeft());
        postOrderTraversal(root.getRight());
        System.out.print(root.getData() + ", ");
    }

    /*
     * 1) Create an empty stack.
     * 2) Initialize current node as root
     * 3) Push the current node to stack and set current = current->left until current is NULL
     * 4) If current is NULL and stack is not empty then
     *    a) Pop the top item from stack.
     *    b) Print the popped item, set current = popped_item->right
     *    c) Go to step 3.
     * 5) If current is NULL and stack is empty then we are done.
     */
    private void inOrderTraversalIteratively() {
        if (this.root == null) {
            return;
        }
        System.out.print("InOrder Traversal Iter. => ");

        TreeNode currentNode = this.root;
        Stack<TreeNode> stack = new Stack<>();

        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.getLeft();
            }
            currentNode = stack.pop();
            System.out.print(currentNode.getData() + ", ");
            currentNode = currentNode.getRight();
        }
        System.out.println();
    }

    private void levelOrderTraversal() {
        if (root == null) {
            return;
        }
        System.out.print("LevelOrder Traversal. => ");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(this.root);
        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();
            System.out.print(currentNode.getData() + ", ");
            if (currentNode.getLeft() != null) {
                queue.offer(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.offer(currentNode.getRight());
            }
        }
        System.out.println();
    }

    private int findMin() {
        if (this.root == null) {
            return Integer.MAX_VALUE;
        }
        TreeNode currentNode = this.root;
        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }
        return currentNode.getData();
    }

    private int findMax() {
        if (this.root == null) {
            return Integer.MIN_VALUE;
        }
        TreeNode currentNode = this.root;
        while (currentNode.getRight() != null) {
            currentNode = currentNode.getRight();
        }
        return currentNode.getData();
    }

    private int countLeaves() {
        return countLeaves(this.root);
    }

    private int countLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        } else if (root.isLeaf()) {
            return 1;
        }
        return countLeaves(root.getLeft()) + countLeaves(root.getRight());
    }

    private int height() {
        return height(this.root);
    }

    private int height(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.getLeft()), height(root.getRight()));
    }

    private int kthSmallestElementInOrder(int k) {
        TreeNode currentNode = root;
        Stack<TreeNode> stack = new Stack<>();

        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.getLeft();
        }

        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            if (--k <= 0) {
                break;
            }

            if (currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
                while (currentNode != null) {
                    stack.push(currentNode);
                    currentNode = currentNode.getLeft();
                }
            }
        }
        return currentNode == null ? -1 : currentNode.getData();
    }

    private boolean isBinarySearchTree() {
        return isBinarySearchTree(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBinarySearchTree(TreeNode root, int minValue, int maxValue) {
        if (root == null) {
            return true;
        } else if (root.getData() < minValue || root.getData() > maxValue) {
            return false;
        }
        return isBinarySearchTree(root.getLeft(), minValue, root.getData() - 1)
                && isBinarySearchTree(root.getRight(), root.getData() + 1, maxValue);
    }

    private int countBSTNodeInRange(int low, int high) {
        return countBSTNodeInRange(this.root, low, high);
    }

    private int countBSTNodeInRange(TreeNode root, int low, int high) {
        if (root == null) {
            return 0;
        } else if (root.getData() == high) {
            return 1 + countBSTNodeInRange(root.getLeft(), low, high);
        } else if (root.getData() == low) {
            return 1 + countBSTNodeInRange(root.getRight(), low, high);
        } else if (root.getData() > low && root.getData() < high) {
            return 1 + countBSTNodeInRange(root.getLeft(), low, high) + countBSTNodeInRange(root.getRight(), low, high);
        } else if (root.getData() < low) {
            return countBSTNodeInRange(root.getRight(), low, high);
        }
        return countBSTNodeInRange(root.getLeft(), low, high);
    }

    /*
     * Given two BST, Return elements of both BSTs in sorted form.
     * The values in the string are in the order of level order traversal of the tree where,
     * numbers denote node values, and a character “N” denotes NULL child.
     *
     * e.g. [1 2 3 N N 4 6 N 5 N N 7 N]
     */
    private int[] mergeTwoBSTsInSortedOrder(int[] bst1, int[] bst2) {
        int[] mergedBST = new int[bst1.length + bst2.length];
//5 3 6
//2 1 3


        return mergedBST;
    }

    private class TreeNode {
        private int data;
        private TreeNode left;
        private TreeNode right;

        TreeNode(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        int getData() {
            return this.data;
        }

        TreeNode getLeft() {
            return this.left;
        }

        TreeNode getRight() {
            return this.right;
        }

        void setLeft(TreeNode left) {
            this.left = left;
        }

        void setRight(TreeNode right) {
            this.right = right;
        }
    }
}

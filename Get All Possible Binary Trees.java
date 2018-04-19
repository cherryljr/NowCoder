/*
Find all possible binary trees with given PreOrder Traversal
Find all possible binary trees with given InOrder Traversal
 */

/**
 * Approach: Recursion
 * 主要就是利用 递归 的方式，在已有数据的基础上，通过 Split 建立出所有可能的树。
 * 而通过给定的 先序遍历 和 中序遍历 这两种数据建树的情况只在递归的 Split 是有所区别，
 * 其他基本相同。
 * 这其实考察的就是能否熟练地应用 递归 将树建立出来。
 * 注意在遍历所有可能性的时候，要考虑到子树为空的情况，因此要处理好边界条件
 */
import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        List<TreeNode> result = getAllTreesBasedPreOrder(arr, 0, arr.length - 1);
        for (TreeNode root : result) {
            BinaryTree.printInorder(root);
        }
        System.out.println();

        List<TreeNode> result2 = getAllTreesBasedInorder(arr, 0, arr.length - 1);
        for (TreeNode root : result2) {
            BinaryTree.printPreorder(root);
        }
    }

    /**
     * The question about traversal of tree, we want to get all possible in-order traversal with given pre-order traversal.
     * So just like traverse a tree, we can use Recursion to solve this problem (Divide).
     * Firstly, we can build all possible trees with given pre-order traversal.
     * Cuz we have the pre-order traversal, so we want choose first value as root and split the remaining nodes into two parts.
     * However, even those split elements can form multiple sub-trees,
     * therefore we choose a list as it's easy to append/remove/iterate
     */
    public static List<TreeNode> getAllTreesBasedPreOrder(int[] arr, int start, int end) {
        List<TreeNode> trees = new LinkedList<>();
        // If there can be no trees, return null
        if (start < 0 || end >= arr.length || start > end) {
            trees.add(null);
            return trees;
        }
        // Only one element
        if (start == end) {
            trees.add(new TreeNode(arr[start]));
            return trees;
        }

        // Otherwise, split them!
        // Notice: the subtree may be null
        for (int i = 0; i <= end - start; i++) {
            // Call recursive part
            // Notice start index is incremented by 1, and end index for left child is controlled by i
            List<TreeNode> leftSubTrees = getAllTreesBasedPreOrder(arr, start + 1, start + i);
            // RightChild is the remaining elements
            List<TreeNode> rightSubTrees = getAllTreesBasedPreOrder(arr, start + i + 1, end);
            // Now, looping left and right subtrees and connecting them to start root
            for (TreeNode left : leftSubTrees) {
                for (TreeNode right : rightSubTrees) {
                    // Every time, we make a copy of root
                    TreeNode tempRoot = new TreeNode(arr[start]);
                    tempRoot.left = left;
                    tempRoot.right = right;
                    trees.add(tempRoot);
                }
            }
        }

        return trees;
    }


    /**
     * Function for constructing all possible trees with
     * given inorder traversal stored in an array from
     * arr[start] to arr[end].
     */
    public static List<TreeNode> getAllTreesBasedInorder(int arr[], int start, int end) {
        // List to store all possible trees
        List<TreeNode> trees= new LinkedList<>();

        // if start > end then subtree will be empty so
        // returning NULL in the list
        if (start > end) {
            trees.add(null);
            return trees;
        }

        // Iterating through all values from start to end
        // for constructing left and right subtree recursively
        for (int i = start; i <= end; i++) {
            // Constructing left subtree
            List<TreeNode> leftSubTrees = getAllTreesBasedInorder(arr, start, i - 1);
            // Constructing right subtree
            List<TreeNode> rightSubTrees = getAllTreesBasedInorder(arr, i + 1, end);

            // Now looping through all left and right subtrees
            // and connecting them to ith root below
            for (TreeNode left : leftSubTrees) {
                for (TreeNode right : rightSubTrees) {
                    // Making arr[i] as root
                    TreeNode node = new TreeNode(arr[i]);
                    // Connecting left subtree
                    node.left = left;
                    // Connecting right subtree
                    node.right = right;

                    // Adding this tree to list
                    trees.add(node);
                }
            }
        }

        return trees;
    }

}

class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int value) {
        this.value = value;
    }
}

class BinaryTree {
    public static void printPreorder(TreeNode root) {
        preOrderTraversal(root);
        System.out.println();
    }

    private static void preOrderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        System.out.print(root.value + " ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }

    public static void printInorder(TreeNode root) {
        inorderTraversal(root);
        System.out.println();
    }

    private static void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.print(root.value + " ");
        inorderTraversal(root.right);
    }
}

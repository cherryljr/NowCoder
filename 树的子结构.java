/*
输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 */

/**
 * Approach: Recursion
 * 与 Subtree of Another Tree 十分类似
 *  https://github.com/cherryljr/LeetCode/blob/master/Subtree%20of%20Another%20Tree.java
 *
 * 区别在于本题求的是：子结构而不是子树，因此匹配条件更加宽松了。
 * 即只需要被包含即可。因此 Tree2 已经全部被遍历完后，Tree1 的子节点可以不为空。
 */

/**
 * public class TreeNode {
 *   int val = 0;
 *    TreeNode left = null;
 *    TreeNode right = null;
 *    public TreeNode(int val) {
 *        this.val = val;
 *    }
 * }
 */
public class Solution {
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        // 当Tree1和Tree2都不为零的时候，才进行比较。否则直接返回false
        if (root1 == null || root2 == null) {
            return false;
        }

        // 如果两棵树完全相同，或者 Tree2 在 Tree1 的左子树或者右子树中，均返回 true
        if (isSameTree(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2)) {
            return true;
        }
        return false;
    }

    private boolean isSameTree(TreeNode node1, TreeNode node2) {
        // 如果Tree2已经遍历完了都能对应的上，返回true
        if (node2 == null) {
            return true;
        }
        // 如果Tree2还没有遍历完，Tree1却遍历完了。返回false
        if (node1 == null) {
            return false;
        }
        // 如果当前节点没有对应上，返回false
        if (node1.val != node2.val) {
            return false;
        }
        // 如果当前节点对应的上，那么就分别去子节点里面匹配
        return isSameTree(node1.left, node2.left) && isSameTree(node1.right, node2.right);
    }
}
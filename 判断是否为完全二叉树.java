任意的一个二叉树，都可以补成一个满二叉树。但是这样中间就会有很多空洞。
在广度优先遍历的时候，如果是满二叉树，或者完全二叉树，这些空洞是在广度优先的遍历的末尾。
所以，但我们遍历到空洞的时候，整个二叉树就已经遍历完成了。
而如果，是非完全二叉树，我们遍历到空洞的时候，就会发现，空洞后面还有没有遍历到的值。
这样，只要根据是否遍历到空洞，整个树的遍历是否结束来判断是否是完全的二叉树。

/*
给定一颗树，判断其是否为完全二叉树
*/

import java.util.*;

class TreeNode {
    int val;
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode(int val) {
    this.val = val;
    }
}

public class CheckCompletion {
    public boolean checking(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        TreeNode node;
        
        // 进行广度优先遍历（层次遍历），并把NULL节点也放入队列  
        queue.add(root);
        while ((node = queue.poll()) != null) {
            queue.offer(node.left);
            queue.offer(node.right);
        }
        
        // 判断是否还有未被访问到的节点  
        while (!queue.isEmpty()) {
            node = queue.poll();
            // 有未访问到的的非NULL节点，则树存在空洞，为非完全二叉树  
            if (node != null) {
                return false;
            }
        }
        
        return true;
    }
}


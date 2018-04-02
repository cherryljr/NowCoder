/*
Given an array of integers. find the maximum XOR subarray value in given array.
Expected time complexity O(n).

Examples:
Input: arr[] = {1, 2, 3, 4}
Output: 7
The subarray {3, 4} has maximum XOR value

Input: arr[] = {8, 1, 2, 12, 7, 6}
Output: 15
The subarray {1, 2, 12} has maximum XOR value

Input: arr[] = {4, 6}
Output: 6
The subarray {6} has maximum XOR value
 */

/**
 * Approach: Trie
 * 对于 Subarray 问题，我们可以考虑 PreSum, Two Pointer, DP 等方法，
 * 经过分析，我们发现也就只有 PreXOR(EOR) 的方法可以用一用。
 * 总体思路为：求所有以 i 作为结尾的 最大异或 子数组。那么答案必定在这里面。
 * 但是我们发现在本题中无法利用 HashMap 实现加速。（因为我们没办法通过它来求得 最大异或和）
 * 因此我们需要考虑另外的一种数据机构来帮我们实现加速。而这里使用的就是 Trie Tree.
 *
 * 我们在 Trie Tree 中放入了放入了 从0到i 的异或值 EOR.
 * 那么根据 异或运算 的性质： A^B=C => B^C=A
 * 我们可以通过 0...i 的异或和计算出 任意 j...i 的异或值：
 *      XOR[j...i] = XOR[0...i] ^ XOR[0...j-1]
 *
 * 那么Trie如何实现快速得到 异或后的最大值 呢？（本题的核心部分）
 * 我们是按照这样的策略（其实是一个贪心的做法）：
 *  对于一个 int 类型，总共有 32 位。因此我们 Trie Tree 的高度为 32.
 *  当我们想要查询如何获得最大的异或值时，
 *  我们可以 从高位开始 向低位逐位查找 最佳异或值。遵循着 首先满足高位 的做法依次向下查询。
 *  同时：我们还需要注意符号位，这里比较特殊，因为 0 代表正，1代表负。
 *  所以对于 符号位 我们要取与原来位置上 相同 的数，
 *  而对于其他位我们要去与原来位置上 不同的数（异或1后的数）。
 *  如果 取不到 最佳值的话，我们就只能取现有的值了（总共就两个...）
 *  最后假设我们得到的 最大异或和 为：EOR[x]^EOR[y]，那么就说明 最大异或和子数组为：[x+1...y]
 *  这样查找下来我们需要循环 32 次，时间复杂度为 O(1)
 * 而我们需要查找 n 个数，因此总体时间复杂度为 O(n)
 */
class Solution {
    public int maxSubarrayXOR(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Trie trie = new Trie();
        // 初始化 trie，将 0 将入到 trie 中，因为 X^0=X
        trie.add(0);
        int eor = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            eor ^= nums[i];
            // 在 Trie 中寻找与 eor 异或起来最大的元素，返回异或之后的结果
            max = Math.max(max, trie.maxXOR(eor));
            // 将 eor 加入到 Trie Tree 中
            trie.add(eor);
        }

        return max;
    }

    class TrieNode {
        // Trie Tree 的节点，因为是 2进制，所以我们只需要 0,1 两个节点即可
        TrieNode[] child;

        TrieNode() {
            child = new TrieNode[2];
        }
    }

    class Trie {
        TrieNode root;

        Trie() {
            root = new TrieNode();
        }

        // 将 nums[i] 加入到 Trie Tree 中
        public void add(int num) {
            TrieNode curr = root;
            for (int i = 31; i >= 0; i--) {
                int path = ((num >> i) & 1);    // 获得第 i 位上的值
                if (curr.child[path] == null) {
                    curr.child[path] = new TrieNode();
                }
                curr = curr.child[path];
            }
        }

        public int maxXOR(int num) {
            TrieNode curr = root;
            int rst = 0;
            for (int i = 31; i >= 0; i--) {
                // 获得第 i 位上的值
                int path = ((num >> i) & 1);
                // 根据 path，我们需要得到相应的最佳 异或值
                // 注意：如果是 符号位 的话，我们取与path相同的值，否则取与 path 不同的值
                int best = i == 31 ? path : (path ^ 1);
                // 判断 curr.child[best] 节点是否存在，存在的话我们就能取到最佳值
                // 否则只能取另外一个值了
                best = curr.child[best] != null ? best : (best ^ 1);
                // 将 rst 的第 i 位置为 path^best
                rst |= ((path ^ best) << i);
                curr = curr.child[best];
            }
            return rst;
        }
    }
}
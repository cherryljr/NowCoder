/*
牛牛现在有一个n个数组成的数列,牛牛现在想取一个连续的子序列,并且这个子序列还必须得满足:
最多只改变一个数,就可以使得这个连续的子序列是一个严格上升的子序列,牛牛想知道这个连续子序列最长的长度是多少。

输入描述:
输入包括两行,第一行包括一个整数n(1 ≤ n ≤ 10^5),即数列的长度;
第二行n个整数a_i, 表示数列中的每个数(1 ≤ a_i ≤ 10^9),以空格分割。

输出描述:
输出一个整数,表示最长的长度。

示例1
输入
6
7 2 3 1 5 6

输出
5
 */

/**
 * Approach: DP
 * 该题是在 Longest Increasing Continuous Subsequence 的基础上改编过来的。
 * https://github.com/cherryljr/LintCode/blob/master/Longest%20Increasing%20Continuous%20Subsequence.java
 * 因此题目的主要核心是相同的，我们只需要加入一些条件判断便能够 AC 这道题目。
 *
 * 首先我们需要有两个 DP 数组分别用于储存 从左往右 和 从右往左 的 LICS.
 * 它们分别为:
 * dpLeft[i], 它表示以 nums[i] 作为结尾的最长递增子串的长度;
 * dpRight[i], 它表示以 nums[i] 作为起始的最长递增子串的长度.
 * 因为我们可以修改一个位置的值，因此：
 *  当 num[i - 1] 与 nums[i + 1] 之间至少相差 2 时，我们可以通过修改 nums[i] 使得 nums[i-1] 和 nums[i+1] 也组成连续递增序列。
 *  值为: dpLeft[i - 1] + dpRight[i + 1] + 1
 *  若不成立，我们取 dpLeft[i - 1] 和 dpRight[i + 1] 中的较大值，然后修改 nums[i]. 组成一个 LICS.
 *  值为: Math.max(dpLeft[i - 1], dpRight[i + 1]) + 1
 */
import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = sc.nextInt();
        }

        // 记录正序遍历得到的各个递增子序列长度
        int[] dpLeft = new int[len];
        // 记录逆序遍历得到的各个递增子序列长度
        int[] dpRight = new int[len];

        // 正向统计连续递增序列的长度（以第i位数结尾的递增子序列）
        dpLeft[0] = 1;
        for (int i = 1; i < len; i++) {
            dpLeft[i] = nums[i - 1] < nums[i] ? dpLeft[i - 1] + 1 : 1;
        }

        // 逆向统计连续递增序列的长度（以第i位数开始的递增子序列）
        dpRight[len - 1] = 1;
        for (int i = len - 2; i >= 0; i--) {
            dpRight[i] = nums[i] < nums[i + 1] ? dpRight[i + 1] + 1 : 1;
        }

        // 最小的序列长度为1，所以把 rst 初始化为1
        int rst = 1;
        for (int i = 1; i < len - 1; i++) {
            // 对于每一位置i有左侧到它的最长连续递增序列dpLeft[i], 右侧有连续递增子序列长度dpRight[i]
            // 加1是因为 nums[i] 可进行修改从而组成 LICS 的一部分, 因此长度要算上第i位数。
            rst = Math.max(rst, Math.max(dpLeft[i - 1], dpRight[i + 1]) + 1);
            if (nums[i + 1] - nums[i - 1] >= 2) {
                // 第i+1位 与 第i-1位 至少相差2，则可以修改第i位数，使第i-1、i、i+1也可以组成连续递增序列。
                rst = Math.max(rst, dpLeft[i - 1] + dpRight[i + 1] + 1);
            }
        }

        System.out.println(rst);
    }
}

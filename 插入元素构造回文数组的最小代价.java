/*
题目描述：
对于一个给定的正整数组成的数组a[], 如果将a倒序后数字的排列与a完全相同，
我们称这个数组为“回文”的。
例如：[1, 2, 3, 2, 1]的倒叙就是它自己，所以是一个回文数组；
而[1, 2, 3, 1, 2]的倒序是[2, 1, 3, 2, 1],所以不是一个回文的数组。
对于一个给定的正整数组成的数组，如果我们向其中某些特定的位置插入一些正整数，
那么我们总能构造出一个回文的数组。

输入一个正整数组成的数组，要求你插入一些数字，使其变成回文的数组。
且数组中所有数字的和尽可能小。输出这个插入后数组中元素的和。

输入描述：
输入数据由两行组成：第一行包含一个正整数 L,表示数组 a 的长度。
第二行包含 L 个正整数，表述数组a。

输出描述：
输出一个整数，表示通过插入若干个正整数，使得数组 a 回文后，
数组 a 的数字和的最小值。
*/

/**
 * 该题与 删除最少的字符构成回文数组（实质为 LCS 问题）具有一定的相似性。
 * https://www.nowcoder.com/questionTerminal/28c1dc06bc9b4afd957b01acdf046e69
 *
 * 但是二者之间仍然有一定的区别，接下来我们来分析这两道题目。
 * （为了方便起见，删除最少的字符构成回文数组 该题将简称为“删除题”）
 * 这两道题目联系在于都可以通过 DP 来解决，区别在于:
 * "删除题"中，我们想要求的最终结果是在原本字符串基础上删除一部分得到的，
 * 即因为某一些不必要的字符串的插入，导致了原本是回文字符串的答案被分割成了题目给出的字符串。
 * 可到此很明显，那就是我们想要求的是原本字符串的 SubSequence。
 * 因此我们可以简单地通过 Reverse + LCS 的方法求得结果。
 *
 * 本题（"插入题"）与“删除题”的区别就在于，题目所给出的字符串中没有一个字符是多余的。
 * 它们都是最终结果的一部分。因此我们无法再通过 Reverse + LCS 的方法解决，因为它本身就是一个 SubSequence。
 * 但是我们仍然可以通过 DP 来解决该题。解法如下：
 * State:
 * dp[i][j] 表示 i~j 段需要插入数值的最小和。
 *
 * Function:
 * 当 nums[i] == nums[j] 时，本身即为回文数组，无需插入新数字。
 * dp[i][j] = dp[i+1][j-1];
 * 当 nums[i] != nums[j] 时，插入二者中的最小值，构成回文数组。
 * dp[i][j] = Math.min(dp[i+1][j] + nums[i-1], dp[i][j-1] + nums[j-1]);
 *
 * Answer:
 * 原数组之和 sum + 插入数字之和的最小值 dp[1][n]
 *
 * PS. 这两题的解法都可以被应用于 Longest Palindrome Substring 中。
 * 但是都不是很好的解法。对于这道题目，有兴趣的可以看详细分析：
 * https://github.com/cherryljr/LintCode/blob/master/Longest%20Palindromic%20Substring.java
 * https://github.com/cherryljr/LintCode/blob/master/Manacher%20Template.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        sc.close();
        System.out.println(minInsert(nums, n));
    }

    public static int minInsert(int[] nums, int n) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        sum += helper(nums, n);

        return sum;
    }

    public static int helper(int[] nums, int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j <= n; j++) {
                if (nums[i - 1] == nums[j - 1]) {
                    dp[i][j] = dp[i+1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i+1][j] + nums[i-1], dp[i][j-1] + nums[j-1]);
                }
            }
        }

        return dp[1][n];
    }
}


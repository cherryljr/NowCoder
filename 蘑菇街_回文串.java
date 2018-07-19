/*
回文串
时间限制：1秒
空间限制：32768K

给定一个字符串，问是否能通过添加一个字母将其变为回文串。

输入描述:
一行一个由小写字母构成的字符串，字符串长度小于等于10。

输出描述:
输出答案(YES\NO).

输入例子1:
coco

输出例子1:
YES
 */

/**
 * Approach: Recursion
 * 根据数据规模，第一反应就是使用递归来解决。
 * 具体解析看代码注释即可。
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            System.out.println(canBeTransformed(str, 0, str.length() - 1, false) ? "YES" : "NO");
        }
    }

    private static boolean canBeTransformed(String str, int start, int end, boolean flag) {
        if (start >= end) {
            return true;
        }

        // 如果两个字符相等，则可以进一步缩小
        if (str.charAt(start) == str.charAt(end)) {
            return canBeTransformed(str, start + 1, end - 1, flag);
        } else if (!flag) {
            // 如果不相等，并且还未插入过字符，则可以选择插入与 start / end 位置相同的字符，去尝试构成回文串
            flag = true;
            return canBeTransformed(str, start + 1, end, flag) || canBeTransformed(str, start, end - 1, flag);
        } else {
            return false;
        }
    }
}

/**
 * Approach 2: DP (Longest Common SubSequence)
 * 因为无后效性，所以这道题目实际上是一道 DP 问题（给出这个数据量真的是...）
 * 看到回文串，我们会下意识地将它进行 reverse，然后求原串和反串的 最长公共子序列 ，
 * 如果 最长公共子序列的长度 是 原字符串的长度减一 ，那么就是可行的
 * （题目明确要求添加一个字符）
 *
 * 时间复杂度：O(MN)
 * 空间复杂度：O(MN)
 *
 * Longest Common Subsequence:
 *  https://github.com/cherryljr/LintCode/blob/master/Longest%20Common%20Subsequence.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            StringBuilder reverseStr = new StringBuilder(str).reverse();
            int len = getLCS(str, reverseStr.toString());
            System.out.println(len + 1 == str.length() ? "YES" : "NO");
        }
    }

    private static int getLCS(String str1, String str2) {
        int n = str1.length(), m = str2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }
}
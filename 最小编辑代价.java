/*
对于两个字符串A和B，我们需要进行插入、删除和修改操作将A串变为B串，定义c0，c1，c2分别为三种操作的代价.
请设计一个高效算法，求出将A串变为B串所需要的最少代价。
给定两个字符串A和B，及它们的长度和三种操作代价，请返回将A串变为B串所需要的最小代价。
保证两串长度均小于等于300，且三种代价值均小于等于100。

测试样例：
"abc",3,"adc",3,5,3,100
返回：8
 */

/**
 * 经典的动态规划题型
 * State:
 * 建立一个二维数组 dp[A.length() + 1][B.length() + 1].
 * dp[i][j]表示将 A 中前i个字符转成 B 中前j个字符所需要的最小代价。
 *
 * Initiailize:
 * dp[0][i]：当 A 为空串，转换成 B 需要的代价为：c_insert * i;
 * dp[i][0]: 当 B 为空串，转换成 B 需要的代价为： c_delete * i;
 *
 * Function:
 * 计算出 dp[i][j] 总共有 4 中情况：
 *  1. 先将 A[0...i-1] 编辑成 A[0...i-2],也就是删除字符 A[i-1],然后由 A[0...i-2] 编辑成 B[0...j-1].
 *  dp[i-1][j] 就表示将 A[0...i-2] 编辑成 B[0...j-1] 的最小代价，即可能等于 dp[i-1][j] + c_delete.
 *  2. 先将 A[0...i-1] 编辑成 B[0...j-2],然后将 B[0...j-2] 插入字符 B[j-1] 编辑成 B[0...j-1].
 *  即相当于在 A[0...i-1] 中插入 B[j-1].
 *  dp[i][j-1] 就表示将 A[0...i-1] 编辑成 B[0...j-2] 的最小代价，即可能等于 dp[i][j-1] + c_insert.
 *  3. 如果 A[i-1] != B[j-1]. 先把 A[0...i-2] 的部分变成 B[0...j-2],然后把字符 A[i-1] 替换成 B[j-1],
 *  这样就把 A[0...i-1] 编辑成了 B[0...j-1].
 *  dp[i-1][j-1] 表示将 A[0...i-2] 编辑成了 B[0...j-2] 的最小代价，即可能等于 dp[i-1][j-1] + c_change.
 *  4. 如果 A[i-1] == B[j-1]. 先把 A[0...i-2] 的部分变成 B[0...j-2],又因为此时 A[i-1] 已经等于 B[j-1].
 *  dp[i-1][j-1] 表示将 A[0...i-2] 编辑成了 B[0...j-2] 的最小代价,即可能等于 dp[i-1][j-1].
 *  以上四种情况取最小值作为 dp[i][j] 的结果.
 *
 * Answer:
 * dp[A.length()][B.length()]
 */

import java.util.*;

public class MinCost {
    public int findMinCost(String A, int n, String B, int m, int c_insert, int c_delete, int c_change) {
        if (A == null || A.length() == 0) {
            return c_insert * B.length();
        }
        if (B == null || B.length() == 0) {
            return c_delete * A.length();
        }

        // Initialize the dp array
        int[][] dp = new int[A.length() + 1][B.length() + 1];
        // 初始化矩阵第一列,代表将字符串A中所有的字符全部删掉
        for (int i = 0; i <= A.length(); i++) {
            dp[i][0] = c_delete * i;
        }
        // 初始化矩阵第一行，代表在空串A中插入i个字符
        for (int i = 0; i <= B.length(); i++) {
            dp[0][i] = c_insert * i;
        }

        // Function
        for (int i = 1; i <= A.length(); i++) {
            for (int j = 1; j <= B.length(); j++) {
                if (A.charAt(i - 1) == B.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1] + c_change, Math.min(dp[i-1][j] + c_delete, dp[i][j-1] + c_insert));
                }
            }
        }

        return dp[A.length()][B.length()];
    }
}

/*
Given a N X N  matrix Matrix[N][N] of positive integers.
There are only three possible moves from a cell Matrix[r][c].
    1. Matrix[r+1][c]
    2. Matrix[r+1][c+1]
    3. Matrix[r+1][c-1]
Starting from any column in row 0, return the largest sum of any of the paths up to row N-1.

Input:
The first line of the input contains an integer T denoting the number of test cases.
The description of T test cases follows.
The first line of each test case contains a single integer N denoting the order of matrix.
Next line contains N*N integers denoting the elements of the matrix in row-major form.

Output:
Output the largest sum of any of the paths starting from any cell of row 0 to any cell of row N-1.
Print the output of each test case in a new line.

Constraints:
1<=T<=20
2<=N<=20
1<=Matrix[i][j]<=1000 (for all 1<=i<=N && 1<=j<=N)

Example:
Input:
1
2
348 391 618 193

Output:
1009

Explanation: In the sample test case, the path leading to maximum possible sum is 391->618.  (391 + 618 = 1009)

OJ地址：https://practice.geeksforgeeks.org/problems/path-in-matrix/0#ExpectOP
 */

/**
 * Approach: Matrix Dp
 * 矩阵 DP 的问题，与 基本相同。
 * dp[i][j] 仅仅由 上一行的 3个 元素值所决定。
 * 因此我们可以利用 滚动数组 对其 空间复杂度 进行优化。
 * 根据状态方程分析可得：我们只需要存储 两行的结果 即可。
 * 
 * 时间复杂度：O(n^2)； 空间复杂度：O(n)
 */

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
    public static void main (String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int T = sc.nextInt();
        for (int i = 0; i < T; i++) {
            int n = sc.nextInt();
            int[][] matrix = new int[n][n];
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    matrix[row][col] = sc.nextInt();
                }
            }
            System.out.println(getResult(matrix));
        }
    }

    private static int getResult(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int n = matrix.length;
        // Using sliding array to optimize the extra space
        int[][] dp = new int[2][n];
        // Initialize the first row
        for (int i = 0; i < n; i++) {
            dp[0][i] = matrix[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // get the max value of three moving method and plus matrix[i][j]
                dp[i & 1][j] = Math.max(dp[(i-1) & 1][j],   // the first move method
                        Math.max(dp[(i-1) & 1][j + 1 >= n ? n - 1 : j + 1], // the second move method
                                dp[(i-1) & 1][j - 1 < 0 ? 0 : j - 1])) + matrix[i][j];  // the third move method
            }
        }

        int max = Integer.MIN_VALUE;
        // Get the result
        for (int i : dp[(n - 1) & 1]) {
            max = Math.max(max, i);
        }
        return max;
    }
}
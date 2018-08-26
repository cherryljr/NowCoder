/*
Description
There is a straight highway with villages alongside the highway.
The highway is represented as an integer axis, and the position of each village is identified with a single integer coordinate.
There are no two villages in the same position.
The distance between two positions is the absolute value of the difference of their integer coordinates.

Post offices will be built in some, but not necessarily all of the villages.
A village and the post office in it have the same position. For building the post offices,
their positions should be chosen so that the total sum of all distances between each village and its nearest post office is minimum.

You are to write a program which, given the positions of the villages and the number of post offices,
computes the least possible sum of all distances between each village and its nearest post office.

Input
Your program is to read from standard input.
The first line contains two integers: the first is the number of villages V, 1 <= V <= 300,
and the second is the number of post offices P, 1 <= P <= 30, P <= V.
The second line contains V integers in increasing order.
These V integers are the positions of the villages. For each position X it holds that 1 <= X <= 10000.

Output
The first line contains one integer S, which is the sum of all distances between each village and its nearest post office.

Sample Input
10 5
1 2 3 6 7 9 11 22 44 50
Sample Output
9
 */

/**
 * Approach: Interval DP
 * 题意：
 * 有 n 个村庄，要求选定其中 p 个建立邮局，每个村庄使用离他最近的那个邮局，求一种方案使最终的距离总和最小;
 *
 * 区间DP的问题，与 Floyd-WashAll 算法的思想有点类似。
 * 思路：
 * 1. 题目中有 2 个“最”：最近的邮局、距离总和最小 ->
 * 第一个最很好解决，我们可以采取预处理的方式 cost[i, j] 即 i, j 之间建立一个邮局的最近距离;
 * 2. 然后我们需要解决总距离最小：
 *  利用 dp[i, j] 代表 前 i 个邮局建立在前 j 个村庄的最小距离总和。
 *  相当于是这 i 个邮局各有一片管辖区域，如何划分使总距离最小：
 * 3. 关于区间划分，这让人想到任务调度类似的题目，推敲下有：
 *  dp[i, j] = min(dp[i, j], dp[i-1][k] + cost[k+1, j]);
 *
 * 时间复杂度：O(n^3)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader in = new InputReader();
        while (in.hasNext()) {
            int village = in.nextInt(), office = in.nextInt();
            int[] pos = new int[village];
            for (int i = 0; i < village; i++) {
                pos[i] = in.nextInt();
            }

            int[][] cost = new int[village][village];
            for (int i = 0; i < village; i++) {
                for (int j = i; j < village; j++) {
                    cost[i][j] = calCost(pos, i, j);
                }
            }
            System.out.println(getResult(cost, village, office));
        }
    }

    private static int calCost(int[] pos, int i, int j) {
        int distance = 0;
        while (i < j) {
            distance += pos[j] - pos[i];
            i++;
            j--;
        }
        return distance;
    }

    private static int getResult(int[][] cost, int village, int office) {
        int[][] dp = new int[office + 1][village + 1];
        for (int i = 0; i <= office; i++) {
            Arrays.fill(dp[i], 0x3f3f3f3f);
        }
        dp[0][0] = 0;

        for (int i = 1; i <= office; i++) {
            for (int j = i; j <= village; j++) {
                for (int k = 0; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + cost[k][j - 1]);
                }
            }
        }
        return dp[office][village];
    }

    static class InputReader {
        BufferedReader buffer;
        StringTokenizer token;

        InputReader() {
            buffer = new BufferedReader(new InputStreamReader(System.in));
        }

        boolean hasNext() {
            while (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(buffer.readLine());
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        String next() {
            if (hasNext()) return token.nextToken();
            return null;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        BigDecimal nextBigDecimal() {
            return new BigDecimal(next());
        }
    }

}
/*
妞妞参加了Nowcoder Girl女生编程挑战赛, 但是很遗憾, 她没能得到她最喜欢的黑天鹅水晶项链。
于是妞妞决定自己来制作一条美丽的项链。一条美丽的项链需要满足以下条件:
1、需要使用n种特定的水晶宝珠
2、第i种水晶宝珠的数量不能少于li颗, 也不能多于ri颗
3、一条美丽的项链由m颗宝珠组成
妞妞意识到满足条件的项链种数可能会很多, 所以希望你来帮助她计算一共有多少种制作美丽的项链的方案。

输入描述:
输入包括n+1行, 第一行包括两个正整数(1 <= n <= 20, 1 <= m <= 100), 表示水晶宝珠的种数和一条美丽的项链需要的水晶宝珠的数量。
接下来的n行, 每行两个整数li, ri(0 <= li <= ri <= 10), 表示第i种宝珠的数量限制区间。

输出描述:
输出一个整数, 表示满足限定条件的方案数。保证答案在64位整数范围内。
示例1
输入
3 5
0 3
0 3
0 3
输出
12

备注:
对于两种方案，当有任意一种水晶宝珠个数不同，就视为两种不同方案。

OJ地址：https://www.nowcoder.com/questionTerminal/e7e0230b12de4239a7f547a01d731522
 */

/**
 * Approach: Backpack (DP)
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] jewelry = new int[n][2];
        long[][] dp = new long[n][m + 1];
        for (int i = 0; i < n; i++) {
            jewelry[i][0] = sc.nextInt();
            jewelry[i][1] = sc.nextInt();
        }

        // Initialize
        for (int i = jewelry[0][0]; i <= jewelry[0][1]; i++) {
            dp[0][i] = 1;
        }
        // Function
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = jewelry[i][0]; k <= jewelry[i][1]; k++) {
                    if (j >= k) {
                        dp[i][j] += dp[i - 1][j - k];
                    }
                }
            }
        }

        System.out.println(dp[n - 1][m]);
    }
}
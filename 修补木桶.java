/*
题目: 修补木桶
时间限制:10000ms
单点时限:1000ms
内存限制:256MB

描述
一只木桶能盛多少水，并不取决于桶壁上最高的那块木板，而恰恰取决于桶壁上最短的那块。
已知一个木桶的桶壁由N块木板组成，第i块木板的长度为Ai。
现在小Hi有一个快捷修补工具，每次可以使用修补工具将连续的不超过L块木板提高至任意高度。
已知修补工具一共可以使用M次(M*L<N)，如何修补才能使最短的那块木板最高呢？
注意: 木板是环形排列的，第N-1块、第N块和第1块也被视为连续的。

输入
第1行：3个正整数，N, M, L。分别表示木板数量，修补工具使用次数，修补工具每次可以同时修补的木板数。 1≤N≤1,000，1≤L≤20，M*L<N
第2行：N个正整数，依次表示每一块木板的高度Ai，1≤Ai≤100,000,000

输出
第1行：1个整数。表示使用修补工具后，最短木块的所能达到的最高高度

样例说明
第一个修补工具覆盖[2 3 4]
第二个修补工具覆盖[5 8 1]

样例输入
8 2 3
8 1 9 2 3 4 7 5
样例输出
7
 */

/**
 * Approach: Binary Search
 * 本题可以使用二分答案的思路解决。
 * 我们考虑这样一个问题，假设最终最短的木板长度至少是K，最小需要使用修复工具几次？
 * 为了描述方便我们将这个最少次数记作F(K)。
 * 于是我们的问题变成求出最大的K，满足F(K) <= M。
 * 如果我们将F(K)看成一个函数，随着K增加，我们要修复的木板越来越多，显然F(K)也会越来越大。
 * 换句话说F(K)是单调递增的。我们可以用二分来求出最大的K。
 *
 * 考虑 1 <= Ai <= 100000000，答案也一定在[1, 100000000]之间。在这个范围内二分的复杂度是O(log(Max{Ai}))。
 * 然后我们的问题是对于确定的K，计算F(K)。
 * 当 K 确定时，我们就可以确定哪些木板需要被修复(Ai < K的木板)。
 * 由于木桶是环形的，我们需要枚举起点，复杂度O(N)。
 * 一旦起点确定，就可以贪心求出每一次修复的位置。从而计算出F(K)，复杂度O(N)。
 * 于是总复杂度是O(log(Max{Ai})N^2)
 *
 * OJ地址：https://hihocoder.com/contest/hiho193/problem/1
 *
 * Binary Search Template:
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 木板数目
        int m = sc.nextInt();   // 可以维修的次数
        int l = sc.nextInt();   // 一次性可以维修的木板数
        int [] bricks = new int[n];
        int maxHeight = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            bricks[i] = sc.nextInt();
            maxHeight = Math.max(maxHeight, bricks[i]);
        }

        // 采用了寻找 上界 的二分查找法
        int left = -1, right = maxHeight;
        while (left < right) {
            int mid = left + ((right - left + 1) >> 1);
            if (minFixTimes(n, l, mid, bricks) <= m) {
                // 当 最少修补次数 <= m 时，说明 最短木块的修补高度还能继续增加
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(left);
    }

    private static int minFixTimes(int n, int l, int mid, int[] bricks) {
        int minTime = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {   // 枚举起点位置
            int num = 0;
            for (int j = 0; j < n; j++) {   // 枚举距离起点位置的距离
                int k = (i + j) % n;
                if (bricks[k] < mid) {  // 小于 mid 则说明需要修补
                    num++;
                    j += (l - 1);
                }
            }
            minTime = Math.min(minTime, num);
        }
        return minTime;
    }
}













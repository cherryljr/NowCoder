/*
小易参加了一个骰子游戏,这个游戏需要同时投掷n个骰子,每个骰子都是一个印有数字1~6的均匀正方体。
小易同时投掷出这n个骰子,如果这n个骰子向上面的数字之和大于等于x,小易就会获得游戏奖励。
小易想让你帮他算算他获得奖励的概率有多大。

输入描述:
输入包括两个正整数n和x(1 ≤ n < 25, 1 ≤ x < 150),分别表示骰子的个数和可以获得奖励的最小数字和。
输出描述:
输出小易可以获得奖励的概率。
如果概率为1,输出1,如果概率为0,输出0,其他以最简分数(x/y)的形式输出。

示例1
输入
3 9
输出
20/27
 */

/**
 * Approach 1: DP (01-Backpack)
 * 这个问题实际上就是一个 01背包问题。
 * 即求使用 n 个数，组成 m 的方案个数。
 *
 * n个骰子的和最大为6n 最小为n
 * 用 dp[n][m] 表示投 前n个骰子 时点数之和为 m 的个数
 * 投第n个骰子的点数之和只与第n-1个骰子有关
 *  dp(n,m)=dp(n-1,m-1)+dp(n-1,m-2)+dp(n-1,m-2)+dp(n-1,m-3)+dp(n-1,m-4)+dp(n-1,m-5)+dp(n-1,m-6)
 * 表示本轮点数之和为n的次数等于上一轮点数和为n-1,n-2,n-3,n-4,n-5,n-6出现的次数之和
 * 例如:
 *  dp(2,6)=dp(1,5)+dp(1,4)+dp(1,3)+dp(1,2)+dp(1,1)+dp(1,0)=1+1+1+1+1=5
 *  dp(3,6)=dp(2,5)+dp(2,4)+dp(2,3)+dp(2,2)+dp(2,1)+dp(2,0)
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^2)
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int dices = sc.nextInt(), num = sc.nextInt();
        if (num <= dices) {
            System.out.println(1);
            return;
        } else if (num > 6 * dices) {
            System.out.println(0);
            return;
        }

        // 获取投掷 n 个骰子所能组成的sum值的分布数组（出现次数）
        long[] counts = getCounts(dices);
        // n个骰子向上面的数字之和大于等于x的情况数总和
        long res = 0L;
        for (int i = num; i <= 6 * dices; i++) {
            res += counts[i];
        }
        // 所有组合个数
        long all = (long)Math.pow(6, dices);
        // 最大公约数
        long divisor = gcd(res, all);

        System.out.println(res / divisor + "/" + all / divisor);
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long[] getCounts(int dices) {
        long[][] dp = new long[dices + 1][6 * dices + 1];
        for (int i = 1; i <= 6; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= dices; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int k = 1; k <= 6 && j > k; k++) {
                    dp[i][j] += dp[i - 1][j - k];
                }
            }
        }
        return dp[dices];
    }
}


/**
 * Approach: 0-1 Backpack (Space Optimized)
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n)
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int dices = sc.nextInt(), num = sc.nextInt();
        if (num <= dices) {
            System.out.println(1);
            return;
        } else if (num > 6 * dices) {
            System.out.println(0);
            return;
        }

        // 获取投掷 n 个骰子所能组成的sum值的分布数组（出现次数）
        long[] counts = getCounts(dices);
        // n个骰子向上面的数字之和大于等于x的情况数总和
        long res = 0L;
        for (int i = num; i <= 6 * dices; i++) {
            res += counts[i];
        }
        // 所有组合个数
        long all = (long)Math.pow(6, dices);
        // 最大公约数
        long divisor = gcd(res, all);

        System.out.println(res / divisor + "/" + all / divisor);
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long[] getCounts(int dices) {
        long[] dp = new long[6 * dices + 1];
        for (int i = 1; i <= 6; i++) {
            dp[i] = 1;
        }
        for (int i = 1; i < dices; i++) {
            for (int j = dp.length - 1; j > 0; j--) {
                dp[j] = 0;
                for (int k = 1; k <= 6 && j > k; k++) {
                    dp[j] += dp[j - k];
                }
            }
        }
        return dp;
    }
}

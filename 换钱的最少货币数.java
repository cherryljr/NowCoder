/*
换钱的最少货币数
【题目】
给定数组arr，arr中所有的值都为正数且不重复。每个值代表一种面值的货币，
每种面值的货币可以使用任意张，再给定一个整数aim代表要找的钱数，求组成aim的最少货币数。

【举例】
arr=[5,2,3]，aim=20。
4张5元可以组成20元，其他的找钱方案都要使用更多张的货币，所以返回4。

arr=[5,2,3]，aim=0。
不用任何货币就可以组成0元，返回0。

arr=[3,5]，aim=2。
根本无法组成2元，钱不能找开的情况下默认返回-1。

【补充题目】
给定数组arr，arr中所有的值都为正数。每个值仅代表一张钱的面值，再给定一个整数aim代表要找的钱数，求组成aim的最少货币数。
*/

/**
 * Approach: Backpack
 * 完全背包问题
 * 补充问题为：01背包问题
 *
 * 这道问题与 基本相同。
 * 因此这里就只给出想要的代码了，详细解释可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 */
public class CoinsMin {

    /**
     * 完全背包问题：最原始版本的动态规划
     * 已经进行了 算法时间 复杂度上的优化，体现在：
     * left = dp[i][j - arr[i]] + 1;
     */
    public static int minCoins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }

        int n = arr.length;
        int[][] dp = new int[n][aim + 1];
        // Initialize
        for (int j = 1; j <= aim; j++) {
            // 初始化成 Integer.MAX_VALUE 表示当前 aim 无法被换出来
            dp[0][j] = Integer.MAX_VALUE;
            if (j - arr[0] >= 0 && dp[0][j - arr[0]] != Integer.MAX_VALUE) {
                dp[0][j] = dp[0][j - arr[0]] + 1;
            }
        }
        // Function
        int left;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= aim; j++) {
                left = Integer.MAX_VALUE;
                if (j - arr[i] >= 0 && dp[i][j - arr[i]] != Integer.MAX_VALUE) {
                    // left 相当于 dp[i-1][j - x*arr[i]] 之和 + 1
                    left = dp[i][j - arr[i]] + 1;
                }
                dp[i][j] = Math.min(left, dp[i - 1][j]);
            }
        }
        // Answer
        return dp[n - 1][aim] != Integer.MAX_VALUE ? dp[n - 1][aim] : -1;
    }

    /**
     * 完全背包问题：进行了 空间复杂度 的优化
     */
    public static int minCoins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }

        int n = arr.length;
        int[] dp = new int[aim + 1];
        // Initialize
        for (int j = 1; j <= aim; j++) {
            dp[j] = Integer.MAX_VALUE;
            if (j - arr[0] >= 0 && dp[j - arr[0]] != Integer.MAX_VALUE) {
                dp[j] = dp[j - arr[0]] + 1;
            }
        }
        // Function
        int left;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= aim; j++) {
                left = Integer.MAX_VALUE;
                if (j - arr[i] >= 0 && dp[j - arr[i]] != Integer.MAX_VALUE) {
                    // left 相当于 dp[i-1][j - x*arr[i]] 之和 + 1
                    // 因此也等于 dp[i][j - arr[i]] + 1
                    left = dp[j - arr[i]] + 1;
                }
                dp[j] = Math.min(left, dp[j]);
            }
        }
        // Answer
        return dp[aim] != Integer.MAX_VALUE ? dp[aim] : -1;
    }

    /**
     * 补充问题，值代表一张钱的面值。（01背包问题）
     * 未进行空间优化的版本
     */
    public static int minCoins3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }

        int n = arr.length;
        // Initialize
        int[][] dp = new int[n][aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[0][j] = Integer.MAX_VALUE;
        }
        if (arr[0] <= aim) {
            dp[0][arr[0]] = 1;
        }
        // Function
        int leftup = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= aim; j++) {
                leftup = Integer.MAX_VALUE;
                if (j - arr[i] >= 0 && dp[i - 1][j - arr[i]] != Integer.MAX_VALUE) {
                    // left 相当于 dp[i-1][j - arr[i]] （这就是与 完全背包 问题的区别点）
                    leftup = dp[i - 1][j - arr[i]] + 1;
                }
                dp[i][j] = Math.min(leftup, dp[i - 1][j]);
            }
        }
        // Answer
        return dp[n - 1][aim] != Integer.MAX_VALUE ? dp[n - 1][aim] : -1;
    }

    /**
     * 补充问题，值代表一张钱的面值。（01背包问题）
     * 进行了空间优化的版本
     */
    public static int minCoins4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        int n = arr.length;
        int max = Integer.MAX_VALUE;
        // Initialize
        int[] dp = new int[aim + 1];
        for (int j = 1; j <= aim; j++) {
            dp[j] = max;
        }
        if (arr[0] <= aim) {
            dp[arr[0]] = 1;
        }
        // Function
        int leftup = 0;
        for (int i = 1; i < n; i++) {
            // 注意这 j 是按照 aim 到 0 的顺序遍历的
            for (int j = aim; j > 0; j--) {
                leftup = max;
                if (j - arr[i] >= 0 && dp[j - arr[i]] != max) {
                    // left 相当于 dp[i-1][j - arr[i]] （这就是与 完全背包 问题的区别点）
                    // 因此这解释了为什么这里 j 是按照 aim 到 0 的顺序遍历
                    leftup = dp[j - arr[i]] + 1;
                }
                dp[j] = Math.min(leftup, dp[j]);
            }
        }
        // Answer
        return dp[aim] != max ? dp[aim] : -1;
    }

    public static void main(String[] args) {
        int[] arr1 = { 100, 20, 5, 10, 2, 50, 1 };
        int aim1 = 17019;
        System.out.println(minCoins1(arr1, aim1));
        System.out.println(minCoins2(arr1, aim1));

        int[] arr2 = { 10, 100, 2, 5, 5, 5, 10, 1, 1, 1, 2, 100 };
        int aim2 = 223;
        System.out.println(minCoins3(arr2, aim2));
        System.out.println(minCoins4(arr2, aim2));

    }
}

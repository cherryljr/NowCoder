/*
题目描述
给定一个有n个正整数的数组A和一个整数sum,求选择数组A中部分数字和为sum的方案数。
当两种选取方案有一个数字的下标不一样,我们就认为是不同的组成方案。

输入描述:
输入为两行:
第一行为两个正整数n(1 ≤ n ≤ 1000)，sum(1 ≤ sum ≤ 1000)
第二行为n个正整数A[i](32位整数)，以空格隔开。

输出描述:
输出所求的方案数

示例1
输入
5 15 5 5 10 2 3
输出
4
 */

/**
 * Approach 1: Backpack (DP)
 * 典型的 01背包 问题。并且是属于 正好装满 的类型。（因为求的是方案数，所以无法被换出来的自然就是 0，无需用极值进行初始化区分）
 * 与 换零钱（完全背包问题） 正好互相对应。
 *
 * 本题要求每个数只能取一次，即相当于一种面值的货币只能取一张，然后组成 sum.
 * State:
 *  dp[i][j] 表示：用前 i 个数组成 j 的方案数。
 * Function:
 * 经过简单分析就可以得出：
 *  dp[i][j] = dp[i - 1][j] (j < arr[i]) （因为 j < arr[i],因此无法取得当前数）
 *  dp[i][j] += dp[i - 1][j - arr[i]] (j >= arr[i])
 * Initialize:
 *  初始化矩阵的第一行和第一列
 * Answer:
 *  最终需要的结果为：
 *  dp[n - 1][sum]
 *  注意：int可能会爆，要开long数组
 *
 * 关于初始化问题，当遇到给定一个 target，
 * 要求只能是 正好达到 target 的话，我们可以将 dp[0][0] 初始化为 需要的值，其他全部初始化为 Integer.MAX_VALUE/MIN_VALUE;
 *  如果我们需要取最小值，则令该方案的值为正无穷大
 *  如果我们需要取最大值，则令该方案的值为负无穷大
 * 而要求是 <= target 均可的话，在初始化的时候，全部用 0 即可。
 * 即对应的 正好完全装入 和 装入的最大值（不一定要装满）
 * -- 背包九讲
 *
 * 对于上述讨论到的 初始化 问题，我们可以根据需求选择适当的方式，比如本题虽然要求正好装入，但是因为要求的是方案数，所以使用 0 就可以了。
 * 对此我们可以对比 换钱的最少货币数（需要使用极值来初始化） 来加深印象：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%92%B1%E7%9A%84%E6%9C%80%E5%B0%91%E8%B4%A7%E5%B8%81%E6%95%B0.java
 *
 * 如果对上述过程感到吃力的话，可以参考 换零钱 （完全背包）有助于大家对背包问题有更深刻的认识：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%8D%A2%E9%9B%B6%E9%92%B1.java
 */
import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt(), sum = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Initialize
        // 组成 sum =0 时，只有一种方法
        // 当 sum >= arr[0] 时, dp[i][arr[0]] = 1,代表用 arr[0] 组成 sum = arr[0] 有一种方法
        long[][] dp = new long[n][sum + 1];
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1L;
        }
        if (arr[0] <= sum) {
            dp[0][arr[0]] = 1L;
        }

        // Function
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= arr[i]) {
                    // 可以取得当前值
                    dp[i][j] += dp[i - 1][j - arr[i]];
                }
            }
        }

        System.out.println(dp[n - 1][sum]);
    }
}

/**
 * Approach 2: Backpack (DP) Space Optimized
 * 由 Approach 1 可得，我们当前状态仅仅由上一行的 两个元素 决定：dp[i-1][j] 和 dp[i-1][j-arr[i]]。
 * 因此实际上我们只需要保留 一行 元素就够用了。
 * 注意：计算的时候，我们要先从 最后一列 开始，向前递推计算。
 * 这样才能保证我们在计算 dp[j] 的时候，使用的 dp[j-arr[i]] 是 上一行的数据，相当于 dp[i-1][j-arr[i]]。
 * 如果 从头向后 计算的话，我们使用的 dp[j-arr[i]] 就变成了当前行的数据了，相当于 dp[i][j-arr[i]]. 
 * 这就变成了 完全背包问题 了。而这二者是有本质区别的。
 */
import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt(), sum = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Initialize
        // 组成 sum =0 时，只有一种方法
        // 当 sum >= arr[0] 时, dp[i][arr[0]] = 1,代表用 arr[0] 组成 sum = arr[0] 有一种方法
        long[] dp = new long[sum + 1];
        dp[0] = 1;
        if (arr[0] <= sum) {
            dp[arr[0]] = 1;
        }

        // Function
        for (int i = 1; i < n; i++) {
            // 注意与 完全背包问题 区分，j是从 sum 向 0 进行遍历的
            // Guarantee the size is big enough to put arr[i] into the backpack.
            for (int j = sum; j >= arr[i]; j--) {
                // 此处的 dp[j] 相当于 dp[i-1][j]
                // dp[j-arr[i]] 相当于 dp[i-1][j-arr[i]]
                dp[j] += dp[j - arr[i]];
            }
        }
        
        System.out.println(dp[sum]);
    }
}

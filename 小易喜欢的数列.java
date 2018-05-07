/*
小易非常喜欢拥有以下性质的数列:
1、数列的长度为n
2、数列中的每个数都在1到k之间(包括1和k)
3、对于位置相邻的两个数A和B(A在B前),都满足(A <= B)或(A mod B != 0)(满足其一即可)
例如,当n = 4, k = 7
那么{1,7,7,2},它的长度是4,所有数字也在1到7范围内,并且满足第三条性质,所以小易是喜欢这个数列的
但是小易不喜欢{4,4,4,2}这个数列。小易给出n和k,希望你能帮他求出有多少个是他会喜欢的数列。

输入描述:
输入包括两个整数n和k(1 ≤ n ≤ 10, 1 ≤ k ≤ 10^5)

输出描述:
输出一个整数,即满足要求的数列个数,因为答案可能很大,输出对1,000,000,007取模的结果。

示例1
输入
2 2
输出
3

OJ地址：https://www.nowcoder.com/questionTerminal/49375dd6a42d4230b0dc4ea5a2597a9b
 */

/**
 * Approach: Matrix DP
 * dp[i][j] 表示：长度为 i 时，以数值 j 结尾的符合要求的数列个数。
 * 那么结果就应该是 sum(dp[n][1...k]) 即矩阵的最后一行数值之和。
 * Base Case 为 dp[1][1...k] = 1，此状态不依赖与任何其他状态。
 * 因为符合条件的数列要求为：A <= B | A % B != 0
 * 因此 dp[i][j] 所依赖的状态为上一行中加上 j 后仍然符合要求的状态。
 * 即 dp[i][j] = sum(dp[i-1][m]) (m代表符合状态的上一行数列中的最后一个数值）
 * 由此分析下来总体时间复杂度为：O(n*k^2)
 * 根据题目的数据：n = 10, k = 10^5  =>  n*k^2 = 10^11 > 10^8
 * 一秒是绝对跑不完的，肯定要爆...因此我们必须对此进行一个时间复杂度上面的优化。
 *
 * 这里使用到了类似 筛法求素数(当然Sieve Method还要牛逼哈) 的做法对 sum(dp[i-1][m]) 的过程进行了一个优化。
 * 因为当我们需要加入 j 的时候，从 1~2*j-1 的部分必定是符合条件的；
 * 因此我们可以直接将 dp[i-1][0...j] 的值直接加起来作为 dp[i][j] 的一个部分。
 * 那么对于其他的值呢？我们可以从其中剔除掉所有 j 的倍数就可以了（A % B == 0 不符合条件）
 * 因此我们可以事先就计算出 dp[i-1][1...k] 的和（计算 i 行的时候，该值可被重复利用），
 * 然后剔除掉不符合条件方案个数的 sum(dp[i-1][1...k]) 就是 dp[i][j] 的值了。
 * 使用该做法可以将原本筛选需要花费 O(n) 时间复杂度优化到 O(logn) 级别。
 * 因此总体时间复杂度为：O(n*k*logk) 估算一下完全是符合要求的。
 */

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] dp = new int[n + 1][k + 1];

        // Initialize
        // 长度只有 1 的时候，不管结尾数值是什么，只有 1 种数列
        Arrays.fill(dp[1], 1);

        // Function
        for (int i = 2; i <= n; i++) {
            // 事先计算出上一行 i-1 个元素组成数列的合法方案数，以作备用
            int sum = 0;
            for (int j = 1; j <= k; j++) {
                sum = (sum + dp[i - 1][j]) % MOD;
            }

            // O(klogk)
            for (int j = 1; j <= k; j++) {
                int invalid = 0;
                int mult = 2;
                while (mult * j <= k) {
                    invalid = (invalid + dp[i - 1][mult * j]) % MOD;
                    mult++;
                }
                // We'd better add MOD here
                dp[i][j] = (sum - invalid + MOD) % MOD;
            }
        }

        // Answer
        int rst = 0;
        for (int i = 1; i <= k; i++) {
            rst = (rst + dp[n][i]) % MOD;
        }
        System.out.println(rst);
    }
}
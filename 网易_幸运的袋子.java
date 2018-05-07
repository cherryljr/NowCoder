/*
题目描述
一个袋子里面有n个球，每个球上面都有一个号码(拥有相同号码的球是无区别的)。
如果一个袋子是幸运的当且仅当所有球的号码的和大于所有球的号码的积。
例如：如果袋子里面的球的号码是{1, 1, 2, 3}，这个袋子就是幸运的，因为1 + 1 + 2 + 3 > 1 * 1 * 2 * 3
你可以适当从袋子里移除一些球(可以移除0个,但是别移除完)，要使移除后的袋子是幸运的。
现在让你编程计算一下你可以获得的多少种不同的幸运的袋子。

输入描述:
第一行输入一个正整数n(n ≤ 1000)
第二行为n个数正整数xi(xi ≤ 1000)
输出描述:
输出可以产生的幸运的袋子数

示例1
输入
3
1 1 1
输出
2
 */


/**
 * Approach: DFS
 * 直接枚举所有的 Subsets 方案，然后判断其是否能够组成幸运袋即可。
 * 值得注意的是：在枚举的过程中，我们需要进行剪枝操作，
 * 不然会进行大量无用的计算，从而导致超时。
 * 因为 DFS 的时间复杂还是相当高的，所以普遍的做法就是：
 * 在 DFS 之前首先进行一个 排序 操作，然后可以利用 有序 的特性对 DFS 过程进行剪枝优化。
 * 因为 nums 已经排序好了，所以当 sum < product 的时候，后面可以直接略去不再进行计算。
 * 而对于 去除重复的subsets同样是利用了实现 排序 的做法。
 * 去重的详细分析可以参考：
 *  Subsets II: https://github.com/cherryljr/LintCode/blob/master/Subsets%20II.java
 *
 * 排序不仅能帮助我们去重，还能帮助我们进行剪枝。（因此通常我们都会先进行一次排序操作）
 * 类似的应用还有
 *  Combination Sum: https://github.com/cherryljr/LeetCode/blob/master/Combination%20Sum.java
 */
import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    private static int result = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Arrays.sort(nums);
        dfs(nums, 0, 0, 1);
        System.out.println(result);
    }

    private static void dfs(int[] nums, int index, int sum, int product) {
        if (sum > product) {
            result++;
        }

        for (int i = index; i < nums.length; i++) {
            // 剪枝
            if (sum + nums[i] < product * nums[i]) {
                break;
            }
            if (i > 0 && i != index && nums[i] == nums[i - 1]) {
                continue;
            }
            dfs(nums, i + 1, sum + nums[i], product * nums[i]);
        }
    }
}
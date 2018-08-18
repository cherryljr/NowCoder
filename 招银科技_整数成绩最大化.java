/*
时间限制：1秒
空间限制：32768K

给出一个整数n，将n分解为至少两个整数之和，使得这些整数的乘积最大化，输出能够获得的最大的乘积。
例如：
2=1+1，输出1；
10=3+3+4，输出36。

输入描述:
输入为1个整数

输出描述:
输出为1个整数

输入例子1:
10

输出例子1:
36
 */

/**
 * Approach: Recursion + Memory Search
 * 看到这个第一反应一个类似完全背包问题的题目。
 * 因为使用 Memory Search 写起来非常简单并且易于理解，所以笔试中并没有采用 DP 的写法。
 * 记忆化搜索的做法很简单：
 *  使用 mem 来记录每个 n 所对应的最优解以避免重复计算。（这里可以使用数组代替）
 *  然后每次枚举所有的分割方案。（从 1~2/n，对于乘积的分割到 n/2 即可，再后面就重复了）
 *  在所有的方案中取最大值即可。
 * 注意：处理初始的几个数值即可（1，2，3）
 *
 * 事后看了下，DP写法基本没差，这里就不写了 O(∩_∩)O
 */
import java.util.*;

public class Main {
    private static Map<Integer, Long> mem = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n == 2 || n == 3) {
                System.out.println(n - 1);
                continue;
            } else {
                System.out.println(dfs(n));
            }
        }
        sc.close();
    }

    private static long dfs(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        if (mem.containsKey(n)) {
            return mem.get(n);
        }

        long max = n;
        // Do DFS (1~n/2)
        for (int i = 1; i <= n / 2; i++) {
            max = Math.max(max, i * dfs(n - i));
        }
        // Make a record
        mem.put(n, max);
        return max;
    }
}

/**
 * Approach 2: Mathematics
 * 该解法是网上看到的，跟大家分享一下。纯粹的找规律，数学题。
 * 
 * 根据题目设定的条件整数n的取值范围为： 2 <= n <= 58
 * 分析一下：
 *  当 n = 2 时： n=1+1; result = 1*1=1
 *  当 n = 3 时：可以拆分为: 1+2 或者 1+1+1，但是显然拆分为 1+2，所获得的乘积最大
 *  当 n = 4 时：可以拆分为： 1+3 或者 2+2，但是显然拆分为 2+2，所获得的乘积最大
 *  当 n = 5 时：可以拆分为：2+3，所获得乘积最大
 *  当 n = 6 时：可以拆分为：3+3，所获得乘积最大
 *  当 n = 7 时：可以拆分为：3+4，所获得乘积最大
 *  当 n = 8 时：可以拆分为：3+3+2，所获得乘积最大
 *  当 n = 9 时：可以拆分为：3+3+3，所获得乘积最大
 *  当 n = 10 时：可以拆分为：3+3+4，所获得乘积最大
 * 通过观察上述内容，我们可以发现从n=5开始，拆分的结果都有数字3。
 * 之后的数字，例如11，可以先拆出来1个3，然后再看余下的8如何拆分。
 */
import java.util.*;

public class Main {
    private static Map<Integer, Long> mem = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n == 2 || n == 3) {
                System.out.println(n - 1);
                continue;
            }
            if (n == 4) {
                System.out.println(4);
                continue;
            }
            int result = 1;
            while (n > 4) {
                result *= 3;
                n -= 3;
            }
            System.out.println(result * n);
        }
        sc.close();
    }
}

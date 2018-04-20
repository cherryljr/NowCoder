/*
有一个数组changes，changes中所有的值都为正数且不重复。
每个值代表一种面值的货币，每种面值的货币可以使用任意张，
对于一个给定值x，请设计一个高效算法，计算组成这个值的方案数。

给定一个int数组changes，代表所以零钱，同时给定它的大小n，另外给定一个正整数x，
请返回组成x的方案数，保证n小于等于100且x小于等于10000。

测试样例：
[5,10,25,1],4,15
返回：6
测试样例：
[5,10,25,1],4,0
返回：1

OJ地址：https://www.nowcoder.com/questionTerminal/185dc37412de446bbfff6bd21e4356ec
 */

/**
 * Approach: 完全背包问题
 * 以下我们将以该问题作为切入点，逐步分析 动态规划 的由来。
 * 请大家按照 递归->记忆化搜索->动态规划 的顺序阅读。
 * 结束之后，相信大家对 动态规划 会有一个更加深刻的认识。
 *
 * 对于 完全背包问题 大家很容易就会想到与之对立的 01背包问题。
 * 其区分无非就在与 只能取1次（01背包） 和 可以无限次使用（完全背包）。
 * 相应的其代码也几乎相同，只在遍历顺序方面有个小小的差别。
 * 但是这背后所隐藏着知识点是 非常非常大 的。
 * 因此希望大家在弄清楚 动态规划 以及 递推过程，建立好 空间感 后，再回来考虑这个问题。
 * 到时候我相信问题会迎刃而解。
 *
 * 本题所对应的 01背包问题：数组和为sum的方法数：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E7%BB%84%E5%92%8C%E4%B8%BAsum%E7%9A%84%E6%96%B9%E6%B3%95%E6%95%B0.java
 */
public class Exchange {

    /**
     * Approach 1: Recursion
     * 最暴力的版本，递归求解所有方法
     * 做法与 Combination Sum 相同
     * https://github.com/cherryljr/LintCode/blob/master/Combination%20Sum.java
     *
     * 取 0 张当前面值纸币的话，用剩下面值的纸币凑出 target 的话有几种方法；
     * 取 1 张当前面值纸币的话，用剩下面值的纸币凑出 target-arr[i] 的话有几种方法；
     * 取 2 张当前面值纸币的话，用剩下面值的纸币凑出 target-arr[i]*2 的话有几种方法；
     * ...
     * 直到取的金额会超过 target 为止，停止.
     * 然后递归求解子状态的信息相加即可。
     *
     * 以上就是 尝试求解 的这么一个过程
     */
    public static int countWaysRC1(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }
        return countWaysRC1(arr, 0, target);
    }

    private static int countWaysRC1(int[] arr, int startIndex, int target) {
        if (target == 0) {
            return 1;
        } else if (target < 0) {
            return 0;
        }

        int rst = 0;
        for (int i = startIndex; i < arr.length; i++) {
            if (target < arr[i]) {
                break;
            }
            // 因为可以重复使用当前的币值，所以继续递归调用 i 位置
            // 不理解的可以参考 Combination Sum 中的详细解释
            rst += countWaysRC1(arr, i, target - arr[i]);
        }

        return rst;
    }

    /**
     * Approach 2: Recursion
     * 仍然是递归写法，不过更换了递归的终止条件。
     * 相比于 版本1 有所改善
     */
    public static int countWaysRC2(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }
        
        return countWaysRC2(arr, 0, target);
    }

    private static int countWaysRC2(int[] arr, int index, int target) {
        int rst = 0;
        // 当所有币值都已经遍历结束了，看 target(剩下需要凑的金额) 是否为 0
        if (index == arr.length) {
            rst = target == 0 ? 1 : 0;
        } else {
            // 递归求解取 i 张当前币值的钱，可以有几种结果，然后加起来
            for (int i = 0; arr[index] * i <= target; i++) {
                rst += countWaysRC1(arr, index + 1, target - arr[index] * i);
            }
        }
        return rst;
    }

    /**
     * Approach 3: Memory Search
     * 我们发现，在递归规程中，进行了大量的重复计算。
     * 比如给定面值为 {10, 5, 1, 25 }； target = 70
     * 当我们取了 2张10，2张5 时，我们需要求用剩余的 1 和 25 组成 40 总共有多少中情况；
     * 而当我们取了 1张10，4张5 时，我们仍然需要求用剩余的 1 和 25 组成 40 总共有多少中情况；
     * 同样的还有 0张10，6张5...
     * 因此我们可以使用一张表，将我们每次求解的结果记录下来。
     * 这样当我们需要某一个结果是，我们直接去表中查找，如果能查找到，直接使用即可；
     * 否则再进行计算。这就避免了大量的重复计算，因此可以降低非常多的时间。
     *
     * 这个方法就是我们常说的 记忆化搜索，它实际上已经非常接近动态规划了，或者可以说是动态规划了。
     */
    public static int countWaysMS(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }

        int[][] map = new int[arr.length + 1][target + 1];
        return countWaysMS(arr, 0, target, map);
    }

    // 我们发现，记忆化搜索相比于上面的 递归版本(2) 几乎没有改动
    // 只是增添了 储存 和 查找 结果的过程
    private static int countWaysMS(int[] arr, int index, int target, int[][] map) {
        int rst = 0;
        if (index == arr.length) {
            rst = target == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; arr[index] * i <= target; i++) {
                // 从 map 中获取需要的信息
                mapValue = map[index + 1][target - arr[index] * i];
                if (mapValue != 0) {
                    // 已经计算过了
                    rst += mapValue == -1 ? 0 : mapValue;
                } else {
                    // 没有该信息，需要计算
                    rst += countWaysMS(arr, index + 1, target - arr[index] * i, map);
                }
            }
        }
        // 记录结果
        map[index][target] = rst == 0 ? -1 : rst;
        return rst;
    }

    /**
     * Approach 4: DP （原始版本未添加任何优化）
     * 有了以上的基础，我们可以开始了解一下什么是 动态规划 了。
     * 其本质就是利用之前的状态来推出当前状态的这么一个过程。
     * 使用动态规划，必须保证过程是 无后效性 的。
     * 无后效性：指的是 当前状态的结果 与 如何到达 该状态是无关的。
     * 以上面的例子为例：
     *  当我知道需要用 1 和 25 来凑出 40 时，这个结果就已经确定了，
     *  与我之前是用 几张10 和 几张5 凑出 30 没有任何关系。
     *  这就是我们所谓的 无后效性。
     * 那么 有后效性 又是什么呢？典型的就是 N皇后 问题。（需要用 BFS 来解决）
     *
     * 那么回到动态规划中，我们如何写出动态规划呢？
     * 其实只需要根据 递归版本 的代码进行分析就可以了。
     * 因此，写出递归版本 是非常重要的。动态规划只是对其过程的一个优化罢了。
     *
     * 那么如何优化呢？
     *  首先，我们从 递归版本 中发现，只要 index 和 target 确定之后，当前状态的结果就能够确定了。
     *  因此我们需要建立一个二维数组 dp[][] 来存储计算得到的结果。
     *  dp[i][j] 表示：使用 0~i 的面值纸币，凑出 j 的金额 的方法数。
     *  然后，我们需要对其初始状态（有些状态在一开始就是确定的）进行一个初始化，然后我们才能利用这些状态推出之后的状态。
     *  比如本题中，当 target==0 时，只有 1 中方法，即什么都不取。因此 dp[i][0] = 1;
     *  只使用 第一张 面值时，只有 arr[0] * i 的金额能被凑出来，且只有一种方法，即 dp[0][arr[0] * i]=1。
     *  有了以上信息（相当于已经初始化好了矩阵的 第一行/列），我们就可以推出下一个状态了。
     *  对于任意位置 dp[i][j]，它所依赖的信息为：取 k张 当前面值的纸币的话，利用剩下面值的纸币凑出剩余的金额有多少中方法。
     *      dp[i - 1][j - arr[i] * k] (j - arr[i] * k >= 0)
     *  而这就是我们常说的 状态转移方程。它其实就是从 递归版本 得出的。
     *  当我们利用以上方法计算出 矩阵 各个位置的值后，返回 dp[n][target] 就是我们需要的结果。
     *
     * 总结：动态规划说白了只是一个对递归解法的 优化 的过程。
     * 因此能写出 递归 解法才是最重要的，然后如果判断出这是一个 无后效性 问题的话，我们就能将其改成 动态规划。
     * 所以当你无法解出一道 DP 问题时，请先写出 递归版本 然后再按照上述步骤进行优化即可。
     * 优化过程是有明显套路方法的，而写出 递归方法 才是难点。
     * 因此如果你觉得 动态规划 掌握不好的话，请务必学会用递归的方法尝试性地去解决问题。这才是最重要的。
     */
    public static int countWaysDP1(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }

        // State
        int[][] dp = new int[arr.length][target + 1];
        // 状态初始化
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; arr[0] * i <= target; i++) {
            dp[0][arr[0] * i] = 1;
        }
        int num;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                num = 0;
                // 状态转移方程
                for (int k = 0; j - arr[i] * k >= 0; k++) {
                    num += dp[i - 1][j - arr[i] * k];
                }
                dp[i][j] = num;
            }
        }

        return dp[arr.length - 1][target];
    }

    /**
     * Approach 5: DP （对时间复杂度稍作优化）
     * 熟悉了以上过程，我们就可以对 DP 进行进一步优化了。
     * 我们知道，Approach 4 实际上是一个 二维矩阵 的求值过程。（如果没有建立起上述空间感的请返回 Approach 4 继续思考）
     * 并且其 状态转移方程 为：
     *  	dp[i - 1][j - arr[i] * k] (j - arr[i] * k >= 0)
     * 即当前状态依赖于上一层的 j-arr[i]*k(纵坐标) 个状态。
     * 那么如果对以上计算过程足够清晰的话，我们就会发现 dp[i-1][j-arr[i]*k] (k>=1) 实际上已经计算过了，
     * 它就是：dp[i][j - arr[i]].那么我们为什么不把结果直接拿过来用呢？
     * 因此只要将 dp[i][j - arr[i]] 加上 dp[i-1][j] 就是我们需要的 dp[i][j] 了。
     * 注意判断 j - arr[i] >= 0 即可，防止越界。
     * 而在 物理 意义方面，其实就是：
     *  	当前面值一张都不取，凑出 j 金额的方法数：dp[i-1][j]
     *  	当前面值取[1...k]张，凑出 j-arr[i]*k 金额的方法数：dp[i-1][j-arr[i]*k] == dp[i][j-arr[i]]
     * 虽然这边给出了 物理含义 是什么，但是说实话，我们并不需要去关心它...
     * 因为当我们 写完递归版本 后，动态优化的过程实际上就是针对 递归 过程了。
     * 与题目的实际意义并没有关系，说白了就是不看题目照样能写。
     * 所以不去理会这些东西，把优化过程弄清楚，知道当前状态是怎么推出来的，建立空间感，是更重要的。
     */
    public static int countWaysDP2(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }

        // State
        int[][] dp = new int[arr.length][target + 1];
        // Initialize
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; arr[0] * i <= target; i++) {
            dp[0][arr[0] * i] = 1;
        }
        // Function
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }

        return dp[arr.length - 1][target];
    }

    /**
     * Approach 6: DP （利用 滚动数组 对空间复杂度进行优化）
     * 通过以上分析，我们发现 当前状态 只与 上一行 和 当前行 有关。
     * 因此我们可以利用 滚动数组 只保留两行数据即可。
     * 利用 滚动数组 进行优化的优点在于，只需要对 递推过程熟悉 就能够改写出来。
     * 并且改写方法十分简单，几乎对原本代码没有改动。只要原来代码是对的，你就很难该错。
     * 推荐在 面试 等高压情况下使用。
     *
     * 关于滚动数组的详细介绍可以参考：（正好也是一道简单的 DP 问题）
     * https://github.com/cherryljr/LintCode/blob/master/Climbing%20Stairs.java
     */
    public static int countWaysDP3(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }

        // State
        int[][] dp = new int[2][target + 1];
        // Initialize
        for (int i = 0; arr[0] * i <= target; i++) {
            dp[0][arr[0] * i] = 1;
        }
        // Function
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                dp[i & 1][j] = dp[(i - 1) & 1][j];
                dp[i & 1][j] += j - arr[i] >= 0 ? dp[i & 1][j - arr[i]] : 0;
            }
        }

        return dp[(arr.length - 1) & 1][target];
    }

    /**
     * Approach 7: DP （在空间复杂度上进行最终版优化）
     * 利用 滚动数组 的优化已经节省了非常多的额外空间。
     * 但是其仍然是一个 二维 数组。（虽然是 常数级 的，跟 一维 没区别了）
     * 那么能不能跟进一步优化，使得其完成全称为一个 一维 数组呢？
     * 答案是可以的。
     *
     * 同样，以下过程建立在你对 整个递推过程足够熟悉，并且具备良好空间感的情况下。
     * 基于 Approach 6 的分析。
     * 当前状态 dp[i][j] 所依赖的是：上一行的 dp[i - 1][j] 以及左侧的 dp[i][j - arr[i]]
     * 那么我们可以将 dp[i] 直接设为 dp[i-1][j]
     * 当计算的时候，dp[i] 就等于 dp[i] + dp[i][j - arr[i]] 从而更新 dp[i] 的状态成为 当前行。
     * 而在下一次计算（计算下一行）的时候，dp[i] 又成为了 dp[i-1].
     * 以上过程较为抽象，如果不能理解，可以将递推过程的 矩阵 画出来以便理解。
     */
    public static int countWaysDP4(int[] arr, int target) {
        if (arr == null || arr.length == 0 || target < 0) {
            return 0;
        }

        // State
        int[] dp = new int[target + 1];
        // Initialize
        for (int i = 0; arr[0] * i <= target; i++) {
            dp[arr[0] * i] = 1;
        }
        // Function
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= target; j++) {
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] coins = { 10, 5, 1, 25 };
        int target = 2000;

        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        System.out.println(countWaysRC1(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        System.out.println(countWaysRC2(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        target = 20000;

        start = System.currentTimeMillis();
        System.out.println(countWaysMS(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        System.out.println(countWaysDP1(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        System.out.println(countWaysDP2(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        System.out.println(countWaysDP3(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        System.out.println(countWaysDP4(coins, target));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");
    }

}

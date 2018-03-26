/*
题目：
定义数组异或和的概念：
数组中所有的数异或起来，得到的结果叫做数组的异或和，
比如数组{3，2，1}的异或和为：3^2^1=0

给定一个数组arr,你可以任意arr分割成多个不相容的子数组，
使得分割出来的子数组中，异或和为0的子数组最多。

请返回：分出来的子数组中，异或和为0的子数组最多是多少？

输入：
{1,2,3,0,3,2,1,0}
输出：
4
 */

/**
 * Approach: PreXOR + DP
 * 想要解决这道问题，首先我们需要了解 异或 的性质：
 *  1. 异或满足交换律. a^b = b^a
 *  2. 相同的数异或为0. a^a = 0
 *  3. 0与所有数异或都是那个数本身. 0^a = a
 * 那么当什么时候一个 子数组 的异或和才能是 0 呢?
 *  1. 该子数组中全部都是 0.（为了形成最多的子数组，每个 0 都可以单独形成一个子数组）
 *  2. [a...b] 的异或和 等于 [a...c] 的异或和。那么根据异或的性质，我们可以知道：（b...c] 的异或和为 0
 * 根据以上分析，我们可以利用一个 HashMap 来存储各个位置从 0 到当前位置的异或和preEOR 和 对应的当前位置 index.
 * (这个解法是跟 累加和为K的最长子数组 相同的)
 * 同样，我们需要注意对 map 进行初始化，先将 (0, -1) 加入 map,代表当一个元素都没有的时候，异或和为0，对应的index为-1.
 *
 * 但是这个时候，我们还没有解决问题。
 * 因为我们发现：如果我们遍历到 i,假设我们可以在 map 中找到当前的异或和preSum,
 * 那么 [0...i] 范围内异或和为0的子数组个数就是 most[map.get(preSum)] + 1.
 * 由此可见，当前状态是由其 子状态 所决定的。
 * 于是我们发现，原来这个问题还需要我们使用 DP 才行。
 *
 * 于是我们建立一个数组 dp[len]
 * dp[i] 表示 从 0 到 i 异或和为0的最多子数组个数.
 * 那么我们对 ith 进行分析：
 *  1. 在最优划分的情况下，ith所在的子数组异或和 不为0，那么说明 ith 在划分过程中没有起到任何作用。
 *  则 从0到ith 的异或和为0的子数组个数 等于 从0到ith-1 的异或和为0的子数组个数
 *  即 dp[i] = dp[i - 1]. （说白了，就是把 ith 扔了也不会发生任何变化）
 *  2. 在最优划分的情况下，ith所在的子数组异或和 为0，
 *  则 从0到ith 的异或和为0的子数组个数 等于 从0到离ith最近一次preEOR出现位置 的异或和为0的子数组个数+1
 *  即 dp[i] = dp[map.get(preEOR)] + 1
 *  这也就意味着我们的 map 中存储的是 preEOR 最一次出现的 index.
 *
 * 时间复杂度为：O(n)
 * 空间复杂度为：O(n)
 */

import java.util.*;

public class Main {

    public static int maxEOR(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int len = arr.length;
        int preEOR = 0;
        int[] dp = new int[len];

        for (int i = 0; i < len; i++) {
            preEOR ^= arr[i];
            if (map.containsKey(preEOR)) {
                // 如果当前的异或和已经在 map 里面有记录
                // 则 dp[i] = dp[preEOR] + 1 (注意判断 pre 是否为 -1)
                int pre = map.get(preEOR);
                dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
            }
            if (i > 0) {
                dp[i] = Math.max(dp[i], dp[i - 1]);
            }
            // 存储 preEOR 最新出现的 index
            // (这里与 累加和为K的最长子数组 正好是相反的哈)
            map.put(preEOR, i);
        }

        return dp[len - 1];
    }

    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] eors = new int[arr.length];
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
            eors[i] = eor;
        }
        int[] mosts = new int[arr.length];
        mosts[0] = arr[0] == 0 ? 1 : 0;
        for (int i = 1; i < arr.length; i++) {
            mosts[i] = eors[i] == 0 ? 1 : 0;
            for (int j = 0; j < i; j++) {
                if ((eors[i] ^ eors[j]) == 0) {
                    mosts[i] = Math.max(mosts[i], mosts[j] + 1);
                }
            }
            mosts[i] = Math.max(mosts[i], mosts[i - 1]);
        }
        return mosts[mosts.length - 1];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 300;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = maxEOR(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
/*
时间限制：2秒
空间限制：65536K

为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。
牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。
在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。
牛牛的小伙伴太多了，于是他只好把这个任务交给了你。

输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
保证不存在两项工作的报酬相同。

输出描述:
对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。

输入例子1:
3 3
1 100
10 1000
1000000000 1001
9 10 1000000000

输出例子1:
100
1000
1001
 */

/**
 * Approach 1: Two PriorityQueue (maxHeap + minHeap)
 * 这道题目与 LeetCode 上的 PIO 十分类似：
 * https://github.com/cherryljr/LeetCode/blob/master/PIO.java
 * 但是有点小小的不同：
 * PIO中，启动资金是升序的，即随着项目越做越多，启动资金也就也来越多。这样就会使得 minHeap 逐步减小
 * 本题中，能力值（对应PIO中的启动资金）不一定是升序来排列的。但是我们可以将其转换成 PIO 问题然后解决。
 * 对此我们需要利用到的就是 排序。
 * 但是值得注意的是：我们需要实现记录下各个人员的 index,
 * 然后在排序，这样才能找到每个人所对应的结果。不然输出结果就乱掉了。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    // 记录输入时各个人的对应的 编号index 和 能力值value
    static class Type {
        int index;  // 编号
        int value;  // 能力值

        public Type(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int m = sc.nextInt();
        // 当前能够接的任务，按照 报酬 从高到低 排序
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        // 总的任务池，当前还不能接的任务，按照 能力值 从低到高 排序
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        Type[] abilities = new Type[m];
        for (int i = 0; i < n; i++) {
            minHeap.offer(new int[]{sc.nextInt(), sc.nextInt()});
        }
        for (int i = 0; i < m; i++) {
            abilities[i] = new Type(i, sc.nextInt());
        }
        // 对各个小伙伴的能力值按照 从低到高 排序（为了处理成和 PIO 相同的情况）
        Arrays.sort(abilities, (a, b) -> (a.value - b.value));

        int[] rst = new int[m];
        for (int i = 0; i < m; i++) {
            // 随着能力的提高，查看 minHeap 中是否还有新的任务可以接受
            while (!minHeap.isEmpty() && abilities[i].value >= minHeap.peek()[0]) {
                maxHeap.offer(minHeap.poll());
            }
            // maxHeap 中没有任务，则无法得到报酬
            if (maxHeap.isEmpty()) {
                rst[abilities[i].index] = 0;
                continue;
            }
            // 在 maxHeap.peek 就是当前所能接的 报酬最高 的任务
            // 无需 poll 出去，因为一个任务可以被重复执行
            rst[abilities[i].index] = maxHeap.peek()[1];
        }

        for (int i : rst) {
            System.out.println(i);
        }
    }
}

/**
 * Approach 2: Sorting + BinarySearch
 * 因为在本题中，我们做完一个项目之后，项目是不会消失的，即一个项目可以重复被做。
 * 那么我们不妨转换思路，对输入的项目按照 能力值 进行排序。
 * 然后我们可以利用每个人的能力值在这里面进行 二分查找 其所能完成的任务。
 * 然后在区间内选取最高的收益即可。
 * 对于最高收益，我们只需要建立一个 preMax[] 来存储区间的 profit 最大值以供后续调用即可。
 * 非常典型的 空间 换 时间 的做法。
 *
 * 本题所应用的 BinarySearch 是其寻找 上界 的方法，即数组中最后一个 不大于 target 的元素。
 * 对于二分法不了解的可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    static class Pair {
        public int ability;
        public int profit;

        Pair(int ability, int profit) {
            this.ability = ability;
            this.profit = profit;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            Pair[] arr = new Pair[n];
            for (int i = 0; i < n; i++) {
                arr[i] = new Pair(sc.nextInt(), sc.nextInt());
            }
            Arrays.sort(arr, (a, b) -> a.ability - b.ability);

            // 计算 报酬 的区间最大值
            int[] preMax = new int[n];
            preMax[0] = arr[0].profit;
            for (int i = 1; i < arr.length; i++) {
                preMax[i] = Math.max(preMax[i - 1], arr[i].profit);
            }
            for (int i = 0; i < m; i++) {
                int index = upperBound(arr, 0, arr.length - 1, sc.nextInt());
                // 如果找不到（即没有工作可以接）则返回 index = -1
                System.out.println(index == -1 ? 0 : preMax[index]);
            }
        }
    }

    public static int upperBound(Pair[] arr, int begin, int end, int tag) {
        --begin;
        while (begin < end) {
            int mid = begin + ((end - begin + 1) >> 1);
            if (tag >= arr[mid].ability) {
                begin = mid;
            }
            else {
                end = mid - 1;
            }
        }
        return end;
    }
}


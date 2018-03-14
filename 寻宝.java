/*
题目描述
牛牛解出了卷轴隐藏的秘密，来到了一片沼泽地。这里有很多空地，而面试直通卡可能埋在任意一块空地中，好在亮亮发现了一堆木材，
他可以将木材铺在两个空地之间的沼泽地上。
因为亮亮不知道面试直通卡具体在哪一块空地中，所以必须要保证任意一块空地对于亮亮来说是可以抵达的。 
“怎么还有鳄鱼！没办法，看来有些空地不能直接到达了。” 亮亮虽然没有洁癖，但是沼泽地实在太臭了，所以亮亮不会循环利用木材。
而且木材不能拼接在一起使用，所以亮亮必须要知道在耗费木材最少的情况下，最长的那根木材至少需要多长。

输入描述:
第一行包含两个整数N(1≤N≤10000),M(1≤M≤1000000)。N表示公有N块空地。
接下来M行，每行包含三个整数P(1≤P≤N),Q(1≤Q≤N),K代表P,Q两个间没有鳄鱼，需要耗费K的木材。

输出描述:
一个整数，即耗费木材最少的情况下，最长的那根木材长度。

示例1
输入
4 3
1 2 1
2 3 1
3 4 2
输出
2
 */

/**
 * Approach: Union Find
 * 主要利用了 Union Find 并查集 这个数据结构来解
 * 不了解这个数据结构的可以参考 LintCode 上面一道关于 并查集 的入门问题：Find the Weak Connected Component in the Directed Graph
 * 解析：https://github.com/cherryljr/LintCode/blob/master/Find%20the%20Weak%20Connected%20Component%20in%20the%20Directed%20Graph.java
 *
 * 接下来对本道题目进行分析：
 * 想到使用 Union Find 基本上这道题就做出来了起码 70% 吧。
 * 那么为什么我会想到使用这个数据结构呢？
 * 突破在于：题目中涉及到了多次 连接 一个个孤立点，并形成联通区 的这么一个情况。（牛牛用木板架桥）
 * 这类题目由于会设计到 联通/合并 问题，所以通常使用 并查集 来解决。
 * 并且 Union Find 有着非常好的时间复杂度。
 * 题目说因为不知道宝藏放在哪里...所以这里也透露出一个信息：
 *  需要我们将所有的 岛屿 都通过木板连接起来，并且使得连接代价最小。
 *  然后要求 所有连接板中最长的长度。
 * 因此，很明显我们只需要在 union 过程中加入判断条件即可。或者直接改写 union 函数
 * （因为我直接用的模板就懒得改了，也不推荐大家改就是了，功能尽量分开一些也方便 Debug）
 *
 * 具体实现方法为：
 *  首先初始化一个大小为 n 的 并查集结构。
 *  然后对输入的边，按照权重 从小到大 进行排序，以保证我们使用最少的木板。
 *  然后我们遍历每一条边，如果发现边两头的岛屿 不在同一块连通区 上，我们就把它们 union 起来。
 *  同时记得更新 最长木板的长度即可。
 *
 * 时间复杂度为：排序O(nlogn) + 并查集的n次操作O(n) => O(nlogn)
 *
 * OJ地址：https://www.nowcoder.com/questionTerminal/59aff3b7a9094432893302c9ee7794e8
 */

import java.util.*;

public class Main {

    static class Edge {
        public int x;
        public int y;
        public int val;

        public Edge(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    static int[] uf;

    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int val = sc.nextInt();
            edges[i] = new Edge(x, y, val);
        }
        System.out.println(getAnswer(edges, n, m));
    }

    private static int getAnswer(Edge[] edges, int n, int m) {
        int rst = 0;
        uf = new int[n + 1];
        // 初始化并查集，使得每个点刚开始的 parent 指向其自身
        initialize(n);
        // 对连接岛屿所需要的木板按从小到大的长度排序（边权重低的在前面）
        Arrays.sort(edges, (e1, e2) -> (e1.val - e2.val));
        for (int i = 0; i < m; i++) {
            int rootA = findParent(edges[i].x);
            int rootB = findParent(edges[i].y);
            if (rootA != rootB) {
                // 如果两个岛的 root 节点不同，说明不在同一个联通区内，因此将它们 union 起来
                // 同时如果遇到 长度更长的木板，需要更新结果
                if (rst < edges[i].val) {
                    rst = edges[i].val;
                }
                union(rootA, rootB);
            }
        }
        return rst;
    }

    private static void initialize(int n) {
        for (int i = 1; i <= n; i++) {
            uf[i] = i;
        }
    }

    private static int findParent(int index) {
        while (uf[index] != index) {
            uf[index] = uf[uf[index]];
            index = uf[index];
        }
        return index;
    }

    private static void union(int x, int y) {
        // 尽量维持树状结构的平衡
        if (x > y) {
            uf[x] = y;
        } else {
            uf[y] = x;
        }
    }
}
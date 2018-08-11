/*
Problem Description
省政府“畅通工程”的目标是使全省任何两个村庄间都可以实现公路交通
（但不一定有直接的公路相连，只要能间接通过公路可达即可）。
经过调查评估，得到的统计表中列出了有可能建设公路的若干条道路的成本。
现请你编写程序，计算出全省畅通需要的最低成本。

Input
测试输入包含若干测试用例。每个测试用例的第1行给出评估的道路条数 N、村庄数目M ( < 100 )；
随后的 N 行对应村庄间道路的成本，每行给出一对正整数，分别是两个村庄的编号，以及此两村庄间道路的成本（也是正整数）。
为简单起见，村庄从1到M编号。当N为0时，全部输入结束，相应的结果不要输出。

Output
对每个测试用例，在1行里输出全省畅通需要的最低成本。若统计数据不足以保证畅通，则输出“?”。

Sample Input
3 3
1 2 1
1 3 2
2 3 4
1 3
2 3 2
0 100

Sample Output
3
?
 */

/**
 * Approach 1: Prime
 * 与 Dijkstra 非常类似，都是基于贪心策略的做法。
 * 这里同样也是利用了 优先级队列（最小堆） 来帮助实现。
 * 不同的是，这里的队列里只需要求当前生成树 T 所邻近的所有的边
 * 中的小边。（Dijkstra中求的是当前点的）其余部分基本相同。
 *
 * 视频讲解可以参考：
 *  https://www.bilibili.com/video/av4768483/?p=1
 * Dijkstra:
 *  https://github.com/cherryljr/LeetCode/blob/master/Network%20Delay%20Time.java
 *
 */

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt(); // 道路个数
            if (n == 0) {
                break;
            }
            int m = sc.nextInt(); // 村庄个数
            // Build the graph
            int[][] graph = new int[m + 1][m + 1];
            for (int i = 0; i < n; i++) {
                int from = sc.nextInt(), to = sc.nextInt();
                int cost = sc.nextInt();
                graph[from][to] = cost;
                graph[to][from] = cost;
            }

            // Initialize
            PriorityQueue<Edge> minHeap = new PriorityQueue<>();
            boolean[] visited = new boolean[m + 1];
            for (int i = 1; i <= m; i++) {
                if (graph[1][i] != 0) {
                    minHeap.offer(new Edge(1, i, graph[1][i]));
                }
            }
            visited[1] = true;

            long rst = 0;
            while (!minHeap.isEmpty()) {
                Edge curr = minHeap.poll();
                if (visited[curr.to]) {
                    continue;
                }
                visited[curr.to] = true;
                rst += curr.cost;
                for (int i = 1; i <= m; i++) {
                    if (graph[curr.to][i] != 0) {
                        minHeap.offer(new Edge(curr.to, i, graph[curr.to][i]));
                    }
                }
            }

            for (int i = 1; i <= m; i++) {
                if (!visited[i]) {
                    rst = -1;
                }
            }
            System.out.println(rst == -1 ? "?" : rst);
        }
        sc.close();
    }

    static class Edge implements Comparable<Edge> {
        int from, to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return this.cost - other.cost;
        }
    }
}

/**
 * Approach 2: Kruskal Algorithm
 * 同样也是最小生成树的算法，基于 并查集 来实现。
 * 相对边权按从小到大进行排序。
 * 然后一次次取出来，将边两端的端点连接起来。
 * 如果成功进行了一次 union 则说明该最小生成树需要这条边，因此 rst += cost；
 * 否则说明两个端点已经在同一个 union 中了。（即使用 UnionFind 来实现）
 *
 * 参考视频：
 *  https://www.bilibili.com/video/av4768483/?p=1
 * UnionFind：
 *  https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt(); // 道路个数
            if (n == 0) {
                break;
            }
            int m = sc.nextInt(); // 村庄个数
            Edge[] edges = new Edge[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
            }
            // 对边按照权值进行从小到大的排序
            Arrays.sort(edges);

            // 使用 并查集 实现
            UnionFind uf = new UnionFind(m);
            long rst = 0;
            for (int i = 0; i < n; i++) {
                if (uf.union(edges[i].from, edges[i].to)) {
                    rst += edges[i].cost;
                }
            }

            for (int i = 1; i <= m; i++) {
                if (uf.find(i) != 1) {
                    rst = -1;
                }
            }
            System.out.println(rst == -1 ? "?" : rst);
        }
        sc.close();
    }

    static class Edge implements Comparable<Edge> {
        int from, to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return this.cost - other.cost;
        }
    }

    static class UnionFind {
        int[] parent;

        UnionFind(int n) {
            parent = new int[n + 1];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int index) {
            if (parent[index] != index) {
                parent[index] = find(parent[index]);
            }
            return parent[index];
        }

        public boolean union(int a, int b) {
            int aFather = find(a);
            int bFather = find(b);
            if (aFather == bFather) {
                return false;
            }
            parent[bFather] = aFather;
            return true;
        }
    }

}

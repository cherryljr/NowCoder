/*
时间限制：1秒 空间限制：32768K

牛牛的快递到了，他迫不及待地想去取快递，但是天气太热了，以至于牛牛不想在烈日下多走一步。
他找来了你，请你帮他规划一下，他最少要走多少距离才能取回快递。

输入描述:
每个输入包含一个测试用例。
输入的第一行包括四个正整数，表示位置个数N(2<=N<=10000)，道路条数M(1<=M<=100000)，
起点位置编号S(1<=S<=N)和快递位置编号T(1<=T<=N)。位置编号从1到N，道路是单向的。
数据保证S≠T，保证至少存在一个方案可以从起点位置出发到达快递位置再返回起点位置。
接下来M行，每行包含三个正整数，表示当前道路的起始位置的编号U(1<=U<=N)，
当前道路通往的位置的编号V(1<=V<=N)和当前道路的距离D(1<=D<=1000)。

输出描述:
对于每个用例，在单独的一行中输出从起点出发抵达快递位置再返回起点的最短距离。
示例1
输入
3 3 1 3
1 2 3
2 3 3
3 1 1
输出
7
 */

/**
 * Approach: Dijkstra 
 * 单源到所有节点的最短路径问题，因为图中不含负权值的边，
 * 所以可以直接使用 Dijkstra 模板来解决即可。
 * 
 * 时间复杂度：O(ElogV)
 *
 * Dijkstra模板可以参考：
 * Network Delay Time
 * 	https://github.com/cherryljr/LeetCode/blob/master/Network%20Delay%20Time.java
 */

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        // 位置个数
        int N = input.nextInt();
        // 道路数目
        int M = input.nextInt();
        // 起点位置
        int S = input.nextInt();
        // 快递位置
        int T = input.nextInt();

        // Build the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < M; i++) {
            graph.computeIfAbsent(input.nextInt(), x -> new ArrayList<>()).add(new int[]{input.nextInt(), input.nextInt()});
        }

        int sTot = dijkstra(graph, S, T);
        int tTos = dijkstra(graph, T, S);
        System.out.println(sTot + tTos);
    }

    private static int dijkstra(Map<Integer, List<int[]>> graph, int start, int end) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{start, 0});
        Map<Integer, Integer> distances = new HashMap<>();

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int pos = curr[0], dis = curr[1];
            if (distances.containsKey(pos)) {
                continue;
            }
            distances.put(pos, dis);

            if (graph.containsKey(pos)) {
                for (int[] negih : graph.get(pos)) {
                    if (distances.containsKey(negih[0])) {
                        continue;
                    }
                    pq.offer(new int[]{negih[0], negih[1] + dis});
                }
            }
        }

        return distances.get(end);
    }

    static class InputReader {
        BufferedReader buffer;
        StringTokenizer token;

        InputReader() {
            buffer = new BufferedReader(new InputStreamReader(System.in));
        }

        boolean hasNext() {
            while (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(buffer.readLine());
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        String next() {
            if (hasNext()) return token.nextToken();
            return null;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        BigDecimal nextBigDecimal() {
            return new BigDecimal(next());
        }
    }
}

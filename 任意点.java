/*
题目描述
平面上有若干个点，从每个点出发，你可以往东南西北任意方向走，直到碰到另一个点，然后才可以改变方向。
请问至少需要加多少个点，使得点对之间互相可以到达。

输入描述:
第一行一个整数n表示点数（ 1 <= n <= 100)。
第二行n行，每行两个整数xi, yi表示坐标（ 1 <= xi, yi <= 1000)。
y轴正方向为北，x轴正方形为东。
输出描述:
输出一个整数表示最少需要加的点的数目。

示例1
输入
2
2 1
1 2
输出
1

示例2
输入
2
2 1
4 1
输出
0
 */

/**
 * Approach: Union Find
 * 横坐标/纵坐标 相同的点都能够被走到，即他们能够组成一个 联通区。
 * 并且这个过程是一个个点逐渐连起来的，毫无疑问使用 并查集 是最佳选择。
 * 对所有的点进行一次遍历，看其后面的点能不能跟当前点在同一个联通区内即可，
 * 判断条件就是 横/纵坐标是否相等。（只要有一个相等即可）
 * 最后，我们找有几个联通区，然后 -1 即可。
 *  比如有两个联通区，那么我们只需要再加一个点，必定可以使他们联通起来。
 * 时间复杂度为：O(n^2)
 *
 * 并查集模板（基于数组实现）
 * https://github.com/cherryljr/LintCode/blob/master/Graph%20Valid%20Tree.java
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int n = input.nextInt();
        int[][] points = new int[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = input.nextInt();
            points[i][1] = input.nextInt();
        }

        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (points[i][0] == points[j][0] || points[i][1] == points[j][1]) {
                    uf.union(i, j);
                }
            }
        }

        int rst = 0;
        for (int i = 0; i < n; i++) {
            if (uf.parent[i] == i) {
                rst++;
            }
        }
        System.out.println(rst - 1);
    }

    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int compressedFind(int index) {
            if (index != parent[index]) {
                parent[index] = compressedFind(parent[index]);
            }
            return parent[index];
        }

        public void union(int a, int b) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather != bFather) {
                if (rank[aFather] > rank[b]) {
                    parent[bFather] = aFather;
                    rank[aFather] += rank[bFather];
                } else {
                    parent[aFather] = bFather;
                    rank[bFather] += rank[aFather];
                }
            }
        }
    }

    // 输入处理模板
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
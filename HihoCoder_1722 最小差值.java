/*
时间限制:10000ms
单点时限:1000ms
内存限制:256MB

描述
给定N个数组，每个数组都包含M个整数。
现在你被要求从每个数组中选出一个数，总共N个数，然后求出其中最大与最小的差值。
在MN种选法中，差值最小是多少？

输入
第一行包含两个整数N和M。
以下N行，每行包含M个整数。
对于50%的数据，1 ≤ N × M ≤ 10000
对于100%的数据，1 ≤ N × M ≤ 200000 0 ≤ 每个整数 ≤ 1000000

输出
最小的差值

样例输入
3 3
8 1 6
3 5 7
4 9 2

样例输出
2
 */

/**
 * Approach: PriorityQueue
 * 属于 Merge k Sorted Arrays 的 Fellow Up. 难度上并没有太大的提升.
 * 首先我们需要将每个数组进行一次排序（从小到大）
 * 然后与 Merge k Sorted Arrays 做法相同，
 * 利用每个数组的最小值（每行）来初始化优先级队列（最小堆）
 * 然后每次从 pq 里 poll 出最小值，求最小差值。
 * 并且如果当前值后面还有元素就可以将后续元素继续添加到队列中。
 * 对此我们需要保存该元素在矩阵中的坐标位置。
 *
 * 时间复杂度：O(NlogM)
 * 空间复杂度：O(N)
 *
 * Merge k Sorted Arrays:
 *  https://github.com/cherryljr/LintCode/blob/master/Merge%20k%20Sorted%20Arrays.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        int[][] matrix = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        int max = Integer.MIN_VALUE;
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < M; i++) {
            // 对每行数组进行一次排序
            Arrays.sort(matrix[i]);
            // 求初始化时的最大值（最小值可以通过优先级队列来获取）
            max = Math.max(max, matrix[i][0]);
            pq.offer(new Node(matrix[i][0], i, 0));
        }

        while (!pq.isEmpty()) {
            // 获取当前所选数组中的最小值
            Node curr = pq.poll();
            // Update minDiff
            minDiff = Math.min(minDiff, max - curr.value);
            if (curr.col < N - 1) {
                int nextValue = matrix[curr.row][curr.col + 1];
                max = Math.max(max, nextValue);
                // 将当前元素所属数组中的下一个元素添加到队列中
                pq.offer(new Node(nextValue, curr.row, curr.col + 1));
            } else {
                // 如果没有后续可以添加的元素，直接退出即可
                break;
            }
        }
        System.out.println(minDiff);
    }

    static class Node implements Comparable<Node> {
        int row, col;
        int value;

        public Node(int value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Node other) {
            return this.value - other.value;
        }

    }

}
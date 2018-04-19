/*
【题目】
给定两个数组arr1和arr2，arr1和arr2按照从小到大的顺序排列，再给定一个整数k，
返回来自arr1和arr2的两个数相加和最大的前k个，两个数必须分别来自两个数组。
【举例】
arr1=[1,2,3,4,5]， arr2=[3,5,7,9,11]， k=4。
返回数组[16,15,14,14]。
【要求】
时间复杂度达到O(klogk)。
 */

/**
 * Approach: PriorityQueue
 * topK 问题最经常使用到的就是 堆 这个数据结构了。
 * 因为题目要求 O(klogk) 的时间复杂度，这就意味着我们只能开 O(k) 大小的堆。
 * 给定的两个数组是有序的，因此最大值是可以确定的。
 * 那么首先我们将最大值加入到 最大堆 中。
 * 而次大值只会来自于 arr1 中的最大值 与 arr2 中的次大值之和 或者是 arr1 中的次大值 与 arr2 中的最大值之和
 * 对应于 arr1 与 arr2 各个位置上对应的两两之和的矩阵，就是 最大值 的 左侧 和 上面 的两个值。
 * 因此我们每次的操作就是：
 *  从 maxHeap 中 poll 出来当前最大值，并将其相邻的两个备选值加入到 maxHeap 中。
 * 同时了为了保证矩阵中同一个位置上的数不会被重复添加，我们需要一个 Set 来记录当前 sum 所对应的 row 和 column.
 * 如：
 *    X 7
 *    6 8
 * 当将 6,7 poll出去之和，我们会重复添加 X 位置的值，这是错误的。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
        int[] arr1 = new int[n];
        int[] arr2 = new int[m];
        for (int i = 0; i < n; i++) {
            arr1[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            arr2[i] = sc.nextInt();
        }

        int[] rst = topKSum(arr1, arr2, k);
        for (int num : rst) {
            System.out.print(num + " ");
        }
    }

    private static int[] topKSum(int[] arr1, int[] arr2, int topK) {
        if (arr1 == null || arr1.length == 0 || arr2 == null || arr2.length == 0 || topK <= 0) {
            return new int[]{};
        }

        int n = arr1.length, m = arr2.length;
        // 看 topK 大小是否已经超过了两个数列所有的两两之和（矩阵大小）
        topK = Math.min(topK, n * m);
        int rstIndex = 0;
        int[] rst = new int[topK];
        PriorityQueue<Pair> maxHeap = new PriorityQueue<>((a, b) -> b.sum - a.sum);
        // 用于记录某个位置的值是否已经被添加过了
        Set<String> visited = new HashSet<>();
        maxHeap.offer(new Pair(n -1 , m - 1, arr1[n - 1] + arr2[m - 1]));
        visited.add((n - 1) + "#" + (m - 1));

        while (rstIndex < topK) {
            Pair curr = maxHeap.poll();
            rst[rstIndex++] = curr.sum;
            int rowIndex = curr.row, colIndex = curr.col;
            // 如果存在且未被添加过，则将 curr 上面的点放入 maxHeap 中
            if (rowIndex > 0 && !visited.contains((rowIndex - 1) + "#" + colIndex)) {
                maxHeap.offer(new Pair(rowIndex - 1, colIndex, arr1[rowIndex - 1] + arr2[colIndex]));
                visited.add((rowIndex - 1) + "#" + colIndex);
            }
            // 如果存在且未被添加过，则将 curr 左侧的点放入 maxHeap 中
            if (colIndex > 0 && !visited.contains(rowIndex + "#" + (colIndex - 1))) {
                maxHeap.offer(new Pair(rowIndex, colIndex - 1, arr1[rowIndex] + arr2[colIndex - 1]));
                visited.add(rowIndex + "#" + (colIndex - 1));
            }
        }

        return rst;
    }

    static class Pair {
        int row, col;
        int sum;

        public Pair(int row, int col, int sum) {
            this.row = row;
            this.col = col;
            this.sum = sum;
        }
    }
}
/*
小Q今天在上厕所时想到了这个问题：有n个数，两两组成二元组，差最小的有多少对呢？差最大呢？

输入描述:
输入包含多组测试数据。
对于每组测试数据：
N - 本组测试数据有n个数
a1,a2...an - 需要计算的数据
保证:
1<=N<=100000,0<=ai<=INT_MAX.

输出描述:
对于每组数据，输出两个数，第一个数表示差最小的对数，第二个数表示差最大的对数。

示例1
输入
6
45 12 45 32 5 6
输出
1 2
 */

/**
 * Approach: Sort + HashMap
 * 比较简单的题目。通常遇到求 最大/最小差值 的情况，我们都会利用到排序。
 * 因此首先我们相对整个数组进行排序即可。
 * 然后 最大差值 必定在数组的 头部元素 和 尾部元素 之间产生。
 * 而 最小差值 必定在两个 相邻元素 之间产生。
 * 又因为数组内可能包含 重复元素。
 * 这给我们统计的时候带来了一点点麻烦，所以我们需要利用 HashMap 来统计
 * 各个元素，以及其对应的出现次数，接下来只需要计算即可。
 *
 * 注意：
 *  1. 排序后可以检查一下数组的 头尾元素是否相等，如果相等说明数组内元素均相同，直接利用
 *  C(n, 2) 计算出差值对数即可。
 *  2. 题目包含 多组测试数据，因此请务必使用 while(sc.hasNext()).
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            // 先对数组进行排序以方便统计
            Arrays.sort(arr);
            // 如果数组中的值全相同，直接两两组合 C(n, 2)
            if (arr[0] == arr[n - 1]) {
                int rst = (n * (n - 1)) / 2;
                System.out.println(rst + " " + rst);
                continue;
            }
            // 利用 map 来统计各个数出现的次数
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            }

            // 求差最小的对数
            int minCount = 0;
            if (map.size() == n) {
                // 如果 map的大小 等于 数组大小，说明没有重复元素
                // 则遍历一遍数组，计算相邻元素的差值，取最小即可
                int min = Integer.MAX_VALUE;
                for (int i = 1; i < n; i++) {
                    int temp = arr[i] - arr[i - 1];
                    if (temp < min) {
                        min = temp;
                        minCount = 1;
                    } else if (temp == min) {
                        minCount++;
                    }
                }
            } else {
                // 若 map的大小 小于 数组大小，说明包含重复元素，即 min=0,
                // 则直接计算由重复元素组成的对数即可（可能存在多组重复元素）
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    int value = entry.getValue();
                    if (value > 1) {
                        minCount += (value * (value - 1) >> 1);
                    }
                }
            }

            // 求差最大的对数
            // 直接取出 最小值 和 最大值 的出现次数相乘即可
            int maxCount = 0;
            int val1 = map.get(arr[0]);
            int val2 = map.get(arr[n - 1]);
            maxCount = val1 * val2;
            System.out.println(minCount + " " + maxCount);
        }
    }

}

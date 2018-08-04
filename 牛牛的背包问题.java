/*
牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
牛牛家里一共有n袋零食, 第i袋零食体积为v[i]。
牛牛想知道在总体积不超过背包容量的情况下,他一共有多少种零食放法(总体积为0也算一种放法)。

输入描述:
输入包括两行
第一行为两个正整数n和w(1 <= n <= 30, 1 <= w <= 2 * 10^9),表示零食的数量和背包的容量。
第二行n个正整数v[i](0 <= v[i] <= 10^9),表示每袋零食的体积。

输出描述:
输出一个正整数, 表示牛牛一共有多少种零食放法。

示例1
输入
3 10
1 2 4
输出
8

说明
三种零食总体积小于10,于是每种零食有放入和不放入两种情况，一共有2*2*2 = 8种情况。
 */

/**
 * Approach: Binary Enumeration + Binary Search
 * 根据数据量可以推测出应该采用折半枚举 + 二分查找的方法。
 * 与 High Capacity Backpack 基本相同，只不过这里求的是方法数罢了
 * 相对的会更加简单一些。
 *
 * High Capacity Backpack:
 *  https://github.com/cherryljr/LintCode/blob/master/High%20Capacity%20Backpack.java
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int w = sc.nextInt();
            int[] snacks = new int[n];
            for (int i = 0; i < n; i++) {
                snacks[i] = sc.nextInt();
            }

            int half = n >> 1;
            long[] records = new long[1 << half];
            for (int i = 0; i < 1 << half; i++) {
                long sum = 0;
                for (int j = 0; j < half; j++) {
                    if ((i >> j & 1) == 1) {
                        sum += snacks[j];
                    }
                }
                records[i] = sum;
            }
            Arrays.sort(records);

            int left = n - half;
            int rst = 0;
            for (int i = 0; i < 1 << left; i++) {
                long sum = 0;
                for (int j = 0; j < left; j++) {
                    if ((i >> j & 1) == 1) {
                        sum += snacks[half + j];
                    }
                }
                rst += upperBound(records, w - sum);
            }
            System.out.println(rst);
        }
    }

    private static int upperBound(long[] records, long target) {
        int left = -1, right = records.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1 >> 1);
            if (target >= records[mid]) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left + 1;
    }
}

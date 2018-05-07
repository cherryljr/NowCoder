/*
题目描述
航天飞行器是一项复杂而又精密的仪器，飞行器的损耗主要集中在发射和降落的过程，
科学家根据实验数据估计，如果在发射过程中，产生了 x 程度的损耗，那么在降落的过程中就会产生 x2 程度的损耗，
如果飞船的总损耗超过了它的耐久度，飞行器就会爆炸坠毁。
问一艘耐久度为 h 的飞行器，假设在飞行过程中不产生损耗，那么为了保证其可以安全的到达目的地，
只考虑整数解，至多发射过程中可以承受多少程度的损耗？

输入描述:
每个输入包含一个测试用例。每个测试用例包含一行一个整数 h （1 <= h <= 10^18）。
输出描述:
输出一行一个整数表示结果。

示例1
输入
10
输出
2
 */

/**
 * Approach: Binary Search
 * 数据量为 10^18，O(n)的方法必爆，因此想到使用 O(logn) 的二分法。
 * 二分法求最后一个符合条件的值（小于等于）
 * 因此使用：二分法上界的方法
 *
 * 关于如何使用二分法的详细解释可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        long n = sc.nextLong();
        System.out.println(binarySearch(n));
    }

    private static long binarySearch(long n) {
        long start = 0, end = (long) Math.sqrt(n);
        while (start < end) {
            long mid = start + ((end - start + 1) >> 1);
            if (n >= mid * (mid + 1)) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }
}
/*
小Q的父母要出差N天，走之前给小Q留下了M块巧克力。
小Q决定每天吃的巧克力数量不少于前一天吃的一半，
但是他又不想在父母回来之前的某一天没有巧克力吃，
请问他第一天最多能吃多少块巧克力

输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含两个正整数，表示父母出差的天数N(N<=50000)和巧克力的数量M(N<=M<=100000)。

输出描述:
输出一个数表示小Q第一天最多能吃多少块巧克力。

输入例子1:
3 7

输出例子1:
4
 */

/**
 * Approach: Binary Search (Upper Bound)
 * 题目要求的是：最多能够次多少块巧克力。
 * 即不大于 target 的最大值，因此对应的就是使用 二分法求上界。
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt(), m = sc.nextInt();
            int start = 1, end = m;
            while (start < end) {
                int mid = start + (end - start + 1 >> 1);
                int count = getSum(mid, n);
                if (m >= count) {
                    start = mid;
                } else {
                    end = mid - 1;
                }
            }
            System.out.println(end);
        }
    }

    private static int getSum(int x, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += x;
            x = (x + 1) >> 1;   // 向上取整
        }
        return sum;
    }
}
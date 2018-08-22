/*
Description
In a certain course, you take n tests. If you get ai out of bi questions correct on test i,
your cumulative average is defined to be
    http://poj.org/problem?id=2976

Given your test scores and a positive integer k, determine how high you can make your cumulative average
if you are allowed to drop any k of your test scores.
Suppose you take 3 tests with scores of 5/5, 0/1, and 2/6.
Without dropping any tests, your cumulative average is 100 * (5+0+2) / (5+1+6) = 50.
However, if you drop the third test, your cumulative average becomes 100 * (5+0) / (5+1) = 83.33≈83.

Input
The input test file will contain multiple test cases, each containing exactly three lines.
The first line contains two integers, 1 ≤ n ≤ 1000 and 0 ≤ k < n.
The second line contains n integers indicating ai for all i.
The third line contains n positive integers indicating bi for all i.
It is guaranteed that 0 ≤ ai ≤ bi ≤ 1, 000, 000, 000.
The end-of-file is marked by a test case with n = k = 0 and should not be processed.

Output
For each test case, write a single line with the highest cumulative average possible after dropping k of the given test scores.
The average should be rounded to the nearest integer.

Sample Input
3 1
5 0 2
5 1 6
4 2
1 2 7 9
5 6 7 9
0 0
Sample Output
83
100
Hint

To avoid ambiguities due to rounding errors,
the judge tests have been constructed so that all answers are at least 0.001 away from a decision boundary
(i.e., you can assume that the average is never 83.4997).
 */

/**
 * Approach: Greedy + BinarySearch
 * 题意：有N个考试，每个考试有ai和bi两个值，最后成绩由上面的公式求得。幸运的是，可以放弃K个科目，求最大化最后的成绩。
 * 思路：该题实质上就是一个 最大化平均值 的问题
 * 最大化平均值：有n个物品的重量和价值分别为 wi 和 vi，从中选择 k 个物品使得单位重量的价值最大。
 * 
 * 那么首先本题需要确定一个贪心策略，来去掉那些对正确率贡献小的考试。
 * 那么如何确定某个考试[ai, bi]对总体准确率x的贡献呢？
 * ai / bi肯定是不行的，不然例子里的[0,1]会首当其冲被刷掉。
 * 因此这里需要使用到 01分数规划 的一个技巧：
 * 题目求的是 max(∑a[i] / ∑b[i])，其中a,b都是一一对应的。
 * 那么可以转化一下：
 *   令 r = ∑a[i] / ∑b[i]，则有 ∑a[i] - ∑b[i] * r = 0；
 * 然后我们就可以 二分枚举 r 的值即可。
 * 对于每一个 r, 求出每个 a[i] - b[i] * r; 然后排序，去除 k 个最小值。
 * 然后相加得到结果 Q(r)，
 * 如果 Q(r) > 0 说明 r 的值偏小，因为可能存在 r 使得 Q(r) = 0；
 * 如果 Q(r) < 0 说明选取的 r 值过大；
 * 
 * 时间复杂度：O(nlogn)
 * 
 * 类似的问题：
 *  Maximum Average Subarray II: (最大化平均值)
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Average%20Subarray%20II.java
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt(), k = sc.nextInt();
            if (n == 0 && k == 0) {
                break;
            }
            double[] a = new double[n];
            double[] b = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextDouble();
            }
            for (int i = 0; i < n; i++) {
                b[i] = sc.nextDouble();
            }
            System.out.printf("%.0f\n", 100 * binarySearch(a, b, n, k));
        }
    }

    private static double binarySearch(double[] a, double[] b, int n, int k) {
        double min = 0.0, max = 1.0;
        while (Math.abs(max - min) > 1e-6) {
            double mid = (max + min) / 2;
            double[] arr = new double[n];
            for (int i = 0; i < n; i++) {
                arr[i] = a[i] - mid * b[i];
            }
            // Greedy, sort the arr and remove the k smallest elements.
            Arrays.sort(arr);
            double sum = 0;
            for (int i = k; i < n; i++) {
                sum += arr[i];
            }
            if (sum < 0) {
                max = mid;
            } else {
                min = mid;
            }
        }
        return min;
    }
    
}



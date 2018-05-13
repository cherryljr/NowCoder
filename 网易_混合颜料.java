/*
题目描述
你就是一个画家！你现在想绘制一幅画，但是你现在没有足够颜色的颜料。
为了让问题简单，我们用正整数表示不同颜色的颜料。
你知道这幅画需要的n种颜色的颜料，你现在可以去商店购买一些颜料，
但是商店不能保证能供应所有颜色的颜料，所以你需要自己混合一些颜料。
混合两种不一样的颜色A和颜色B颜料可以产生(A XOR B)这种颜色的颜料
(新产生的颜料也可以用作继续混合产生新的颜色,XOR表示异或操作)。
本着勤俭节约的精神，你想购买更少的颜料就满足要求，所以兼职程序员的你需要编程来计算出最少需要购买几种颜色的颜料？

输入描述:
第一行为绘制这幅画需要的颜色种数n (1 ≤ n ≤ 50)
第二行为n个数xi(1 ≤ xi ≤ 1,000,000,000)，表示需要的各种颜料.
输出描述:
输出最少需要在商店购买的颜料颜色种数，注意可能购买的颜色不一定会使用在画中，只是为了产生新的颜色。

示例1
输入
3
1 7 3
输出
3
 */

/**
 * Approach: Gaussian Elimination (高斯消元法)
 * 第一次看到这道题目并没有什么思路，于是观察其数据量。
 * Xi大小为 10^9 级别，因此不考虑 DP 解决，n 的级别为 50，因此折半枚举也不可能。
 * 再次思考，突然发现，这道题目的问法其实可以被改成：
 *  我们需要选取最少的整数个数，使得他们能代表集合中的全部整数。
 * 因此，这个问题实际上就被转换成了一个 矩阵求秩 的问题，我们需要求的就是 特征向量 的个数。
 * 对此，我们可以使用到 高斯消元法。
 * 时间复杂度为：O(32*n) => O(n)
 *
 * 类似问题 与 高斯消元法的详细解析可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/Maximum%20XOR%20Subset.java
 */

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int index = 0;
        for (int i = 31; i >= 0; i--) {
            int maxValue = Integer.MIN_VALUE, maxIndex = index;
            for (int row = index; row < n; row++) {
                if (((nums[row] >>> i) & 1) == 1 && nums[row] > maxValue) {
                    maxValue = nums[row];
                    maxIndex = row;
                }
            }
            if (maxValue == Integer.MIN_VALUE) {
                continue;
            }

            int temp = nums[index];
            nums[index] = nums[maxIndex];
            nums[maxIndex] = temp;
            maxIndex = index;

            for (int row = 0; row < n; row++) {
                if (row != maxIndex && ((nums[row] >>> i) & 1) == 1) {
                    nums[row] ^= nums[maxIndex];
                }
            }
            index++;
        }

        int rst = 0;
        // 计算特征向量的个数
        for (int row = 0; row < n; row++) {
            rst += nums[row] != 0 ? 1 : 0;
        }
        System.out.println(rst);
    }
}


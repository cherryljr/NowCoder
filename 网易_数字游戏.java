/*
题目描述
小易邀请你玩一个数字游戏，小易给你一系列的整数。你们俩使用这些整数玩游戏。
每次小易会任意说一个数字出来，然后你需要从这一系列数字中选取一部分出来让它们的和等于小易所说的数字。
例如： 如果{2,1,2,7}是你有的一系列数，小易说的数字是11.你可以得到方案2+2+7 = 11.
如果顽皮的小易想坑你，他说的数字是6，那么你没有办法拼凑出和为6。
现在小易给你n个数，让你找出无法从n个数中选取部分求和的数字中的最小数。

输入描述:
输入第一行为数字个数n (n ≤ 20)
第二行为n个数xi (1 ≤ xi ≤ 100000)

输出描述:
输出最小不能由n个数选取求和组成的数

示例1
输入
3
5 1 2
输出
4
 */

/**
 * Approach: Similar to Backpack
 * 基于这样一种迭代的思想：
 * 假如说可以得到最大的数为 k，则再来一个新的数字p，
 * 如果 p<=k+1，则我可以凑出的最大的数为 p+k，
 * 如果 p>k+1，则会出现空挡，我们肯定无法凑出 k+1 。
 *
 * 本题实际上是Google的一道考题的简化版：
 * https://code.google.com/codejam/contest/4244486/dashboard#s=p2&a=2
 */

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Arrays.sort(nums);
        int min = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] > min + 1) {
                break;
            }
            min += nums[i];
        }
        System.out.println(min + 1);
    }
}

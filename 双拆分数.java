/*
对于一个数字串 s，若能找到一种将其分成左右两个非空部分 s1,s2 的方案，使得：
    1、s1,s2 均无前导零
    2、存在两个正整数 a,b，使得 b 整除 a，且 a/b=s1, a*b=s2
那么我们记这是一个合法的分法。特别地，如果一个串有两个或更多个不同的合法的分法，那么我们称这个数字串是双拆分数字串。
给定一个 n，要求构造一个长度恰为 n 的双拆分数字串。如果无解，输出 -1。

输入描述:
输入仅一行一个正整数 n(1 <= n <= 300)。
输出描述:
仅一行一个数字串或者 -1。
示例1
输入
8
输出
24419764
*/

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        if (num <= 3) {
            System.out.println(-1);
        }

        if ((num & 1) == 0) {
            System.out.print("1144");
            num -= 4;
        } else {
            System.out.print("16400");
            num -= 5;
        }
        while (num-- > 0) {
            System.out.print("0");
        }
    }
}
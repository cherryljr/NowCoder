/*
时间限制：1秒
空间限制：32768K

牛牛去犇犇老师家补课，出门的时候面向北方，但是现在他迷路了。
虽然他手里有一张地图，但是他需要知道自己面向哪个方向，请你帮帮他。

输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含一个正整数，表示转方向的次数N(N<=1000)。
接下来的一行包含一个长度为N的字符串，由L和R组成，L表示向左转，R表示向右转。

输出描述:
输出牛牛最后面向的方向，N表示北，S表示南，E表示东，W表示西。

示例1
输入
3
LRR

输出
E
 */

/**
 * Approach: 模拟
 * 从北开始，顺时针旋转，将N,E,S,W分别记为：0,1,2,3
 * 左转相当于逆时针转，即-1；右转+1。
 * 避免结果为负数 加4 再对 4取余。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static final String  DIRS = "NESW";

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int len = sc.nextInt();
        String dirs = sc.next();
        char[] chars = dirs.toCharArray();
        int rst = 0;

        for (int i = 0; i < len; i++) {
            if (chars[i] == 'L') {
                // Turn left
                rst = (rst + 4 - 1) & 3;
            } else {
                // Turn right
                rst = (rst + 4 + 1) & 3;
            }
        }

        System.out.println(DIRS.charAt(rst));
    }
}

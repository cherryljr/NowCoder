/*
一些项目要占用一个会议室宣讲， 会议室不能同时容纳两个项目的宣讲。
 给你每一个项目开始的时间和结束的时间(给你一个数组， 里面 是一个个具体的项目)，
 你来安排宣讲的日程， 要求会议室进行 的宣讲的场次最多。
 返回这个最多的宣讲场次。

 输入：
 n 代表有n个会议需要安排
 之后的 n 行，每行有两个数子，代表会议的开始和结束的时间
 */

/**
 * Approach: Greedy
 * 这道题目与 Minimum Number of Arrows to Burst Balloons 非常地相似，用到同一个 贪心策略
 * https://github.com/cherryljr/LeetCode/blob/master/Minimum%20Number%20of%20Arrows%20to%20Burst%20Balloons.java
 * 
 * 我们只需要按照会议结束的时间进行排序，然后依次安排，就能够安排最多的场数
 */

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static class Meeting {
        int start;
        int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        Meeting[] meetings = new Meeting[n];
        int start, end;
        for (int i = 0; i < n; i++) {
            start = sc.nextInt();
            end = sc.nextInt();
            meetings[i] = new Meeting(start, end);
        }

        Arrays.sort(meetings, (a, b) -> a.end - b.end);
        int sum = 1;
        int endTime = meetings[0].end;
        for (int i = 1; i < meetings.length; i++) {
            if (meetings[i].start < endTime) {
                continue;
            }
            sum++;
            endTime = meetings[i].end;
        }

        System.out.println(sum);
    }
}
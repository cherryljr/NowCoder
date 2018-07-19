/*
聊天
时间限制：1秒
空间限制：32768K

A和B是好友，他们经常在空闲时间聊天，A的空闲时间为[a1 ,b1 ],[a2 ,b2 ]..[ap ,bp ]。
B的空闲时间是[c1 +t,d1 +t]..[cq +t,dq +t],这里t为B的起床时间。这些时间包括了边界点。
B的起床时间为[l,r]的一个时刻。若一个起床时间能使两人在某一时刻聊天，那么这个时间就是合适的，问有多少个合适的起床时间？

输入描述:
第一行数据四个整数：p,q,l,r（1≤p,q≤50,0≤l≤r≤1000)。接下来p行数据每一行有一对整数ai，bi(0≤ai+1>bi,ci+1>di）

输出描述:
输出答案个数

输入例子1:
2 3 0 20
15 17
23 26
1 4
7 11
15 17

输出例子1:
20
 */

/**
 * Approach: Simulation (Find Overlap Interval)
 * 没啥水平的题...初看区间类题，以为会涉及到扫描线或者线段树结果发现直接暴力遍历一遍
 * 判断两个区间是否有交集即可...
 * 题目描述有些坑爹，这边直接做了下修改。
 *
 * 时间复杂度：O(N*M*(R-L))
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int p = sc.nextInt(), q = sc.nextInt();
            int l = sc.nextInt(), r = sc.nextInt();
            int[][] A = new int[p][2];
            int[][] B = new int[q][2];
            for (int i = 0; i < p; i++) {
                A[i][0] = sc.nextInt();
                A[i][1] = sc.nextInt();
            }
            for (int i = 0; i < q; i++) {
                B[i][0] = sc.nextInt();
                B[i][1] = sc.nextInt();
            }

            int count = 0;
            for (int i = l; i <= r; i++) {
                if (isProperTime(A, B, i)) {
                    count++;
                }
            }
            System.out.println(count);
        }
    }

    // 判断 A[] 和 B[] 中所有的时间区间，只要有一个区间有交集即可
    private static boolean isProperTime(int[][] A, int[][] B, int time) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                // 如果发现 A[i] 和 B[j] 这两个区间有交集，则返回 true
                if ((B[j][0] + time >= A[i][0] && B[j][0] + time <= A[i][1])
                        || (A[i][0] >= B[j][0] + time && A[i][0] <= B[j][1] + time)) {
                    return true;
                }
            }
        }
        return false;
    }
}
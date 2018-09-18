/*
小Q有X首长度为A的不同的歌和Y首长度为B的不同的歌，
现在小Q想用这些歌组成一个总长度正好为K的歌单，每首歌最多只能在歌单中出现一次，
在不考虑歌单内歌曲的先后顺序的情况下，请问有多少种组成歌单的方法。

输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含一个整数，表示歌单的总长度K(1<=K<=1000)。
接下来的一行包含四个正整数，分别表示歌的第一种长度A(A<=10)和数量X(X<=100)以及歌的第二种长度B(B<=10)和数量Y(Y<=100)。保证A不等于B。

输出描述:
输出一个整数,表示组成歌单的方法取模。因为答案可能会很大,输出对1000000007取模的结果。

示例1
输入
5
2 3 3 3
输出
9
 */

/**
 * Approach: DP 
 * 这道题目的实质是一个 DP 问题，考察的是杨辉三角的构造。
 * 只有两种长度的歌曲，且方案数跟顺序无关，很明显就是使用 C(m, n) 的方法来计算。
 * 因此问题就转换成了：如何高效地计算出 C(m, n).
 * 这里可以利用数学归纳法：
 * 由C(n,k) = C(n-1,k) + C(n-1,k-1）
 * 因此实际上是对应于杨辉三角：
 * 1
 * 1 1
 * 1 2 1
 * 1 3 3 1
 * 1 4 6 4 1
 * ...........
 * 
 * 我们只需要构造出需要的杨辉三角，然后直接取出需要的值计算即可。
 */
import java.util.Scanner;

public class Main {
    public static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int K = sc.nextInt();
            int A = sc.nextInt(), X = sc.nextInt();
            int B = sc.nextInt(), Y = sc.nextInt();
            System.out.println(getResult(K, A, X, B, Y));
        }
        sc.close();
    }

    private static int getResult(int K, int A, int X, int B, int Y) {
        // 构建杨辉三角
        long[][] tri = new long[105][105];
        tri[0][0] = 1;
        for (int i = 1; i < 105; i++) {
            tri[i][0] = 1;
            for (int j = 1; j < 105; j++) {
                tri[i][j] = (tri[i - 1][j - 1] + tri[i - 1][j]) % MOD;
            }
        }

        long rst = 0;
        // 其中 A 长度的歌曲选取 i 首
        for (int i = 0; i <= X; i++) {
            int left = K - A * i;
            // 剩余的长度必须 >= 0 且正好能够被 B 长度的歌曲使用完
            if (left >= 0 && left % B == 0 && left / B <= Y) {
                // C(X, i) * C(Y, left/B)
                rst = (rst + tri[X][i] * tri[Y][left / B]) % MOD;
            }
        }
        return (int)rst;
    }
}
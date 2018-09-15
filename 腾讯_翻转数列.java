/*
小Q定义了一种数列称为翻转数列:
给定整数n和m, 满足n能被2m整除。对于一串连续递增整数数列1, 2, 3, 4..., 每隔m个符号翻转一次, 最初符号为'-';。
例如n = 8, m = 2, 数列就是: -1, -2, +3, +4, -5, -6, +7, +8.
而n = 4, m = 1, 数列就是: -1, +2, -3, + 4.
小Q现在希望你能帮他算算前n项和为多少。

输入描述:
输入包括两个整数n和m(2 <= n <= 109, 1 <= m), 并且满足n能被2m整除。
输出描述:
输出一个整数, 表示前n项和。

输入例子1:
8 2
输出例子1:
8
 */

/**
 * Approach: Mathematics
 * 找规律的问题。这一点从题目的数据规模也可以看出。
 * 因为 n % 2m == 0 所以我们可以把 n 个数分成 n / m 组，
 * 并且我们可以发现每组之和是固定的，都等于 m^2.
 * 因此结果为： n / m * m^2 = n * m / 2
 *
 * 时间复杂度：O(n)
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            long n = sc.nextInt(), m = sc.nextInt();
            System.out.println(n * m >> 1);
        }
    }
}
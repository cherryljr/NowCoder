/*
Description
This is a small but ancient game.
You are supposed to write down the numbers 1, 2, 3, . . . , 2n - 1, 2n consecutively in clockwise order
on the ground to form a circle, and then,
to draw some straight line segments to connect them into number pairs.
Every number must be connected to exactly one another.
And, no two segments are allowed to intersect.
It's still a simple game, isn't it? But after you've written down the 2n numbers,
can you tell me in how many different ways can you connect the numbers into pairs? Life is harder, right?

Input
Each line of the input file will be a single positive number n, except the last line, which is a number -1.
You may assume that 1 <= n <= 100.

Output
For each n, print in a single line the number of ways to connect the 2n numbers into pairs.

Sample Input
2
3
-1
Sample Output
2
5
 */

/**
 * Approach: Catalan Number
 * 这道题目与 求n个结点可构造多少种不同的二叉树 相同，都是 卡特兰数 的应用。
 * 因为是高精度，所以我们需要使用到 BigInteger 这个类来帮助我们解决问题。
 *
 * 卡特兰数的代码可以使用 递归 求解，但是我们都知道 卡特兰数 有几种求解方式，
 * 我们应该使用哪一种最合适呢？
 * 首先，我们可以令 h(0)＝1,h(1)＝1，catalan数满足递归式：
 * h(n)= h(0)*h(n-1) + h(1)*h(n-2) + ... + h(n-1)h(0) (其中n>=2),这是n阶递推关系;
 * 并且可以进一步化简为1阶递推关系:
 *      h(n) = (4n-2)/(n+1)*h(n-1) (n>1) h(0)=1
 *      毫无疑问，这个一阶递推式就是我们需要的
 * 该递推关系的解为：h(n)=C(2n,n)/(n+1) (n=1,2,3,...)
 * 卡 塔兰数例的前几项为[注： n = 0, 1, 2, 3, … n]
 * 1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440,
 * 9694845, 35357670, 129644790, 477638700, 1767263190, 6564120420, 24466267020，...
 *
 * 我们只需要建立一个 BigInteger[] 用来存储递归计算出来的结果即可。（打表）
 */

import java.io.BufferedInputStream;
import java.math.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        BigInteger[] table = new BigInteger[105];

        table[0] = BigInteger.valueOf(1);
        table[1] = BigInteger.valueOf(1);
        for (int i = 2; i <= 100; i++) {
            //  要注意 (4n-2)/(n+1) 可能为小数
            table[i] = table[i - 1].multiply(BigInteger.valueOf(4 * i - 2)).divide(BigInteger.valueOf(i + 1));
        }

        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n == -1) {
                break;
            }
            System.out.println(table[n]);
        }
    }
}
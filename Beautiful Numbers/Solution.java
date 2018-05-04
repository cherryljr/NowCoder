/*
Problem
We consider a number to be beautiful if it consists only of the digit 1 repeated one or more times.
Not all numbers are beautiful, but we can make any base 10 positive integer beautiful by writing it in another base.

Given an integer N, can you find a base B (with B > 1) to write it in such that all of its digits become 1?
If there are multiple bases that satisfy this property, choose the one that maximizes the number of 1 digits.

Input
The first line of the input gives the number of test cases, T. T test cases follow.
Each test case consists of one line with an integer N.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1)
and y is the base described in the problem statement.

Limits
1 ≤ T ≤ 100.
Small dataset
3 ≤ N ≤ 1000.
Large dataset
3 ≤ N ≤ 1018.

Sample
Input
2
3
13
Output
Case #1: 2
Case #2: 3

In case #1, the optimal solution is to write 3 as 11 in base 2.
In case #2, the optimal solution is to write 13 as 111 in base 3.
Note that we could also write 13 as 11 in base 12, but neither of those representations has as many 1s.

O地址：https://code.google.com/codejam/contest/5264487/dashboard#s=p1
 */

/**
 * 这里是为了更像具体地说明如何 估算运行时间，才写了 getResultSmall 方法。
 * 实际过程中，大家只需要一步到位直接写出 getResultBig 即可。
 * （当然也存在大数据的解法写不出来，只能写出小数据的做法拿点分的情况...）
 */

import java.util.*;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = sc.nextInt();
        for (int i = 1; i <= t; ++i) {
            long n = sc.nextLong();
            System.out.println("Case #" + i + ": " + getResultLarge(n));
        }
    }

    /**
     * Approach 1: Enum radix
     * 遍历所有的 2~num-1 的进制，求得能够将其转为 Beautiful Number 的最小进制。
     * 即枚举所有范围内的 radix，然后依据该 radix 将 num 转换成对应的值，并检查该值是否每一位都是1.
     * 因为一个数的 num-1 进制表示是：11，所以必定存在能够将其转成 Beautiful Number 的方案，
     * 也就是将其转为 Beautiful Number 的最大 radix 其实就是 num-1.
     *
     * 时间复杂度分析：对于 small cases 肯定是能过的，这里我们是以 large cases 作为分析标准的。
     * 首先我们需要遍历所有可能的 radix (2~num-1) 需要花费 O(n) 的时间，
     * 其次我们还需要对每个 radix 求是否能够将 num 转换成 Beautiful Number，需要花费 O(logn) 的时间。
     * 因此总体时间复杂度为：O(nlogn)
     * 因为测试数据最大值为：10^18,所以 logn < 64
     * 对此我们可以进行一个估算，首先 10^8 的级别运算是 秒级别的，
     * 即相当于 10^8次运算 约等于 1s
     * 而这里我们需要 10^18 * 64 / 10^8 = 64 * 10^10 s 的时间才能算出最大数据，
     * 这是什么概念呢？相当于 2w 年的时间啊...肯定会出问题的...
     */
    private static long getResultSmall(long num) {
        for (long radix = 2; radix < num; radix++) {
            if (isBeautiful(num, radix)) {
                return radix;
            }
        }
        // Should not reach here
        return num - 1;
    }

    /**
     * @return 判断 num 的 radix 能否转换成一个 Beautiful Number
     */
    private static boolean isBeautiful(long num, long radix) {
        while (num > 0) {
            if (num % radix != 1) {
                return false;
            }
            num /= radix;
        }
        return true;
    }

    /**
     * Approach: Binary Search
     * 我们发现 O(nlogn) 的时间复杂度无法满足要求，因此我们需要考虑更优的解法。
     * 通过观察我们发现，我们的目的就是要将 num -> 11..11 (k个1, k 指的是 bits).
     * 即 num = 1*radix^(k-1) + 1*radix^(k-2) + ... + 1
     * 等式的右边其实就是一个等比数列。当 radix 越大时，k的值就越小。
     * 这二者之间存在一个 单调的线性关系，而我们要求的就是满足条件最小的 radix,即 k 的值要最大。
     * 因为 num 最大只有 10^18，所以完全能用long放下，因此 k 最大就只会到 64.
     * 综上，我们发现 存在单调情况 且 虽然 num 很大，但是 k 的值非常小。
     * 因此我们可以想到对 num 进行 二分查找 来确定符合条件的 radix.
     * 然后，我们可以 从大到小 枚举 k 的值，求解：
     * 能否利用 k个1 来表示 num,如果可以的话，其 radix 是多少。
     * 而在确定 radix 的过程中，我们就应用到了 二分 的做法。
     *
     * 时间复杂度分析：
     * 首先我们需要遍历 k 的范围 (2~64),需要花费 O(logn) 的时间；
     * 然后我们需要在 2~num 的范围内进行 二分查找radix，需要花费 O(logn) 的时间，
     * 然后我们还需要计算出 k个1 按照 radix 可以转换成的十进制数是多少 convert()，需要花费 O(logn) 的时间。
     * 因此总体时间复杂度为：O(logn * logn * logn) => O(log3n) => O(logn)
     */
    private static long getResultLarge(long num) {
        for (int k = 64; k >= 2; k--) {
            long radix = getRadix(num, k);
            if (radix != -1) {
                return radix;
            }
        }
        return num - 1;
    }

    /**
     * Binary Search the radix so that num is 11..1 (bits 1 in total) in that radix.
     * If we can't find the suitable radix (k bits 1 can't form num), return -1.
     */
    private static long getRadix(long num, int bits) {
        long minRadix = 2, maxRadix = num;
        while (minRadix < maxRadix) {
            long midRadix = minRadix + (maxRadix - minRadix) / 2;
            long x = convert(midRadix, bits);
            if (x == num) {
                return midRadix;
            } else if (x < num) {
                minRadix = midRadix + 1;
            } else {
                maxRadix = midRadix;
            }
        }
        return -1;
    }

    /**
     * 将 bits个1 基于 radix 进制转换成 十进制的数
     * 需要注意的是，这里的数据非常大，我们也无法进行 MOD 操作。
     * 因此当超过 Long 所能表示的范围时，联系上下文可得，
     * 我们只需要返回最大值即可。
     */
    private static long convert(long radix, int bits) {
        long component = 1, sum = 0;
        for (int i = 0; i < bits; i++) {
            if (Long.MAX_VALUE - sum < component) {
                sum = Long.MAX_VALUE;
            } else {
                sum += component;
            }

            if (Long.MAX_VALUE / component < radix) {
                component = Long.MAX_VALUE;
            } else {
                component *= radix;
            }
        }
        return sum;
    }
}
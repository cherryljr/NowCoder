/*
时间限制：1秒
空间限制：32768K

牛牛以前在老师那里得到了一个正整数数对(x, y), 牛牛忘记他们具体是多少了。
但是牛牛记得老师告诉过他x和y均不大于n, 并且x除以y的余数大于等于k。
牛牛希望你能帮他计算一共有多少个可能的数对。

输入描述:
输入包括两个正整数n,k(1 <= n <= 10^5, 0 <= k <= n - 1)。

输出描述:
对于每个测试用例, 输出一个正整数表示可能的数对数量。

输入例子1:
5 2

输出例子1:
7

例子说明1:
满足条件的数对有(2,3),(2,4),(2,5),(3,4),(3,5),(4,5),(5,3)
 */

/**
 * Approach: Mathmatics
 * 我们可以在 k+1...n 的范围内枚举 y (当 y<k 时所有余数均小于k，因此不需要考虑)
 * 当除数是 y 时，余数是 0,1,2,3,...,y-1,0,1,2,3... 循环出现，循环节长度为 y
 * 通过以上分析可得：对于一个特定的除数 y, x%y 的值是周期性出现的，周期长度为 y.
 * 并且当 x 在 1~n 范围上时，总共出现了 [n/y] 个周期。
 * 然后我们只需要计算出每个周期内 余数大于等于k 的个数即可。 (n/y * (y-k))
 * 最后再数出不满一个周期的不小于k的余数，就是答案了。       (n % y >= k ? n % y - k + 1 : 0)
 * 注意：
 *  1. 大数统计，可能出现溢出（并未尝试过，如果用 int 过了只能说明测试数据不行）
 *  2. 对于 k=0 要特殊处理，因为对于任意的(x, y)，x%y 永远大于等于0，因此，当 k=0 时，答案为 n*n
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNext()) {
            long n = sc.nextLong();
            long k = sc.nextLong();
            long rst = 0;

            if (k == 0) {
                rst = n * n;
            } else {
                for (long y = k + 1; y <= n; y++) {
                    rst += n / y * (y - k)  // 周期出现的次数 * 周期内余数 >= k 的个数
                            + (n % y >= k ? n % y - k + 1 : 0);  // 剩下不满一个周期内 余数大于等于k 的个数
                }
            }

            System.out.println((rst));
        }
    }
}


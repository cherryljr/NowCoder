/*
时间限制：1秒
空间限制：32768K

小Q得到一个神奇的数列: 1, 12, 123,...12345678910,1234567891011...。
并且小Q对于能否被3整除这个性质很感兴趣。
小Q现在希望你能帮他计算一下从数列的第l个到第r个(包含端点)有多少个数可以被3整除。

输入描述:
输入包括两个整数l和r(1 <= l <= r <= 1e9), 表示要求解的区间两端。

输出描述:
输出一个整数, 表示区间内能被3整除的数字个数。

输入例子1:
2 5

输出例子1:
3

例子说明1:
12, 123, 1234, 12345...
其中12, 123, 12345能被3整除。
 */

/**
 * Approach: Mathmatic
 * 看到数据范围就应该明白，这题不能递推，一是数组开不了这么大，二是递推时间很长。
 * 因此只能用数学方法算出来(这就是数据范围给我们做题的提示)。
 *
 * 一个数字n如果可以被3整除，那么 1, 2, 3, 4, ...分别对3取模得到 1, 2, 0, 1, 2, 0, ...，
 * 这个时候再看题中给出的序列 1, 12, 123, 1234, ...
 * 可以发现，1, 12, 123, 1234, 12345, ...，
 * 能被3整除的如下false, true, true, false, true, true, false, ...
 * 可以发现这个序列以3为周期，因此，直接按照这个规律写出代码即可。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int start = sc.nextInt();
        int end = sc.nextInt();
        System.out.println(fun(end) - fun(start - 1));
    }

    public static int fun(int x) {
        return x / 3 * 2 + (x % 3 == 0 ? 0 : x % 3 == 1 ? 0 : 1);
    }
}


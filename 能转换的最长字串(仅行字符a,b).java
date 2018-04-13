/*
有一个仅包含’a’和’b’两种字符的字符串s，长度为n，每次操作可以把一个字符做一次转换
（把一个’a’设置为’b’，或者把一个’b’置成’a’)；但是操作的次数有上限m，
问在有限的操作数范围内，能够得到最大连续的相同字符的子串的长度是多少。

输入描述:
第一行两个整数 n , m (1<=m<=n<=50000)，第二行为长度为n且只包含’a’和’b’的字符串s。
输出描述:
输出在操作次数不超过 m 的情况下，能够得到的 最大连续 全’a’子串或全’b’子串的长度。

示例1
输入
8 1
aabaabaa
输出
5
说明
把第一个 'b' 或者第二个 'b' 置成 'a'，可得到长度为 5 的全 'a' 子串。
 */

/**
 * Approach: Binary Search
 * 第一眼看到以为是一个 区间DP 的问题。时间复杂度为 O(n^2) 写了一下发现超时了...
 * 因此我们只能考虑 O(nlogn) 的解法了。
 *
 * 重新分析题目：
 * 该字符串非 a 即 b 也就是说在区间 left~right 之间把所有字符变为 a 所需的步骤数是 该区间内 b 的数量。反之亦然.
 * 用数组 count_a[i] 表示字符串中位置区间 0~i-1 包含的 a 的个数 (为了方便起见count_a数组大小为 n+1)
 * 则 区间left~right 的 a 的个数为 count_a[right] - count_a[left-1]
 * b 的个数可以利用 a 的个数算出即区间 left~right 的 b 的个数为: 区间总字符个数（区间长度）- 字符'a'的个数
 * right - left + 1 - (count_a[right] - count_a[left-1])
 * 在区间 left~right 的 a 和 b 的个数已知的情况下
 *  若 区间长度step内的 a 的个数 <= m 则 可以通过 m 个步骤 产生 长度为step的字符串 b
 *  若 区间长度step内的 b 的个数 <= m 则 可以通过 m 个步骤 产生 长度为step的字符串 a
 * 归纳为：若 区间长度step内的 b 或 a 的个数 <= m 则 可以通过 m 个步骤 产生 长度为step的字符串
 *
 * 这样问题就转成成求一个 最长字符串长度（区间长度step） 使得 a 或 b 的个数 <= m。
 * 在已有 count_a[] 的情况下，因为 step 是单调的，所以我们可以对其进行二分从而实现快速确定区间长度。
 * 检查一个长度step是否可行的时间复杂度为O(n)；二分搜索的时间复杂度为O(logn)。
 * 因此，该方法总的时间复杂度为 O(nlogn)
 *
 * 注意边界条件的处理：通常直接举个例子调试一遍就行了。
 * 对于 二分法 不清楚的可以参考：
 * https://github.com/cherryljr/NowCoder/blob/master/%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0.java
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int m = sc.nextInt();
        String str = sc.next();
        char[] arr = str.toCharArray();
        // 计算字符'a'个数的前缀和数组
        int[] count_a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            count_a[i] = count_a[i - 1] + (arr[i - 1] == 'a' ? 1 : 0);
        }

        // 二分法求上界
        int left = 0, right = n;
        while (left < right) {
            int mid = left + ((right - left + 1) >> 1);
            if (canBeTransformed(mid, count_a, m)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        System.out.println(left);
    }

    private static boolean canBeTransformed(int step, int[] count_a, int m) {
        for (int i = 0; i + step < count_a.length; i++) {
            int a = count_a[i + step] - count_a[i];     // 区间内字符'a'的个数
            // 如果将 'a' 全部转换成 'b' 的次数 <= m 返回 true
            if (a <= m) {
                return true;
            }
            // 如果将 'b' 全部转换成 'a' 的次数 <= m 返回 true
            if (step - a <= m) {
                return true;
            }
        }
        return false;
    }
}

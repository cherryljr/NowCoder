/*
时间限制：1秒
空间限制：32768K

小Q正在给一条长度为n的道路设计路灯安置方案。
为了让问题更简单,小Q把道路视为n个方格,需要照亮的地方用'.'表示, 不需要照亮的障碍物格子用'X'表示。
小Q现在要在道路上设置一些路灯, 对于安置在pos位置的路灯, 这盏路灯可以照亮pos - 1, pos, pos + 1这三个位置。
小Q希望能安置尽量少的路灯照亮所有'.'区域, 希望你能帮他计算一下最少需要多少盏路灯。

输入描述:
输入的第一行包含一个正整数t(1 <= t <= 1000), 表示测试用例数
接下来每两行一个测试数据, 第一行一个正整数n(1 <= n <= 1000),表示道路的长度。
第二行一个字符串s表示道路的构造,只包含'.'和'X'。

输出描述:
对于每个测试用例, 输出一个正整数表示最少需要多少盏路灯。

输入例子1:
2
3
.X.
11
...XX....XX

输出例子1:
1
3
 */

/**
 * Approach: Greedy
 * 不知道为什么网易这么喜欢路灯呢...
 * 之前可考过这么一道题目，而且还十分相似...都是对 贪心 的考察
 * https://github.com/cherryljr/NowCoder/blob/master/%E7%BD%91%E6%98%93_%E8%B7%AF%E7%81%AF.java
 *
 * 没什么好说的了，看到之后第一反应就是 贪心.
 * 因为非常明显，我们需要每个灯都能尽可能地覆盖 最大范围。
 * 那么对于 "..." 这三个位置而言，肯定是放在 第二个 位置上是最优的。
 * 因此我们可以按照如上的策略，遍历整个字符串：
 *  遇到 障碍 时不用管，直接跳过即刻。
 *  遇到 需要照亮的位置 时，我们直接向后跳 3 个位置（路灯能照亮的距离）
 *
 * 时间复杂度为：O(n)
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        for (int T = sc.nextInt(); T-- > 0;) {
            int n = sc.nextInt();
            int rst = 0;
            String str = sc.next();
            int pos = 0;
            while (pos < str.length()) {
                // 遇到障碍
                if (str.charAt(pos) == 'X') {
                    pos++;
                    continue;
                }
                // 遇到需要照亮的位置
                if (str.charAt(pos) == '.') {
                    rst++;
                    pos += 3;
                }
            }
            System.out.println(rst);
        }
    }
}


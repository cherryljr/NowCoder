/*
题目描述
一个字符串S是偶串当且仅当S中的每一个字符都出现了偶数次。如字符串”aabccb”是一个偶串，因为字符a,b,c都出现了两次。
而字符串”abbcc”不是偶串，因为字符a出现了一次。

现在给出一个长度为n的字符串T=t1,t2,t3,…,tn。字符串的子串为其中任意连续一段。
T长度为1的子串有n个，长度为2的子串有n-1个，以此类推，T一共有n(n+1)/2个子串。
给定T，你能算出它有多少个子串是偶串吗？

输入
输入一个字符串T，T中只有小写字母。T的长度不超过100000。

样例输入
abbc

输出
输出一个数，T的所有子串中偶串的个数。

样例输出
1

时间限制
C/C++语言：2000MS其它语言：4000MS
内存限制
C/C++语言：65536KB其它语言：589824KB
 */

/**
 * Approach: PreXOR + HashMap
 * 属于利用 PreSum + HashMap 解决 Subarray 问题较为经典的改编。
 * 一开始确实较难想出最优解，但是可以通过一步步进行优化，从而达到目标。
 * 关键点在于：
 *  我们并不关心某一段区间内某个字符的出现个数具体为多少个，
 *  而是仅仅需要 奇/偶 这个信息。因此可以将其优化为 0/1 的表示。
 *  然后在通过异或运算进行改变状态。
 *  从而达到最终优化的目的。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 参考资料：
 *  http://exercise.acmcoder.com/online/online_judge_answer_pdf?ques_id=3980&konwledgeId=42
 * Maximum Size Subarray Sum Equals k:
 *  https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            Map<Integer, Integer> map = new HashMap<>();
            // 初始化表示所有的字母都出现0(偶数)次
            map.put(0, 1);

            // 用低26bit表示下标为[0,i]的子串所拥分别有的字母是偶数个（0）还是奇数个（1）。
            int preXOR = 0;
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                // 求加入当前字符后，拥有字母个数的奇偶性。如果异或后是0，表示加入该字符后有偶数个字母，反之为奇数个
                preXOR ^= (1 << (str.charAt(i) - 'a'));
                if (map.containsKey(preXOR)) {
                    count += map.get(preXOR);
                    map.put(preXOR, map.get(preXOR) + 1);
                } else {
                    map.put(preXOR, 1);
                }
            }

            System.out.println(count);
        }
        sc.close();
    }
}
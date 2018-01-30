/*
如果一个单词通过循环右移获得的单词，我们称这些单词都为一种循环单词。
例如：picture 和 turepic 就是属于同一种循环单词。
现在给出n个单词，需要统计这个n个单词中有多少种循环单词。

输入描述:
输入包括n+1行：
第一行为单词个数n(1 ≤ n ≤ 50)
接下来的n行，每行一个单词word[i]，长度length(1 ≤ length ≤ 50)。由小写字母构成

输出描述:
输出循环单词的种数

示例1
输入
5
picture
turepic
icturep
word
ordw

输出
2
 */

/**
 * 这道题目的考点其实大家都非常熟悉：
 *  如何判断一个单词是否由另外一个单词旋转得来。（剑指offer中非常经典的一道题目）
 * 解法就是在原来的单词后面再次 append 原单词，比如：picture => picturepicture
 * 这样处理过后的单词就能够 contains 原单词的所有旋转情况。
 * 这也是本题中 循环单词 的定义。
 * 
 * 那么知道了题目的核心考点之后，本题要求的是在给定的所有单词中有几种 循环单词？
 * 因此我们需要一个 容器 来存储当前遇到单词的 所有循环单词 情况。
 * 即每当遇到一个没有被 contains 的单词，我们就把该单词所有的 循环单词情况全部 add 到容器中。
 * 这里我们使用了 List, 当然 Set 也完全可以。
 */
import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<String> list = new ArrayList<>();
        int count = 0;
        // 总共有 n 个单词
        for (int i = 0; i < n; i++) {
            String str = sc.next();
            // 如果 List 中不能找到该单词，则说明该单词为新的一种
            if (!list.contains(str)) {
                count++;
                // 将该单词的 所有循环单词 全部加入到 List 中
                StringBuilder sb = new StringBuilder(str);
                sb.append(str);
                for (int j = 0; j < str.length(); j++) {
                    list.add(sb.substring(j, j + str.length()));
                }
            }
        }

        System.out.println(count);
    }
}


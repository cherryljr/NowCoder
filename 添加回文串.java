/*
对于一个字符串，我们想通过添加字符的方式使得新的字符串整体变成回文串，
但是只能在原串的结尾添加字符，请返回在结尾添加的最短字符串。

给定原字符串A及它的长度n，请返回添加的字符串。保证原串不是回文串。

测试样例：
"ab",2
返回："a"
 */

/**
 * Approach 1: KMP + Reverse
 * 这道题与 Shortest Palindrome 区别在于，这道题是在字符串的 结尾 添加最短字符串形成回文串。
 * 因此同样能够利用 KMP + Reverse 的方法来解决。
 * 将求解实质转换为：原字符串后缀 与 逆序串前缀 的 最长相同部分。 （s的最长回文字串，结尾为最后一个字符）
 *  1. 构建字符串 str = reverse(s) + '#' + s.
 *  2. 然后对 str 利用 KMP 算法求 next 数组，str 终止位置的 endNext 值.
 *  3. 最终结果为：s + reverse(s).substring(endNext)
 *
 * 如果无法将思想转换过来可以参考 Shortest Palindrome 中对该思路的详细分析：
 * https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Palindrome.java
 */
import java.util.*;

public class Palindrome {
    public String addToPalindrome(String A, int n) {
        if (A == null || A.length() <= 1) {
            return A;
        }

        StringBuilder sb = new StringBuilder(A);
        String reverse = sb.reverse().toString();
        sb.append('#').append(A);
        int endNext = endNextLength(sb.toString().toCharArray());

        return reverse.substring(endNext);
    }

    private int endNextLength(char[] arr) {
        int[] next = new int[arr.length + 1];
        next[0] = -1;
        int pos = 2, cn = 0;
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next[next.length - 1];
    }
}

/**
 * Approach 2: Manacher
 * 这道题目还有另外一种思路（这个思路在 Shortest Palindrome 有所提及）
 * 即这道题目可以转换为：
 *      以字符串 末尾字符 作为结尾的最长回文子串
 * （注：在 Shortest Palindrome 中我们求解的便是 以字符串 首字母 作为开头的最长回文子串）
 * 求出这个之后，我们只需要在字符串末尾加上 原本字符串除去回文字符串部分 的 逆序 就是我们需要的答案。
 *
 * 而求一个字符串的最长回文子串，毫无疑问我们可以利用 Manacher 算法。
 * 因为是求以字符串 末尾字符 作为结尾的最长回文子串，所以我们可以利用 Manacher 算法中的
 * 最右回文半径 以及 所对应的回文中心 来确定：末尾最长回文子串。
 * 具体做法为：
 *  首先对字符串进行 Manacher 算法，当 最右回文半径 扩到字符串末尾的时候停止，
 *  然后根据此时的 最右回文半径 与 回文中心 计算出 末尾最长回文子串。
 *      注：为什么此时就是 包含末尾字符的 最长回文子串呢？
 *      因为根据 Manacher 中 回文中心 的定义：它与其相对应的 最右回文边界 最先到达的地方。所以不存在比 [C...R] 更长的回文半径了。
 *  然后我们将 除去回文子串 部分的数组进行逆序，添加在原字符串之后，就能够得到答案了。
 *  代码编辑基本与 Manacher 算法模板 无异，只是添加了一个条件判断罢了。
 *
 * 时间复杂度与使用 KMP 算法相同，但是这道题目我还是更倾向于使用 KMP 来进行解答。
 * 因为 Manacher 在解决它的相似问题 Shortest Palindrome 时，就不太好用了。
 *
 * 对于 Manacher 算法不太了解的可以参考：
 *  Manacher Template:
 * https://github.com/cherryljr/LintCode/blob/master/Manacher%20Template.java
 */
public class Palindrome {
    public String addToPalindrome(String str, int n) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] charArr = getMancherString(str);     // 处理后获得的Manacher字符数组 （添加了'#'）
        int[] radiusArr = new int[charArr.length];  // 回文半径数组
        int center = -1;                            // 最右回文边界 所对应的 回文中心
        int pR = -1;                                // 最右回文边界
        int maxContainsEnd = -1;                    // 最右回文边界到达最后一个字符时 所对应的 回文半径大小
        for (int i = 0; i != charArr.length; i++) {
            // 计算当前字符的 回文半径 (4种情况)
            radiusArr[i] = pR > i ? Math.min(pR - i, radiusArr[2 * center - i]) : 1;
            // 尝试以charArr[i]为中心向外扩，如果不匹配就停止
            // 该写法较为简洁，不需要写成 4种if-else 的判断情况
            while (i + radiusArr[i] < charArr.length && i - radiusArr[i] > -1) {
                if (charArr[i + radiusArr[i]] == charArr[i - radiusArr[i]])
                    radiusArr[i]++;
                else {
                    break;
                }
            }
            // 当 i+radiusArr[i] 大于 最右回文边界 时，更新 pR，同时还需要更新其所对应的 回文中心.
            // 即 最右回文边界 向右扩，并更新 对称点 位置
            if (i + radiusArr[i] > pR) {
                pR = i + radiusArr[i];
                center = i;
            }
            // 当 最右回文边界 到达字符串末尾的时候，停止
            if (pR == charArr.length) {
                maxContainsEnd = radiusArr[i];
                break;
            }
        }

        // 去除 最长回文子串(末尾字符) 后剩余字符串的逆序
        // 注意边界问题！
        char[] rst = new char[str.length() - maxContainsEnd + 1];
        for (int i = 0; i < rst.length; i++) {
            rst[rst.length - 1 - i] = charArr[i * 2 + 1];
        }
        return String.valueOf(rst);
    }

    public char[] getMancherString(String str) {
        char[] charArr = str.toCharArray();
        char[] rst = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != rst.length; i++) {
            rst[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return rst;
    }

}
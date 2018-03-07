/*
题目描述：
给定一个字符串s，请计算输出含有连续两个s作为子串的最短字符串。
注意两个s可能有重叠部分。例如，”ababa”含有两个aba。

输入描述：
输入包括一个字符串s，字符串长度length（1<length1<50），s中每个字符都是小写字母。

输出描述：
输出一个字符串，即含有连续两个s作为子串的最短字符串。

输入：
abracadabra

输出：
abracadabracadabra
 */

/**
 * Approach: KMP
 * 题目中说是包含 两个s 作为子串，而且可以有重叠部分。
 * 所以说，如果没有重叠的部分，最后输出的便是最长的字符串，即两个s拼接在一起。
 * 如果有重叠部分，我们只需要添加除去 s的前n位 和s 的后n位 相同的部分即可。
 * 即最短的添加部分为：
 *      s字符串 - s的最长相同前后缀。
 *
 * 那么我们如何求得 最长相同前后缀呢？
 * 这毫无以为就是 KMP 算法中 next 数组的求法了。
 * 因此我们可以利用 KMP 中的 getNextArray 方法来解决这道题目，求出 终止位置 的最长前后缀。
 * 然后补上 s.subString(endNext) 就能得到答案了。
 *
 * KMP Template:
 *
 */
public class Solution {

    public static String shortestHaveTwice(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        // 如果只有一个字符，加上其本身就可以
        char[] arr = str.toCharArray();
        if (arr.length == 1) {
            return str + str;
        }
        // 如果有两个字符，判断第一个字符和第二个字符是否相等，如果相等添加第一个字符即可
        // 否则加上其本身
        if (arr.length == 2) {
            return arr[0] == arr[1] ? (str + String.valueOf(arr[0])) : (str + str);
        }

        // 计算 终止位置 的最长相同前后缀(next值)
        int endNext = endNextLength(arr);
        // 补上 s 除去 相同前后缀的部分
        return str + str.substring(endNext);
    }

    public static int endNextLength(char[] arr) {
        int[] next = new int[arr.length + 1];
        // 根据定义初始化next数组，0位置为-1，1位置为0.
        next[0] = -1;
        // next[1] = 0;
        int pos = 2;    // 当前位置
        int cn = 0;     // 当前位置前一个字符的 next[] 值(最长相等前后缀的长度)
        while (pos < next.length) {
            if (arr[pos - 1] == arr[cn]) {
                // 当字符串的 pos-1 位置与 pos-1 位置字符所对应的最长相同前后缀的下一个字符 arr[next[pos-1]] 相等时
                // 我们就能确定 next[pos] 的值为 pos-1 位置所对应的 next[pos-1] + 1,即 ++cn.
                next[pos++] = ++cn;
            } else if (cn > 0) {
                // 当着两个字符 不相等 时，cn向前跳跃到 next[cn] 的位置，去寻找长度更短的相同前后缀。
                cn = next[cn];
            } else {
                // cn<=0; 此时说明前面已经没有相同前后缀了，即 cn 已经没办法再跳跃了，
                // 此时 pos 对应的 next[pos] 值为 0 （无相同前后缀）
                next[pos++] = 0;
            }
        }

        return next[next.length - 1];
    }

    public static void main(String[] args) {
        String test1 = "a";
        System.out.println(shortestHaveTwice(test1));

        String test2 = "aa";
        System.out.println(shortestHaveTwice(test2));

        String test3 = "ab";
        System.out.println(shortestHaveTwice(test3));

        String test4 = "abcdabcd";
        System.out.println(shortestHaveTwice(test4));

        String test5 = "abracadabra";
        System.out.println(shortestHaveTwice(test5));

    }

}




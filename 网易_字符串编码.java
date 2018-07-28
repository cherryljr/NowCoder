/*
时间限制：1秒
空间限制：32768K

给定一个字符串，请你将字符串重新编码，将连续的字符替换成“连续出现的个数+字符”。
比如字符串AAAABCCDAA会被编码成4A1B2C1D2A。

输入描述:
每个测试输入包含1个测试用例
每个测试用例输入只有一行字符串，字符串只包括大写英文字母，长度不超过10000。

输出描述:
输出编码后的字符串

输入例子1:
AAAABCCDAA

输出例子1:
4A1B2C1D2A
 */


/**
 * Approach: Traverse
 * 热身题...遍历往后走就行
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.next();
            StringBuilder sb = new StringBuilder();
            int index = 0;
            while (index < str.length()) {
                int count = 1;
                while (index < str.length() - 1 && str.charAt(index) == str.charAt(index + 1)) {
                    count++;
                    index++;
                }
                sb.append(count).append(str.charAt(index++));
            }
            System.out.println(sb.toString());
        }
    }
}
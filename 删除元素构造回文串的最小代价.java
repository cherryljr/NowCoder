/*
给定一个字符串s，你可以从中删除一些字符，使得剩下的串是一个回文串。如何删除才能使得回文串最长呢？
输出需要删除的字符个数。

输入描述:
输入数据有多组，每组包含一个字符串s，且保证:1<=s.length<=1000.
  
输出描述:
对于每组数据，输出一个整数，代表最少需要删除的字符个数。

示例1
输入
abcda
google

输出
2
2
*/

/**
 * 提到回文串，自然要利用回文串的特点，想到将源字符串逆转后，
 * 如果是“回文串”（不一定连续）的话，那么字符串的顺序相当于没有改变。
 * 求原字符串和其反串的最大公共子序列的长度 LCS（使用动态规划很容易求得），
 * 然后用原字符串的长度减去这个最大公共子串的长度就得到了最小编辑长度。
 */
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.nextLine();
            System.out.println(str.length() - LCS(str));
        }
    }
    
    private static int LCS(String str) {
        StringBuilder sb = new StringBuilder(str);
        String strReverse = sb.reverse().toString();
         
        int[][] dp = new int[str.length() + 1][str.length() + 1];
        dp[0][0] = 0;
         
        for (int i = 1; i <= str.length(); i++) {
            for (int j = 1; j <= str.length(); j++) {
                if (str.charAt(i - 1) == strReverse.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
 
        return dp[str.length()][str.length()];
    }
}

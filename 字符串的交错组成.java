/*
【题目】
给定三个字符串str1、str2和aim，如果aim包含且仅包含来自str1和str2的所有字符，
而且在aim中属于str1的字符之间保持原来在str1中的顺序，属于str2的字符之间保持原来在str2中的顺序，
那么称aim是str1和str2的交错组成。
实现一个函数，判断aim是否是str1和str2交错组成。
【举例】
str1="AB"， str2="12"。 那么"AB12"、 "A1B2"、 "A12B"、 "1A2B"和"1AB2"等都
是str1和str2的交错组成。
 */

/**
 * Approach: DP
 * 很明显的 DP 问题。
 * 当 str1 中的位置 i 与 str2 中的位置 j 确定之后，其结果就已经是确定了的（无后效性）
 * 当前位置的值 dp[i][j] 依赖于 dp[i-1][j] 与 dp[i][j-1]
 * 只需要 i+j-1 之前部分能够匹配并且 str1.charAt(i-1) == aim.charAt(i+j-1)
 * 或者是 str2.charAt(j-1) == aim.charAt(i+j-1)
 * 那么当前状态就能够成立。
 */
public class Main {

    public static boolean isCross(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null) {
            return false;
        }
        if (aim.length() != str1.length() + str2.length()) {
            return false;
        }

        boolean[][] dp = new boolean[str1.length() + 1][str2.length() + 1];
        // Initialize
        dp[0][0] = true;
        for (int i = 1; i <= str1.length(); i++) {
            if (str1.charAt(i - 1) != aim.charAt(i - 1)) {
                break;
            }
            dp[i][0] = true;
        }
        for (int j = 1; j <= str2.length(); j++) {
            if (str2.charAt(j - 1) != aim.charAt(j - 1)) {
                break;
            }
            dp[0][j] = true;
        }

        // Function
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if ((str1.charAt(i - 1) == aim.charAt(i + j - 1) && dp[i - 1][j])
                        || (str2.charAt(j - 1) == aim.charAt(i + j - 1) && dp[i][j - 1])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }

    public static void main(String[] args) {
        String str1 = "1234";
        String str2 = "abcd";
        String aim = "1a23bcd4";
        System.out.println(isCross(str1, str2, aim));
    }

}

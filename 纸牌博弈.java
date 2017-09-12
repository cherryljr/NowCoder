与 LintCode 中的 Coins in a Line III 几乎相同。
传输门：
https://github.com/cherryljr/LintCode/blob/master/Coins%20in%20a%20Line%20III.java
博弈问题，采用动态规划解决。

/*
题目描述
有一个整型数组A，代表数值不同的纸牌排成一条线。
玩家a和玩家b依次拿走每张纸牌，规定玩家a先拿，玩家B后拿，
但是每个玩家每次只能拿走最左或最右的纸牌，玩家a和玩家b都绝顶聪明，他们总会采用最优策略。
请返回最后获胜者的分数。
给定纸牌序列A及序列的大小n，请返回最后分数较高者得分数(相同则返回任意一个分数)。
保证A中的元素均小于等于1000。且A的大小小于等于300。

测试样例：
[1,2,100,4],4

返回：
101
*/

import java.util.*;

public class Cards {
    public int cardGame(int[] A, int n) {
        if (A == null || A.length == 0) {
            return 0;
        }
        
        int len = A.length;
        int[][] sum = new int[len + 1][len + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = i; j <= len; j++) {
                if (i == j) {
                    sum[i][j] = A[j - 1];
                } else {
                    sum[i][j] = sum[i][j - 1] + A[j - 1];   
                }
            }
        }
        
        int[][] dp = new int[len + 1][len + 1];
        for (int i = len; i >= 1; i--) {
            for (int j = i; j <= len; j++) {
                if (i == j) {
                    dp[i][j] = A[j - 1];
                } else {
                    dp[i][j] = sum[i][j] - Math.min(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return Math.max(dp[1][len], sum[1][len] - dp[1][len]);
    }
}
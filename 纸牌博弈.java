�� LintCode �е� Coins in a Line III ������ͬ��
�����ţ�
https://github.com/cherryljr/LintCode/blob/master/Coins%20in%20a%20Line%20III.java
�������⣬���ö�̬�滮�����

/*
��Ŀ����
��һ����������A��������ֵ��ͬ��ֽ���ų�һ���ߡ�
���a�����b��������ÿ��ֽ�ƣ��涨���a���ã����B���ã�
����ÿ�����ÿ��ֻ��������������ҵ�ֽ�ƣ����a�����b�����������������ܻ�������Ų��ԡ�
�뷵������ʤ�ߵķ�����
����ֽ������A�����еĴ�Сn���뷵���������ϸ��ߵ÷���(��ͬ�򷵻�����һ������)��
��֤A�е�Ԫ�ؾ�С�ڵ���1000����A�Ĵ�СС�ڵ���300��

����������
[1,2,100,4],4

���أ�
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
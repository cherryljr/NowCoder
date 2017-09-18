该题与 删除最少的字符构成回文数组（实质为 LCS 问题） 类似。
https://www.nowcoder.com/questionTerminal/28c1dc06bc9b4afd957b01acdf046e69
不同的是该题为插入，我们仍然可以通过 DP 来解决该问题。

State:
    dp[i][j] 表示 i~j 段需要插入数值的最小和。
Function:
    当 nums[i] == nums[j] 时，本身即为回文数组，无需插入新数字。
    dp[i][j] = dp[i+1][j-1];
    当 nums[i] != nums[j] 时，插入二者中的最小值，构成回文数组。
    dp[i][j] = Math.min(dp[i+1][j] + nums[i-1], dp[i][j-1] + nums[j-1]);
Answer:
    原数组之和 sum + 插入数字之和的最小值 dp[1][n] 

/*
题目描述：
对于一个给定的正整数组成的数组a[], 如果将a倒序后数字的排列与a完全相同，
我们称这个数组为“回文”的。
例如：[1, 2, 3, 2, 1]的倒叙就是它自己，所以是一个回文数组；
而[1, 2, 3, 1, 2]的倒序是[2, 1, 3, 2, 1],所以不是一个回文的数组。
对于一个给定的正整数组成的数组，如果我们向其中某些特定的位置插入一些正整数，
那么我们总能构造出一个回文的数组。

输入一个正整数组成的数组，要求你插入一些数字，使其变成回文的数组。
且数组中所有数字的和尽可能小。输出这个插入后数组中元素的和。

输入描述：
输入数据由两行组成：第一行包含一个正整数 L,表示数组 a 的长度。
第二行包含 L 个正整数，表述数组a。

输出描述：
输出一个整数，表示通过插入若干个正整数，使得数组 a 回文后，
数组 a 的数字和的最小值。
*/

import java.util.*;

public class Main {
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    int n = sc.nextInt();
	    int[] nums = new int[n];
	    for (int i = 0; i < n; i++) {
	        nums[i] = sc.nextInt();
	    } 
	    sc.close();
	    System.out.println(minInsert(nums, n));
	}
	
    public static int minInsert(int[] nums, int n) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        sum += helper(nums, n);
        
        return sum;
    }
    
    public static int helper(int[] nums, int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j <= n; j++) {
                if (nums[i - 1] == nums[j - 1]) {
                    dp[i][j] = dp[i+1][j-1];
                } else {
                    dp[i][j] = Math.min(dp[i+1][j] + nums[i-1], dp[i][j-1] + nums[j-1]);
                }
            }
        }
        
        return dp[1][n];
    }
}


  2017 年京东研发类编程考题答案。
思路：
	第一步就是要把数字 n 每一位上的数拆分出来以方便我们的计算。
	第二步，分成两个部分使得和相等这就意味着，
	我们需要拆分得到的数组 int[] num中找到两个 Target.使得 Target1 = Target2.
	第三步，说到这里 背包问题 的实质已经暴露出来了。因此我们使用 DP 来解决该问题。
	而问题的实质就是：在一个数组能否中找到一个和为 sum / 2 的 subarray.
	类似的问题可以常见：LintCode 的 Backpack VI 问题
	解答也可以在我的 github 中找到： https://github.com/cherryljr/LintCode/blob/master/Backpack%20VI.java
	
/*
题目描述：神奇数（仅凭记忆描述，原题放出后会补上）
神奇数的定义是，将一个数的各个位上的数取出来。分成两个部分。
当这两个部分的书之和相等时，则意味着该数是一个神奇数。
比如：123,可以分成 {1, 2} 和 {3}. 1+2=3.故 123 是神奇数
要求：
输入两个数 l 和 r。要求能够找出区间 [l, r] 中所有的神奇数的输出。

测试案例：
1 50
输出解果：
4
*/

import java.util.*;
import java.io.*;

public class LintCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt(); // 输入 l
        int r = sc.nextInt(); // 输入 r
        int count = 0;				// 计算有几个数
        
        // 遍历 l ~ r 闭区间
        for (int i = l; i <= r; i++) {
        	// 将数字 i 转换为 int[] 
            String s = String.valueOf(i);
            char[] ch = s.toCharArray();
            int[] nums = new int[ch.length];
            for (int j = 0; j < nums.length; j++) {
                nums[j] = ch[j] - '0';
            }
            // 判断是否为神奇数字
            if (isMagicalNumber(nums)) {
                count++;
            }
        }
        System.out.println(count);
    }
    
    // 判断函数
    public static boolean isMagicalNumber(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += nums[i];
        }
        if (sum % 2 == 1) {
            return false;
        }
        sum /= 2;
        
        // State & Initialize
        boolean[] dp = new boolean[sum + 1];
        for (int i = 0; i <= sum; i++) {
            dp[i] = false;
        }
        dp[0] = true;
        
        // Function
        for (int i = 0; i < len; i++) {
            for (int j = sum; j >= nums[i]; j--) {
                dp[j] |= dp[j - nums[i]];
            }
        }
        
        // Answer
        return dp[sum];
    }
}


  2017 �꾩���з����̿���𰸡�
˼·��
	��һ������Ҫ������ n ÿһλ�ϵ�����ֳ����Է������ǵļ��㡣
	�ڶ������ֳ���������ʹ�ú���������ζ�ţ�
	������Ҫ��ֵõ������� int[] num���ҵ����� Target.ʹ�� Target1 = Target2.
	��������˵������ �������� ��ʵ���Ѿ���¶�����ˡ��������ʹ�� DP ����������⡣
	�������ʵ�ʾ��ǣ���һ�������ܷ����ҵ�һ����Ϊ sum / 2 �� subarray.
	���Ƶ�������Գ�����LintCode �� Backpack VI ����
	���Ҳ�������ҵ� github ���ҵ��� https://github.com/cherryljr/LintCode/blob/master/Backpack%20VI.java
	
/*
��Ŀ����������������ƾ����������ԭ��ų���Ჹ�ϣ�
�������Ķ����ǣ���һ�����ĸ���λ�ϵ���ȡ�������ֳ��������֡�
�����������ֵ���֮�����ʱ������ζ�Ÿ�����һ����������
���磺123,���Էֳ� {1, 2} �� {3}. 1+2=3.�� 123 ��������
Ҫ��
���������� l �� r��Ҫ���ܹ��ҳ����� [l, r] �����е��������������

���԰�����
1 50
��������
4
*/

import java.util.*;
import java.io.*;

public class LintCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt(); // ���� l
        int r = sc.nextInt(); // ���� r
        int count = 0;				// �����м�����
        
        // ���� l ~ r ������
        for (int i = l; i <= r; i++) {
        	// ������ i ת��Ϊ int[] 
            String s = String.valueOf(i);
            char[] ch = s.toCharArray();
            int[] nums = new int[ch.length];
            for (int j = 0; j < nums.length; j++) {
                nums[j] = ch[j] - '0';
            }
            // �ж��Ƿ�Ϊ��������
            if (isMagicalNumber(nums)) {
                count++;
            }
        }
        System.out.println(count);
    }
    
    // �жϺ���
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
        boolean[] dp = new boolean[sum];
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


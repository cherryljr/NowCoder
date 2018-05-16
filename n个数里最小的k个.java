/*
题目描述
找出n个数里最小的k个

输入描述:
每个测试输入包含空格分割的n+1个整数，最后一个整数为k值,n
不超过100。
输出描述:
输出n个整数里最小的k个数。升序输出

示例1
输入
3 9 6 8 -10 7 -11 19 30 12 23 5
输出
-11 -10 3 6 7
 */

/**
 * Approach: QuickSelect | Partition
 * 经典的 TopK 问题，使用 快排Partition 的思想即可解决。
 * 
 * 类似的问题有：
 *  https://github.com/cherryljr/LintCode/blob/master/Kth%20Largest%20Element.java
 * 对于 Partition 过程不了解的可以参考：
 *  https://github.com/cherryljr/LintCode/blob/master/Sort%20Colors.java
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = bf.readLine().split(" ");
        int[] nums = new int[strings.length - 1];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.valueOf(strings[i]);
        }
        int k = Integer.valueOf(strings[strings.length - 1]);

        helper(nums, 0, nums.length - 1, k);
        int[] rst = new int[k];
        for (int i = 0; i < k; i++) {
            rst[i] = nums[i];
        }
        Arrays.sort(rst);
        for (int i = 0; i < k - 1; i++) {
            System.out.print(rst[i] + " ");
        }
        System.out.println(rst[k - 1]);
    }

    private static void helper(int[] nums, int left, int right, int k) {
        if (left == right) {
            return;
        }

        int position = partition(nums, left, right);
        if (position == k) {
            return;
        } else if (position > k){
            helper(nums, left, position - 1, k);
        } else {
            helper(nums, position + 1, right, k);
        }
    }

    private static int partition(int[] nums, int left, int right) {
        int less = left - 1, more = right;
        while (left < more) {
            if (nums[left] < nums[right]) {
                swap(nums, ++less, left++);
            } else if (nums[left] > nums[right]) {
                swap(nums, --more, left);
            } else {
                left++;
            }
        }
        swap(nums, more,right);
        return less + 1;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
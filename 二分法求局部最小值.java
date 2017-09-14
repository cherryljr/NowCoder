与 LeetCode 中的 Find Peak Element 解法相同。
该题只是将 局部最大值 换成了求 局部最小值。
Find Peak Element: https://leetcode.com/problems/find-peak-element/discuss/

该道题目给我们最大的启发就是：
	不一定要有序才能进行二分操作。
	只要是存在排他性，它就能够进行二分操作。
	1. 一边肯定有，另一边可能有；
	2. 一边可能有，另一边绝对没有。

/*
给定一个无序数组 nums[]，相邻数字不同。返回任意一个局部最小值。
局部最小值的定义为：
	1. 对于 第一个元素 而言，只要它比 第二个元素 要小，则就是局部最小值。
	同理，对于 最后一个元素 而言，只要它比 倒数第二个元素 要小，则就是局部最小值。
	2. 对于数据中间的元素，如果一个数相邻的两个数都比他大，那么他就是局部最小值。
*/

import java.util.*;

public class Main {
	public static void main(String[] args) {
		int[] A = {19, 11, 3, 8, 10};
		System.out.println(BinarySearch(A));
	}
	
	private static int BinarySearch(int[] nums) {
        // 规定当数组中只有一个数时，不存在局部最小值
		if (nums == null || nums.length <= 1) {
			return 0;
		}
		
		int len = nums.length;
		if (nums[0] < nums[1]) {
			return nums[0];
		}
		if (nums[len - 1] < nums[len - 2]) {
			return nums[len - 1];
		}
		
		int start = 0;
		int end = len - 1;
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (nums[mid] > nums[mid + 1]) {
				start = mid + 1;
			} else {
				end = mid;
			}
		}
		
		return (nums[start] < nums[start + 1]) ? nums[start] : nums[end];
	}
}
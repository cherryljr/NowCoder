该题在左神课程的 2.16排序(4)案例6 中提过.
值得学习的一种方法

/*
给定一个无序数组arr。如果想要让所有元素从小到大排列，求出需要排序的最短子数组长度。 
例如： arr = {1，5，3，4，2，6，7} 返回4，因为只有{5，3，4，2}需要排序。
注：本题请尽量做到时间复杂度O(N)，额外空间复杂度O(1)
*/

public class Solution {
	/**
	 *	求需要排序的最短子数组长度
	 *	输入：数组arr
	 *	返回：需要排序的最短子数组长度
	 */
	public int getMinSortLength(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        // 假定最大值,最小值
        int max = arr[0];
        int min = arr[arr.length - 1];
        // right 和 left 之间的范围便是需要排序的最短子数组(一开始假定全部范围)
        // left 代表从左向右遍历的index; right 代表从右向左遍历的index.
        int left = 0, right = arr.length - 1;
        /**
         * 从右向左遍历，找出需要排序数的最右范围
         * (遍历过部分的最大值大于当前正在遍历的值,那么当前值就是invalid,那么真实排序之后,这个最大值在当前位置,或者是更右的位置)
         * 只记录发生这种情况的最右位置
         */
        for(int i = 1; i < arr.length; i++){
            if(max > arr[i]) {
                left = i;
            }
            else {
                max = arr[i];
            }
        }        
        
        /**
         * 从左向右遍历，找出需要排序数的最左范围
         * (遍历过部分的最小值小于当前正在遍历的值,那么当前值就是invalid,那么真实排序之后,这个最小值在当前位置,或者是更左的位置)
         * 只记录发生这种情况的最左位置
         */
        for(int i = arr.length-2; i > -1; i--){
            if(min < arr[i]) {
                right = i;
            }
            else {
                min = arr[i];
            }
        } 

        if(left == 0 && right == arr.length - 1){
            return 0;   // 证明原数组是有序的
        }
        return left - right + 1;
	}
}

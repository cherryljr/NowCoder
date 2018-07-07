/*
题目描述
输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，
并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */

/**
 * Approach 1: Traverse
 * 本题要求各个数之间的相对位置不能发生变换，这就以为这这是一个稳定的 Partition 操作。
 * 因此不能够使用 QuickSelect.
 * 对此最简单暴力的方法就是：
 *  新建一个数组空间，然后进行扫描分类即可。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 */
public class Solution {
    public void reOrderArray(int [] array) {
        int[] newArray = new int[array.length];
        int oddPointer = 0, oddCount = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                oddCount++;
            }
        }

        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                newArray[oddPointer++] = array[i];
            } else {
                newArray[oddCount++] = array[i];
            }
        }
        System.arraycopy(newArray, 0, array, 0, array.length);
    }
}

/**
 * Approach 2: Bubble Sort
 * 如果要求空间复杂度为 O(1) 的话，那么我们只能使用稳定排序的实现了。
 * 这里使用了 冒泡排序 的思想。如果前偶数后奇数就发生一次交换。
 * 这样就能够使得所有奇数在左，所有偶数在右。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 */
public class Solution {
    public void reOrderArray(int [] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int start = 0; start < end; start++) {
                if ((array[start] & 1) == 0 && (array[start + 1] & 1) == 1) {
                    int temp = array[start];
                    array[start] = array[start + 1];
                    array[start + 1] = temp;
                }
            }
        }
    }
}
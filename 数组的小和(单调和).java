/*
数组小和的定义如下：例如，数组s=[1,3,5,2,4,6]
在s[0]的左边小于或等于s[0]的数的和为0
在s[1]的左边小于或等于s[1]的数的和为1
在s[2]的左边小于或等于s[2]的数的和为1+3=4
在s[3]的左边小于或等于s[3]的数的和为1
在s[4]的左边小于或等于s[4]的数的和为1+3+2=6
在s[5]的左边小于或等于s[5]的数的和为1+3+5+2+4=15
所以s的小和为0+1+4+1+6+15=27
给定一个数组s，实现函数返回s的小和。
 */

/**
 * Approach: Merge Sort
 * 该题与 Reverse Pairs 基本相同。都是利用到了 Merge Sort 的 merge 过程来计算。
 * Reverse Pairs:
 * 经过分析，我们可以发现相比与 MergeSort Template，该题的代码基本只是添加了一句
 * 用于计算 最小和 的代码而已，其余部分均相同。
 * 这边为了验证代码的正确性使用了 对数比较器。
 *
 * 牛客网上面也有道相同的题目，大家也可以去OJ上试一下：
 * https://www.nowcoder.com/questionTerminal/8397609ba7054da382c4599d42e494f3
 * (牛客网上面的定义有一点点的不同，只需要把 小于 改成 小于等于 即可)
 */
public class Solution {

    public static int smallSum(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static int mergeSort(int[] nums, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        // 结果为左半段数组中的小和 + 右半段数组中的小和 + 左边段数组中 与 右半段数组中 的元素组成的小和
        return mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right) + merge(nums, left, mid, right);
    }

    public static int merge(int[] nums, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        int rst = 0;
        while (p1 <= mid && p2 <= right) {
            // 当 nums[p1] < nums[p2] 说明能够形成一个最小和
            // 又因为左右两部分是已经排序好了的，所以 nums[p2...right] 均能够与 nums[p1] 形成最小和
            rst += nums[p1] < nums[p2] ? (right - p2 + 1) * nums[p1] : 0;
            help[i++] = nums[p1] < nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= mid) {
            help[i++] = nums[p1++];
        }
        while (p2 <= right) {
            help[i++] = nums[p2++];
        }

        for (i = 0; i < help.length; i++) {
            nums[left + i] = help[i];
        }
        return rst;
    }

    // for test
    public static int comparator(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int rst = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                rst += nums[j] < nums[i] ? nums[j] : 0;
            }
        }
        return rst;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] nums) {
        if (nums == null) {
            return null;
        }
        int[] rst = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            rst[i] = nums[i];
        }
        return rst;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] nums) {
        if (nums == null) {
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fuck You!");
    }

}
/*
【题目】
给定一个整数数组arr，和一个整数target， 求累加和小于等于target的最长子数组.
要求时间复杂度O(N)

【输入】
 [8,7,3,4,1,1,1,1,1,2,9] 7
【输出】
 6
 */

/**
 * Approach: Sliding Window
 * 这道题目实际上是具有相当难度的。
 * 主要是因为：给定的是整数数组，这就意味着数组中可能含有 负数，0，正数。
 * 这就使得 Two Pointers 无法使用（或者说是目前看来是无法使用的），因为其不具备单调性啊。
 * 我们不能确定移除当前元素后，sum 值一定会减少。
 * 如果该题题目中给定的全部都是 非负数，求最短 subarray 的话（全是正数求最长就没意义了），那么我们就可以使用这个方法：
 * https://github.com/cherryljr/LintCode/blob/master/Minimum%20Size%20Subarray%20Sum.java
 *
 * 那我们考虑下使用 PreSum + HashMap 的方法呢？
 * 同样也不行，因为 HashMap 只能一次判断一个值，而本题中需要判断的是一个范围，
 * 那么我们只能加上一个 for 循环来检测范围内的数，但是这样时间复杂度就不符合要求了。
 * 如果题目中要求的是 等于k 的话，那么我们就可以使用这个方法：
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Size%20Subarray%20Sum%20Equals%20k.java
 *
 * 这就是本题的难点所在，具体解决方法请看代码与注释。
 * （注释写得很详细了，这边就不多说什么了...）
 */
public class Main {

    public static int maxLengthBest(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // sums[i] 表示以 arr[i] 作为开头的所有子数组的 最小累加和
        int[] sums = new int[arr.length];
        // ends[i] 表示以 arr[i] 作为开头的最小累加和子数组的 右边界
        int[] ends = new int[arr.length];
        // 从右向左递推计算
        // 首先初始化 arr.length-1 位置
        sums[arr.length - 1] = arr[arr.length - 1];
        ends[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            // 当 ith 后面一个位置的 sum[i+1] < 0,
            // 说明以 ith 开头的子数组的最小累加和可以往后面扩
            // 否则就是其本身位置
            if (sums[i + 1] < 0) {
                sums[i] = arr[i] + sums[i + 1];
                ends[i] = ends[i + 1];
            } else {
                sums[i] = arr[i];
                ends[i] = i;
            }
        }

        int end = 0;    // 滑动窗口的右边界（不包括边界值）
        int sum = 0;    // 窗口内的累加和
        int rst = 0;
        // 自左向右开始遍历，i 为左边界
        for (int i = 0; i < arr.length; i++) {
            // 当以 i 作为开头的最小累加和 + 下一段最小累加和 不超过 target 的话
            // 说明 满足条件的子数组 可以继续先 右 扩。一直扩到不满足条件位置。
            // （以上操作就是利用了实现建立好的 sums[] 和 ends[] 实现的）
            while (end < arr.length && sum + sums[end] <= target) {
                sum += sums[end];
                end = ends[end] + 1;
            }
            // 更新 累加和 <=target 子数组的 最长长度
            rst = Math.max(rst, end - i);
            // 查看 end 位置，如果右边界一开始就没扩动，那么 sum 保持不变（因为根本就没有元素加进来）
            // 比如 {8, 3, 4}; target =7.当 i==0时，end根本无法向右移动。
            // 如果 end 扩动了，那么我们需要从窗口内将左边界上的数移除出去（滑动窗口）
            // 这样才能使得 end 有机会继续向右扩。说白了也就是枚举下一个 ith+1 开头的子数组情况。
            sum -= end > i ? arr[i] : 0;
            // 同样是为了处理 end 没扩动的情况。当 end 无法向右扩时，直接向右移动一位。
            end = Math.max(end, i + 1);
        }

        return rst;
    }

    /**
     * O(nlogn)的解法，了解即可
     */
    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 2000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLengthBest(arr, k) != maxLength(arr, k)) {
                System.out.println("Fucking Fucked!");
            }
        }

    }

}

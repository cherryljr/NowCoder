/*
给定一个数组序列, 需要求选出一个区间, 使得该区间是所有区间中经过如下计算的值最大的一个：
区间中的最小数 * 区间所有数的和最后程序输出经过计算后的最大值即可，不需要输出具体的区间。
如给定序列  [6 2 1]则根据上述公式, 可得到所有可以选定各个区间的计算值:
    [6] = 6 * 6 = 36;
    [2] = 2 * 2 = 4;
    [1] = 1 * 1 = 1;
    [6,2] = 2 * 8 = 16;
    [2,1] = 1 * 3 = 3;
    [6, 2, 1] = 1 * 9 = 9;
从上述计算可见选定区间 [6] ，计算值为 36， 则程序输出为 36。
区间内的所有数字都在[0, 100]的范围内;

输入描述:
第一行输入数组序列长度n，第二行输入数组序列。
对于 50%的数据,  1 <= n <= 10000;
对于 100%的数据, 1 <= n <= 500000;

输出描述:
输出数组经过计算后的最大值。
示例1
输入
3
6 2 1
输出
36
 */
 
/**
 * Approach 1: Brute Force
 * 直接对数组进行大小为[i, j]的窗口遍历。
 * 同时计算区间和 sum; 更新区间最小值 min; 最后由 sum * min 得到结果。
 * 看是否比 max 大，如果更大，更新 max 即可。
 * 属于相当暴力直观的方法。
 * 时间复杂度为：O(n^2)
 */
import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int size = sc.nextInt();
        int[] input = new int[size];
        for (int i = 0; i < input.length; i++) {
            input[i] = sc.nextInt();
        }

        long max = input[0] * input[0];
        for (int i = 0; i < size; i++) {
            long sum = input[i];
            int min = input[i];
            for (int j = i+1; j < size; j++) {
                // 如果值为 0 直接退出即可
                if (input[j] == 0) {
                    break;
                }
                sum += input[j];
                if (input[j] < min) {
                    min = input[j];
                }
                if (min * sum > max) {
                    max = min * sum;
                }
            }
        }

        System.out.println(max);
    }
}

/**
 * Approach 2: 利用单调栈寻找左右边界
 * 把每个数字都看作是当前区间内的最小值，那么只要区间和的值越大，结果值就越大。
 *
 * 因此我们可以利用（递增）单调栈得到每个元素的左边界和右边界
 * （边界的定义即为 左/右边 第一个比该元素更小的值），最后用每个元素乘以每个元素对应的区间和，找出最大值即可。
 *
 * 为了防止每个元素重复计算一段区间和，可以提前计算一个前缀和数组，用于保存某元素之前的各项和（不含该元素），
 * 求取一段区间和的时候用右边界的前缀和减去左边界的前缀和即可。
 * 
 * 这个方法的优点在于只需要遍历数组一次，大大降低了时间复杂度.
 * 时间复杂度为：O(2n). 各个元素进出栈各一次。
 */
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = sc.nextInt();
        }

        System.out.println(getMaxIntervalSum(nums));
    }

    public static long getMaxIntervalSum(int[] nums) {
        long max = 0;
        long[] preSum = new long[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= nums.length; i++) {
            int curr = i == nums.length ? -1 : nums[i];
            while (!stack.isEmpty() && curr < nums[stack.peek()]) {
                int num = nums[stack.pop()];
                if (stack.isEmpty()) {
                    max = Math.max(max, preSum[i] * num);
                } else {
                    max = Math.max(max, (preSum[i] - preSum[stack.peek() + 1]) * num);
                }
            }
            stack.push(i);
        }

        return max;
    }
}




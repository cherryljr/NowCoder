/*
给定一个数组，数组中只包含0和1。请找到一个最长的子序列，其中0和1的数量是相同的。
例1：10101010     结果就是其本身。
例2：1101000       结果是110100
*/

/**
 * Approach: Prefix Sum + HashMap
 * 首先我们要对原来的数组进行变换一下。原来是 0 和 1 的串，我们将 0 都换为-1。
 * 则我们要找的子串是 最长的和为0 的子串。
 * 这种子串求和的问题，一般采用前缀和的方法来解决。
 * 用 preSum[i]代表 前i个 数的和，问题的模型转换为:
 * 找到i和j，满足 preSum[i] 与 preSum[j]相等，且|i-j|最大。
 * 然后使用 HashMap 作为辅助数据结构，map中的 key 为 preSum 的值，value 为 preSum[i] 第一次出现的位置。
 * 从左到右遍历 preSum[]，看在 map 中查找是否存在，
 * 如果存在，则记录下 map.get(preSum[i]) 和 i 的距离差，
 * 否则说明该值第一次出现，将其 put 到 map 中即可。
 * 一次遍历结束后得到最大的距离差，同时也可以得到具体是哪一段。
 *
 * 类似的题目还有：“和最大的子数组”，和为0的子数组”，“和最接近0的子数组”。
 * 其通用的解决方法都是通过 前缀和 来解决。
 * https://github.com/cherryljr/LintCode/blob/master/Maximum%20Subarray.java
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(maxSubString("1101100"));
    }

    public static String maxSubString(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        int len = str.length();
        int[] preSum = new int[len + 1];
        char[] arr = str.toCharArray();
        // Get the Prefix Sum
        for (int i = 1; i <= len; i++) {
            preSum[i] = arr[i - 1] == '1' ? 1 : -1;
            preSum[i] += preSum[i - 1];
        }

        Map<Integer, Integer> map = new HashMap<>();
        int start = 0, maxLen = 0;
        // Using HashMap to get the maxLen
        for (int i = 0; i <= len; i++) {
            if (!map.containsKey(preSum[i])) {
                map.put(preSum[i], i);
                continue;
            }
            if (i - map.get(preSum[i]) > maxLen) {
                maxLen = i - map.get(preSum[i]);
                start = map.get(preSum[i]);
            }
        }

        return str.substring(start, start + maxLen);
    }
}

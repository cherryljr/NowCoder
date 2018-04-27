/*
描述：
给定数组arr和整数num，共返回有多少个子数组满足如下情况：
    max(arr[i..j]) - min(arr[i..j]) <= num
max(arr[i..j])表示子数组 arr[i..j] 的最大值;
min(arr[i..j])表示子数组 arr[i..j] 中的最小值.

要求：
如果数组长度为 N,请实现时间复杂度为 O(N)的解法。
 */

/**
 * Approach: Deque
 * 如果采用暴力解法，我们需要遍历 所有大小 的窗口，然后再遍历该子数组是否符合条件。
 * 总时间复杂度为 O(n^3)
 *
 * 但是这道题目可以利用 双端队列 进行优化。
 * 对于一个子数组，设其最大值为 max, 最小值为 min.
 * 如果 符合条件(max-min <= num) 那么对于子该数组内所有的子数组，必定有 max'<=max, min'>=min.
 * 也就是其子数组必定符合条件。因此通过该子数组我们就能够推得：
 *      所有以 arr[left] 开头的能够组成的达标子数组总共有 right-left 个。
 * 如果 不符合条件(max-min <= num) 那么该子数组继续向右扩的话，必定不达标，因为这样只会导致
 * max 更大，或者 min 更小。
 *
 * 由上述分析可得：
 *  我们需要求得一个子数组(窗口)内的 最大值 和 最小值。
 *  因此我们可以借助两个 双端队列 来对时间复杂度进行优化。
 *  实现方法同 Sliding Window Maximum：
 *  https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Maximum.java
 *  具体实现：
 *  利用 left, right 表示窗口的左右边界
 *  一开始 right 向右扩，直到窗口内的子数组不达标为止。
 *  此时所有窗口内所有以 arr[left] 开头的能够组成的达标子数组总共有 right-left 个，则 rst += (right - left)
 *  然后因为 继续向右扩的话，窗口内数组无法达标，因此 左边界 向右移动（换一个开头）。
 *  这样因为 窗口中的元素被移除了一个，有可能导致窗口能够达标，因此继续以上操作，直到 左边界 到达数组末尾。
 *
 * 时间复杂度分析：
 *  左边界 与 右边界 一直向右移动，不会后退，因此时间复杂度为 O(n)
 */
public class Main {

    public static int getNum(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        Deque<Integer> qMin = new LinkedList<>();   // 采用双端队列来维持窗口的内的 最小值
        Deque<Integer> qMax = new LinkedList<>();   // 采用双端队列来维持窗口的内的 最大值
        int left = 0;                               // 窗口左边界
        int right = 0;                              // 窗口右边界
        int rst = 0;
        while (left < arr.length) {
            // 左边界不动，右边界向右扩到不能继续扩时停止（子数组无法达标）
            while (right < arr.length) {
                // 当 arr[right] 大于等于 最大值双端队列尾部的值 且 队列不为空 时，将尾部元素弹出
                while (!qMax.isEmpty() && arr[qMax.getLast()] <= arr[right]) {
                    qMax.removeLast();
                }
                // 将该元素加入 最大双端队列的末尾
                qMax.addLast(right);
                // 当 arr[right] 小于等于 最小值双端队列尾部的值 且 队列不为空 时，将尾部元素弹出
                while (!qMin.isEmpty() && arr[qMin.getLast()] >= arr[right]) {
                    qMin.removeLast();
                }
                // 将该元素加入 最小双端队列的末尾
                qMin.addLast(right);
                // 当窗口中 最大值 与 最小值 的差值大于 num 时，right 停止移动
                if (arr[qMax.getFirst()] - arr[qMin.getFirst()] > num) {
                    break;
                }
                right++;
            }

            // 当窗口 左边界值 过期时，将队列的 头部元素弹出
            if (qMax.getFirst() == left) {
                qMax.removeFirst();
            }
            if (qMin.getFirst() == left) {
                qMin.removeFirst();
            }
            // 窗口内的 子数组 均符合条件
            rst += right - left;
            // 左边界 向右移动，换一个开头，看是否能够使得窗口内的数组达标
            left++;
        }

        return rst;
    }

    public static void main(String[] args) {
        int[] arr = getRandomArray(30);
        int num = 5;
        printArray(arr);
        System.out.println(getNum(arr, num));
    }

    //for test
    public static int[] getRandomArray(int len) {
        if (len < 0) {
            return null;
        }

        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 10);
        }
        return arr;
    }

    //for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

}

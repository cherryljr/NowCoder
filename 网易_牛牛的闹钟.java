/**
 * Approach: Binary Search
 * 把时间都转换为分钟计数
 * 最晚起床时间 = 上课时间 - 路上时间
 * 把所有闹钟时间排序后，二分查找最晚起床时间即可。
 *
 * 对于二分查找，这里我们可以自己写，或者使用 Java 自带的 binarySearch() 函数。
 * 自己实现的 binarySearchUpperBound()：
 *  查找时，采用二分查找法查找 begin-needTime 的上界，即最后一个小于 target 的数；
 * 
 * 使用系统自带的Arrays.binarySearch()：
 *  笔试过程中，能用就用，毕竟省时间。
 *  只不过大家需要注意：
 *  binarySearch()方法的返回值为：
 *      1. 如果找到关键字，则返回值为关键字在数组中的位置索引，且索引从0开始；
 *      2. 如果没有找到关键字，返回值为负的插入点值，所谓插入点值就是第一个 比关键字大 的元素在数组中的位置索引，
 *      而且这个位置索引从1开始。(这就是为什么当返回值为 负数 时，我们转成正数后是 -2）
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        int[] times = new int[n];
        for (int i = 0; i < n; i++) {
            times[i] = sc.nextInt() * 60 + sc.nextInt();
        }
        Arrays.sort(times);

        int needTime = sc.nextInt();
        int begin = sc.nextInt() * 60 + sc.nextInt();
        // int rst = Arrays.binarySearch(times, begin - needTime);
        // rst = rst > 0 ? times[rst] : times[-rst - 2];
        int rst = times[binarySearchUpperBound(times, begin - needTime)];
        System.out.println(rst / 60 + " " + rst % 60);
    }

    private static int binarySearchUpperBound(int[] times, int target) {
        int left = -1, right = times.length - 1;
        while (left < right) {
            int mid = left + ((right - left + 1) >> 1);
            if (target >= times[mid]) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}

/*
题目描述
统计一个数字在排序数组中出现的次数。

输入：
[1, 2, 3, 4, 4, 4, 4, 5, 6, 7, 8, 8] 4

输出：
4
 */

/**
 * Approach: Binary Search
 * 本题考查了二分查找的 求上界 和 求下界 两种写法。
 * 非常适合来阐述二分查找的模板。对二分法已经有一定了解的朋友，可以直接看总结部分。
 *
 * 首先我们来看一下 求下界 的方法：
 *  下界的定义是：找到一个数，这个数是 第一个 大于等于 target 的数，这个数的位置就是下界。
 *  注意到代码二分的边界设成了left = 0, right = nums.length，这是一个左闭右开的区间，中点处是 mid = (left+right)/2
 *  二分结束后，left 等于 right；
 *  下界的位置靠近 左端点，所以我们从 左端点开始 找，因此，可以看到:
 *  二分中 left 的位置在一步一步向右端点靠近 (因此要加一)，而 right 只是起到缩小范围的作用；
 *  右端点是个开端点，这是为了处理有序数组中没有一个数字比 target 大的情况，
 *  因此，如果查找失败，left 和 right 可以指向一个空的位置，也就是数组的最后一个位置的后一个位置。
 *  这和程序设计中的“左闭右开”区间的思想是一样的。
 *
 * 至于中点处为什么要向下取整，原因是这样的：
 * 如果这个题要你顺序查找这个有序数组找到 下界 ，你会从哪里开始找？
 * 肯定是左边第一个元素开始找啊，你总不可能从第二个元素开始找吧，这就是为什么要向下取整的原因，向下取整可以避免漏掉最优解。
 * 还有一点要始终记得，二分结束后，left = right，因此最后 left, right 都是答案。
 *
 * 接下来我们再来看一下 求上界 的方法
 * 其方法与 求下界 是一样的。
 * 求上界求的是 最后一个 大于等于 target 的数字的位置，我们把数组反过来，
 * 以 右端点作为起点 开始查找，这个时候第一个小于或等于 target 的元素的位置就是原问题的上界；
 * 因此我们只需要把求上界转化成求下界来看，代码中的一切东西都理所当然了：
 *  right = nums.length - 1, left = -1，因为把数组反过来的，故 右端点就是起点，结束的位置就是第一个元素前面的一个位置.
 *  mid = (left + right + 1) / 2，因为把数组反过来了，因此这里的 向上进一 其实就是 反过来之后的向下取整。
 *
 * 总结：
 * 可以看到求上界也可以转换成求下界来解决，并且求下界更加常见，因此最后我们来详细总结一下求下界的做法。
 * 求下界第一件事就是 确定左右端点 范围，由于求下界是求第一个满足条件的位置，
 * 因此，以左端点为起点，最后一个元素的后一个位置作为终点。
 * 这是为了当 没有满足条件的解 时可以得到一个合理的值(指向最后一个元素的后一个位置就是代表着没有找到下界)；
 * 中点 取 靠近起点的一端 ，根据靠近的位置选择 向下取整 还 是向上进一。
 * 在缩小范围的时候：
 *  如果 nums[mid] 满足条件，那么令 right = mid, 这样子可以缩小范围. 因为 [mid + 1, r) 必定不是我们想要找的下界，
 *  但 mid 有可能是答案，没关系，我们让 left 来等于 mid 就行，right只管缩小范围)，而且，这样可以保证一定有解；
 *  如果 nums[mid] 不满足条件，令 left = mid + 1, 由于 [left, mid] 是 一定不满足条件 的，故让 left 一步步靠近 right 来找到满足条件的答案。
 *  （注：条件指的是 我们想要寻找的上/下界 存在于 起点 和 mid 组成的那一段范围中）
 */
class Solution {
    public int GetNumberOfK(int [] array , int k) {
        if (array == null || array.length == 0) {
            return 0;
        }

        return upperBound(array, k) - lowerBound(array, k) + 1;
    }

    // 求下界
    private int lowerBound(int[] nums, int target) {
        // 左闭右开
        // left负责一步步向 right 逼近寻找答案; right 只负责缩小范围
        int left = 0, right = nums.length;
        while (left < right) {
            // 中点选取在靠 起点 的一端（求下界方法里的话是 left,因此向下取整）
            int mid = left + ((right - left) >> 1);
            if (target <= nums[mid]) {
                // 当 target <= nums[mid] 时，说明 符合条件，即 下界 存在于 [left, mid] 中
                // 因此我们可以直接去掉 [mid+1, right] 这个部分，即 right 移动到 mid. (mid有可能是最终结果)
                right = mid;
            } else {
                // 否则，我们可以直接去除 [left, mid] 部分，因为他们肯定是不符合条件的。
                // 即 left 移动到 mid+1,靠近 right 来寻找答案
                left = mid + 1;
            }
        }
        return left;
    }

    // 求上界
    // 可以转换成 求下界 来解决，只是将数组 反过来 而已
    private int upperBound(int[] nums, int target) {
        // 左开右闭
        // right负责一步步向 left 逼近寻找答案; left 只负责缩小范围
        int left = -1, right = nums.length - 1;
        while (left < right) {
            // 中点选取在靠 起点 的一端（求上界方法里的话是 right,因此向上取整）
            int mid = left + ((right - left + 1) >> 1);
            if (target >= nums[mid]) {
                // 当 target >= nums[mid] 时，说明 符合条件，即 上界 存在于 [mid, right] 中
                // 因此我们可以直接去掉 [left, mid-1] 这个部分，即 left 移动到 mid. (mid有可能是最终结果)
                left = mid;
            } else {
                // 否则，我们可以直接去除 [mid, right] 部分，因为他们肯定是不符合条件的。
                // 即 right 移动到 mid-1,靠近 left 来寻找答案
                right = mid - 1;
            }
        }
        return right;
    }

}
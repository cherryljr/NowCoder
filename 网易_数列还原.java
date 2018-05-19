/*
题目描述
牛牛的作业薄上有一个长度为 n 的排列 A，这个排列包含了从1到n的n个数，
但是因为一些原因，其中有一些位置（不超过 10 个）看不清了，
但是牛牛记得这个数列顺序对的数量是 k，顺序对是指满足 i < j 且 A[i] < A[j] 的对数，
请帮助牛牛计算出，符合这个要求的合法排列的数目。

输入描述:
每个输入包含一个测试用例。每个测试用例的第一行包含两个整数 n 和 k（1 <= n <= 100, 0 <= k <= 1000000000），
接下来的 1 行，包含 n 个数字表示排列 A，其中等于0的项表示看不清的位置（不超过 10 个）。
输出描述:
输出一行表示合法的排列数目。

示例1
输入
5 5
4 0 0 2 0
输出
2
 */

/**
 * Approach: Backtracking
 * 该题思路还是非常直接明了的，但是考验一定的代码能力。
 * 需要大家能够熟练地写出 全排列的求解方案 以及 顺序对的求解。
 *
 * 对于顺序对的分析主要如下：
 *  总顺序对数 = 已经填进去的数之间的顺序对数 + 没有填进去的数之间的顺序对数 + 已经填进去和没有填进去的数之间的顺序对数
 *  第一部分：预先算一遍已经填进去的数之间的顺序对数 calBaseOrderPairs()
 *  第二部分：只计算新加入模糊数字后，模糊数字之间所产生的顺序对数。
 *  第三部分：将每个要插入的模糊数填在空位上，和其他预先填进去的数组成的顺序对数。
 *  三部分相加，判断等不等于k就行。
 * 其中 第二部分 和 第三部分 可以合并起来计算 calAppendedOrderPairs()。
 * 第一部分是确定不变的，但是 二三部分 会因为插入模糊数的顺序方案的不同而产生不同的结果，
 * 因此这里需要遍历我们之前枚举出来 所有的模糊数的全排列方案。
 *
 * 时间复杂度：计算原顺序对数 O(n^2) + 枚举全排列 O(10!) * 计算插入模糊数后的顺序对数O(k*n)
 * 因为 n 的值比较小，最大只有 100.所以还是能过的。
 *
 * 注：
 * 求解顺序对这里我们可以使用：BIT / MergeSort 的方法来进行计算，从而达到对时间复杂度进行优化的目的。
 * 实际上等同于 计算每个数前面比它小的数有多少个。
 * https://github.com/cherryljr/LintCode/blob/master/Count%20of%20Smaller%20Number%20before%20itself.java
 * 然而本题代码本来就不少，还是不要引入这些东西让其更加复杂话了，有兴趣的朋友可以试一试。
 * 全排列的求解模板：
 * https://github.com/cherryljr/LintCode/blob/master/Permutations.java
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int n = input.nextInt();
        int k = input.nextInt();
        int[] nums = new int[n];
        boolean[] exist = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            nums[i] = input.nextInt();
            exist[nums[i]] = true;
        }
        // 获得模糊的数字
        List<Integer> lostNumbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (!exist[i]) {
                lostNumbers.add(i);
            }
        }

        // 计算模糊数字的全排列
        List<List<Integer>> permutations = new ArrayList<>();
        getPermutation(permutations, lostNumbers, new ArrayList<>());

        // 计算已有排列的顺序对
        int count = 0;
        count = calBaseOrderPairs(nums);

        int rst = 0;
        for (List<Integer> list : permutations) {
            int temp = count;
            int[] tempNums = Arrays.copyOf(nums, n);
            // 计算加入模糊数字之后，模糊数字所产生的顺序对数
            // 模糊数字所产生的顺序对包括：模糊数字与模糊数字之间的顺序对 + 模糊数字与已有数字之间的顺序对
            temp += calAppendedOrderPairs(tempNums, list);
            if (temp == k) {
                rst++;
            }
        }
        System.out.println(rst);
    }

    // 计算已有数字的顺序对数
    private static int calBaseOrderPairs(int[] nums) {
        int rst = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] != 0 && nums[j] > nums[i]) {
                        rst++;
                    }
                }
            }
        }
        return rst;
    }

    // 计算加入模糊数字后，新增的顺序对数（加入方案为lostNumbers）
    private static int calAppendedOrderPairs(int[] nums, List<Integer> lostNumbers) {
        int index = 0, count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                nums[i] = lostNumbers.get(index++);
                // 模糊数字与其之前所有元素所产生的顺序对数
                for (int j = 0; j < i; j ++) {
                    if (nums[j] != 0 && nums[j] < nums[i]) {
                        count++;
                    }
                }
                // 模糊数字与其之后所有元素所产生的顺序对数
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] != 0 && nums[j] > nums[i]) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // 计算全排列的所有方案
    private static void getPermutation(List<List<Integer>> permutations, List<Integer> nums, List<Integer> list) {
        if (list.size() == nums.size()) {
            permutations.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.size(); i++) {
            if (!list.contains(nums.get(i))) {
                list.add(nums.get(i));
                getPermutation(permutations, nums, list);
                list.remove(list.size() - 1);
            }
        }
    }

    static class InputReader {
        BufferedReader buffer;
        StringTokenizer token;

        InputReader() {
            buffer = new BufferedReader(new InputStreamReader(System.in));
        }

        boolean hasNext() {
            while (token == null || !token.hasMoreElements()) {
                try {
                    token = new StringTokenizer(buffer.readLine());
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }

        String next() {
            if (hasNext()) return token.nextToken();
            return null;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        BigDecimal nextBigDecimal() {
            return new BigDecimal(next());
        }
    }

}
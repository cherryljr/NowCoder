/*
为了不断优化推荐效果，今日头条每天要存储和处理海量数据。
假设有这样一种场景：我们对用户按照它们的注册时间先后来标号，对于一类文章，每个用户都有不同的喜好值，
我们会想知道某一段时间内注册的用户（标号相连的一批用户）中，有多少用户对这类文章喜好值为k。
因为一些特殊的原因，不会出现一个查询的用户区间完全覆盖另一个查询的用户区间(不存在L1<=L2<=R2<=R1)。

输入描述:
输入： 第1行为n代表用户的个数 第2行为n个整数，第i个代表用户标号为i的用户对某类文章的喜好度 第3行为一个正整数q代表查询的组数
第4行到第（3+q）行，每行包含3个整数l,r,k代表一组查询，即标号为l<=i<=r的用户中对这类文章喜好值为k的用户的个数。
数据范围n <= 300000,q<=300000 k是整型

输出描述:
输出：一共q行，每行一个整数代表喜好值为k的用户的个数

输入例子1:
5
1 2 3 3 5
3
1 2 1
2 4 5
3 5 3

输出例子1:
1
0
2

例子说明1:
样例解释:
有5个用户，喜好值为分别为1、2、3、3、5，
第一组询问对于标号[1,2]的用户喜好值为1的用户的个数是1
第二组询问对于标号[2,4]的用户喜好值为5的用户的个数是0
第三组询问对于标号[3,5]的用户喜好值为3的用户的个数是2
 */

/**
 * Approach: HashMap + BinarySearch
 * 根据题目的数据量，可以推测出来该题的时间复杂度在 O(nlogn) 级别。
 * 所以很自然就能够想到 二分查找 的方法。
 * 首先利用 Map 统计每个喜好程序下的人员编号。
 * 因为按编号大小输入，所以map下的每个 list 里面的编号已经是有序的了（从小到大）。
 * 然后根据 left 和 right 在对应的 list 中进行查询人数区间。
 * 用到了 lowerBound 和 upperBound。
 * 注意处理一下查询目标不存在和查询范围越界的情况即可。
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();   // 用户个数
            Map<Integer, List<Integer>> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                map.computeIfAbsent(sc.nextInt(), x -> new ArrayList<>()).add(i);
            }

            int q = sc.nextInt(); // 查询组数
            for (int i = 0; i < q; i++) {
                int left = sc.nextInt() - 1, right = sc.nextInt() - 1, k = sc.nextInt();
                if (!map.containsKey(k)) {
                    System.out.println(0);
                } else {
                    List<Integer> list = map.get(k);
                    int leftRst = lowerBound(list, left);
                    int rightRst = upperBound(list, right);
                    // list 中不存在对应的范围（查询范围越界）
                    // left > list中的最大值 || right < list中的最小值
                    if (leftRst == list.size() || right == -1) {
                        System.out.println(0);
                        continue;
                    }
                    System.out.println(rightRst - leftRst + 1);
                }
            }
        }
        sc.close();
    }

    private static int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size();
        while (left < right) {
            int mid = left + (right - left >> 1);
            if (target <= list.get(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private static int upperBound(List<Integer> list, int target) {
        int left = -1, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left + 1 >> 1);
            if (target >= list.get(mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }

}


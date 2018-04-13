/*
题目描述
某餐馆有n张桌子，每张桌子有一个参数：a 可容纳的最大人数；
有m批客人，每批客人有两个参数:b人数，c预计消费金额。
在不允许拼桌的情况下，请实现一个算法选择其中一部分客人，使得总预计消费金额最大

输入描述:
输入包括m+2行。 第一行两个整数n(1 <= n <= 50000),m(1 <= m <= 50000) 第二行为n个参数a,即每个桌子可容纳的最大人数,以空格分隔,范围均在32位int范围内。 接下来m行，每行两个参数b,c。分别表示第i批客人的人数和预计消费金额,以空格分隔,范围均在32位int范围内。
输出描述:
输出一个整数,表示最大的总预计消费金额

示例1
输入
3 5 2 4 2 1 3 3 5 3 7 5 9 1 10
输出
20
 */

/**
 * Approach: Greedy
 * 这道题目实际上是一个贪心的做法。
 * 策略就是：我们优先接纳那些消费最多的客户。（和现实生活一样呢...）
 * 为了实现贪心策略，我们需要对 桌子(从小到大) 和 客户(按金额从大到小) 进行排序。
 * 这里使用到了 优先级队列（最大堆）
 * 具体解释详见注释。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt(), m = sc.nextInt();
        int[] desks = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            desks[i] = sc.nextInt();
        }
        // 餐桌按照 从小到大 进行排序
        Arrays.sort(desks);
        // 客人按照 消费金额 从大到小 排序
        PriorityQueue<Consumer> maxHeap = new PriorityQueue<>();
        for (int i = 0; i < m; i++) {
            int people = sc.nextInt(), cost = sc.nextInt();
            if (people <= desks[n - 1]) {
                maxHeap.offer(new Consumer(people, cost));
            }
        }

        // 不开long long见祖宗，十年OI一场空
        long maxProfit = 0;
        while (!maxHeap.isEmpty()) {
            Consumer curr = maxHeap.poll();
            // 因为桌子已经按大小排序好了，
            // 所以在 desks 中二分查找第一个 大于等于 curr.people 的桌子（能容纳下的桌子）
            int index = binarySearch(curr.people, desks);
            // 因为 二分查找 到的是第一个位置，但是 desks 数组中是存在 重复 元素的。
            // 所以我们无法确定后面的桌子是否被使用过，
            // 如果当前桌子被使用，我们就遍历后面的元素找到里 index 最近的未被使用的桌子
            while (index < n && visited[index]) {
                index++;
            }
            // 如果存在能够容纳人数并且还未被使用的桌子，就说明我们能够接待该批客人
            if (index < n) {
                visited[index] = true;
                maxProfit += curr.cost;
            }
        }
        System.out.println(maxProfit);
    }

    private static int binarySearch(int people, int[] desks) {
        int left = 0, right = desks.length;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (people <= desks[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    static class Consumer implements Comparable<Consumer> {
        int people, cost;

        public Consumer(int people, int cost) {
            this.people = people;
            this.cost = cost;
        }

        @Override
        public int compareTo(Consumer other) {
            return other.cost - this.cost;
        }
    }
}

/*
一块金条切成两半，是需要花费和长度数值一样的铜板的。
比如长度为20的 金条， 不管切成长度多大的两半， 都要花费20个铜板。
一群人想整分整块金 条， 怎么分最省铜板？

例如,给定数组{10,20,30}， 代表一共三个人， 整块金条长度为10+20+30=60. 金条要分成10,20,30三个部分。
如果，先把长度60的金条分成10和50， 花费60 再把长度50的金条分成20和30，花费50一共花费110铜板。
但是如果， 先把长度60的金条分成30和30， 花费60 再把长度30金条分成10和20， 花费30 一共花费90铜板。

求输入一个数组， 返回分割的最小代价。
 */

/**
 * Approach: Greedy
 * 这道题目实际上考察的是 哈夫曼编码 问题。而它就是一个 贪心算法。
 * 其做法是：
 *  1. 建立一个 小根堆；
 *  2. 每次取出最小的 两个数 将他们合并在一起（加起来）。
 *  加起来得到的这个值 cost,就是我们切分一次所需要的代价；
 *  3. 将 cost 返回小根堆中；
 *  4. 重复 2，3 步骤，直到小根堆中只剩下一个数。
 *
 * 贪心的证明大家可以搜索一下 Huffman Code 的证明，
 * 而我这边想要表达的是：
 *  当一个 总代价 是由 子代价 累加/累乘 计算而来的时候，我们可以尝试着考虑使用 哈夫曼编码 的方式进行解决。
 *
 * 参考资料：
 * https://www.youtube.com/watch?v=dM6us854Jk0&t=15s
 * https://www.geeksforgeeks.org/greedy-algorithms-set-3-huffman-coding/
 */
import java.io.BufferedInputStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();   // 输出输入长度
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        System.out.println(leastCost(arr));
    }

    private static int leastCost(int[] arr) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i : arr) {
            minHeap.add(i);
        }
        int sum = 0;
        int temp = 0;
        while (minHeap.size() > 1) {
            temp = minHeap.poll() + minHeap.poll();
            sum += temp;
            minHeap.add(temp);
        }
        return sum;
    }
}
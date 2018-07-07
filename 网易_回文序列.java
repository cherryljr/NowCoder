/*
题目描述
如果一个数字序列逆置之后跟原序列是一样的就称这样的数字序列为回文序列。
例如：
{1, 2, 1}, {15, 78, 78, 15} , {112} 是回文序列,
{1, 2, 2}, {15, 78, 87, 51} ,{112, 2, 11} 不是回文序列。
现在给出一个数字序列，允许使用一种转换操作：
选择任意两个相邻的数，然后从序列移除这两个数，并用这两个数字的和插入到这两个数之前的位置(只插入一个和)。
现在对于所给序列要求出最少需要多少次操作可以将其变成回文序列。

输入描述:
输入为两行，第一行为序列长度n ( 1 ≤ n ≤ 50) 第二行为序列中的n个整数item[i] (1 ≤ iteam[i] ≤ 1000)，以空格分隔。

输出描述:
输出一个数，表示最少需要的转换次数

示例1
输入
4
1 1 1 3
输出
2
 */

/**
 * Approach: Deque | Two Pointers
 * 本题可以使用双端队列deque数据结构进行求解。
 * 主要思想为：判断队首和队尾元素。
 *  若二者相等，则将这两个元素都弹出队列，将队列规模缩小2个，再对该问题进行判断；
 *  若二者不相等，则选择其中较小的一个，将该元素和与其相邻的元素都弹出队列，
 *  再将其和插入队列，从而将队列规模缩小1个，再对该问题进行判断。
 *
 * 但实际上，我们也可以不使用双端队列，直接使用数据进行操作，维护两个指针 head, tail
 * 也能够达到同样的效果。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        while (input.hasNext()) {
            int n = input.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = input.nextInt();
            }

            int head = 0, tail = n - 1;
            int times = 0;
            while (head < tail) {
                if (nums[head] > nums[tail]) {
                    nums[--tail] += nums[tail + 1];
                    times++;
                } else if (nums[head] < nums[tail]) {
                    nums[++head] += nums[head - 1];
                    times++;
                } else {
                    head++;
                    tail--;
                }
            }
            System.out.println(times);
        }
    }

    // 输入输出模板
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
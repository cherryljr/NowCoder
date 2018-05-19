/*
题目描述
现在有一个数组，其值为从1到10000的连续增长的数字。
出于某次偶然操作，导致这个数组中丢失了某三个元素，同时顺序被打乱，
现在需要你用最快的方法找出丢失的这三个元素，并且将这三个元素根据从小到大重新拼接为一个新数字，计算其除以7的余数。
例：丢失的元素为336，10，8435，得到的新数字为103368435，除以七的余数为2。

输入描述:
输入数据为一行，包含9997个数字，空格隔开。
输出描述:
输出为一行，包含一个数字。

示例1
输入
同题设例子输入
输出
2
 */

/**
 * Approach: Traverse (make note)
 * 因为只需要找出哪三个数字缺少了，并且数字出现的范围已知。
 * 所以利用一个 boolean[] 记录每个数字是否出现过即可，（以数字的值作为下标）
 * 然后从前往后遍历时顺序自然是从小到大的，根本无需排序。
 * 将其拼接起来后，%7 就是答案了
 *
 * 时间复杂度：O(n)
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        boolean[] nums = new boolean[10001];
        for (int i = 0; i < 9997; i++) {
            nums[input.nextInt()] = true;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            if (!nums[i]) {
                sb.append(i);
            }
        }
        System.out.println(new Long(sb.toString()) % 7);
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
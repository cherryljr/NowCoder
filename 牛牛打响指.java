/*
时间限制：1秒空间限制：32768K

牛牛在地上捡到了一个手套，他带上手套发现眼前出现了很多个小人，当他打一下响指，这些小人的数量就会发生以下变化：
如果小人原本的数量是偶数那么数量就会变成一半，如果小人原本的数量是奇数那么数量就会加一。
现在牛牛想考考你，他要打多少次响指，才能让小人的数量变成1。

输入描述:
每个输入包含一个测试用例。
输入的第一行包括一个正整数，表示一开始小人的数量N(1<=N<=10^100)。

输出描述:
对于每个用例，在单独的一行中输出牛牛需要打多少次响指才能让小人的数量变成1。
示例1
输入
10000
输出
20
 */

/**
 * Approach 1: Simulation
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        long num = input.nextLong();

        long count = 0;
        while (num != 1) {
            if ((num & 1) == 0) {
                num >>>= 1;
                count++;
            } else {
                num += 1;
                count++;
            }
        }

        System.out.println(count);
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
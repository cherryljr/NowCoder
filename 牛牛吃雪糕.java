/*
牛牛吃雪糕
时间限制：1秒空间限制：32768K

最近天气太热了，牛牛每天都要吃雪糕。雪糕有一盒一份、一盒两份、一盒三份这三种包装，
牛牛一天可以吃多盒雪糕，但是只能吃六份，吃多了就会肚子疼，吃少了就会中暑。
而且贪吃的牛牛一旦打开一盒雪糕，就一定会把它吃完。请问牛牛能健康地度过这段高温期么？

输入描述:
每个输入包含多个测试用例。
输入的第一行包括一个正整数，表示数据组数T(1<=T<=100)。
接下来N行，每行包含四个正整数，表示高温期持续的天数N(1<=N<=10000)，
一盒一份包装的雪糕数量A(1<=A<=100000)，一盒两份包装的雪糕数量B(1<=B<=100000)，一盒三份包装的雪糕数量C(1<=A<=100000)。

输出描述:
对于每个用例，在单独的一行中输出结果。如果牛牛可以健康地度过高温期则输出"Yes"，否则输出"No"。
示例1
输入
4
1 1 1 1
2 0 0 4
3 0 2 5
4 24 0 0
输出
Yes
Yes
No
Yes
 */

/**
 * Approach 1: 0-1 Backpack (TLE)
 * 起初没啥想法，想直接接使用 01背包 看能不能背出目标值即可。
 * target总量为：days * 6 根雪糕
 * 然后用一个List记录下所有的雪糕，开始DP就行了...
 * 非常暴力的一个做法，时间复杂度为：O(n^2)
 * 根据给的数据，毫无疑问会超时...只过了 70% 的数据
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int T = input.nextInt();
        while (T-- > 0) {
            int day = input.nextInt();
            int hasOne = input.nextInt();
            int hasTwo = input.nextInt();
            int hasThree = input.nextInt();
            int total = hasOne + hasTwo + hasThree;
            int target = day * 6;

            // 利用 ice 来记录所有的雪糕。
            List<Integer> ice = new ArrayList<>(100000);
            for (int i = 0; i < hasOne; i++) {
                ice.add(1);
            }
            for (int i = 0; i < hasTwo; i++) {
                ice.add(2);
            }
            for (int i = 0; i < hasThree; i++) {
                ice.add(3);
            }
            // 对所有的雪糕进行 01 背包
            boolean[] dp = new boolean[target + 1];
            // Initialize
            dp[ice.get(0)] = true;
            for (int i = 1; i < total; i++) {
                for (int j = target; j >= ice.get(i); j--) {
                    dp[j] |= dp[j - ice.get(i)];
                }
            }

            System.out.println(dp[target] ? "Yes" : "No");
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

/**
 * Approach 2: Greedy
 * 看到这个数据量，应该是使用 O(n) 级别的算法。
 * 因此想干脆使用 贪心的策略 来试试。
 * 想法就是使用 1*hasOne, 2*hasTwo, 3*hasThree 去凑出 6*day 的量。
 * 首先吃多的，然后再吃少的，即 3 -> 2 -> 1 的顺序吃法。
 * 但是这个想法有一点是错误的：
 * 举一个例子1 0 3 1，没有一份一盒的，有3个两份一盒的，有1个三份一盒的，按照上面的贪心做法，先吃掉三份一盒的，
 * 牛牛还需要吃三份才能度过危机，这时却发现剩下的3个两份一盒的却怎么也凑不出3份来！如果直接吃3盒一盒两份的雪糕，牛牛是可以度过危机的。
 * （比赛时这个策略是能过的，牛客网还有没有加上这个Case,不知道后来加上去了没有）
 * 
 * 正确的吃法确实要先吃三份一盒(一天吃两盒)，但是，如果发现吃完三盒一份的还剩下一盒，
 * 这时候这一盒三份的就要和一盒两份的和一盒一份的凑着吃或者这一盒三份的和3个一盒一份的凑着吃，
 * 这是因为如果你独吃这个三份一盒的，你就会留下一个奇数，这个奇数有可能用一份一盒的和两份一盒的凑不齐；
 * 然后再吃两份一盒的(一天吃三盒)，这时如果剩下一盒两份的，要不要先拿一盒一份的来凑呢，
 * 不需要的，因为剩下的数一定是偶数，而用若干个1和若干个2一定是可以凑出一个偶数的；然后再吃一份一盒的(一天吃六盒)；
 * 最后看下剩下的两盒一份的和剩下的一盒一份的能不能再凑出一份口粮来。
 * 
 * 时间复杂度：O(n)
 * 参考：https://blog.csdn.net/FlushHip/article/details/80446002
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        InputReader input = new InputReader();
        int T = input.nextInt();
        while (T-- > 0) {
            int day = input.nextInt();
            int hasOne = input.nextInt();
            int hasTwo = input.nextInt();
            int hasThree = input.nextInt();

            int safe = hasThree >> 1;
            hasThree %= 2;
            if (hasThree == 1) {
                if (hasOne != 0 && hasTwo != 0) {
                    hasOne--;
                    hasTwo--;
                    safe++;
                } else if (hasOne >= 3 && hasTwo == 0) {
                    hasOne -= 3;
                    safe++;
                }
            }
            safe += hasTwo / 3;
            hasTwo %= 3;
            safe += hasOne / 6;
            hasOne %= 6;
            safe += 2 * hasTwo + hasOne >= 6 ? 1 : 0;

            System.out.println(safe >= day ? "Yes" : "No");
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
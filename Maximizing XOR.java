/*
给定两个整数：L 和 R
L ≤ A ≤ B ≤ R, 找出 A xor B 的最大值。

输入格式
第一行为 L
第二行为 R

数据范围
1 ≤ L ≤ R ≤ 103

输出格式
输出最大的异或和

样例输入
1
10

样例输出
15

样例解释
当B = 10, A = 5时，异或和最大。
1010 xor 0101 = 1111，所以答案是 15.

Sample Input
10
15
Sample Output
7

Explanation
Here two pairs (10, 13) and (11, 12) have maximum xor value 7, and this is the answer.
 */

/**
 * Approach: Mathematics
 * To maximize A xor B, we want A and B to differ as much as possible at every bit index.
 * We first find the most significant bit that we can force to differ by looking at L and R.
 * For all of the lesser significant bits in A and B, we can always ensure that they differ and still have L <= A <= B <= R.
 * Our final answer will be the number represented by all 1s starting from the most significant bit that differs between A and B
 * Let's try an example
 *      L = 10111
 *      R = 11100
 *          _X___  <-- that's most significant differing bit
 *          01111  <-- here's our final answer
 *
 * Notice how we don't directly calculate the values of A and B.
 */

import java.util.*;

public class Solution {
    static int maximizingXor(int l, int r) {
        int xor = l ^ r;
        int significantBit = 31 - Integer.numberOfLeadingZeros(xor);
        int result = (1 << (significantBit + 1)) - 1;
        return result;
    }

    // with no additional method calls (numberOfLeadingZeros)
    static int maximizingXor2(int l, int r) {
    	int xor = l ^ r;
    	int result = 0;
    	while (xor > 0) {
    		result <<= 1;
    		result |= 1;
    		xor >>= 1;
    	}
    	return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int l = in.nextInt();
        int r = in.nextInt();
        int result = maximizingXor(l, r);
        System.out.println(result);
        in.close();
    }
}

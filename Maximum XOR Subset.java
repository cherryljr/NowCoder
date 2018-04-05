/*
Given a set A[] of positive integers, find the maximum XOR value of the elements from all the possible subsets.

Input
The first line of input contains an integer T denoting the number of test cases. Then T test cases follow.
The first line of each test case contains an integer N, where N is the size of the set A[].
The second line of each test case contains N space separated integers, denoting elements of the set A[].

Output
Print out the maximum XOR subset value.

Constraints
1 <= T <= 100
1 <= N <= 30
0 <= A[i] < 1000

Examples
Input
3
3
2 4 5
3
33 73 64
9
5 633 64 43 131 51 87 999 23

Output
7
104
1023

Expected Complexity:
Time  : O(n)

OJ地址：https://practice.geeksforgeeks.org/problems/maximum-xor-subset/0/?ref=self
 */

/**
 * Approach: Gaussian Elimination
 * How does the Gaussian Elimination works?
 * For example: 3 numbers {9, 8, 5}
 * First sort them in decreasing order and convert them into binary:
 *      9 : 1001
 *      8 : 1000
 *      5 : 0101
 * Observe the 1st number. Highest bit is 4. （Firstly, we are in row1, it means index = 0)
 * Now check 4th bit of the 1st number (9). As it is 1, xor the number with the rest of the numbers where 4th bit is 1.
 * We want to set the other elements in the 4th bit as 0.
 *      9 : 1001
 *      1 : 0001 > changed
 *      5 : 0101
 * Then We move to the next row, it means index++
 * Check 3rd bit of 2nd number (1). As it is 0, check rest of the below numbers where 3rd bit is 1.
 * Number 5 has 1 in 3rd bit. Swap them :
 *      9 : 1001
 *      5 : 0101 > swapped
 *      1 : 0001 >
 * Now xor 5 with the rest of the numbers where 3rd bit is 1. Here none exists. So there will be no change.
 * Then move to the next row, index++
 * Now check 2nd bit of 3rd number (1). As it is 0 and there is no other number below where 2nd bit is 1, so there will be no change.
 * Now check 1st bit of 3rd number (1). As it is 1, change the rest of the numbers where 1st bit is 1.
 *      8 : 1000 > changed
 *      4 : 0100 > changed
 *      1 : 0001
 * No more bit left to consider
 * Now xor the whole remaining array {8 ^ 4 ^ 1} = 13
 * So 13 is the result.
 *
 * From the example above, I think you can understand the Approach clearly.
 * Now, let's analyse the time complexity.
 * From the process, we can see that we move from left->right and never come back.
 * (The length of one element is 32 bits)
 * And at each bit, we will check n elements.
 * So the total time complexity is : O(32n) => O(n)
 *
 * Reference:
 * Good Explanation of Gaussian Elimination (Mathematics): https://www.youtube.com/watch?v=2j5Ic2V7wq4
 * https://math.stackexchange.com/questions/48682/maximization-with-xor-operator
 */

import java.util.*;
import java.io.*;

class GfG {
    public static void main (String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            System.out.println(maxSubsetXOR(nums));
        }
    }

    public static int maxSubsetXOR(int nums[]) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Initialize index of chosen elements
        int index = 0;
        // Traverse through all bits of integer starting from the most significant bit (MSB)
        for (int i = 31; i >= 0; i--) {
            // Initialize the maximum value and index of maximum value
            int maxValue = Integer.MIN_VALUE;
            int maxIndex = index;
            // Find the maximum value and its index (maxIndex)
            for (int j = index; j < nums.length; j++) {
                // if the ith bit of nums[j] equals to 1 and num[j] is bigger than maxValue
                // update the maxVaule and maxIndex
                if (((nums[j] >> i) & 1) == 1 && nums[j] > maxValue) {
                    maxValue = nums[j];
                    maxIndex = j;
                }
            }
            // If there was no element with ith bit set, move to smaller i
            if (maxValue == Integer.MIN_VALUE) {
                continue;
            }

            // Put maximum value with ith bit num at index (Swap the index row with maxIndex row)
            int temp = nums[index];
            nums[index] = nums[maxIndex];
            nums[maxIndex] = temp;
            // Update maxIndex (After the process above, the maxValue is in the index row)
            maxIndex = index;

            // Do XOR of num[maxIndex] with all numbers having ith bit
            for (int j = 0; j < nums.length; j++) {
                // XOR num[maxIndex] those numbers which have the ith bit (Set the ith bit as 0 in other rows)
                if (j != maxIndex && ((nums[j] >> i) & 1) == 1) {
                    nums[j] ^= nums[maxIndex];
                }
            }

            // Increment index of chosen elements
            index++;
        }

        // Final result is XOR of all elements
        int rst = 0;
        for (int i = 0; i < nums.length; i++) {
            rst ^= nums[i];
        }
        return rst;
    }
}
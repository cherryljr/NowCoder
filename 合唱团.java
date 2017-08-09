经典的DP问题，与 LintCode 的 K Sum 问题十分相似。
该题可以跟 K Sum 一样使用三维数组的方法解决，但是为了节省空间，我们采用了二维数组。
同样的 K Sum 也可以使用二维数组进一步优化。
Backpack DP时，for loop的写法考虑顺序都是这样的：
	1. 先对于 i 来说，考虑前i个元素
	2. 然后对于 k 来说，考虑前i个元素中取几个值
	3. 最后对于 j 来说，考虑限制条件，这里是指学生位置编号的差不能超过 d，
		 在K Sum 问题中，则是所取得值的和刚好为 Target
		 注意：在 K Sum 的 2D-Arrays 中思路有所不同，请看详细解析 

因为存在负数的问题，故需要维护两个 dp数组， 一个储存最大值，一个储存最小值
State:
	fmax[i][k]: 表示前i个学生中，取k个。并且以第i个学生为结尾所得到的最大乘积
	fmin[i][k]: 表示前i个学生中，取k个。并且以第i个学生为结尾所得到的最大乘积
Function:
	fmax[i][k] = Math.max(fmax[i][k], Math.max(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
  fmin[i][k] = Math.min(fmin[i][k], Math.min(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
Initialize:
	f[i][0] = 0, f[0][i] = 0, f[i][1] = a[i]
Answer:
	Math.max(f[i][K])
	

/*
题目描述
有 n 个学生站成一排，每个学生有一个能力值，牛牛想从这 n 个学生中按照顺序选取 k 名学生，
要求相邻两个学生的位置编号的差不超过 d，使得这 k 个学生的能力值的乘积最大，你能返回最大的乘积吗？

输入描述:
每个输入包含 1 个测试用例。每个测试数据的第一行包含一个整数 n (1 <= n <= 50)，表示学生的个数，
接下来的一行，包含 n 个整数，按顺序表示每个学生的能力值  ai（-50 <= ai <= 50）。
接下来的一行包含两个整数，k 和 d (1 <= k <= 10, 1 <= d <= 50)。

输出描述:
输出一行表示最大的乘积。

示例1
输入

3
7 4 7
2 50
输出

49
*/

import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            int n = scan.nextInt();          // n 个学生
            int[] arr = new int [n];
            for (int i = 0; i < n ; i++) {
                arr[i] = scan.nextInt();
            }
            int K = scan.nextInt();            // 选出K个
            int d = scan.nextInt();            // 两个学生的位置编号的差不超过 d
            
            System.out.println(getResult(n, arr, K, d));
        }
    }
    
    private static long getResult(int n, int[] arr, int K, int d) {
        long[][] fmax = new long[n + 1][K + 1]; // 记录最大乘积
        long[][] fmin = new long[n + 1][K + 1]; // 记录最小乘积
        // fmax[k][i]表示 : 当选中了k个学生，并且以第i个学生为结尾，所产生的最大乘积；
        // fmin[k][i]表示 : 当选中了k个学生，并且以第i个学生为结尾，所产生的最小乘积；
        long  res = Integer.MIN_VALUE; // 记得用Long类型，考虑数值范围
        
        // Initialize & Function
        for (int i = 1; i <= n; i++) {
            fmax[i][1] = arr[i - 1];
            fmin[i][1] = arr[i - 1];
            for (int k = 1; k <= K; k++) {
            	// 令j从 i-1 到 1，并且 j 与 i 之间小于间隔 d，遍历fmax[j][k-1]，以及fmin[j][k-1]；
                for (int j = i - 1 ; j > 0 &&  i - j <= d ; j--) {
                    fmax[i][k] = Math.max(fmax[i][k], Math.max(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
                    fmin[i][k] = Math.min(fmin[i][k], Math.min(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
                }
            }
            res = Math.max(res ,fmax[i][K]);
        }
        
        // Answer
        return res;
    }
}
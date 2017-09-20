完全背包问题
补充问题为：01背包问题

/*
换钱的最少货币数
【题目】
给定数组arr，arr中所有的值都为正数且不重复。每个值代表一种面值的货币，
每种面值的货币可以使用任意张，再给定一个整数aim代表要找的钱数，求组成aim的最少货币数。

【举例】
arr=[5,2,3]，aim=20。
4张5元可以组成20元，其他的找钱方案都要使用更多张的货币，所以返回4。

arr=[5,2,3]，aim=0。
不用任何货币就可以组成0元，返回0。

arr=[3,5]，aim=2。
根本无法组成2元，钱不能找开的情况下默认返回-1。

【补充题目】
给定数组arr，arr中所有的值都为正数。每个值仅代表一张钱的面值，再给定一个整数aim代表要找的钱数，求组成aim的最少货币数。
*/

public class CoinsMin {

	public static int minCoins1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return -1;
		}
		int n = arr.length;
		int max = Integer.MAX_VALUE;
		int[][] dp = new int[n][aim + 1];
        // Initialize
		for (int j = 1; j <= aim; j++) {
			dp[0][j] = max;
			if (j - arr[0] >= 0 && dp[0][j - arr[0]] != max) {
				dp[0][j] = dp[0][j - arr[0]] + 1;
			}
		}
        // Function
		int left = 0;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= aim; j++) {
				left = max;
				if (j - arr[i] >= 0 && dp[i][j - arr[i]] != max) {
					left = dp[i][j - arr[i]] + 1;
				}
				dp[i][j] = Math.min(left, dp[i - 1][j]);
			}
		}
        // Answer
		return dp[n - 1][aim] != max ? dp[n - 1][aim] : -1;
	}
    
    // 动态规划的空间优化
	public static int minCoins2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return -1;
		}
		int n = arr.length;
		int max = Integer.MAX_VALUE;
		int[] dp = new int[aim + 1];
        // Initialize
		for (int j = 1; j <= aim; j++) {
			dp[j] = max;
			if (j - arr[0] >= 0 && dp[j - arr[0]] != max) {
				dp[j] = dp[j - arr[0]] + 1;
			}
		}
        // Function
		int left = 0;
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= aim; j++) {
				left = max;
				if (j - arr[i] >= 0 && dp[j - arr[i]] != max) {
					left = dp[j - arr[i]] + 1;
				}
				dp[j] = Math.min(left, dp[j]);
			}
		}
        // Answer
		return dp[aim] != max ? dp[aim] : -1;
	}

    // 补充问题，值代表一张钱的面值。（01背包问题）
	public static int minCoins3(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return -1;
		}
		int n = arr.length;
		int max = Integer.MAX_VALUE;
        // Initialize
		int[][] dp = new int[n][aim + 1];
		for (int j = 1; j <= aim; j++) {
			dp[0][j] = max;
		}
		if (arr[0] <= aim) {
			dp[0][arr[0]] = 1;
		}
        // Function
		int leftup = 0; 
		for (int i = 1; i < n; i++) {
			for (int j = 1; j <= aim; j++) {
				leftup = max;
				if (j - arr[i] >= 0 && dp[i - 1][j - arr[i]] != max) {
					leftup = dp[i - 1][j - arr[i]] + 1;
				}
				dp[i][j] = Math.min(leftup, dp[i - 1][j]);
			}
		}
        // Answer
		return dp[n - 1][aim] != max ? dp[n - 1][aim] : -1;
	}
    
    // 01背包问题的空间优化 
	public static int minCoins4(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return -1;
		}
		int n = arr.length;
		int max = Integer.MAX_VALUE;
        // Initialize
		int[] dp = new int[aim + 1];
		for (int j = 1; j <= aim; j++) {
			dp[j] = max;
		}
		if (arr[0] <= aim) {
			dp[arr[0]] = 1;
		}
        // Function
		int leftup = 0; 
		for (int i = 1; i < n; i++) {
			for (int j = aim; j > 0; j--) {
				leftup = max;
				if (j - arr[i] >= 0 && dp[j - arr[i]] != max) {
					leftup = dp[j - arr[i]] + 1;
				}
				dp[j] = Math.min(leftup, dp[j]);
			}
		}
        // Answer
		return dp[aim] != max ? dp[aim] : -1;
	}

	public static void main(String[] args) {
		int[] arr1 = { 100, 20, 5, 10, 2, 50, 1 };
		int aim1 = 17019;
		System.out.println(minCoins1(arr1, aim1));
		System.out.println(minCoins2(arr1, aim1));

		int[] arr2 = { 10, 100, 2, 5, 5, 5, 10, 1, 1, 1, 2, 100 };
		int aim2 = 223;
		System.out.println(minCoins3(arr2, aim2));
		System.out.println(minCoins4(arr2, aim2));

	}
}

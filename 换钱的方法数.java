完全背包问题

/*
换钱的方法数
【题目】
给定数组arr，arr中所有的值都为正数且不重复。每个值代表一种面值的货币，
每种面值的货币可以使用任意张，再给定一个整数aim代表要找的钱数，求换钱有多少种方法。

【举例】
arr=[5,10,25,1]，aim=0。
组成0元的方法有1种，就是所有面值的货币都不用。所以返回1。

arr=[5,10,25,1]，aim=15。
组成15元的方法有6种，分别为3张5元、1张10元+1张5元、1张10元+5张1元、10张1元+1张5元、2张5元+5张1元和15张1元。所以返回6。

arr=[3,5]，aim=2。
任何方法都无法组成2元。所以返回0。
*/

public class CoinsWay {

	public static int coins1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return process1(arr, 0, aim);
	}
    // 暴力递归，子过程未被优化
	public static int process1(int[] arr, int index, int aim) {
		int res = 0;
		if (index == arr.length) {
			res = aim == 0 ? 1 : 0;
		} else {
			for (int i = 0; arr[index] * i <= aim; i++) {
				res += process1(arr, index + 1, aim - arr[index] * i);
			}
		}
		return res;
	}

	public static int coinsOther(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return processOther(arr, arr.length - 1, aim);
	}
    // 暴力递归，从后往前，子过程未被优化
	public static int processOther(int[] arr, int index, int aim) {
		int res = 0;
		if (index == -1) {
			res = aim == 0 ? 1 : 0;
		} else {
			for (int i = 0; arr[index] * i <= aim; i++) {
				res += processOther(arr, index - 1, aim - arr[index] * i);
			}
		}
		return res;
	}

	public static int coins2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[][] map = new int[arr.length + 1][aim + 1];
		return process2(arr, 0, aim, map);
	}
    // 使用记忆搜索优化
	public static int process2(int[] arr, int index, int aim, int[][] map) {
		int res = 0;
		if (index == arr.length) {
			res = aim == 0 ? 1 : 0;
		} else {
			int mapValue = 0;
			for (int i = 0; arr[index] * i <= aim; i++) {
				mapValue = map[index + 1][aim - arr[index] * i];
				if (mapValue != 0) {
					res += mapValue == -1 ? 0 : mapValue;
				} else {
					res += process2(arr, index + 1, aim - arr[index] * i, map);
				}
			}
		}
		map[index][aim] = res == 0 ? -1 : res;
		return res;
	}

    // 动态规划
	public static int coins3(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[][] dp = new int[arr.length][aim + 1];
        // Initialize
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		for (int j = 1; arr[0] * j <= aim; j++) {
			dp[0][arr[0] * j] = 1;
		}
        // Function
		int num = 0;
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= aim; j++) {
				num = 0;
				for (int k = 0; j - arr[i] * k >= 0; k++) {
					num += dp[i - 1][j - arr[i] * k];
				}
				dp[i][j] = num;
			}
		}
        // Answer
		return dp[arr.length - 1][aim];
	}

    // 动态规划第一步优化 时间复杂度的优化
	public static int coins4(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[][] dp = new int[arr.length][aim + 1];
        // Initialize
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		for (int j = 1; arr[0] * j <= aim; j++) {
			dp[0][arr[0] * j] = 1;
		}
        // Function
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= aim; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
			}
		}
        // Answer
		return dp[arr.length - 1][aim];
	}

    // 动态规划第二步优化 空间复杂度的优化
	public static int coins5(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int[] dp = new int[aim + 1];
        // Initialize
		for (int j = 0; arr[0] * j <= aim; j++) {
			dp[arr[0] * j] = 1;
		}
        // Function
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= aim; j++) {
				dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
			}
		}
        // Answer
		return dp[aim];
	}

	public static void main(String[] args) {
		int[] coins = { 10, 5, 1, 25 };
		int aim = 2000;

		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		System.out.println(coins1(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		System.out.println(coinsOther(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

		aim = 20000;

		start = System.currentTimeMillis();
		System.out.println(coins2(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		System.out.println(coins3(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		System.out.println(coins4(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		System.out.println(coins5(coins, aim));
		end = System.currentTimeMillis();
		System.out.println("cost time : " + (end - start) + "(ms)");

	}

}

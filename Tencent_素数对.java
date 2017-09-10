筛法求素数的一个应用罢了。
思路：要求 n 以内所有符合的素数对。
本来是打算每次都求出 n 以内所有的素数。
然后遍历 2~n-1 是否都为 true,若是则 count++.
但是因为题目中明确给出了 input<1000 
因此直接求出1000以内所有的素数。需要的话调用即可。

/*
给定一个正整数，编写程序计算有多少对质数的和等于输入的这个正整数，并输出结果。输入值小于1000。
如，输入为10, 程序应该输出结果为2。（共有两对质数的和为10,分别为(5,5),(3,7)） 

输入描述:
输入包括一个整数n,(3 ≤ n < 1000)

输出描述:
输出对数

示例1
输入
10

输出
2
*/

public class Main { 
    static boolean[] isPrime = new boolean[1000];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        isPrime();
        while (sc.hasNext()) {
            int a = sc.nextInt();
            System.out.println(countPrimePairs(a));
        }
        sc.close();
    }
    
    private static int countPrimePairs(int a) {
        int count = 0;
        for (int i = 2; i <= a / 2; i++) {
            if (isPrime[i] && isPrime[a - i]) {
                count++;
            }
        }
        return count;
    }
    
    // 筛法求素数（优化版）
    private static void isPrime() {
        Arrays.fill(isPrime, 2, 999, true);
        
        for (int i = 2; i * i < 1000; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < 1000; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }
}

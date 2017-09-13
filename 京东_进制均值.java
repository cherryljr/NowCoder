该题考察点相对清晰。
	1. 如何计算一个数的 k 进制 
	2. 求两个数的最大公约数 GCD 

做法:
	通过方法 averageA 可以求出一个数 radix 进制的每一位数上的和，
	然后通过 calculate 方法求得将 2~A-1 进制表达的各个位数之和全部加起来的值
	最后求出 sum 和 A-2 的最大公约数，除以 GCD，从而得到最后结果。

/*
尽管是一个CS专业的学生，小B的数学基础很好并对数值计算有着特别的兴趣，喜欢用计算机程序来解决数学问题，
现在，她正在玩一个数值变换的游戏。她发现计算机中经常用不同的进制表示一个数，
如十进制数123表达为16进制时只包含两位数7、11（B），用八进制表示为三位数1、7、3，
按不同进制表达时，各个位数的和也不同，如上述例子中十六进制和八进制中各位数的和分别是18和11,。
小B感兴趣的是，一个数A如果按2到A-1进制表达时，各个位数之和的均值是多少？她希望你能帮她解决这个问题？ 
所有的计算均基于十进制进行，结果也用十进制表示为不可约简的分数形式。 

输入描述:
输入中有多组测试数据，每组测试数据为一个整数A(1 ≤ A ≤ 5000).

输出描述:
对每组测试数据，在单独的行中以X/Y的形式输出结果。

输入例子1:
5
3

输出例子1:
7/3
2/1
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        while (sc.hasNext()) { 
            int num = sc.nextInt();
            int sum = calculate(num);
            // 分子分母同时除以最大公约数， 化成最简形式
            System.out.printf("%d/%d\n", sum / GCD(sum, num-2), (num-2) / GCD(sum, num-2));
        }

    }
    // 求解两个数的最大公约数
    public static int GCD(int a, int b) {
        int temp = 0;
        if (a < b) {
            temp = a;
            a = b;
            b = temp;
        }
        int c = a % b;
        if (c == 0) {
            return b;
        } else {
            return GCD(b, c);
        }
    }
    
    // 对每个数的 2 到 n-1 进制的位数进行求和
    public static int calculate(int n) {
        int radix = n - 1;
        int sum = 0;
        while (radix > 1) {
            sum += averageA(radix, n );
            radix--;
        }

        return sum;
    }
    
    //对一个数的某一进制的数字进行求和
    public static int averageA(int radix, int n) {
        int sumOfN = 0;
        while (n > 0) {
            sumOfN += (n % radix);
            n /= radix;
        }
        return sumOfN;
    }
}

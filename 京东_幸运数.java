该题是 京东_进制均值 这道题的一个简化版本。
该题中我们只需要求出 二进制 和 十进制 各个位数上的和，然后进行比较即可。
而一个数的 k进制 各个位数上的求和方法，我们已经在 京东_进制均值 这道里面写过了。
直接拿过来使用即可。

总体而言，这是一道水题，大家知道这个方法就行了。
(二进制求和 & 十进制求和 其实都是一回事，同一个方法即可搞定，千万别犯二去写两个方法...)

/*
小明同学学习了不同的进制之后，拿起了一些数字做起了游戏。
小明同学知道，在日常生活中我们最常用的是十进制数，而在计算机中，二进制数也很常用。
现在对于一个数字x，小明同学定义出了两个函数 f(x) 和 g(x)。 
f(x)表示把x这个数用十进制写出后各个数位上的数字之和。
如f(123)=1+2+3=6。 g(x)表示把x这个数用二进制写出后各个数位上的数字之和。
如123的二进制表示为1111011，那么，g(123)=1+1+1+1+0+1+1=6。 
小明同学发现对于一些正整数x满足f(x)=g(x)，他把这种数称为幸运数，现在他想知道，小于等于n的幸运数有多少个？ 

输入描述:
每组数据输入一个数n(n<=100000)

输出描述:
每组数据输出一行，小于等于n的幸运数个数。

输入例子1:
21

输出例子1:
3
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            int n = sc.nextInt();
            int count = 0;
            while(n > 0) {
                if(solution(n, 10) == solution(n, 2)) {
                    count++;
                }
                n--;
            }
            System.out.println(count);
        }
        sc.close();
    }
    
    // 把 n 转成 k 进制数,并将每一位上的数相加起来
    public static int solution(int n, int radix) {
        int sum = 0;
        while(n > 0) {
            sum += n % radix;
            n /= radix;
        }
        return sum;
    }
}
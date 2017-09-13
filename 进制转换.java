该题目可以通过直接调用 Java 自带的进制转换方法来实现。
并且可以进一步扩展为：
	1. 十进制数转 N 进制数。
		1.1 解决方法1：打表解决
		1.2 解决方法2：调用 Java 中 BigInteger 类自带的方法
	2. M进制 转 N进制
		直接调用 Native Method 解决

/*
写出一个程序，接受一个十六进制的数值字符串，输出该数值的十进制字符串。（多组同时输入 ）
*/

import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        while(sc.hasNext())
        {
            String s = sc.next().substring(2);
            System.out.println(Integer.parseInt(s, 16));
        }
         
    }
}

/*
扩张问题 1
给定一个十进制数M，以及需要转换的进制数N。将十进制数 M 转化为 N 进制数 

输入描述:
输入为一行，M(32位整数)、N(2 ≤ N ≤ 16)，以空格隔开。

输出描述:
为每个测试实例输出转换后的数，每个输出占一行。如果N大于9，则对应的数字规则参考16进制（比如，10用A表示，等等）

示例
输入
7 2

输出
111
*/

// Solution 1: 打表
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        int radix = sc.nextInt();
		// 打表
        char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // 注意对负数进行处理
		boolean negative = false;
        if (num < 0) {
			negative = true;
		}
        StringBuilder sb = new StringBuilder();
        num = Math.abs(num);
        while (num >= radix) {
            sb.insert(0, chars[num % radix]);
            num /= radix;
        }
        sb.insert(0, chars[num]);
        System.out.println((negative ? "-" : "") + sb.toString());
    }
}

// Solution 2: 调用 Native Method
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String m = sc.next();
        int n = sc.nextInt();
        BigInteger bi = new BigInteger(m,10);
        System.out.println(bi.toString(n).toUpperCase());
    }
}


/*
扩张问题 2：M进制 转 N进制

将M进制的数X转换为N进制的数输出。 

输入描述:
输入的第一行包括两个整数：M和N(2<=M,N<=36)。
下面的一行输入一个数X，X是M进制的数，现在要求你将M进制的数X转换成N进制的数输出。

输出描述:
输出X的N进制表示的数。
输入时字母部分为大写，输出时为小写，并且有大数据。

示例
输入
16 10
F

输出
15
*/

/*
直接使用了BigInteger类型，因为BigInteger有这样的数据结构： 
BigInteger(String val, int radix)
将指定基数的 BigInteger 的字符串表示形式转换为 BigInteger。 

toString方法： 
toString(int radix)
返回此 BigInteger 的给定基数的字符串表示形式。 
再根据题目要求，把string变成小写就好了。
*/

import java.util.Scanner;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {   
        change();
    }
 
    public static void change() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int M = sc.nextInt();
            int N = sc.nextInt();
            String val = sc.next();
            BigInteger big = new BigInteger(val, M);
            String str = big.toString(N).toLowerCase();
            System.out.println(str);
        }
    }
}

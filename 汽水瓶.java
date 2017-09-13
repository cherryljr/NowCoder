解法1：
通过数学分析，最后获得的饮料数是总空瓶数整除2 。
解法2：
普通解法，通过简单的递归调用解决。

/*
有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。
小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶.
方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，
这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。
如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？ 
*/

// Soluton1: Math Analysis
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n;
        while(sc.hasNext())
        {
            n = sc.nextInt();
            System.out.println(Drink(n));
        }
         
    }
     
    public static int Drink(int n)
    {
        if(n <= 0) {
            return 0;
        }
        else {
            return n / 2;
        }
    }
}

// Solution 2: Recursion
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n;
        while(sc.hasNext())
        {
            n = sc.nextInt();
            System.out.println(Drink(n));
        }
         
    }
     
    public static int Drink(int n)
    {
        if(n <= 0) {
			return 0;
		}
        else if (n == 3) {
            return 1;
		}
        else if (n == 2) {
            return 1;
		}
        else // 此时表明对应为 3 的倍数，递归
        {
            int h = 0;
            h = n / 3;
            return h + Drink(n - 3 * h + h);
        }
             
         
    }
}
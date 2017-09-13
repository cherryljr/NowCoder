Josephus 问题
直接利用数学公式计算出来即可。
注意：
题目是从 0 开始报数，而调用的 Josephus 函数报数最少是从 1 开始的，故答案需要 -1 即可。

/*
有一个数组a[N]顺序存放0~N-1，要求每隔两个数删掉一个数，到末尾时循环至开头继续进行，求最后一个被删掉的数的原始下标位置。
以8个数(N=7)为例:｛0，1，2，3，4，5，6，7｝，0->1->2(删除)->3->4->5(删除)->6->7->0(删除),如此循环直到最后一个数被删除。

输入描述:
每组数据为一行一个整数n(小于等于1000)，为数组成员数,如果大于1000，则对a[999]进行计算。

输出描述:
一行输出最后一个被删掉的数的原始下标位置。

输入例子1:
8

输出例子1:
6
*/

import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n > 1000) {
                n = 999;
            }
            
            System.out.println(Josephus(n, 3, 1) - 1);
        }
    }
    
    /** 
     * Josephus 环的一个O(N)算法 
     *  
     * @param n 总人数 
     * @param m 数到m的人出列 
     * @param k 开始报数人的编号 
     * @return 最后一个出列的编号 
     */ 
    public static int Josephus(int n, int m, int k) {  
        int ans = 0;  
        for (int i = 2; i <= n; i++) {  
            ans = (ans + m) % i;  
        }  
		// 返回最后一人的位置  
        return ans + k == n ? n : (ans + k) % n; 
    } 
}




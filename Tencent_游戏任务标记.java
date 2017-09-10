/**
 * 思路：1024=32*32,因此可用 32 个整数表示 1024 位 (因为每个整数32位)
 * 因为任务ID范围是1~1024,所以减1转化为 0~1023
 * 然后任务ID除以32，商为存到哪个整数，余数为该整数对应位（置1即可）
 * 注：用位运算进行 除 操作时， a >> n 表示a除以2^n.
 * 用位运算进行 取余 操作时，a&(2^n-1) 表示a对(2^n)取余
 * 因此除以32相当于直接右移5位，对32取余相当于 "&31"（这个技巧只适用于 2^n 数）．
 */

/*
游戏里面有很多各式各样的任务，其中有一种任务玩家只能做一次，这类任务一共有1024个，任务ID范围[1,1024]。
请用32个unsigned int类型来记录着1024个任务是否已经完成。初始状态都是未完成。
输入两个参数，都是任务ID，需要设置第一个ID的任务为已经完成；并检查第二个ID的任务是否已经完成。
输出一个参数，如果第二个ID的任务已经完成输出1，如果未完成输出0。如果第一或第二个ID不在[1,1024]范围，则输出-1。 

输入描述:
输入包括一行,两个整数表示人物ID.

输出描述:
输出是否完成

示例1
输入
1024 1024

输出
1
*/

import java.util.*;

public class Main { 
    //定义long型，但只用前32位，java没有unsigned int
    static long[] arr = new long[32]; 
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int set = sc.nextInt() - 1;
            int find = sc.nextInt() - 1;
            System.out.println(Find(set,find));
        }
        sc.close();
    }
    
    private static int Find(int set, int find){
        if(set >= 1024 || find >= 1024) {
            return -1;
        }
         
        arr[set >> 5] |= (1 << (set & 31));
        if((arr[find >> 5] & (1 << (find & 31))) != 0) {
            return 1;
        }
        return 0;
    }
}

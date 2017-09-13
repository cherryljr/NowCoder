题目要求：去重 + 排序
毫无疑问 TreeSet 该数据结构完美符合要求。直接使用该数据结构即可。

/*
明明想在学校中请一些同学一起做一项问卷调查，为了实验的客观性，他先用计算机生成了N个1到1000之间的随机整数（N≤1000），
对于其中重复的数字，只保留一个，把其余相同的数去掉，不同的数对应着不同的学生的学号。
然后再把这些数从小到大排序，按照排好的顺序去找同学做调查。请你协助明明完成“去重”与“排序”的工作。
 
Input Param 
     n               输入随机数的个数     
 inputArray      n个随机整数组成的数组 
     
Return Value
     OutputArray    输出处理后的随机整数
 
注：测试用例保证输入参数的正确性，答题者无需验证。测试用例不止一组。
*/

import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        while(sc.hasNext())
        {
            n = sc.nextInt();
            if (n > 0) {
                TreeSet<Integer> ts = new TreeSet<>();
                for (int i = 0; i < n; i++) {
                    ts.add(sc.nextInt());
                }
                for (int i : ts) {
                    System.out.println(i);
                }
            }
        }
         
    }
}
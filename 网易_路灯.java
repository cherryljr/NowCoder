这个题目的实质就是求路灯之间的 最大间隔 。
只需要注意的是：
    1. 第一盏路灯 和 最后一盏路灯 与道路的两边尽头距离就是d.
    2. 中间部分，两盏路灯之间的距离 / 2 = d
故只需要对 路灯的位置 进行排序即可。

/*
题目： 
一条长l的笔直的街道上有n个路灯，若这条街的起点为0，终点为l，第i个路灯坐标为ai ，
每盏灯可以覆盖到的最远距离为d，为了照明需求，所有灯的灯光必须覆盖整条街，但是为了省电，要是这个d最小，
请找到这个最小的d。 

输入： 
每组数据第一行两个整数n和l（n大于0小于等于1000，l小于等于1000000000大于0）。
第二行有n个整数(均大于等于0小于等于l)，为每盏灯的坐标，多个路灯可以在同一点。
7 15
15 5 3 7 9 14 0
1
2

输出： 
输出答案，保留两位小数。
2.50
*/

import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int l = sc.nextInt();
        int[] lights = new int[n];
        
        for (int i = 0; i < n; i++) {
            lights[i] = sc.nextInt();
        }
        Arrays.sort(lights);
        
        double max = Math.max(lights[0], l - lights[n - 1]);
        for (int i = 0; i < n - 1; i++) {
            max = (float) Math.max(max, (lights[i + 1] - lights[i]) / 2.0);
        }
        System.out.printf("%.2f", max);
    }
    

}
/*
咱们来玩一笔画游戏吧，规则是这样的：有一个连通的图，能否找到一个恰好包含了所有的边，并且没有重复的路径。

输入描述:
输入包含多组数据。
每组数据的第一行包含两个整数n和m (2≤n, m≤1000)，其中n是顶点的个数，m是边的条数。
紧接着有m行，每行包含两个整数from和to (1 ≤ from, to ≤ n, from != to)，分别代表边的两端顶点。
边是双向的，并且两个顶点之间可能不止一条边。

输出描述:
对应每一组输入，如果能一笔画则输出“Yes”；否则输出“No”。

示例1
输入
3 3
1 2
2 3
1 3
4 7
1 2
2 1
1 3
1 4
1 4
2 3
4 3
输出
Yes
No
 */

/**
 * Approach: 欧拉回路问题
 * 欧拉回路问题（七桥问题），如果一个联通图，奇数点的个数为0或者2的时候，那么一定存在一个欧拉回路。
 * 小学奥数题...应该都见过没啥好说的了...
 *
 * 对此想要有更进一步了解的可以参考：
 *  https://blog.csdn.net/u011466175/article/details/18825453
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt(), m = sc.nextInt();
            int[] points = new int[n];
            for (int i = 0; i < m; i++) {
                points[sc.nextInt() - 1]++;
                points[sc.nextInt() - 1]++;
            }

            // Check the edges of each point
            int count = 0;
            for (int i = 0; i < n; i++) {
                if ((points[i] & 1) == 1) {
                    count++;
                }
            }
            System.out.println((count == 0 || count == 2) ? "Yes" : "No");
        }
    }
}
/*
题目: Counting Islands II
    时间限制:10000ms
    单点时限:1000ms
    内存限制:256MB
描述
Country H is going to carry out a huge artificial islands project.
The project region is divided into a 1000x1000 grid. The whole project will last for N weeks.
Each week one unit area of sea will be filled with land.

As a result, new islands (an island consists of all connected land in 4 -- up, down, left and right -- directions) emerges in this region.
Suppose the coordinates of the filled units are (0, 0), (1, 1), (1, 0). Then after the first week there is one island:
#...
....
....
....
After the second week there are two islands:
#...
.#..
....
....
After the three week the two previous islands are connected by the newly filled land and thus merge into one bigger island:
#...
##..
....
....
Your task is track the number of islands after each week's land filling.

输入
The first line contains an integer N denoting the number of weeks. (1 ≤ N ≤ 100000)
Each of the following N lines contains two integer x and y denoting the coordinates of the filled area.  (0 ≤ x, y < 1000)

输出
For each week output the number of islands after that week's land filling.

样例输入
3
0 0
1 1
1 0
样例输出
1
2
1

OJ地址：https://hihocoder.com/contest/hiho183/problem/1
 */

/**
 * Approach: Union Find
 * LeetCode上的原题...没啥好说的了...
 * 详细解释可以参见：（有 HashMap 和 Array 两个版本）
 * https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Islands%20II.java
 */

import java.util.*;

public class Main {
    static int[] uf = new int[1000000];
    static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int count = 0;

    public static void main(String[] args) {
        Arrays.fill(uf, -1);
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x, y, index;
        for (int i = 0; i < n; i++) {
            x = sc.nextInt();
            y = sc.nextInt();
            index = x * 1000 + y;
            uf[index] = index;
            count++;

            for (int[] dir : dirs) {
                int nextX = x + dir[0];
                int nextY = y + dir[1];
                int nextP = nextX * 1000 + nextY;
                if (nextX < 0 || nextX >= 1000 || nextY < 0 || nextY >= 1000 || uf[nextP] == -1) {
                    continue;
                }

                int rootA = findFather(index);
                int rootB = findFather(nextP);
                if (rootA != rootB) {
                    uf[rootA] = rootB;
                    count--;
                }
            }
            System.out.println(count);
        }
    }

    private static int findFather(int index) {
        while (index != uf[index]) {
            uf[index] = uf[uf[index]];
            index = uf[index];
        }
        return index;
    }

}
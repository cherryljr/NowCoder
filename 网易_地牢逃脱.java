/*
题目描述
给定一个 n 行 m 列的地牢，其中 '.' 表示可以通行的位置，'X' 表示不可通行的障碍，
牛牛从 (x0 , y0 ) 位置出发，遍历这个地牢，和一般的游戏所不同的是，他每一步只能按照一些指定的步长遍历地牢，
要求每一步都不可以超过地牢的边界，也不能到达障碍上。
地牢的出口可能在任意某个可以通行的位置上。
牛牛想知道最坏情况下，他需要多少步才可以离开这个地牢。

输入描述:
每个输入包含 1 个测试用例。每个测试用例的第一行包含两个整数 n 和 m（1 <= n, m <= 50），表示地牢的长和宽。
接下来的 n 行，每行 m 个字符，描述地牢，地牢将至少包含两个 '.'。
接下来的一行，包含两个整数 x0, y0，表示牛牛的出发位置（0 <= x0 < n, 0 <= y0 < m，左上角的坐标为 （0, 0），出发位置一定是 '.'）。
之后的一行包含一个整数 k（0 < k <= 50）表示牛牛合法的步长数，
接下来的 k 行，每行两个整数 dx, dy 表示每次可选择移动的行和列步长（-50 <= dx, dy <= 50）

输出描述:
输出一行一个数字表示最坏情况下需要多少次移动可以离开地牢，如果永远无法离开，输出 -1。
以下测试用例中，牛牛可以上下左右移动，在所有可通行的位置.上，地牢出口如果被设置在右下角，
牛牛想离开需要移动的次数最多，为3次。

示例1
输入
3 3
...
...
...
0 1
4
1 0
0 1
-1 0
0 -1
输出
3
 */

/**
 * Approach: BFS
 * 撸码五分钟，看题两小时的典例...
 * 好吧...说过份了点。这道题目其实没有什么难度可言，让人头疼的是题目的表述...
 * 最开始看到 最多移动次数 的时候，楞了一下，以为是要用 DFS 枚举所有方法然后取个最大的。
 * 后面看到测试用例发现如果这么布局的话，并不是 3 啊...我可以蛇行走位...步数肯定大于三。
 *
 * 后来发现题目的关键点所在：出口不确定！！！看清楚了出口不确定！！！这个非常重要。
 * 这道题目真正想问的其实是：如何安放出口使得走出迷宫的步数要最多，求最多步数...
 * 因此，解法仍然是 BFS...我们只需要在最基础的 BFS 上多记录一个方向而言。
 * 用个 dirs 数组即可，反正都是 for 循环遍历，也不差多那么几次...
 * 至于 步数 问题，我们只需要在将 位置信息 加入到 queue 的时候，加上对应的 步数信息 即可。
 * 在这里我们为了方便直接将其一起封装在了数组中。
 * 最后，重要的是：因为要求最坏情况，所以如果能够将 出口 放在无法到达的地方的话，就一定要放在那里。
 * 因此 BFS 结束之后，我们遍历整个 maze 和 visited 看是否还有未被 visited 的点，并且该点能被到达 (maze[i][j] == '.')
 * 那么结果直接返回 -1 即可。否则返回最大步数 maxStep
 *
 * 关于 BFS 的应用解析可以参考：
 * https://github.com/cherryljr/LeetCode/blob/master/The%20Maze.java
 */

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int rows = sc.nextInt(), cols = sc.nextInt();
        char[][] maze = new char[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        // 建立迷宫
        for (int i = 0; i < rows; i++) {
            String str = sc.next();
            for (int j = 0; j < cols; j++) {
                maze[i][j] = str.charAt(j);
            }
        }
        // 开始位置
        int[] start = new int[]{sc.nextInt(), sc.nextInt()};
        // 合法的步伐
        int k = sc.nextInt();
        int[][] dirs = new int[k][2];
        for (int i = 0; i < k; i++) {
            dirs[i][0] = sc.nextInt();
            dirs[i][1] = sc.nextInt();
        }

        System.out.println(BFS(maze, visited, start, dirs));
    }

    private static int BFS(char[][] maze, boolean[][] visited, int[] start, int[][] dirs) {
        Queue<int[]> queue = new LinkedList<>();
        int maxStep = 0;
        queue.offer(new int[]{start[0], start[1], 0});
        visited[start[0]][start[1]] = true;

        // BFS
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            maxStep = Math.max(maxStep, curr[2]);
            for (int i = 0; i < dirs.length; i++) {
                int[] next = new int[]{curr[0] + dirs[i][0], curr[1] + dirs[i][1], curr[2] + 1};
                // 处理无法到达的情况 以及 已经遍历过的情况
                if (next[0] < 0 || next[0] >= maze.length || next[1] < 0 || next[1] >= maze[0].length
                        || visited[next[0]][next[1]] == true || maze[next[0]][next[1]] == 'X') {
                    continue;
                }
                queue.offer(next);
                visited[next[0]][next[1]] = true;
            }
        }

        // 最后再遍历一遍 maze 和 visited 是否还有未被 visited 的点，并且该点能被到达
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == '.' && visited[i][j] == false) {
                    return -1;
                }
            }
        }
        return maxStep;
    }
}
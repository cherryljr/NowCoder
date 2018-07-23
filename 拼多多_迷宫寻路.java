/*
时间限制：1秒
空间限制：131072K

假设一个探险家被困在了地底的迷宫之中，要从当前位置开始找到一条通往迷宫出口的路径。
迷宫可以用一个二维矩阵组成，有的部分是墙，有的部分是路。迷宫之中有的路上还有门，
每扇门都在迷宫的某个地方有与之匹配的钥匙，只有先拿到钥匙才能打开门。请设计一个算法，帮助探险家找到脱困的最短路径。
如前所述，迷宫是通过一个二维矩阵表示的，每个元素的值的含义如下 0-墙，1-路，2-探险家的起始位置，
3-迷宫的出口，大写字母-门，小写字母-对应大写字母所代表的门的钥匙

输入描述:
迷宫的地图，用二维矩阵表示。第一行是表示矩阵的行数和列数M和N
后面的M行是矩阵的数据，每一行对应与矩阵的一行（中间没有空格）。M和N都不超过100, 门不超过10扇。

输出描述:
路径的长度，是一个整数

示例1
输入
5 5
02111
01a0A
01003
01001
01111
输出
7
 */

/**
 * Approach: BFS
 * 和 LeetCode 上的 Shortest Path to Get All Keys 非常类似。
 * 利用 BFS 求最短路径即可，多了钥匙的信息，所以需要再开辟一维空间。
 * 因为迷宫的行列不会超过 100，钥匙不会多于 10 把。
 * 所以依然可以使用一个 整形 来表示所有状态。
 * 低10位表示钥匙状态；10~20位表示纵坐标；高位表示横坐标。
 *
 * 时间复杂度：O(m*n*2^1024)
 * 空间复杂度：O(m*n*2^1024)
 *
 * Shortest Path to Get All Keys:
 *  https://github.com/cherryljr/LeetCode/blob/master/Shortest%20Path%20to%20Get%20All%20Keys.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            char[][] maze = new char[m][n];
            for (int i = 0; i < m; i++) {
                String str = sc.next();
                for (int j = 0; j < n; j++) {
                    maze[i][j] = str.charAt(j);
                }
            }
            System.out.println(BFS(maze));
        }
        sc.close();
    }

    private static int BFS(char[][] maze) {
        int rows = maze.length, cols = maze[0].length;
        Queue<Integer> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][1024];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == '2') {
                    queue.offer((i << 20) | (j << 10));
                    visited[i][j][0] = true;
                    break;
                }
            }
        }

        int step = 0;
        final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                int row = curr >> 20 & 0x3ff, col = curr >> 10 & 0x3ff;
                int state = curr & 0x3ff;
                if (maze[row][col] == '3') {
                    return step;
                }

                for (int[] dir : DIRS) {
                    int nextRow = row + dir[0], nextCol = col + dir[1];
                    int nextState = state;
                    if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols || maze[nextRow][nextCol] == '0') {
                        continue;
                    }
                    if (Character.isUpperCase(maze[nextRow][nextCol]) && (state >> (maze[nextRow][nextCol] - 'A') & 1) == 0) {
                        continue;
                    }
                    if (Character.isLowerCase(maze[nextRow][nextCol])) {
                        nextState = state | (1 << maze[nextRow][nextCol] - 'a');
                    }
                    if (!visited[nextRow][nextCol][nextState]) {
                        queue.offer((nextRow << 20) | (nextCol << 10) | nextState);
                        visited[nextRow][nextCol][nextState] = true;
                    }
                }
            }
            step++;
        }

        return step;
    }

}
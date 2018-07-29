/*
时间限制：1秒
空间限制：32768K

大家一定玩过“推箱子”这个经典的游戏。具体规则就是在一个N*M的地图上，有1个玩家、1个箱子、1个目的地以及若干障碍，其余是空地。
玩家可以往上下左右4个方向移动，但是不能移动出地图或者移动到障碍里去。如果往这个方向移动推到了箱子，箱子也会按这个方向移动一格，
当然，箱子也不能被推出地图或推到障碍里。当箱子被推到目的地以后，游戏目标达成。现在告诉你游戏开始是初始的地图布局，
请你求出玩家最少需要移动多少步才能够将游戏目标达成。

输入描述:
每个测试输入包含1个测试用例
第一行输入两个数字N，M表示地图的大小。其中0<N，M<=8。
接下来有N行，每行包含M个字符表示该行地图。其中 . 表示空地、X表示玩家、*表示箱子、#表示障碍、@表示目的地。
每个地图必定包含1个玩家、1个箱子、1个目的地。

输出描述:
输出一个数字表示玩家最少需要移动多少步才能将游戏目标达成。当无论如何达成不了的时候，输出-1。

示例1
输入
4 4
....
..*@
....
.X..
6 6
...#..
......
#*##..
..##.#
..X...
.@#...

输出
3
11
 */

/**
 * Approach 1: BFS
 * 这道题目一开始把它想得太复杂了。觉得需要不断地去分析箱子和人的相对位置，然后看怎么推，能不能推。
 * 然而在写代码的过程中我们可以发现非常重要的一点：
 *  箱子的移动方向和人的移动方向是一致的。因此我们只需要对人的移动进行分析即可。
 * 在加入了箱子这个因素之后，人的移动结果对于箱子有两种情况：
 *  1. 人的下一步移动到了箱子上（人和箱子重合了），此时意味着箱子可以被推动，
 *  并且推动的方向与人的移动方向一致。然后我们再根据箱子的位置判断本次移动是否合法。
 *  2. 人的下一步并没有移动到箱子上，此时与普通情况下的 BFS 没有任何差别。
 *  只需要判断是否会走到障碍物上或者出界即可。
 * 为了在 BFS 过程中记录下状态信息，防止陷入死循环，我们需要一个 四维数组visited。
 * 分别记录 人的位置信息(x, y) 和 箱子的位置信息(bx, by)
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(n^4)
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int N = sc.nextInt(), M = sc.nextInt();
            char[][] maze = new char[N][M];
            for (int i = 0; i < N; i++) {
                maze[i] = sc.next().toCharArray();
            }

            System.out.println(minDistance(maze));
        }
        sc.close();
    }

    private static int minDistance(char[][] maze) {
        int rows = maze.length, cols = maze[0].length;
        int startX = 0, startY = 0, startBX = 0, startBY = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'X') {
                    startX = i;
                    startY = j;
                } else if (maze[i][j] == '*') {
                    startBX = i;
                    startBY = j;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][][][] visited = new boolean[rows][cols][rows][cols];
        queue.offer(new State(startX, startY, startBX, startBY));
        visited[startX][startY][startBX][startBY] = true;

        final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                State curr = queue.poll();
                if (maze[curr.bx][curr.by] == '@') {
                    return step;
                }

                for (int[] dir : DIRS) {
                    int nextX = curr.x + dir[0];
                    int nextY = curr.y + dir[1];
                    int nextBX = curr.bx;
                    int nextBY = curr.by;

                    // 人的下一步遇到障碍物或者走出界直接continue
                    if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols || maze[nextX][nextY] == '#') {
                        continue;
                    }
                    // 人的下一步位置与箱子重合，说明人的本次移动可以推动箱子，方向与人移动的方向相同
                    if (nextX == curr.bx && nextY == curr.by) {
                        nextBX = curr.bx + dir[0];
                        nextBY = curr.by + dir[1];
                        // 不能把箱子推出界或者推到障碍物中去
                        if (nextBX < 0 || nextBX >= rows || nextBY < 0 || nextBY >= cols || maze[nextBX][nextBY] == '#') {
                            continue;
                        }
                    }
                    // 如果当前状态还没记录过
                    if (!visited[nextX][nextY][nextBX][nextBY]) {
                        queue.offer(new State(nextX, nextY, nextBX, nextBY));
                        visited[nextX][nextY][nextBX][nextBY] = true;
                    }
                }
            }
            step++;
        }

        return -1;
    }

    static class State {
        int x, y;
        int bx, by;

        public State(int x, int y, int bx, int by) {
            this.x = x;
            this.y = y;
            this.bx = bx;
            this.by = by;
        }
    }
}

/**
 * Approach 2: BFS (Record the route)
 * 考虑到 Approach 1 中只给出了最佳步数，并没有给出最佳的走法（路径）。
 * 但反正都是 BFS 跑一边，把路径记录下来不也挺好的吗？（万一什么时候用到了呢）
 * 因此这边提供一个记录路径的做法。
 *
 * 代码几乎没有修改，只是在 State 中增加了一个 前向指针 prev. 用来指向上一步的状态信息。
 * 然后记录下每一步的走法。最后遍历一遍链表，将他们存储下来即可。
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int N = sc.nextInt(), M = sc.nextInt();
            char[][] maze = new char[N][M];
            for (int i = 0; i < N; i++) {
                maze[i] = sc.next().toCharArray();
            }

            System.out.println(minDistance(maze).size() - 1);
        }
        sc.close();
    }

    private static List<State> minDistance(char[][] maze) {
        int rows = maze.length, cols = maze[0].length;
        int startX = 0, startY = 0, startBX = 0, startBY = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'X') {
                    startX = i;
                    startY = j;
                } else if (maze[i][j] == '*') {
                    startBX = i;
                    startBY = j;
                }
            }
        }

        Queue<State> queue = new LinkedList<>();
        boolean[][][][] visited = new boolean[rows][cols][rows][cols];
        queue.offer(new State(startX, startY, startBX, startBY, null));
        visited[startX][startY][startBX][startBY] = true;

        State end = null;
        final int[][] DIRS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            if (maze[curr.bx][curr.by] == '@') {
                end = curr;
                break;
            }

            for (int[] dir : DIRS) {
                int nextX = curr.x + dir[0];
                int nextY = curr.y + dir[1];
                int nextBX = curr.bx;
                int nextBY = curr.by;

                // 人的下一步遇到障碍物或者走出界直接continue
                if (nextX < 0 || nextX >= rows || nextY < 0 || nextY >= cols || maze[nextX][nextY] == '#') {
                    continue;
                }
                // 人的下一步位置与箱子重合，说明人的本次移动可以推动箱子，方向与人移动的方向相同
                if (nextX == curr.bx && nextY == curr.by) {
                    nextBX = curr.bx + dir[0];
                    nextBY = curr.by + dir[1];
                    // 不能把箱子推出界或者推到障碍物中去
                    if (nextBX < 0 || nextBX >= rows || nextBY < 0 || nextBY >= cols || maze[nextBX][nextBY] == '#') {
                        continue;
                    }
                }
                // 如果当前状态还没记录过
                if (!visited[nextX][nextY][nextBX][nextBY]) {
                    queue.offer(new State(nextX, nextY, nextBX, nextBY, curr));
                    visited[nextX][nextY][nextBX][nextBY] = true;
                }
            }
        }

        // 路径结果
        List<State> rst = new LinkedList<>();
        while (end != null) {
            rst.add(0, end);
            end = end.prev;
        }
        return rst;
    }

    static class State {
        int x, y;
        int bx, by;
        State prev;     // 指向前一个状态信息

        public State(int x, int y, int bx, int by, State prev) {
            this.x = x;
            this.y = y;
            this.bx = bx;
            this.by = by;
            this.prev = prev;
        }
    }
}

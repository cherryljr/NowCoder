/*
Time Limit: 1000MS		Memory Limit: 65536K

Description
There is going to be a party to celebrate the 80-th Anniversary of the Ural State University.
The University has a hierarchical structure of employees.
It means that the supervisor relation forms a tree rooted at the rector V. E. Tretyakov.
In order to make the party funny for every one,
the rector does not want both an employee and his or her immediate supervisor to be present.
The personnel office has evaluated conviviality of each employee, so everyone has some number (rating) attached to him or her.
Your task is to make a list of guests with the maximal possible sum of guests' conviviality ratings.

Input
Employees are numbered from 1 to N. A first line of input contains a number N. 1 <= N <= 6 000.
Each of the subsequent N lines contains the conviviality rating of the corresponding employee.
Conviviality rating is an integer number in a range from -128 to 127.
After that go N – 1 lines that describe a supervisor relation tree.
Each line of the tree specification has the form:
L K
It means that the K-th employee is an immediate supervisor of the L-th employee. Input is ended with the line
0 0

Output
Output should contain the maximal sum of guests' ratings.

Sample Input
7
1
1
1
1
1
1
1
1 3
2 3
6 4
7 4
4 5
3 5
0 0

Sample Output
5
 */

/**
 * Approach 1: 树形DP
 * 这道题目作为 树形DP 的入门题还是非常简单的。
 * 正如其字面意思，我们是在一棵树上进行 DP.
 * 树形DP 的难点主要在于状态的分析，而在实现上实际是非常简单的。
 * 其主要的思想就是：Divide and Conquer.
 * 只要是树，基本都可以用这个思想来解决。
 *
 * 树形DP的做法总结就是：
 *  1. 收集子节点的状态信息
 *  2. 根据子节点的状态信息推出当前节点的信息
 *  3. 然后不断向上递推
 * 介于以上的处理方法，因此我们通常都是通过 递归 的方法来进行编程。
 * 当前节点状态由子节点觉得，递归求解下去即可。
 *
 * 本道题的状态分析还是非常简单的：
 *  当前员工 来 或者 不来。
 *  如果来的话，其手下员工都不能到场；
 *  如果不来的话，其手下员工到不到场均可。我们只需要取活跃值较大的策略即可。
 *
 * 通过以上分析，我们可以发现平时我们所接触到的许多题目都是树形DP（是不是突然觉得并没有那么难了）
 * Binary Tree Maximum Path Sum： https://github.com/cherryljr/LintCode/blob/master/Binary%20Tree%20Maximum%20Path%20Sum.java
 * Balanced Binary Tree： https://github.com/cherryljr/LintCode/blob/master/Balanced%20Binary%20Tree.java
 * The Biggest Score On The Tree： https://github.com/cherryljr/LintCode/blob/master/The%20Biggest%20Score%20On%20The%20Tree.java
 *
 * 所以，解决 Tree 类的题目，除了遍历什么的，Divide and Conquer 是非常常见而又好用的利器！！！
 * 比较 树 这个结构实在太适合进行分治了。
 * （对于分治法，我们一般都是用递归来写）
 */

import java.io.BufferedInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Node {
        int rate;
        List<Node> child;

        public Node(int rate) {
            this.rate = rate;
            this.child = new LinkedList<>();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        Node[] nodes = new Node[n + 1];
        // 初始化各个员工的 活跃度
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(sc.nextInt());
        }
        // 建立多叉树
        int l, k;
        boolean[] father = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            l = sc.nextInt();
            k = sc.nextInt();
            if (l == 0 && k == 0) {
                break;
            }
            father[l] = true;
            nodes[k].child.add(nodes[l]);
        }

        // Get the root node
        Node root = null;
        for (int i = 1; i <= n; i++) {
            if (father[i]) {
                continue;
            }
            root = nodes[i];
        }

        int[] rst = maxRate(root);
        System.out.println(Math.max(rst[0], rst[1]));
    }

    private static int[] maxRate(Node root) {
        // rst[0] 表示不来的活跃度，rst[1]表示来的活跃度
        int[] rst = new int[2];
        rst[1] = root.rate;
        // 遍历其所有子节点，根据处理得到的信息进行 Conquer
        for (Node child : root.child) {
            // 获得子节点信息（递归调用）
            int[] childRst = maxRate(child);
            // 如果当前员工 不参加，则其属下员工来或者不来都可以，取二者的较大值
            rst[0] += Math.max(childRst[0], childRst[1]);
            // 如果当前员工 参加，则其属下员工必定不会参加
            rst[1] += childRst[0];
        }
        return rst;
    }
}

/**
 * Approach 2: 树形DP (Optimize Space)
 * Approach 1 为了使得大家更加容易理解，我将各个信息处理成了一个多叉树。
 * 但实际上该题使用 数据 即可解决。
 * 具体实现请看代码以及注释
 */

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        // matrix[i][0] 代表第 i 位员工的上司是谁
        // matrix[i][1] 代表第 i 位员工自身的活跃度
        int[][] matrix = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            matrix[i][1] = sc.nextInt();
        }
        int l, k;
        for (int i = 1; i <= n; i++) {
            l = sc.nextInt();
            k = sc.nextInt();
            if (l == 0 && k == 0) {
                break;
            }
            matrix[l][0] = k;
        }

        //Get the root node
        int root = 0;
        for (int i = 1; i <= n; i++) {
            if (matrix[i][0] == 0) {
                root = i;
                break;
            }
        }

        boolean[] visited = new boolean[n + 1];
        // dp[i][0] 代表第 i 位员工未到场的最高活跃度
        // dp[i][1] 代表第 i 位员工到场的最高活跃度
        int[][] dp = new int[n + 1][2];
        maxRate(matrix, dp, visited, root);
        System.out.println(Math.max(dp[root][0], dp[root][1]));
    }

    private static void maxRate(int[][] matrix, int[][] dp, boolean[] visited, int root) {
        visited[root] = true;
        dp[root][1] = matrix[root][1];
        for (int i = 1; i < matrix.length; i++) {
            // 如果第 i 位员工是 root 的下属，那么我们先收集他的信息，从而推算出 root 的信息
            if (!visited[i] && matrix[i][0] == root) {
                // 递归处理下属信息
                maxRate(matrix, dp, visited, i);
                dp[root][0] += Math.max(dp[i][0], dp[i][1]);
                dp[root][1] += dp[i][0];
            }
        }
    }

}
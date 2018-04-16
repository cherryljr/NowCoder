/*
#1515 : 分数调查
时间限制:10000ms
单点时限:1000ms
内存限制:256MB

描述
小Hi的学校总共有N名学生，编号1-N。学校刚刚进行了一场全校的古诗文水平测验。
学校没有公布测验的成绩，所以小Hi只能得到一些小道消息，例如X号同学的分数比Y号同学的分数高S分。
小Hi想知道利用这些消息，能不能判断出某两位同学之间的分数高低？

输入
第一行包含三个整数N, M和Q。N表示学生总数，M表示小Hi知道消息的总数，Q表示小Hi想询问的数量。
以下M行每行三个整数，X, Y和S。表示X号同学的分数比Y号同学的分数高S分。
以下Q行每行两个整数，X和Y。表示小Hi想知道X号同学的分数比Y号同学的分数高几分。
对于50%的数据，1 <= N, M, Q <= 1000
对于100%的数据，1 <= N, M, Q<= 100000 1 <= X, Y <= N -1000 <= S <= 1000
数据保证没有矛盾。

输出
对于每个询问，如果不能判断出X比Y高几分输出-1。否则输出X比Y高的分数。

样例输入
10 5 3
1 2 10
2 3 10
4 5 -10
5 6 -10
2 5 10
1 10
1 5
3 5

样例输出
-1
20
0
 */

/**
 * Approach: Union Find
 * 一旦两个学生通过 消息M 建立起连接，我们就可以通过这个消息来判断二者的分差。
 * 并且该信息是具有 传递性 的，即得知了 A,B 和 B,C 的关系后，我们可以推出 A,C 的关系。
 * 即相当于 A,B,C 形成一个连通区，我们可以得知连通区内任意两点之间的分差。
 *
 * 因此这道题目是一个 带权并查集 的考察。
 * 当两位学生在同一个连通区内（具有同一个 parent）时，他们是可以被比较的，否则无法被比较。
 * 权值数组 rank[i] 定义为：学生 ith 低于 parent 学生分数的分值大小，即 rank[i] = iParent - i
 * Union(a, b) 的时候，b所属的区域 成为 a所属区域 的子节点，同时我们也要计算出 bFather 的 rank 值。
 *      parent[bFather] = aFather;
 *      rank[bFather] = rank[a] - rank[b] + diff;
 * 关于 rank[bFather] 的计算我们可以做如下分析：
 *  rank[a] = aFather - a;  rank[b] = bFather - b;  diff = a - b
 *  而我们要求的 rank[bFather] = aFather - bFather
 *  那么 rank[a] - rank[b] = aFather - bFather - a + b;
 *  经过等式变换后可得：rank[bFather] = aFather - bFather = rank[a] - rank[b] + diff
 * 
 * 类似问题：
 * https://github.com/cherryljr/LintCode/blob/master/Deliver%20The%20Message.java
 */

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int q = sc.nextInt();
        UnionFind uf = new UnionFind(n + 1);

        int a, b, diff;
        for (int i = 0; i < m; i++) {
            a = sc.nextInt();
            b = sc.nextInt();
            diff = sc.nextInt();
            uf.union(a, b, diff);
        }
        for (int i = 0; i < q; i++) {
            a = sc.nextInt();
            b = sc.nextInt();
            if (uf.compressedFind(a) == uf.compressedFind(b)) {
                System.out.println(uf.rank[b] - uf.rank[a]);
            } else {
                System.out.println(-1);
            }
        }

        sc.close();
    }

    static class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int compressedFind(int index) {
            // 考虑到 rank[] 的影响，此处使用了 递归 写法
            // 大家也可以使用 Stack 改为非递归版本
            if (index == parent[index]) {
                return index;
            } else {
                // 预先保存 parent[index],以便 rank[index] 调用，不然之后会发生变化
                int pre = parent[index];    
                parent[index] = compressedFind(parent[index]);
                rank[index] = rank[index] + rank[pre];
                return parent[index];
            }
        }

        public void union(int a, int b, int diff) {
            int aFather = compressedFind(a);
            int bFather = compressedFind(b);
            if (aFather != bFather) {
                // 将 b 归入到 a 所属的区域中
                parent[bFather] = aFather;
                rank[bFather] = rank[a] - rank[b] + diff;
            }
        }
    }
}
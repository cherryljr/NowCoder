/*
Description
Luke wants to upgrade his home computer network from 10mbs to 100mbs.
His existing network uses 10base2 (coaxial) cables that allow you to connect any number of computers together in a linear arrangement.
Luke is particulary proud that he solved a nasty NP-complete problem in order to minimize the total cable length.
Unfortunately, Luke cannot use his existing cabling. The 100mbs system uses 100baseT (twisted pair) cables.
Each 100baseT cable connects only two devices: either two network cards or a network card and a hub.
(A hub is an electronic device that interconnects several cables.) Luke has a choice:
He can buy 2N-2 network cards and connect his N computers together by inserting one or more cards into each computer and connecting them all together.
Or he can buy N network cards and a hub and connect each of his N computers to the hub.
The first approach would require that Luke configure his operating system to forward network traffic.
However, with the installation of Winux 2007.2, Luke discovered that network forwarding no longer worked.
He couldn't figure out how to re-enable forwarding, and he had never heard of Prim or Kruskal,
so he settled on the second approach: N network cards and a hub.
Luke lives in a loft and so is prepared to run the cables and place the hub anywhere.
But he won't move his computers. He wants to minimize the total length of cable he must buy.

Input
The first line of input contains a positive integer N <= 100, the number of computers. N lines follow;
each gives the (x,y) coordinates (in mm.) of a computer within the room. All coordinates are integers between 0 and 10,000.
Output
Output consists of one number, the total length of the cable segments, rounded to the nearest mm.

Sample Input
4
0 0
0 10000
10000 10000
10000 0

Sample Output
28284
 */

/**
 * Approach 1: 模拟退火(Simulated Anneal)
 * 题目大意：
 * 求n边形的费马点，即找到一个点使得这个点到n个点的距离之和最小。
 *
 * 对于这个问题我们可以使用 模拟退火 来获得全局最优解。
 * 模拟退火中的移动方法，退火的幅度还需要根据不同的题目来确定。
 * 这里的移动方法为分别朝 8个方向 进行尝试，幅值最初为 100（初始温度）
 * 每次退火幅度为 0.01，最终温度为 0.02
 * 
 * 模拟退火算法其特点是在开始搜索阶段解的质量提高比较缓慢，但是到了迭代后期，它的解的质量提高明显，
 * 所以如果在求解过程中，对迭代步数限制比较严格的话，模拟退火算法在有限的迭代步数内很难得到高质量的解。
 * 总体而言模拟退火算法比较适合用于有充足计算资源的问题求解。
 *
 * 模拟退火详解与 TSP 问题实现
 *  https://blog.csdn.net/bbbeoy/article/details/79081459
 *  https://blog.csdn.net/tyhj_sf/article/details/53447731
 * 利用物理学知识解决寻找费曼点的问题
 *  http://www.matrix67.com/blog/archives/422
 */

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    private static final int[][] DIRS = new int[][]{{0, -1}, {-1, -1}, {-1, 0}, {-1, 1},
            {0, 1}, {1, 1}, {1, 0}, {1, -1}};
    // Set initial temperature (step is same as currentTemperature here)
    private static double step = 100;
    // minimal temperature to cool (eps is same as minTemperature here)
    private static double eps = 0.02;
    // Cooling Rate (rate is same as CoolingRate  here)
    private static double rate = 0.01;

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        Point fermatP = points[0], point;
        double rst = getDistanceSum(points[0], points);
        while (step > eps) {
            for (int[] dir : DIRS) {
                point = new Point(fermatP.x + step * dir[0], fermatP.y + step * dir[1]);
                double tempSum = getDistanceSum(point, points);
                if (tempSum < rst || Math.exp((rst - tempSum) / step) > Math.random()) {
                    rst = tempSum;
                    fermatP = point;
                }
            }
            step *= (1 - rate);
        }

        System.out.printf("%.0f\n", rst);
    }

    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getDistance(Point other) {
            return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
        }
    }

    private static double getDistanceSum(Point p, Point[] points) {
        double rst = 0.0;
        for (Point other : points) {
            rst += p.getDistance(other);
        }
        return rst;
    }
}

/**
 * Approach 2: 模拟退火(Simulated Anneal)
 * 这道题目利用 模拟退火 还有另外一种做法，与第一种解法主要差在每次 步长的缩小幅度。
 * 不过这个做法其实我也不确定是不是模拟退火，但是很好用是真的...
 * 
 * 三角形也有费马点，三角形费马点是这样定义的：寻找三角形内的一个点，使得三个顶点到该点的距离之和最小。
 * 三角形费马点的做法是：
 *  （1）若有一个角大于120度，那么这个角所在的点就是费马点。
 *  （2）若不存在，那么对于三角形ABC，任取两条边（假设AB、AC），向外做等边三角形得到C' 和 A'  ，那么AA' 和CC' 的交点就是费马点。
 *
 * 而对于这题 n多边形 ，所采取的策略完全不同，采用了模拟退火的做法，这种做法相对比较简单，也可以用在求三角形费马点之中。
 * 模拟退火算法就跟数值算法里面的自适应算法相同的道理
 * （1）定义好步长  step
 * （2）寻找一个起点，往8个方向的按这个步长搜索。  points[0]
 * （3）如果找到比答案更小的点，那么以这个新的点为起点，重复（2）
 * （4）如果找不到比答案更小的点，那么步长减半(rate)，再尝试（2）
 * （5）直到步长小于要求的答案的精度(eps)就停止。
 */

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    private static final int[][] DIRS = new int[][]{{0, -1}, {-1, -1}, {-1, 0}, {-1, 1},
            {0, 1}, {1, 1}, {1, 0}, {1, -1}};
    // Set initial temperature (step is same as currentTemperature here)
    private static double step = 100;
    // minimal temperature to cool (eps is same as minTemperature here)
    private static double eps = 0.02;
    // Cooling Rate (rate is same as CoolingRate  here)
    private static double rate = 0.5;

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        int n = sc.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }

        Point fermatP = points[0], point;
        double rst = getDistanceSum(points[0], points);
        while (step > eps) {
            boolean flag = false;
            for (int[] dir : DIRS) {
                point = new Point(fermatP.x + step * dir[0], fermatP.y + step * dir[1]);
                double tempSum = getDistanceSum(point, points);
                if (tempSum < rst) {
                    rst = tempSum;
                    fermatP = point;
                    flag = true;
                }
            }
            if (!flag) {
                step *= rate;
            }
        }

        System.out.printf("%.0f\n", rst);
    }

    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getDistance(Point other) {
            return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
        }
    }

    private static double getDistanceSum(Point p, Point[] points) {
        double rst = 0.0;
        for (Point other : points) {
            rst += p.getDistance(other);
        }
        return rst;
    }
}


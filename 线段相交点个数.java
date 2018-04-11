/*
Problem Description
Many geometry（几何）problems were designed in the ACM/ICPC.
And now, I also prepare a geometry problem for this final exam.
According to the experience of many ACMers, geometry problems are always much trouble,
but this problem is very easy, after all we are now attending an exam, not a contest :)
Give you N (1<=N<=100) segments（线段）, please output the number of all intersections（交点）.
You should count repeatedly if M (M>2) segments intersect at the same point.

Note:
You can assume that two segments would not intersect at more than one point.

Input
Input contains multiple test cases. Each test case contains a integer N (1=N<=100) in a line first, and then N lines follow.
Each line describes one segment with four float values x1, y1, x2, y2 which are coordinates of the segment’s ending.
A test case starting with 0 terminates the input and this test case is not to be processed.

Output
For each case, print the number of intersections, and one line one case.

Sample Input
2
0.00 0.00 1.00 1.00
0.00 1.00 1.00 0.00
3
0.00 0.00 1.00 1.00
0.00 1.00 1.00 0.000
0.00 0.00 1.00 0.00
0

Sample Output
1
3

OJ 地址: http://acm.hdu.edu.cn/showproblem.php?pid=1086
 */

/**
 * Approach: Using Cross Product
 * 要处理几何题，我们不得不涉及到 叉积 和 点积，而判断 线段相交 就需要要用到叉积（外积）。
 * 首先我们来讲讲相交的形式：
 * 说到线段，我们很自然想到直线，判断两条直线是否相交只需判断它们 斜率是否相等 ，相等就为平行或重合，不等就相交。
 * （注：判断相交我们不采用除法，因为除法容易产生浮点误差，当两条直线斜率接近时，很容易出错。事实上，几乎所有几何题都不建议采用除法）。
 *
 * 线段相交有两种形式：
 *  规范相交 和 非规范相交。区别就是交点是否是其中一条线段的端点，不是的是规范相交。
 *
 * 叉积的概念：设向量 a(x1, y1), b(x2, y2);
 *      a x b = x1*y2 - x2*y1;
 * 判断线段是否相交主要就是判断异侧：
 * 我们以一条线段的一端点为起点，沿着线段方向看去（一条射线），在左手边为逆时针方向，右手边为顺时针方向。
 * 如果另一线段两端点分别在这一线段的两侧，那么线段可能相交（也可能在线段外），否则不可能相交。
 * 对于可能相交的情况，对另一线段采用相同的方法就可判断出是否相交了。
 *
 * 这个过程主要通过叉积来判断：
 *      叉积大于 0，在点在向量的顺时针方向；
 *      叉积小于 0，在逆时针方向；
 *      叉积等于 0，端点在直线上。
 * 具体实现：
 * 设：线段 line1: P1(x1, y1)、P2(x2, y2)  线段 line2: Q1(x3, y3)、Q2(x4, y4)
 *      crossp1 ====> (Q1 - P1) x (P2 - P1) (叉积)
 *      crossp2 ====> (Q2 - P1) x (P2 - P1) (叉积)
 *      crossp3 ====> (P1 - Q1) x (Q2 - Q1) (叉积)
 *      crossp4 ====> (P2 - P1) x (Q2 - Q1) (叉积)
 * 如果 crossp1 * crossp2 < 0  并且 crossp3 * crossp4 < 0 便可判断线段相交。
 * 如果有一个为 0，则根据相应的信息判断端点是否在另一线段上。
 *
 * 补充知识：
 * 向量 外积 与 内积 在 计算机图形 方面有着非常多的应用，下面便为大家列举一些：
 *  向量内积 (Dot Product):
 *      1. 判断两条直线是否垂直
 *      2. 计算 点与线段 之间的最短距离
 *  向量外积 (Cross Product):
 *      1. 判断路径是 顺时针 还是 逆时针
 *      2. 计算 点与直线 之间的最短距离： Math.abs((AB x AC)/|AB|)
 *      3. 判断多边形是 凸多边形 还是 凹多边形
 *      https://github.com/cherryljr/LeetCode/blob/master/Convex%20Polygon.java
 *		4. 计算 三角形 面积
 *		https://github.com/cherryljr/LeetCode/blob/master/Largest%20Triangle%20Area.java
 *      5. 计算 多边形 面积，将其分割成多个 三角形，并根据 外积 具有方向性(正负)，累加起来计算。
 *      （根据结果还能判断是 凸多边形 还是 凹多边形，属于 No.4 的 Fellow Up)
 *      6. 判断空间上 两条线段 是否 相交（本题）
 *      7. 判断一个点在一个多边形的 内部 还是 外部
 *      8. Convex Hull : https://leetcode.com/problems/erect-the-fence/description/
 *
 * 其他常见问题：
 *      1. 两条直线的交点。
 *      2. 求出 不共线的3点 所确定的圆及其半径 （利用垂直特性，k1 * k2 = -1)
 *      3. 点关于直线的对称点 （同样利用了垂直)
 *
 * Reference (Very Good Article):
 * https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-basic-concepts/#introduction
 */

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Main {
    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Line {
        Point point1, point2;

        public Line(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        while (sc.hasNext()) {
            int n = sc.nextInt();
            if (n == 0) {
                sc.close();
                return;
            }
            Line[] lines = new Line[n];
            for (int i = 0; i < n; i++) {
                lines[i] = new Line(new Point(sc.nextDouble(), sc.nextDouble()), new Point(sc.nextDouble(), sc.nextDouble()));
            }
            int count = 0;
            // 将每条线段 两两 组合进行计算
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (intersect(lines[i], lines[j])) {
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }

    // 判断 line1 与 line2 是否相交
    private static boolean intersect(Line line1, Line line2) {
        Point p1 = line1.point1, p2 = line1.point2;
        Point q1 = line2.point1, q2 = line2.point2;

        double crossp1 = calCorssProduct(p1, q1, p2);
        double crossp2 = calCorssProduct(p1, q2, p2);
        double crossp3 = calCorssProduct(q1, p1, q2);
        double crossp4 = calCorssProduct(q1, p2, q2);

        // 如果两个对叉积相乘的结果都是 负数，那么必定相交
        if (crossp1 * crossp2 < 0 && crossp3 * crossp4 < 0) {
            return true;
        }
        // q1 在 line1 上面
        else if (crossp1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }
        // q2 在 line1 上面
        else if (crossp2 == 0 && onSegment(p1, p2, q2)) {
            return true;
        }
        // p1 在 line2 上面
        else if (crossp3 == 0 && onSegment(q1, q2, p1)) {
            return true;
        }
        // p2 在 line2 上面
        else if (crossp4 == 0 && onSegment(q1, q2, p2)) {
            return true;
        } else {
            return false;
        }
    }

    // 行列式计算 ab x ac
    private static double calCorssProduct(Point a, Point b, Point c) {
        double abx = b.x - a.x;
        double aby = b.y - a.y;
        double acx = c.x - a.x;
        double acy = c.y - a.y;
        return abx * acy - aby * acx;
    }

    // 判断 点C 是否在线段 AB 上面
    private static boolean onSegment(Point a, Point b, Point c) {
        double minX = Math.min(a.x, b.x);
        double maxX = Math.max(a.x, b.x);
        double minY = Math.min(a.y, b.y);
        double maxY = Math.max(a.y, b.y);

        if (c.x >= minX && c.x <= maxX && c.y >= minY && c.y <= maxY) {
            return true;
        }
        return false;
    }
}
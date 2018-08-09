/*
平面内有n个矩形, 第i个矩形的左下角坐标为(x1[i], y1[i]), 右上角坐标为(x2[i], y2[i])。
如果两个或者多个矩形有公共区域则认为它们是相互重叠的(不考虑边界和角落)。
请你计算出平面内重叠矩形数量最多的地方,有多少个矩形相互重叠。

输入描述:
输入包括五行。
第一行包括一个整数n(2 <= n <= 50), 表示矩形的个数。
第二行包括n个整数x1[i](-10^9 <= x1[i] <= 10^9),表示左下角的横坐标。
第三行包括n个整数y1[i](-10^9 <= y1[i] <= 10^9),表示左下角的纵坐标。
第四行包括n个整数x2[i](-10^9 <= x2[i] <= 10^9),表示右上角的横坐标。
第五行包括n个整数y2[i](-10^9 <= y2[i] <= 10^9),表示右上角的纵坐标。

输出描述:
输出一个正整数, 表示最多的地方有多少个矩形相互重叠,如果矩形都不互相重叠,输出1。
示例1
输入
2
0 90
0 90
100 200
100 200
输出
2
 */

/**
 * Approach: 离散化 + 几何判断
 * 这个题和线段重叠那个题很像，但是多了一维就不是那么好搞了，这里的 n 很小，那么肯定就是从这里下手了；
 * 第一反应就是随机化算法，即随机生成一个点，然后判断这个点在多少个矩形中，维护一个最大值。
 * 但是坐标的范围太大了，因此，要进行离散化，把X轴和Y轴的坐标离散化成小坐标；
 * 但是，这个离散化算法还是有问题，无法处理两个矩形共线或共点！多么希望我们随机化出来的点不在矩形的边界上啊；
 * 注意到，我们离散化出来的坐标都是挨在一起的，例如1后面一定是2，但是如果我们把离散化后的坐标扩大两倍，
 * 那么2后面就是4了，中间的3是没有使用的，而单位区域的中心是不会在矩形的边界上的，
 * 因此我们可以随机化单位区域的中心，以这个点去判断是否在矩形中，这样就解决了不考虑边界和角落这个条件，
 * 而把离散化后的坐标扩大两倍，举个例子，中心就是(2 + 4) / 2 = 3，中心可以确保都是正整数。
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] xAxis = new int[n << 1];
            int[] yAxis = new int[n << 1];
            Rectangle[] rectangles = new Rectangle[n];
            for (int i = 0; i < n; i++) {
                rectangles[i] = new Rectangle();
            }
            // 左下角的横坐标
            for (int i = 0; i < n; i++) {
                xAxis[i] = rectangles[i].pos[0] = sc.nextInt();
            }
            // 左下角的纵坐标
            for (int i = 0; i < n; i++) {
                yAxis[i] = rectangles[i].pos[1] = sc.nextInt();
            }
            // 右上角的横坐标
            for (int i = 0; i < n; i++) {
                xAxis[n + i] = rectangles[i].pos[2] = sc.nextInt();
            }
            // 右上角的纵坐标
            for (int i = 0; i < n; i++) {
                yAxis[n + i] = rectangles[i].pos[3] = sc.nextInt();
            }
            Arrays.sort(xAxis);
            Arrays.sort(yAxis);

            // 离散化（离散化后的值需要 *2）
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((j & 1) == 0) {
                        rectangles[i].pos[j] = 2 * Arrays.binarySearch(xAxis, rectangles[i].pos[j]);
                    } else {
                        rectangles[i].pos[j] = 2 * Arrays.binarySearch(yAxis, rectangles[i].pos[j]);
                    }
                }
            }

            int rst = 1;
            int bound = 2 * (2 * n - 1);
            for (int i = 0; i + 2 < bound; i += 2) {
                for (int j = 0; j + 2 < bound; j += 2) {
                    int sum = 0;
                    for (int k = 0; k < n; k++) {
                        sum += isInRectangle(rectangles[k], i + 1, j + 1) ? 1 : 0;
                    }
                    rst = Math.max(rst, sum);
                }
            }
            System.out.println(rst);
        }
    }

    private static boolean isInRectangle(Rectangle rectangle, int x, int y) {
        return x > rectangle.pos[0] && x < rectangle.pos[2] && y > rectangle.pos[1] && y < rectangle.pos[3];
    }

    static class Rectangle {
        int[] pos;

        Rectangle() {
            this.pos = new int[4];
        }
    }

}


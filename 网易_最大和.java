/*
时间限制：1秒
空间限制：32768K

在一个N*N的数组中寻找所有横，竖，左上到右下，右上到左下，四种方向的直线连续D个数字的和里面最大的值
输入描述:
每个测试输入包含1个测试用例，第一行包括两个整数 N 和 D :
3 <= N <= 100
1 <= D <= N
接下来有N行，每行N个数字d:
0 <= d <= 100

输出描述:
输出一个整数，表示找到的和的最大值

输入例子1:
4 2
87 98 79 61
10 27 95 70
20 64 73 29
71 65 15 0

输出例子1:
193
 */

/**
 * Approach: Brute Force
 * 暴力解的题目，纯粹考验编码能力...
 * 四个方向全部算一遍，求最大值即可（根据题目说明，数据不会溢出，不用开long）
 * 挺反感网易在笔试中出这种题目的...
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] data = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                data[i][j] = sc.nextInt();
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                max = Math.max(max, Math.max(leftToRight(data, i, j, M), rightTopToLeftBottom(data, i, j, M)));
                max = Math.max(max, Math.max(leftTopToRightBottom(data, i, j, M), TopToBottom(data, i, j, M)));
            }
        }
        System.out.println(max);
    }

    private static int leftToRight(int[][] data, int row, int col, int M) {
        int count = 0, sum = 0;
        for (int j = col; count < M && j < data[0].length; j++) {
            count++;
            sum += data[row][j];
        }
        return count == M ? sum : -1;
    }

    private static int TopToBottom(int[][] data, int row, int col, int M) {
        int count = 0, sum = 0;
        for (int i = row; count < M && i < data.length; i++) {
            count++;
            sum += data[i][col];
        }
        return count == M ? sum : -1;
    }

    private static int leftTopToRightBottom(int[][] data, int row, int col, int M) {
        int count = 0, sum = 0;
        for (int i = row, j = col; count < M && i < data.length && j < data[0].length; i++, j++) {
            count++;
            sum += data[i][j];
        }
        return count == M ? sum : -1;
    }

    private static int rightTopToLeftBottom(int[][] data, int row, int col, int M) {
        int count = 0, sum = 0;
        for (int i = row, j = col; count < M && i >= 0 && j < data[0].length; i--, j++) {
            count++;
            sum += data[i][j];
        }
        return count == M ? sum : -1;
    }
}
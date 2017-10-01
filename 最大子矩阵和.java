Max Sum of Rectangle No Larger Than K 的一个子问题。
使用到了 动态规划 和 Kadane's algorithm.
主要的思想为：
遍历所有的行数组，将遍历的所有行数组相加，然后产生一个新的数组，
将问题转换为：Maximum Subarray.
然后只要求出所有 Maximum Subarray 的最大值即可。

/*
给定一个矩阵 matrix，其中矩阵中的元素可以包含正数、负数、和0，返回子矩阵的最大累加和。
例如，矩阵 matrix 为： 
0 -2 -7 0 
9 2 -6 2 
-4 1 -4 1 
-1 8 0 -2 

拥有最大和的子矩阵为： 
9 2 
-4 1 
-1 8 
其和为15。返回15
*/

public class Main {
    public static void main(String[] args) {
        // 最大子矩阵的累加和
        int matrix[][] = {{0, -2, -7, 0}, {9, 2, -6, 2}, {-4, 1, -4, 1}, {-1, 8, 0, -2}};
        maxSubmatrix(matrix);
    }
    
    public static void maxSubmatrix(int matrix[][]) {
        if(matrix == null || matrix.length == 0) {
            return;            
        }

        int max = 0;
        int col = matrix[0].length;
        int row = matrix.length;     
        for (int i = 0; i < row; i++) {
            int arr[] = new int[col];
            for (int j = i; j < row; j++) {
            //遍历所有的子行
                for(int k = 0; k < col; k++) {
                    arr[k] += matrix[j][k];
                    //将每子行的值进行相加然后利用子数组的最大和就可以求出子矩阵的最大和
                }
                max = Math.max(maxSubArray(arr), max);
                // 求出数组的子数组和最大值
            }
        }
        System.out.println(max);
    }
    // Kadane's algorithm
    // 还有另外一种 Prefix Sum 的写法
    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i : nums) {
            sum += i;
            max = Math.max(max, sum);
            sum = Math.max(sum, 0);
        }
        
        return max;
    }
}
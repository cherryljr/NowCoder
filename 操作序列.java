/*
小易有一个长度为n的整数序列,a_1,...,a_n。然后考虑在一个空序列b上进行n次以下操作:
1、将a_i放入b序列的末尾
2、逆置b序列
小易需要你计算输出操作n次之后的b序列。
输入描述:
输入包括两行,第一行包括一个整数n(2 ≤ n ≤ 2*10^5),即序列的长度。
第二行包括n个整数a_i(1 ≤ a_i ≤ 10^9),即序列a中的每个整数,以空格分割。

输出描述:
在一行中输出操作n次之后的b序列,以空格分割,行末无空格。

示例1
输入
4
1 2 3 4
输出
4 2 1 3
 */

/**
 * 这实际上是一题找规律的题目，并不需要每次都进行逆序操作。
 * 其规律就是：
 * 最后结果的前一半由：原字符串从后向前间隔一个进行输出；
 * 后一半由：原字符串中未被输出的字符，从前往后按照原本顺序输出即可。
 *
 * 注意点：
 * 输出格式中，最后一个字符后面不能有空格。
 */
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNextInt()) {
            int n = in.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = in.nextInt();
            }
            // 前一半：从最后一个数开始以2为步长递减
            for (int i = n - 1; i >= 0; i -= 2) {
                System.out.print(nums[i] + " ");
            }
            // 后一半：根据整数个数的奇偶，分别从第二个或第一个数开始以2为步长递增
            for (int i = n % 2; i < n - 2; i += 2) {
                System.out.print(nums[i] + " ");
            }
            // 输出最后一个数
            System.out.print(nums[n - 2]);
        }
    }
}

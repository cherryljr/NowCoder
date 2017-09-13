比较水的题目。
分析可得 机器1 只能得到奇数枚魔法币； 机器2 只能得到偶数枚魔法币。
因此我们只需要从结果递推回去便可以得到结果了。

/*
小易准备去魔法王国采购魔法神器,购买魔法神器需要使用魔法币,但是小易现在一枚魔法币都没有,但是小易有两台魔法机器可以通过投入x(x可以为0)个魔法币产生更多的魔法币。
魔法机器1:如果投入x个魔法币,魔法机器会将其变为2x+1个魔法币
魔法机器2:如果投入x个魔法币,魔法机器会将其变为2x+2个魔法币
小易采购魔法神器总共需要n个魔法币,所以小易只能通过两台魔法机器产生恰好n个魔法币,小易需要你帮他设计一个投入方案使他最后恰好拥有n个魔法币。 

输入描述:
输入包括一行,包括一个正整数n(1 ≤ n ≤ 10^9),表示小易需要的魔法币数量。

输出描述:
输出一个字符串,每个字符表示该次小易选取投入的魔法机器。其中只包含字符'1'和'2'。

示例1
输入
10

输出
122
*/

import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int coinCount = sc.nextInt();
            StringBuilder sb = new StringBuilder();
            while (coinCount > 0) {
                if ((coinCount & 1) == 0) {
                    //偶数
                    coinCount = (coinCount - 2) / 2;
                    sb.append("2");
                } else {
                    //奇数
                    coinCount = (coinCount - 1) / 2;
                    sb.append("1");
                }
            }
            System.out.println(sb.reverse().toString());
        }
    }
}

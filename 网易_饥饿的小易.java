/*
题目描述
小易总是感觉饥饿，所以作为章鱼的小易经常出去寻找贝壳吃。
最开始小易在一个初始位置x_0。对于小易所处的当前位置x，他只能通过神秘的力量移动到 4 * x + 3或者8 * x + 7。
因为使用神秘力量要耗费太多体力，所以它只能使用神秘力量最多100,000次。
贝壳总生长在能被1,000,000,007整除的位置(比如：位置0，位置1,000,000,007，位置2,000,000,014等)。
小易需要你帮忙计算最少需要使用多少次神秘力量就能吃到贝壳。

输入描述:
输入一个初始位置x_0,范围在1到1,000,000,006
输出描述:
输出小易最少需要使用神秘力量的次数，如果使用次数使用完还没找到贝壳，则输出-1

示例1
输入
125000000
输出
1
 */

/**
 * Approach 1: BFS
 * 求最短路径问题，第一反应就是使用 BFS.
 * 因为需要求步数，所以我们新建了一个 node 类，将步数信息封装在里面。
 * 后面的就是 BFS 模板写法了...
 *
 * 其实没想到 BFS 能过的...看来 case 卡的并不高
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    static class Node {
        long value;
        int step;

        public Node(long value, int step) {
            this.value = value;
            this.step = step;
        }
    }

    public static final long MOD = 1000000007;
    public static final long LIMIT = 100000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        long pos = sc.nextLong();
        if (pos == 0) {
            System.out.println(1);
            return;
        }

        int rst = -1;
        Queue<Node> queue = new LinkedList<>();
        Set<Long> visited = new HashSet<>();
        queue.offer(new Node(pos, 0));
        visited.add(pos);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            if (curr.step > LIMIT) {
                break;
            }
            if (curr.value % MOD == 0) {
                rst = curr.step;
                break;
            }

            long x1 = ((curr.value << 2) + 3) % MOD;
            long x2 = ((curr.value << 3) + 7) % MOD;
            if (!visited.contains(x1)) {
                queue.offer(new Node(x1, curr.step + 1));
                visited.add(x1);
            }
            if (!visited.contains(x2)) {
                queue.offer(new Node(x2, curr.step + 1));
                visited.add(x2);
            }
        }

        System.out.println(rst);
    }
}

/**
 * Approach 2: Mathematics
 * 转换成数学问题，我们可以找到其中的规律。
 * 因为题目中给出的数据非常特殊 4, 3; 8, 7.
 * 分别是 2^2 和 2^2-1 以及 2^3 和 2^3-1.
 * 对此我们想到的不仅仅是可以利用 位运算 进行优化。
 *
 * 4x+3 和 8x+7 的数学操作，可以用二进制的 左移 和 补1 表示：
 *  y = 4*x+3,相当于x的二进制左移2位，然后空位补1，即原先x的二进制为#####,则y的二进制为#####11，
 *  y = 8*x+3,相当于y的二进制左移3位，然后空位补1，即原先x的二进制为#####,则y的二进制为#####111。
 * 因此小易的移动，最终可以表达成 4x+3 操作进行了 m 次，8x+7 操作进行了 n 次
 *  4*x+3操作进行 m 次，则x的二进制后面增加 2m 个1
 *  8*x+7操作进行 n 次，则x的二进制后面增加 3n 个1
 * 故小易的移动，最终可以表达为：x的二进制后面增加了 2m+3n 个1
 * 移动的顺序对其到达没有影响
 *
 * 基于以上信息，我们可以对小易移动次数进行分析：
 *  初始位置为0，则直接满足，需移动0次。
 *  初始位置不为0，则记 steps = （2m+3n）, m取1到10_0000, n取1到10_0000
 * 所以，steps 的取值范围为[2,30_0000]。即：最多30_0000次搜索，就能获得结果。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static final long MOD = 1000000007;
    public static final long LIMIT = 300000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        long pos = sc.nextLong();
        if (pos == 0) {
            System.out.println(1);
            return;
        }

        //遍历，获取最小位移
        for (int i = 1; i <= LIMIT; i++) {
            //递推
            pos = ((pos << 1) + 1) % MOD;
            if (pos % MOD == 0) {
                //i是符合条件的最小偏移，然后对其进行分解
                for (int j = 0; j <= (i >> 1); j++) {
                    if ((i - (j << 1)) % 3 == 0) {
                        System.out.println((i + j) / 3);
                        return;
                    }
                }
            }
        }
        //超过最大次数还没匹配，则输出-1
        System.out.println(-1);
    }
}
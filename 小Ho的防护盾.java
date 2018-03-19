/*
题目：小Ho的防护盾
    时间限制:10000ms
    单点时限:1000ms
    内存限制:256MB
描述
小Ho的虚拟城市正在遭受小Hi的攻击，小Hi用来攻击小Ho城市的武器是一艘歼星舰，
这艘歼星舰会以T(T为大于0的整数)个单位时间的间隔向小Ho的城市轰击。歼星舰总共有N枚炮弹，其中第i枚会造成Ai点伤害值。

幸好小Ho的城市有K层护盾，每层护盾可以抵挡M点伤害。当某次轰击使得伤害值达或超过M时，该层护盾就会被击碎；
该次轰击溢出的伤害不会作用于下一层护盾；下一次轰击将由下一层护盾承受。

同时，受损但尚未被击碎护盾会以每单位时间减少1点伤害值的速度修复自己，直到伤害值降为0。
这就意味着小Hi的攻击间隔T越大，小Ho撑过这N枚炮弹的可能性就越大。

那么问题来了，小Hi的攻击间隔T至少需要是多少，小Ho城市的防护护盾才能不被全部击破？

为了使题目不存在歧义，规定：
小Hi的第i次攻击发生在时刻(i-1)*T
小Ho的第i次修复发生在时刻i-0.5

输入
第一行包含3个整数N、M和K，意义如前文所述。
第二行包含N个整数A1 - AN，表示小Hi第i枚炮弹的伤害值。
对于30%的数据，满足N<=100
对于100%的数据，满足1<=N<=100000
对于100%的数据，满足1<=K<=10, 1<=Ai, M<=100000

输出
输出使得小Ho城市的防护护盾不被全部击破的小Hi攻击间隔的最小值。如果不存在这样的T，则输出-1。

样例输入
3 5 1
3 3 3
样例输出
3

OJ地址：https://hihocoder.com/contest/hiho188/problem/1
 */

/**
 * Approach: Binary Search
 * 攻击间隔越大，护盾可能的回血就越多。回血上限为 M.
 * 如果小Hi的攻击中 伤害值大于等于M的炮弹 少于K发，那么如果攻击间隔过大，即便N发总伤害超过KM，也可能最终不能击破全部护盾。
 * 题目要求出可以使护盾不被全部击破的攻击间隔最小值。这要求我们能够挖掘出一个隐藏信息：
 *      因为护盾回复的速度为每秒钟 1 点，所以这也就意味着攻击间隔 time,只有在范围 [1...M] 上才有意义。
 *      低于 1，则说明全部炮弹一次性发射，这才题目中是禁止的（参考题目规定）
 *      大于 M，则说明当前护盾的 HP 已经回满了，时间再长这个值也不会增加，因此没有考虑的必要。
 *
 * 那么根据以上分析 最短攻击间隔time,显然可以利用 二分法 进行求解。
 * 具体操作为：
 *  根据二分法获得当前的攻击间隔 time,然后判断遍历一遍所有炮弹的伤害，看是否能够击破所有护盾即可。
 *  炮弹攻击护盾的过程可以直接运算模拟即可。时间复杂度为 O(n)
 *  如果不能全部击破护盾，则 right = mid (right边界缩小向左靠),否则 left = mid + 1 (mid 必定不符合条件)
 * 因此总体时间复杂度为 O(nlogm)
 */

import java.util.*;

public class Main {
    static int n, m, k;
    static int[] shells;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();
        shells = new int[n];
        for (int i = 0; i < n; i++) {
            shells[i] = sc.nextInt();
        }

        int count = 0;
        // 首先遍历整个炮弹数组，如果发现伤害值大于护盾M的炮弹个数 >= 护盾个数
        // 直接输出 -1 （恢复时间再久都没用）
        for (int shell : shells) {
            if (shell >= m) {
                count++;
            }
            if (count >= k) {
                System.out.println(-1);
                return;
            }
        }

        // 二分法 寻找最短输出时间间隔 (注意：left从是 1 开始)
        int left = 1, right = m;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (!canDestroy(mid)) {
                // 无法击破全部护盾
                right = mid;
            } else {
                // 能够击破全部护盾
                left = mid + 1;
            }
        }

        // 最后检查能否击破全部护盾
        if (canDestroy(left)) {
            System.out.println(-1);
        } else {
            System.out.println(left);
        }
    }

    private static boolean canDestroy(int time) {
        int leftShields = k;
        int hp = m;
        for (int shell : shells) {
            if (shell >= hp) {
                // 伤害值大于护盾值时，直接击破
                // 更新剩余护盾个数与护盾值
                leftShields--;
                hp = m;
            } else {
                // 如果未能击破，则扣去攻击值，并加上回复值
                hp -= shell;
                hp = hp + time >= m ? m : hp + time;
            }
            if (leftShields == 0) {
                // 无剩余护盾，代表能够击破全部护盾
                return true;
            }
        }
        return false;
    }

}
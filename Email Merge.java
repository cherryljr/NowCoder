/*
题目: Email Merge
时间限制:10000ms
单点时限:1000ms
内存限制:256MB

描述
You are given a list of usernames and their email addresses in the following format:
alice 2 alice@hihocoder.com alice@gmail.com
bob 1 bob@qq.com
alicebest 2 alice@gmail.com alice@qq.com
alice2016 1 alice@qq.com

Your task is to merge the usernames if they share common email address:
alice alicebest alice2016
bob

输入
The first line contains an integer N, denoting the number of usernames. (1 < N ≤ 10000)
The following N lines contain N usernames and their emails in the previous mentioned format.
Each username may have 10 emails at most.

输出
Output one merged group per line.
In each group output the usernames in the same order as the input.
Output the groups in the same order as their first usernames appear in the input.

样例输入
4
alice 2 alice@hihocoder.com alice@gmail.com
bob 1 bob@qq.com
alicebest 2 alice@gmail.com alice@qq.com
alice2016 1 alice@qq.com

样例输出
alice alicebest alice2016
bob
 */

/**
 * Approach: 倒排索引 + 并查集
 * 我们知道判断两个 字符串 A和B是否相等的复杂度是与字符串长度成正比的，要比 比较两个整数 是否相同的复杂度高。
 * 所以首先我们可以做的优化就是把 用户名 和 邮箱 都用 hashmap 映射到整数1, 2, 3 ... 上。这样比较就是O(1)的了。
 *
 * 输入给的数据是每个用户名对应哪些邮箱，我们利用倒排索引可以得到每个邮箱对应哪些用户名：
 * alice@hihocoder.com: alice
 * alice@gmail.com: alice alicetest
 * bob@qq.com: bob
 * alice@qq.com: alicetest alice2016
 * 注意这里我们为了描述方便使用的还是字符串，实际上你的程序这时处理的应该是映射后的整数。
 * 然后我们使用并查集把有相同邮箱的用户名合并到一个集合。
 * 注意由于输入的每个用户名最多10个邮箱，所以这里最多做O(10N)次合并。
 * 最后我们要把所有的集合输出。注意这里还需要花费点代码保证输出顺序与题目要求一致。
 * 本题主要考察的就是能否使用 倒排索引 + 并查集 同时使用 HashMap 对其进行优化。
 * 不过不得不说 输出格式非常不好调...大家多多注意。
 */

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // key -> 用户ID; value -> 用户名 （映射用户名称）
        Map<Integer, String> userMap = new HashMap<>();
        // key -> 邮箱地址; value -> 邮箱ID （映射邮箱地址）
        Map<String, Integer> emailMap = new HashMap<>();
        // key -> 邮箱地址; value -> 相应的所属用户名称 （创建倒排索引）
        Map<String, Set<Integer>> emailToUsers = new HashMap<>();
        int n = sc.nextInt();
        int emailIdx = 0;   // 用于生成邮箱ID

        for (int userIdx = 0; userIdx < n; userIdx++) {
            userMap.put(userIdx, sc.next());
            int m = sc.nextInt();
            for (int j = 0; j < m; j++) {
                String email = sc.next();
                if (!emailMap.containsKey(email)) {
                    emailMap.put(email, emailIdx++);
                }
                if (!emailToUsers.containsKey(email)) {
                    emailToUsers.put(email, new HashSet<>());
                }
                emailToUsers.get(email).add(userIdx);
            }
        }

        UnionFind uf = new UnionFind(n);
        for (Map.Entry<String, Set<Integer>> entry : emailToUsers.entrySet()) {
            Iterator<Integer> it = entry.getValue().iterator();
            boolean firstFlag = true;
            Integer first = 0;
            while (it.hasNext()) {
                if (firstFlag) {
                    // 获取该邮箱 第一次出现时 所属的用户名称
                    first = it.next();
                    firstFlag = false;
                } else {
                    Integer other = it.next();
                    // 只需要将该组内的 所有其他用户 与 第一个用户 union 即可
                    uf.union(first, other);
                }
            }
        }

        // 输出结果
        for (int i = 0; i < uf.parent.length; i++) {
            StringBuilder sb = new StringBuilder();
            Integer father = uf.parent[i];
            if (father == i) {
                // 如果是该区域的 father 节点，这就是这一组的开头
                sb.append(userMap.get(i));
            } else {
                // 自己不是开头，那么就是属于前面的某个项，已经输出过了
                continue;
            }
            for (int j = i + 1; j < uf.parent.length; j++) {
                if (uf.parent[j] == father) {
                    sb.append(" " + userMap.get(j));
                }
            }
            System.out.println(sb.toString());
        }
    }
}

// Union Find Template
class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // 查找某个元素的所在集合的根元素
    public int find(int a) {
        return parent[a];
    }

    // 合并两个集合
    public void union(int a, int b) {
        // 获得p和q的各自所在集合的根
        int aFather = find(a);
        int bFather = find(b);
        // 如果两个根相等，直接返回
        if (aFather == bFather) {
            return;
        }
        // 遍历一次，改变根使他们属于一个根,根是最小的那个
        for (int i = 0; i < parent.length; i++){
            if(aFather < bFather) {
                if (parent[i] == bFather) {
                    parent[i] = aFather;
                }
            } else {
                if (parent[i] == aFather) {
                    parent[i] = bFather;
                }
            }
        }
    }
}
该题和 LintCode 的 Majority Number.java 几乎一模一样
只不过这里的 Majority Number 可能不存在罢了，
故最后只需要再遍历一遍看 count 是否大于 n / 2 即可。
进一步进阶的题目可以看：
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number.java
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number%20II.java
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number%20III.java

/*
春节期间小明使用微信收到很多个红包，非常开心。在查看领取红包记录时发现，某个红包金额出现的次数超过了红包总数的一半。
请帮小明找到该红包金额。写出具体算法思路和代码实现，要求算法尽可能高效。
给定一个红包的金额数组gifts及它的大小n，请返回所求红包的金额。
若没有金额超过总数的一半，返回0。

测试样例：
[1,2,3,2,2],5

返回：2
*/

import java.util.*;
 
public class Gift {
    public int getValue(int[] gifts, int n) {
        if (gifts == null || gifts.length == 0) {
            return 0;
        }
 
        int count = 0;
        int candidate = 0;
        for (int i : gifts) {
            if (count == 0) {
                candidate = i;
                count = 1;
            } else {
                if (candidate == i) {
                    count++;
                } else {
                    count--;
                }
            }
        }
        count = 0;
        for (int i : gifts) {
            if (i == candidate) {
                count++;
            }
        }
 
        return count > n / 2 ? candidate : 0;
    }
}
该题目考查的是基础。
因为要求时间复杂度为 O(1).并且题目中说明只含有 中文 和 英文 两种字符。
所以我们想到了使用字节占用空间的方法。
  一个英文字符占 1 个字节，一个中文字符占 2 个字节。
  利用这点我们便可以计算出结果。
  比如：字符串中有 n 个字符，占用了 m 个字节。
  那么中文有：m - n 个； 英文有：2*n-m 个

/*
给定一个字符串，里面只含有 中文字符 和 英文字符。
请分别求出 中文字符 和 英文字符 的个数。

要求：不使用 循环，时间复杂度为 O(1)
*/

public class Solution {
    public static void main(String[] args) {
        String a = "test测试cherryljr樱花";
        int size = a.length();
        int bytelen = a.getBytes().length;
        // System.out.println(size);
        // System.out.println(bytelen);
        System.out.println("英文字符个数:" + (size * 2 - bytelen));
        System.out.println("中文字符个数:" + (bytelen - size));
    }
}
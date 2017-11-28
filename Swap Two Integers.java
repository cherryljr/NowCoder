import java.lang.reflect.Field;

/**
 * @author Administrator
 * 面试题目：
 * 主要定义了两个 Integer 变量，通过调用 swap 方法后，
 * 使得这两个变量交换数据，然后控制台打印交换前后的吧值。
 * 请写出 swap 方法的实现
 */

public class Solution {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        System.out.println("before swap: a=" + a + ", b=" + b);
        swap(a, b);
        System.out.println("after swap: a=" + a + ", b=" + b);
    }
    
    /**
     * 解法：
     * 因为 java 无法像 C/C++ 一样通过指针来实现两个数的交换
     * 故我们需要使用 java 的反射机制来解决问题。
     * 记得需要 catch exception
     * @param num1
     * @param num2
     */
    private static void swap(Integer num1, Integer num2) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int temp = num1.intValue();
            field.set(num1, num2);
            field.set(num2, new Integer(temp));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
��������ĵ�һ��Ӧ���� LintCode ����� Max Tree �����Ŀ��
֪ʶ��Ϊ������ Stack ���ҵ�һ�������� ��� / �ұ� ��һ�� ���� / С�� ��������
ʱ�临�Ӷ�Ϊ��O(N)
������Ƚ��� Max Tree �����������Ѷ����������һ��������

˼·��
	����˼·��һ���ġ�
	
/*
ս����Ϸ��������Ҫ���ھ�Ҫ�����ˣ���εĽ������������������������СB�����׶��ķ���������
�׶�λ��һ�����滷ɽ������У���Χ��n��Сɽ����һ��������ΪԤ����ʩ��СB�ƻ���ÿ��Сɽ������һ���۲��ڣ�
��ҹ��ͣ�Ĳt����Χ����������� һ��������������¼���ɽ���ϵĸ��ڽ���ȼ���̣�
�������������ڵ�ɽ��֮��û�и��ߵ�ɽ���ڵ�������֮��������ͨ·������ڿ��Թ۲쵽��һ��ɽ���ϵķ����Ƿ��ȼ��
����Сɽ���ڻ��ϣ���������Сɽ֮�����������ͬ������ͨ·�������������ڵ��������£�
һ��ɽ���ϸ��ڵ�ȼ�ķ������ٿ���ͨ��һ��ͨ·����һ�˹۲쵽��
�����������ڵĸ��ڣ�һ�˵ĸ���һ�����Է���һ�˵�ȼ�ķ��̡�
СB��Ƶ����ֱ���������һ����Ҫ�������ܹ��۲⵽�Է����̵ĸ��ڶԵ���������ϣ�����ܹ��������������⡣ 

��������:
�������ж���������ݣ�ÿһ��������ݵĵ�һ��Ϊһ������n(3<=n<=10^6),Ϊ�׶���Χ��Сɽ�������ڶ���Ϊn�����������α�ʾΪСɽ�ĸ߶�h��1<=h<=10^9��.

�������:
��ÿ��������ݣ��ڵ�����һ����������໥�۲쵽�ĸ��ڵĶ�����

��������1:
5
1 2 4 5 3

�������1:
7
*/

import java.util.Scanner;
import java.util.Stack;
  
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            int size = in.nextInt();
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = in.nextInt();
            }
            System.out.println(communications(arr));
        }
    }
   
    public static class Pair {
        public int value;
        public int times;
  
        public Pair(int value) {
            this.value = value;
            this.times = 1;
        }
    }
  
    public static long communications(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
         
        int size = arr.length;
        int maxIndex = 0;
        for (int i = 0; i < size; i++) {
            if (arr[maxIndex] < arr[i]) {
                maxIndex = i;
            }
        }
        // ���ҵ������е�һ�����ֵ�����ܲ�ֹһ����
        int value = arr[maxIndex]; 
        Stack<Pair> stack = new Stack<>();
        // �Ȱ����ֵѹ�뵥��ջջ��
        stack.push(new Pair(value));
        long res = 0L;
         
        int index = nextIndexInCircle(size, maxIndex);
        while (index != maxIndex) {
            value = arr[index];
            while (!stack.isEmpty() && value > stack.peek().value) {    // ����һ�������Ԫ��
                int times = stack.pop().times;      // ջ��Ԫ�س�ջ�����õ���ջ��Ԫ�ص��ۼƸ���
                // ��ջ��ջ��Ԫ��֮�乹�ɿɹ۲���ڶ��� C(times)2 = n*(n-1)/2���� times==1 ʱ�����ɵĿɹ۲���ڶ���Ϊ 0
                // ��ջ��ջ��Ԫ�����������Ԫ���Լ�ʹ����ջ��Ԫ�������ɵĿɹ۲���ڶ��� times * 2
                res += getInternalSum(times) + times * 2;
            }
            // �ۼ�ջ����������ͬԪ�ظ���
            if (!stack.isEmpty() && value == stack.peek().value) {
                stack.peek().times++;
            } else {   
                stack.push(new Pair(value));
            }
            index = nextIndexInCircle(size, index);
        }
        
        // ���� Stack �е�����Ԫ��
        while (!stack.isEmpty()) {  
            int times = stack.pop().times;
            res += getInternalSum(times);   // ��ͬԪ��֮�乹�ɵĿɹ۲���ڶ���
            if (!stack.isEmpty()) {
                res += times;   // ���������Ԫ�������ɵĿɹ۲���ڶ���  
                if (stack.size() >= 2) {    // �����沢����ջ�����ֵ
                    res += times;   // ��ջ�����ֵ�����ɵĿɹ۲���ڶ���
                } else {    // ����������ջ�����ֵ
                    res += stack.peek().times == 1 ? 0 : times; // ����������ջ�����ֵֻ��1������Ȼ���Ѿ��ڼӹ���
                }
            }
        }
         
        return res;
    }
  
		/**
     * �õ�Բ������һ��Ԫ�ص���������Ϊ����������������ʾԲ����
     */
    public static int nextIndexInCircle(int size, int i) {
        return i < (size - 1) ? (i + 1) : 0;
    }
  
    /**
     * ����ջ����ջ����������ͬԪ��֮�乹�ɵĿɹ۲���ڶ���
     */
    public static long getInternalSum(int n) {
        return n == 1 ? 0L : (long) n * (long) (n - 1) / 2L;
    }
}
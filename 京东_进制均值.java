���⿼������������
	1. ��μ���һ������ k ���� 
	2. �������������Լ�� GCD 

����:
	ͨ������ averageA �������һ���� radix ���Ƶ�ÿһλ���ϵĺͣ�
	Ȼ��ͨ�� calculate ������ý� 2~A-1 ���Ʊ��ĸ���λ��֮��ȫ����������ֵ
	������ sum �� A-2 �����Լ�������� GCD���Ӷ��õ��������

/*
������һ��CSרҵ��ѧ����СB����ѧ�����ܺò�����ֵ���������ر����Ȥ��ϲ���ü���������������ѧ���⣬
���ڣ���������һ����ֵ�任����Ϸ�������ּ�����о����ò�ͬ�Ľ��Ʊ�ʾһ������
��ʮ������123���Ϊ16����ʱֻ������λ��7��11��B�����ð˽��Ʊ�ʾΪ��λ��1��7��3��
����ͬ���Ʊ��ʱ������λ���ĺ�Ҳ��ͬ��������������ʮ�����ƺͰ˽����и�λ���ĺͷֱ���18��11,��
СB����Ȥ���ǣ�һ����A�����2��A-1���Ʊ��ʱ������λ��֮�͵ľ�ֵ�Ƕ��٣���ϣ�����ܰ������������⣿ 
���еļ��������ʮ���ƽ��У����Ҳ��ʮ���Ʊ�ʾΪ����Լ��ķ�����ʽ�� 

��������:
�������ж���������ݣ�ÿ���������Ϊһ������A(1 �� A �� 5000).

�������:
��ÿ��������ݣ��ڵ�����������X/Y����ʽ��������

��������1:
5
3

�������1:
7/3
2/1
*/

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        while (sc.hasNext()) {
            int num = sc.nextInt();
            int sum = calculate(num);
            // ���ӷ�ĸͬʱ�������Լ���� ���������ʽ
            System.out.printf("%d/%d\n", sum / GCD(sum, num-2), (num-2) / GCD(sum, num-2));
        }

    }
    // ��������������Լ��
    public static int GCD(int a, int b) {
        int temp = 0;
        if (a < b) {
            temp = a;
            a = b;
            b = temp;
        }
        int c = a % b;
        if (c == 0) {
            return b;
        } else {
            return GCD(b, c);
        }
    }
    
    // ��ÿ������ 2 �� n-1 ���Ƶ�λ���������
    public static int calculate(int n) {
        int radix = n - 1;
        int sum = 0;
        while (radix > 1) {
            sum += averageA(radix, n );
            radix--;
        }

        return sum;
    }
    
    //��һ������ĳһ���Ƶ����ֽ������
    public static int averageA(int radix, int n) {
        int sumOfN = 0;
        while (n > 0) {
            sumOfN += (n % radix);
            n /= radix;
        }
        return sumOfN;
    }
}
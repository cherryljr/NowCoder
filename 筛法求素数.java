ɸ����������
�� Basic Method �Ͻ����������ط����Ż�����������롣
������Ȼ���Ǵ����ظ�����ĵط���
eg. j=2*15=30 ʱɸ��һ�Σ����� j=5*6=30 ʱ��ɸ��һ�Ρ�(��Ȼ��ֻ�Ǹ�����~)

��˸��㷨�����Խ�һ�������Ż���
http://blog.csdn.net/dinosoft/article/details/5829550

import java.util.Arrays;
import java.util.Scanner;  

public class Main {  
    public static void main(String[] args) {  
        Scanner sc = new Scanner(System.in);  
        int n = sc.nextInt();
        boolean[] rst = Main.isPrime(n);
        
        for (int i = 2; i < rst.length; i++) {
            if (rst[i]) {
                System.out.println(i);
            }
        }
        
        sc.close();
    }
    
    private static boolean[] isPrime(int n) {
        boolean[] isPrime = new boolean[n + 1]; 
        // Initialize 
        Arrays.fill(isPrime, 2, n+1, true);  
        
        // �Ż��� 1 
        // i �ķ�Χֻ��Ҫ�� sqrt(n) ����
        for (int i = 2; i * i <= n; i++) {  
            if (isPrime[i]) {  
              // �Ż��� 2
            	// j �� i*i ��ʼ,��Ϊ 1*i, 2*i...�ȿ϶��� 1,2 ��ʱ���Ѿ�������ˣ��������Ա����ظ�����
                for (int j = i * i; j <= n; j = j + i) {  
                    isPrime[j] = false;  
                }  
            }  
        }
        
        return isPrime;
    }
    
} 





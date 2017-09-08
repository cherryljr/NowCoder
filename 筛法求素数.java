筛法求素数。
在 Basic Method 上进行了两个地方的优化。具体见代码。
但是仍然还是存在重复计算的地方。
eg. j=2*15=30 时筛了一次，但是 j=5*6=30 时又筛了一次。(当然这只是个栗子~)

因此该算法还可以进一步进行优化：
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
        
        // 优化点 1 
        // i 的范围只需要到 sqrt(n) 即可
        for (int i = 2; i * i <= n; i++) {  
            if (isPrime[i]) {  
              // 优化点 2
            	// j 从 i*i 开始,因为 1*i, 2*i...等肯定在 1,2 的时候已经计算过了，这样可以避免重复计算
                for (int j = i * i; j <= n; j = j + i) {  
                    isPrime[j] = false;  
                }  
            }  
        }
        
        return isPrime;
    }
    
} 





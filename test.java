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
    
    for (int i = 2; i < 8; i++) {
        if (i == 7) {
            return 2;
        }
    }
    
    return isPrime;
}
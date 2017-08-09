�����DP���⣬�� LintCode �� K Sum ����ʮ�����ơ�
������Ը� K Sum һ��ʹ����ά����ķ������������Ϊ�˽�ʡ�ռ䣬���ǲ����˶�ά���顣
ͬ���� K Sum Ҳ����ʹ�ö�ά�����һ���Ż���
Backpack DPʱ��for loop��д������˳���������ģ�
	1. �ȶ��� i ��˵������ǰi��Ԫ��
	2. Ȼ����� k ��˵������ǰi��Ԫ����ȡ����ֵ
	3. ������ j ��˵����������������������ָѧ��λ�ñ�ŵĲ�ܳ��� d��
		 ��K Sum �����У�������ȡ��ֵ�ĺ͸պ�Ϊ Target
		 ע�⣺�� K Sum �� 2D-Arrays ��˼·������ͬ���뿴��ϸ���� 

��Ϊ���ڸ��������⣬����Ҫά������ dp���飬 һ���������ֵ��һ��������Сֵ
State:
	fmax[i][k]: ��ʾǰi��ѧ���У�ȡk���������Ե�i��ѧ��Ϊ��β���õ������˻�
	fmin[i][k]: ��ʾǰi��ѧ���У�ȡk���������Ե�i��ѧ��Ϊ��β���õ������˻�
Function:
	fmax[i][k] = Math.max(fmax[i][k], Math.max(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
  fmin[i][k] = Math.min(fmin[i][k], Math.min(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
Initialize:
	f[i][0] = 0, f[0][i] = 0, f[i][1] = a[i]
Answer:
	Math.max(f[i][K])
	

/*
��Ŀ����
�� n ��ѧ��վ��һ�ţ�ÿ��ѧ����һ������ֵ��ţţ����� n ��ѧ���а���˳��ѡȡ k ��ѧ����
Ҫ����������ѧ����λ�ñ�ŵĲ���� d��ʹ���� k ��ѧ��������ֵ�ĳ˻�������ܷ������ĳ˻���

��������:
ÿ��������� 1 ������������ÿ���������ݵĵ�һ�а���һ������ n (1 <= n <= 50)����ʾѧ���ĸ�����
��������һ�У����� n ����������˳���ʾÿ��ѧ��������ֵ  ai��-50 <= ai <= 50����
��������һ�а�������������k �� d (1 <= k <= 10, 1 <= d <= 50)��

�������:
���һ�б�ʾ���ĳ˻���

ʾ��1
����

3
7 4 7
2 50
���

49
*/

import java.util.Scanner;
 
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextInt()) {
            int n = scan.nextInt();          // n ��ѧ��
            int[] arr = new int [n];
            for (int i = 0; i < n ; i++) {
                arr[i] = scan.nextInt();
            }
            int K = scan.nextInt();            // ѡ��K��
            int d = scan.nextInt();            // ����ѧ����λ�ñ�ŵĲ���� d
            
            System.out.println(getResult(n, arr, K, d));
        }
    }
    
    private static long getResult(int n, int[] arr, int K, int d) {
        long[][] fmax = new long[n + 1][K + 1]; // ��¼���˻�
        long[][] fmin = new long[n + 1][K + 1]; // ��¼��С�˻�
        // fmax[k][i]��ʾ : ��ѡ����k��ѧ���������Ե�i��ѧ��Ϊ��β�������������˻���
        // fmin[k][i]��ʾ : ��ѡ����k��ѧ���������Ե�i��ѧ��Ϊ��β������������С�˻���
        long  res = Integer.MIN_VALUE; // �ǵ���Long���ͣ�������ֵ��Χ
        
        // Initialize & Function
        for (int i = 1; i <= n; i++) {
            fmax[i][1] = arr[i - 1];
            fmin[i][1] = arr[i - 1];
            for (int k = 1; k <= K; k++) {
            	// ��j�� i-1 �� 1������ j �� i ֮��С�ڼ�� d������fmax[j][k-1]���Լ�fmin[j][k-1]��
                for (int j = i - 1 ; j > 0 &&  i - j <= d ; j--) {
                    fmax[i][k] = Math.max(fmax[i][k], Math.max(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
                    fmin[i][k] = Math.min(fmin[i][k], Math.min(fmax[j][k - 1] * arr[i - 1], fmin[j][k - 1] * arr[i - 1]));
                }
            }
            res = Math.max(res ,fmax[i][K]);
        }
        
        // Answer
        return res;
    }
}
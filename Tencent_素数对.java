ɸ����������һ��Ӧ�ð��ˡ�
˼·��Ҫ�� n �������з��ϵ������ԡ�
�����Ǵ���ÿ�ζ���� n �������е�������
Ȼ����� 2~n-1 �Ƿ�Ϊ true,������ count++.
������Ϊ��Ŀ����ȷ������ input<1000 
���ֱ�����1000�������е���������Ҫ�Ļ����ü��ɡ�

/*
����һ������������д��������ж��ٶ������ĺ͵���������������������������������ֵС��1000��
�磬����Ϊ10, ����Ӧ��������Ϊ2�����������������ĺ�Ϊ10,�ֱ�Ϊ(5,5),(3,7)�� 

��������:
�������һ������n,(3 �� n < 1000)

�������:
�������

ʾ��1
����
10

���
2
*/

public class Main { 
    static boolean[] isPrime = new boolean[1000];
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        isPrime();
        while (sc.hasNext()) {
            int a = sc.nextInt();
            System.out.println(countPrimePairs(a));
        }
        sc.close();
    }
    
    private static int countPrimePairs(int a) {
        int count = 0;
        for (int i = 2; i <= a / 2; i++) {
            if (isPrime[i] && isPrime[a - i]) {
                count++;
            }
        }
        return count;
    }
    
    // ɸ�����������Ż��棩
    private static void isPrime() {
        Arrays.fill(isPrime, 2, 999, true);
        
        for (int i = 2; i * i < 1000; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < 1000; j += i) {
                    isPrime[j] = false;
                }
            }
        }
    }
}

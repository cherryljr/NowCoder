/** 
 * ��Ѫ������,��Ч�ʰ汾.<br> 
 * һ��4λ���֣����Բ��2��2λ�����ֵĳ˻�,˳���ޡ�<br> 
 * ���� 1395 =15 * 93 
 */  
public class Vampire {  
  public static void main(String[] arg) {  
    String[] ar_str1, ar_str2;  
    int sum = 0;  
    int from;  
    int to;  
    int i_val;  
    int count = 0;  
    // ˫��ѭ�����  
    for (int i = 10; i < 100; i++) {  
      // j=i+1�����ظ�  
      from = Math.max(1000 / i, i + 1);  
      to = Math.min(10000 / i, 100);  
      for (int j = from; j < to; j++) {  
        i_val = i * j;  
        // �öδ��뿴ע��
        if (i_val % 100 == 0 || (i_val - i - j) % 9 != 0) {  
          continue;  
        }  
        count++;  
        ar_str1 = String.valueOf(i_val).split("");  
        ar_str2 = (String.valueOf(i) + String.valueOf(j)).split("");  
        Arrays.sort(ar_str1);  
        Arrays.sort(ar_str2);  
        if (Arrays.equals(ar_str1, ar_str2)) {// �����Ƚ�,Ϊ�����ҵ�һ��  
          sum++;  
          System.out.println("��" + sum + "��: " + i + "*" + j + "=" + i_val);  
        }  
      }  
    }  
    System.out.println("���ҵ�" + sum + "����Ѫ����");  
    System.out.println(count);  
  }  
}  
 
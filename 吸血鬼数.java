/** 
 * 吸血鬼数字,高效率版本.<br> 
 * 一个4位数字，可以拆分2个2位数数字的乘积,顺序不限。<br> 
 * 比如 1395 =15 * 93 
 */  
public class Vampire {  
  public static void main(String[] arg) {  
    String[] ar_str1, ar_str2;  
    int sum = 0;  
    int from;  
    int to;  
    int i_val;  
    int count = 0;  
    // 双重循环穷举  
    for (int i = 10; i < 100; i++) {  
      // j=i+1避免重复  
      from = Math.max(1000 / i, i + 1);  
      to = Math.min(10000 / i, 100);  
      for (int j = from; j < to; j++) {  
        i_val = i * j;  
        // 该段代码看注释
        if (i_val % 100 == 0 || (i_val - i - j) % 9 != 0) {  
          continue;  
        }  
        count++;  
        ar_str1 = String.valueOf(i_val).split("");  
        ar_str2 = (String.valueOf(i) + String.valueOf(j)).split("");  
        Arrays.sort(ar_str1);  
        Arrays.sort(ar_str2);  
        if (Arrays.equals(ar_str1, ar_str2)) {// 排序后比较,为真则找到一组  
          sum++;  
          System.out.println("第" + sum + "组: " + i + "*" + j + "=" + i_val);  
        }  
      }  
    }  
    System.out.println("共找到" + sum + "组吸血鬼数");  
    System.out.println(count);  
  }  
}  
 
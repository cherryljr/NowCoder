����� LintCode �� Majority Number.java ����һģһ��
ֻ��������� Majority Number ���ܲ����ڰ��ˣ�
�����ֻ��Ҫ�ٱ���һ�鿴 count �Ƿ���� n / 2 ���ɡ�
��һ�����׵���Ŀ���Կ���
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number.java
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number%20II.java
https://github.com/cherryljr/LintCode/blob/master/Majority%20Number%20III.java

/*
�����ڼ�С��ʹ��΢���յ��ܶ��������ǳ����ġ��ڲ鿴��ȡ�����¼ʱ���֣�ĳ����������ֵĴ��������˺��������һ�롣
���С���ҵ��ú����д�������㷨˼·�ʹ���ʵ�֣�Ҫ���㷨�����ܸ�Ч��
����һ������Ľ������gifts�����Ĵ�Сn���뷵���������Ľ�
��û�н���������һ�룬����0��

����������
[1,2,3,2,2],5

���أ�2
*/

import java.util.*;
 
public class Gift {
    public int getValue(int[] gifts, int n) {
        if (gifts == null || gifts.length == 0) {
            return 0;
        }
 
        int count = 0;
        int candidate = 0;
        for (int i : gifts) {
            if (count == 0) {
                candidate = i;
                count = 1;
            } else {
                if (candidate == i) {
                    count++;
                } else {
                    count--;
                }
            }
        }
        count = 0;
        for (int i : gifts) {
            if (i == candidate) {
                count++;
            }
        }
 
        return count > n / 2 ? candidate : 0;
    }
}
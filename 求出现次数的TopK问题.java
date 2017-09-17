先遍历一遍整个数组进行词频统计，用 HashMap 储存每个 String 出现的次数。
维护一个 K 大小的最小堆，遍历 HashMap，当堆内元素未满K个时，直接将该元素
offer 到堆中，当堆满了之后，每次将遍历到元素的 timers 与 堆顶元素的出现次数相比，
若更大，这将堆顶元素弹出推入新的元素。重新进行堆的构造。
毫无以为，这里我们会用到 PriorityQueue 这个数据结构。
该数据结构的特点是：每当 offer(), poll() 一个元素的时候，都会对堆进行重新构造，
并且保证堆顶为最小元素。

/*
题目：
给定String类型的数组strArr,再给定整数k,请严格按照排名顺序打印出现次数前k名的字符串。
若出现次数一样多可以任意打印两个字符串。

示例1：
strArr = {"1", "2", "3", "4"}  k = 2
输出：
No1: 1, times: 1
No2: 2, times: 1

示例2：
strArr = {"1", "1", "3", "4"}  k = 2
输出：
No1: 1, times: 2
No2: 3, times: 1

要求：
时间复杂度为 O(nlogk)
n为 strArr 的长度

*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Solution {

    public static class Node {
        public String str;
        public int times;

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }

    public static void printTopKAndRank(String[] arr, int topK) {
        if (arr == null || topK < 1) {
            return;
        }
        
        // Use HashMap to restore the occurences of a string / number
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i != arr.length; i++) {
            String cur = arr[i];
            if (!map.containsKey(cur)) {
                map.put(cur, 1);
            } else {
                map.put(cur, map.get(cur) + 1);
            }
        }
        
        // Use Heap to restore topK strings
        PriorityQueue<Node> pq = new PriorityQueue<>(topK, new Comparator<Node>(){
            @Override
            public int compare(Node a, Node b) {
                return a.times - b.times;
            }
        });
        int index = 0;
        for (Entry<String, Integer> entry : map.entrySet()) {
            String str = entry.getKey();
            int times = entry.getValue();
            Node node = new Node(str, times);
            if (index != topK) {
                pq.offer(node);
                index++;
            } else {
                // peek element has min occurences in topK element
                if (pq.peek().times < node.times) {
                    pq.poll();
                    pq.offer(node);
                }
            }
        }
        
        // Reverse the element in Heap
        int len = pq.size();
        ArrayList<Node> rst = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            // Once poll an element the heap will heapify again.
            rst.add(pq.poll());
        }
        
        int count = 0;
        for (int i = rst.size() - 1; i >= 0; i--) {
            System.out.print("No." + (++count) + ": ");
            System.out.print(rst.get(i).str + ", times: ");
            System.out.println(rst.get(i).times);
        }
    }

    public static String[] generateRandomArray(int len, int max) {
        String[] res = new String[len];
        for (int i = 0; i != len; i++) {
            res[i] = String.valueOf((int) (Math.random() * (max + 1)));
        }
        return res;
    }

    public static void printArray(String[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        String[] arr = generateRandomArray(50, 10);
        int topK = 5;
        printArray(arr);
        printTopKAndRank(arr, topK);

    }
}
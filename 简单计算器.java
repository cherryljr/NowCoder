/*
题目: 简单计算器
	时间限制:10000ms
	单点时限:1000ms
	内存限制:256MB
描述
编写一个程序可以完成基本的带括号的四则运算。其中除法(/)是整除，并且在负数除法时向0取整。
(C/C++/Java默认的除法就是向0取整，python默认的是向负无穷取整。)

例如计算 100 * ( 2 + 12 ) - (20 / 3) * 2, 结果是1388。

输入
一个长度不超过100的字符串，代表要计算的算式。包含数字0-9以及+-/*()
输入保证计算过程不会超过32位有符号整数，并且其中的'-'都是减号没有负号。
输出
计算的结果

样例输入
100*(2+12)-(20/3)*2
样例输出
1388

OJ地址：https://hihocoder.com/contest/hiho169/problem/1
*/

/**
 * Approach 1: Transform into Postfix
 * 对于计算一个表达式而言，后缀表达式无疑是最方便的。
 * 因此我们可以先将 表达式 转换成 后缀表达式。
 * 然后再计算即可。
 * 
 * 对于将 中序表达式 转换成 后缀表达式 可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Convert%20Expression%20to%20Reverse%20Polish%20Notation.java
 *
 * 对于后缀表达式的 计算 可以参考：
 * https://github.com/cherryljr/LintCode/blob/master/Evaluate%20Reverse%20Polish%20Notation.java
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String exp = sc.nextLine();
        // Transform the infix expression into postfix expression
        String[] tokens = infixToPostfix(exp);
        Stack<Integer> stack = new Stack<>();

        // Calculate the result by postfix
        for (String s : tokens) {
            if (s.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (s.equals("-")) {
                stack.push(-stack.pop() + stack.pop());
            } else if (s.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (s.equals("/")) {
                int x = stack.pop(), y = stack.pop();
                stack.push(y / x);
            } else {
                stack.push(Integer.parseInt(s));
            }
        }

        System.out.println(stack.pop());
    }

    /**
     * The main method that converts given infix expression to postfix expression.
     */
    private static String[] infixToPostfix(String exp) {
        if (exp == null || exp.length() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        char[] chars = exp.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            // Current token is a whitespace, skip it
            if (chars[i] == ' ') {
                continue;
            }
            // Current token is a number, push it to stack for numbers
            if (Character.isDigit(chars[i])) {
                // get the number from string
                while (i < chars.length && Character.isDigit(chars[i])) {
                    sb.append(chars[i++]);
                }
                i--;
                sb.append(" ");
            } else if (chars[i] == '(') {
                // If the scanned character is an '(', push it to the stack.
                stack.push(chars[i]);
            } else if (chars[i] == ')') {
                //  If the scanned character is an ')', pop and output from the stack until an '(' is encountered.
                while (!stack.isEmpty() && stack.peek() != '(') {
                    sb.append(stack.pop() + " ");
                }
                stack.pop();
            } else {
                // an operator is encountered
                int precedence = getPrecedence(chars[i]);
                while (!stack.isEmpty() && precedence <= getPrecedence(stack.peek())) {
                    sb.append(stack.pop() + " ");
                }
                stack.push(chars[i]);
            }
        }
        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            sb.append(stack.pop() + " ");
        }

        // trim the string and split it by space
        return sb.toString().trim().split(" ");
    }

    /**
     * A utility function to return precedence of a given operator
     * Higher returned value means higher precedence
     */
    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return-1;
        }
    }
}

/**
 * Approach 2: Two Stacks One Pass
 * 对于中序表达式，我们只需要经过处理，仍然能够直接计算出结果来。
 * 只是相比与 后缀表达式 稍微麻烦些罢了。
 *
 * 思路：
 * 利用两个stack，一个存数字，一个存符号。如果遇到数字直接存到数字stack；
 * 如果遇到符号，有几种情况：
 *  1.当前符号比上一个符号优先级高，比如*高于+，那么直接进栈
 *  2.当前符号低于上一个，那么就要把所有已经在 stack 里面优先级大于等于当前符号的全算完，再 push 当前符号
 *  3.当前符号是“（”，直接push
 *  4.当前符号是“）”，就要把所有“（”以前的符号全部算完
 * 可以发现，该方法与 Approach 1 几乎相同。
 * 其中 存符号 的Stack与 Approach 1 中转换后缀表达式中的 stack 是相同的；
 * 而 存数组 的Stack只是存下了计算的信息，使得我们只需要遍历一次即可。
 *
 * 参考资料：
 * https://www.geeksforgeeks.org/expression-evaluation/
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String exp = sc.nextLine();
        System.out.println(calculate(exp));
    }

    public static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] tokens = s.toCharArray();
        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<>();
        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ') {
                continue;
            }

            // Current token is a number, push it to stack for numbers
            if (Character.isDigit(tokens[i])) {
                int num = 0;
                // Convert the String into number
                while (i < tokens.length && Character.isDigit(tokens[i])) {
                    num = num * 10 + tokens[i++] - '0';
                }
                // Remember to roll back number i
                i--;
                values.push(num);
            }
            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            }
            // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')') {
                while (!values.isEmpty() && !ops.isEmpty() && ops.peek() != '(') {
                    values.push(calc(values.pop(), values.pop(), ops.pop()));
                }
                ops.pop();
            }
            // Current token is an operator.
            else {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                int precedence = getPrecedence(tokens[i]);
                while (!values.isEmpty() && !ops.empty() && precedence <= getPrecedence(ops.peek())) {
                    values.push(calc(values.pop(), values.pop(), ops.pop()));
                }
                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!values.isEmpty() && !ops.empty()) {
            values.push(calc(values.pop(), values.pop(), ops.pop()));
        }

        // Top of 'values' contains result, return it
        return values.pop();
    }

    /**
     * A utility function to return precedence of a given operator
     * Higher returned value means higher precedence
     */
    public static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return-1;
        }
    }

    /**
     * A utility method to apply an operator 'op' on operands 'a' and 'b'.
     * Return the result.
     */
    public static int calc(int b, int a, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    System.out.println("Cannot divide by zero");
                    return -1;
                }
                return a / b;
        }
        return 0;
    }
}

/**
 * Approach 3: Recursion and Stack
 * 以上两种方法差别并不大，实质上都是利用了 后缀表达式 进行计算。
 * 但是这里使用的方法则是利用 递归 来实现 模拟计算。
 * （之所以引入 递归 的原因是因为 小括号 的存在）
 * 我们可以假设如果没有 小括号，那么我们只需要处理 加减 和 乘除 这两个不同的优先级即可。
 * 只用 Stack 即可简单实现。
 * 具体可以参见：
 *
 * 但是引入了 小括号 之后使得计算变得有些麻烦了。
 * 因此这里使用了 递归 计算括号内结果的方式。
 * 做法为：
 *  1. 从左向右遍历，当我们没有遇到 '(' 时，就按照普通的方法进行计算。
 *  2. 当遇到 '(' 时,我们递归调用 value() 函数，并传入位置信息： 当前位置+1 （跳过左括号的位置）
 *  而 value() 函数将返回两个值：括号内的计算结果 和 右括号的位置。
 *  3. 得益于递归调用 value() 的方法，我们可以在当前步骤无需关心括号带来的麻烦。
 */

import java.io.BufferedInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        String exp = sc.nextLine();
        System.out.println(calculate(exp));
    }

    public static int calculate(String str) {
        return value(str.toCharArray(), 0)[0];
    }

    public static int[] value(char[] str, int i) {
        LinkedList<String> que = new LinkedList<>();
        int pre = 0;
        int[] rst = null;
        while (i < str.length && str[i] != ')') {
            if (Character.isDigit(str[i])) {
                pre = pre * 10 + str[i++] - '0';
            } else if (str[i] != '(') {
                addNum(que, pre);
                que.addLast(String.valueOf(str[i++]));
                pre = 0;
            } else {
                // 遇到 '(' 递归调用 value.（跳过左括号的位置）
                rst = value(str, i + 1);
                pre = rst[0];
                i = rst[1] + 1;
            }
        }
        addNum(que, pre);
        return new int[] { getNum(que), i };
    }

    public static void addNum(LinkedList<String> que, int num) {
        if (!que.isEmpty()) {
            int cur = 0;
            String top = que.pollLast();
            if (top.equals("+") || top.equals("-")) {
                que.addLast(top);
            } else {
                cur = Integer.valueOf(que.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        que.addLast(String.valueOf(num));
    }

    public static int getNum(LinkedList<String> que) {
        int res = 0;
        boolean add = true;
        String cur = null;
        int num = 0;
        while (!que.isEmpty()) {
            cur = que.pollFirst();
            if (cur.equals("+")) {
                add = true;
            } else if (cur.equals("-")) {
                add = false;
            } else {
                num = Integer.valueOf(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }
}